module.exports = function(app, router){

	var Track = require('./track');

	router.route('/tracks')

	.post(function(req, res) {
		var track = new Track();
		track.name = req.body.name;

		track.save(function(err) {
			if (err)
				res.send(err);
			res.respond({ message: 'Track created!' }, 200);
		});
		
	})
	
	.get(function(req, res) {
		Track.find(function(err, tracks) {
			if (err)
				res.send(err);

			res.respond(tracks, 200);
		});
	});
	
	router.route('/tracks/:trackId')
	
	.get(function(req, res) {
		var id = req.param("trackId");
		var query  = Track.where({ name: id });
		query.findOne(function(err, track) {
			if (err)
				res.send(err);

			res.respond(track, 200);
		});
	});
		
	router.route('/usertracks/:userId')
	
	.get(function(req, res) {
		var id = req.param("userId");
		var query  = Track.where({ user: id });
		query.findOne(function(err, track) {
			if (err)
				res.send(err);

			res.respond(track, 200);
		});
	});
	
	//le find est hardcodé car on a pas de jeu de données a points contants.
	//utiliser le map-reduce a la place
	router.route('/find')
	
	.get(function(req, res) {
		res.respond([
			{name:'Plouagat_Chatelaudren', cost:1645},
			{name:'Lanrodec_Bringolo', cost:582},
			{name:'Plouvara_Plouagat', cost:408}
		], 200);
	});
	

};