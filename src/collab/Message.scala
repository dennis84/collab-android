package collab.android

import spray.json._

case class CodeMessage(
  val buffer: String,
  val path: String,
  val lang: String) extends java.io.Serializable

object MessageProtocol extends DefaultJsonProtocol {

  implicit object CodeJsonReader extends RootJsonReader[CodeMessage] {
    def read(value: JsValue) =
      value.asJsObject.getFields("buffer", "path", "lang") match {
        case Seq(JsString(buffer), JsString(path), JsString(lang)) ⇒
          CodeMessage(buffer, path, lang)
        case _ => throw new DeserializationException("CodeMessage expected")
      }
  }
}
