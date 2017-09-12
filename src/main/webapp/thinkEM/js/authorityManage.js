
$(function () {
    layui.use(['laypage','layer','form'], function() {
        var laypage = layui.laypage;
        var layer = layui.layer;
        var form = layui.form();

        //ajax获取总页数,根据不同type获取
        $.ajax({
            url: '../getAllPermissionByPage',    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
            data: { "currentPage": 1 ,"pageSize":10},    //可以不用
            type: "POST",   //请求方式
            success: function(data) {
                //请求成功时处理
                pages = data.totalPage;
                console.log("总页数："+pages);
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
                //请求出错处理
            }
        });

        //分页跳转函数
        function  laypagesuccess(obj) {
            console.log('当前页:'+obj.curr);
            $.ajax({
                url: "../getAllPermissionByPage",    //请求的url地址  url_link_passages[tag]
                dataType: "json",   //返回格式为json
                async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                data: { "currentPage": obj.curr ,"pageSize":10},    //参数值 可能不用
                type: "POST",   //请求方式
                success: function(data) {
                    //请求成功时处理
                    ajaxPageContent(data);
                },
                error: function() {
                    //请求出错处理
                }
            });
        }

        //ajax请求文章页函数（含有编辑删除处理函数）
        function ajaxPageContent(data) {
            var $operation = '<td class="td-manage"><a title="编辑" href="javascript:;" class="my-edit" style="text-decoration:none"><i class="layui-icon" style="margin:0 20px;">&#xe642;</i></a></td>';
            //渲染数据
            var $tbody = $('.iframe-content .layui-table tbody');
            $tbody.html("");
            var my_length = data.dataList.length;
            $.each(data.dataList,function (index,element) {
                var $tr = $('<tr></tr>');
                $tr.data('ids',element.id)
                $tr.append("<td class='mapping limit' style='width: 110px;'>"+element.mapping+"</td>");
                $tr.append("<td class='description limit'  style='width: 10em'>"+element.description+"</td>");
                $tr.append("<td class='function limit'>"+element.function+"</td>");
                $tr.append($operation);
                $tbody.append($tr);
                /*每个编辑按钮的注册事件*/
                var $my_edits = $('.my-edit i');
                /* alert($my_edits.length);*/
                if($my_edits.length==my_length){
                    $my_edits.on('click',authorityEdit)
                }
            })
        }

        // 编辑用户处理函数，点击添加图标的时候触发
        function authorityEdit() {
            var id = $(this).parents('tr').data('ids');
            console.log('编辑的URlid:'+id);
            var mapping1 = $(this).parents('tr').children('td.mapping').text();
            var fun1 = $(this).parents('tr').children('td.function').text();

            layer.open({
                type: 2,
                area: ['530px', '530px'],
                fix: false, //不固定
                maxmin: true,
                shadeClose: true,
                shade:0.4,
                title: id,
                offset: '30px',
                content: 'authorityEdit.html',
                end: function () {
                    location.reload();
                },//请求方式
                success:function () {
                            //插入数据
                            $("#layui-layer-iframe1").contents().find("#authorityInfoform input.id").val(mapping1);
                            $("#layui-layer-iframe1").contents().find("#authorityInfoform textarea.text").val(fun1);
                            //提交之后的处理
                            $("#layui-layer-iframe1").contents().find("#authorityInfoform").on('submit',function (e) {
                            //取得修改后的值
                            var afterMapping = $("#layui-layer-iframe1").contents().find("#authorityInfoform input.id").val();
                            var afterFun = $("#layui-layer-iframe1").contents().find("#authorityInfoform textarea.text").val();
                            
                            console.log(afterFun);
                            console.log(afterMapping);
                                //把数据传到后台
                                $.ajax({
                                    url: "../updatePermission",    //请求的url地址
                                    dataType: "json",   //返回格式为json
                                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                                    data :{"id":id,"function":afterFun,"mapping":afterMapping},
                                    type: "POST",   //请求方式
                                    success: function(data) {
                                        //请求成功时处理
                                        layer.alert("修改成功", {icon: 6},function () {
                                            //获得frame索引
                                            layer.closeAll('iframe');
                                        });
                                    },
                                    error: function() {
                                        //请求出错处理
                                       alert("请求失败啦");
                                    }
                                });
                                return false;
                            });

                }
            });

        }
    });
})



