define([ "commons/3rdparty/log",
         "commons/validation",
         "commons/opmaak",
         "pages/beherenRelatie/details/model/bijlage"],
//         "commons/3rdparty/knockout",
//         "commons/3rdparty/knockoutValidation/knockout.validation.min"],
         function(logger, validation, opmaak, bijlage) {
	
	return function hypotheek(data) {
		_this = this;
		
		_this.soortenHypotheek = ko.observableArray();

		_this.soorten = function(){
			return $.ajax({
				type: "GET",
				async: false,
				url: '../dejonge/rest/medewerker/hypotheek/alleSoortenHypotheek',	
				dataType:'json',
				success: function (data) {
					$.each(data, function(i, item) {
						_this.soortenHypotheek.push(new SoortHypotheek(item));
					});
					
					return data;
				}
			});
		};
		_this.soorten();

		_this.bedrag = function(bedrag){
			return opmaak.maakBedragOp(ko.utils.unwrapObservable(bedrag));
		};
		
		_this.id = ko.observable(data.id);
		_this.bank = ko.observable(data.bank);
		_this.bankId = ko.observable(data.bankId);
		_this.boxI = ko.observable(data.boxI).extend({number: true});
		_this.boxIII = ko.observable(data.boxIII).extend({number: true});
		_this.relatie = ko.observable(_relatieId);
		_this.hypotheekVorm = ko.observable(data.hypotheekVorm).extend({required: true});
		_this.hypotheekBedrag = ko.observable(data.hypotheekBedrag).extend({required: true, number: true});
		_this.rente = ko.observable(data.rente).extend({required: true, number: true});
		_this.marktWaarde = ko.observable(data.marktWaarde).extend({number: true});
		_this.onderpand = ko.observable(data.onderpand);
		_this.koopsom = ko.observable(data.koopsom).extend({number: true});
		_this.vrijeVerkoopWaarde = ko.observable(data.vrijeVerkoopWaarde).extend({number: true});
		_this.taxatieDatum = ko.observable(data.taxatieDatum);
		_this.wozWaarde = ko.observable(data.wozWaarde).extend({number: true});
		_this.waardeVoorVerbouwing = ko.observable(data.waardeVoorVerbouwing).extend({number: true});
		_this.waardeNaVerbouwing = ko.observable(data.waardeNaVerbouwing).extend({number: true});
		_this.ingangsDatum = ko.observable(data.ingangsDatum).extend({validation: {
	        validator: function (val) {
	        	return validation.valideerDatum(val);
	        },
	        message: 'Juiste invoerformaat is : dd-mm-eejj'
	    }});
		_this.eindDatum = ko.observable(data.eindDatum).extend({validation: {
	        validator: function (val) {
	        	return validation.valideerDatum(val);
	        },
	        message: 'Juiste invoerformaat is : dd-mm-eejj'
	    }});;
		_this.duur = ko.observable(data.duur).extend({number: true});
		_this.ingangsDatumRenteVastePeriode = ko.observable(data.ingangsDatumRenteVastePeriode).extend({validation: {
	        validator: function (val) {
	        	return validation.valideerDatum(val);
	        },
	        message: 'Juiste invoerformaat is : dd-mm-eejj'
	    }});
		_this.eindDatumRenteVastePeriode = ko.observable(data.eindDatumRenteVastePeriode).extend({validation: {
	        validator: function (val) {
	        	return validation.valideerDatum(val);
	        },
	        message: 'Juiste invoerformaat is : dd-mm-eejj'
	    }});
		_this.duurRenteVastePeriode = ko.observable(data.duurRenteVastePeriode).extend({number: true});
		_this.leningNummer = ko.observable(data.leningNummer);
		_this.omschrijving = ko.observable(data.omschrijving);
		_this.idDiv = ko.computed(function() {
	        return "collapsable" + data.id;
		}, this);
		_this.idDivLink = ko.computed(function() {
	        return "#collapsable" + data.id;
		}, this);
		_this.maakRenteOp = function(rente) {
			return rente() + '%';
		};
		_this.className = ko.computed(function() {
			var datum = moment(data.ingangsDatum);
			var tijd = moment(datum).fromNow();
			if(tijd.substr(tijd.length - 3) == "ago"){
				return "panel-title";
			}else{
				return "polisNietActief panel-title";
			}
		}, this);
		_this.hypotheekVormOpgemaakt = ko.computed(function() {
			var hypVorm;
			
			$.each(_this.soortenHypotheek(), function(i, soort){
				if(data.hypotheekVorm == soort.id()){
					hypVorm = soort.omschrijving();
				}
			});
			
			return hypVorm;
		}, this);
		_this.titel = ko.computed(function() {
			var hypVorm;
			
			$.each(_this.soortenHypotheek(), function(i, soort){
				if(data.hypotheekVorm == soort.id()){
					hypVorm = soort.omschrijving();
				}
				
			});
			var omschrijving = "";
			if(data.leningNummer != null){
				omschrijving += data.leningNummer + " - ";
			}
			omschrijving += hypVorm + " - ";
			if(data.bank != null){
				omschrijving += data.bank + " - ";
			}
			omschrijving += data.rente + "% - ";
			omschrijving += _this.bedrag(data.hypotheekBedrag);
			
			return omschrijving;
		}, this);
	    _this.opmerkingen = ko.observableArray();
		if(data.opmerkingen != null){
			$.each(data.opmerkingen, function(i, item){
				_this.opmerkingen.push(new Opmerking(item));
			});
		}
		_this.bijlages = ko.observableArray();
		if(data.bijlages != null){
			var bijlages = [];
			$.each(data.bijlages, function(i, item){
				_this.bijlages.push(new bijlage(item));
			});
		}
		_this.gekoppeldeHypotheek = ko.observable();
		
		_this.opslaan = function(hypotheek){
	    	var result = ko.validation.group(hypotheek, {deep: true});
	    	if(!hypotheek.isValid()){
	    		result.showAllMessages(true);
	    	}else{
	    		logger.debug("Versturen : " + ko.toJSON(hypotheek));
		    	$.ajax({
		            url: '../dejonge/rest/medewerker/hypotheek/opslaan',
		            type: 'POST',
		            data: ko.toJSON(hypotheek) ,
		            contentType: 'application/json; charset=utf-8',
		            success: function (data) {
		            	_this.id(data.foutmelding);
		    			for (var int = 1; int <= $('#hoeveelFiles').val(); int++) {
		    				var formData = new FormData($('#hypotheekForm')[0]);
		    				uploadBestand(formData, '../dejonge/rest/medewerker/bijlage/uploadHypotheek' + int + 'File');
		    			}
		            	plaatsMelding("De gegevens zijn opgeslagen");
		            	document.location.hash = "#beherenRelatie/" + _relatieId + "/hypotheken";
		            },
		            error: function (data) {
		            	plaatsFoutmelding(data);
		            }
		        });
	    	}
		};

	    self.bewerkHypotheek = function(hypotheek){
			verbergMeldingen();
	    	document.location.hash = "#beherenRelatie/" + _relatieId + "/hypotheek/" + ko.utils.unwrapObservable(hypotheek.id);
	    };

		_this.berekenEinddatumLening = function(hypotheek){
			if(ko.utils.unwrapObservable(_this.duur()) != null && ko.utils.unwrapObservable(_this.duur()) != "" && ko.utils.unwrapObservable(_this.ingangsDatum()) != null && ko.utils.unwrapObservable(_this.ingangsDatum()) != ""){
				var duur = parseInt(ko.utils.unwrapObservable(_this.duur()));
				_this.eindDatum(moment(_this.ingangsDatum(), "DD-MM-YYYY").add(duur, 'y').format("DD-MM-YYYY"));
			}
		}

		_this.berekenEinddatumRenteVastePeriode = function(hypotheek){
			if(ko.utils.unwrapObservable(_this.duurRenteVastePeriode()) != null && ko.utils.unwrapObservable(_this.duurRenteVastePeriode()) != "" && ko.utils.unwrapObservable(_this.ingangsDatumRenteVastePeriode()) != null && ko.utils.unwrapObservable(_this.ingangsDatumRenteVastePeriode()) != ""){
				var duur = parseInt(ko.utils.unwrapObservable(_this.duurRenteVastePeriode()));
				_this.eindDatumRenteVastePeriode(moment(_this.ingangsDatumRenteVastePeriode(), "DD-MM-YYYY").add(duur, 'y').format("DD-MM-YYYY"));
			}
		}

	};

	function SoortHypotheek(data){
		var _this = this;
		
		_this.id = ko.observable(data.id);
		_this.omschrijving = ko.observable(data.omschrijving);
	}
	
	function Opmerking(data){
		var self = this;

		self.id = ko.observable(data.id);
		self.opmerking = ko.observable(data.opmerking);
		self.medewerker = ko.observable(data.medewerker);
		self.tijd = ko.observable(data.tijd);
	}
	
//	function Bijlage(data){
//		var self = this;
//
//		self.id = ko.observable(data.id);
//		self.url = ko.computed(function() {
//	        return "../dejonge/rest/medewerker/bijlage/download?bijlageId=" + data.id;
//		}, this);
//		self.bestandsNaam = ko.observable(data.bestandsNaam);
//		self.soortBijlage = ko.observable(data.soortBijlage);
//		self.parentId = ko.observable(data.parentId);
//		self.tonen = ko.computed(function() {
//			return ko.utils.unwrapObservable(self.soortBijlage) + " (" + ko.utils.unwrapObservable(self.parentId) + ")";
//		},this);
//	}
});