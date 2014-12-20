// BASE SETUP
// =============================================================================

// call the packages we need
var express    	= require('express'); 		// call express
var app        	= express(); 				// define our app using express
var bodyParser 	= require('body-parser');
var fs 		   	= require('fs');
var http 	   	= require('http');
var morgan     	= require('morgan');
var mongoose    = require('mongoose');

mongoose.connect('mongodb://192.168.165.14/local');

// configure app to use bodyParser()
// this will let us get the data from a POST
//app.use(morgan('dev')); //Logger
var HTTPLog 	= morgan({format: "tiny"});

app.use(HTTPLog);
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(function(req, res, next) {
		// CORS
		var oneof = false;
		if(req.headers.origin) {
			res.header('Access-Control-Allow-Origin', req.headers.origin);
			res.header('Access-Control-Allow-Credentials', "true");
			oneof = true;
		}
		if(req.headers['access-control-request-method']) {
			res.header('Access-Control-Allow-Methods', req.headers['access-control-request-method']);
			oneof = true;
		}
		if(req.headers['access-control-request-headers']) {
			res.header('Access-Control-Allow-Headers', req.headers['access-control-request-headers']);
			oneof = true;
		}
		if(oneof) {
			res.header('Access-Control-Max-Age', 60 * 60 * 24 * 365);
		}

		// intercept OPTIONS method
		if (oneof && req.method == 'OPTIONS') {
			//res.send(200);
			next();
		}
		else {
			next();
		}
	});

var port = process.env.PORT || 8090;


// Response monkey patch
http.ServerResponse.prototype.respond = function (content, status) {
  if ('undefined' == typeof status) { // only one parameter found
    if ('number' == typeof content || !isNaN(parseInt(content))) { // usage "respond(status)"
      status = parseInt(content);
      content = undefined;
    } else { // usage "respond(content)"
      status = 200;
    }
  }
  if (status != 200) { // error
    content = {
      "code":    status,
      "status":  http.STATUS_CODES[status],
      "message": content && content.toString() || null
    };
    if ('object' != typeof content) { // wrap content if necessary
	    content = {"result":content};
	  }
	  // respond with JSON data
	  this.send(JSON.stringify(content)+"\n", status);
  
  } else {
  	this.send(content);
  }
  
};

// ROUTES FOR OUR API
// =============================================================================
var router = express.Router(); 				// get an instance of the express Router

// test route to make sure everything is working (accessed at GET http://localhost:8080/api)
router.get('/', function(req, res) {
	res.respond({ message: 'hooray! welcome to our api!' }, 200);	
});

// 192.168.165.14

//api Extension
fs.readdir('./models', function (err, list) {
	if (err) {
		console.log(err);
		return;
	}
	list.forEach(function (file) {
		if (!fs.lstatSync('./models/' + file).isDirectory())
			return;
		require('./models/' + file +'/api-extension')(app, router);
	});
});

// REGISTER OUR ROUTES -------------------------------
// all of our routes will be prefixed with /api
app.use('/api', router);

// START THE SERVER
// =============================================================================
app.listen(port);
console.log('Server Started ' + port);