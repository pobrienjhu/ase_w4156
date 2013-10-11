
/*
 * Adds an onClick handler to the provided tag_id that will submit the
 * form_id after setting input_id to the provided OpenId URL. 
 */
function assignOpenIdClickHandler(tag_id, input_id, form_id, url) {
	var el = document.getElementById(tag_id);
	if (el) {
		el.onclick = function() {
			var input = document.getElementById(input_id);
			if (input) {
				input.value = url;
			}
			var form = document.getElementById(form_id);
			if (form) {
				form.submit();
			}
		}
	}
}

/*
 * Sets up OpenId handlers on page load.
 */
function initOpenId(evt) {
	assignOpenIdClickHandler('openid_google', 'openid_identifier',
			'openid_form', 'https://www.google.com/accounts/o8/id');
	assignOpenIdClickHandler('openid_yahoo', 'openid_identifier', 'openid_form',
			'https://me.yahoo.com');
}

initOpenId();
// Register event listener to annotate page when load completes.
//document.addEventListener('DOMContentLoaded', initOpenId);





