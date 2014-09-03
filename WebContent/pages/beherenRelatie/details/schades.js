function go(log, relatieId, actie, subId){
	$.get( "../dejonge/rest/medewerker/schade/lijst", {"relatieId" : relatieId}, function(data) {
		log.debug("Gegevens opgehaald, applyBindings");
       	ko.applyBindings(new Schades(data, log, relatieId));
    });
}

function Schade(data, log, relatieId){
	var self = this;

    self.id = ko.observable(data.id);
    self.polis = ko.observable(data.polis);
    self.schadeNummerMaatschappij = ko.observable(data.schadeNummerMaatschappij);
    self.schadeNummerTussenPersoon = ko.observable(data.schadeNummerTussenPersoon);
    self.soortSchade = ko.observable(data.soortSchade);
    self.locatie = ko.observable(data.locatie);
    self.statusSchade = ko.observable(data.statusSchade);
    self.datumTijdSchade = ko.observable(data.datumTijdSchade);
    self.datumTijdMelding = ko.observable(data.datumTijdMelding);
    self.datumAfgehandeld = ko.observable(data.datumAfgehandeld);
    self.eigenRisico = ko.observable(data.eigenRisico);
    self.omschrijving = ko.observable(data.omschrijving);

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
	};

    self.bewerkSchade = function(schade){
    	document.location.hash = "#beherenRelatie/" + relatieId + "/schade/" + ko.utils.unwrapObservable(schade.id);
    };
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