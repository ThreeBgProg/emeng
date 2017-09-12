/**
 * Created by guiliang on 2017/6/17 0017.
 */
$(function () {
    var annex = '';
    layui.use(['laypage', 'layer', 'form'], function () {
        var laypage = layui.laypage;
        var layer = layui.layer;
        var form = layui.form();


        var nums = 10;  //一页10条
        var pages = 10;  //共10页 模拟 实际用ajax获取到

        //ajax获取会议总页数
        $.ajax({
            url: "../meetingPage",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
            data: {},    //参数值 可能不用
            type: "POST",   //请求方式
            success: function(data) {
            	//请求出错处理
                /*alert('获取总页数失败');*/
                pages = data.meetingList.totalRecord%10==0?data.meetingList.totalRecord/10:(data.meetingList.totalRecord/10+1);
                laypage({
                    cont: 'page'
                    ,pages:pages //得到总页数
                    ,skip:true
                    ,jump: function (obj) {
                        laypagesuccess(obj)
                    }
                });
            },
            error: function() {
                
            }
        });

        //分页跳转函数
        function  laypagesuccess(obj) {
            /* alert(obj.curr);//obj.curr表示选择的页码*/
            console.log(obj.curr);
            //ajax链接列表
            $.ajax({
                url: "../meetingPage",    //请求的url地址
                dataType: "json",   //返回格式为json
                async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                data: { "pageNum": obj.curr ,"pageSize":10},    //参数值 可能不用
                type: "POST",   //请求方式
                success: function(data) {
                	ajaxPageContent(data);
                },
                error: function() {
                    //请求出错处理

                    
                }
            });
        }

        //ajax请求会议页函数（含有编辑删除处理函数）
        function ajaxPageContent(data) {
            var $operation = '<td class="td-manage"><a title="编辑" href="javascript:;" class="my-edit" style="text-decoration:none"><i class="layui-icon">&#xe642;</i></a><a title="删除" href="javascript:;"  class="my-delete" style="text-decoration:none"><i class="layui-icon">&#xe640;</i></a></td>';
            var $tbody = $('.iframe-content .layui-table tbody');
            $tbody.html("");
            var my_length = data.meetingList.dataList.length;
            $.each(data.meetingList.dataList,function (index,element) {
                var $tr = $('<tr></tr>');
                var type = element.type==0?'外部会议':'自举会议';
                $tr.append("<td class='meeting-id'>"+element.id+"</td>");
                $tr.append("<td class='limit'>"+element.title+"</td>");
/*                $tr.append("<td class='limit'>"+element.content+"</td>");
*/                $tr.append("<td>"+type+"</td>");
                $tr.append("<td >"+new Date(element.releaseDate).toLocaleDateString()+"</td>");
                $tr.append("<td >"+element.code+"</td>");
                $tr.append($operation);
                $tbody.append($tr);
                var $my_edits = $('.my-edit i');
                /* alert($my_edits.length);*/
                if($my_edits.length==my_length){
                    $my_edits.on('click',contributeEdit)
                }
                /*每个删除按钮注册事件*/
                var $my_delete = $('.my-delete');
                if($my_delete.length==my_length){
                    $my_delete.on('click',contributeDelete)
                }
            })
        }

        /*编辑处理函数*/
        function contributeEdit() {
            var id = $(this).parents('tr').children('.meeting-id').text();
            layer.open({
                type: 2,
                area: ['550px', '530px'],
                fix: false, //不固定
                maxmin: true,
                shadeClose: true,
                shade:0.4,
                title: id,
                offset: '30px',
                content: 'meetingEdit.html',
                end: function () {
                    location.reload();
                },//请求方式
                success:function () {
                    $.ajax({
                        url: "../meetSelByPK",    //请求的url地址
                        dataType: "json",   //返回格式为json
                        async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                        data: { "id": id },    //参数值
                        type: "POST",
                        success: function(data) {
                            //console.log(data);
                            $("#layui-layer-iframe1").contents().find("#meeting_title").val(data.title);
                            $("#layui-layer-iframe1").contents().find("#meeting_content").val(data.content);
                            $("#layui-layer-iframe1").contents().find("#meeting_link").val(data.link);
                            $("#layui-layer-iframe1").contents().find("#meeting_annex_had").val(data.annex);
                            if(data.type==true) 
                            	var data_type = 1;
                            else data_type = 0;
                            $("#layui-layer-iframe1").contents().find("#meeting_type").val(data_type);
                            $("#layui-layer-iframe1").contents().find("#meeting_time").val(new Date(data.releaseDate).toLocaleDateString());
                            $("#layui-layer-iframe1").contents().find("#meeting_code").val(data.code);

                           /* 文件上传*/
                            $("#layui-layer-iframe1").contents().find("#meeting_annex").on('change',function () {
                                var fileinput = $("#layui-layer-iframe1").contents().find("#meeting_annex");
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

                           /*表单提交*/
                            $("#layui-layer-iframe1").contents().find("#meeting_form").on('submit',function (e) {
                            	
                                var meeting_title =  $("#layui-layer-iframe1").contents().find("#meeting_title").val();
                                var meeting_content = $("#layui-layer-iframe1").contents().find("#meeting_content").val();
                                var meeting_link = $("#layui-layer-iframe1").contents().find("#meeting_link").val();
                                var meeting_type = $("#layui-layer-iframe1").contents().find("#meeting_type").val();
                                var meeting_releaseDate = $("#layui-layer-iframe1").contents().find("#meeting_time").val();
                                var meeting_code = $("#layui-layer-iframe1").contents().find("#meeting_code").val();
                                var meeting_annex_had = $("#layui-layer-iframe1").contents().find("#meeting_annex_had").val();
                                annex = annex==null?meeting_annex_had:annex;
                                $.ajax({
                                    url: "../meetupdByPKWB",    //请求的url地址
                                    dataType: "json",   //返回格式为json
                                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                                    data: { "id": id,'title':meeting_title,'content':meeting_content,'link':meeting_link,'type':meeting_type,'date':meeting_releaseDate,'code':meeting_code,'annex':annex},    //参数值 可能不用
                                    type: "POST",   //请求方式
                                    success: function(data) {
                                        //请求出错处理
                                        /*alert('获取总页数失败');*/
                                        layer.alert("编辑会议成功", {icon: 6},function () {
                                            // 获得frame索引
                                            //关闭当前frame
                                            layer.closeAll('iframe');
                                        });
                                    },
                                    error: function(){
                                    console.log('编辑会议信息失败失败');
                                        layer.alert("编辑会议失败", {icon: 9},function () {
                                            // 获得frame索引
                                            //关闭当前frame
                                            layer.closeAll('iframe');
                                        });

                                    }
                                });
                                return false;
                            })
                        },
                        error: function() {


                        }
                    });
                }
            });
            /*alert($('.layui-layer-title').html())//这里保存的是点击的文章id*/
            /*投稿内容页显示代码ajax请求*/
            var id = $('.layui-layer-title').text();
            /*alert(id)*/

        }

        /* 删除处理函数*/
        function contributeDelete() {
            var id = $(this).parents('tr').children('.meeting-id').text();
            layer.confirm('确认要删除吗？',function(index){
                //捉到被选中的，发异步进行删除
                $.ajax({
                    url: "../meetdelPK",    //请求的url地址
                    dataType: "json",   //返回格式为json
                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                    data: { "id": id },    //参数值 可能不用
                    type: "POST",   //请求方式
                    success: function(data) {
                        //请求出错处理
                        /*弹出删除投稿成功并刷新页面*/
                        console.log('删除的链接id'+id);
                        layer.msg('删除成功', {icon: 1},function () {
                            window.location.reload();
                        });
                    },
                    error: function() {

                    }
                });
            });

        }

    })
   /* 增加会议*/
    $('#meeting_add').on('click',function () {
        layer.open({
            type: 2,
            area: ['550px', '530px'],
            fix: false, //不固定
            maxmin: true,
            shadeClose: true,
            shade:0.4,
            offset: '30px',
            content: 'meetingAdd.html',
            end: function () {
                location.reload();
            },//请求方式
            success:function () {
                /*文件上传*/
                $("#layui-layer-iframe1").contents().find("#meeting_annex").on('change',function () {
                    var fileinput = $("#layui-layer-iframe1").contents().find("#meeting_annex");
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
                $("#layui-layer-iframe1").contents().find("#meeting_add_form").on('submit',function (e) {                	
                    var meeting_title =  $("#layui-layer-iframe1").contents().find("#meeting_title").val();
                    var meeting_content = $("#layui-layer-iframe1").contents().find("#meeting_content").val();
                    var meeting_link = $("#layui-layer-iframe1").contents().find("#meeting_link").val();
                    var meeting_type = $("#layui-layer-iframe1").contents().find("#meeting_type").val();
                    var meeting_releaseDate = $("#layui-layer-iframe1").contents().find("#meeting_time").val();
                    var meeting_code = $("#layui-layer-iframe1").contents().find("#meeting_code").val();
                    var formData = new FormData($("#layui-layer-iframe1").contents().find("#meeting_add_form"));
                    formData.append("annex",annex);
                    formData.append("title",meeting_title);
                    formData.append("content",meeting_content);
                    formData.append("link",meeting_link);
                    formData.append("type",meeting_type);
                    formData.append("date",meeting_releaseDate);
                    formData.append("code",meeting_code);
                    $.ajax({
                        url: "../meetinsert",    //请求的url地址
                        dataType: "json",   //返回格式为json
                        async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                        processData: false,    //必须设置
                        contentType: false,    //必须设置
                        data:formData,
                        /*data: {'title':meeting_title,'content':meeting_content,'link':meeting_link,'type':meeting_type,'release_date':new Date(meeting_releaseDate),'code':meeting_code,'annex':formData},    //参数值 可能不用*/
                        type: "POST",   //请求方式
                        success: function(data) {
                            //请求出错处理
                            /*alert('获取总页数失败');*/
                            layer.alert("添加会议成功", {icon: 6},function () {
                                // 获得frame索引
                                //关闭当前frame
                                layer.closeAll('iframe');
                            });
                        },
                        error: function(){
                            console.log('编辑会议信息失败失败');
                            layer.alert("添加会议失败", {icon: 9},function () {
                                // 获得frame索引
                                //关闭当前frame
                                layer.closeAll('iframe');
                            });

                        }
                    });
                    return false;
                })
            }
        });
    })
    
    function onprogress(evt){
        var loaded = evt.loaded;                  //已经上传大小情况 
        var tot = evt.total;                      //附件总大小 
        var per = Math.floor(100*loaded/tot);      //已经上传的百分比  
        $("#layui-layer-iframe1").contents().find("#son").html( per +"%" );
        $("#layui-layer-iframe1").contents().find("#son").css("width" , per +"%");
    }
})