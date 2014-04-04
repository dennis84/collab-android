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
      case WordCode(v)       ⇒ s"""<font color="${Theme.Violet}">$v</font>"""
      case TextCode(v)       ⇒ s"""<font color="${Theme.Green}">$v</font>"""
      case CommentCode(v)    ⇒ s"""<font color="${Theme.Base1}">$v</font>"""
      case BracketCode(v)    ⇒ s"""<font color="${Theme.Red}">$v</font>"""
      case WhitespaceCode(v) ⇒ v replace (" ", "&nbsp;")
      case c: Code           ⇒ c.value
    } replace ("\n", "<br>")

    val spannedCode = Html.fromHtml(
      """<font face="monospace">""" + html + "</font>")

    val lineNumbersHtml = (1 to (code split "\n").length) map { n ⇒
      s"""<font color="${Theme.Base1}">$n</font>"""
    } mkString("<br>")

    val spannedLineNumbers = Html.fromHtml(
      """<font face="monospace">""" + lineNumbersHtml + "</font>")

    contentView = new SRelativeLayout {
      val ln = STextView(spannedLineNumbers)
      STextView(spannedCode).<<.rightOf(ln).>>.marginLeft(25 sp)
    }
  }
}
