var mongoose     = require('mongoose');
var Schema       = mongoose.Schema;

var TrackSchema   = new Schema({
	id: String,
	name: String,
	user: String,	
	updated: { type: Date, default: Date.now },
	start: [],
	values: [],
	end: []
});

module.exports = mongoose.model('Track', TrackSchema);