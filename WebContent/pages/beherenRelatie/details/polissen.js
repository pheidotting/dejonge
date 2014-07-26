function go(log, relatieId, actie, subId){
	$.getScript("pages/beherenRelatie/details/bijlages.js", function(data) {
		$.get( "../dejonge/rest/medewerker/polis/lijst", {"relatieId" : relatieId}, function(data) {
			log.debug("Gegevens opgehaald, applyBindings");
	       	ko.applyBindings(new Polissen(data, log));
	    });
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
	
    self.verwijderPolis = function(polis){
		var r=confirm("Weet je zeker dat je deze polis wilt verwijderen?");
		if (r==true) {
			self.polissen.remove(polis);
			$.get( "../dejonge/rest/medewerker/polis/verwijder", {"id" : ko.utils.unwrapObservable(polis.id)}, function( data ) {});
		}
    }
    
    self.schadeMeldenBijPolis = function(polis){
//		$('#tabs').puitabview('select', 6);
		console.log(ko.utils.unwrapObservable(polis.id));
		console.log($('#polisVoorSchademelding').val());
//		$('#polisVoorSchademelding').val(ko.utils.unwrapObservable(polis.id));
    }
    
    self.bewerkPolis = function(polis){
    	document.location.hash = "#beherenRelatie/3/polis/" + ko.utils.unwrapObservable(polis.id);
    }

}

function Polissen(data, log){
	var self = this;
	
	self.polissen = ko.observableArray();
	$.each(data, function(i, item){
		self.polissen.push(new Polis(item, log));
	})
}
