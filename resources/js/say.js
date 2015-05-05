function say(text) {
  var u = new SpeechSynthesisUtterance()
  u.text = text
  u.lang = 'it'
  u.onerror = function (e) {
    alert("Get a better browser, bro!")
  }
  speechSynthesis.speak(u)
}
