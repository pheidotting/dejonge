define(['jquery',
         'knockout',
         'model/relatie',
         'commons/commonFunctions',
         'knockoutValidation',
         'blockUI'],
	function ($, ko, Relatie, functions, blockUI) {

	return function(){
		var relatieLijstModel = ko.validatedObservable({

			lijst : ko.observableArray(),

			naarDetailScherm : function(relatie){
				functions.verbergMeldingen();
				$(document).ajaxStop($.unblockUI);
				$.blockUI({ message: '<img src="images/ajax-loader.gif">'});
				document.location.hash='#beherenRelatie/' + relatie.id;
			},

			toevoegenNieuweRelatie : function(){
				functions.verbergMeldingen();
				document.location.hash='#beherenRelatie/0';
			}
		});

		return relatieLijstModel;
    };
});