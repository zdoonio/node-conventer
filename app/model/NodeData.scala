package model

/**
  * Created by Dominik Zduńczyk on 17.04.19.
  */
case class NodeData(
  nodeId: Int,
  name: String,
  nodes: List[NodeData]
)
