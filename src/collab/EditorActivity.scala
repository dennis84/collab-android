package collab.android

import org.scaloid.common._
import android.webkit.WebView

class EditorActivity extends SActivity {

  onCreate {
    val webView = new WebView(this)
    webView.getSettings.setJavaScriptEnabled(true)
    webView loadUrl "file:///android_asset/index.html"
    setContentView(webView)
  }
}
