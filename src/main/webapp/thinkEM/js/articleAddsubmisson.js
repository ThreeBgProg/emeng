/**
 * Created by guiliang on 2017/7/1 0001.
 */
/**
 * Created by guiliang on 2017/6/15 0015.
 */
$(function () {
	var annex='';
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
        var $article_content = layedit.getContent(editIndex);
        var $article_title = $('#L_title').val();
        var $article_author = $('#L_author').val();
        var $article_link = $('#L_link').val();
        if($article_content&&$article_title){
        $.ajax({
            url: "../submit/passage",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
            data: {'link':$article_link,'annex':annex,'titile':$article_title,'content':$article_content},    //参数值 可能不用
            type: "POST",   //请求方式
            success: function(data) {
                layer.alert("投稿成功", {icon: 6},function () {
                	location.reload();
                });
            },
            error: function() {
                layer.alert("投稿失败，你可能还没登录", {icon: 9});
            }
        });
        }else{
            layer.alert("标题和内容不能为空", {icon: 9});
        }
        return false;
    })

    layui.use(['layedit'], function() {
        layedit = layui.layedit;
        layedit.set({
            uploadImage: {
                url: "../picupload" //接口url
                ,type: 'post' //默认post
                ,sucess:function(e) {
                    console.log(e);
                }
            }
        })
        //创建一个编辑器
        editIndex = layedit.build('L_content',{
        	height:480
        });
    })
    
    function onprogress(evt){
        var loaded = evt.loaded;                  //已经上传大小情况 
        var tot = evt.total;                      //附件总大小 
        var per = Math.floor(100*loaded/tot);      //已经上传的百分比  
        $("#son").html( per +"%" );
        $("#son").css("width" , per +"%");
    }

})