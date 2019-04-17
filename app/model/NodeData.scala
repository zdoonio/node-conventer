package model

import net.fwbrasil.activate.entity.LazyList

/**
  * Created by Dominik Zduńczyk on 17.04.19.
  */
case class NodeData(
  nodeId: Int,
  name: String,
  nodes: LazyList[Node]
)
