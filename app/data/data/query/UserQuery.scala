package data.data.query

import scala.concurrent.Future
import scala.language.postfixOps

import com.google.inject.Inject
import data.data.DefaultDBContext
import data.model.User
import slick.jdbc.GetResult

class UserQuery @Inject()(context: DefaultDBContext) {

  import context._
  import profile.api._

  def getAll: Future[Seq[User]] = {
    db.run {
      sql"SELECT id, name FROM users".as[User]
    }
  }

  def get(id: Int): Future[Option[User]] = {
    db.run {
      sql"SELECT id, name FROM users WHERE id = $id".as[User].headOption
    }
  }

  implicit val userResult: GetResult[User] = GetResult { r =>
    User(id = r <<, name = r <<)
  }
}
