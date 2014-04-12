package collab.android

import org.scaloid.common._
import android.content._
import android.os.IBinder
import android.util.Log
import de.tavendo.autobahn._
import spray.json._

object Connection {

  def Join   = "collab.android.Join"
  def Joined = "collab.android.Joined"
  def Leave  = "collab.android.Leave"
  def Leaved = "collab.android.Leaved"
  def Code   = "collab.android.Code"
}

class ConnectionService extends SService {

  lazy val ws = new WebSocketConnection

  def onBind(intent: Intent): IBinder = null

  broadcastReceiver(Connection.Join) { (context, intent) ⇒
    connect(intent.getStringExtra("uri"))
  }

  broadcastReceiver(Connection.Leave) { (context, intent) ⇒
    ws.disconnect
  }

  def connect(uri: String) {
    ws.connect(uri, new WebSocketConnectionHandler {
      override def onOpen {
        gossip(Connection.Joined)
      }

      override def onClose(code: Int, reason: String) {
        Log.e("WS", reason)
        gossip(Connection.Leaved)
      }

      override def onTextMessage(data: String) {
        data.asJson.asJsObject.getFields("t", "s", "d") match {
          case Seq(JsString("code"), JsString(s), d) ⇒
            gossip(Message.Code, s, d.prettyPrint)
          case Seq(JsString("join"), JsString(s), JsString(m)) ⇒
            gossip(Message.Join, s, m) 
          case _ ⇒ Log.e("WS", "parse error: " + data)
        }
      }
    })
  }

  def gossip(action: String) = sendBroadcast(new Intent(action))

  def gossip(action: String, sender: String, data: String) =
    sendBroadcast(new Intent(action).putExtra("data", data))
}
