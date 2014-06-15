// Puns on puns on puns on the internet.
//

// ## Dependencies:
// - [express](https://www.npmjs.org/package/express) -- Sinatra inspired web
//    development framework
// - [morgan](https://www.npmjs.org/package/morgan) -- http request logger
//    middleware
// - [multiparty](https://www.npmjs.org/package/multiparty) --
//    multipart/form-data parser which supports streaming


var express = require('express'),
  app = express(),
  debug = require('debug')('the-pun-machine'),
  engines = require('consolidate'),
  morgan = require('morgan'),
  multiparty = require('multiparty');


app.use(morgan('short'))
app.use(express.static(__dirname + '/public'))
app.set('views', __dirname + '/views')
app.engine('.html', engines.handlebars)


app.get('/', function(req, res) {
  var data = {
    title: 'The Pun Machine'
  }
  res.render('index.html', data)
})

app.post('/', function(req, res) {
  var form = new multiparty.Form()

  form.parse(req, function(err, fields, files) {
    res.send(fields.q[0])
  })
})


// Listen on port 3000 and say, "Hayyy.."
console.log('Hayyy..')
app.listen(process.env.PORT || 3000)
