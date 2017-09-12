/**
 * Created by guiliang on 2017/6/10 0010.
 */
$(function () {
    layui.use(['laypage','layer','form'], function() {
        var laypage = layui.laypage;
        var layer = layui.layer;
        var form = layui.form();

        var nums = 10;  //一页10条
        var pages= 10;  //共10页 模拟 实际用ajax获取到
        var title;


        //ajax获取总页数
        $.ajax({
            url: "../submission/list",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
            data: { },    //参数值 可能不用
            type: "POST",   //请求方式
            success: function(data) {
                /*console.log(data);*/
                pages = data.submissionPage.totalRecord%10==0?data.submissionPage.totalRecord/10:(data.submissionPage.totalRecord/10+1);
                laypage({
                    cont: 'page'
                    ,pages:pages //得到总页数
                    ,skip:true
                    ,jump: function (obj,first) {
                        laypagesuccess(obj)
                    }
                });
            },
            error: function() {
                //请求出错处理
                /*alert('获取总页数失败');*/

            }
        });



        //分页跳转函数
        function  laypagesuccess(obj) {
                /* alert(obj.curr);//obj.curr表示选择的页码*/
                console.log(obj.curr);
                //ajax获取投稿列表
                $.ajax({
                    url: "../submission/list",    //请求的url地址http://localhost:8082/emeng/submission/list
                    dataType: "json",   //返回格式为json
                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                    data: { "pageNum": obj.curr ,"pageSize":10},    //参数值 可能不用
                    type: "POST",   //请求方式
                    success: function(data) {
                        //请求出错处理
                        /*console.log('返回数据失败');*/
                        ajaxPageContent(data);
                    },
                    error: function() {

                    }
                });
        }

        //ajax请求投稿页函数（含有编辑删除处理函数）
        function ajaxPageContent(data) {
            var tag = $('.iframe-content').data('tag');
            var $operation = '<td class="td-manage"><a title="编辑" href="javascript:;" class="my-edit" style="text-decoration:none"><i class="layui-icon">&#xe642;</i></a><a title="删除" href="javascript:;"  class="my-delete" style="text-decoration:none"><i class="layui-icon">&#xe640;</i></a></td>';
            //渲染数据
            var $tbody = $('.iframe-content .layui-table tbody');
            $tbody.html("");
           var my_length = data.submissionPage.dataList.length;
            $.each(data.submissionPage.dataList,function (index,element) {
                var $tr = $('<tr></tr>');
                $tr.data('ids',element.id);
                $tr.append("<td class='article-title limit' style='width: 20em'>"+element.titile+"</td>");
                $tr.append("<td>"+element.author+"</td>");
                $tr.append("<td>"+new Date(element.publishTime).toLocaleString()+"</td>");
                $tr.append($operation);
                $tbody.append($tr);

                /*/var $my_tr = $('.layui-table tr');
                 if($my_tr.length==11)
                 for(i=0;i<11;i++)
                 console.log($($my_tr[i]).data['ids']);*/
                /*每个编辑按钮的注册事件*/
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
                /*console.log($(this).parents('tr').data('ids'));*/
                var id = $(this).parents('tr').data('ids');
                var title = $(this).parents('tr').children().eq(0).text();
                var content = $(this).parents('tr').children().eq(1).text();
                layer.open({
                    type: 2,
                    area: ['530px', '530px'],
                    fix: false, //不固定
                    maxmin: true,
                    shadeClose: true,
                    shade:0.4,
                    title: id,
                    offset: '30px',
                    content: 'contributeEdit.html',
                    end: function () {
                        location.reload();
                    },//请求方式
                    success:function () {
                        $.ajax({
                            url: "../show/submission",    //请求的url地址
                            dataType: "json",   //返回格式为json
                            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                            data: { "passageId": id },    //参数值
                            type: "POST",
                            success: function(data) {
                                $("#layui-layer-iframe1").contents().find("#title").val(data.passage.titile);
                                $("#layui-layer-iframe1").contents().find("#annex").val(data.passage.annex);
                                $("#layui-layer-iframe1").contents().find("#author").val(data.passage.author);
                                $("#layui-layer-iframe1").contents().find("#contribute-edit-content").html(data.passage.content);
                                $("#layui-layer-iframe1").contents().find("#contribute-type").on('change',function () {
                                    if($(this).val()==12){
                                        $("#layui-layer-iframe1").contents().find("#teest").show();
                                    }
                                    else $("#layui-layer-iframe1").contents().find("#teest").hide();

                                });
                                $("#layui-layer-iframe1").contents().find("#contribute_form").on('submit',function (e) {
                                    var $title =  $("#layui-layer-iframe1").contents().find("#title").val();
                                    var $content =  $("#layui-layer-iframe1").contents().find("#contribute-edit-content").html();
                                    var $contribute_id = $('.layui-layer-title').text();
                                    var $contribute_type = $("#layui-layer-iframe1").contents().find("#contribute-type").val();
                                    var $contribute_degree = $("#layui-layer-iframe1").contents().find("#contribute-type").val()==12?$("#layui-layer-iframe1").contents().find("#recommend_degree").val():0;
                                    console.log('通过的投稿');
                                    console.log('id'+$contribute_id);
                                    console.log('title'+$title);
                                    console.log('content'+$content);
                                    console.log('type'+$contribute_type);
                                    console.log('degree'+$contribute_degree);
                                    console.log(data);
                                    //把投稿数据传到后台
                                    $.ajax({
                                        url: "../pass/submission",    //请求的url地址
                                        dataType: "json",   //返回格式为json
                                        async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                                        data:{ "id": $contribute_id,'type':$contribute_type,'recommnd':$contribute_degree,'titile':$title,'content':$content,'annex':data.passage.annex,'author':data.passage.author,'chapter':data.passage.chapter,'lesson':data.passage.lesson,'link':data.passage.link,'state':1},    //参数值 可能不用
                                        type: "POST",   //请求方式
                                        success: function(data) {
                                        	//请求出错处理
                                            /*alert('获取总页数失败');*/
                                            layer.alert("通过投稿成功", {icon: 6},function () {
                                                // 获得frame索引
                                                //关闭当前frame
                                                layer.closeAll('iframe');
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
                    }
                });
               
        }

       /* 删除处理函数*/
       function contributeDelete() {
           var id = $(this).parents('tr').data('ids');
           layer.confirm('确认要删除吗？',function(index){
               //捉到被选中的，发异步进行删除
               $.ajax({
                   url: "../delete/passage",    //请求的url地址
                   dataType: "json",   //返回格式为json
                   async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                   data: { "passageId": id },    //参数值 可能不用
                   type: "POST",   //请求方式
                   success: function(data) {
                	 //请求出错处理
                       /*弹出删除投稿成功并刷新页面*/
                       console.log(id);
                       layer.msg('删除成功', {icon: 1},function () {
                           window.location.reload();
                       });
                   },
                   error: function() {
                	 //请求出错处理
                       /*弹出删除投稿成功并刷新页面*/
                       console.log(id);
                       layer.msg('删除失败', {icon: 6},function () {
                           window.location.reload();
                       });
                   }
               });
           });

       }

    });

})



