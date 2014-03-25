package collab
package android

import org.scaloid.common._

class EditorActivity extends SActivity {

  onCreate {
    toast("Joined " + getIntent.getStringExtra("room"))
  }
}
