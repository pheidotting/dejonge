function go(log, relatieId, actie, subId){
	$.getScript("pages/beherenRelatie/details/bedrijven.js", function() {
		if(subId != null && subId != "0"){
			log.debug("Ophalen Bedrijf met id : " + subId);
			log.debug("Relatie id " + relatieId);
			$.get( "../dejonge/rest/medewerker/bedrijf/lees", {"id" : subId}, function(data) {
				var bedrijf = new Bedrijf(data, log, relatieId);
				ko.applyBindings(bedrijf);
		    });
		}else{
			log.debug("aanmaken nieuw Bedrijf");
			ko.applyBindings(new Bedrijf('', log, relatieId));
		}
	});
}