function go(log, relatieId, actie, subId){
	$.getScript("pages/beherenRelatie/details/bedrijven.js");
	
	$('#opslaanBedrijf').click(function(){
		verbergMeldingen();
      	var bedrijf = {
	       	relatie : relatieId,
	       	naam : $('#naamBedrijf').val(),
	        kvk : $('#kvkBedrijf').val(),
	       	straat : $('#straatBedrijf').val(),
	       	huisnummer : $('#huisnummerBedrijf').val(),
	       	toevoeging : $('#toevoegingBedrijf').val(),
	       	postcode : $('#postcodeBedrijf').val(),
	       	plaats : $('#plaatsBedrijf').val()
      	}
      	var json = JSON.stringify(bedrijf);
		$.ajax({
			type: "POST",
			url: '../dejonge/rest/medewerker/gebruiker/opslaanBedrijf',
			contentType: "application/json",
	        data: json,
	        success: function (response) {
	        	plaatsMelding("De gegevens zijn opgeslagen");
	    		document.location.hash='#beherenRelatie/' + relatieId + '/bedrijven';
	        },
	        error: function (data) {
	        	plaatsFoutmelding(data);
	        }
		});
	});
}