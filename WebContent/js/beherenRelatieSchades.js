define(['jquery',
        "knockout",
        'model/schades',
        'commons/block',
        'commons/3rdparty/log',
        'commons/commonFunctions'],
     function($, ko, Schades, block, log, commonFunctions) {

	return function(relatieId) {
		log.debug("Ophalen schades bij Relatie met id " + relatieId);
		block.block();
		$.ajax({
			type: "GET",
			url: "../dejonge/rest/medewerker/schade/lijst",
			async: false,
			dataType: "json",
			data: {
				relatieId : relatieId
			},
			context: this,
			success: function(data) {
				log.debug("opgehaald : " + JSON.stringify(data));
				ko.validation.registerExtenders();

				ko.applyBindings(new Schades(data));
			},
            error: function (data) {
            	commonFunctions.nietMeerIngelogd(data);
    		}
		});
	};
});	