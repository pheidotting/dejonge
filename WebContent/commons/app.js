requirejs.config({
    paths: {
        commons: '../commons',
        jquery: '../commons/3rdparty/jquery-1.9.1',
    	sammy: '../commons/3rdparty/sammy-0.7.6',
    	moment: '../commons/3rdparty/moment',
    	js: '../js',
    	pages: '../pages',
    	model: '../js/model',
    	knockout: '../commons/3rdparty/knockout',
        knockoutValidation: '../commons/3rdparty/knockoutValidation/knockout.validation',
        'blockUI': 'http://malsup.github.com/jquery.blockUI'
    },
	shim: {
        "knockoutValidation": ["knockout"],
        'blockUI': ['jquery']
    },
    config: {
        moment: {
            noGlobal: true
        }
    }
    });

requirejs(['jquery',
           'sammy',
           'commons/commonFunctions',
           'js/inloggen',
           'js/lijstRelaties',
           'js/beherenRelatie',
           'js/taak/taken',
           'js/taak/afhandelenTaak',
           'js/dashboard'],
function   ($, Sammy, commonFunctions, inloggen, lijstRelaties, beherenRelatie, taken, afhandelenTaak, dashboard) {
	commonFunctions.haalIngelogdeGebruiker();
	window.setInterval(commonFunctions.haalIngelogdeGebruiker, 300000);

	$('#uitloggen').click(function() {
		commonFunctions.uitloggen();
	});

	var app = new Sammy('body');

	app.route('GET', '#taken', function(context) {
		new taken();
	});
	
	app.route('GET', '#dashboard', function(context) {
		new dashboard();
	});
	
	app.route('GET', '#taak/:id', function(context) {
		id = this.params['id'];
		new afhandelenTaak(id);
	});

	app.route('GET', '#inloggen', function(context) {
		new inloggen();
	});

	app.route('GET', '#lijstRelaties', function(context) {
		new lijstRelaties();
	});

	app.route('GET', '#lijstRelaties/:zoekTerm', function(context) {
		zoekTerm = this.params['zoekTerm'];
		new lijstRelaties(zoekTerm);
	});
	
	app.route('GET', '#beherenRelatie/:id/:actie/:subId', function(context) {
		id = this.params['id'];
		actie = this.params['actie'];
		subId = this.params['subId'];
		new beherenRelatie(id, actie, subId);
	});

	app.route('GET', '#beherenRelatie/:id/:actie', function(context) {
		id = this.params['id'];
		actie = this.params['actie'];
		new beherenRelatie(id, actie);
	});

	app.route('GET', '#beherenRelatie/:id', function(context) {
		id = this.params['id'];
		new beherenRelatie(id);
	});

	app.route('GET', '#beherenRelatie', function(context) {
		new beherenRelatie(null);
	});

	app.route('GET', '', function(context) {
		new lijstRelaties();
	});

	app.raise_errors = true;
	app.run();
});