package collab.android

import android.util.Log
import org.scaloid.common._
import android.webkit.WebView
import de.tavendo.autobahn._

class EditorActivity extends SActivity {

  lazy val ws = new WebSocketConnection()

  onCreate {
    val editor = new SWebView()

    contentView = new SVerticalLayout {
      this += editor
    }

    editor.getSettings.setJavaScriptEnabled(true)
    editor loadUrl "file:///android_asset/editor.html"

    val uri = "ws://polar-woodland-4270.herokuapp.com/foo"

    ws.connect(uri, new WebSocketConnectionHandler() {
      override def onTextMessage(data: String) {
        editor loadUrl "javascript:updateCode('" + data + "', 'scala')"
      }
    })
  }

  override def onDestroy() {
    super.onDestroy
    if(ws.isConnected) {
      ws.disconnect
    }
  }
}
