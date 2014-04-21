// JavaScript Document
$(function() {
  $("#school_selector").dialog({
	  autoOpen: false,
	  resizable: false,
	  modal: true,
	  width: 660,
	  buttons: [{
		  text: "关闭",
		  click: function(){
			  $( this ).dialog( "close" ); 
		  }
	  }]
  });
});

var _cs_data =	{
	cities: null,
	counties: null
};

var _cs_opera = {
	onProvinceClick: function(index, elem){
		$("#school_selector #province_list a").removeClass("active");
		$(elem).addClass("active");
		
		$("#school_selector #city_list").html("");
		$("#school_selector #county_list").html("");
		$("#school_selector #school_list").html("");
		var city = null;
		for (var i = 0; i < _cs_data.cities[index][1].length; i++){
			city = _cs_data.cities[index][1][i];
			$("#school_selector #city_list").append("<li><a " + "onclick=\"_cs_opera.onCityClick('" + city.split(":")[0] +"', this)\"" + ">" + city.split(":")[1] + "</a></li>");
		}
		
		$("#school_selector #city_list a").first().click();
	},
	onCityClick: function(code, elem){
		$("#school_selector #city_list a").removeClass("active");
		$(elem).addClass("active");
		
		_cs_data.counties = null;
		_cs_data.counties = util.getJSON("data/" + code +".json");
		
		$("#school_selector #county_list").html("");
		$("#school_selector #school_list").html("");
		
		if (_cs_data.counties == null)
			return;
		
		var county = null;
		for (var i = 0; i < _cs_data.counties.length; i++){
			county =_cs_data.counties[i];
			$("#school_selector #county_list").append("<li><a " + "onclick=\"_cs_opera.onCountyClick(" + i  +", this)\"" + ">" + county[0].split(":")[1] + "</a></li>");	
		}
		
		$("#school_selector #county_list a").first().click();
	},
	onCountyClick: function(index, elem){
		$("#school_selector #county_list a").removeClass("active");
		$(elem).addClass("active");	
		
		$("#school_selector #school_list").html("");
		var school = null;
		for (var i = 0; i < _cs_data.counties[index][1].length; i++){
			school = _cs_data.counties[index][1][i];
			$("#school_selector #school_list").append("<li><a " + "onclick=\"_cs_opera.onSchoolClick('" + school.split(":")[0] +"', this)\"" + ">" + school.split(":")[1] + "</a></li>");
		}
	},
	onSchoolClick: function(code, elem){
		$("#school_selector #school_list a").removeClass("active");
		$(elem).addClass("active");	
		
		if (_cs_opera.schoolSelectCallback != null){
			_cs_opera.schoolSelectCallback(code, $(elem).text());
		}
		
		$("#school_selector").dialog("close");
	},
	schoolSelectCallback: null
};

var _schoolDlg = {
		open: function(func){
			_cs_opera.schoolSelectCallback = func;
			
			
			$("#school_selector").dialog("open");
			
			if (_cs_data.cities == null){
				_cs_data.cities = util.getJSON("data/city.json");	
			}
			
			$("#school_selector #province_list").html("");
			for (var i = 0; i < _cs_data.cities.length; i++){
				$("#school_selector #province_list").append("<li><a " + "onclick=\"_cs_opera.onProvinceClick(" + i  +", this)\"" + ">" + _cs_data.cities[i][0].split(":")[1] + "</a></li>");
			}
			
			$("#school_selector #province_list a").first().click();
		}
};