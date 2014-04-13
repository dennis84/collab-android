package collab.android

import org.scaloid.common._

class MainActivity extends SActivity {

  onCreate {
    setContentView(R.layout.main)
  }

  broadcastReceiver(Message.Join) { (context, intent) ⇒
    fragment[MembersFragment](R.id.members).update(
      intent.getStringExtra("data"))
  }

  broadcastReceiver(Message.Code) { (context, intent) ⇒
    fragment[EditorFragment](R.id.editor).update(
      intent.getStringExtra("data"))
  }

  def fragment[T](res: Int) =
    getFragmentManager.findFragmentById(res).asInstanceOf[T]
}
