define(['jquery',
         'knockout',
         'commons/3rdparty/log',
         'commons/commonFunctions'],
	function ($, ko, log, commonFunctions) {

	return function bijlageModel (data){
		bijlage = this;

		bijlage.id = ko.observable(data.id);
		bijlage.url = ko.computed(function() {
	        return "../dejonge/rest/medewerker/bijlage/download?bijlageId=" + data.id;
		}, this);
		bijlage.bestandsNaam = ko.observable(data.bestandsNaam);
		bijlage.soortBijlage = ko.observable(data.soortBijlage);
		bijlage.parentId = ko.observable(data.parentId);
		bijlage.tonen = ko.computed(function() {
			return ko.utils.unwrapObservable(bijlage.soortBijlage) + " (" + ko.utils.unwrapObservable(bijlage.parentId) + ")";
		},this);
    };
});