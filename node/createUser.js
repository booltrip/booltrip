var mongoose     = require('mongoose');
var User = require('./models/User/user');
var tj = require('togeojson'),
    fs = require('fs'),
    // node doesn't have xml parsing or a dom. use jsdom
    jsdom = require('jsdom').jsdom;


mongoose.connect('mongodb://192.168.165.14/local');

var user = new User();
user.name = 'Kevin';
user.email = 'kevin.dupont@lamatrice.bzh'
user.rank = 'Sauveur de pingouins';

console.log('saving');

user.save(function(err) {
	if (err)
		console.log('error ' + err);
	console.log('done');
});
