$(function(){
	
});
function searchMusic(){
	var searcMsg = $(".searchTxt").val();
	if(searcMsg.trim()==""){
		return;
	}
	var url = basePath+"rest/musicApiAction/searchMusic";
	$.ajax({
		url:url,
		data:{name:searcMsg,number:10},
		dataType:"json", 
		success:function(json){
			if(json.success){
				 var data = JSON.parse(json.data)
				 console.log(data);
				 
			}
		}
	})
}