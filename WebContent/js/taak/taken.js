define(['jquery',
        "knockout",
        "js/model/taak/taken",
         "knockout"],
    function($, ko, Taken) {

	return function(){
		$('#content').load("templates/taken/taken.html", function(){
			$.get('../dejonge/rest/medewerker/taak/lijst', function(data){
				ko.applyBindings(new Taken(data));
//				setTimeout(takenOphalen, 5000);
			});
		});
	};

});