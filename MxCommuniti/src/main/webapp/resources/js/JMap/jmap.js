window.JMap={};window.JMap.Layer={};window.JMap.Util={};JMap.Util.getDistance=function(b,a){return Math.sqrt((b.x-a.x)*(b.x-a.x)+(b.y-a.y)*(b.y-a.y))};JMap.Map=util.Class({layers:null,map:null,clickEvent:null,initialize:function(b){this.map=new OpenLayers.Map("map",{restrictedExtent:new OpenLayers.Bounds(-128*156543.0339,-128*156543.0339,128*156543.0339,128*156543.0339),maxExtent:new OpenLayers.Bounds(-128*156543.0339,-128*156543.0339,128*156543.0339,128*156543.0339),projection:"EPSG:900913",displayProjection:new OpenLayers.Projection("EPSG:4326")});var a=new OpenLayers.Layer.Google("Google streets",{});this.map.addLayer(a);this.map.setCenter(new OpenLayers.LonLat(105,37).transform(this.map.displayProjection,this.map.getProjectionObject()),4);this.layers=[];this.map.events.register("zoomend",this,this.onZoomEvent)},addLayer:function(c){var a=false;for(var b=0;b<this.layers.length;b++){if(this.layers[b]==c){a=true;break}}if(!a){this.map.addLayer(c.layer);c.map=this.map;this.layers.push(c)}},getLayersByName:function(a){},removeLayer:function(a){},updatePosition:function(b){this.clickEvent=b;var c=this;var a=function(d){if(c.clickEvent!=null){var e=c.map.getLonLatFromPixel(d.xy).transform(c.map.getProjectionObject(),c.map.displayProjection);c.clickEvent(e)}c.map.events.unregister("click",c.map,a);$("#map").css("cursor","auto")};this.map.events.register("click",this.map,a);$("#map").css("cursor","crosshair")},onZoomEvent:function(){for(var a=0;a<this.layers.length;a++){if(this.layers[a].zoomEvent!=null){this.layers[a].zoomEvent()}}},CLASS_NAME:"JMap.Map"});JMap.Layer.Persons=util.Class({layer:null,myApple:null,apples:null,appleTrees:null,map:null,aggregatable:true,aggregateWeight:10,popup:null,initialize:function(){this.layer=new OpenLayers.Layer.Markers(this.CLASS_NAME);this.layer.setOpacity(0.8);this.apples=[];this.appleTrees=[]},setAggregatable:function(a){if(this.aggregatable!=a){this.aggregatable=a;this.redraw()}},addPerson:function(e,b){var a=this.adaptApple(e,b);var h=false;for(var f=0;f<this.apples.length;f++){if(this.apples[f].key==a.key){this.removeApple(this.apples[f]);this.addApple(a);h=true;return}}var c=null;for(var f=0;f<this.appleTrees.length;f++){c=this.appleTrees[f];for(var d=0;d<c.apples;d++){if(c.apples[d].key==a.key){this.removeAppleTree(c);c.apples.splice(d,1);var g=c.apples;g.push(a);this.addApples(g);return}}}this.addApple(a)},adaptApple:function(c,b){var a={key:c.code,name:c.name,sex:c.sex,image:c.smallAvatar,longitude:c.longitude,latitude:c.latitude,singleFlag:b,};return a},addApples:function(b){for(var a=0;a<b.length;a++){this.addApple(b[a])}},addApple:function(g){var h=false;if(g!=null){if(this.aggregatable&&(g.singleFlag==null||g.singleFlag==false)){var d=new OpenLayers.LonLat(g.longitude,g.latitude).transform(this.map.displayProjection,this.map.getProjectionObject());var j=this.map.getPixelFromLonLat(d);var f;var a;for(var c=0;c<this.apples.length;c++){a=new OpenLayers.LonLat(this.apples[c].longitude,this.apples[c].latitude).transform(this.map.displayProjection,this.map.getProjectionObject());f=this.map.getPixelFromLonLat(a);if((this.apples[c].singleFlag==null||this.apples[c].singleFlag==false)&&JMap.Util.getDistance(j,f)<this.aggregateWeight){var k=this.apples[c];this.removeApple(k);var b={apples:[],marker:null,longitude:null,latitude:null};b.apples.push(k);b.apples.push(g);b.longitude=(g.longitude+k.longitude)/2;b.latitude=(g.latitude+k.latitude)/2;this.drawAppleTree(b);this.appleTrees.push(b);h=true;break}}if(!h){for(var c=0;c<this.appleTrees.length;c++){a=new OpenLayers.LonLat(this.appleTrees[c].longitude,this.appleTrees[c].latitude).transform(this.map.displayProjection,this.map.getProjectionObject());f=this.map.getPixelFromLonLat(a);if(JMap.Util.getDistance(j,f)<this.aggregateWeight){var e=this.appleTrees[c];this.removeAppleTree(e);e.apples.push(g);e.longitude=(g.longitude+e.longitude)/2;e.latitude=(g.latitude+e.latitude)/2;this.drawAppleTree(e);this.appleTrees.push(e);h=true;break}}}}if(!h){this.drawApple(g);this.apples.push(g)}}},removeAppleAll:function(){for(var a=0;a<this.apples.length;a++){this.layer.removeMarker(this.apples[a].marker);this.apples[a].marker=null}this.apples=[]},removeAppleTreeAll:function(){for(var a=0;a<this.appleTrees.length;a++){this.layer.removeMarker(this.appleTrees[a].marker);this.appleTrees[a].marker=null}this.appleTrees=[]},removeApple:function(a){if(a!=null){for(var b=0;b<this.apples.length;b++){if(this.apples[b]==a){this.layer.removeMarker(this.apples[b].marker);this.apples[b].marker=null;this.apples.splice(b,1);break}}}},removeAppleTree:function(a){if(a!=null){for(var b=0;b<this.appleTrees.length;b++){if(a==this.appleTrees[b]){this.layer.removeMarker(a.marker);a.marker=null;this.appleTrees.splice(b,1);break}}}},redraw:function(){var b=this.apples;for(var a=0;a<this.appleTrees.length;a++){b=b.concat(this.appleTrees[a].apples)}this.removeAppleAll();this.removeAppleTreeAll();for(var a=0;a<b.length;a++){this.addApple(b[a])}},drawApple:function(b){var d=new OpenLayers.Size(16,16);var g=new OpenLayers.Pixel(-8,-8);var f=null;if(b.sex==2){f="js/JMap/theme/images/map_female.png"}else{f="js/JMap/theme/images/map_male.png"}if(b.singleFlag!=null&&b.singleFlag){f="js/JMap/theme/images/map_my_location.gif";d=new OpenLayers.Size(24,24);g=new OpenLayers.Pixel(-12,-12)}var e=new OpenLayers.Icon(f,d,g);var a=new OpenLayers.LonLat(b.longitude,b.latitude).transform(this.map.displayProjection,this.map.getProjectionObject());var c=new OpenLayers.Marker(a,e);this.layer.addMarker(c);c.events.register("mouseover",this,this.mouseOverHandler);c.events.register("mouseout",this,this.mouseOutHandler);b.marker=c},drawAppleTree:function(d){var f=new OpenLayers.Icon(null);var b=new OpenLayers.LonLat(d.longitude,d.latitude).transform(this.map.displayProjection,this.map.getProjectionObject());var c=new OpenLayers.Marker(b,f);c.events.register("mouseover",this,this.mouseOverHandler);c.events.register("mouseout",this,this.mouseOutHandler);$markerDiv=$(c.icon.imageDiv).empty();var e,a;if(d.apples.length<=3){e="16";a="16"}else{if(d.apples.length<=5){e="20";a="20"}else{if(d.apples.length<=10){e="24";a="24"}else{if(d.apples.length<=20){e="32";a="32"}else{e="48";a="48"}}}}$($markerDiv).append("<div><img style='width: "+e+"px; height: "+a+"px;' src='js\\JMap\\theme\\images\\location_bk3.png'></img><span style='position: absolute; left: 0px; top: 0px; display: block; width: "+e+"px; height:"+a+"px; text-align:center;line-height: "+a+"px;'>"+d.apples.length+"<span></div>");this.layer.addMarker(c);d.marker=c},mouseOverHandler:function(g){if(this.popup!=null){this.map.removePopup(this.popup);this.popup=null}var b=null;var f="";var a=0;for(var d=0;d<this.apples.length;d++){if(this.apples[d].marker==g.object){b=this.apples[d];break}}if(b!=null){f="<div><img src='"+b.image+"' style='float: left; width: 36px; height: 36px;'><div style='float: left;'><span>"+b.name+"<span><div></div>";a=40}else{for(var d=0;d<this.appleTrees.length;d++){if(this.appleTrees[d].marker==g.object){for(var c=0;c<this.appleTrees[d].apples.length;c++){b=this.appleTrees[d].apples[c];f+="<div style='width: 100%; height: 38px; overflow:hidden;margin:0 0 1px 0;padding:0;'><img src='"+b.image+"' style='float: left; width: 36px; height: 36px;'><div style='float: left; width:52px;margin:0 0 0 2px;'><span>"+b.name+"<span></div></div>";a+=39}break}}}this.popup=new OpenLayers.Popup("info",new OpenLayers.LonLat(g.object.lonlat.lon,g.object.lonlat.lat),new OpenLayers.Size(100,a),f,false);this.map.addPopup(this.popup)},mouseOutHandler:function(a){if(this.popup!=null){this.map.removePopup(this.popup);this.popup=null}},zoomEvent:function(){this.redraw()},CLASS_NAME:"JMap.Layer.Persons"});