package model

import net.fwbrasil.activate.ActivateContext
import net.fwbrasil.activate.slick.SlickQueryContext
import net.fwbrasil.activate.storage.relational.PooledJdbcRelationalStorage
import net.fwbrasil.activate.storage.relational.idiom.{h2Dialect, SqlIdiom}
import play.api.{Mode, Play}

object PersistanceContext extends ActivateContext with SlickQueryContext{

  override val storage = Play.current.mode match {
    case Mode.Test =>
      new PooledJdbcRelationalStorage {
        val jdbcDriver = "org.h2.Driver"
        val user = Some("USER")
        val password = Some("PASS")
        val url = "jdbc:h2:mem:my_database;DB_CLOSE_DELAY=-1"
        val dialect = h2Dialect
      }

    case _ =>
      new PooledJdbcRelationalStorage {
        val jdbcDriver = Play.current.configuration.getString("activate.db.driver").get
        val user = Play.current.configuration.getString("activate.db.user")
        val password = Play.current.configuration.getString("activate.db.password")
        val url = Play.current.configuration.getString("activate.db.url").get
        val dialect = SqlIdiom.dialectsMap.find {
          case (key, idiom) => url.contains(key.substring(0, key.length - 7))
        }.get._2
      }
  }
}
