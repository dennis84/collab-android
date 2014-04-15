package collab.android

import android.app.ListFragment
import android.os.Bundle
import org.scaloid.common._

class CursorsFragment extends ListFragment {

  var adapter: CursorsAdapter = _

  override def onActivityCreated(savedInstanceState: Bundle) {
    super.onActivityCreated(savedInstanceState)
    implicit val ctx = getActivity

    adapter = CursorsAdapter(getActivity)
    setListAdapter(adapter)
  }

  def update(cursor: Cursor) {
    adapter update cursor
    adapter.notifyDataSetChanged
  }
}
