function go(log, relatieId, actie, subId){
	$.getScript("pages/beherenRelatie/details/polissen.js");

	$.get( "../dejonge/rest/medewerker/overig/lijstVerzekeringsMaatschappijen", {}, function(data) {
		var $select = $('#verzekeringsMaatschappijen');
		$.each(data, function(key, value) {
		    $('<option>').text(value).appendTo($select);
		});
	
		$.get( "../dejonge/rest/medewerker/bedrijf/lijst", {"relatieId" : relatieId}, function(data) {
			$.getScript("pages/beherenRelatie/details/bedrijven.js", function(dataX, textStatus, jqxhr) {
				var $select = $('#bedrijfBijPolis');
			    $('<option>', { value : '0' }).text('Kies (evt.) een Bedrijf uit de lijst').appendTo($select);
				$.each(data, function(key, value) {
				    $('<option>', { value : key }).text(value.naam).appendTo($select);
				});
			});
	
			if(subId != null){
				log.debug("Ophalen Polis met id : " + subId);
				$.get( "../dejonge/rest/medewerker/polis/lees", {"id" : subId}, function(data) {
					ko.applyBindings(new Polis(data, log));
			    });
			}
		});
	});
}