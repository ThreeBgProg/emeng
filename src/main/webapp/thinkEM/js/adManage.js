/**
 * Created by guiliang on 2017/6/14 0014.
 */
$(function () {
    layui.use(['laypage','layer','form'], function() {
        var laypage = layui.laypage;
        var layer = layui.layer;
        var form = layui.form();
        
        //ajax获取总页数
        $.ajax({
            url: '../adverPage',//请求的url地址
            dataType: "json",   //返回格式为json
            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
            data: { "pageNum": 1 ,"pageSize":10},    //可以不用
            type: "POST",   //请求方式
            success: function(data) {
                //请求成功处理
                console.log(data.advertisementList);
                var adverL = data.advertisementList.dataList.length;
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
            error: function(data) { 
            	
            }
        });

        //分页跳转函数
        function  laypagesuccess(obj) {
        	//obj.curr表示选择的页码*/
            console.log('当前页:'+obj.curr);
            //ajax获取视频列表
            $.ajax({
                url: "../adverPage",    //请求的url地址
                dataType: "json",   //返回格式为json
                async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                data: { "pageNum": obj.curr ,"pageSize":10},    //参数值
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
        $my_add.on('click',vedioAdd);
        
        //ajax请求视频页函数
        function ajaxPageContent(data) {
            //渲染函数（其实应该在success里面）

            var $operation = '<td class="td-manage"><a title="编辑" href="javascript:;" class="my-edit" style="text-decoration:none"><i class="layui-icon" >&#xe642;</i></a><a title="删除" href="javascript:;"  class="my-delete" style="text-decoration:none"><i class="layui-icon" >&#xe640;</i></a></td>';
            //渲染数据   
            var $tbody = $('.iframe-content .layui-table tbody');
            $tbody.html("");
            var my_length = data.advertisementList.dataList.length;
            $.each(data.advertisementList.dataList,function (index,element) {
                var $tr = $('<tr></tr>');
                $tr.data('ids',element.id);//注意这一行，为下面是传ID，向元素附加数据，然后取回该数据
                $tr.append("<td class='user-name' >"+element.slogan+"</td>");
                $tr.append("<td class='user-link limit'>"+element.link+"</td>");
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

        //编辑视频处理函数，点击添加图标的时候触发
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
                content: 'adEdit.html',
                end: function () {
                    location.reload();
                },//请求方式
                success:function () {
                    $.ajax({
                        url: "../adverselectByPK",    //请求的url地址
                        dataType: "json",   //返回格式为json
                        async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                        data: {'id':id},    //参数值
                        type: "POST",
                        success:  function(data) {
                            //请求成功处理
                            //$("#layui-layer-iframe1").contents().find("div input.id").val(data.id);
                             console.log(data);
                             // {"id":18,"slogan":"kk","link":"kk","pic":"kk"}
                            $("#layui-layer-iframe1").contents().find("div input.adname").val(data.slogan);
                            $("#layui-layer-iframe1").contents().find("div #preview").attr("src",data.pic);
                            $("#layui-layer-iframe1").contents().find("div input.adlink").val(data.link);
   
                            //提交之后的处理
                            $("#layui-layer-iframe1").contents().find("#adInfoform").on('submit',function (e) {
                                // //取得修改后的值
                                var afterSlogan = $("#layui-layer-iframe1").contents().find("div input.adname").val();
                                var afterPic = $("#layui-layer-iframe1").contents().find("div #preview").attr("src");
                                var afterLink = $("#layui-layer-iframe1").contents().find("div input.adlink").val();

                                // var afterLesson = $("#layui-layer-iframe1").contents().find("div input.lesson").val();
                                // var afterChapter = $("#layui-layer-iframe1").contents().find("div input.chapter").val();
                                console.log(afterSlogan);
                                console.log(afterPic);
                                console.log(afterLink);
                                console.log("zhende");
                                $.ajax({
                                    url: "../adverupdateByPK",    //请求的url地址
                                    dataType: "json",  //返回格式为json
                                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                                    // {"id":30,"name":"30","pic":"30","link":"30","lesson":0,"chapter":0}
                                    data :{"id":id,"slogan":afterSlogan,"link":afterLink,"pic":afterPic,"pageNum":1,"pageSize":10},
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

            //增加视频信息函数
           function vedioAdd() {
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
                content: 'adEdit.html',
                end: function () {
                    location.reload();
                },//请求方式
                success:function () {
                    $("#layui-layer-iframe1").contents().find("#adInfoform").on('submit',function (e) {
                                //取得修改后的内容
                                var afterSlogan = $("#layui-layer-iframe1").contents().find("div input.adname").val();
                                var afterPic = $("#layui-layer-iframe1").contents().find("div #preview").attr("src");
                                var afterLink = $("#layui-layer-iframe1").contents().find("div input.adlink").val();

                                //把投稿数据传到后台
                                $.ajax({
                                    url: "../adverinsert",    //请求的url地址
                                    dataType: "json",  //返回格式为json
                                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                                    // {"id":30,"name":"30","pic":"30","link":"30","lesson":0,"chapter":0}
                                    data :{"slogan":afterSlogan,"link":afterLink,"pic":afterPic},
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
                // alert("真的有删除？");
                //捉到被选中的，发异步进行删除
                $.ajax({
                    url: "../adverdelPK",    //请求的url地址
                    dataType: "json",   //返回格式为json
                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                    data: { "id":id, "pageNum":1,"pageSize":10},    //参数值 可能不用
                    type: "POST",   //请求方式
                    success: function(data) {
                        //请求成功时处理
                        layer.msg('删除成功', {icon: 1});
                        console.log('删除的文章id:'+id)
                        window.location.reload();
                    },
                    error: function() {
                        //请求出错处理
                    }
                });
            });
        }
    });

})



