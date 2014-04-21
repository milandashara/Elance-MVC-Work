// JavaScript Document
var bookShelfs = [];
var pageSize = 8;
$(function(){	
	
	$(".share-class").each(function(){
	    var id = $(this).attr("id");
	    var pageNumber = 1;
	    setShareList(id,pageNumber);
	 });
});

function setShareList(id,pageNumber){
	var bookShelf = new JShelf.Shelf( id + " .apple_list", {template: "jshelf_template"});
	bookShelfs.push({id: id, bookShelf: bookShelf});
	
	var data = {
			classId: id,
			pageNumber: pageNumber,
			pageSize: pageSize
	};
	
	util.getJSONAsync('ajax/share/book/getbooks', data, function(result){
		$("#currentPage_"+id).val(pageNumber);
		var totalPage = parseInt((result.totolCount + 8-1)/8);
		$("#totalPage_"+id).val(totalPage);
		setPageButton(id,pageNumber,totalPage);
		
		if (result.object.length > 0){
			var shareList = result.object;
			var share = null;
			for (var i = 0; i < shareList.length; i++){
				share = shareList[i];
				
				bookShelf.addApple(share);
			}
		}
	});
}

function onShareQueryPrev(id){
	var pageNumber = parseInt($("#currentPage_"+id).val());
	var totalPage = parseInt($("#totalPage_"+id).val());
	if((pageNumber-1) >= 1){
		pageNumber --;
		$("#currentPage_"+id).val(pageNumber);
		setShareList(id,pageNumber);
	}
	setPageButton(id,(pageNumber-1),totalPage);
}

function onShareQueryNext(id){
	var pageNumber = parseInt($("#currentPage_"+id).val());
	var totalPage = parseInt($("#totalPage_"+id).val());
	if((pageNumber+1) <= totalPage){
		pageNumber ++;
		$("#currentPage_"+id).val(pageNumber);
		setShareList(id,pageNumber);
	}
	setPageButton(id,(pageNumber+1),totalPage);
}

function setPageButton(id,currentPage,totalPage){
	if(currentPage >= totalPage){
		$("#next_"+id).removeClass("focus_next");
		$("#next_"+id).addClass("next");
		$("#next_"+id).removeAttr("onclick");
	}else{
		$("#next_"+id).addClass("focus_next");
		$("#next_"+id).removeClass("next");
		$("#next_"+id).attr("onclick","onShareQueryNext("+id+")");
	}
	
	if(currentPage <= 1){
		$("#prev_"+id).removeClass("focus_prev");
		$("#prev_"+id).addClass("prev");
		$("#prev_"+id).removeAttr("onclick");
	}else{
		$("#prev_"+id).removeClass("prev");
		$("#prev_"+id).addClass("focus_prev");
		$("#prev_"+id).attr("onclick","onShareQueryPrev("+id+")");
	}
}