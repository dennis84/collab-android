package collab.android

import android.widget.ArrayAdapter
import android.content.Context
import android.view.{View, ViewGroup, LayoutInflater, WindowManager}
import android.widget.TextView
import android.widget.RelativeLayout
import java.util.ArrayList
import scala.collection.JavaConversions._

class CursorsAdapter(
  context: Context,
  cursors: ArrayList[Cursor]
) extends ArrayAdapter[Cursor](context, R.layout.cursor, cursors) {

  val dm = new android.util.DisplayMetrics
  context.getSystemService("window").asInstanceOf[WindowManager]
    .getDefaultDisplay().getMetrics(dm)

  override def getView(
    position: Int,
    convertView: View,
    parent: ViewGroup
  ) = {
    var view = convertView
    val cursor = getItem(position)

    if(null == convertView) {
      view = LayoutInflater.from(getContext).inflate(R.layout.cursor, null)
    }

    //val nickname = view.findViewById(R.id.nickname).asInstanceOf[TextView]
    //nickname.setText(cursor.sender)

    view.setX(11 * cursor.x * dm.scaledDensity)
    view.setY(22 * (cursor.y - 1) * dm.scaledDensity)
    view
  }

  def update(cursor: Cursor) {
    cursors.toSet.find(_.sender == cursor.sender) map { c ⇒
      c.x = cursor.x
      c.y = cursor.y
    } getOrElse {
      add(cursor)
    }
  }
}

object CursorsAdapter {
  def apply(context: Context): CursorsAdapter =
    new CursorsAdapter(context, new ArrayList[Cursor])
}
