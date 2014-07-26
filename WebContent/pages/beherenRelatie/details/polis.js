function go(log, relatieId, actie, subId){
//	$.getScript("pages/beherenRelatie/details/polissen.js");
//	$.getScript("pages/beherenRelatie/details/bijlages.js");
	
	$.get( "../dejonge/rest/medewerker/overig/lijstVerzekeringsMaatschappijen", {}, function(data) {
		var $select = $('#verzekeringsMaatschappijen');
		$.each(data, function(key, value) {
		    $('<option>').text(value).appendTo($select);
		});
    });
	
	$.get( "../dejonge/rest/medewerker/bedrijf/lijst", {"relatieId" : relatieId}, function(data) {
		$.getScript("pages/beherenRelatie/details/bedrijven.js", function(dataX, textStatus, jqxhr) {
			var $select = $('#bedrijfBijPolis');
			$.each(data, function(key, value) {
			    $('<option>', { value : key }).text(value.naam).appendTo($select);
			});
		});
	});
	
	if(subId != null){
		log.debug("Ophalen Polis met id : " + subId);
		$.get( "../dejonge/rest/medewerker/polis/lees", {"id" : subId}, function(data) {
  			$('#polisId').val(data.id);
       		$('#id').val(data.relatie);
       		$('#premie').val(data.premie);
			$('#verzekeringsMaatschappijen').val(data.maatschappij);
			$('#soortVerzekering').val(data.soortVerzekering);
			$('#polisNummer').val(data.polisNummer);
			$('#ingangsDatumString').val(data.ingangsDatum);
			$('#wijzigingsdatumString').val(data.wijzigingsDatum);
			$('#prolongatiedatumString').val(data.prolongatieDatum);
			$('#bedrijfBijPolis').val(data.bedrijf);
			$('#betaalfrequentie').val(data.betaalfrequentie);
	    });
	}
	
	$('#opslaanPolis').click(function(){
    	verbergMeldingen();
      	var polis = {
      		id : $('#polisId').val(),
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