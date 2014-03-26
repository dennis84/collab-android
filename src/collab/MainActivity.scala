package collab.android

import org.scaloid.common._
import android.content.Context
import akka.actor._

class MainActivity extends SActivity {

  onCreate {
    contentView = new SVerticalLayout {
      val room = SEditText()
      SButton("Connect", join(room.getText.toString))
    } padding 20.dip

    val system = getApplication.asInstanceOf[CollabApplication].system
    val actor = system.actorOf(Props(new TestActor(getApplicationContext)))

    actor ! "Hello World"
  }

  def join(id: String) =
    startActivity(SIntent[EditorActivity].putExtra("room", id))
}

class TestActor(c: Context) extends Actor {
  implicit val ctx = c

  def receive = {
    case x: String ⇒ toast(x)
  }
}
