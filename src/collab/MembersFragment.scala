package collab.android

import java.util.ArrayList
import android.app.ListFragment
import android.os.Bundle
import android.widget.ArrayAdapter
import android.content._
import org.scaloid.common._

class MembersFragment extends ListFragment {

  var adapter: ArrayAdapter[String] = _

  override def onActivityCreated(savedInstanceState: Bundle) {
    super.onActivityCreated(savedInstanceState)
    implicit val ctx = getActivity

    val members = new ArrayList[String]()
    val headline = new STextView("Who's Online")
    headline.padding(16 dip)
    getListView.addHeaderView(headline)

    adapter = new ArrayAdapter[String](getActivity, R.layout.member, members)
    setListAdapter(adapter)
  }

  def update(member: String) {
    adapter.add(member)
    adapter.notifyDataSetChanged
  }
}
