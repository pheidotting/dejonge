define(['jquery',
        'commons/commonFunctions',
         'knockout', 
         'model/rekeningNummer',
         'model/telefoonNummer',
         'moment',
         'commons/3rdparty/log'],
	function ($, commonFunctions, ko, RekeningNummer, TelefoonNummer, moment, log) {
    
	return function relatieModel (data){
		_this = this;
	
		_this.identificatie = ko.observable(data.identificatie).extend({required: true, email: true});
		_this.id = ko.observable(data.id);
		_this.voornaam = ko.observable(data.voornaam).extend({required: true});
		_this.achternaam = ko.observable(data.achternaam).extend({required: true});
		_this.tussenvoegsel = ko.observable(data.tussenvoegsel);
		_this.straat = ko.observable(data.straat).extend({ required: true });
		_this.huisnummer = ko.observable(data.huisnummer).extend({ number: true, required: true });
		_this.toevoeging = ko.observable(data.toevoeging);
		_this.postcode = ko.observable(data.postcode);
		_this.plaats = ko.observable(data.plaats);
		_this.bsn = ko.observable(data.bsn);
		_this.zakelijkeKlant = ko.observable(data.zakelijkeKlant);
		_this.rekeningnummers = ko.observableArray();
		if(data.rekeningnummers != null){
			$.each(data.rekeningnummers, function(i, item) {
				_this.rekeningnummers().push(new RekeningNummer(item));
			});
		}
		_this.telefoonnummers = ko.observableArray();
		if(data.telefoonnummers != null){
			$.each(data.telefoonnummers, function(i, item) {
				_this.telefoonnummers().push(new TelefoonNummer(item));
			});
		}
		_this.geboorteDatum = ko.observable(data.geboorteDatumOpgemaakt).extend({validation: {
	        validator: function (val) {
	        	if(moment(val, "DD-MM-YYYY").format("DD-MM-YYYY") == "Invalid date"){
	    			return false;
	    		}else{
	    			return true;
	    		}
	        },
	        message: 'Juiste invoerformaat is : dd-mm-eejj'
	    }});
		_this.overlijdensdatum = ko.observable(data.overlijdensdatumOpgemaakt);
		_this.geslacht = ko.observable(data.geslacht);
		_this.burgerlijkeStaat = ko.observable(data.burgerlijkeStaat);
		_this.voegRekeningToe = function() {
			_this.rekeningNummers().push(new RekeningNummer(""));
			_this.rekeningNummers.valueHasMutated();
		};
		
		_this.verwijderRekening = function(nummer){
			_this.rekeningNummers().remove(nummer);
			_this.rekeningNummers.valueHasMutated();
		};
		
		_this.voegTelefoonNummerToe = function() {
			_this.telefoonnummers().push(new TelefoonNummer(""));
			_this.telefoonnummers.valueHasMutated();
		};
		
		_this.verwijderTelefoonNummer = function(telefoon) {
			_this.telefoonnummers().remove(telefoon);
			_this.telefoonnummers.valueHasMutated();
		};
		
		_this.opslaan = function(){
	    	var result = ko.validation.group(_this, {deep: true});
	    	if(!_this.isValid()){
	    		result.showAllMessages(true);
	    	}else{
				commonFunctions.verbergMeldingen();
				if(_this.geboorteDatum() != null && _this.geboorteDatum() != ''){
					_this.geboorteDatum(moment(_this.geboorteDatum(), "DD-MM-YYYY").format("YYYY-MM-DD"));
				}
				if(_this.overlijdensdatum() != null && _this.overlijdensdatum() != ''){
					_this.overlijdensdatum(moment(_this.overlijdensdatum(), "DD-MM-YYYY").format("YYYY-MM-DD"));
				}
				log.debug("Versturen naar ../dejonge/rest/medewerker/gebruiker/opslaan : ");
				log.debug(ko.toJSON(_this));
				$.ajax({
					url: '../dejonge/rest/medewerker/gebruiker/opslaan',
					type: 'POST',
					data: ko.toJSON(_this) ,
					contentType: 'application/json; charset=utf-8',
					success: function (response) {
						document.location.hash='#lijstRelaties';
						commonFunctions.plaatsMelding("De gegevens zijn opgeslagen");
					},
					error: function (data) {
						commonFunctions.plaatsFoutmelding(data);
					}
				});
				if(_this.geboorteDatum() != null && _this.geboorteDatum() != ''){
					_this.geboorteDatum(moment(_this.geboorteDatum(), "YYYY-MM-DD").format("DD-MM-YYYY"));
				}
				if(_this.overlijdensdatum() != null && _this.overlijdensdatum() != ''){
					_this.overlijdensdatum(moment(_this.overlijdensdatum(), "YYYY-MM-DD").format("DD-MM-YYYY"));
				}
	    	}
		};
	
		_this.verwijderenRelatie = function(relatie){
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
		
		_this.naarDetailScherm = function(relatie){
			commonFunctions.verbergMeldingen();
			document.location.hash='#beherenRelatie/' + ko.utils.unwrapObservable(relatie.id);
		}
    };
});