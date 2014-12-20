module.exports = function(app, router){

	var User = require('./user');

	router.route('/users')

	.post(function(req, res) {
		var user = new User();
		user.name = req.body.name;

		user.save(function(err) {
			if (err)
				res.send(err);
			res.respond({ message: 'User created!' }, 200);
		});
		
	})
	
	.get(function(req, res) {
		User.find(function(err, users) {
			if (err)
				res.send(err);

			res.respond(users, 200);
		});
	});

};