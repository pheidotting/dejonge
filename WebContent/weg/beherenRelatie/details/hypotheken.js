require(["commons/3rdparty/log", 
         "pages/beherenRelatie/details/model/hypothekenEnPakketten"],
		function(log, hypothekenEnPakketten){
	
	log.debug("inlezen hypotheken");
	$.get( "../dejonge/rest/medewerker/hypotheek/lijstHypotheken", {relatieId : _relatieId}, function(hypotheken) {
		$.get( "../dejonge/rest/medewerker/hypotheek/lijstHypotheekPakketten", {relatieId : _relatieId}, function(pakketten) {
			log.debug("opgehaald");

			var h = new hypothekenEnPakketten(pakketten, hypotheken);
			
			ko.applyBindings(h);
		});
	});
});