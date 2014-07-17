function Relatie(data) {
    var self = this;
    
	this.id = ko.observable(data.id);
	this.voornaam = ko.observable(data.voornaam);
	this.achternaam = ko.observable(data.achternaam);
	this.tussenvoegsel = ko.observable(data.tussenvoegsel);
	this.straat = ko.observable(data.straat);
	this.huisnummer = ko.observable(data.huisnummer);
	this.toevoeging = ko.observable(data.toevoeging);
	this.postcode = ko.observable(data.postcode);
	this.plaats = ko.observable(data.plaats);
	this.bsn = ko.observable(data.bsn);
	this.identificatie = ko.observable(data.identificatie);
	this.zakelijkeKlant = ko.observable(data.zakelijkeKlant);
	if(this.zakelijkeKlant){
		$('#tabs').puitabview('enable' , 1);
	}else{
		$('#tabs').puitabview('disable' , 1);
	}
	this.rekeningnummers = ko.observableArray();
	if(data.rekeningnummers != null){
		$.each(data.rekeningnummers, function(i, item) {
			self.rekeningnummers.push(new RekeningNummer(item));
		});
	}
	this.telefoonnummers = ko.observableArray();
	if(data.telefoonnummers != null){
		$.each(data.telefoonnummers, function(i, item) {
			self.telefoonnummers.push(new TelefoonNummer(item));
		});
	}

	this.geboorteDatum = ko.observable(data.geboorteDatumOpgemaakt);
	this.overlijdensdatum = ko.observable(data.overlijdensdatumOpgemaakt);
	this.geslacht = ko.observable(data.geslacht);
	this.burgerlijkeStaat = ko.observable(data.burgerlijkeStaat);

	this.onderlingeRelaties = ko.observableArray();
	/*		if(data.onderlingeRelaties != null){
		$.each(data.onderlingeRelaties, function(i, item) {
			self.onderlingeRelaties.push(new OnderlingeRelatie(item));
 		});
	}
	*/
	this.voegRekeningToe = function() {
        self.rekeningnummers.push(new RekeningNummer(""));
    };
    
    this.verwijderRekening = function(nummer){
    	self.rekeningnummers.remove(nummer);
    }
    
    this.voegTelefoonNummerToe = function() {
    	self.telefoonnummers.push(new TelefoonNummer(""));
    }
    
    this.verwijderTelefoonNummer = function(telefoon) {
    	self.telefoonnummers.remove(telefoon);
    }
    
    
    this.opslaan = function(){
    	verbergMeldingen();
    	if(ko.utils.unwrapObservable(this.geboorteDatum) != null && ko.utils.unwrapObservable(this.geboorteDatum) != ''){
    		this.geboorteDatum(moment(ko.utils.unwrapObservable(this.geboorteDatum), "DD-MM-YYYY").format("YYYY-MM-DD"));
    	}
    	if(ko.utils.unwrapObservable(this.overlijdensdatum) != null && ko.utils.unwrapObservable(this.overlijdensdatum) != ''){
    		this.overlijdensdatum(moment(ko.utils.unwrapObservable(this.overlijdensdatum), "DD-MM-YYYY").format("YYYY-MM-DD"));
    	}
    	$.ajax({
            url: '../dejonge/rest/medewerker/gebruiker/opslaan',
            type: 'POST',
            data: ko.toJSON(this) ,
            contentType: 'application/json; charset=utf-8',
            success: function (response) {
				document.location.hash='#lijstRelaties';
            	plaatsMelding("De gegevens zijn opgeslagen");
            },
            error: function (data) {
            	plaatsFoutmelding(data);
            }
        });
    	if(ko.utils.unwrapObservable(this.geboorteDatum) != null && ko.utils.unwrapObservable(this.geboorteDatum) != ''){
	    	this.geboorteDatum(moment(ko.utils.unwrapObservable(this.geboorteDatum), "YYYY-MM-DD").format("DD-MM-YYYY"));
    	}
    	if(ko.utils.unwrapObservable(this.overlijdensdatum) != null && ko.utils.unwrapObservable(this.overlijdensdatum) != ''){
	    	this.overlijdensdatum(moment(ko.utils.unwrapObservable(this.overlijdensdatum), "YYYY-MM-DD").format("DD-MM-YYYY"));
		}
    }
    
    this.verwijderenRelatie = function(obj){
		$.ajax({
			type: "GET",
			url: '../dejonge/rest/medewerker/gebruiker/verwijderen',
			dataType:'json',
			data: {
				id : ko.utils.unwrapObservable(obj.id)
			}
		});
		document.location.hash='#lijstRelaties';
    }
}

function OnderlingeRelatie(data){
	this.id = ko.observable(data.id);
	this.idNaar = ko.observable(data.idNaar);
	this.metWie = ko.observable(data.metWie);
	this.soort = ko.observable(data.soort);
}

function RekeningNummer(data){
	this.id = ko.observable(data.id);
	this.rekeningnummer = ko.observable(data.rekeningnummer);
	this.bic = ko.observable(data.bic);
}

function TelefoonNummer(data){
	this.id = ko.observable(data.id);
	this.telefoonnummer = ko.observable(data.telefoonnummer);
	this.soort = ko.observable(data.soort);
}

function Bijlage(data){
	var self = this;

	self.url = ko.computed(function() {
        return "../dejonge/rest/medewerker/bijlage/download?bijlageId=" + data.id;
	}, this);
	self.bestandsNaam = ko.observable(data.bestandsNaam);
	self.soortBijlage = ko.observable(data.soortBijlage);
}

function go(log, relatieId, actie, subId){
	log.debug("Ophalen gegevens Relatie met id " + relatieId);
	$.get( "../dejonge/rest/medewerker/gebruiker/lees", {"id" : relatieId}, function(data) {
		log.debug("Gegevens opgehaald, applyBindings");
       	ko.applyBindings(new Relatie(data));
    });
}