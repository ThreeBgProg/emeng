/**
 * Created by guiliang on 2017/6/15 0015.
 */
$(function () {
	var annex='';
    var url = location.href;    //获取iframe的url
    var tag= getUrlParam('tag')==null?getUrlParam('passagetype'):getUrlParam('tag');
    /*如果是热点推荐要加一个推荐等级*/
    var lessonid= getUrlParam('lessonid');
    var chapternumber= getUrlParam('chapternumber');
    if(tag==12){
        $('#recomand_box').css('display','block');
    }
    else {$('#recomand_box').css('display','none'); }
   /* 如果是阅读书目*/
    if(tag==23){
        $('#to_lesson').css('display','block');
        lessonid =  $('#lesson-type').val();
    }
    else {$('#to_lesson').css('display','none'); }
    console.log('文章类别'+tag);       //获取参数即文章类别
    function getUrlParam(name)
    {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r!=null) return unescape(r[2]); return null; //返回参数值
    }
    
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
    	if(tag==23){
            lessonid =  $('#lesson-type').val();
        }
        var $article_content = layedit.getContent(editIndex);
        var $article_title = $('#L_title').val();
        var $article_author = $('#L_author').val();
        var $article_link = $('#L_link').val();
        var $article_type = tag;
        var recomand_degree = $('#recommend_degree').val(); //为0表示其他文章
        console.log('发布文章type'+$article_type);
        console.log('文章标题'+$article_title);
        console.log('文章内容'+$article_content);
        console.log('文章推荐等级'+recomand_degree);
        console.log('文章所属课程id'+lessonid);
        console.log('文章所属章节'+chapternumber);
        //把投稿数据传到后台
        $.ajax({
            url: "../insert/passage",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
            data: {'link':$article_link,'annex':annex,'author':$article_author,'titile':$article_title,'content':$article_content,'type':$article_type,'recommend':recomand_degree,'lesson':lessonid,'chapter':chapternumber},    //参数值 可能不用
            type: "POST",   //请求方式
            success: function(data) {
            	layer.alert("发布文章成功", {icon: 6},function () {
                    window.history.back();
                });
            },
            error: function() {
            	layer.alert("发布文章失败", {icon: 9},function () {
                    window.history.back();
                });
            }
        });
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
      	  height: 480   //设置编辑器高度
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