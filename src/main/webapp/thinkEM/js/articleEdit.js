/**
 * Created by guiliang on 2017/6/15 0015.
 */
$(function () {
	 layui.use(['layedit'], function() {
	        layedit = layui.layedit;
	        layedit.set({
	            uploadImage: {
	                url: "../picupload" //接口url
	                ,type: 'post' //默认post
	            }
	        })
	        //创建一个编辑器
	        editIndex = layedit.build('L_content',{
	        	  height: 480   //设置编辑器高度
	        });
	    })
	
	var annex = null;
    var url = location.href;    //获取iframe的url
    var id= getUrlParam('id');    //获取参数即文章id
    var type= getUrlParam('type'); 
    var lesson_id = getUrlParam('lessonid');
    var chapter_number = getUrlParam('chapternumber');
    function getUrlParam(name)
    {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r!=null) return unescape(r[2]); return null; //返回参数值
    }
    $.ajax({
        url: "../passage/main",    //请求的url地址
        dataType: "json",   //返回格式为json
        async: true, //请求是否异步，默认为异步，这也是ajax重要特性
        data: { "passageId": id},    //参数值
        type: "POST",
        success: function(data) {
        	 $('#L_title').val(data.passageMainList[1].titile);
             $('#L_content').val(data.passageMainList[1].content);
             $('#L_author').val(data.passageMainList[1].author);
             $('#L_annex').val(data.passageMainList[1].annex);
             layui.use(['layedit'], function() {
     	        layedit = layui.layedit;
     	        layedit.set({
     	            uploadImage: {
     	                url: "../picupload" //接口url
     	                ,type: 'post' //默认post
     	            }
     	        })
     	        //创建一个编辑器
     	        editIndex = layedit.build('L_content',{
  	        	  height: 480   //设置编辑器高度
    	        });
     	    })
     	    
     	    
     	   /* 文件上传*/
     	 $("#article_annex").on('change',function () {
        var fileinput = $("#article_annex");
        var file = fileinput[0].files[0];
        var formData1 = new FormData();
        formData1.append("annex1",file);

        $.ajax({
            url: "../meetingupload",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
            processData: false,    //必须设置
            contentType: false,    //必须设置
            data:formData1,
            /*data: {'title':meeting_title,'content':meeting_content,'link':meeting_link,'type':meeting_type,'release_date':new Date(meeting_releaseDate),'code':meeting_code,'annex':formData},    //参数值 可能不用*/
            type: "POST",   //请求方式
            xhr: function(){
                var xhr = $.ajaxSettings.xhr();
                if(onprogress && xhr.upload) {
                    xhr.upload.addEventListener("progress" , onprogress, false);
                    return xhr;
                }
            },//请求方式
            success: function(data) {
            	 
                console.log(data.annex);
                annex = data.annex;

            },
            error: function(){
                console.log('上传文件失败');
            }
        });

    })
             
             
             $("#article_submit").on('click',function (e) {
                 var $article_id = id;
                 var $article_content = layedit.getContent(editIndex);
                 var $article_title = $('#L_title').val();
                 var $article_author = $('#L_author').val();
                 var $article_annex = $('#L_annex').val();
                 var $article_type = data.type;
                 console.log('文章id'+$article_id);
                 console.log('文章type'+$article_type);
                 console.log('文章标题'+$article_title);
                 console.log('文章内容'+$article_content);
                 annex = annex==null?$article_annex:annex;
                 console.log($article_annex);
                 //把投稿数据传到后台
                 $.ajax({
                     url: "../update/passage",    //请求的url地址
                     dataType: "json",   //返回格式为json
                     async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                     data: { 'annex':annex,'author':$article_author,"id": $article_id,'titile':$article_title,'content':$article_content,'type':$article_type,'lesson':lesson_id,'chapter':chapter_number},    //参数值 可能不用
                     type: "POST",   //请求方式
                     success: function(data) {
                    	 layer.alert("修改文章成功", {icon: 6},function () {
                             window.history.back();
                         });
                     },
                     error: function() {
                         
                     }
                 });
                 return false;
             })
            
        },
        error: function() {
           

        }
    });

    function onprogress(evt){
        var loaded = evt.loaded;                  //已经上传大小情况 
        var tot = evt.total;                      //附件总大小 
        var per = Math.floor(100*loaded/tot);      //已经上传的百分比  
        $("#son").html( per +"%" );
        $("#son").css("width" , per +"%");
    }

})