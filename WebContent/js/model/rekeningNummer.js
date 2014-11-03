define(['jquery',
        "knockout"],
     function($, ko) {

	return function(data){
		var rekeningModel = ko.validatedObservable({
			id : ko.observable(data.id),
			rekeningnummer : ko.observable(data.rekeningnummer),
			bic : ko.observable(data.bic)
		});
		return rekeningModel;
	};
});