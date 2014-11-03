define(['jquery',
        'knockout',
        'commons/3rdparty/log',
        'js/model/hypothekenEnPakketten'],
		function($, ko, log, hypothekenEnPakketten){
	
	return function(relatieId) {
		log.debug("inlezen hypotheken");
		$.get( "../dejonge/rest/medewerker/hypotheek/lijstHypotheken", {relatieId : relatieId}, function(hypotheken) {
			$.get( "../dejonge/rest/medewerker/hypotheek/lijstHypotheekPakketten", {relatieId : relatieId}, function(pakketten) {
				log.debug("opgehaald");
	
				var h = new hypothekenEnPakketten(pakketten, hypotheken);
				
				ko.applyBindings(h);
			});
		});
	};
});