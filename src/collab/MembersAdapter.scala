package collab.android

import android.widget.ArrayAdapter
import android.content.Context
import java.util.ArrayList

class MembersAdapter(
  context: Context,
  members: ArrayList[String]
) extends ArrayAdapter[String](context, R.layout.member, members) {

  def addMember(member: Member) {
    add(member.name)
  }

  def reset(ms: List[Member]) {
    members.clear
    ms foreach addMember
  }
}

object MembersAdapter {
  def apply(context: Context): MembersAdapter =
    new MembersAdapter(context, new ArrayList[String])
}
