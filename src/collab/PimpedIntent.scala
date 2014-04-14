package collab.android

import android.content.Intent
import spray.json._

trait PimpedIntent {

  implicit class DataUnmarshalling(i: Intent) {
    def data[String]       = i.getStringExtra("data")
    def data[T:JsonReader] = i.getStringExtra("data").asJson.convertTo[T]
  }
}
