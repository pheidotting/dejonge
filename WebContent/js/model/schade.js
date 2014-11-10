define(['jquery',
         'knockout',
         'commons/3rdparty/log',
         'commons/commonFunctions',
         'moment',
         'model/bijlage',
         'model/opmerking',
         "commons/opmaak"],
	function ($, ko, log, commonFunctions, moment, Bijlage, Opmerking, opmaak) {

	return function schadeModel (data){
		self = this;

		self.bedrag = function(bedrag){
			log.debug("opmaken bedrag " + bedrag());
			return opmaak.maakBedragOp(bedrag());
		};
	    self.id = ko.observable(data.id);
	    self.relatie = ko.observable(data.relatie);
	    self.polis = ko.observable(data.polis).extend({validation: {
	        validator: function (val) {
	        	if(ko.utils.unwrapObservable(self.polis) == "Kies een polis uit de lijst.."){
					if(self.schadeNummerMaatschappij.isValid()){
	        			return false;
	        		}else{
	        			return true;
	        		}
	    		}else{
	    			return true;
	    		}
	        },
	        message: 'Dit veld is verplicht.'
	    }});
	    self.schadeNummerMaatschappij = ko.observable(data.schadeNummerMaatschappij).extend({required: true});
	    self.schadeNummerTussenPersoon = ko.observable(data.schadeNummerTussenPersoon);
	    self.soortSchade = ko.observable(data.soortSchade).extend({required: true});
	    self.locatie = ko.observable(data.locatie);
	    self.statusSchade = ko.observable(data.statusSchade).extend({validation: {
	        validator: function (val) {
	        	if(ko.utils.unwrapObservable(self.statusSchade) == "Kies een status uit de lijst.."){
					if(self.schadeNummerMaatschappij.isValid()){
	        			return false;
	        		}else{
	        			return true;
	        		}
	    		}else{
	    			return true;
	    		}
	        },
	        message: 'Dit veld is verplicht.'
	    }});
	    self.datumTijdSchade = ko.observable(data.datumTijdSchade).extend({required: true, validation: {
	        validator: function (val) {
	        	if(moment(val, "DD-MM-YYYY HH:mm").format("DD-MM-YYYY HH:mm") == "Invalid date"){
	    			return false;
	    		}else{
	    			return true;
	    		}
	        },
	        message: 'Juiste invoerformaat is : dd-mm-eejj uu:mm'
	    }});
	    self.datumTijdMelding = ko.observable(data.datumTijdMelding).extend({required: true, validation: {
	        validator: function (val) {
	        	if(moment(val, "DD-MM-YYYY HH:mm").format("DD-MM-YYYY HH:mm") == "Invalid date"){
	    			return false;
	    		}else{
	    			return true;
	    		}
	        },
	        message: 'Juiste invoerformaat is : dd-mm-eejj uu:mm'
	    }});
	    self.datumAfgehandeld = ko.observable(data.datumAfgehandeld).extend({validation: {
	        validator: function (val) {
	        	if(val != undefined && val != ""){
		        	if(moment(val, "DD-MM-YYYY").format("DD-MM-YYYY") == "Invalid date"){
		    			return false;
		    		}else{
		    			return true;
		    		}
	        	}else{
	        		return true;
	        	}
	        },
	        message: 'Juiste invoerformaat is : dd-mm-eejj'
	    }});;
	    self.eigenRisico = ko.observable(data.eigenRisico).extend({number: true});
	    self.omschrijving = ko.observable(data.omschrijving);

	    self.titel = ko.computed(function() {
	    	return data.soortSchade + " (" + data.schadeNummerMaatschappij + ")";
	    }, this);

		self.idDiv = ko.computed(function() {
	        return "collapsableSchade" + data.id;
		}, this);
		self.idDivLink = ko.computed(function() {
	        return "#collapsableSchade" + data.id;
		}, this);

	    this.opmerkingen = ko.observableArray();
			if(data.opmerkingen != null){
			$.each(data.opmerkingen, function(i, item){
				self.opmerkingen.push(new Opmerking(item));
			});
		}

		self.bijlages = ko.observableArray();
		if(data.bijlages != null){
			$.each(data.bijlages, function(i, item){
				self.bijlages.push(new Bijlage(item));
			});
		}

		self.opslaan = function(schade){
	    	var result = ko.validation.group(schade, {deep: true});
	    	if(!schade.isValid()){
	    		result.showAllMessages(true);
	    	}else{
	    		log.debug("Versturen : " + ko.toJSON(schade));
		    	$.ajax({
		            url: '../dejonge/rest/medewerker/schade/opslaan',
		            type: 'POST',
		            data: ko.toJSON(schade) ,
		            contentType: 'application/json; charset=utf-8',
		            success: function () {
		    			for (var int = 1; int <= $('#hoeveelFiles').val(); int++) {
		    				var formData = new FormData($('#schadeMeldForm')[0]);
		    				uploadBestand(formData, '../dejonge/rest/medewerker/bijlage/uploadSchade' + int + 'File');
		    			}
		            	commonFunctions.plaatsMelding("De gegevens zijn opgeslagen");
		            	document.location.hash = "#beherenRelatie/" + schade.relatie() + "/schades";
		            },
		            error: function (data) {
		            	commonFunctions.plaatsFoutmelding(data);
		            }
		        });
	    	}
		};

	    self.bewerkSchade = function(schade){
	    	document.location.hash = "#beherenRelatie/" + schade.relatie() + "/schade/" + schade.id();
	    };

	    self.plaatsOpmerking = function(schade){
	    	document.location.hash = "#beherenRelatie/" + schade.relatie() + "/opmerking/s" + schade.id();
	    };
	};
});