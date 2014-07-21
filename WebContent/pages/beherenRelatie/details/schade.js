function go(log, relatieId, actie, subId){
	$.getScript("pages/beherenRelatie/details/schades.js");
	$.getScript("pages/beherenRelatie/details/polissen.js");

	$.get( "../dejonge/rest/medewerker/polis/lijst", {"relatieId" : relatieId}, function(data) {
       	ko.applyBindings(new Polissen(data, log));
    });

	$.get( "../dejonge/rest/medewerker/overig/lijstStatusSchade", {"relatieId" : relatieId}, function(data) {
		var $select = $('#statusSchade');
		$.each(data, function(key, value) {
		    $('<option>').text(value).appendTo($select);
		});
	});
	
	var bestPictures = new Bloodhound({
		datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
		queryTokenizer: Bloodhound.tokenizers.whitespace,
		remote: '../dejonge/rest/medewerker/overig/soortenSchade?query=%QUERY'
	});
	
	bestPictures.initialize();
	
	$('#soortSchade').typeahead({
		hint: true,
		highlight: true,
		minLength: 1
	},
	{
		name: 'states',
		displayKey: 'value',
		source: bestPictures.ttAdapter()
	});
	
	$('#schadeMeldingOpslaan').click(function(){
    	var schade = new Schade({
    		polis : $('#polisVoorSchademelding').val(),
	        schadeNummerMaatschappij : $('#schadeNummerMaatschappij').val(),
	        schadeNummerTussenPersoon : $('#schadeNummerTussenpersoon').val(),
	        soortSchade : $('#soortSchade').val(),
	        locatie : $('#locatie').val(),
	        statusSchade : $('#statusSchade').val(),
	        datumTijdSchade : $('#datumTijdSchade').val(),
	        datumTijdMelding : $('#datumTijdMelding').val(),
	        datumAfgehandeld : $('#datumAfgehandeld').val(),
	        eigenRisico : $('#eigenRisico').val(),
	        omschrijving : $('#omschrijving').val()
    	});
    	$.ajax({
            url: '../dejonge/rest/medewerker/schade/opslaan',
            type: 'POST',
            data: ko.toJSON(schade) ,
            contentType: 'application/json; charset=utf-8',
            success: function (response) {
    			for (var int = 1; int <= $('#hoeveelFiles').val(); int++) {
    				var formData = new FormData($('#schadeMeldForm')[0]);
    				uploadBestand(formData, '../dejonge/rest/medewerker/bijlage/uploadSchade' + int + 'File');
    			}
            	plaatsMelding("De gegevens zijn opgeslagen");
				$('#tabs').puitabview('select', 5);
            },
            error: function (data) {
            	plaatsFoutmelding(data);
            }
        });
    });
}