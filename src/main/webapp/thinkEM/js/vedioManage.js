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
            url: '../videoPage',//请求的url地址
            dataType: "json",   //返回格式为json
            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
            data: {"pageNum": 1 ,"pageSize":10},    //可以不用
            type: "POST",   //请求方式
            success: function(data) {
                //请求成功处理
                console.log(data.videoList);
                var vedioL = data.videoList.dataList.length;
                // console.log(vedioL);
                console.log(vedioL);
                pages = vedioL%10==0?vedioL/10:(vedioL/10+1);
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
                url: "../videoPage",    //请求的url地址
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
                    pages = data.nums;
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
            var my_length = data.videoList.dataList.length;
            $.each(data.videoList.dataList,function (index,element) {
                var $tr = $('<tr></tr>');
                $tr.data('ids',element.id);//注意这一行，为下面是传ID，向元素附加数据，然后取回该数据
                $tr.append("<td class='videoName' style='width: 110px;'>"+element.name+"</td>");
                $tr.append("<td class='picLink limit'  style='width: 10em'>"+element.pic+"</td>");
//                $tr.append("<td class='lesson'>"+element.lesson+"</td>");
                $tr.append("<td class='link limit' >"+element.link+"</td>");
                $tr.append($operation);
                $tbody.append($tr);

                /*每个编辑按钮的注册事件*/
                var $my_edits = $('.my-edit i');

                if($my_edits.length==my_length){
                    $my_edits.on('click',vedioEdit)
                }

                /*每个删除按钮注册事件*/
                var $my_delete = $('.my-delete');
                if($my_delete.length==my_length){
                    $my_delete.on('click',vedioDelete)
                }

            })
        }

        // 编辑视频处理函数，点击添加图标的时候触发
        function vedioEdit() {
            var id = $(this).parents('tr').data('ids');//点击的时候获得该行ID的值

            layer.open({
                type: 2,
                area: ['530px', '530px'],
                fix: false, //不固定
                maxmin: true,
                shadeClose: true,
                shade:0.4,
                title: id,
                offset: '30px',
                content: 'vedioEdit.html',
                end: function () {
                    location.reload();
                },//请求方式
                success:function () {
                    var lesson;
                    $.ajax({
                        url: "../videoselectPK",    //请求的url地址
                        dataType: "json",   //返回格式为json
                        async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                        data: {'id':id},    //参数值
                        type: "POST",
                        success:  function(data) {
                            var videoname = data.name;
                            var picLink = data.pic;
                            lesson = data.lesson;
                            var link = data.link;
                            chapter = data.chapter;

                            console.log("视频名称"+videoname);
                            console.log("图片链接"+picLink);
                            console.log("所属课程名称"+lesson);
                            console.log("视频链接"+link);
                            console.log("章节"+chapter);
                            $("#layui-layer-iframe1").contents().find("div input.name").val(videoname);
                            $("#layui-layer-iframe1").contents().find("div input.link").val(link);
                            $("#layui-layer-iframe1").contents().find("#preview").attr("src",picLink);

                        },
                        error: function(data) {
                            //请求出错时处理
                            layer.alert('错误了哦！');
                        }
                    });


                    //提交之后的处理
                    $("#layui-layer-iframe1").contents().find("#videoInfoform").on('submit',function (e) {
                        // //取得修改后的值
                        var afterName = $("#layui-layer-iframe1").contents().find("div input.name").val();
                        var afterPic = $("#layui-layer-iframe1").contents().find("div #preview").attr("src");
                        var afterLink = $("#layui-layer-iframe1").contents().find("div input.link").val();

                        console.log(afterPic);

                        $.ajax({
                            url: "../videoupdByPK",    //请求的url地址
                            dataType: "json",  //返回格式为json
                            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                            // {"id":30,"name":"30","pic":"30","link":"30","lesson":0,"chapter":0}
                            data :{'id':id,'name':afterName,'pic':afterPic,'link':afterLink,'lesson':1,'chapter':1,'pageNum':1,'pageSize':10},
                            type: "POST",   //请求方式
                            success: function() {
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
                content: 'vedioEdit.html',
                end: function () {
                    location.reload();
                },//请求方式
                success:function () {
                    //点击提交按钮触发
                    $("#layui-layer-iframe1").contents().find("#videoInfoform").on('submit',function (e) {
                        //取得修改后的内容
                        var afterName = $("#layui-layer-iframe1").contents().find("div input.name").val();
                        var afterPic = $("#layui-layer-iframe1").contents().find("div #preview").attr("src");
                        var afterLink = $("#layui-layer-iframe1").contents().find("div input.link").val();

                        //把投稿数据传到后台
                        console.log(afterName);
                        console.log(afterLink);
                        console.log(afterPic);

                        $.ajax({
                            url: "../videoinsert",    //请求的url地址
                            dataType: "json",  //返回格式为json
                            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                            // {"id":30,"name":"30","pic":"30","link":"30","lesson":0,"chapter":0}
                            data :{'name':afterName,'pic':afterPic,'link':afterLink,'lesson':1,'chapter':1},
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
        function vedioDelete() {
            var id = $(this).parents('tr').data('ids');
            /*删除某篇投稿ajax及接口*/
            layer.confirm('确认要删除吗？',function(index){
                // alert("真的有删除？");
                //捉到被选中的，发异步进行删除
                $.ajax({
                    url: "../videodelByPK",    //请求的url地址
                    dataType: "json",   //返回格式为json
                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                    data: { "id": id },    //参数值 可能不用
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



