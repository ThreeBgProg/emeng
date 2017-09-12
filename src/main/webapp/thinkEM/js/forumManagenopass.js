/**
 * Created by guiliang on 2017/6/25 0025.
 */
/**
 * Created by guiliang on 2017/6/25 0025.
 */
/**
 * Created by guiliang on 2017/6/17 0017.
 */
$(function () {
    layui.use(['laypage', 'layer', 'form'], function () {
        var laypage = layui.laypage;
        var layer = layui.layer;
        var form = layui.form();

        var nums = 10;  //一页10条
        var pages = 10;  //共10页 模拟 实际用ajax获取到

        //ajax获取帖子总页数
        $.ajax({
            url: "../postPage",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
            data: {},    //参数值 可能不用
            type: "POST",   //请求方式
            success: function(data) {
                //请求出错处理
                /*alert('获取总页数失败');*/
                pages = data.postList2.totalRecord%10==0?data.postList2.totalRecord/10:(data.postList2.totalRecord/10+1);
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
                url: "../postPage",    //请求的url地址
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

        //ajax请求帖子页函数（含有编辑删除处理函数）
        function ajaxPageContent(data) {
            var $operation = '<td class="td-manage"><a title="编辑" href="javascript:;" class="my-edit" style="text-decoration:none"><i class="layui-icon">&#xe642;</i></a><a title="删除" href="javascript:;"  class="my-delete" style="text-decoration:none"><i class="layui-icon">&#xe640;</i></a></td>';
            var $tbody = $('.iframe-content .layui-table tbody');
            $tbody.html("");
            var my_length = data.postList2.dataList.length;
            $.each(data.postList2.dataList,function (index,element) {
                var $tr = $('<tr></tr>');
                $tr.append("<td class='forum-id'>"+element.id+"</td>");
                $tr.append("<td class='limit'>"+element.titile+"</td>");
                /*$tr.append("<td class='limit'>"+element.content+"</td>");*/
                $tr.append("<td>"+element.username+"</td>");
                $tr.append("<td>"+new Date(element.releaseTime).toLocaleDateString()+"</td>");
                /* $tr.append("<td class='limit'>"+element.reply+"</td>");*/
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
            var id = $(this).parents('tr').children('.forum-id').text();
            layer.open({
                type: 2,
                area: ['530px', '530px'],
                fix: false, //不固定
                maxmin: true,
                shadeClose: true,
                shade:0.4,
                title: id,
                offset: '30px',
                content: 'forumTest.html',
                end: function () {
                    location.reload();
                },//请求方式
                success:function () {
                    $("#layui-layer-iframe1").contents().find("#forum_nopass").hide();
                    $.ajax({
                        url: "../postsePK",    //请求的url地址
                        dataType: "json",   //返回格式为json
                        async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                        data: { "id": id },    //参数值
                        type: "POST",
                        success: function(data) {
                            $("#layui-layer-iframe1").contents().find("#forum_username").val(data.username);
                            $("#layui-layer-iframe1").contents().find("#forum_content").html(data.content);
                            $("#layui-layer-iframe1").contents().find("#forum_pass").on('click',function (e) {
                                $.ajax({
                                    url: "../postupPKS",    //请求的url地址
                                    dataType: "json",   //返回格式为json
                                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                                    data: { "id": id,'visit':1},    //参数值 可能不用
                                    type: "POST",   //请求方式
                                    success: function(data) {
                                        //请求出错处理
                                        /*alert('获取总页数失败');*/
                                        layer.alert("重新通过帖子成功", {icon: 6},function () {
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
            /*alert($('.layui-layer-title').html())//这里保存的是点击的文章id*/
            /*投稿内容页显示代码ajax请求*/


        }

        /* 删除处理函数*/
        function contributeDelete() {
            var id = $(this).parents('tr').children('.forum-id').text();
            layer.confirm('确认要删除吗？',function(index){
                //捉到被选中的，发异步进行删除
                $.ajax({
                    url: "../postdePK",    //请求的url地址
                    dataType: "json",   //返回格式为json
                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                    data: { "id": id },    //参数值 可能不用
                    type: "POST",   //请求方式
                    success: function(data) {
                        /*弹出删除投稿成功并刷新页面*/
                        console.log('删除的帖子id'+id);
                        layer.msg('删除帖子成功', {icon: 1},function () {
                            window.location.reload();
                        });
                    },
                    error: function() {
                        layer.msg('删除帖子失败', {icon: 6},function () {
                            window.location.reload();
                        });
                    }
                });
            });

        }
    })
})