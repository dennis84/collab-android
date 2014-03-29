package collab.android

import org.scaloid.common._
import android.webkit.WebView

class EditorActivity extends SActivity {

  onCreate {
    contentView = new SVerticalLayout {
      val editor = SWebView()
      editor.getSettings.setJavaScriptEnabled(true)
      editor loadUrl "file:///android_asset/index.html"
    }
  }
}
