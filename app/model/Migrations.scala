package model

import net.fwbrasil.activate.migration.Migration
import play.api.Logger
import PersistanceContext._

/**
  * Created by Dominik Zduńczyk on 17.04.19.
  */

object SchemaConst {
  val baseDbTimeStamp = 1555495971L
}

class CreateSchema extends Migration {
  override def timestamp: Long = 1555495971L

  def up = {
    Logger.debug("Recreating all tables")
    removeAllEntitiesTables.ifExists
    createTableForAllEntities.ifNotExists
  }

}