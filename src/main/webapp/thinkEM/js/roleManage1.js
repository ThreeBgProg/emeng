
$(function () {
    layui.use(['laypage','layer','form'], function() {
        var laypage = layui.laypage;
        var layer = layui.layer;
        var form = layui.form();

        //ajax获取总页数,根据不同type获取
        $.ajax({
            url: '../getAllRole',    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
            data: { "currentPage": 1 ,"pageSize":10},    //可以不用
            type: "POST",   //请求方式
            success: function(data) {
                //请求成功时处理
            	var adverL = data.length;
                // console.log(adverL);
                pages = adverL%10==0?adverL/10:(adverL/10+1);
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
                alert("请求出错了呢");
            }
        });

        //分页跳转函数
        function  laypagesuccess(obj) {
            console.log('当前页:'+obj.curr);
            //ajax获取当前权限
            $.ajax({
                url: "../getAllRole",    //请求的url地址 
                dataType: "json",   //返回格式为json
                async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                data: { "currentPage": obj.curr ,"pageSize":10},    //参数值可能不用
                type: "POST",   //请求方式
                success: function(data) {
                    //请求成功时处理
                    ajaxPageContent(data);
                },
                error: function() {
                    //请求出错处理 
                    alert("请求出错了呢");
                }
            });
        }

        //ajax请求文章页函数（含有编辑删除处理函数）
        function ajaxPageContent(data) {
            //渲染函数（其实应该在success里面）
            var $operation = '<td class="td-manage"><a title="编辑" href="javascript:;" class="my-edit" style="text-decoration:none"><i class="layui-icon">&#xe642;</i></a><a title="删除" href="javascript:;"  class="my-delete" style="text-decoration:none"><i class="layui-icon" >&#xe640;</i></a></td>';    
            //渲染数据
            //增加角色按钮
            var $my_add = $('.my-add');
            $my_add.on('click',roleAdd);
            var $tbody = $('.iframe-content .layui-table tbody');
            $tbody.html("");
            var my_length = data.length;
            $.each(data,function (index,element) {
                var $tr = $('<tr></tr>');
                $tr.data('ids',element.id)
                $tr.append("<td class='rolename' style='width: 110px;'>"+element.rolename+"</td>");
                $tr.append($operation);
                $tbody.append($tr);

                /*每个编辑按钮的注册事件*/
                var $my_edits = $('.my-edit i');
                if($my_edits.length==my_length){
                    $my_edits.on('click',roleEdit)
                }

                 /*每个删除按钮注册事件*/
                var $my_delete = $('.my-delete');
                if($my_delete.length==my_length){
                    $my_delete.on('click',roleDelete)
                }
            })
        }

        // 修改角色处理函数，点击编辑图标的时候触发
        function roleEdit() {
            var id = $(this).parents('tr').data('ids');
            var rolename = $(this).parents('tr').children(".rolename").text();
            console.log(rolename);
            layer.open({
                type: 2,
                area: ['830px', '530px'],
                fix: false, //不固定
                maxmin: true,
                shadeClose: true,
                shade:0.4,
                title: id,
                offset: '0px',
                content: 'roleEdit.html',
                end: function () {
                    location.reload();
                },//请求方式
                success:function () {
                    $.ajax({
                        url: "../getPermissionsByRole",    //请求的url地址
                        dataType: "json",   //返回格式为json
                        async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                        data: { "id": id },    //参数值
                        type: "POST",
                        success: function(data) {
                            //请求成功时处理
                            //插入数据
                            $("#layui-layer-iframe1").contents().find("div input.rolename").val(rolename);
                            
                            for(var i=0;i<data.havedPermissionList.length;i++){
                                var option = document.createElement('option');
                                option.value = data.havedPermissionList[i].id;
                                option.innerHTML = data.havedPermissionList[i].description;
                                var select2 = $("#layui-layer-iframe1").contents().find("#select2");
                                select2.append(option);
                            }


                            for(var i=0;i<data.unHavedPermissionList.length;i++){
                                var option = document.createElement('option');
                                option.value = data.unHavedPermissionList[i].id;
                                option.innerHTML = data.unHavedPermissionList[i].description;
                                var select1 = $("#layui-layer-iframe1").contents().find("#select1");
                                select1.append(option);
                            }
             
                            //提交之后的处理
                            $("#layui-layer-iframe1").contents().find("#roleInfo").on('submit',function (e) {
                            // 使用map()函数把每个option的值传递到当前匹配集合，生成包含返回值的对象；
                            // 使用 get() 处理返回的对象以得到基础的数组；
                            // 使用join()函数组装字符串。
                            var permissionListStr = $("#layui-layer-iframe1").contents().find("#select2 option").map(function(){return $(this).val();}).get().join(",");
                            var permissionList = permissionListStr.split(",");// 在每个逗号(,)处进行分解。
                            var rolename = $("#layui-layer-iframe1").contents().find("div input.rolename").val();
                            console.log(rolename);
                            console.log(permissionList);
                            console.log(permissionList instanceof Array);
                                //把投稿数据传到后台
                                $.ajax({
                                    url: "../updateRolePermission",    //请求的url地址
                                    dataType: "json",   //返回格式为json
                                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                                    data :{"id":id,"rolename":rolename,"permissionList":permissionList},
                                    type: "POST",   //请求方式
                                    success: function(data) {
                                        //请求成功时处理
                                        layer.alert("修改成功", {icon: 6},function () {
                                            // 获得frame索引
                                            layer.closeAll('iframe');
                                        });
                                    },
                                    error: function() {
                                        //请求出错处理
                                        pages = data.nums;  
                                    }
                                });
                                return false;
                            })
                         },
                        error: function(data) {
                            //请求出错处理
                            alert("请求出错了呢");
                        }
                    });
                }
            });
        }

        //增加角色信息函数
           function roleAdd() {
            var id = $(this).parents('tr').data('ids');
            layer.open({
                type: 2,
                area: ['830px', '530px'],
                fix: false, //不固定
                maxmin: true,
                shadeClose: true,
                shade:0.4,
                title: id,
                offset: '30px',
                content: 'roleEdit.html',
                end: function () {
                    location.reload();//重新刷新
                },//请求方式
                success:function () {
                    $.ajax({
                        url: "../getAllPermission",    //请求的url地址
                        dataType: "json",   //返回格式为json
                        async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                        data: { "id": id },    //参数值
                        type: "POST",
                        success: function(data) {
                            //请求成功时处理
                            for(var i=0;i<data.length;i++){
                                var option = document.createElement('option');
                                option.value = data[i].id;
                                option.innerHTML = data[i].description;
                                var select1 = $("#layui-layer-iframe1").contents().find("#select1");
                                select1.append(option);
                            }

                             $("#layui-layer-iframe1").contents().find("#roleInfo").on('submit',function (e) {
                                //把投稿数据传到后台
                                var permissionListStr = $("#layui-layer-iframe1").contents().find("#select2 option").map(function(){return $(this).val();}).get().join(",");
                                var permissionList = permissionListStr.split(",");// 在每个逗号(,)处进行分解
                                var rolename = $("#layui-layer-iframe1").contents().find("#roleInfo .rolename").val();

                                console.log(rolename);
                                console.log(permissionList);
                                console.log(permissionList instanceof Array);
                                $.ajax({
                                    url: "../addRole",    //请求的url地址
                                    dataType: "json",   //返回格式为json
                                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                                    data: { "rolename":rolename,"permissionList": permissionList},    //参数值 可能不用
                                    type: "POST",   //请求方式
                                    success: function(data) {
                                        //请求成功时处理
                                        layer.alert("添加角色成功", {icon: 6},function () {
                                            //关闭当前frame
                                            layer.closeAll('iframe');
                                        });
                                    },
                                    error: function() {
                                        //请求出错处理

                                    }
                                });
                                return false;
                            })
                        },
                        error: function() {
                            //请求出错处理  
                        }
                    });
                }
            });
        }

        /* 删除处理函数*/
        function roleDelete() {
            var id = $(this).parents('tr').data('ids');
            layer.confirm('确认要删除吗？',function(index){
                //捉到被选中的，发异步进行删除
                $.ajax({
                    url: "../deleteRole",    //请求的url地址
                    dataType: "json",   //返回格式为json
                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                    data: { "id": id },    //参数值 可能不用
                    type: "POST",   //请求方式
                    success: function(data) {
                        //请求成功时处理
                        layer.msg('删除成功', {icon: 1});
                        console.log('删除的角色id:'+id)
                        window.location.reload();
                    },
                    error: function() {
                        
                    }
                });
            });

        }
    });
})


