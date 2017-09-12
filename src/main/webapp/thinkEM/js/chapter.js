/**
 * Created by guiliang on 2017/6/17 0017.
 */
$(function () {
    var tag = $('.iframe-content').data('tag');
    layui.use(['laypage', 'layer', 'form', 'layedit'], function () {
        var laypage = layui.laypage;
        var layer = layui.layer;
        var form = layui.form();
        layedit = layui.layedit;
        var current_page;

        var nums = 10;  //一页10条
        var pages = 10;  //共10页 模拟 实际用ajax获取到
        var title;


        //ajax获取总页数,根据不同课程获取
        $.ajax({
            url: "../"+tag+"/chapter",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
            type: "POST",   //请求方式
            success: function(data) {
                pages = data.chapterList.totalRecord%10==0?data.chapterList.totalRecord/10:(data.chapterList.totalRecord/10+1);
                laypage({
                    cont: 'page'
                    ,pages:pages //得到总页数
                    ,skip:true
                    ,jump: function (obj,first) {
                        current_page = obj.curr;
                        laypagesuccess(obj);
                    }
                });
            },
            error: function() {
           
            }
        });

        //分页跳转函数
        function  laypagesuccess(obj) {
            /* alert(obj.curr);//obj.curr表示选择的页码*/
            console.log('当前页:'+obj.curr);
            //ajax获取文章列表
            $.ajax({
                url: "../"+tag+"/chapter",    //请求的url地址  url_link_passages[tag]
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

        //ajax请求章节页函数（含有编辑跳转处理函数）
        function ajaxPageContent(data) {
            var $operation = '<td class="td-manage"><a title="编辑" href="javascript:;" class="my-edit" style="text-decoration:none"><i class="layui-icon">&#xe642;</i></a><a title="下一级" href="javascript:;" class="my-next" style="text-decoration:none"><i class="layui-icon">&#xe602;</i></a></td>';
            var $tbody = $('.iframe-content .layui-table tbody');
            $tbody.html("");
            var my_length = data.chapterList.dataList.length;
            $.each(data.chapterList.dataList,function (index,element) {
                var $tr = $('<tr></tr>');
                $tr.append("<td class='chapter-id'>"+element.id+"</td>");
                $tr.append("<td class='chapter-num'>"+element.number+"</td>");
                $tr.append("<td class='chapter-name'>"+element.name+"</td>");
                $tr.append($operation);
                $tbody.append($tr);
                var $my_edits = $('.my-edit i');
                /* alert($my_edits.length);*/
                if($my_edits.length==my_length){
                    $my_edits.on('click',contributeEdit)
                }

                var $my_nexts = $('.my-next i');
                /* alert($my_edits.length);*/
                if($my_nexts.length==my_length){
                    $my_nexts.on('click',toArticleList)
                }
            })
        }

        /*编辑处理函数*/
        function contributeEdit() {
            //获得课程名/(id)和章节序号
            var lesson = tag;
            var id = $(this).parents('tr').children('.chapter-id').text();
            var number = $(this).parents('tr').children('.chapter-num').text();
            var name = $(this).parents('tr').children('.chapter-name').text();
            layer.open({
                type: 2,
                area: ['530px', '530px'],
                fix: false, //不固定
                maxmin: true,
                shadeClose: true,
                shade:0.4,
                title: id,
                offset: '30px',
                content: 'chapterEdit.html',
                end: function () {
                    location.reload();
                },//请求方式
                success:function () {



                            var lessonlist = ['','基础','概论','原理','纲要'];

                            $("#layui-layer-iframe1").contents().find("#chapter_num").val(number);
                            $("#layui-layer-iframe1").contents().find("#chapter_name").val(name);
                            $("#layui-layer-iframe1").contents().find("#chapter_lesson").val(lessonlist[tag]);
                            $("#layui-layer-iframe1").contents().find("#chapter_edit_form").on('submit',function (e) {
                                var chapter_num=  $("#layui-layer-iframe1").contents().find("#chapter_num").val();
                                var chapter_name = $("#layui-layer-iframe1").contents().find("#chapter_name").val();
                                //把章节数据传到后台
                                $.ajax({
                                    url: "../update/chapter",    //请求的url地址
                                    dataType: "json",   //返回格式为json
                                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                                    data: { 'id':id,"lesson": tag,'number':chapter_num,'name':chapter_name},    //参数值 可能不用
                                    type: "POST",   //请求方式
                                    success: function(data) {
                                        //请求出错处理
                                        /*alert('获取总页数失败');*/
                                        layer.alert("编辑章节成功", {icon: 6},function () {
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



                }
            });
            /*alert($('.layui-layer-title').html())//这里保存的是点击的文章id*/
            /*投稿内容页显示代码ajax请求*/
            var id = $('.layui-layer-title').text();
            /*alert(id)*/

        }

       /* 跳转指定课程章节文章函数*/
        function toArticleList() {
            var number = $(this).parents('tr').children('.chapter-num').text();
            console.log('跳转课程id'+tag+'跳转章节'+number);
            var new_href = '../thinkEM/glorysix.html?lessonid='+tag+'&chapternumber='+number;
            console.log('newhref'+new_href);
            location.href =  new_href;
        }

        })
    /*发布章节处理函数*/
    $('#chapter_add').on('click',function () {
        layer.open({
            type: 2,
            area: ['530px', '530px'],
            fix: false, //不固定
            maxmin: true,
            shadeClose: true,
            shade:0.4,
            title:tag,
            offset: '30px',
            content: 'chapterAdd.html',
            end: function () {
                location.reload();
            },//请求方式
            success:function () {
                var lesson_list = ['','基础','概论','原理','纲要']
                var chapter_name = $("#layui-layer-iframe1").contents().find("#chapter_lesson").val(lesson_list[tag]);
                $("#layui-layer-iframe1").contents().find("#chapter_add_form").on('submit',function (e) {
                    var chapter_num=  $("#layui-layer-iframe1").contents().find("#chapter_num").val();
                    var chapter_name = $("#layui-layer-iframe1").contents().find("#chapter_name").val();
                    $.ajax({
                        url: "../insert/chapter",    //请求的url地址
                        dataType: "json",   //返回格式为json
                        async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                        data: { "lesson": tag,'number':chapter_num,'name':chapter_name},    //参数值 可能不用
                        type: "POST",   //请求方式
                        success: function(data) {
                        	//请求出错处理
                            /*alert('获取总页数失败');*/
                            layer.alert("添加章节成功", {icon: 6},function () {
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

            }
        });

    });
})
