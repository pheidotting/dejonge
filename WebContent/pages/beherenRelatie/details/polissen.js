function go(log, relatieId, actie, subId){
	$.get( "../dejonge/rest/medewerker/polis/lijst", {"relatieId" : relatieId}, function(data) {
		log.debug("Gegevens opgehaald, applyBindings");
       	ko.applyBindings(new Polissen(data, log));
    });
}

function Polis(data, log){
	var self = this;

	self.id = ko.observable(data.id);
	self.omschrijving = ko.observable(data.omschrijving);
	self.polisNummer = ko.observable(data.polisNummer);
	self.ingangsDatum = ko.observable(moment(data.ingangsDatum).format("DD-MM-YYYY"));
	self.prolongatieDatum = ko.observable(moment(data.prolongatieDatum).format("DD-MM-YYYY"));
	self.wijzigingsDatum = ko.observable(moment(data.wijzigingsDatum).format("DD-MM-YYYY"));
	self.maatschappij = ko.observable(data.maatschappij.naam);
	self.soort = ko.observable(data.soort);
	self.premie = ko.observable(data.premie);
	self.betaalfrequentie = ko.observable(data.betaalfrequentie);
	self.bedrijf = ko.observable(data.bedrijf);
	self.idDiv = ko.computed(function() {
        return "collapsable" + data.id;
	}, this);
	self.idDivLink = ko.computed(function() {
        return "#collapsable" + data.id;
	}, this);
	self.className = ko.computed(function() {
		var datum = moment(data.ingangsDatum);
		var tijd = moment(datum).fromNow();
		if(tijd.substr(tijd.length - 3) == "ago"){
			return "panel-title";
		}else{
			return "polisNietActief panel-title";
		}
	}, this);

	self.bijlages = ko.observableArray();
	if(data.bijlages != null){
		$.each(data.bijlages, function(i, item){
			self.bijlages.push(new Bijlage(item));
		})
	}
}

function Polissen(data, log){
	var self = this;
	
	self.polissen = ko.observableArray();
	$.each(data, function(i, item){
		self.polissen.push(new Polis(item, log));
	})
}
