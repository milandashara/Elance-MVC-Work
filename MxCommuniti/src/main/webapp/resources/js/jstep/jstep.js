window.JStep = {};

JStep.Util = {
	getImagePath: function(image){
		return util.getScriptLocation("jstep.js") + "theme/images/" + image;
	}
};

JStep.JStep = util.Class({
	parent: null,
	initialize: function(div, options){
		this.parent = "#" + div;
		$(this.parent).html("<div class='jstep-box'><ul></ul></div>");
		
		if (options){
			if (options.steps && options.steps.length > 0){
				for (var i = 0; i < options.steps.length; i++){
					var step = options.steps[i];
					var stepHtml = "<li class='jstep-step step-next' seq='" + (i + 1) + "'>"
						+ "<span class='step-deco'></span>"
						+ "<span class='step-val'>" + (i + 1) + "</span>"
						+ "<span class='step-title'>" + step + "</span>"
						+ "</li>";
					$(this.parent).find(".jstep-box ul").append(stepHtml);
				}
			}
		}
		
		this.setNowStep(2);
	},
	setNowStep: function(index){
		$(this.parent).find(".jstep-box ul li").each(function(){
			var order = $(this).attr("seq") - 1;
			if (order < index){
				$(this).removeClass("step-next step-on").find(".step-val").html("");
			}
			else if (order == index){
				$(this).removeClass("step-next").addClass("step-on").find(".step-val").html(order + 1);
			}
			else {
				$(this).removeClass("step-on").addClass("step-next").find(".step-val").html(order + 1);
			}
		});
	}
});