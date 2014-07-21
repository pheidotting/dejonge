function go(log, relatieId, actie, subId){
	$.get( "../dejonge/rest/medewerker/schade/lijst", {"relatieId" : relatieId}, function(data) {
		log.debug("Gegevens opgehaald, applyBindings");
       	ko.applyBindings(new Schades(data, log));
    });
}

function Schade(data){
	var self = this;
	
    self.id = ko.observable(data.id);
    self.polis = ko.observable(data.polis);
    self.schadeNummerMaatschappij = ko.observable(data.schadeNummerMaatschappij);
    self.schadeNummerTussenPersoon = ko.observable(data.schadeNummerTussenPersoon);
    self.soortSchade = ko.observable(data.soortSchade);
    self.locatie = ko.observable(data.locatie);
    self.statusSchade = ko.observable(data.statusSchade);
    self.datumTijdSchade = ko.observable(data.datumTijdSchade);
    self.datumTijdMelding = ko.observable(data.datumTijdMelding);
    self.datumAfgehandeld = ko.observable(data.datumAfgehandeld);
    self.eigenRisico = ko.observable(data.eigenRisico);
    self.omschrijving = ko.observable(data.omschrijving);
    
	self.idDiv = ko.computed(function() {
        return "collapsableSchade" + data.id;
	}, this);
	self.idDivLink = ko.computed(function() {
        return "#collapsableSchade" + data.id;
	}, this);

    this.opmerkingen = ko.observableArray();
		if(data.opmerkingen != null){
		$.each(data.opmerkingen, function(i, item){
			self.opmerkingen.push(new Opmerking(item));
		})
		}

	self.bijlages = ko.observableArray();
	if(data.bijlages != null){
		$.each(data.bijlages, function(i, item){
			self.bijlages.push(new Bijlage(item));
		})
	}
}

function Schades(data, log){
	var self = this;
	
	self.schades = ko.observableArray();
	$.each(data, function(i, item){
		self.schades.push(new Schade(item, log));
	})
}
