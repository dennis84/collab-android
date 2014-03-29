package collab.android

import org.scaloid.common._

class MainActivity extends SActivity {

  onCreate {
    contentView = new SVerticalLayout {
      val room = SEditText()
      SButton("Connect", join(room.getText.toString))
    } padding 20.dip
  }

  def join(id: String) =
    startActivity(SIntent[EditorActivity].putExtra("room", id))
}
