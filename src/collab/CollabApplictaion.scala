package collab.android

import android.app.Application
import akka.actor._

class CollabApplication extends Application {

  var system: Option[ActorSystem] = None

  def unsafeSystem = system.get

  override def onCreate() {
    super.onCreate
    system = Some(ActorSystem("collab"))
  }

  override def onTerminate() {
    super.onTerminate
    system foreach (_.shutdown)
  }
}
