define(['jquery',
        'commons/commonFunctions',
         'knockout',
         'model/rekeningNummer',
         'model/telefoonNummer',
         'model/opmerking',
         'model/adres',
         'moment',
         'commons/3rdparty/log',
         "commons/validation"],
	function ($, commonFunctions, ko, RekeningNummer, TelefoonNummer, Opmerking, Adres, moment, log, validation) {

	return function relatieModel (data){
		_thisRelatie = this;

		_thisRelatie.veranderDatum = function(datum){
			datum(commonFunctions.zetDatumOm(datum()));
		};
		_thisRelatie.zetPostcodeOm = function(){
			var postcode = _thisRelatie.postcode();
			if(postcode != null){
				if(postcode.length == 6){
					postcode = postcode.toUpperCase();
					postcode = postcode.substring(0, 4) + " " + postcode.substring(4);
					_thisRelatie.postcode(postcode);
				}
			}
		};
		_thisRelatie.identificatie = ko.observable(data.identificatie).extend({email: true});
		_thisRelatie.id = ko.observable(data.id);
		_thisRelatie.voornaam = ko.observable(data.voornaam).extend({required: true});
		_thisRelatie.roepnaam = ko.observable(data.roepnaam);
		_thisRelatie.tussenvoegsel = ko.observable(data.tussenvoegsel);
		_thisRelatie.achternaam = ko.observable(data.achternaam).extend({required: true});

		_thisRelatie.adressen = ko.observableArray();
		if(data.adressen != null){
			$.each(data.adressen, function(i, item) {
				_thisRelatie.adressen().push(new Adres(item));
			});
		}

		_thisRelatie.voegAdresToe = function(){
			log.debug("nieuwe Adres");
			_thisRelatie.adressen().push(new Adres(""));
			_thisRelatie.adressen.valueHasMutated();
		}

		_thisRelatie.verwijderAdres = function(adres){
			log.debug(ko.toJSON(adres));
			_thisRelatie.adressen.remove(adres);
			_thisRelatie.adressen.valueHasMutated();
		}

		_thisRelatie.bsn = ko.observable(data.bsn);
		_thisRelatie.zakelijkeKlant = ko.observable(data.zakelijkeKlant);
		_thisRelatie.opmerkingen = ko.observableArray();
		if(data.opmerkingen != null){
			$.each(data.opmerkingen, function(i, item) {
				_thisRelatie.opmerkingen().push(new Opmerking(item));
			});
		}
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
//			_thisRelatie.rekeningnummers().remove(nummer);
			_thisRelatie.rekeningnummers.valueHasMutated();
		};

		_thisRelatie.voegTelefoonNummerToe = function() {
			_thisRelatie.telefoonnummers().push(new TelefoonNummer(""));
			_thisRelatie.telefoonnummers.valueHasMutated();
		};

		_thisRelatie.verwijderTelefoonNummer = function(telefoon) {
//			_thisRelatie.telefoonnummers().remove(telefoon);
			_thisRelatie.telefoonnummers.valueHasMutated();
		};
		
		_thisRelatie.nieuweOpmerking = ko.observable();
		_thisRelatie.opmerkingOpslaan = function(){
			log.debug(_thisRelatie.nieuweOpmerking());
			if(_thisRelatie.nieuweOpmerking() != ''){
		    	$.ajax({
		            url: '/rest/authorisatie/authorisatie/ingelogdeGebruiker',
		            type: 'GET',
		            contentType: 'application/json; charset=utf-8',
		            success: function (response) {
	            		var opmerking = new Opmerking('');
	            		opmerking.medewerker(response.gebruikersnaam);
	            		opmerking.tijd(new moment());
	            		opmerking.opmerking(_thisRelatie.nieuweOpmerking());
	            		
	            		log.debug(ko.toJSON(opmerking));

	            		_thisRelatie.opmerkingen().push(opmerking);
//	            		_thisRelatie.valueHasMutated();
	            		_thisRelatie.nieuweOpmerking('');
	            		
		            }
		    	});
			}
	    };

		_thisRelatie.opslaan = function(){
	    	var result = ko.validation.group(_thisRelatie, {deep: true});
	    	if(!_thisRelatie.isValid()){
	    		result.showAllMessages(true);
	    	}else{
				commonFunctions.verbergMeldingen();
				log.debug("Versturen naar ../dejonge/rest/medewerker/gebruiker/opslaan : ");
				log.debug(ko.toJSON(_thisRelatie));
				var foutmelding;
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
						foutmelding = true;
					}
				});
				if(foutmelding == undefined || foutmelding == null){
					document.location.hash='#lijstRelaties';
					commonFunctions.plaatsMelding("De gegevens zijn opgeslagen");
				}
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

        _thisRelatie.opzoekenAdres = function(adres){
            log.debug(ko.toJSON(adres));
            adres.postcode(adres.postcode().toUpperCase().replace(" ", ""));
            $.ajax({
                type: "GET",
                url: 'http://api.postcodeapi.nu/' + adres.postcode() + '/' + adres.huisnummer(),
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                headers: { 'Api-Key': '0eaff635fe5d9be439582d7501027f34d5a3ca9d'},
                success: function (data) {
                    log.debug(JSON.stringify(data));
                    adres.straat(data.resource.street);
                    adres.plaats(data.resource.town);
                    adres.postcode(adres.zetPostcodeOm(adres.postcode()));
                },
                error: function(data) {
                    log.debug(JSON.stringify(data));
                    adres.straat('');
                    adres.plaats('');
                }
            });
        };
    };
});