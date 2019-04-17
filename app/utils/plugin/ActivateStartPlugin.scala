package utils.plugin

import play.api.Plugin

/**
  * Created by Dominik Zduńczyk on 17.04.19.


  * Plugin który inicjalizuje połączenie z bazą danych, jest potrzebny bo Swagger musi się uruchomić po odpaleniu bazy
  *
  * @param app aplikacja
  */
class ActivateStartPlugin(app: play.Application) extends Plugin {

  override def onStart = {
    model.PersistanceContext.context
  }

  override def onStop = {
  }
}