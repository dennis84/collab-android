var hljs = require('highlight.js')

window.updateCode = function(code, lang) {
  var content = hljs.highlight(lang, code).value
    , code    = '<code class="hljs ' + lang + '">' + content + '</code>'

  document.getElementById("code").innerHTML = code
}
