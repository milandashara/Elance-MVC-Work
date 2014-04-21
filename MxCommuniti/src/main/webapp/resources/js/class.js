$(function(){
	$("#N_class_34556775").addClass("N_active");
	$("#members").click(function(){
		//$("#memeber_box").load('ajax/class/members');
		$("#memeber_box").load('ajax/class/search');
		
		return false;
	});
});