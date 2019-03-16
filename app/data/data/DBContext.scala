package data.data

import com.google.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait DBContext {
  val dbConfigProvider: DatabaseConfigProvider

  val driver = dbConfigProvider.get[JdbcProfile]

  val db = driver.db

  val profile = driver.profile
}

class DefaultDBContext @Inject()(val dbConfigProvider: DatabaseConfigProvider)
    extends DBContext
