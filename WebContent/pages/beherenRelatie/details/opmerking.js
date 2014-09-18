function go(log, relatieId, actie, subId){
	var schadeId = subId.replace('s', '');

	log.debug("ko.applyBindings, schadeId : " + schadeId);

	$.get( "../dejonge/rest/medewerker/opmerking/nieuw", {}, function(data) {
		log.debug("Gegevens opgehaald, applyBindings");
		ko.applyBindings(new Opmerking(data, log, schadeId, relatieId));
	});
	log.debug("ko.applyBindings done");
}

function Opmerking(data, log, schadeId, relatieId){
	var _this = this;

	_this.id = ko.observable();
	_this.opmerking = ko.observable();
	_this.schade = ko.observable(schadeId);

	_this.opslaan = function(opmerking){
		log.debug(ko.toJSON(opmerking));
    	$.ajax({
            url: '../dejonge/rest/medewerker/opmerking/opslaan',
            type: 'POST',
            data: ko.toJSON(opmerking) ,
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
}