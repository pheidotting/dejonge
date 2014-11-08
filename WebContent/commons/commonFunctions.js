define([ "commons/3rdparty/log"],
         function(log) {

	var refreshIntervalId;

	return {

 		plaatsFoutmelding: function(melding){
        	var foutmelding = jQuery.parseJSON(melding.responseText);

			$('#alertDanger').show();
			$('#alertDanger').html("Er is een fout opgetreden : " + foutmelding.foutmelding);
 		},

 		plaatsMelding: function(melding){
 			$("html, body").animate({ scrollTop: 0 }, "slow");
			$('#alertSucces').show();
			$('#alertSucces').html(melding);
			refreshIntervalId = setInterval(this.verbergMeldingen, 10000);
		},

		nietMeerIngelogd: function(data){
        	log.error("FOUT opgehaald : " + JSON.stringify(data));
        	log.error("naar inlogscherm");
			document.location.hash='#inloggen';
			this.plaatsFoutmelding("Sessie verlopen, graag opnieuw inloggen");
		},

 		verbergMeldingen: function(){
			clearInterval(refreshIntervalId); 		
 			$("html, body").animate({ scrollTop: 0 }, "slow");
			$('#alertSucces').hide();
			$('#alertDanger').hide();
 		},
 
		uitloggen: function(){
	    	$.ajax({
	            url: '../dejonge/rest/authorisatie/authorisatie/uitloggen',
	            type: 'GET'
	    	})
			$('#ingelogdeGebruiker').html("");
			$('#uitloggen').hide();
			$('#homeKnop').hide();
			document.location.hash='#inloggen';
		},
 
		haalIngelogdeGebruiker: function(){
			log.debug("Haal ingelogde gebruiker");
	    	$.ajax({
	            url: '../dejonge/rest/authorisatie/authorisatie/ingelogdeGebruiker',
	            type: 'GET',
	            contentType: 'application/json; charset=utf-8',
	            success: function (response) {
	            	if(response.kantoor != null){
	            		log.debug("Ingelogde gebruiker : " + response.gebruikersnaam + ", (" + response.kantoor + ")");
						$('#ingelogdeGebruiker').html("Ingelogd als : " + response.gebruikersnaam + ", (" + response.kantoor + ")");
	            	}else{
	            		log.debug("Ingelogde gebruiker : " + response.gebruikersnaam);
						$('#ingelogdeGebruiker').html("Ingelogd als : " + response.gebruikersnaam);
	            	}
					$('#uitloggen').show();
					$('#homeKnop').show();
	            },
	            error: function (data) {
					$('#ingelogdeGebruiker').html("");
					$('#uitloggen').hide();
					$('#homeKnop').hide();
	    			document.location.hash='#inloggen';
	            }
	        });
		},

		laadDataMetLoginCheck: function(url) {
			var opgehaaldeData;
			$.ajax({
				type: "GET",  
				url: url,
				async: false,
				dataType: "json",
				context: this,
				success: function(data) {
					opgehaaldeData = data;
				},
	            error: function (data) {
					document.location.hash='#inloggen';
        		}
			});
			return opgehaaldeData;
		}
    };
});