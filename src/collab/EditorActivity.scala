package collab.android

import android.text.Html
import android.view.Gravity
import org.scaloid.common._
import spray.json._
import colors._

class EditorActivity extends SActivity {
  import MessageProtocol._

  onCreate {
    val code = new STextView
    val lineNumbers = new STextView

    contentView = new SRelativeLayout {
      lineNumbers.gravity(Gravity.RIGHT)
      this += lineNumbers
      code.horizontallyScrolling(true)
      code.<<.rightOf(lineNumbers).>>.marginLeft(25 sp)
      this += code
    }

    broadcastReceiver(Connection.Code) { (context, intent) ⇒
      val message = intent.getStringExtra("data").asJson.convertTo[CodeMessage]

      val html = Colors(message.buffer, message.lang) {
        case WordCode(v)       ⇒ s"""<font color="${Theme.Violet}">$v</font>"""
        case TextCode(v)       ⇒ s"""<font color="${Theme.Green}">$v</font>"""
        case CommentCode(v)    ⇒ s"""<font color="${Theme.Base1}">$v</font>"""
        case BracketCode(v)    ⇒ s"""<font color="${Theme.Red}">$v</font>"""
        case WhitespaceCode(v) ⇒ v replace (" ", "&nbsp;")
        case c: Code           ⇒ c.value
      } replace ("\n", "<br>")

      val spannedCode = Html.fromHtml(
        """<font face="monospace">""" + html + "</font>")

      val lineNumbersHtml = (1 to (message.buffer split "\n").length) map { n ⇒
        s"""<font color="${Theme.Base1}">$n</font>"""
      } mkString("<br>")

      val spannedLineNumbers = Html.fromHtml(
        """<font face="monospace">""" + lineNumbersHtml + "</font>")

      code text spannedCode
      lineNumbers text spannedLineNumbers
    }
  }
}
