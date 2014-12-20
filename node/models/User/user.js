var mongoose     = require('mongoose');
var Schema       = mongoose.Schema;

var UserSchema   = new Schema({
	name: String,
	email: String,
	rank: String,
	hashId : String,
	seed : String
});

module.exports = mongoose.model('User', UserSchema);