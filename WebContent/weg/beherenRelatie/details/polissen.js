function go(log, relatieId, actie, subId){
	$.getScript("pages/beherenRelatie/details/bijlages.js", function() {
		$.get( "../dejonge/rest/medewerker/polis/lijst", {"relatieId" : relatieId}, function(data) {
			log.debug("Gegevens opgehaald, applyBindings");
			ko.validation.registerExtenders();
	       	ko.applyBindings(new Polissen(data, log, relatieId));
	    });
	});
}

function Polis(data, log, relatieId){
	var self = this;

	self.relatie = relatieId;
	self.id = ko.observable(data.id);
	self.omschrijving = ko.observable(data.omschrijving);
	self.polisNummer = ko.observable(data.polisNummer).extend({required: true});
	if(data.ingangsDatum != undefined){
		self.ingangsDatum = ko.observable(moment(data.ingangsDatum).format("DD-MM-YYYY")).extend({required: true});
	}else{
		self.ingangsDatum = ko.observable().extend({required: true});
	}
	if(data.eindDatum != undefined){
		self.eindDatum = ko.observable(moment(data.eindDatum).format("DD-MM-YYYY"));
	}else{
		self.eindDatum = ko.observable();
	}
	if(data.wijzigingsDatum != undefined){
		self.wijzigingsDatum = ko.observable(moment(data.wijzigingsDatum).format("DD-MM-YYYY"));
	}else{
		self.wijzigingsDatum = ko.observable();
	}
	if(data.prolongatieDatum != undefined){
		self.prolongatieDatum = ko.observable(moment(data.prolongatieDatum).format("DD-MM-YYYY"));
	}else{
		self.prolongatieDatum = ko.observable();
	}
	self.maatschappij = ko.observable(data.maatschappij);
	self.soort = ko.observable(data.soort);
	self.premie = ko.observable(data.premie);
	self.betaalfrequentie = ko.observable(data.betaalfrequentie);
	self.bedrijf = ko.observable(data.bedrijf);
	self.idDiv = ko.computed(function() {
        return "collapsable" + data.id;
	}, this);
	self.idDivLink = ko.computed(function() {
        return "#collapsable" + data.id;
	}, this);
	self.className = ko.computed(function() {
		var datum = moment(data.ingangsDatum);
		var tijd = moment(datum).fromNow();
		if(tijd.substr(tijd.length - 3) == "ago"){
		}else{
			return "polisNietActief panel-title";
		}
		if(data.eindDatum){
			datum = moment(data.eindDatum);
			tijd = moment(datum).fromNow();
			if(tijd.substr(tijd.length - 3) == "ago"){
				return "polisBeeindigd panel-title";
			}else{
				return "panel-title";
			}
		}
	}, this);
	self.titel = ko.computed(function () {
		return data.soort + " (" + data.polisNummer + ")";
	}, this);
	self.bijlages = ko.observableArray();
	if(data.bijlages != null){
		$.each(data.bijlages, function(i, item){
			self.bijlages.push(new Bijlage(item));
		});
	};

    self.schadeMeldenBijPolis = function(polis){
		log.debug(ko.utils.unwrapObservable(polis.id));
		log.debug($('#polisVoorSchademelding').val());
    };

    self.bewerkPolis = function(polis){
		verbergMeldingen();
    	document.location.hash = "#beherenRelatie/" + relatieId + "/polis/" + ko.utils.unwrapObservable(polis.id);
    };
    
    self.beeindigPolis = function(polis){
		$.get('../dejonge/rest/medewerker/polis/beeindigen', {"id" : polis.id()});
		self.eindDatum(moment().format("DD-MM-YYYY"));
    }

    self.opslaan = function(polis){
    	var result = ko.validation.group(polis, {deep: true});
    	if(!polis.isValid()){
    		result.showAllMessages(true);
    	}else{
	    	verbergMeldingen();
			$.ajax({
				type: "POST",
				url: '../dejonge/rest/medewerker/polis/opslaan',
				contentType: "application/json",
	            data: ko.toJSON(polis),
	            success: function() {
	    			for (var int = 1; int <= $('#hoeveelFiles').val(); int++) {
	    				var formData = new FormData($('#polisForm')[0]);
	    				uploadBestand(formData, '../dejonge/rest/medewerker/bijlage/uploadPolis' + int + 'File');
	    			}
	            	plaatsMelding("De gegevens zijn opgeslagen");
	            	document.location.hash = "#beherenRelatie/" + relatieId + "/polissen";
	        	},
				error: function (data) {
					plaatsFoutmelding(data);
				}
	    	});
    	}
	};
}

function Polissen(data, log, relatieId){
	var self = this;

	self.polissen = ko.observableArray();
	$.each(data, function(i, item){
		self.polissen.push(new Polis(item, log, relatieId));
	});
	
    self.verwijderPolis = function(polis){
		verbergMeldingen();
		var r=confirm("Weet je zeker dat je deze polis wilt verwijderen?");
		if (r==true) {
			self.polissen.remove(polis);
			$.get( "../dejonge/rest/medewerker/polis/verwijder", {"id" : ko.utils.unwrapObservable(polis.id)}, function() {});
		}
    };
}