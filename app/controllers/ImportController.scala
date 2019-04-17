package controllers

import play.api.Logger
import play.api.mvc.{Action, Controller}

/**
  * Created by Dominik Zduńczyk on 17.04.19.
  */
object ImportController extends Controller {

  def importNodes = Action(parse.multipartFormData) { implicit request =>
    request.body.file("sheet").map { sheet =>
      Logger.info(sheet.toString)

      Ok
    }.getOrElse {
      BadRequest
    }
  }
}
