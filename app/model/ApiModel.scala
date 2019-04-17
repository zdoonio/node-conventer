package model

import play.api.libs.json.{JsValue, Json, Writes}

/**
  * Created by Dominik Zduńczyk on 17.04.19.
  */
object ApiModel {

  implicit val NodeList_format = Json.format[NodeData]

}
