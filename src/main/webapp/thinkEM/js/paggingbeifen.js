/**
 * Created by guiliang on 2017/6/13 0013.
 */
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
            }
        });


        laypage({
            cont: 'page'
            ,pages:pages //得到总页数
            ,skip:true
            ,jump: function(obj){
                /* alert(obj.curr);//obj.curr表示选择的页码*/
                console.log(obj.curr);
                //ajax获取投稿列表
                $.ajax({
                    url: "",    //请求的url地址
                    dataType: "json",   //返回格式为json
                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                    data: { "pageNum": obj.curr ,"pageSize":10},    //参数值 可能不用
                    type: "POST",   //请求方式
                    success: function(data) {
                        //请求成功时处理
                        pages = data.nums;
                    },
                    error: function() {
                        //请求出错处理
                        /* alert('获取投稿列表失败');*/
                        //渲染函数（其实应该在success里面）
                        var data = {'data':[{'id':79,'title':'我是动态获取的标题','content':'我是动态的内容而且要经过删减的是吧是的呀哈哈哈我也不知道什么鬼图片怎么办啦？','author':'老茶客','publish_time':'2016-15-15'},
                            {'id':1,'title':'我是动态获取的标题','content':'我是动态的内容而且要经过删减的是吧是的呀哈哈哈我也不知道什么鬼图片怎么办啦？','author':'老茶客','publish_time':'2016-15-15'},
                            {'id':2,'title':'我是动态获取的标题','content':'我是动态的内容而且要经过删减的是吧是的呀哈哈哈我也不知道什么鬼图片怎么办啦？','author':'老茶客','publish_time':'2016-15-15'},
                            {'id':3,'title':'我是动态获取的标题','content':'我是动态的内容而且要经过删减的是吧是的呀哈哈哈我也不知道什么鬼图片怎么办啦？','author':'老茶客','publish_time':'2016-15-15'},
                            {'id':4,'title':'我是动态获取的标题','content':'我是动态的内容而且要经过删减的是吧是的呀哈哈哈我也不知道什么鬼图片怎么办啦？','author':'老茶客','publish_time':'2016-15-15'},
                            {'id':5,'title':'我是动态获取的标题','content':'我是动态的内容而且要经过删减的是吧是的呀哈哈哈我也不知道什么鬼图片怎么办啦？','author':'老茶客','publish_time':'2016-15-15'},
                            {'id':6,'title':'我是动态获取的标题','content':'我是动态的内容而且要经过删减的是吧是的呀哈哈哈我也不知道什么鬼图片怎么办啦？','author':'老茶客','publish_time':'2016-15-15'},
                            {'id':7,'title':'我是动态获取的标题','content':'我是动态的内容而且要经过删减的是吧是的呀哈哈哈我也不知道什么鬼图片怎么办啦？','author':'老茶客','publish_time':'2016-15-15'},
                            {'id':8,'title':'我是动态获取的标题','content':'我是动态的内容而且要经过删减的是吧是的呀哈哈哈我也不知道什么鬼图片怎么办啦？','author':'老茶客','publish_time':'2016-15-15'},
                            {'id':900,'title':'我是动态获我是动态获取的标题我是动态获取的标题我是动态获取的标题我是动态获取的标题我是动态获取的标题我是动态获取的标题取的标题','content':'我是动态的而且要经过删减的是吧是的呀而且要经过删减的是吧是的呀而且要经过删减的是吧是的呀而且要经过删减的是吧是的呀而且要经过删减的是吧是的呀而且要经过删减的是吧是的呀而且要经过删减的是吧是的呀而且要经过删减的是吧是的呀内容而且要经过删减的是吧是的呀哈哈哈我也不知道什么鬼图片怎么办啦？','author':'老茶客','publish_time':'2016-15-17'}]
                        }
                        var $operation = '<td class="td-manage"><a title="编辑" href="javascript:;" class="my-edit" style="text-decoration:none"><i class="layui-icon">&#xe642;</i></a><a title="删除" href="javascript:;"  class="my-delete" style="text-decoration:none"><i class="layui-icon">&#xe640;</i></a></td>';
                        //渲染数据
                        var $tbody = $('.iframe-content .layui-table tbody');
                        $tbody.html("");
                        $.each(data.data,function (index,element) {
                            var $tr = $('<tr></tr>');
                            $tr.data('ids',element.id)
                            $tr.append("<td class='article-title' style='width: 20em'>"+element.title+"</td>");
                            $tr.append("<td class='article-content'  style='width: 30em'>"+element.content+"</td>");
                            $tr.append("<td>"+element.author+"</td>");
                            $tr.append("<td>"+element.publish_time+"</td>");
                            $tr.append($operation);
                            $tbody.append($tr);

                            /*/var $my_tr = $('.layui-table tr');
                             if($my_tr.length==11)
                             for(i=0;i<11;i++)
                             console.log($($my_tr[i]).data['ids']);*/
                            /*每个编辑按钮的注册事件*/
                            var $my_edits = $('.my-edit i');
                            /* alert($my_edits.length);*/
                            if($my_edits.length==10){
                                $my_edits.on('click',function () {
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
                                                url: "",    //请求的url地址
                                                dataType: "json",   //返回格式为json
                                                async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                                                data: { "id": id },    //参数值
                                                type: "POST",
                                                success: function(data) {
                                                    //请求成功时处理
                                                    pages = data.nums;
                                                },
                                                error: function() {
                                                    //请求出错处理
                                                    /*alert('获取投稿内容失败');*/
                                                    var data = {'id':79,'title':'我是动态获取的标题第二次ajax获取到的','content':'<p style="color:red">我是动态的内容而且要经过删减的是吧是的呀哈哈哈我也不知道什么鬼图片怎么办啦？</p><img src="images/banner.png" style="width: 200px"/>','author':'老茶客','publish_time':'2016-15-15'}//模拟返回文章
                                                    /*alert(data.title);*/
                                                    /* var a = document.getElementById('layui-layer-iframe1').contentWindow.document.getElementsByClassName('contribute-edit-title');
                                                     console.log(a)*/
                                                    $("#layui-layer-iframe1").contents().find("#title").val(data.title);
                                                    $("#layui-layer-iframe1").contents().find("#contribute-edit-content").html(data.content);
                                                    $("#layui-layer-iframe1").contents().find("#contribute-type").on('change',function () {
                                                        if($(this).val()==12){
                                                            $("#layui-layer-iframe1").contents().find("#teest").show();
                                                        }
                                                        else $("#layui-layer-iframe1").contents().find("#teest").hide();

                                                    });
                                                    /* $("#layui-layer-iframe1").contents().find("#contribute-pass").on('click',function(){
                                                     /!*alert($('.layui-layer-title').text());//文章对应的id*!/
                                                     if( ($("#layui-layer-iframe1").contents().find("#contribute-type").val()==12&&$("#layui-layer-iframe1").contents().find("#recommend_degree").val()!='')||$("#layui-layer-iframe1").contents().find("#contribute-type").val()!=12){
                                                     alert('成功')
                                                     }
                                                     })*/
                                                    /* form.on('submit(go)', function(data){
                                                     alert('submit');
                                                     return false;
                                                     });*/
                                                    /* $("#layui-layer-iframe1").contents().find("#contribute_pass").on('click',function () {
                                                     $("#layui-layer-iframe1").contents().find("#contribute_submit").click();

                                                     layer.alert("保存成功", {icon: 6},function () {
                                                     // 获得frame索引
                                                     /!* var index = parent.layer.getFrameIndex(window.name);
                                                     console.log(index)
                                                     //关闭当前frame
                                                     parent.layer.close(index);
                                                     layer.closeAll('iframe');
                                                     });

                                                     return false;

                                                     })*/
                                                    $("#layui-layer-iframe1").contents().find("#contribute_form").on('submit',function (e) {
                                                        /*console.log($('.layui-layer-title').text());
                                                         console.log($("#layui-layer-iframe1").contents().find("#contribute-type").val());
                                                         console.log($("#layui-layer-iframe1").contents().find("#recommend_degree").val());*/
                                                        var $contribute_id = $('.layui-layer-title').text();
                                                        var $contribute_type = $("#layui-layer-iframe1").contents().find("#contribute-type").val();
                                                        var $contribute_degree = $("#layui-layer-iframe1").contents().find("#contribute-type").val()==12?$("#layui-layer-iframe1").contents().find("#recommend_degree").val():null;
                                                        console.log($contribute_id);
                                                        console.log($contribute_type);
                                                        console.log($contribute_degree);
                                                        //把投稿数据传到后台
                                                        $.ajax({
                                                            url: "",    //请求的url地址
                                                            dataType: "json",   //返回格式为json
                                                            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                                                            data: { "id": $contribute_id,'type':$contribute_type,'recommond':$contribute_degree },    //参数值 可能不用
                                                            type: "POST",   //请求方式
                                                            success: function(data) {
                                                                //请求成功时处理
                                                                pages = data.nums;
                                                            },
                                                            error: function() {
                                                                //请求出错处理
                                                                /*alert('获取总页数失败');*/
                                                                layer.alert("通过投稿成功", {icon: 6},function () {
                                                                    // 获得frame索引
                                                                    //关闭当前frame
                                                                    layer.closeAll('iframe');
                                                                });
                                                            }
                                                        });
                                                        return false;
                                                    })

                                                }
                                            });
                                        }
                                    });
                                    /*alert($('.layui-layer-title').html())//这里保存的是点击的文章id*/
                                    /*投稿内容页显示代码ajax请求*/
                                    var id = $('.layui-layer-title').text();
                                    /*alert(id)*/

                                })
                            }

                            /*每个删除按钮注册事件*/
                            var $my_delete = $('.my-delete');
                            if($my_delete.length==10){
                                $my_delete.on('click',function () {
                                    var id = $(this).parents('tr').data('ids');
                                    /*alert(id);*/
                                    /*删除某篇投稿ajax及接口*/
                                    layer.confirm('确认要删除吗？',function(index){
                                        //捉到被选中的，发异步进行删除
                                        $.ajax({
                                            url: "",    //请求的url地址
                                            dataType: "json",   //返回格式为json
                                            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                                            data: { "id": id },    //参数值 可能不用
                                            type: "POST",   //请求方式
                                            success: function(data) {
                                                //请求成功时处理
                                                pages = data.nums;
                                            },
                                            error: function() {
                                                //请求出错处理
                                                /*弹出删除投稿成功并刷新页面*/
                                                layer.msg('删除成功', {icon: 1});
                                                window.location.reload();
                                            }
                                        });
                                    });

                                })
                            }
                        })
                    }
                });



            }
        });

    });

})



