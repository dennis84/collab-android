package collab.android

import java.util.ArrayList
import android.text.Html
import android.view.Gravity
import android.graphics.Typeface
import android.support.v4.widget.DrawerLayout
import android.widget.ArrayAdapter
import android.widget._
import android.view.ViewGroup
import android.graphics.Color
import org.scaloid.common._
import spray.json._
import colors._

class EditorActivity extends SActivity {
  import MessageProtocol._

  onCreate {
    val drawer = new DrawerLayout(this)
    val members = new ArrayAdapter[String](
      this, R.layout.member, new ArrayList[String]())

    var list = new SListView
    val headline = new STextView
    headline.padding(16 dip).text("Who's Online")
    list.addHeaderView(headline)
    list.setAdapter(members)

    val nav = new SLinearLayout {
      this += list
    }.backgroundColor(Color.WHITE)

    val code = new STextView
    val lineNumbers = new STextView
    val font = Typeface.createFromAsset(getAssets,
      "FantasqueSansMono-Regular.ttf")

    val scroll = new SScrollView {
      this += new SHorizontalScrollView {
        this += new SRelativeLayout {
          lineNumbers.gravity(Gravity.RIGHT)
          lineNumbers.<<.fill
          lineNumbers.typeface(font)
          this += lineNumbers
          code.<<.wrap.rightOf(lineNumbers).>>
          code.typeface(font)
          this += code
        }
      }
    }

    val params = new DrawerLayout.LayoutParams(
      ViewGroup.LayoutParams.MATCH_PARENT,
      ViewGroup.LayoutParams.MATCH_PARENT,
      Gravity.START)

    nav.setLayoutParams(params)
    drawer.addView(scroll)
    drawer.addView(nav)
    contentView = drawer

    broadcastReceiver(Message.Join) { (context, intent) ⇒
      val member = intent.getStringExtra("data")
      members.add(member)
      members.notifyDataSetChanged
      android.util.Log.e("JOIN", member)
    }

    broadcastReceiver(Message.Code) { (context, intent) ⇒
      val message = intent.getStringExtra("data").asJson.convertTo[CodeMessage]
      val html = Colors(message.buffer, message.lang) {
        case WordCode(v)       ⇒ s"""<font color="${Theme.Violet}">$v</font>"""
        case TextCode(v)       ⇒ s"""<font color="${Theme.Green}">$v</font>"""
        case CommentCode(v)    ⇒ s"""<font color="${Theme.Base1}">$v</font>"""
        case BracketCode(v)    ⇒ s"""<font color="${Theme.Red}">$v</font>"""
        case WhitespaceCode(v) ⇒ v replace (" ", "&nbsp;")
        case c: Code           ⇒ c.value
      } replace ("\n", "<br>")

      val spannedCode = Html.fromHtml(html)

      val lineNumbersHtml = (1 to (message.buffer split "\n").length) map { n ⇒
        s"""<font color="${Theme.Base1}">$n&nbsp;</font>"""
      } mkString("<br>")

      val spannedLineNumbers = Html.fromHtml(lineNumbersHtml)

      code text spannedCode
      lineNumbers text spannedLineNumbers
    }
  }
}
