define(['jquery',
         'knockout',
         'model/bijlage',
         'commons/3rdparty/log',
         'commons/commonFunctions'],
	function ($, ko, Bijlage, log, commonFunctions) {

	return function aangifteModel (data){
		aangifte = this;

		aangifte.id = ko.observable(data.id);
		aangifte.jaar = ko.observable(data.jaar);
		aangifte.datumAfgerond = ko.observable(data.datumAfgerond);
		aangifte.uitstelTot = ko.observable(data.uitstelTot);
		aangifte.afgerondDoor = ko.observable(data.afgerondDoor);
		aangifte.relatie = ko.observable(data.relatie);
		aangifte.bijlages = ko.observableArray();
		if(data.bijlages != null){
			var bijlages = [];
			$.each(data.bijlages, function(i, item){
				aangifte.bijlages.push(new Bijlage(item));
			});
		}

		aangifte.idDiv = ko.computed(function() {
	        return "collapsable" + data.id;
		}, this);
		aangifte.idDivLink = ko.computed(function() {
	        return "#collapsable" + data.id;
		}, this);

//		aangifte.veranderDatum = function(){
//			log.debug("datum1");
//			log.debug(datum);
//			datum(commonFunctions.zetDatumOm(datum()));
//		}

		aangifte.afronden = function(aangifte){
			$.ajax({
				type: "POST",
				url: '../dejonge/rest/medewerker/aangifte/afronden',
				contentType: "application/json",
				data: JSON.stringify(aangifte.id()),
	            success: function(data) {
	            	document.location.hash = "#beherenRelatie/aangiftes";
	        	},
				error: function (data) {
					commonFunctions.plaatsFoutmelding(data);
				}
	    	});
	    };
	    
	    aangifte.opslaan = function(aangifte){
			aangifte.bijlages([]);
    		commonFunctions.verbergMeldingen();
    		log.debug("versturen naar ../dejonge/rest/medewerker/aangifte/opslaan");
    		log.debug(ko.toJSON(aangifte));
			$.ajax({
				type: "POST",
				url: '../dejonge/rest/medewerker/aangifte/opslaan',
				contentType: "application/json",
	            data: ko.toJSON(aangifte),
	            success: function(data) {
	            	aangifte.id(data);
	    			for (var int = 1; int <= $('#hoeveelFiles').val(); int++) {
	    				var formData = new FormData($('#aangifteForm')[0]);
	    				log.debug("Versturen naar ../dejonge/rest/medewerker/bijlage/uploadAangifte" + int + 'File')
	    				log.debug(JSON.stringify(formData));
	    				uploadBestand(formData, '../dejonge/rest/medewerker/bijlage/uploadAangifte' + int + 'File');
	    			}
	    			commonFunctions.plaatsMelding("De gegevens zijn opgeslagen");
	            	document.location.hash = "#beherenRelatie/" + aangifte.relatie();
	        	},
				error: function (data) {
					commonFunctions.plaatsFoutmelding(data);
				}
	    	});
		};
    };
});