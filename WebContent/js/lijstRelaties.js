define(['jquery',
        "knockout", 
        'model/lijstRelaties',
        'model/relatie',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log'],
     function($, ko, LijstRelaties, Relatie, functions, block, log) {

	return function(){
		$('#content').load('templates/lijstRelaties.html', function(response, status, xhr) {
			if (status == "success") {
				log.debug("ophalen lijst met Relaties");
				block.block();
				$.when(functions.laadDataMetLoginCheck('../dejonge/rest/medewerker/gebruiker/lijstRelaties')).done(function(data) {
					log.debug("opgehaald " + JSON.stringify(data));
					if(data != undefined){
						var lijst = new LijstRelaties();
						$.each(data.jsonRelaties, function(i, item) {
							var a = item;
							lijst().lijst().push(a);
						});
						
						log.debug("Relaties opgehaald, applyBindings");
						ko.applyBindings(lijst);
					}
				});
			}
		});
	};
});