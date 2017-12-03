$(function() {
	/*
	 * Elements in the left column in user_dashboard.jsp
	 */
	var $checkboxesNextToFiles = $(".file-checkbox");
	var $files = $(".file");

	/*
	 * Elements in the middle column in user_dashboard.jsp
	 */
	var $noFileSelectedHeader = $(".middle-column .no-file-selected");
	var $fileTitleHeader = $("#file-title");

	/*
	 * Elements in the right column in user_dashboard.jsp
	 */
	var $downloadDeleteButtonContainer = $("#download-delete-btn-container");
	var $numberOfFilesHeader = $("#number-of-files-header");
	var $downloadButton = $("#download-btn");
	var $deleteButton = $("#delete-btn");
	var $searchField = $("#search-field");

	/*
	 * various variables
	 */
	var numberOfSelectedFiles = 0;
	var selectedFileTitle = "";
	var idsOfFilesToDownload = [];

	/**
	 * When user toogle checkbox next to the file
	 */
	$checkboxesNextToFiles.on("change", function() {
		var fileId = this.id.replace("check", "");

		if (numberOfSelectedFiles < 0)
			numberOfSelectedFiles = 0;

		if (this.checked) {
			idsOfFilesToDownload.push(fileId);
			numberOfSelectedFiles++;
		} else {
			var indexOfId = idsOfFilesToDownload.indexOf(fileId);
			idsOfFilesToDownload.splice(indexOfId, 1);
			numberOfSelectedFiles--;
		}

		if (numberOfSelectedFiles > 0) {
			showDownloadAndDeleteButton();
		} else {
			hideDownloadAndDeleteButton();
		}

		$fileTitleHeader.hide().text(idsOfFilesToDownload).fadeIn(300);
	});
	function hideDownloadAndDeleteButton() {
		$downloadDeleteButtonContainer.addClass("hidden");
	}
	function showDownloadAndDeleteButton() {
		$downloadDeleteButtonContainer.removeClass("hidden");
		if (numberOfSelectedFiles == 1)
			setNumberOfFilesHeaderContent(selectedFileTitle);
		else
			setNumberOfFilesHeaderContent(numberOfSelectedFiles
					+ " Files selected");
	}

	function setNumberOfFilesHeaderContent(content) {
		$numberOfFilesHeader.text(content);
	}

	$files.on("click", function() {
		var idOfCheckboxNextToClickedButton = "check" + this.id;
		var $checkboxForThisFile = $("#" + idOfCheckboxNextToClickedButton);

		/*
		 * File div has got invisible input which represents file name. Name
		 * attribute of this input is data-title and value is the name of the
		 * file this div points to. We can think about those 2 attributes as
		 * key:pair values.
		 */
		selectedFileTitle = $(this).children("input[name='data-title']").attr(
				"value");

		switchOffEveryCheckbox();

		$checkboxForThisFile.prop('checked', !$checkboxForThisFile
				.prop("checked")); // Toggle checkbox to opposite state
		$checkboxForThisFile.trigger("change");
		idsOfFilesToDownload = [ this.id ];

		$noFileSelectedHeader.remove();

		$fileTitleHeader.hide().text(idsOfFilesToDownload).fadeIn(300);
	});

	function switchOffEveryCheckbox() {
		$checkboxesNextToFiles.prop("checked", false);
		numberOfSelectedFiles = 0;

	}

	$("#id-upload-btn").on("change", function() {
		this.form.submit();
	});

	$downloadButton.on("click", function() {
		var url = idsOfFilesToDownload;
		window.location.href = "user/" + url;
	});

	$deleteButton.on("click", function() {
		var url = idsOfFilesToDownload;
		window.location.href = "user/delete/" + url;
	});

	$searchField.on("keyup", function() {
		if ($(this).length >= 1) {
			showFilesWithNameMatchingToSearchString($(this).val());
		}
	});
	function showFilesWithNameMatchingToSearchString(searchString) {
		$.each($files, function() {
			var filename = $(this).children("input[name='data-title']").attr(
					"value");
			if (!fileMatchingSearchString(filename, searchString)) {
				$(this).hide();
				$(this).prev().hide();
			}else{
				$(this).show();
				$(this).prev().show();
			}
		})
	}
	function fileMatchingSearchString(filename, searchString) {
		var regex = new RegExp(".*" + searchString + ".*", "i");
		return regex.test(filename);
	}
});