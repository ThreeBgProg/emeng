
$(function () {
    layui.use(['laypage','layer','form'], function() {
        var laypage = layui.laypage;
        var layer = layui.layer;
        var form = layui.form();

        //ajax获取总页数
        $.ajax({
            url: '../getSchoolPage',//请求的url地址
            dataType: "json",   //返回格式为json
            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
            data: { "currentPage": 1 ,"pageSize":10},    //可以不用
            type: "POST",   //请求方式
            success: function(data) {
                //请求成功处理
                var pages = data.totalPage;
                console.log("总共页数"+pages);
                
                laypage({
                    cont: 'page'
                    ,pages:pages //得到总页数
                    ,skip:true
                    ,jump: function (obj) {
                        laypagesuccess(obj)
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
            //ajax获取高校列表
            $.ajax({
                url: "../getSchoolPage",    //请求的url地址
                dataType: "json",   //返回格式为json
                async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                data: {"currentPage": obj.curr ,"pageSize":10},    //参数值
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

        var $my_add = $('.my-add');
        $my_add.on('click',schoolAdd);
        
        //ajax请求高校页函数
        function ajaxPageContent(data) {
            //渲染函数（其实应该在success里面）
            var $operation = '<td class="td-manage"><a title="编辑" href="javascript:;" class="my-edit" style="text-decoration:none"><i class="layui-icon" >&#xe642;</i></a><a title="删除" href="javascript:;"  class="my-delete" style="text-decoration:none"><i class="layui-icon" >&#xe640;</i></a></td>';
            //渲染数据   
            var $tbody = $('.iframe-content .layui-table tbody');
            $tbody.html("");
            //解决异步的问题
            var my_length = data.dataList.length;
            $.each(data.dataList,function (index,element) {
                var $tr = $('<tr></tr>');
                $tr.data('ids',element.school.id);//注意这一行，为下面是传ID，向元素附加数据，然后取回该数据
                if(element.school==null){
                	var schoolName = '';
                }else{
                	schoolName = element.school.name;
                }
                
                if(element.location==null){
                	var locationName = '';
                }else{
                	locationName = element.location.name;
                }
                $tr.append("<td class='user-name' >"+schoolName+"</td>");
                $tr.append("<td class='address' >"+locationName+"</td>");
                $tr.append($operation);
                $tbody.append($tr);

                /*每个编辑按钮的注册事件*/
                var $my_edits = $('.my-edit i');

                if($my_edits.length==my_length){
                    $my_edits.on('click',adverEdit)
                }

                /*每个删除按钮注册事件*/
                var $my_delete = $('.my-delete');
                if($my_delete.length==my_length){
                    $my_delete.on('click',adverDelete)
                }

            })
        }

        //编辑高校处理函数，点击添加图标的时候触发
        function adverEdit() {
            var id = $(this).parents('tr').data('ids');//点击的时候获得该行ID的值
            // {"advertisementList":{"pageSize":15,"currentPage":1,"totalRecord":18,"totalPage":2,"dataList":[{"id":18,"slogan":"kk","link":"kk","pic":"kk"},{"id":17,"slogan":"jj","link":"jj","pic":"jj"},{"id":16,"slogan":"hh","link":"hh","pic":"hh"},{"id":15,"slogan":"gg","link":"gg","pic":"gg"},{"id":14,"slogan":"ff","link":"ff","pic":"ff"},{"id":13,"slogan":"dd","link":"dd","pic":"dd"},{"id":12,"slogan":"cc","link":"cc","pic":"cc"},{"id":11,"slogan":"bb","link":"bb","pic":"bb"},{"id":10,"slogan":"aa","link":"aa","pic":"aa"},{"id":9,"slogan":"99","link":"99","pic":"99"},{"id":8,"slogan":"88","link":"88","pic":"88"},{"id":7,"slogan":"77","link":"77","pic":"77"},{"id":6,"slogan":"66","link":"66","pic":"66"},{"id":5,"slogan":"55","link":"55","pic":"55"},{"id":4,"slogan":"44","link":"44","pic":"44"}]}}
            layer.open({
                type: 2,
                area: ['530px', '530px'],
                fix: false, //不固定
                maxmin: true,
                shadeClose: true,
                shade:0.4,
                title: id,
                offset: '30px',
                content: 'schoolEdit.html',
                end: function () {
                    location.reload();
                },//请求方式
                success:function () {
                    $.ajax({
                        url: "../selectSchoolByPrimaryKey",    //请求的url地址
                        dataType: "json",   //返回格式为json
                        async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                        data: {'id':id},    //参数值
                        type: "POST",
                        success:  function(data) {
                            //请求成功处理
                            console.log(data);
                            $("#layui-layer-iframe1").contents().find("div input.schName").val(data.school.name);
                            $("#layui-layer-iframe1").contents().find("div input.schLink").val(data.school.link);
                            $("#layui-layer-iframe1").contents().find("div #schPreview").attr("src",data.school.pic);
                            
                            var provinceSelectId = data.school.provinceId;
                            console.log("省份ID"+provinceSelectId);
                         // 请求省份
                            $.ajax({
                                url: "../getAllLocation",    //请求的url地址
                                dataType: "json",   //返回格式为json
                                async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                                // data: { "user_id": id },    //参数值
                                type: "POST",
                                success:function(data){
                                	console.log(data);
                                    schoolSelect = $("#layui-layer-iframe1").contents().find("#schoolSelect");
                                    $.each(data,function(i,n){
                                    $("#layui-layer-iframe1").contents().find("#provinceSelect").append('<option value='+n.id+'>'+n.name+'</option>');
                                });
                                    $("#layui-layer-iframe1").contents().find("select option[value="+provinceSelectId+"]").prop("selected","selected");
                            },
                                error:function(){

                                }
                            });
                            
                            
                            //设置一开始的时候学校类型和所属省份
                            var schoolSelectType = data.school.type;
                            
                            console.log("学校种类"+schoolSelectType);
                           
                            $("#layui-layer-iframe1").contents().find("select option[value="+schoolSelectType+"]").prop("selected","selected"); //如果值一样 就选中对应的option
                            $("#layui-layer-iframe1").contents().find("select option[value="+provinceSelectId+"]").prop("selected","selected"); 

                           
                            $("#layui-layer-iframe1").contents().find("div #schPreview").css("width","100px");

                            

                            //提交之后的处理
                            $("#layui-layer-iframe1").contents().find("#schInfoform").on('submit',function (e) {
                                //取得修改后的值
                                var schName = $("#layui-layer-iframe1").contents().find("div input.schName").val();
                                var schLink = $("#layui-layer-iframe1").contents().find("div input.schLink").val();
                                var schPreview = $("#layui-layer-iframe1").contents().find("div #schPreview").attr("src");
                                var typeSelect = $("#layui-layer-iframe1").contents().find("#typeSelect").find("option:selected").val();
                                var provinceSelect = $("#layui-layer-iframe1").contents().find("#provinceSelect").find("option:selected").val();

                                console.log(schName);
                                console.log(schLink);
                                console.log(schPreview);
                                console.log(typeSelect);
                                console.log(provinceSelect);

                                $.ajax({
                                    url: "../updateSchool",    //请求的url地址
                                    dataType: "json",  //返回格式为json
                                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                                    // {"id":30,"name":"30","pic":"30","link":"30","lesson":0,"chapter":0}
                                    data :{'id':id,'name':schName,'link':schLink,'type':typeSelect,'pic':schPreview,'provinceId':provinceSelect},
                                    type: "POST",   //请求方式
                                    success: function() {
                                        // layer.close(layer.index);//先关闭当前窗口
                                        //请求成功处理
                                        layer.alert("添加成功", {icon: 6},function(index){
                                            layer.closeAll();
                                        });
                                    },
                                    error: function(data) {
                                        //请求出错时处理
                                       layer.alert('错误了哦！');
                                    }
                                });
                                return false;
                            });
                        },
                        error: function(data) {
                            //请求出错时处理
                            layer.alert('错误了哦！');
                         }
                    });
                }
        });

    }

            //增加高校信息函数
           function schoolAdd() {
            var id = $(this).parents('tr').data('ids');
            layer.open({
                type: 2,
                area: ['530px', '530px'],
                fix: false, //不固定
                maxmin: true,
                shadeClose: true,
                shade:0.4,
                title: id,
                offset: '30px',
                content: 'schoolEdit.html',
                end: function () {
                    location.reload();
                },//请求方式
                success:function () {
                    // 请求省份
                            $.ajax({
                                url: "../getAllLocation",    //请求的url地址
                                dataType: "json",   //返回格式为json
                                async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                                // data: { "user_id": id },    //参数值
                                type: "POST",
                                success:function(data){
                                    schoolSelect = $("#layui-layer-iframe1").contents().find("#schoolSelect");
                                    $.each(data,function(i,n){
                                    $("#layui-layer-iframe1").contents().find("#provinceSelect").append('<option value='+n.id+'>'+n.name+'</option>');
                                });
                                  //  $("select option[value='"+provinceSelect+"']").attr("select","selected"); //如果值一样 就选中对应的option
                            },
                                error:function(){

                                }
                            });
                    
                    $("#layui-layer-iframe1").contents().find("#schInfoform").on('submit',function (e) {
                                //取得修改后的内容
                                console.log("yy");
                                var schName = $("#layui-layer-iframe1").contents().find("div input.schName").val();
                                var schLink = $("#layui-layer-iframe1").contents().find("div input.schLink").val();
                                var schPreview = $("#layui-layer-iframe1").contents().find("div #schPreview").attr("src");
                                var typeSelect = $("#layui-layer-iframe1").contents().find("#typeSelect").find("option:selected").val();
                                var provinceSelect = $("#layui-layer-iframe1").contents().find("#provinceSelect").find("option:selected").val();

                                console.log(schName);
                                console.log(schLink);
                                console.log(schPreview);
                                console.log(typeSelect);
                                console.log(provinceSelect);

                                //把投稿数据传到后台
                                $.ajax({
                                    url: "../insertSchool",    //请求的url地址
                                    dataType: "json",  //返回格式为json
                                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                                    // {"id":30,"name":"30","pic":"30","link":"30","lesson":0,"chapter":0}
                                    data :{'name':schName,'link':schLink,'type':typeSelect,'pic':schPreview,'provinceId':provinceSelect},
                                    type: "POST",   //请求方式
                                    success: function() {
                                        // layer.close(layer.index);//先关闭当前窗口
                                        //请求成功处理
                                        layer.alert("添加成功", {icon: 6},function(index){
                                            layer.closeAll();
                                        });
                                    },
                                    error: function(data) {
                                        //请求出错时处理
                                       layer.alert('错误了哦！');
                                    }
                                });
                                return false;
                            })   
                    
                }
            });

        }

        /* 删除处理函数*/
        function adverDelete() {
            var id = $(this).parents('tr').data('ids');
            layer.confirm('确认要删除吗？',function(index){
                //捉到被选中的，发异步进行删除
                $.ajax({
                    url: "../deleteSchool",    //请求的url地址
                    dataType: "json",   //返回格式为json
                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                    data: { "id":id},    //参数值 可能不用
                    type: "POST",   //请求方式
                    success: function(data) {
                        //请求成功时处理
                    	if(data=="操作失败"){
                    		layer.msg('删除失败，因为还有该学校的用户', {icon: 1});
                    	}else{
                    		layer.msg('删除成功', {icon: 1});
                            console.log('删除的文章id:'+id)
                            window.location.reload();
                    	}
                        
                    },
                    error: function() {
                        //请求出错处理
                    }
                });
            });
        }
    });

})



