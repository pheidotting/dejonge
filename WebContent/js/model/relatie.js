define(['jquery',
        'commons/commonFunctions',
         'knockout',
         'model/rekeningNummer',
         'model/telefoonNummer',
         'moment',
         'commons/3rdparty/log',
         "commons/validation"],
	function ($, commonFunctions, ko, RekeningNummer, TelefoonNummer, moment, log, validation) {

	return function relatieModel (data){
		_thisRelatie = this;

		_thisRelatie.veranderDatum = function(datum){
			datum(commonFunctions.zetDatumOm(datum()));
		}
		_thisRelatie.identificatie = ko.observable(data.identificatie).extend({email: true});
		_thisRelatie.id = ko.observable(data.id);
		_thisRelatie.voornaam = ko.observable(data.voornaam).extend({required: true});
		_thisRelatie.achternaam = ko.observable(data.achternaam).extend({required: true});
		_thisRelatie.tussenvoegsel = ko.observable(data.tussenvoegsel);
		_thisRelatie.straat = ko.observable(data.straat);
		_thisRelatie.huisnummer = ko.observable(data.huisnummer).extend({ number: true});
		_thisRelatie.toevoeging = ko.observable(data.toevoeging);
		_thisRelatie.postcode = ko.observable(data.postcode);
		_thisRelatie.plaats = ko.observable(data.plaats);
		_thisRelatie.bsn = ko.observable(data.bsn);
		_thisRelatie.zakelijkeKlant = ko.observable(data.zakelijkeKlant);
		_thisRelatie.rekeningnummers = ko.observableArray();
		if(data.rekeningnummers != null){
			$.each(data.rekeningnummers, function(i, item) {
				_thisRelatie.rekeningnummers().push(new RekeningNummer(item));
			});
		}
		_thisRelatie.telefoonnummers = ko.observableArray();
		if(data.telefoonnummers != null){
			$.each(data.telefoonnummers, function(i, item) {
				_thisRelatie.telefoonnummers().push(new TelefoonNummer(item));
			});
		}
		_thisRelatie.geboorteDatum = ko.observable(data.geboorteDatumOpgemaakt).extend({validation: {
	        validator: function (val) {
	        	if(val != undefined){
	        		return validation.valideerDatum(val);
	        	}else{
	        		return true;
	        	}
	        },
	        message: 'Juiste invoerformaat is : dd-mm-eejj'
	    }});
		_thisRelatie.overlijdensdatum = ko.observable(data.overlijdensdatumOpgemaakt);
		_thisRelatie.geslacht = ko.observable(data.geslacht);
		_thisRelatie.burgerlijkeStaat = ko.observable(data.burgerlijkeStaat);

		_thisRelatie.voegRekeningToe = function() {
			_thisRelatie.rekeningnummers().push(new RekeningNummer(""));
			_thisRelatie.rekeningnummers.valueHasMutated();
		};

		_thisRelatie.verwijderRekening = function(nummer){
			_thisRelatie.rekeningnummers().remove(nummer);
			_thisRelatie.rekeningnummers.valueHasMutated();
		};

		_thisRelatie.voegTelefoonNummerToe = function() {
			_thisRelatie.telefoonnummers().push(new TelefoonNummer(""));
			_thisRelatie.telefoonnummers.valueHasMutated();
		};

		_thisRelatie.verwijderTelefoonNummer = function(telefoon) {
			_thisRelatie.telefoonnummers().remove(telefoon);
			_thisRelatie.telefoonnummers.valueHasMutated();
		};

		_thisRelatie.opslaan = function(){
	    	var result = ko.validation.group(_thisRelatie, {deep: true});
	    	if(!_thisRelatie.isValid()){
	    		result.showAllMessages(true);
	    	}else{
				commonFunctions.verbergMeldingen();
//				if(_thisRelatie.geboorteDatum() != null && _thisRelatie.geboorteDatum() != ''){
//					_thisRelatie.geboorteDatum(moment(_thisRelatie.geboorteDatum(), "DD-MM-YYYY").format("YYYY-MM-DD"));
//				}
//				if(_thisRelatie.overlijdensdatum() != null && _thisRelatie.overlijdensdatum() != ''){
//					_thisRelatie.overlijdensdatum(moment(_thisRelatie.overlijdensdatum(), "DD-MM-YYYY").format("YYYY-MM-DD"));
//				}
				log.debug("Versturen naar ../dejonge/rest/medewerker/gebruiker/opslaan : ");
				log.debug(ko.toJSON(_thisRelatie));
				$.ajax({
					url: '../dejonge/rest/medewerker/gebruiker/opslaan',
					type: 'POST',
					data: ko.toJSON(_thisRelatie) ,
					contentType: 'application/json; charset=utf-8',
					success: function (response) {
						document.location.hash='#lijstRelaties';
						commonFunctions.plaatsMelding("De gegevens zijn opgeslagen");
					},
					error: function (data) {
						commonFunctions.plaatsFoutmelding(data);
					}
				});
//				if(_thisRelatie.geboorteDatum() != null && _thisRelatie.geboorteDatum() != ''){
//					_thisRelatie.geboorteDatum(moment(_thisRelatie.geboorteDatum(), "YYYY-MM-DD").format("DD-MM-YYYY"));
//				}
//				if(_thisRelatie.overlijdensdatum() != null && _thisRelatie.overlijdensdatum() != ''){
//					_thisRelatie.overlijdensdatum(moment(_thisRelatie.overlijdensdatum(), "YYYY-MM-DD").format("DD-MM-YYYY"));
//				}
	    	}
		};

		_thisRelatie.verwijderenRelatie = function(relatie){
			log.debug("verwijderen Relatie met id " + relatie.id());
			$.ajax({
				type: "GET",
				url: '../dejonge/rest/medewerker/gebruiker/verwijderen',
				dataType:'json',
				data: {
					id : ko.utils.unwrapObservable(relatie.id)
				}
			});
			document.location.hash='#lijstRelaties';
		},

		_thisRelatie.naarDetailScherm = function(relatie){
			commonFunctions.verbergMeldingen();
			document.location.hash='#beherenRelatie/' + ko.utils.unwrapObservable(relatie.id);
		}
    };
});