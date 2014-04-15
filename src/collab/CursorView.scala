package collab.android

import android.content.Context
import android.view.View
import android.graphics.Paint
import android.graphics.Color
import android.graphics.Canvas
import android.util.AttributeSet
import android.graphics.Typeface

class CursorView(
  context: Context,
  attrs: AttributeSet
) extends View(context, attrs) {

  var paint = new Paint
  val font = Typeface.createFromAsset(context.getAssets,
    "FantasqueSansMono-Regular.ttf")

  override def onDraw(canvas: Canvas) {
    paint.setStyle(Paint.Style.FILL)
    paint.setColor(Color.BLACK)
    paint.setAlpha(50)
    canvas.drawRect(0, 0, getWidth, getHeight, paint)
  }
}
