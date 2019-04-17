package controllers

import model.{Node, NodeData, PersistanceContext}
import play.api.mvc.{Action, Controller}
import org.apache.poi.ss.usermodel.{Workbook, WorkbookFactory}
import PersistanceContext._

/**
  * Created by Dominik Zduńczyk on 17.04.19.
  */
object ImportController extends Controller {

  def importNodes = Action(parse.multipartFormData) { implicit request =>
    request.body.file("sheet").map { content =>

      def load: Workbook =
        WorkbookFactory.create(content.ref.file)

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

      groupNodes._1.foreach { mainNodeData =>
        groupNodes._2.foreach { subNodeData =>
          if(mainNodeData.name.charAt(0) == subNodeData.name.charAt(0))
            mainNodeData.nodes ::: List(subNodeData)
        }

        transactional {
          Node.create(mainNodeData)
        }

      }

      Ok
    }.getOrElse {
      BadRequest
    }
  }
}
