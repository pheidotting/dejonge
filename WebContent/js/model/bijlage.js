define(['jquery',
         'knockout',
         'commons/3rdparty/log',
         'commons/commonFunctions'],
	function ($, ko, log, commonFunctions) {

	return function bijlageModel (modelData){
		thisBijlage = this;

		thisBijlage.id = ko.observable(modelData.id);
		thisBijlage.url = ko.computed(function() {
	        return "../dejonge/rest/medewerker/bijlage/download?bijlageId=" + modelData.id;
		}, this);
		thisBijlage.bestandsNaam = ko.observable(modelData.bestandsNaam);
		thisBijlage.soortBijlage = ko.observable(modelData.soortBijlage);
		thisBijlage.parentId = ko.observable(modelData.parentId);
		thisBijlage.omschrijving = ko.observable(modelData.omschrijving);
		thisBijlage.datumUpload = ko.observable(modelData.datumUpload);
		thisBijlage.omschrijvingOfBestandsNaam = ko.computed(function() {
			if(thisBijlage.omschrijving() == null){
				return thisBijlage.bestandsNaam() + " " + thisBijlage.datumUpload();
			}else{
				return thisBijlage.omschrijving() + " " + thisBijlage.datumUpload();
			}
		},this);
		thisBijlage.tonen = ko.computed(function() {
			return ko.utils.unwrapObservable(thisBijlage.soortBijlage) + " (" + ko.utils.unwrapObservable(thisBijlage.parentId) + ")";
		},this);
    };
});