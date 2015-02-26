define(['jquery',
        "knockout",
        'model/aangifte',
        'model/aangiftes',
        'commons/block',
        'commons/3rdparty/log'],
    function($, ko, Aangifte, Aangiftes, block, log) {

	return function(relatieId) {
		block.block();
		log.debug("Ophalen openstaande aangiftes voor Relatie met id : " + relatieId);
		$.get( "../dejonge/rest/medewerker/aangifte/geslotenAangiftes", {"relatie" : relatieId}, function(data) {
			ko.applyBindings(new Aangiftes(data));
	    });
	};
});