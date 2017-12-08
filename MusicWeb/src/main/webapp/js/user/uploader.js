var $list = $("#thelist");
var uploader = WebUploader.create({

    // swf文件路径
    swf: basePath + 'js/uploader/swf/Uploader.swf',

    // 文件接收服务端。
    server: basePath+'rest/uploaderAction/upload',

    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: '#picker',
    duplicate:true,//是否可重复选择同一文件
    resize: false,// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
    chunked: true,  //分片处理
    hunkSize: 5 * 1024 * 1024,//每片5M
    chunkRetry:false,//如果失败，则不重试
    threads:1,//上传并发数。允许同时最大上传进程数。
    // runtimeOrder: 'flash',  
    // 禁掉全局的拖拽功能。这样不会出现图片拖进页面的时候，把图片打开。  
    disableGlobalDnd: true,
 	// 如果某个分片由于网络问题出错，允许自动重传多少次？
 	
    chunkRetry: 3,
 	// 上传本分片时预处理下一分片
    prepareNextFile: true,
    // 去重
    duplicate: true,
    accept : {
        title : 'Applications',
        extensions : 'xls,xlsx',
        mimeTypes : 'application/xls,application/xlsx'
    }
});
//当有文件添加进来的时候
uploader.on( 'fileQueued', function( file ) {
	console.log(file);
	$list.append( '<div id="' + file.id + '" class="item">' +
		        '<h4 class="info">' + file.name + '</h4>' +
		        '<p class="state">等待上传...</p>' +
		    '</div>' );
	$("#getBtn").hide();
});
// 文件上传过程中创建进度条实时显示。
uploader.on( 'uploadProgress', function( file, percentage ) {
    var $li = $( '#'+file.id ),
        $percent = $li.find('.progress .progress-bar');

    // 避免重复创建
    if ( !$percent.length ) {
        $percent = $('<div class="progress progress-striped active">' +
          '<div class="progress-bar" role="progressbar" style="width: 0%">' +
          '</div>' +
        '</div>').appendTo( $li ).find('.progress-bar');
    }

    $li.find('p.state').text('上传中');

    $percent.css( 'width', percentage * 100 + '%' );
});
//上传成功时回调
uploader.on( 'uploadSuccess', function( file ) {
    $( '#'+file.id ).find('p.state').text('已上传');
});
//上传失败时回调
uploader.on( 'uploadError', function( file ) {
    $( '#'+file.id ).find('p.state').text('上传出错');
    uploader.cancelFile(file);
    uploader.removeFile(file,true);
    uploader.reset();
});
//上传完成后回调
uploader.on( 'uploadComplete', function( file ) {
    $( '#'+file.id ).find('.progress').fadeOut();
    $("#getBtn").show();
});
// 长传完毕，不管成功失败都会调用该事件，主要用于关闭进度条
uploader.on("uploadFinished",function(){
    console.log("uploadFinished:");
});
function uploadTest() {
    uploader.upload();
}
function getFile(){
	$.ajax({
		url:basePath + "rest/uploaderAction/download",
		type:"post",
		dataType:"json",
		success:function(json){
			if(json.success){
				var path = json.data;
				$(".down").append("<a href='"+path+"'><button  class='btn btn-default' >下载</button></a>")
			}
		},
		error : function(json) {
			alert("error")
		}
	});
}