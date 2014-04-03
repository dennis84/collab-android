package collab.android

import android.text.Html
import org.scaloid.common._
import colors._

class EditorActivity extends SActivity {

  onCreate {
    val code = """|object HelloWorld extends App {
                  |  println("Hello World!")
                  |}
                  |""".stripMargin

    val html = Colors(code, "scala") {
      case WordCode(v)       ⇒ s"""<font color="blue">$v</font>"""
      case TextCode(v)       ⇒ s"""<font color="green">$v</font>"""
      case WhitespaceCode(v) ⇒ v replace (" ", "&nbsp;")
      case c: Code           ⇒ c.value
    } replace ("\n", "<br>")

    val spannedCode = Html.fromHtml(
      """<font face="monospace">""" + html + "</font>")

    contentView = new SVerticalLayout {
      STextView(spannedCode)
    }
  }
}
