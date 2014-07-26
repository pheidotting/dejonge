function start(log, relatieId, actie, subId){
	$('#content').load('pages/beherenRelatie/template.html', function(response, status, xhr) {
		if (status == "success") {
			//Op basis van actie de actieve tab bepalen
			if(actie == undefined){
				actie= "beherenRelatie";
			}
			$("#" + actie).addClass("navdivactive");
			
			//Onderliggende pagina aanroepen
			$.getScript("pages/beherenRelatie/details/" + actie + ".js", function(){
				go(log, relatieId, actie, subId);
			});
			$('#details').load("pages/beherenRelatie/details/" + actie + ".html");
			
			//Navigatie
			$("#beherenRelatie").click(function(){
				document.location.hash='#beherenRelatie/' + relatieId;
			});
			$("#bedrijven").click(function(){
				document.location.hash='#beherenRelatie/' + relatieId + '/bedrijven';
			});
			$("#bedrijf").click(function(){
				document.location.hash='#beherenRelatie/' + relatieId + '/bedrijf/0';
			});
			$("#polissen").click(function(){
				document.location.hash='#beherenRelatie/' + relatieId + '/polissen';
			});
			$("#polis").click(function(){
				document.location.hash='#beherenRelatie/' + relatieId + '/polis/0';
			});
			$("#schades").click(function(){
				document.location.hash='#beherenRelatie/' + relatieId + '/schades';
			});
			$("#schade").click(function(){
				document.location.hash='#beherenRelatie/' + relatieId + '/schade/0';
			});
			$("#bijlages").click(function(){
				document.location.hash='#beherenRelatie/' + relatieId + '/bijlages';
			});
		}
	});
}