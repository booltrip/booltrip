module.exports = function(app, router){

	var Task = require('./task');

	router.route('/tasks')

	.post(function(req, res) {
		var task = new Task();
		task.name = req.body.name;

		task.save(function(err) {
			if (err)
				res.send(err);
			res.respond({ message: 'Task created!' }, 200);
		});
		
	})
	
	.get(function(req, res) {
		Task.find(function(err, tasks) {
			if (err)
				res.send(err);

			res.respond(tasks, 200);
		});
	});

};