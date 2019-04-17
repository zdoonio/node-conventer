package model

import net.fwbrasil.activate.entity.{Entity, LazyList}

/**
  * Created by Dominik Zduńczyk on 17.04.19.
  */
case class Node(
  nodeId: Int,
  name: String,
  nodes: LazyList[Node]
) extends Entity


object Node {

  def create(nodeData: NodeData) =
    Node(nodeData.nodeId, nodeData.name, nodeData.nodes)
}