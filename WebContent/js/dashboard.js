define(['jquery'],
    function($) {

	return function(){
		$('#content').load('templates/dashboard/dashboard.html', function(response, status, xhr) {
			if (status == "success") {
//				ko.applyBindings(new dashboard());
			}
		});
	};
});