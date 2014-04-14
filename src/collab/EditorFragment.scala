package collab.android

import android.app.Fragment
import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup}
import android.widget.TextView
import android.graphics.Typeface
import android.text.Html
import spray.json._
import colors._

class EditorFragment extends Fragment {
  import MessageProtocol._

  var code: TextView = _
  var lineNumbers: TextView = _

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ) = inflater.inflate(R.layout.editor, container, false)

  override def onActivityCreated(savedInstanceState: Bundle) {
    super.onActivityCreated(savedInstanceState)

    code        = getActivity.findViewById(R.id.code).asInstanceOf[TextView]
    lineNumbers = getActivity.findViewById(R.id.lineNumbers).asInstanceOf[TextView]

    val font = Typeface.createFromAsset(getActivity.getAssets,
      "FantasqueSansMono-Regular.ttf")

    code setTypeface font
    lineNumbers setTypeface font

    if(null != savedInstanceState) {
      code setText savedInstanceState.getCharSequence("buffer")
      lineNumbers setText savedInstanceState.getCharSequence("lineNumbers")
    }
  }

  override def onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putCharSequence("buffer", code getText)
    outState.putCharSequence("lineNumbers", lineNumbers getText)
  }

  def update(message: String) {
    val m = message.asJson.convertTo[Code]
    val html = Colors(m.buffer, m.lang) {
      case WordCode(v)       ⇒ s"""<font color="${Theme.Violet}">$v</font>"""
      case TextCode(v)       ⇒ s"""<font color="${Theme.Green}">$v</font>"""
      case CommentCode(v)    ⇒ s"""<font color="${Theme.Base1}">$v</font>"""
      case BracketCode(v)    ⇒ s"""<font color="${Theme.Red}">$v</font>"""
      case WhitespaceCode(v) ⇒ v replace (" ", "&nbsp;")
      case c: Code           ⇒ c.value
    } replace ("\n", "<br>")

    val spannedCode = Html fromHtml html

    val lineNumbersHtml = (1 to (m.buffer split "\n").length) map { n ⇒
      s"""<font color="${Theme.Base1}">$n&nbsp;</font>"""
    } mkString("<br>")

    val spannedLineNumbers = Html fromHtml lineNumbersHtml

    code setText spannedCode
    lineNumbers setText spannedLineNumbers
  }
}
