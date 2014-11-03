define(['jquery',
        "knockout", 
        'model/polissen',
        'commons/block',
        'commons/3rdparty/log',
        'commons/commonFunctions'],
     function($, ko, Polissen, block, log, commonFunctions) {

	return function(relatieId) {
		log.debug("Ophalen polissen bij Relatie met id " + relatieId);
		block.block();
		$.ajax({  
			type: "GET",  
			url: "../dejonge/rest/medewerker/polis/lijst",
			async: false,
			dataType: "json",  
			data: {
				relatieId : relatieId
			},
			context: this,
			success: function(data) {
				log.debug("opgehaald : " + JSON.stringify(data));
				ko.validation.registerExtenders();
				
				ko.applyBindings(new Polissen(data));
			},
            error: function (data) {
            	commonFunctions.nietMeerIngelogd(data);
    		}
		});
	};	
});	