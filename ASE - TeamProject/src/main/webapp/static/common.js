
$(document).ready(
		function() {
			var els = $('#logout-href');
			if (els.length) {
				els[0].onclick = function(evt) {
					$.ajax('/logout', { type: 'POST' })
					 .done(
							 function() { 
								 window.location.href =
									 window.location.protocol + '//' +
									 window.location.host;
							 })
				}
			}
		}
);