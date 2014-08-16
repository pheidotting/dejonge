function go(log, relatieId, actie, subId){
	$.get( "../dejonge/rest/medewerker/bedrijf/lijst", {"relatieId" : relatieId}, function(data) {
		log.debug("Gegevens opgehaald, applyBindings");
       	ko.applyBindings(new Bedrijven(data, log, relatieId));
    });
}

function Bedrijven(data, log, relatieId){
	var _this = this;
	
	_this.bedrijven = ko.observableArray();
	$.each(data, function(i, item) {
		_this.bedrijven.push(new Bedrijf(item, log, relatieId));
	});

	this.verwijderBedrijf = function(bedrijf){
		verbergMeldingen();
		var r=confirm("Weet je zeker dat je dit bedrijf wilt verwijderen?");
		if (r==true) {
			_this.bedrijven.remove(bedrijf);
			$.get( "../dejonge/rest/medewerker/bedrijf/verwijder", {"id" : ko.utils.unwrapObservable(bedrijf.id)}, function( data ) {});
		}
    };
}

function Bedrijf(data, log, relatieId){
	log.debug(relatieId);

	_this = this;

	_this.id = ko.observable(data.id);
	_this.naam = ko.observable(data.naam);
	_this.kvk = ko.observable(data.kvk);
	_this.straat = ko.observable(data.straat);
	_this.huisnummer = ko.observable(data.huisnummer).extend({min: 1});
	_this.toevoeging = ko.observable(data.toevoeging);
	_this.postcode = ko.observable(data.postcode);
	_this.plaats = ko.observable(data.plaats);
	_this.relatie = ko.observable(relatieId);

	_this.idDiv = ko.computed(function() {
        return "collapsable" + data.id;
	}, this);
	_this.idDivLink = ko.computed(function() {
        return "#collapsable" + data.id;
	}, this);

	_this.bewerkBedrijf = function(bedrijf){
		verbergMeldingen();
    	document.location.hash = "#beherenRelatie/" + relatieId + "/bedrijf/" + ko.utils.unwrapObservable(bedrijf.id);
    };

	_this.isValid = function(){
		return _this.huisnummer.isValid();
	};

	_this.opslaan = function(bedrijf){
		verbergMeldingen();
		if(_this.isValid()){
			$.ajax({
				type: "POST",
				url: '../dejonge/rest/medewerker/gebruiker/opslaanBedrijf',
				contentType: "application/json",
		        data: ko.toJSON(bedrijf),
		        success: function (response) {
		        	plaatsMelding("De gegevens zijn opgeslagen");
		    		document.location.hash='#beherenRelatie/' + relatieId + '/bedrijven';
		        },
		        error: function (data) {
		        	plaatsFoutmelding(data);
		        }
			});
		}
	};
}