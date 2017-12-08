$(function() {

	/*
	 * Variables
	 */

	// Elements in the left column in user_dashboard.jsp
	var $checkboxesNextToFiles = $(".file-checkbox");
	var $files = $(".file-container");

	// Elements in the middle column in user_dashboard.jsp
	var $noFileSelectedHeader = $(".middle-column-container .no-file-selected");
	var $fileTitleHeader = $("#file-title");
	var $fileUploadDateHeader = $("#file-upload-date");
	var $fileContent = $(".file-content-container");

	// Elements in the right column in user_dashboard.jsp
	var $downloadDeleteButtonContainer = $("#download-delete-btn-container");
	var $numberOfFilesHeader = $("#number-of-files-header");
	var $downloadButton = $("#download-btn");
	var $deleteButton = $("#delete-btn");
	var $searchField = $("#search-field");

	// various
	var idsOfFilesToDownload = [];
	var hasAnyFileBeenSelectedYet = false;

	/*
	 * Functions
	 */
	function showDownloadDeleteButtonContainer() {
		$downloadDeleteButtonContainer.removeClass("hidden");
		if (idsOfFilesToDownload.length == 1) {
			var selectedFileTitle = getFileTitleAssignedToSingleCheckedCheckbox();
			$numberOfFilesHeader.text(selectedFileTitle);
		} else
			$numberOfFilesHeader.text(idsOfFilesToDownload.length + " Files selected");
	}
	
	function getFileTitleAssignedToSingleCheckedCheckbox(){
		var selectedFileTitle = "";
		$checkboxesNextToFiles.each(function() {
			if ($(this).is(":checked")) {
				var fileId = this.id.replace("check", "");
				selectedFileTitle = $("#" + fileId).children("input[name='data-title']").attr("value");
			}
		});
		return selectedFileTitle;
	}

	function hideDownloadDeleteButtonContainer() {
		$downloadDeleteButtonContainer.addClass("hidden");
	}

	function switchOffEveryCheckbox() {
		$checkboxesNextToFiles.prop("checked", false);
		idsOfFilesToDownload = [];
	}
	
	function switchOnCheckbox($checkbox){
		$checkbox.prop('checked', !$checkbox.prop("checked")); // Toggle checkbox to opposite state
		$checkbox.trigger("change");
	}

	function highlightSelectedFile(fileId) {
		var $selectedFile = $("#" + fileId);

		$files.removeClass("file-selected"); //remove highlight from other files
		$selectedFile.addClass("file-selected");
	}

	function previewFileContent(fileId) {
		$fileContent.empty();
		/*
		 * Every file div has got 3 invisible inputs which represents file data: 
		 * 1st - file title
		 * 2nd - file upload date
		 * 3rd - file content type
		 */
		var selectedFileTitle = $("#" + fileId).children("input[name='data-title']").attr("value");
		var selectedFileUploadDate = $("#" + fileId).children("input[name='data-upload-date']").attr("value");
		var selectedFileContentType = $("#" + fileId).children("input[name='data-content-type']").attr("value");
		var typeOfContent = determineWhatTypeOfContentFileRepresents(selectedFileContentType);
		
		$fileTitleHeader.hide().text(selectedFileTitle).fadeIn(300);
		$fileUploadDateHeader.hide().text("Uploaded: " + selectedFileUploadDate).fadeIn(300);
		
		switch (typeOfContent) {
		case 'text':
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
			var element = "<img width='100%' height='auto' class='centered' src=user/"
				+ fileId + " />";
			$fileContent.append(element);
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
	/*
	 * selectedFileContentType is in format 'text/something', 'image/something'.
	 * We need to get just the first part(text, image, application etc.) of
	 * content type so files with different subcategory of content type would be
	 * processed in the same way. For example image/jpeg and image/png should be
	 * done with the same operation
	 */
	function determineWhatTypeOfContentFileRepresents(selectedFileContentType) {
		var typeOfContent = "";

		if (selectedFileContentType.indexOf("text") != -1)
			typeOfContent = "text";
		// application/zip && application/pdf are not supported
		else if (selectedFileContentType.indexOf("application") != -1
				&& (selectedFileContentType.indexOf("application/zip") == -1 && selectedFileContentType
						.indexOf("application/pdf") == -1))
			typeOfContent = "application";
		else if (selectedFileContentType.indexOf("image") != -1)
			typeOfContent = "image";
		else if (selectedFileContentType.indexOf("audio") != -1)
			typeOfContent = "audio";
		else if (selectedFileContentType.indexOf("video") != -1)
			typeOfContent = "video";
		else
			typeOfContent = "unsupported";
		return typeOfContent;
	}
	
	function showFilesWithNameMatchingTheSearchString(searchString) {
		$.each($files, function() {
			var filename = $(this).children("input[name='data-title']").attr("value");
			if (!fileMatchingSearchString(filename, searchString)) {
				$(this).hide();
				$(this).prev().hide(); //Hide checkbox next to file
			} else {
				$(this).show();
				$(this).prev().show(); //Show checkbox next to file
			}
		})
	}
	function fileMatchingSearchString(filename, searchString) {
		var regex = new RegExp(".*" + searchString + ".*", "i");
		return regex.test(filename);
	}

	/*
	 * Event handlers
	 */
	$files.on("click", function() {
		var $checkboxForThisFile = $("#check" + this.id);
		hasAnyFileBeenSelectedYet = true;

		switchOffEveryCheckbox();
		previewFileContent(this.id);
		switchOnCheckbox($checkboxForThisFile);
		highlightSelectedFile(this.id);
		
		$noFileSelectedHeader.remove();
	});

	
	$checkboxesNextToFiles.on("change", function() {
		var fileId = this.id.replace("check", "");

		if (!hasAnyFileBeenSelectedYet){
			$("#" + fileId).trigger("click");
		} else {
			if (this.checked) {
				idsOfFilesToDownload.push(fileId);
			} else {
				var indexOfFile = idsOfFilesToDownload.indexOf(fileId);
				idsOfFilesToDownload.splice(indexOfFile, 1);
			}

			if (idsOfFilesToDownload.length > 0) {
				showDownloadDeleteButtonContainer();
			} else {
				hideDownloadDeleteButtonContainer();

			}
		}

	});
	
	$searchField.on("keyup", function() {
		if ($(this).length >= 1) {
			showFilesWithNameMatchingTheSearchString($(this).val());
		}
	});
	
	$("#id-upload-btn").on("change", function() {
		this.form.submit();
	});

	$downloadButton.on("click", function() {
		window.location.href = "user/download/" + idsOfFilesToDownload;
	});

	$deleteButton.on("click", function() {
		window.location.href = "user/delete/" + idsOfFilesToDownload;
	});
});