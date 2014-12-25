define([ "commons/3rdparty/log",
         "commons/validation",
         "commons/opmaak",
         'commons/commonFunctions',
         "knockout",
         'commons/block',
         'model/relatie',
         'js/model/taak/afhandelenTaak'],
     function(logger, validation, opmaak, commonFunctions, ko, block, Relatie, AfhandelenTaak) {

	return function taakAanvullenAdresAfhandelen(data) {
		_thisTaak = this;

		_thisTaak.relatie = ko.observable('');
		_thisTaak.straat = ko.observable(data.straat).extend({required: true});
		_thisTaak.huisnummer = ko.observable(data.huisnummer).extend({required: true, number: true});
		_thisTaak.toevoeging = ko.observable(data.toevoeging);
		_thisTaak.postcode = ko.observable(data.postcode).extend({required: true});
		_thisTaak.plaats = ko.observable(data.plaats).extend({required: true});

		$.get( "../dejonge/rest/medewerker/gebruiker/lees", {"id" : data.gerelateerdAan}, function(data) {
			_thisTaak.relatie(data);
			_thisTaak.straat(data.straat);
			_thisTaak.huisnummer(data.huisnummer);
			_thisTaak.toevoeging(data.toevoeging);
			_thisTaak.postcode(data.postcode);
			_thisTaak.plaats(data.plaats);
		});

		_thisTaak.taakId = ko.observable(data.id);
		
		_thisTaak.opzoekenAdres = function(){
			_thisTaak.postcode(_thisTaak.postcode().toUpperCase());
			$.ajax({
				type: "GET",
				url: 'http://api.postcodeapi.nu/' + _thisTaak.postcode() + '/' + _thisTaak.huisnummer(),
				contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
				headers: { 'Api-Key': '0eaff635fe5d9be439582d7501027f34d5a3ca9d'},
		        success: function (data) {
		        	logger.debug(JSON.stringify(data));
		        	_thisTaak.straat(data.resource.street);
		        	_thisTaak.plaats(data.resource.town);
		        },
		        error: function(data) {
		        	logger.debug(JSON.stringify(data));
		        	_thisTaak.straat('');
		        	_thisTaak.plaats('');
		        }
			});
		};
		
		_thisTaak.opslaan = function(){
			block.block();

			_thisTaak.relatie().straat = _thisTaak.straat();
			_thisTaak.relatie().huisnummer = _thisTaak.huisnummer();
			_thisTaak.relatie().toevoeging = _thisTaak.toevoeging();
			_thisTaak.relatie().postcode = _thisTaak.postcode();
			_thisTaak.relatie().plaats = _thisTaak.plaats();
			
			logger.debug("Versturen naar ../dejonge/rest/medewerker/gebruiker/opslaan : ");
			logger.debug(ko.toJSON(_thisTaak.relatie()));
			$.ajax({
				url: '../dejonge/rest/medewerker/gebruiker/opslaan',
				type: 'POST',
				data: ko.toJSON(_thisTaak.relatie()) ,
				contentType: 'application/json; charset=utf-8',
				success: function (response) {
					var afhandelenTaak = new AfhandelenTaak(_thisTaak.taakId());
					
					logger.debug("Versturen naar ../dejonge/rest/medewerker/taak/afhandelen : ");
					logger.debug(ko.toJSON(afhandelenTaak));
					$.ajax({
						url: '../dejonge/rest/medewerker/taak/afhandelen',
						type: 'POST',
						data: ko.toJSON(afhandelenTaak) ,
						contentType: 'application/json; charset=utf-8',
						success: function (response) {
							document.location.hash='#taken';
							commonFunctions.plaatsMelding("De gegevens zijn opgeslagen");
						},
						error: function (data) {
							commonFunctions.plaatsFoutmelding(data);
						}
					});
				},
				error: function (data) {
					commonFunctions.plaatsFoutmelding(data);
				}
			});

		};
		
		_thisTaak.terug = function(){
			document.location.hash = "#taken";
		};
	};
});