function start(log, relatieId, actie, subId){
	
	
	$('#content').load('pages/beherenRelatie/template.html', function(response, status, xhr) {
		if(relatieId == undefined || relatieId == null || relatieId == 0){
			$('#bedrijven').hide();
			$('#bedrijf').hide();
			$('#polissen').hide();
			$('#polis').hide();
			$('#schades').hide();
			$('#schade').hide();
			$('#hypotheken').hide();
			$('#hypotheek').hide();
			$('#bijlages').hide();
		}
		if (status == "success") {
			//Op basis van actie de actieve tab bepalen
			if(actie == undefined){
				actie= "beherenRelatie";
			}
			$("#" + actie).addClass("navdivactive");

			//Onderliggende pagina aanroepen
			$.getScript("pages/beherenRelatie/details/" + actie + ".js", function(){
				if(actie != "hypotheek" && actie != "hypotheken"){
					go(log, relatieId, actie, subId);
				}
				_relatieId = relatieId;
				_subId = subId;

			});
			if(actie != "polis"){
				$('#details').load("pages/beherenRelatie/details/" + actie + ".html");
			}

			//Navigatie
			$("#beherenRelatie").click(function(){
		    	verbergMeldingen();
				document.location.hash='#beherenRelatie/' + relatieId;
			});
			$("#bedrijven").click(function(){
		    	verbergMeldingen();
				document.location.hash='#beherenRelatie/' + relatieId + '/bedrijven';
			});
			$("#bedrijf").click(function(){
		    	verbergMeldingen();
				document.location.hash='#beherenRelatie/' + relatieId + '/bedrijf/0';
			});
			$("#polissen").click(function(){
		    	verbergMeldingen();
				document.location.hash='#beherenRelatie/' + relatieId + '/polissen';
			});
			$("#polis").click(function(){
		    	verbergMeldingen();
				document.location.hash='#beherenRelatie/' + relatieId + '/polis/0';
			});
			$("#schades").click(function(){
		    	verbergMeldingen();
				document.location.hash='#beherenRelatie/' + relatieId + '/schades';
			});
			$("#schade").click(function(){
		    	verbergMeldingen();
				document.location.hash='#beherenRelatie/' + relatieId + '/schade/0';
			});
			$("#hypotheken").click(function(){
				verbergMeldingen();
				document.location.hash='#beherenRelatie/' + relatieId + '/hypotheken';
			});
			$("#hypotheek").click(function(){
				verbergMeldingen();
				document.location.hash='#beherenRelatie/' + relatieId + '/hypotheek/0';
			});
			$("#bijlages").click(function(){
		    	verbergMeldingen();
				document.location.hash='#beherenRelatie/' + relatieId + '/bijlages';
			});
		}
	});
}