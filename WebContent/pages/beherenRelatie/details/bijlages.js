function go(log, relatieId, actie, subId){
	$.get( "../dejonge/rest/medewerker/bijlage/lijst", {"relatieId" : relatieId}, function(data) {
		log.debug("Gegevens opgehaald, applyBindings");
       	ko.applyBindings(new Bijlages(data, log));
    });
}

function Bijlage(data){
	var self = this;

	self.id = ko.observable(data.id);
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
	
	self.verwijderBijlage = function(bijlage){
		verbergMeldingen();
		var r=confirm("Weet je zeker dat je dit bedrijf wilt verwijderen?");
		if (r==true) {
			self.bijlages.remove(bijlage);
			$.get( "../dejonge/rest/medewerker/bijlage/verwijder", {"bijlageId" : ko.utils.unwrapObservable(bijlage.id)}, function() {});
		}
	}
}