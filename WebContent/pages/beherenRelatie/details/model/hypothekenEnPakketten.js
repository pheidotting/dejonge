define([ "commons/3rdparty/log",
         "commons/validation",
         "commons/opmaak",
         "pages/beherenRelatie/details/model/hypotheekPakket",
         "pages/beherenRelatie/details/model/hypotheek"],
         function(logger, validation, opmaak, HypotheekPakket, Hypotheek) {

	return function hypothekenEnPakketten(pakketten, hypotheken) {
		dit = this;

		dit.hypotheekPakketten = ko.observableArray();
		$.each(pakketten, function(i, item){
			dit.hypotheekPakketten.push(new HypotheekPakket(item));
		});
		dit.hypotheken = ko.observableArray();
		$.each(hypotheken, function(i, item){
			dit.hypotheken.push(new Hypotheek(item));
		});
	};
});
