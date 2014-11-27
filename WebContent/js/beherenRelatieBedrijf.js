define(['jquery',
        "knockout",
        'model/bedrijf',
        'commons/block',
        'commons/3rdparty/log',
        'commons/commonFunctions'],
    function($, ko, Bedrijf, block, log, commonFunctions) {

	return function(bedrijfId, relatieId) {
		block.block();
		log.debug("Ophalen Bedrijf met id : " + bedrijfId);
		$.get( "../dejonge/rest/medewerker/bedrijf/lees", {"id" : subId}, function(data) {
			var bedrijf = new Bedrijf(data);
			bedrijf.relatie(relatieId);
			ko.applyBindings(bedrijf);
	    });
	};
});