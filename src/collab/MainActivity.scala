package collab.android

import org.scaloid.common._

class MainActivity extends SActivity {

  onCreate {
    contentView = new SVerticalLayout {
      val room = SEditText()
      SButton("Connect", join(room.text.toString))
    } padding 20.dip
  }

  def join(id: String) =
    startService(SIntent[ConnectionService].putExtra("uri",
      getString(R.string.endpoint) + "/" + id))
}
