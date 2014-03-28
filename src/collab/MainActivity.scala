package collab.android

import org.scaloid.common._

class MainActivity extends SActivity {

  onCreate {
    setContentView(R.layout.main)
    find[SListView](R.id.left_drawer).setAdapter(
      SArrayAdapter("Foo", "Bar", "Baz")
        .dropDownStyle(_.textSize(25 dip)))
  }
}
