define(['jquery'],
    function($) {

	return function(){
		$('#content').load('templates/dashboard/dashboard.html', function(response, status, xhr) {
			if (status == "success") {
				$.get( "../dejonge/rest/medewerker/taak/aantalOpenTaken", function(data) {
					$('#aantalOpenstaandeTaken').html(data);
				});
			}

			$('#zoeken').click(function(){
				document.location.hash='#lijstRelaties/' + $('#zoekTerm').val();
			});
			
			$('#zoekTerm').on("keypress", function(e) {
	            if (e.keyCode == 13) {
					document.location.hash='#lijstRelaties/' + $('#zoekTerm').val();
	            }
			});
		});
	};
});