// Puns on puns on puns on the internet.
//

// ## Dependencies:
// - [request](https://www.npmjs.org/package/request) -- simplify HTTP requests
// - [express](https://www.npmjs.org/package/express) -- Sinatra inspired web
//    development framework
// - [morgan](https://www.npmjs.org/package/morgan) -- http request logger
//    middleware
// - [multiparty](https://www.npmjs.org/package/multiparty) --
//    multipart/form-data parser which supports streaming


var express = require('express'),
  app = express(),
  debug = require('debug')('the-pun-machine'),
  morgan = require('morgan'),
  multiparty = require('multiparty'),
  router = express.Router(),
  util = require('util')

  app.use(express.static(__dirname + '/public'))
  app.use(morgan('short'))


  app.use('/', router.get('/', function(req, res) {
    res.render('index.html')
  }))

  app.post('/', function(req, res) {
    var form = new multiparty.Form()

    form.parse(req, function(err, fields, files) {
      res.send(fields.q[0])
    })
  })

  // Listen on port 3000 and say, "Hayyy.."
  console.log('Hayyy..')
  app.listen(process.env.PORT || 3000)
