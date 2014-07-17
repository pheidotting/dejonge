function go(log, relatieId, actie, subId){
	$.get( "../dejonge/rest/medewerker/bedrijf/lijst", {"id" : relatieId}, function(data) {
		log.debug("Gegevens opgehaald, applyBindings");
       	ko.applyBindings(new Bedrijven(data, log));
    });
}

function Bedrijven(data, log){
	_this = this;
	
	this.bedrijven = ko.observableArray();
	$.each(data, function(i, item) {
		_this.bedrijven.push(new Bedrijf(item));
	});
}

function Bedrijf(data){
    this.id = ko.observable(data.id);
    this.naam = ko.observable(data.naam);
    this.kvk = ko.observable(data.kvk);
    this.straat = ko.observable(data.straat);
    this.huisnummer = ko.observable(data.huisnummer);
    this.toevoeging = ko.observable(data.toevoeging);
    this.postcode = ko.observable(data.postcode);
    this.plaats = ko.observable(data.plaats);
}
