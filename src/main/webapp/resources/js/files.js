$(function() {
	/*
	 * Elements in the left column in user_dashboard.jsp
	 */
	var $checkboxesNextToFiles = $(".file-checkbox");
	var $files = $(".file-container");

	/*
	 * Elements in the middle column in user_dashboard.jsp
	 */
	var $noFileSelectedHeader = $(".middle-column-container .no-file-selected");
	var $fileTitleHeader = $("#file-title");
	var $fileUploadDateHeader = $("#file-upload-date");
	var $fileContent = $(".file-content-container");

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
	var selectedFileUploadDate = "";
	var selectedFileContentType = "";
	var idsOfFilesToDownload = [];
	var idOfFileWhichDescriptionIsShown = -1;

	/**
	 * When user toogle checkbox next to the file
	 */
	$checkboxesNextToFiles.on("change", function() {
		var fileId = this.id.replace("check", "");

		if (idOfFileWhichDescriptionIsShown == -1) {
			$("#" + fileId).trigger("click");
		} else {
			if (numberOfSelectedFiles < 0)
				numberOfSelectedFiles = 0;

			if (this.checked) {
				idsOfFilesToDownload.push(fileId);
				numberOfSelectedFiles++;
			} else {
				var indexOfFile = idsOfFilesToDownload.indexOf(fileId);
				idsOfFilesToDownload.splice(indexOfFile, 1);
				numberOfSelectedFiles--;
			}

			if (numberOfSelectedFiles > 0) {
				showDownloadAndDeleteButton();
			} else {
				hideDownloadAndDeleteButton();

			}
		}

	});
	function hideDownloadAndDeleteButton() {
		$downloadDeleteButtonContainer.addClass("hidden");
	}
	function showDownloadAndDeleteButton() {
		$downloadDeleteButtonContainer.removeClass("hidden");
		if (numberOfSelectedFiles == 1){
			$checkboxesNextToFiles.each(function(){
				if($(this).is(":checked")){
					var fileId = this.id.replace("check", "");
					selectedFileTitle = $("#" + fileId).children("input[name='data-title']").attr(
					"value");
					
				}
			});
			setNumberOfFilesHeaderContent(selectedFileTitle);
		}
		else
			setNumberOfFilesHeaderContent(numberOfSelectedFiles
					+ " Files selected");
	}

	function setNumberOfFilesHeaderContent(content) {
		$numberOfFilesHeader.text(content);
	}

	$files.on("click", function() {
		idOfFileWhichDescriptionIsShown = this.id;
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
		selectedFileUploadDate = $(this).children(
				"input[name='data-upload-date']").attr("value");
		selectedFileContentType = $(this).children(
				"input[name='data-content-type']").attr("value");

		switchOffEveryCheckbox();

		$checkboxForThisFile.prop('checked', !$checkboxForThisFile
				.prop("checked")); // Toggle checkbox to opposite state
		$checkboxForThisFile.trigger("change");
		idsOfFilesToDownload = [ this.id ];

		$noFileSelectedHeader.remove();

		$fileTitleHeader.hide().text(selectedFileTitle).fadeIn(300);
		$fileUploadDateHeader.hide()
				.text("Uploaded: " + selectedFileUploadDate).fadeIn(300);
		highlightSelectedFile(this.id);
		getFileContent(this.id);
	});

	function switchOffEveryCheckbox() {
		$checkboxesNextToFiles.prop("checked", false);
		numberOfSelectedFiles = 0;
	}

	function highlightSelectedFile(fileId) {
		var $selectedFile = $("#" + fileId);

		$files.not($selectedFile).removeClass("file-selected");
		$selectedFile.addClass("file-selected");
	}

	function getFileContent(fileId) {
		var optionToChooseDependingOnFileContent = "";

		if (selectedFileContentType.indexOf("text") != -1)
			optionToChooseDependingOnFileContent = "text";
		else if (selectedFileContentType.indexOf("application") != -1 && 
				(selectedFileContentType.indexOf("application/zip") == -1 
						&& selectedFileContentType.indexOf("application/pdf") == -1))
			optionToChooseDependingOnFileContent = "application";
		else if (selectedFileContentType.indexOf("image") != -1)
			optionToChooseDependingOnFileContent = "image";
		else if (selectedFileContentType.indexOf("audio") != -1)
			optionToChooseDependingOnFileContent = "audio";
		else if (selectedFileContentType.indexOf("video") != -1)
			optionToChooseDependingOnFileContent = "video";
		else
			optionToChooseDependingOnFileContent = "unsupported";

		$fileContent.empty();

		switch (optionToChooseDependingOnFileContent) {
		case 'text':
			$.ajax({
				url : "user/" + fileId,
				cache : false,
				success : function(result) {
					$fileContent.append("<p>" + result + "</p>");
				}
			});
			break;
		case 'application':
			$.ajax({
				url : "user/" + fileId,
				cache : false,
				success : function(result) {
					$fileContent.append("<p>" + result + "</p>");
				}
			});
			break;
		case 'image':
			$fileContent
					.append("<img width='100%' height='auto' class='centered' src=user/"
							+ fileId + " />");
			break;

		case 'audio':
			var element = "<audio controls style='outline:none; width:100%; height:100%;' class='centered'> <source src=user/"
					+ fileId + " type='audio/mp3'> </audio>";
			$fileContent.append(element);
			break;

		case 'video':
			var element = "<video controls  style='outline:none; width:100%; height:100%;' class='centered'> <source src=user/"
					+ fileId + " type='audio/mp3'> </video>";
			$fileContent.append(element);
			break;

		default:
			var element = "<p>Can't preview the file content!</p>";
			$fileContent.append(element);
		}
	}

	$("#id-upload-btn").on("change", function() {
		this.form.submit();
	});

	$downloadButton.on("click", function() {
		var url = idsOfFilesToDownload;
		window.location.href = "user/download/" + url;
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
			} else {
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