require(["commons/3rdparty/log", 
         "pages/beherenRelatie/details/model/hypotheek",
         "pages/beherenRelatie/details/model/hypotheken"],
		function(log, hypotheek, hypotheken){
	
	log.debug("inlezen hypotheken");
	$.get( "../dejonge/rest/medewerker/hypotheek/lijst", {}, function(data) {
		log.debug("opgehaald");
//		ko.validation.registerExtenders();
       	ko.applyBindings(new hypotheken(data));
	});
});