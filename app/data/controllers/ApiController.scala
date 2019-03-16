package data.controllers

import scala.concurrent.ExecutionContext

import com.google.inject.Inject
import data.data.query.UserQuery
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

class ApiController @Inject()(cc: ControllerComponents, query: UserQuery)(
  implicit ec: ExecutionContext
) extends AbstractController(cc) {

  def getUsers: Action[AnyContent] = Action.async {
    query.getAll.map(users => Ok(Json.toJson(users)))
  }

  def getUser(id: Int): Action[AnyContent] = Action.async {
    query.get(id).map {
      case Some(user) => Ok(Json.toJson(user))
      case None => NotFound
    }
  }

}
