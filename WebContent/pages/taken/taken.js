define(["pages/taken/model/taak",
         "pages/taken/model/taken",
         "knockout"],
         function(Taak, Taken, ko) {
	
	function takenOphalen(){
		$('#content').load("pages/taken/taken.html", function(){
			$.get('../dejonge/rest/medewerker/taak/lijst', function(data){
				ko.applyBindings(new Taken(data));
				setTimeout(takenOphalen,5000);
			});
		});
	};
	
	return takenOphalen();
});