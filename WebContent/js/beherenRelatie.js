define(['jquery',
        'js/beherenRelatieMain',
        'js/beherenRelatieBedrijven',
        'js/beherenRelatieBedrijf',
        'js/beherenRelatiePolissen',
        'js/beherenRelatiePolis',
        'js/beherenRelatieSchades',
        'js/beherenRelatieSchade',
        'js/beherenRelatieHypotheken',
        'js/beherenRelatieHypotheek',
        'js/beherenRelatieBijlages',
        'js/beherenRelatieAangifte',
        'js/beherenRelatieAangiftes',
        'commons/3rdparty/log',
        'commons/commonFunctions'],
    function($, beherenRelatie, beherenRelatieBedrijven, beherenRelatieBedrijf, beherenRelatiePolissen, beherenRelatiePolis, beherenRelatieSchades, beherenRelatieSchade, beherenRelatieHypotheken, beherenRelatieHypotheek, beherenRelatieBijlages, beherenRelatieAangifte, beherenRelatieAangiftes, log, commonFunctions) {

	return function(relatieId, actie, subId){
		$('#content').load('templates/beherenRelatieTemplate.html', function(response, status, xhr) {
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
				if(actie == undefined || actie == null){
					 actie = "";
				}

				//Onderliggende pagina aanroepen
				$('#details').load("templates/beherenRelatie" + actie + ".html", function(){
					if(actie == ""){
						new beherenRelatie(relatieId);
					}else if(actie == "bedrijven"){
						new beherenRelatieBedrijven(relatieId);
					}else if(actie == "bedrijf"){
						new beherenRelatieBedrijf(subId, relatieId);
					}else if(actie == "polissen"){
						new beherenRelatiePolissen(relatieId);
					}else if(actie == "polis"){
						new beherenRelatiePolis(subId, relatieId);
					}else if(actie == "schades"){
						new beherenRelatieSchades(relatieId);
					}else if(actie == "schade"){
						new beherenRelatieSchade(subId, relatieId);
					}else if(actie == "hypotheken"){
						new beherenRelatieHypotheken(relatieId);
					}else if(actie == "hypotheek"){
						new beherenRelatieHypotheek(subId, relatieId);
					}else if(actie == "bijlages"){
						new beherenRelatieBijlages(relatieId);
					}else if(actie == "aangifte"){
						new beherenRelatieAangifte(relatieId);
					}else if(actie == "aangiftes"){
						new beherenRelatieAangiftes(relatieId);
					}
					_relatieId = relatieId;
					_subId = subId;

				});

				if(actie == ""){
					actie = "beherenRelatie";
				}
				$("#" + actie).addClass("navdivactive");
				if(actie == "beherenRelatie"){
					actie = "";
				}

				//Navigatie
				$("#beherenRelatie").click(function(){
			    	commonFunctions.verbergMeldingen();
					document.location.hash='#beherenRelatie/' + relatieId;
				});
				$("#bedrijven").click(function(){
			    	commonFunctions.verbergMeldingen();
					document.location.hash='#beherenRelatie/' + relatieId + '/bedrijven';
				});
				$("#bedrijf").click(function(){
			    	commonFunctions.verbergMeldingen();
					document.location.hash='#beherenRelatie/' + relatieId + '/bedrijf/0';
				});
				$("#polissen").click(function(){
			    	commonFunctions.verbergMeldingen();
					document.location.hash='#beherenRelatie/' + relatieId + '/polissen';
				});
				$("#polis").click(function(){
			    	commonFunctions.verbergMeldingen();
					document.location.hash='#beherenRelatie/' + relatieId + '/polis/0';
				});
				$("#schades").click(function(){
			    	commonFunctions.verbergMeldingen();
					document.location.hash='#beherenRelatie/' + relatieId + '/schades';
				});
				$("#schade").click(function(){
			    	commonFunctions.verbergMeldingen();
					document.location.hash='#beherenRelatie/' + relatieId + '/schade/0';
				});
				$("#hypotheken").click(function(){
					commonFunctions.verbergMeldingen();
					document.location.hash='#beherenRelatie/' + relatieId + '/hypotheken';
				});
				$("#hypotheek").click(function(){
					commonFunctions.verbergMeldingen();
					document.location.hash='#beherenRelatie/' + relatieId + '/hypotheek/0';
				});
				$("#bijlages").click(function(){
			    	commonFunctions.verbergMeldingen();
					document.location.hash='#beherenRelatie/' + relatieId + '/bijlages';
				});
				$("#aangiftes").click(function(){
					commonFunctions.verbergMeldingen();
					document.location.hash='#beherenRelatie/' + relatieId + '/aangiftes';
				});
				$("#aangifte").click(function(){
					commonFunctions.verbergMeldingen();
					document.location.hash='#beherenRelatie/' + relatieId + '/aangifte/0';
				});
			}
		});
	};
});