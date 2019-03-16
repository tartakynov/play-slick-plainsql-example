package data.model

import play.api.libs.json.{Format, Json}

case class User(id: Int, name: String)

object User {
  implicit val format: Format[User] = Json.format[User]
}
