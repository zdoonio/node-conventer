package controllers

import model.{Node}
import model.ApiModel.NodeList_format
import play.api.libs.json.Json
import play.api.mvc._

object ApiController extends Controller {

  def getNodeList = Action { implicit request =>
    try {
        val nodes = Node.getMainNodeList.map { node =>
          Node.toNodeData(node)
        }

      Ok(Json.obj("nodes" -> nodes))
    } catch {
      case e : IllegalArgumentException => BadRequest(Json.obj("error" -> e.toString))
    }
  }
}