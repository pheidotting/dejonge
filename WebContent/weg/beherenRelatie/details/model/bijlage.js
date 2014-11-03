define([ "commons/3rdparty/log"],
         function(logger) {
	
	return function bijlage(data) {
		logger.debug(data);
		_this = this;

		_this.id = ko.observable(data.id);
		_this.url = ko.computed(function() {
	        return "../dejonge/rest/medewerker/bijlage/download?bijlageId=" + data.id;
		}, this);
		_this.bestandsNaam = ko.observable(data.bestandsNaam);
		_this.soortBijlage = ko.observable(data.soortBijlage);
		_this.parentId = ko.observable(data.parentId);
		_this.tonen = ko.computed(function() {
			return ko.utils.unwrapObservable(_this.soortBijlage) + " (" + ko.utils.unwrapObservable(_this.parentId) + ")";
		}, this);
	}
});