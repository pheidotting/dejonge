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
           'js/beherenRelatie'],
function   ($, Sammy, commonFunctions, inloggen, lijstRelaties, beherenRelatie) {
	commonFunctions.haalIngelogdeGebruiker();
	window.setInterval(commonFunctions.haalIngelogdeGebruiker, 300000);

	$('#uitloggen').click(function() {
		commonFunctions.uitloggen();
	});

	var diasRoute = new Sammy('body');

	diasRoute.route('GET', '#inloggen', function(context) {
		new inloggen();
	});

	diasRoute.route('GET', '#lijstRelaties', function(context) {
		new lijstRelaties();
	});

	diasRoute.route('GET', '#beherenRelatie/:id/:actie/:subId', function(context) {
		id = this.params['id'];
		actie = this.params['actie'];
		subId = this.params['subId'];
		new beherenRelatie(id, actie, subId);
	});

	diasRoute.route('GET', '#beherenRelatie/:id/:actie', function(context) {
		id = this.params['id'];
		actie = this.params['actie'];
		new beherenRelatie(id, actie);
	});

	diasRoute.route('GET', '#beherenRelatie/:id', function(context) {
		id = this.params['id'];
		new beherenRelatie(id);
	});

	diasRoute.route('GET', '#beherenRelatie', function(context) {
		new beherenRelatie(null);
	});

	diasRoute.route('GET', '#taken', function(context) {
		$.getScript("pages/taken/laden.js");
	});

	diasRoute.route('GET', '', function(context) {
		new lijstRelaties();
	});

	diasRoute.run();
});