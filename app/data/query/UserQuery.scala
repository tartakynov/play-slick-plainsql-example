package data.query

import scala.concurrent.Future
import scala.language.postfixOps

import com.google.inject.Inject
import data.DefaultDBContext
import model.User
import slick.jdbc.GetResult

case class UserCreateData(name: String)

class UserQuery @Inject()(context: DefaultDBContext) {

  import context._
  import profile.api._

  def getAll: Future[Seq[User]] = {
    db.run {
      sql"""
        |SELECT 
        |   "id",
        |   "name"
        |FROM 
        |   "users"
         """.stripMargin.as[User]
    }
  }

  def get(id: Int): Future[Option[User]] = {
    db.run {
      sql"""
        |SELECT
        |   "id",
        |   "name"
        |FROM
        |   "users"
        |WHERE
        |   "id" = $id
        """.stripMargin.as[User].headOption
    }
  }

  def insert(user: UserCreateData): Future[Int] = {
    val action = for {
      _ <- sql"""INSERT INTO "users" ("name") VALUES (${user.name})""".as[Int]
      id <- sql"CALL SCOPE_IDENTITY()".as[Int].head
    } yield id
    db.run(action.transactionally)
  }

  implicit val userResult: GetResult[User] = GetResult { r =>
    User(id = r <<, name = r <<)
  }
}
