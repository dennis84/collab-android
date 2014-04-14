package collab.android

import android.content.Intent
import org.scaloid.common._
import spray.json._

class MainActivity extends CollabActivity {
  import MessageProtocol._

  onCreate {
    setContentView(R.layout.main)
    sendBroadcast(new Intent(Connection.Members))
  }

  broadcastReceiver(Message.Members)((c, i) ⇒
    fragment[MembersFragment](R.id.members) reset (i.data[List[Member]]))

  broadcastReceiver(Message.Join)((c, i) ⇒
    fragment[MembersFragment](R.id.members) add (Member(i.data)))

  broadcastReceiver(Message.Code)((c, i) ⇒
    fragment[EditorFragment](R.id.editor) update (i.data))
}
