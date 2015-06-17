define(['jquery',
         'knockout',
         'commons/commonFunctions',
         'commons/block'],
	function ($, ko, commonFunctions, block) {

	return function(){
		var inlogModel = ko.validatedObservable({
			identificatie : ko.observable().extend({ required: true }),
			wachtwoord : ko.observable().extend({ required: true }),
			onthouden : ko.observable('false'),

			inloggen : function(){
				commonFunctions.verbergMeldingen();
				if(this.isValid()){
					block.block();
					$.ajax({
						type: "POST",
						url: '../dejonge/rest/authorisatie/authorisatie/inloggen',
						contentType: "application/json",
						data: ko.toJSON(this),
			            success: function (response) {
			            	commonFunctions.haalIngelogdeGebruiker();
			    			document.location.hash='#dashboard';
			            },
			            error: function (data) {
			            	commonFunctions.plaatsFoutmelding(data);
			            }
					});
				}
			}
		});
		return inlogModel;
    };
});