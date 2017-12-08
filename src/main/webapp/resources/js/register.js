$(function() {
	/**
	 * Variables
	 */
	var $firstName = $("#first-name");
	var $lastName = $("#last-name");

	var $email = $("#email");
	var $invalidEmail = $("#email-invalid");

	var $password = $("#password");
	var $invalidPassword = $("#password-invalid");

	var $submitButton = $("#register-submit");
	
	/**
	 * Functions
	 */
	function isFirstNameValid(firstName) {
		return firstName.length > 0;
	}
	function isLastNameValid(lastName) {
		return lastName.length > 0;
	}

	function validateEmail() {
		var emailContent = $email.val();
		if (isEmailValid(emailContent)) {
			$invalidEmail.text("");
			$email.attr("class", "valid-input");
		} else {
			$invalidEmail.css("color", "red");
			$invalidEmail.text("INVALID!");
			$email.attr('class', 'invalid-input');
		}

	}
	
	function isEmailValid(email) {
		var regexForProperEmail = new RegExp("[a-zA-Z0-9._%-+]+@[a-zA-Z0-9-.]+");
		return regexForProperEmail.test(email);
	}

	function validatePassword() {
		var password = $password.val();
		if (isPasswordValid(password)) {
			$invalidPassword.text("");
			$password.attr("class", "valid-input");
		} else {
			$invalidPassword.css("color", "red");
			$invalidPassword.text("TOO SHORT!");
			$password.attr('class', 'invalid-input');
		}

	}
	
	function isPasswordValid(password) {
		var minimumLengthOfPassword = 8;
		return password.length >= minimumLengthOfPassword;
	}

	/**
	 * Event handlers
	 */
	$firstName.on("blur", function() {
		var firstName = $firstName.val();
		if (isFirstNameValid(firstName)) {
			$firstName.attr("class", "valid-input");
		} else {
			$firstName.removeClass();
		}
	});

	$lastName.on("blur", function() {
		var lastName = $lastName.val();
		if (isLastNameValid(lastName)) {
			$lastName.attr("class", "valid-input");
		} else {
			$lastName.removeClass();
		}
	});

	$email.on("keyup", function() {
		if ($email.hasClass("invalid-input")) {
			validateEmail();
		}
	});
	
	$email.on("blur", function() {
		validateEmail();
	});

	$password.on("keyup", function() {
		validatePassword();
	});

	$submitButton.on("click", function(event) {
		event.preventDefault();
		var firstNameValid = isFirstNameValid($firstName.val());
		var lastNameValid = isLastNameValid($lastName.val());
		var emailValid = isEmailValid($email.val());
		var passwordValid = isPasswordValid($password.val());

		/*
		 * Those bunch of ifs are constructed in order to focus fields from top
		 * to bottom when they're invalid
		 */
		if (!firstNameValid) {
			$firstName.trigger("focus");
		} else {
			if (!lastNameValid) {
				$lastName.trigger("focus");
			} else {
				if (!emailValid) {
					$email.trigger("focus");
				} else {
					if (!passwordValid) {
						$password.trigger("focus");
					} else {
						this.form.submit();
					}
				}
			}
		}
	});
})