define(['jquery',
        "knockout",
        'model/relatie',
        'commons/block',
        'commons/3rdparty/log',
        'commons/commonFunctions',
        'jqueryUI'],
     function($, ko, Relatie, block, log, commonFunctions, jqueryUI) {

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

				var relatie = new Relatie(data);
				
				ko.applyBindings(relatie);
				
				if(relatie.opmerkingen().length > 0){
					$("#opmerkingenDialog").dialog();
				}
				$("#persoonsGegevensDialog").dialog();
			},
            error: function (data) {
            	commonFunctions.nietMeerIngelogd(data);
    		}
		});
		
	};
});