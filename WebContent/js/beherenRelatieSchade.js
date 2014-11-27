define(['jquery',
        "knockout",
        'model/schade',
        'commons/block',
        'commons/3rdparty/log',
        'commons/commonFunctions'],
     function($, ko, Schade, block, log, commonFunctions) {

	return function(polisId, relatieId) {
		block.block();
		$.get( "../dejonge/rest/medewerker/polis/lijst", {"relatieId" : relatieId}, function(data) {
			var $select = $('#polisVoorSchademelding');
			$.each(data, function(key, value) {
				var polisTitel = value.soort + " (" + value.polisNummer + ")";

			    $('<option>', { value : value.id }).text(polisTitel).appendTo($select);
			});

			$.get( "../dejonge/rest/medewerker/overig/lijstStatusSchade", function(data) {
				var $select = $('#statusSchade');
				$.each(data, function(key, value) {
				    $('<option>', { value : value }).text(value).appendTo($select);
				});
				if(subId != null && subId != "0"){
					log.debug("ophalen Schade met id " + subId);
					$.get( "../dejonge/rest/medewerker/schade/lees", {"id" : subId}, function(data) {
						log.debug("applybindings met " + JSON.stringify(data));
						var schade = new Schade(data);
						schade.relatie(relatieId);
						schade.bijlages.removeAll();
						ko.applyBindings(schade);
				    });
				}else{
					log.debug("applybindings met een nieuw Schade object");
					var schade = new Schade('');
					schade.relatie(relatieId);
					ko.applyBindings(schade);
				}
			});
		});

//			var soortenSchade = new Bloodhound({
//				datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
//				queryTokenizer: Bloodhound.tokenizers.whitespace,
//				remote: '../dejonge/rest/medewerker/overig/soortenSchade?query=%QUERY'
//			});
//
//			soortenSchade.initialize();
//
//			$('#soortSchade').typeahead({
//				hint: true,
//				highlight: true,
//				minLength: 1
//			},
//			{
//				name: 'states',
//				displayKey: 'value',
//				source: soortenSchade.ttAdapter()
//			});
	};
});	