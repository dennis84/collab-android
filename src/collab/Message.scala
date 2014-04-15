package collab.android

import spray.json._

object Message {
  def Join    = "collab.android.message.Join"
  def Code    = "collab.android.message.Code"
  def Members = "collab.android.message.Members"
  def Cursor  = "collab.android.message.Cursor"
}

case class Code(
  val buffer: String,
  val path: String,
  val lang: String)

case class Member(
  val id: String,
  val name: String)

object Member {
  def apply(id: String): Member = Member(id, id)
}

case class RawCursor(val x: Int, val y: Int)

case class Cursor(
  var x: Int,
  var y: Int,
  var sender: String)

object MessageProtocol extends DefaultJsonProtocol {

  implicit object CodeJsonFormat extends RootJsonFormat[Code] {
    def write(code: Code) = JsObject(
      "buffer" -> JsString(code.buffer),
      "path"   -> JsString(code.path),
      "lang"   -> JsString(code.lang))

    def read(value: JsValue) =
      value.asJsObject.getFields("buffer", "path", "lang") match {
        case Seq(JsString(buffer), JsString(path), JsString(lang)) ⇒
          Code(buffer, path, lang)
        case _ => throw new DeserializationException("Code expected")
      }
  }

  implicit object MemberJsonFormat extends RootJsonFormat[Member] {
    def write(member: Member) = JsObject(
      "id"   -> JsString(member.id),
      "name" -> JsString(member.name))

    def read(value: JsValue) =
      value.asJsObject.getFields("id", "name") match {
        case Seq(JsString(id), JsString(name)) ⇒ Member(id, name)
        case _ => throw new DeserializationException("Member expected")
      }
  }

  implicit object RawCursorJsonFormat extends RootJsonFormat[RawCursor] {
    def write(cursor: RawCursor) = JsObject(
      "x" -> JsNumber(cursor.x),
      "y" -> JsNumber(cursor.y))

    def read(value: JsValue) =
      value.asJsObject.getFields("x", "y") match {
        case Seq(JsNumber(x), JsNumber(y)) ⇒ RawCursor(x.toInt, y.toInt)
        case _ => throw new DeserializationException("RawCursor expected")
      }
  }
}
