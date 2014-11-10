define([ "commons/3rdparty/log"],
	function(log) {

	return {
		maakBedragOp: function(bedrag) {
			if(bedrag != undefined){
				var opgemaaktBedrag = bedrag.replace(".", ",");
				var delen = opgemaaktBedrag.split(",");

				if(delen[1].length == 1){
					opgemaaktBedrag = opgemaaktBedrag + "0";
				}

				return '\u20AC ' + opgemaaktBedrag.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
			}
		}
    };
});