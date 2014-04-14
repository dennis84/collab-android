package collab.android

import android.app.ListFragment
import android.os.Bundle
import org.scaloid.common._

class MembersFragment extends ListFragment {

  var adapter: MembersAdapter = _

  override def onActivityCreated(savedInstanceState: Bundle) {
    super.onActivityCreated(savedInstanceState)
    implicit val ctx = getActivity

    val headline = new STextView("Who's Online")
    headline padding 16.dip
    getListView addHeaderView headline

    adapter = MembersAdapter(getActivity)
    setListAdapter(adapter)
  }

  def add(member: Member) {
    adapter addMember member
    adapter.notifyDataSetChanged
  }

  def reset(members: List[Member]) {
    adapter reset members
    adapter.notifyDataSetChanged
  }
}
