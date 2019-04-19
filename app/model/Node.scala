package model

import java.io.File

import net.fwbrasil.activate.entity.{Entity, LazyList}
import org.apache.poi.ss.usermodel.{Workbook, WorkbookFactory}
import PersistanceContext._


/**
  * Created by Dominik Zduńczyk on 17.04.19.
  */
case class Node(
  nodeId: Int,
  name: String,
  mainNode: Option[Node]
) extends Entity


object Node {

  def create(mainNodeData: NodeData, subNodesData: List[NodeData]) = {

    val savedNode = select[Node] where (mainNodeData.nodeId :== _.nodeId)

    val node = if(savedNode.isEmpty)
      Node(mainNodeData.nodeId, mainNodeData.name, None)
    else
      savedNode.head

    subNodesData.map { subNodeData =>
      Node(subNodeData.nodeId, subNodeData.name, Some(node))
    }

  }

  def parseNodes(file: File) = {
    def load: Workbook =
      WorkbookFactory.create(file)

    val sheet = load.getSheetAt(0)
    val cells = for (r <- 1 to sheet.getLastRowNum; c <- 0 to 3)
      yield sheet.getRow(r).getCell(c)

    val cellsToMap = cells.filterNot(_.toString == "").grouped(2).map { row =>
      (row(0), row(1))
    }.toList

    val nodeData = cellsToMap.map { row =>
      NodeData(row._2.getNumericCellValue.toInt, row._1.toString, List())
    }

    val groupNodes = nodeData.partition(_.name.length == 1)
    val groupSubNodes =  groupNodes._2.partition(_.name.length == 2)

    val nodesAndSubNodes = groupNodes._1.map { mainNodeData =>
      (mainNodeData, groupSubNodes._1.filter(mainNodeData.name.charAt(0) == _.name.charAt(0)))
    }

    val subNodesAndSubNodes = groupSubNodes._1.map { subNodeData =>
      (subNodeData, groupSubNodes._2.filter{ subNode =>
        subNodeData.name.charAt(0) == subNode.name.charAt(0) && subNodeData.name.charAt(1) == subNode.name.charAt(1)
      })
    }

    nodesAndSubNodes.foreach { nodeAndSubNode =>
      transactional {
        Node.create(nodeAndSubNode._1, nodeAndSubNode._2)
      }
    }

    subNodesAndSubNodes.foreach { subNodeAndSubNode =>
      transactional {
        Node.create(subNodeAndSubNode._1, subNodeAndSubNode._2)
      }
    }
  }

  def getMainNodeList: List[Node] = transactional(select[Node] where(_.mainNode.isNull)).reverse

  def getSubNodeList(node: Node): List[Node] = transactional(select[Node] where(_.mainNode.orNull.id :== node.id))

  def getBaseNode(node: Node): Node = {
    node.mainNode.map { prev =>
      getBaseNode(prev)
    }.getOrElse(node)
  }

  def toNodeData(node: Node): NodeData = {
    val subNodes = getSubNodeList(node)

    val nodesData = subNodes.map { node =>
      val subNodeNodes = getSubNodeList(node).map { subNodeNodes =>
        NodeData(subNodeNodes.nodeId, subNodeNodes.name, List())
      }
      NodeData(node.nodeId, node.name, subNodeNodes)
    }

    NodeData(node.nodeId, node.name, nodesData)
  }
}