var mongoose     = require('mongoose');
var Track = require('./models/Tracks/track');
var tj = require('togeojson'),
    fs = require('fs'),
    // node doesn't have xml parsing or a dom. use jsdom
    jsdom = require('jsdom').jsdom;


mongoose.connect('mongodb://192.168.165.14/local');
var name = 'Guingamp_plelo';
var user = 'Kevin';
var gpx = jsdom(fs.readFileSync('..\\trip_data\\kevin_Guingamp_plelo.gpx', 'utf8'));

var converted = tj.gpx(gpx);

//var converted_with_styles = tj.gpx(gpx, { styles: true });

/*var values = converted_with_styles.features[0].geometry.coordinates;
var start = converted_with_styles.features[1].geometry.coordinates;
var end = converted_with_styles.features[2].geometry.coordinates;*/

var values = [];

var temp = converted.features[0].geometry.coordinates;

for (var i = 0; i < temp.length; i++) {
	if(temp[i][1] != null)
		values.push([temp[i][1], temp[i][0]]);
}

var temp = converted.features[1].geometry.coordinates;
for (var i = 0; i < temp.length; i++) {
	if(temp[i][1] != null)
		values.push([temp[i][1], temp[i][0]]);
}

var temp = converted.features[2].geometry.coordinates;
for (var i = 0; i < temp.length; i++) {
	if(temp[i][1] != null)
		values.push([temp[i][1], temp[i][0]]);
}

console.log(values);

var track = new Track();
track.id = name;
track.name = name;
track.user = user;
//track.start = start;
track.values = values;
//track.end = end;

console.log('saving');

track.save(function(err) {
	if (err)
		console.log('error ' + err);
	console.log('done');
});
