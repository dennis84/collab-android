package collab.android

import java.util.ArrayList
import android.app.ListFragment
import android.os.Bundle
import android.widget.ArrayAdapter
import android.content._
import org.scaloid.common._

class MembersFragment extends ListFragment {

  var members = new ArrayList[String]()
  var adapter: ArrayAdapter[String] = _

  override def onActivityCreated(savedInstanceState: Bundle) {
    super.onActivityCreated(savedInstanceState)
    implicit val ctx = getActivity

    val headline = new STextView("Who's Online")
    headline.padding(16 dip)
    getListView.addHeaderView(headline)

    if(null != savedInstanceState) {
      members = savedInstanceState getStringArrayList "members"
    }

    adapter = new ArrayAdapter[String](getActivity, R.layout.member, members)
    setListAdapter(adapter)
  }

  override def onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putStringArrayList("members", members)
  }

  def update(member: String) {
    adapter.add(member)
    adapter.notifyDataSetChanged
  }
}
