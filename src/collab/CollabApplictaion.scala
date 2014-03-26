package collab.android

import android.app.Application
import akka.actor._
import com.typesafe.config.ConfigFactory
import java.io.{InputStreamReader, BufferedReader}

class CollabApplication extends Application {

  var system: ActorSystem = null

  override def onCreate() {
    super.onCreate()

    val conf = new BufferedReader(
      new InputStreamReader((getResources.openRawResource(R.raw.akka))))

    system = ActorSystem("collab",
      ConfigFactory.load(ConfigFactory.parseReader(conf)))
  }

  override def onTerminate() {
    super.onTerminate()
    system.shutdown()
  }
}
