define(['jquery',
         'knockout',
         'commons/3rdparty/log',
         'commons/commonFunctions'],
	function ($, ko, log, commonFunctions) {
    
	return function bedrijfModel (data){
		_this = this;
	
		_this.id = ko.observable(data.id);
		_this.naam = ko.observable(data.naam).extend({required: true});
		_this.kvk = ko.observable(data.kvk);
		_this.straat = ko.observable(data.straat);
		_this.huisnummer = ko.observable(data.huisnummer).extend({ number: true });
		_this.toevoeging = ko.observable(data.toevoeging);
		_this.postcode = ko.observable(data.postcode);
		_this.plaats = ko.observable(data.plaats);
		_this.relatie = ko.observable(data.relatie);

		_this.idDiv = ko.computed(function() {
	        return "collapsable" + data.id;
		}, this);
		_this.idDivLink = ko.computed(function() {
	        return "#collapsable" + data.id;
		}, this);

		_this.bewerkBedrijf = function(bedrijf){
			log.debug("Bewerk Bedrijf : " + ko.toJSON(bedrijf));
			commonFunctions.verbergMeldingen();
	    	document.location.hash = "#beherenRelatie/" + bedrijf.relatie() + "/bedrijf/" + bedrijf.id();
	    };

		_this.opslaan = function(bedrijf){
	    	var result = ko.validation.group(bedrijf, {deep: true});
	    	if(!bedrijf.isValid()){
	    		result.showAllMessages(true);
	    	}else{
				commonFunctions.verbergMeldingen();
				log.debug("Versturen naar ../dejonge/rest/medewerker/gebruiker/opslaanBedrijf : ");
				log.debug(ko.toJSON(_this));
				$.ajax({
					type: "POST",
					url: '../dejonge/rest/medewerker/gebruiker/opslaanBedrijf',
					contentType: "application/json",
			        data: ko.toJSON(bedrijf),
			        success: function () {
			        	commonFunctions.plaatsMelding("De gegevens zijn opgeslagen");
			    		document.location.hash='#beherenRelatie/' + _this.relatie() + '/bedrijven';
			        },
			        error: function (data) {
			        	commonFunctions.plaatsFoutmelding(data);
			        }
				});
			}
		};
    };
});