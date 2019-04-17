package model

import net.fwbrasil.activate.entity.{Entity, LazyList}
import PersistanceContext._

/**
  * Created by Dominik Zduńczyk on 17.04.19.
  */
case class Node(
  nodeId: Int,
  name: String,
  nodes: LazyList[Node]
) extends Entity


object Node {

  def create(nodeData: NodeData) = {
    val nodes = nodeData.nodes.map { node =>
      Node(node.nodeId, nodeData.name, listToLazyList(List()))
    }
    Node(nodeData.nodeId, nodeData.name, nodes)
  }
}