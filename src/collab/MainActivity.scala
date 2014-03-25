package collab
package android

import org.scaloid.common._
import akka.actor._
import com.typesafe.config.ConfigFactory
import java.io.{InputStreamReader, BufferedReader}

class MainActivity extends SActivity {

  onCreate {
    // val conf = new BufferedReader(
    //   new InputStreamReader((getResources.openRawResource(R.raw.akka))))

    // val system = ActorSystem("actor-system",
    //   ConfigFactory.load(ConfigFactory.parseReader(conf)))

    contentView = new SVerticalLayout {
      val room = SEditText()
      SButton("Connect", join(room.getText.toString))
    } padding 20.dip
  }

  def join(id: String) =
    startActivity(SIntent[EditorActivity].putExtra("room", id))
}
