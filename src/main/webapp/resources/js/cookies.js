function areCookiesEnabled() {
	document.cookie = "testcookie";
	var cookieEnabled = document.cookie.indexOf("testcookie") != -1;

	return cookieEnabled;
}

document.getElementById("login-button").addEventListener("click", function(event){
	if(!areCookiesEnabled()){
		event.preventDefault();
		var url = "cookies";
		window.location.href = url;
	}
});

