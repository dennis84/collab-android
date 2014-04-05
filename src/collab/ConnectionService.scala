package collab.android

import org.scaloid.common._
import android.content._
import android.os.IBinder

object Connection {

  def Opened = "collab.android.Opened"
  def Message = "collab.android.Message"
}

class ConnectionService extends SService {

  def onBind(intent: Intent): IBinder = null

  onCreate {
    val code = """|object HelloWorld extends App {
                  |  println("Hello World!")
                  |}
                  |""".stripMargin

    sendBroadcast(new Intent(Connection.Message).putExtra("code", code))
  }
}
