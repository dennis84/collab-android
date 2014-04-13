package collab.android

import java.util.ArrayList
import android.os.Bundle
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
  var code: TextView = _
  var lineNumbers: TextView = _
  var members: ListView = _

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.editor)

    code        = find[TextView](R.id.code)
    lineNumbers = find[TextView](R.id.lineNumbers)

    val font = Typeface.createFromAsset(getAssets,
      "FantasqueSansMono-Regular.ttf")

    code.typeface(font)
    lineNumbers.typeface(font)

    if(null != savedInstanceState) {
      code text savedInstanceState.getCharSequence("buffer")
      lineNumbers text savedInstanceState.getCharSequence("line_numbers")
    }
  }

  override def onSaveInstanceState(savedInstanceState: Bundle) {
    super.onSaveInstanceState(savedInstanceState)
    savedInstanceState.putCharSequence("buffer", code text)
    savedInstanceState.putCharSequence("line_numbers", lineNumbers text)
  }

  broadcastReceiver(Message.Join) { (context, intent) ⇒
    val f = getFragmentManager.findFragmentById(R.id.members)
      .asInstanceOf[MembersFragment]
    f.update(intent.getStringExtra("data"))
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
