package collab.android

import android.text.Html
import org.scaloid.common._
import colors._

class EditorActivity extends SActivity {

  onCreate {
    val code = new STextView()
    val lineNumbers = new STextView()

    contentView = new SRelativeLayout {
      this += lineNumbers
      code.<<.rightOf(lineNumbers).>>.marginLeft(25 sp)
      this += code
    }

    broadcastReceiver(Connection.Message) { (context, intent) ⇒
      val input = intent.getStringExtra("code")
      val html = Colors(input, "scala") {
        case WordCode(v)       ⇒ s"""<font color="${Theme.Violet}">$v</font>"""
        case TextCode(v)       ⇒ s"""<font color="${Theme.Green}">$v</font>"""
        case CommentCode(v)    ⇒ s"""<font color="${Theme.Base1}">$v</font>"""
        case BracketCode(v)    ⇒ s"""<font color="${Theme.Red}">$v</font>"""
        case WhitespaceCode(v) ⇒ v replace (" ", "&nbsp;")
        case c: Code           ⇒ c.value
      } replace ("\n", "<br>")

      val spannedCode = Html.fromHtml(
        """<font face="monospace">""" + html + "</font>")

      val lineNumbersHtml = (1 to (input split "\n").length) map { n ⇒
        s"""<font color="${Theme.Base1}">$n</font>"""
      } mkString("<br>")

      val spannedLineNumbers = Html.fromHtml(
        """<font face="monospace">""" + lineNumbersHtml + "</font>")

      code text spannedCode
      lineNumbers text spannedLineNumbers
    }

    startService(SIntent[ConnectionService])
  }
}
