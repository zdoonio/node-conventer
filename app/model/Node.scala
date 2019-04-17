package model

import net.fwbrasil.activate.entity.{Entity, LazyList}
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

    val node = Node(mainNodeData.nodeId, mainNodeData.name, None)

    subNodesData.map { subNodeData =>
      Node(subNodeData.nodeId, subNodeData.name, Some(node))
    }

  }

  def getMainNodeList: List[Node] = transactional(select[Node] where(_.mainNode.isNull))

  def getSubNodeList(node: Node): List[Node] = transactional(select[Node] where(_.mainNode.orNull.id :== node.id))

  def toNodeData(node: Node): NodeData = {
    val subNodes = getSubNodeList(node)

    val nodesData = subNodes.map { node =>
      NodeData(node.nodeId, node.name, List())
    }

    NodeData(node.nodeId, node.name, nodesData)
  }
}