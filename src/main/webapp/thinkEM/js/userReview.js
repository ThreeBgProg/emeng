
$(function () {
    layui.use(['laypage','layer','form'], function() {
        var laypage = layui.laypage;
        var layer = layui.layer;
        var form = layui.form();

        //ajax获取总页数
        $.ajax({
            url: '../getAllUnpassUser',//请求的url地址
            dataType: "json",   //返回格式为json
            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
            data: {"currentPage":1,"pageSize":10},    //可以不用
            type: "POST",   //请求方式
            success: function(data) {
                //请求成功处理
                var userL = data.userList.totalRecord;
                pages = userL%10==0?userL/10:(userL/10+1);
                laypage({
                    cont: 'page'
                    ,pages:pages //得到总页数
                    ,skip:true
                    ,jump: function (obj) {
                        laypagesuccess(obj)//跳转函数
                    }
                });
            },
            error: function(data) { 
            }
        });


        //分页跳转函数
        function  laypagesuccess(obj) {
            //obj.curr表示选择的页码*/
            console.log('当前页:'+obj.curr);
            //ajax获取视频列表
            $.ajax({
                url: "../getAllUnpassUser",    //请求的url地址
                dataType: "json",   //返回格式为json
                async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                data: { "currentPage":obj.curr,"pageSize":10},    //参数值
                type: "POST",   //请求方式
                success: function(data) {
                    //请求成功时处理
                    ajaxPageContent(data);
                },
                error: function() {
                    //请求出错处理
                    pages = data.nums;
                }
            });
        }

        //ajax请求函数（含有编辑删除处理函数）
        function ajaxPageContent(data) {

            //渲染函数（其实应该在success里面）
            var $operation = '<td class="td-manage"><a title="编辑" href="javascript:;" class="my-edit" style="text-decoration:none"><i class="layui-icon" >&#xe609;</i></a><a title="删除" href="javascript:;"  class="my-delete" style="text-decoration:none"><i class="layui-icon" >&#xe640;</i></a></td>';
            //渲染数据
            var $tbody = $('.iframe-content .layui-table tbody');
            $tbody.html("");
            var my_length = data.userList.dataList.length;
            
            for(var i = 0;i<my_length;i++){
                user = data.userList.dataList[i].user;
                school = data.userList.dataList[i].school;
                    var $tr = $('<tr></tr>');
                    $tr.data('ids',user.id);
                    $tr.append("<td class='user-jobId' style='width: 110px;'>"+user.jobId+"</td>");
                    $tr.append("<td class='user-username'  style='width: 10em'>"+user.username+"</td>");
                    if(school==null)
                        var school_name = '';
                    else school_name = school.name;
                    $tr.append("<td>"+school_name+"</td>");
                    $tr.append("<td>"+user.phone+"</td>");
                    $tr.append($operation);
                    $tbody.append($tr);           
            }
            
         // 每个编辑按钮的注册事件
            var $my_delete = $('.my-delete');
            $my_delete.on('click',userDelete);
            // 每个编辑按钮的注册事件
            var $my_edits = $('.my-edit i');
            $my_edits.on('click',userEdit);
        }
      	
        // 编辑用户处理函数，点击编辑图标的时候触发
        function userEdit() {
            var id = $(this).parents('tr').data('ids');
            console.log('用户id:'+id);
            layer.open({
                type: 2,//基本层类型
                area: ['530px', '530px'],//宽高
                fix: false, //不固定
                maxmin: true,//最大最小化
                shadeClose: true,//是否点击遮罩关闭
                shade:0.4,//遮罩
                offset: '30px',//坐标
                content: 'userReviewEdit.html',//页面内容
                end: function () {//层销毁后触发的回调
                    location.reload();
                },//请求方式
                success:function () {//层弹出后的成功回调方法
                    $.ajax({
                        url: "../findUser",    //请求的url地址
                        dataType: "json",   //返回格式为json
                        async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                        data: { "id": id},    //参数值
                        type: "POST",
                        success: function(data) {
                  
                            //请求成功处理
                        	
                            //插入数据
                            var afterJob_id = $("#layui-layer-iframe1").contents().find("div input.job_id").val();
                            $("#layui-layer-iframe1").contents().find("div input.username").val(data.user.username);
                            $("#layui-layer-iframe1").contents().find("#userIdNot").css("display","none");
                            $("#layui-layer-iframe1").contents().find("div input.password").val(data.user.password);
                            $("#layui-layer-iframe1").contents().find("div input.phone").val(data.user.phone);
                            $("#layui-layer-iframe1").contents().find("div input.mail").val(data.user.mail);
                          
                            //设置不可编辑
                            $("#layui-layer-iframe1").contents().find("div input.username").attr("disabled","disabled");
                            $("#layui-layer-iframe1").contents().find("div input.password").attr("disabled","disabled");
                            $("#layui-layer-iframe1").contents().find("div input.phone").attr("disabled","disabled");
                            $("#layui-layer-iframe1").contents().find("div input.mail").attr("disabled","disabled");

                            schoolId = data.user.schoolId;
                            /*roleId = data.role.id;
                            console.log(roleId);*/
                            //请求所属学校还有所属角色，然后将它们查到下拉框后面
                                $.ajax({
                                        url: "../getSchools",    //请求的url地址
                                        dataType: "json",   //返回格式为json
                                        async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                                        // data: { "user_id": id },    //参数值
                                        type: "POST",
                                        success:function(data){
                                            schoolSelect = $("#layui-layer-iframe1").contents().find("#schoolSelect");
                                            $.each(data,function(i,n){
                                                
                                                if(n.school.id==schoolId){
                                                	$("#layui-layer-iframe1").contents().find("#schoolSelect").append('<option value='+n.school.id+' selected>'+n.school.name+'</option>');
                                                }else{
                                                	$("#layui-layer-iframe1").contents().find("#schoolSelect").append('<option value='+n.school.id+'>'+n.school.name+'</option>');
                                                }
                                        });
                                    },
                                        error:function(){

                                        }
                                    });
                                
                                
                            //{"pageSize":10,"currentPage":1,"totalRecord":1,"totalPage":1,"dataList":[{"id":1,"rolename":"admin","state":1}]}
                            $.ajax({
                                url: "../getAllRole",    //请求的url地址
                                dataType: "json",   //返回格式为json
                                async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                                data:{"currentPage":1,"pageSize":10},
                                type: "POST",
                                success:function(data){
                                    roleSelect1 = $("#layui-layer-iframe1").contents().find("#roleSelect1");
                                    console.log(data);
                                    $.each(data,function(i,n){
                                    // option = document.createElementById ("option");
                                    
                                  /*  if(roleId==n.id){
                                    	$("#layui-layer-iframe1").contents().find("#roleSelect1").append('<option value='+n.id+' selected>'+n.rolename+'</option>');
                                    }else{*/
                                    	$("#layui-layer-iframe1").contents().find("#roleSelect1").append('<option value='+n.id+'>'+n.rolename+'</option>');
                                   // }
                                });
                            },
                                error:function(){

                                }
                            });
                            
                            
                            //提交之后的处理
                            $("#layui-layer-iframe1").contents().find("#userInfoform").on('submit',function (e) {
                            //取得修改后的值
                            $("#layui-layer-iframe1").contents().find("#schoolSelect").find("option:selected").val();
                            
                            var afterRoleId = $("#layui-layer-iframe1").contents().find("#roleSelect1").find("option:selected").val();
                            if(afterRoleId==''){
                                alert("请选择用戶角色哦！");
                            }else{
                                /*console.log("修改后名字"+afterUsername);
                                console.log("修改后Job_id"+afterJob_id);
                                console.log("修改后Password"+afterPassword);
                                console.log("修改后SchoolId"+afterSchoolId);
                                console.log("修改后Phone"+afterPhone);
                                console.log("修改后Mail"+afterMail);*/
                            	console.log("id是："+id);
                                console.log("修改后RoleId"+afterRoleId);
                                //把投稿数据传到后台
                                $.ajax({
                                    url: "../passUserRegister",    //请求的url地址
                                    dataType: "json",   //返回格式为json
                                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                                    data :{"id":id,"roleId":afterRoleId},
                                    type: "POST",   //请求方式
                                    success: function() {
                                        //请求成功处理
                                        layer.alert("审核通过", {icon: 6},function () {
                                            // 获得frame索引
                                            layer.closeAll('iframe');
                                        });
                                    },
                                    error:function(data) {
                                        //请求失败时处理
                                        alert("请求失败");
                                    }
                                });
                            }
                                return false;
                            })
                        },
                        error: function(data) {
                            //请求失败时处理
                            alert("请求失败");
                        }
                    });
                }
            });

        }
        
        /* 删除处理函数*/
        function userDelete() {
            var id = $(this).parents('tr').data('ids');
            layer.confirm('确认要删除吗？',function(index){
                //捉到被选中的，发异步进行删除
                $.ajax({
                    url: "../ignoreUserRegister",    //请求的url地址
                    dataType: "json",   //返回格式为json
                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                    data: { "id": id },    //参数值 可能不用
                    type: "POST",   //请求方式
                    success: function() {
                        //请求成功时处理
                        /*弹出删除投稿成功并刷新页面*/
                        layer.alert('删除成功', {icon: 1});
                        console.log('删除的用户id:'+id);
                        window.location.reload();
                    },
                    error: function(data) {
                        //请求出错处理
                        pages = data.nums;
                    }
                });
            });

        }

    });

})



