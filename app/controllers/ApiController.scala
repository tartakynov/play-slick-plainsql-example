package controllers

import scala.concurrent.ExecutionContext

import com.google.inject.Inject
import data.query.{UserCreateData, UserQuery}
import model.User
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{Format, Json}
import play.api.mvc._

class ApiController @Inject()(cc: ControllerComponents, query: UserQuery)(
  implicit ec: ExecutionContext
) extends AbstractController(cc) {

  private implicit val userFormat: Format[User] = Json.format[User]

  private val form: Form[UserCreateData] = Form(
    mapping("name" -> nonEmptyText)(UserCreateData.apply)(
      UserCreateData.unapply
    )
  )

  def getUsers: Action[AnyContent] = Action.async {
    query.getAll.map(users => Ok(Json.toJson(users)))
  }

  def getUser(id: Int): Action[AnyContent] = Action.async {
    query.get(id).map {
      case Some(user) => Ok(Json.toJson(user))
      case None => NotFound("")
    }
  }

  def postUser(): Action[UserCreateData] =
    Action.async(parse.form[UserCreateData](form)) { r =>
      query.insert(r.body).map(id => Ok(Json.toJson(id)))
    }

}
