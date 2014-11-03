define([ "commons/3rdparty/log",
         "commons/validation",
         "pages/beherenRelatie/details/model/hypotheek"],
         function(log, validation, hypotheek) {

	return function hypotheken(data) {
		var _this = this;

		_this.hypotheken = ko.observableArray();
		$.each(data, function(i, item){
			_this.hypotheken.push(new hypotheek(item));
		});
	    _this.verwijderHypotheek = function(hypotheek){
			verbergMeldingen();
			var r=confirm("Weet je zeker dat je deze hypotheek wilt verwijderen?");
			if (r==true) {
				_this.hypotheken.remove(hypotheek);
				$.get( "../dejonge/rest/medewerker/hypotheek/verwijder", {"id" : ko.utils.unwrapObservable(hypotheek.id)}, function() {});
			}
	    };
	    self.plaatsOpmerking = function(schade){
	    	document.location.hash = "#beherenRelatie/" + _relatieId + "/opmerking/h" + ko.utils.unwrapObservable(schade.id);
	    };
	};
});