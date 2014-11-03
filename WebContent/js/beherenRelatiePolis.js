define(['jquery',
        "knockout", 
        'model/polis',
        'commons/block',
        'commons/3rdparty/log',
        'commons/commonFunctions'],
     function($, ko, Polis, block, log, commonFunctions) {

	return function(polisId, relatieId) {
		block.block();
		log.debug("Ophalen lijst met verzekeringsmaatschappijen");
		$.get( "../dejonge/rest/medewerker/overig/lijstVerzekeringsMaatschappijen", {}, function(data) {
			var $select = $('#verzekeringsMaatschappijen');
			$.each(data, function(key, value) {
			    $('<option>', { value : value }).text(value).appendTo($select);
			});
		});
		
		$.get( "../dejonge/rest/medewerker/bedrijf/lijst", {"relatieId" : relatieId}, function(data) {
			if(data.length > 0){
				$.getScript("pages/beherenRelatie/details/bedrijven.js", function(dataX, textStatus, jqxhr) {
					var $select = $('#bedrijfBijPolis');
				    $('<option>', { value : '0' }).text('Kies (evt.) een Bedrijf uit de lijst').appendTo($select);
					$.each(data, function(key, value) {
					    $('<option>', { value : key }).text(value.naam).appendTo($select);
					});
				});
			}else{
				$('#bedrijfBijPolis').hide();
			}

			if(subId != null && subId != "0"){
				$('#soortVerzekering').prop('disabled', true);
				$('#soortVerzekeringAlles').prop('disabled', true);
				log.debug("Ophalen Polis met id : " + polisId);
				$.get( "../dejonge/rest/medewerker/polis/lees", {"id" : polisId}, function(data) {
					log.debug(JSON.stringify(data));
					var polis = new Polis(data);
					polis.relatie(relatieId);
					polis.bijlages.removeAll();
					ko.applyBindings(polis);
			    });
			}else{
				var polis = new Polis('');
				polis.relatie(relatieId);
				ko.applyBindings(polis);
			}
		});
	};
});	