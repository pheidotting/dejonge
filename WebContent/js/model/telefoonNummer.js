define(['jquery',
        "knockout"],
     function($, ko) {

	return function(data){
		var telefoonModel = ko.validatedObservable({
			id : ko.observable(data.id),
			telefoonnummer : ko.observable(data.telefoonnummer),
			soort : ko.observable(data.soort),
			omschrijving : ko.observable(data.omschrijving)
	});
		return telefoonModel;
	};
});