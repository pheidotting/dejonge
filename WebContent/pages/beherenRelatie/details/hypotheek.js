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
		$.get( "../dejonge/rest/medewerker/hypotheek/lees", {"id" : subId}, function(data) {
			logger.debug("Gegevens opgehaald voor hypotheek, applyBindings");
	//		ko.validation.registerExtenders();
	       	ko.applyBindings(new hypotheek(data));
	    });
	});
});