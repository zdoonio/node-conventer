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

      val nodesAndSubNodes = groupNodes._1.map { mainNodeData =>
        (mainNodeData, groupNodes._2.filter(mainNodeData.name.charAt(0) == _.name.charAt(0)))
      }

      nodesAndSubNodes.foreach { nodeAndSubNode =>
        transactional {
          Node.create(nodeAndSubNode._1, nodeAndSubNode._2)
        }
      }

      Ok
    }.getOrElse {
      BadRequest
    }
  }
}
