function go(log, relatieId, actie, subId){
	$.getScript("pages/beherenRelatie/details/polissen.js");

	$.get( "../dejonge/rest/medewerker/overig/lijstVerzekeringsMaatschappijen", {}, function(data) {
		var $select = $('#verzekeringsMaatschappijen');
		$.each(data, function(key, value) {
		    $('<option>').text(value).appendTo($select);
		});
    });
	
	$.get( "../dejonge/rest/medewerker/bedrijf/lijst", {"relatieId" : relatieId}, function(data) {
		$.getScript("pages/beherenRelatie/details/bedrijven.js", function(dataX, textStatus, jqxhr) {
			ko.applyBindings(new Bedrijven(data, log));
		});
    });

	$('#opslaanPolis').click(function(){
    	verbergMeldingen();
      	var polis = {
           	relatie : $('#id').val(),
           	premie : $('#premie').val().replace(",", "."),
			maatschappij : $('#verzekeringsMaatschappijen').val(),
			soortVerzekering : $('#soortVerzekering').val(),
			polisNummer : $('#polisNummer').val(),
			ingangsDatumString : $('#ingangsDatumString').val(),
			wijzigingsdatumString : $('#wijzigingsdatumString').val(),
			prolongatiedatumString : $('#prolongatiedatumString').val(),
			bedrijf : $('#bedrijfBijPolis').val(),
			betaalfrequentie : $('#betaalfrequentie').val()
      	}
      	var json = JSON.stringify(polis);
		$.ajax({
			type: "POST",
			url: '../dejonge/rest/medewerker/polis/opslaan',
			contentType: "application/json",
            data: json,
            success: function(data) {
            	$('progress').show();
				var formData = new FormData($('form')[0]);
				uploadBestand(formData, '../dejonge/rest/medewerker/polis/upload');
        	},
			error: function (data) {
				plaatsFoutmelding(data);
			}
    	});
	});
}