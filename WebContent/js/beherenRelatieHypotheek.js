define(['jquery',
        'knockout',
        "commons/3rdparty/log",
         "js/model/hypotheek"],
	function($, ko, logger, hypotheek){

	return function(polisId, relatieId) {
		logger.debug("aanmaken nieuw hypotheek model");
		$.get( "../dejonge/rest/medewerker/hypotheek/alleSoortenHypotheek", {}, function(data) {
			var $select = $('#hypotheekVorm');
		    $('<option>', { value : '' }).text('Kies een soort hypotheek uit de lijst...').appendTo($select);
			$.each(data, function(key, value) {
			    $('<option>', { value : value.id }).text(value.omschrijving).appendTo($select);
			});
			$.get( "../dejonge/rest/medewerker/hypotheek/lijstHypothekenInclDePakketten", {relatieId : relatieId}, function(data) {
				if(data.length > 0){
					var $select = $('#koppelHypotheek');
					$('<option>', { value : '' }).text('Kies evt. een hypotheek om mee te koppelen...').appendTo($select);
					$.each(data, function(key, value) {
						var h = new hypotheek(value);
						if(h.id() != subId){
							$('<option>', { value : value.id }).text(h.titel()).appendTo($select);
						}
					});
				}else{
					$('#gekoppeldeHypotheekGroep').hide();
				}
				$.get( "../dejonge/rest/medewerker/hypotheek/lees", {"id" : subId}, function(data) {
					logger.debug("Gegevens opgehaald voor hypotheek, applyBindings");
					logger.debug(data);
					ko.applyBindings(new hypotheek(data));
				});
			});
		});
	};
});