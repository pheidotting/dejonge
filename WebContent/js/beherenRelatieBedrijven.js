define(['jquery',
        "knockout",
        'model/bedrijven',
        'commons/block',
        'commons/3rdparty/log',
        'commons/commonFunctions'],
     function($, ko, Bedrijven, block, log, commonFunctions) {

	return function(relatieId) {
		log.debug("Ophalen bedrijven bij Relatie met id " + relatieId);
		block.block();
		$.ajax({
			type: "GET",
			url: "../dejonge/rest/medewerker/bedrijf/lijst",
			async: false,
			dataType: "json",
			data: {
				relatieId : relatieId
			},
			context: this,
			success: function(data) {
				log.debug("opgehaald : " + JSON.stringify(data));
				ko.validation.registerExtenders();

				ko.applyBindings(new Bedrijven(data));
			},
            error: function (data) {
            	commonFunctions.nietMeerIngelogd(data);
    		}
		});
	};
});