define(['jquery',
        'model/schade',
         'knockout',
         'commons/3rdparty/log',
         'commons/commonFunctions'],
	function ($, Schade, ko, log, commonFunctions) {

	return function schadesModel (data, relatieId) {
		selfSchades = this;

		selfSchades.schades = ko.observableArray();
		$.each(data, function(i, item){
			selfSchades.schades.push(new Schade(item, log, relatieId));
		});

		selfSchades.verwijderPolis = function(schade){
			var r=confirm("Weet je zeker dat je deze schade wilt verwijderen?");
			if (r==true) {
				selfSchades.schades.remove(schade);
				$.get( "../dejonge/rest/medewerker/schade/verwijder", {"id" : ko.utils.unwrapObservable(schade.id)}, function() {});
			}
	    };
    };
});