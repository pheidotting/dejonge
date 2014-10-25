require(["commons/3rdparty/log", 
         "pages/beherenRelatie/details/model/hypotheek"],
		function(logger, hypotheek){
	
	logger.debug("aanmaken nieuw hypotheek model");
	$.get( "../dejonge/rest/medewerker/hypotheek/alleSoortenHypotheek", {}, function(data) {
		var $select = $('#hypotheekVorm');
	    $('<option>', { value : '' }).text('Kies een soort hypotheek uit de lijst...').appendTo($select);
		$.each(data, function(key, value) {
		    $('<option>', { value : value.id }).text(value.omschrijving).appendTo($select);
		});
		$.get( "../dejonge/rest/medewerker/hypotheek/alleBanken", {}, function(data) {
			var $select = $('#bank');
			$('<option>', { value : '' }).text('Kies een bank uit de lijst...').appendTo($select);
			$.each(data, function(key, value) {
				$('<option>', { value : value.id }).text(value.naam).appendTo($select);
			});
			$.get( "../dejonge/rest/medewerker/hypotheek/lijstHypothekenInclDePakketten", {relatieId : _relatieId}, function(data) {
				if(data.length > 0){
					var $select = $('#koppelHypotheek');
					$('<option>', { value : '' }).text('Kies evt. een hypotheek om mee te koppelen...').appendTo($select);
					$.each(data, function(key, value) {
						$('<option>', { value : value.id }).text(value.leningNummer).appendTo($select);
					});
					$.get( "../dejonge/rest/medewerker/hypotheek/lees", {"id" : subId}, function(data) {
						logger.debug("Gegevens opgehaald voor hypotheek, applyBindings");
				       	ko.applyBindings(new hypotheek(data));
					});
				}else{
					$('#gekoppeldeHypotheekGroep').hide();
				}
			});
		});
	});
});