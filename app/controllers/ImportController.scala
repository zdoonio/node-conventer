package controllers

import model.Node
import play.api.mvc.{Action, Controller}


/**
  * Created by Dominik Zduńczyk on 17.04.19.
  */
object ImportController extends Controller {

  def importNodes = Action(parse.multipartFormData) { implicit request =>
    request.body.file("sheet").map { content =>
      try {
        Node.parseNodes(content.ref.file)
      } catch {
        case e: Exception =>
          InternalServerError
      }
      Ok
    }.getOrElse {
      BadRequest
    }
  }
}
