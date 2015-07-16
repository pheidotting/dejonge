define(['jquery',
         'knockout',
         'commons/3rdparty/log',
         'commons/commonFunctions'],
	function ($, ko, log, commonFunctions) {

	return function opmerkingModel (data){
		_this = this;

		_this.id = ko.observable(data.id);
		_this.opmerking = ko.observable(data.opmerking);
		_this.schade = ko.observable(data.schade);
		_this.hypotheek = ko.observable(data.hypotheek);
		_this.polis = ko.observable(data.polis);
		_this.tijd = ko.observable(data.tijd);
		_this.medewerker = ko.observable(data.medewerker);
		_this.soort = ko.computed(function() {
			var soort = "Persoon";
			if(_this.schade() != null){
				soort = "Schade";
			}
			if(_this.hypotheek() != null){
				soort = "Hypotheek";
			}
			if(_this.polis() != null){
				soort = "Polis";
			}
			return soort;
		}, this);

		_this.opslaan = function(opmerking){
			log.debug("Versturen naar ../dejonge/rest/medewerker/opmerking/opslaan");
			log.debug(ko.toJSON(opmerking));
	    	$.ajax({
	            url: '../dejonge/rest/medewerker/opmerking/opslaan',
	            type: 'POST',
	            data: ko.toJSON(opmerking) ,
	            contentType: 'application/json; charset=utf-8',
	            success: function () {
//	    			for (var int = 1; int <= $('#hoeveelFiles').val(); int++) {
//	    				var formData = new FormData($('#schadeMeldForm')[0]);
//	    				uploadBestand(formData, '../dejonge/rest/medewerker/bijlage/uploadSchade' + int + 'File');
//	    			}
	            	commonFunctions.plaatsMelding("De gegevens zijn opgeslagen");
	            	document.location.hash = "#beherenRelatie/" + relatieId + "/schades";
	            },
	            error: function (data) {
	            	commonFunctions.plaatsFoutmelding(data);
	            }
	        });
		};
    };
});