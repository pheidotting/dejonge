function go(log, relatieId, actie, subId){
	$.getScript("pages/beherenRelatie/details/bijlages.js", function() {
		$.get( "../dejonge/rest/medewerker/schade/lijst", {"relatieId" : relatieId}, function(data) {
			log.debug("Gegevens opgehaald, applyBindings");
			ko.validation.registerExtenders();
	       	ko.applyBindings(new Schades(data, log, relatieId));
		});
	});
}

function Schade(data, log, relatieId){
	var self = this;

    self.id = ko.observable(data.id);
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
        	if(moment(val, "DD-MM-YYYY").format("DD-MM-YYYY") == "Invalid date"){
    			return false;
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
	            	plaatsMelding("De gegevens zijn opgeslagen");
	            	document.location.hash = "#beherenRelatie/" + relatieId + "/schades";
	            },
	            error: function (data) {
	            	plaatsFoutmelding(data);
	            }
	        });
    	}
	};

    self.bewerkSchade = function(schade){
    	document.location.hash = "#beherenRelatie/" + relatieId + "/schade/" + ko.utils.unwrapObservable(schade.id);
    };
   
    self.plaatsOpmerking = function(schade){
    	document.location.hash = "#beherenRelatie/" + relatieId + "/opmerking/s" + ko.utils.unwrapObservable(schade.id);
    };
}

function Opmerking(data){
	var self = this;

	self.id = ko.observable(data.id);
	self.opmerking = ko.observable(data.opmerking);
	self.medewerker = ko.observable(data.medewerker);
	self.tijd = ko.observable(data.tijd);
}

function Schades(data, log, relatieId){
	var self = this;

	self.schades = ko.observableArray();
	$.each(data, function(i, item){
		self.schades.push(new Schade(item, log, relatieId));
	});

	self.verwijderPolis = function(schade){
		var r=confirm("Weet je zeker dat je deze schade wilt verwijderen?");
		if (r==true) {
			self.schades.remove(schade);
			$.get( "../dejonge/rest/medewerker/schade/verwijder", {"id" : ko.utils.unwrapObservable(schade.id)}, function() {});
		}
    };
}