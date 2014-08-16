function go(log, relatieId, actie, subId){
	$.get( "../dejonge/rest/medewerker/bijlage/lijst", {"relatieId" : relatieId}, function(data) {
		log.debug("Gegevens opgehaald, applyBindings");
       	ko.applyBindings(new Bijlages(data, log));
    });
}

function Bijlage(data){
	var self = this;

	self.url = ko.computed(function() {
        return "../dejonge/rest/medewerker/bijlage/download?bijlageId=" + data.id;
	}, this);
	self.bestandsNaam = ko.observable(data.bestandsNaam);
	self.soortBijlage = ko.observable(data.soortBijlage);
}

function Bijlages(data, log){
	var self = this;

	self.bijlages = ko.observableArray();
	$.each(data, function(i, item){
		self.bijlages.push(new Bijlage(item, log));
	});
}