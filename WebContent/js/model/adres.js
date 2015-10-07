define(['jquery',
        'commons/commonFunctions',
         'knockout',
         'commons/3rdparty/log',
         "commons/validation"],
	function ($, commonFunctions, ko, log, validation) {

	return function adresModel (data){
	    thisAdres = this;

        thisAdres.zetPostcodeOm = function(postcode){
            log.debug(postcode);
            if(postcode != null){
                if(postcode.length == 6){
                    postcode = postcode.toUpperCase();
                    postcode = postcode.substring(0, 4) + " " + postcode.substring(4);

                    return postcode;
                }
            }
        };
		thisAdres.straat = ko.observable(data.straat);
		thisAdres.huisnummer = ko.observable(data.huisnummer).extend({ number: true});
		thisAdres.toevoeging = ko.observable(data.toevoeging);
		thisAdres.postcode = ko.observable(data.postcode);
        thisAdres.soortAdres = ko.observable(data.soortAdres);
		thisAdres.plaats = ko.observable(data.plaats);

		thisAdres.zetPostcodeOm();
    };
});