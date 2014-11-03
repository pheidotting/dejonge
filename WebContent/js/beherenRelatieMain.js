define(['jquery',
        "knockout", 
        'model/relatie',
        'commons/block',
        'commons/3rdparty/log',
        'commons/commonFunctions'],
     function($, ko, Relatie, block, log, commonFunctions) {

	return function(relatieId) {
		block.block();
		log.debug("ophalen Relatie met id " + relatieId);
		$.ajax({  
			type: "GET",  
			url: "../dejonge/rest/medewerker/gebruiker/lees",
			async: false,
			dataType: "json",  
			data: {
				id : relatieId
			},
			context: this,
			success: function(data) {
				log.debug("opgehaald : " + JSON.stringify(data));
				ko.validation.registerExtenders();
				
				ko.applyBindings(new Relatie(data));
			},
            error: function (data) {
            	commonFunctions.nietMeerIngelogd(data);
    		}
		});
	};	
});	