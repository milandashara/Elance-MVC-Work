
$(function() {
	$("#year").html("");
	
	var now= new Date(); 
	var year = now.getFullYear();
	for (var i = year; i >= 1970; i--){
		$("#year").append("<option value=\"" + i + "\">" + i + "</option>");
	}
});

// JavaScript Document
 function selectSchool(){
	 //$("#school_selector").show();
	 _schoolDlg.open(onSelectSchool);
 }
 
 function onSelectSchool(code, name){
	 $("#school").val(name);
	 $("#school_id").val(code);
	 
 }