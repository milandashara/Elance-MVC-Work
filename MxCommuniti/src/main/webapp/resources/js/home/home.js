var map, layer, markers, myMark;
var jmap;

var map;
var personLayer;

$(function(){   
    $("#map_tools_locate").click(setUserPosition);
	$("#map_tool_aggregate").click(onMapToolAggregate);		
	
	map = new JMap.Map("map");
	
	personLayer = new JMap.Layer.Persons();
	map.addLayer(personLayer);
	map.clickEvent = function(lonlat){
		alert(lonlat);
	};
	
	util.getJSONAsync('ajax/user/getInfo', {}, onGetUserInfoResult);
	util.getJSONAsync('ajax/user/getUserClass', {}, onGetUserClassResult);
});

function onMapToolAggregate(){
	var has = $("#map_tool_aggregate").hasClass("check-on");
	
	personLayer.setAggregatable(!has);
	$("#map_tool_aggregate").toggleClass("check-on");
}

function onGetUserInfoResult(result, status){
	if (result != null){
		personLayer.addPerson(result.data, true);	
	}
}

function onGetUserClassResult(data, status){
	if (data != null){
		var myClassList = data.data;
		for (var i = 0; i < myClassList.length; i++){
			
			var data = {
					classCode: myClassList[i].code
			};
			util.getJSONAsync('ajax/class/getClassMembers', data, onGetMembers);	
		}
	}
}

function onGetMembers(data, status){
	if (data != null){
		for (var i = 0; i < data.data.length; i++){
			var member = data.data[i];
			if (member.code != g_user_code){
				personLayer.addPerson(member);		
			}
		}
	}
}

function setMyLocation(lonlat){	
	var data = {
			longitude: lonlat.lon,
			latitude: lonlat.lat
		};
	util.getJSONAsync('ajax/user/updateUserPosition', data, onUpdateUserPosition);
}

function onUpdateUserPosition(result){
	if (result != null && result.status == true){
		personLayer.addPerson(result.data, true);	
	}	
}

function setUserPosition(){	
	map.updatePosition(onMapClick);
	$("#map_tools_locate").addClass("focus");
}

function onMapClick(lonlat){
	setMyLocation(lonlat);
	$("#map_tools_locate").removeClass("focus");
}