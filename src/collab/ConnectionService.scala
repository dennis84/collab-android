package collab.android

import org.scaloid.common._
import android.content._
import android.os.IBinder
import android.util.Log
import de.tavendo.autobahn._
import spray.json._

object Connection {

  def Opened = "collab.android.Opened"
  def Code   = "collab.android.Code"
}

class ConnectionService extends SService {

  lazy val ws = new WebSocketConnection

  def onBind(intent: Intent): IBinder = null

  override def onStartCommand(intent: Intent, flags: Int, startId: Int) = {
    connect(intent.getStringExtra("uri"))
    android.app.Service.START_STICKY
  }

  def connect(uri: String) {
    ws.connect(uri, new WebSocketConnectionHandler {
      override def onOpen {
        startActivity(SIntent[EditorActivity]
          .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
      }

      override def onClose(code: Int, reason: String) {
        Log.e("WS", reason)
      }

      override def onTextMessage(data: String) {
        data.asJson.asJsObject.getFields("t", "s", "d") match {
          case Seq(JsString("code"), JsString(s), d) ⇒
            gossip(Connection.Code, s, d)
          case _ ⇒ Log.e("WS", "parse error: " + data)
        }
      }
    })
  }

  def gossip(action: String, sender: String, data: JsValue) =
    sendBroadcast(new Intent(action).putExtra("data", data.prettyPrint))
}
