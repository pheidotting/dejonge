function start(log, relatieId, actie, subId){
	$('#content').load('pages/beherenRelatie/template.html', function(response, status, xhr) {
		if (status == "success") {
			//Op basis van actie de actieve tab bepalen
			if(actie == undefined){
				actie= "beherenRelatie";
			}
			$("#" + actie).addClass("navdivactive");
			
			//Onderliggende pagina aanroepen
			$('#details').load("pages/beherenRelatie/details/" + actie + ".html");
			$.getScript("pages/beherenRelatie/details/" + actie + ".js", function(){
				go(log, relatieId, actie, subId);
			});
			
			//Navigatie
			$("#beherenRelatie").click(function(){
				document.location.hash='#beherenRelatie/3';
			});
			$("#bedrijven").click(function(){
				document.location.hash='#beherenRelatie/3/bedrijven';
			});
			$("#bedrijf").click(function(){
				document.location.hash='#beherenRelatie/3/bedrijf/0';
			});
			$("#polissen").click(function(){
				document.location.hash='#beherenRelatie/3/polissen';
			});
			$("#polis").click(function(){
				document.location.hash='#beherenRelatie/3/polis/0';
			});
			$("#schades").click(function(){
				document.location.hash='#beherenRelatie/3/schades';
			});
			$("#schade").click(function(){
				document.location.hash='#beherenRelatie/3/schade/0';
			});
			$("#bijlages").click(function(){
				document.location.hash='#beherenRelatie/3/bijlages';
			});
		}
	});
}