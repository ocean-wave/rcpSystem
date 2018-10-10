(function(env) {
	const dev  =   {
		host: "ws://10.10.1.226:8080"
	};
	switch (env) {
		case 'dev':
			return dev;
			break;
	}

}("devs"))