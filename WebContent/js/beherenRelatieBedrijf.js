define(['jquery',
        "knockout",
        'model/bedrijf',
        'commons/block',
        'commons/3rdparty/log'],
    function($, ko, Bedrijf, block, log) {

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