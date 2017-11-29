$(function() {
	/*
	 * Elements in the left column in user_dashboard.jsp
	 */
	var $checkboxesNextToFiles = $(".file-checkbox");
	var $file = $(".file");
	
	/*
	 * Elements in the middle column in user_dashboard.jsp
	 */
	var $noFileSelectedHeader = $(".middle-column .no-file-selected");
	var $fileTitleHeader = $("#file-title");
	
	/*
	 * elements in the right column in user_dashboard.jsp
	 */
	var $downloadDeleteButtonContainer = $("#download-delete-btn-container");
	var $numberOfFilesHeader = $("#number-of-files-header");
	var $downloadButton = $("#download-btn");
	var $deleteButton = $("#delete-btn");
	var numberOfSelectedFiles = 0;
	var selectedFileTitle = "";

	
	function hideDownloadAndDeleteButton() {
		$downloadDeleteButtonContainer.addClass("hidden");
	}
	function showDownloadAndDeleteButton() {
		$downloadDeleteButtonContainer.removeClass("hidden");
		if (numberOfSelectedFiles == 1)
			setNumberOfFilesHeaderContent(selectedFileTitle);
		else
			setNumberOfFilesHeaderContent(numberOfSelectedFiles + " Files selected");
	}
	
	function setNumberOfFilesHeaderContent(content){
		$numberOfFilesHeader
		.text(content);
	}
	
	function switchOffEveryCheckbox(){
		$checkboxesNextToFiles.prop("checked", false);
		numberOfSelectedFiles = 0;
		
	}

	/*
	 * When user toogle checkbox next to the file
	 */
	$checkboxesNextToFiles.on("change", function() {
		if(numberOfSelectedFiles < 0)
			numberOfSelectedFiles = 0;
		
		if (this.checked) {
			numberOfSelectedFiles++;
		} else {
			numberOfSelectedFiles--;
		}

		if (numberOfSelectedFiles > 0) {
			showDownloadAndDeleteButton();
		} else {
			hideDownloadAndDeleteButton();
		}
	});

	$file.on("click", function() { 
		var idOfCheckboxNextToClickedButton = "check" + this.id;
		var $checkboxForThisFile = $("#" + idOfCheckboxNextToClickedButton);
		
		/*
		 * File div has got invisible input which represents file name.
		 * Name attribute of this input is data-title and value is the name of the file this div points to.
		 * We can think about those 2 attributes as key:pair values.
		 */
		selectedFileTitle = $(this).children("input[name='data-title']").attr("value");
		

		switchOffEveryCheckbox();
		$checkboxForThisFile.prop('checked', !$checkboxForThisFile.prop("checked")); //Toggle checkbox to opposite state
		$checkboxForThisFile.trigger("change");
		
		
		$noFileSelectedHeader.remove(); 
	

		$fileTitleHeader.hide().text(selectedFileTitle).fadeIn(300);
	});
	

	$("#id-upload-btn").on("change", function() {
		this.form.submit();
	});
});