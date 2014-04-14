package collab.android

import org.scaloid.common._

class CollabActivity extends SActivity {

  def fragment[T](res: Int) =
    (getFragmentManager findFragmentById res).asInstanceOf[T]
}
