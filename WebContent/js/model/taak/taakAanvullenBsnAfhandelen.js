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
		_thisTaak.bsn = ko.observable(data.bsn);

		$.get( "../dejonge/rest/medewerker/gebruiker/lees", {"id" : data.gerelateerdAan}, function(data) {
			_thisTaak.relatie(data);
			_thisTaak.bsn(data.bsn);
		});

		_thisTaak.taakId = ko.observable(data.id);

		_thisTaak.verwijderFoutMelding = function(){
			commonFunctions.verbergMeldingen();
		};

		_thisTaak.opslaan = function(){
			block.block();

			_thisTaak.relatie().bsn = _thisTaak.bsn();
			
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