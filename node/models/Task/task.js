var mongoose     = require('mongoose');
var Schema       = mongoose.Schema;

var TaskSchema   = new Schema({
	name: String,
	requestor: String,
	user: String,
	type: String,
	payload: String,
	startPosition: String,
	endPostion: String,
	done: Date,
	signature: Buffer,
	reward: String
});

module.exports = mongoose.model('Task', TaskSchema);