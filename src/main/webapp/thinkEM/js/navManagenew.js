/**
 * Created by guiliang on 2017/7/1 0001.
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

        //ajax获取链接总页数
        $.ajax({
            url: "",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
            data: { "id": "value" },    //参数值 可能不用
            type: "POST",   //请求方式
            success: function(data) {
                //请求成功时处理
                pages = data.nums;
            },
            error: function() {
                //请求出错处理
                /*alert('获取总页数失败');*/
                pages = 1;
                laypage({
                    cont: 'page'
                    ,pages:pages //得到总页数
                    ,skip:true
                    ,jump: function (obj) {
                        laypagesuccess(obj)
                    }
                });
            }
        });

        //分页跳转函数
        function  laypagesuccess(obj) {
            /* alert(obj.curr);//obj.curr表示选择的页码*/
            console.log(obj.curr);
            //ajax链接列表
            $.ajax({
                url: "../naviselect",    //请求的url地址
                dataType: "json",   //返回格式为json
                async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                data: {},/*{ "pageNum": obj.curr ,"pageSize":100},  */  //参数值 可能不用无需分页就不用传参数
                type: "POST",   //请求方式
                success: function(data) {
                    ajaxPageContent(data);
                },
                error: function() {
                    //请求出错处理


                }
            });
        }

        //ajax请求链接页函数（含有编辑删除处理函数）
        function ajaxPageContent(data) {
            var $operation = '<td class="td-manage"><a title="编辑" href="javascript:;" class="my-edit" style="text-decoration:none"><i class="layui-icon">&#xe642;</i></a><a title="删除" href="javascript:;"  class="my-delete" style="text-decoration:none"><i class="layui-icon">&#xe640;</i></a></td>';
            var $tbody = $('.iframe-content .layui-table tbody');
            $tbody.html("");
            var my_length = 7;
            console.log(data.length);
            $.each(data.slice(0,7),function (index,element) {
                var $tr = $('<tr></tr>');
                $tr.append("<td class='nav-id'>"+element.id+"</td>");
                $tr.append("<td >"+element.position+"</td>");
                $tr.append("<td>"+element.document+"</td>");
                $tr.append("<td class='limit'>"+element.link+"</td>");
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
            var id = $(this).parents('tr').children('.nav-id').text();
            console.log('navid'+id)
            layer.open({
                type: 2,
                area: ['530px', '530px'],
                fix: false, //不固定
                maxmin: true,
                shadeClose: true,
                title:id,
                shade:0.4,
                offset: '30px',
                content: 'navEdit.html',
                end: function () {
                    location.reload();
                },//请求方式
                success:function () {
                    $.ajax({
                        url: "../naviselPK",    //请求的url地址
                        dataType: "json",   //返回格式为json
                        async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                        data: { "id": id},    //参数值
                        type: "POST",
                        success: function(data) {
                            console.log(data);
                            $("#layui-layer-iframe1").contents().find("#nav_position").val(data.position);
                            $("#layui-layer-iframe1").contents().find("#nav_document").val(data.document);
                            $("#layui-layer-iframe1").contents().find("#nav_link").val(data.link);
                            $("#layui-layer-iframe1").contents().find("#nav_edit_form").on('submit',function (e) {
                                var nav_position =  $("#layui-layer-iframe1").contents().find("#nav_position").val();
                                var nav_document = $("#layui-layer-iframe1").contents().find("#nav_document").val();
                                var nav_link = $("#layui-layer-iframe1").contents().find("#nav_link").val();
                                //把投稿数据传到后台
                                $.ajax({
                                    url: "../naviupdatePKS",    //请求的url地址
                                    dataType: "json",   //返回格式为json
                                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                                    data: { "id": id,'position':nav_position,'link':nav_link,'document':nav_document  },    //参数值 可能不用
                                    type: "POST",   //请求方式
                                    success: function(data) {
                                        //请求出错处理
                                        /*alert('获取总页数失败');*/
                                        layer.alert("编辑导航成功", {icon: 6},function () {
                                            // 获得frame索引
                                            //关闭当前frame
                                            layer.closeAll('iframe');
                                        });
                                    },
                                    error: function() {
                                        layer.alert("编辑导航失败", {icon: 9},function () {
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
            /*alert(id)*/

        }

        /* 删除处理函数
         function contributeDelete() {
         var id = $(this).parents('tr').children('.nav-id').text();
         layer.confirm('确认要删除吗？',function(index){
         //捉到被选中的，发异步进行删除
         $.ajax({
         url: "http://localhost:8082/emeng/navidelPK",    //请求的url地址
         dataType: "json",   //返回格式为json
         async: true, //请求是否异步，默认为异步，这也是ajax重要特性
         data: { "id": id },    //参数值 可能不用
         type: "POST",   //请求方式
         success: function(data) {
         layer.msg('删除成功', {icon: 1},function () {
         window.location.reload();
         });
         },
         error: function() {
         layer.msg('删除失败', {icon: 6},function () {
         window.location.reload();
         });
         }
         });
         });

         }*/
        function contributeDelete() {
            var id = $(this).parents('tr').children('.nav-id').text();
            layer.confirm('确认要删除吗？',function(index){
                //捉到被选中的，发异步进行删除
                $.ajax({
                    url: "../naviupdatePKS",    //请求的url地址
                    dataType: "json",   //返回格式为json
                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                    data: { "id": id,'link':'','document':''},    //参数值 可能不用
                    type: "POST",   //请求方式
                    success: function(data) {
                        layer.msg('删除成功', {icon: 1},function () {
                            window.location.reload();
                        });
                    },
                    error: function() {
                        layer.msg('删除失败', {icon: 6},function () {
                            window.location.reload();
                        });
                    }
                });
            });

        }

        /* 添加导航*/
        $('#form_add_nav').on('submit',function (e) {
            e.preventDefault();
            var $add_nav_position = $('#add_nav_position').val();
            var $add_nav_document = $('#add_nav_document').val();
            var $add_nav_link = $('#add_nav_link').val();
            $.ajax({
                url: "../naviInsert",    //请求的url地址
                dataType: "json",   //返回格式为json
                async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                data: { "position":  $add_nav_position,'link':$add_nav_link ,'document':$add_nav_document },    //参数值 可能不用
                type: "POST",   //请求方式
                success: function(data) {
                    layer.msg('增加导航成功', {icon: 1},function () {
                        window.location.reload();
                    });
                },
                error: function() {
                    layer.msg('增加导航失败', {icon: 2},function () {
                        window.location.reload();
                    });

                }
            });
        })
    })
})