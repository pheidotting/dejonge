function Relatie(data, log) {
	var _this = this;
	_this.identificatie = ko.observable(data.identificatie).extend({required: true, email: true});
	_this.id = ko.observable(data.id);
	_this.voornaam = ko.observable(data.voornaam).extend({required: true});
	_this.achternaam = ko.observable(data.achternaam).extend({required: true});
	_this.tussenvoegsel = ko.observable(data.tussenvoegsel);
	_this.straat = ko.observable(data.straat);
	_this.huisnummer = ko.observable(data.huisnummer).extend({ number: true });
	_this.toevoeging = ko.observable(data.toevoeging);
	_this.postcode = ko.observable(data.postcode);
	_this.plaats = ko.observable(data.plaats);
	_this.bsn = ko.observable(data.bsn);
	_this.zakelijkeKlant = ko.observable(data.zakelijkeKlant);
	if(_this.zakelijkeKlant){
		$('#bedrijven').show();
	}else{
		$('#bedrijven').hide();
	}
	_this.rekeningnummers = ko.observableArray();
	if(data.rekeningnummers != null){
		$.each(data.rekeningnummers, function(i, item) {
			_this.rekeningnummers.push(new RekeningNummer(item));
		});
	}
	_this.telefoonnummers = ko.observableArray();
	if(data.telefoonnummers != null){
		$.each(data.telefoonnummers, function(i, item) {
			_this.telefoonnummers.push(new TelefoonNummer(item));
		});
	}

	_this.geboorteDatum = ko.observable(data.geboorteDatumOpgemaakt);
	_this.overlijdensdatum = ko.observable(data.overlijdensdatumOpgemaakt);
	_this.geslacht = ko.observable(data.geslacht);
	_this.burgerlijkeStaat = ko.observable(data.burgerlijkeStaat);

//	_this.onderlingeRelaties = ko.observableArray();
	/*		if(data.onderlingeRelaties != null){
		$.each(data.onderlingeRelaties, function(i, item) {
			_this.onderlingeRelaties.push(new OnderlingeRelatie(item));
 		});
	}
	*/
	_this.voegRekeningToe = function() {
		_this.rekeningnummers.push(new RekeningNummer(""));
	};
	
	_this.verwijderRekening = function(nummer){
		_this.rekeningnummers.remove(nummer);
	};
	
	_this.voegTelefoonNummerToe = function() {
		_this.telefoonnummers.push(new TelefoonNummer(""));
	};
	
	_this.verwijderTelefoonNummer = function(telefoon) {
		_this.telefoonnummers.remove(telefoon);
	};
	
	_this.opslaan = function(){
		log.debug("Opslaan Relatie");
    	var result = ko.validation.group(_this, {deep: true});
    	if(!_this.isValid()){
    		result.showAllMessages(true);
    	}else{
			verbergMeldingen();
			if(ko.utils.unwrapObservable(_this.geboorteDatum) != null && ko.utils.unwrapObservable(_this.geboorteDatum) != ''){
				_this.geboorteDatum(moment(ko.utils.unwrapObservable(_this.geboorteDatum), "DD-MM-YYYY").format("YYYY-MM-DD"));
			}
			if(ko.utils.unwrapObservable(_this.overlijdensdatum) != null && ko.utils.unwrapObservable(_this.overlijdensdatum) != ''){
				_this.overlijdensdatum(moment(ko.utils.unwrapObservable(_this.overlijdensdatum), "DD-MM-YYYY").format("YYYY-MM-DD"));
			}
			log.debug(ko.toJSON(this));
			$.ajax({
				url: '../dejonge/rest/medewerker/gebruiker/opslaan',
				type: 'POST',
				data: ko.toJSON(this) ,
				contentType: 'application/json; charset=utf-8',
				success: function (response) {
					document.location.hash='#lijstRelaties';
					plaatsMelding("De gegevens zijn opgeslagen");
				},
				error: function (data) {
					plaatsFoutmelding(data);
				}
			});
			if(ko.utils.unwrapObservable(_this.geboorteDatum) != null && ko.utils.unwrapObservable(_this.geboorteDatum) != ''){
				_this.geboorteDatum(moment(ko.utils.unwrapObservable(_this.geboorteDatum), "YYYY-MM-DD").format("DD-MM-YYYY"));
			}
			if(ko.utils.unwrapObservable(_this.overlijdensdatum) != null && ko.utils.unwrapObservable(_this.overlijdensdatum) != ''){
				_this.overlijdensdatum(moment(ko.utils.unwrapObservable(_this.overlijdensdatum), "YYYY-MM-DD").format("DD-MM-YYYY"));
			}
    	}
	};

	_this.verwijderenRelatie = function(relatie){
		log.debug("Verwijderen relatie");
		$.ajax({
			type: "GET",
			url: '../dejonge/rest/medewerker/gebruiker/verwijderen',
			dataType:'json',
			data: {
				id : ko.utils.unwrapObservable(relatie.id)
			}
		});
		document.location.hash='#lijstRelaties';
	};
}

function OnderlingeRelatie(data){
	var _this = this;

	_this.id = ko.observable(data.id);
	_this.idNaar = ko.observable(data.idNaar);
	_this.metWie = ko.observable(data.metWie);
	_this.soort = ko.observable(data.soort);
}

function RekeningNummer(data){
	var _this = this;

	_this.id = ko.observable(data.id);
	_this.rekeningnummer = ko.observable(data.rekeningnummer);
	_this.bic = ko.observable(data.bic);
}

function TelefoonNummer(data){
	var _this = this;

	_this.id = ko.observable(data.id);
	_this.telefoonnummer = ko.observable(data.telefoonnummer);
	_this.soort = ko.observable(data.soort);
}

function go(log, relatieId, actie, subId){
	log.debug("Ophalen gegevens Relatie met id " + relatieId);

	$.get( "../dejonge/rest/medewerker/gebruiker/lees", {"id" : relatieId}, function(data) {
		log.debug("Gegevens opgehaald, applyBindings");
		ko.validation.registerExtenders();
	   	ko.applyBindings(new Relatie(data, log));
	});
}