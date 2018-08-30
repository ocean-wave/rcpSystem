(function(env) {
	const dev  =   {
		host: "ws://10.1.3.123:8086"
	};
	switch (env) {
		case 'dev':
			return dev;
			break;
	}

}("devs"))