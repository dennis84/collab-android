package collab.android

import org.scaloid.common._
import android.view.View
import android.content._

class StartActivity extends SActivity {

  onCreate {
    val room = new SEditText
    val joinButton = new SButton("Join", join(room.text.toString))
    val leaveButton = new SButton("Leave", leave)
    val joinLayout = new SVerticalLayout
    val leaveLayout = new SVerticalLayout

    contentView = new SVerticalLayout {
      joinLayout += room
      joinLayout += joinButton
      joinLayout padding 20.dip

      leaveLayout += leaveButton
      leaveLayout padding 20.dip
      leaveLayout visibility View.GONE

      this += joinLayout
      this += leaveLayout
    }

    broadcastReceiver(Connection.Joined) { (c, i) ⇒
      startActivity(SIntent[MainActivity])
      joinLayout visibility View.GONE
      leaveLayout visibility View.VISIBLE
    }

    broadcastReceiver(Connection.Leaved) { (c, i) ⇒
      joinLayout visibility View.VISIBLE
      leaveLayout visibility View.GONE
    }

    startService(SIntent[ConnectionService])
  }

  def join(id: String) =
    sendBroadcast(new Intent(Connection.Join).putExtra("uri",
      getString(R.string.endpoint) + "/" + id))

  def leave = sendBroadcast(new Intent(Connection.Leave))
}
