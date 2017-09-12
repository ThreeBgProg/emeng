/**
 * Created by guiliang on 2017/6/17 0017.
 */
$(function () {

    var url = location.href;    //获取iframe的url
    var lesson_id= getUrlParam('lessonid');    //获取参数即课程id
    var chapter_number= getUrlParam('chapternumber');    //获取参数即章节
    var passage_type = getUrlParam('type');
    console.log('url传过来的a'+lesson_id+chapter_number+passage_type);
    function getUrlParam(name)
    {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r!=null) return unescape(r[2]); return null; //返回参数值
    }


    layui.use(['laypage','layer','form','layedit'], function() {
        var laypage = layui.laypage;
        var layer = layui.layer;
        var form = layui.form();
        layedit = layui.layedit;
        var current_page;

        var nums = 10;  //一页10条
        var pages = 10;  //共10页 模拟 实际用ajax获取到
        var title;

        //ajax获取总页数,根据不课程id和章节号获取
        $.ajax({
            url: "../"+lesson_id+"/"+chapter_number+"/blockpassagelist",    //请求的url地址"http://localhost:8082/emeng/"+tag+"/chapter"  '/{lessonId}/{chapterId}/blockpassagelist'
            dataType: "json",   //返回格式为json
            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
            data: { "passageType": passage_type},    //可以不用
            type: "POST",   //请求方式
            success: function(data) {
            	 pages = data.pagingResult.totalRecord%10==0?data.pagingResult.totalRecord/10:(data.pagingResult.totalRecord/10+1);
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
                //请求出错处理
            }
        });

        //分页跳转函数
        function  laypagesuccess(obj) {
            /* alert(obj.curr);//obj.curr表示选择的页码*/
            console.log('当前页:'+obj.curr);
            //ajax获取文章列表
            $.ajax({
                url: "../"+lesson_id+"/"+chapter_number+"/blockpassagelist",    //请求的url地址  url_link_passages[tag]
                dataType: "json",   //返回格式为json
                async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                data: { "pageNum": obj.curr ,"pageSize":10,"passageType": passage_type},    //参数值 可能不用
                type: "POST",   //请求方式
                success: function(data) {
                	ajaxPageContent(data);
                },
                error: function() {
                    //请求出错处理

                    
                }
            });
        }

        //ajax请求文章页函数（含有编辑删除处理函数）
        function ajaxPageContent(data) {
            var $operation = '<td class="td-manage"><a title="编辑" href="javascript:;" class="my-edit" style="text-decoration:none"><i class="layui-icon">&#xe642;</i></a><a title="删除" href="javascript:;"  class="my-delete" style="text-decoration:none"><i class="layui-icon">&#xe640;</i></a></td>';
            var $tbody = $('.iframe-content .layui-table tbody');
            $tbody.html("");
            var my_length = data.pagingResult.dataList.length;
            $.each(data.pagingResult.dataList,function (index,element) {
                var $tr = $('<tr></tr>');
                $tr.data('ids',element.id);
                $tr.append("<td class='article-title' style='width: 20em'>"+element.titile+"</td>");
                $tr.append("<td>"+element.author+"</td>");
                $tr.append("<td>"+new Date(element.publishTime).toLocaleString()+"</td>");
                $tr.append($operation);
                $tbody.append($tr);

                /*每个编辑按钮的注册事件*/
                var $my_edits = $('.my-edit i');
                /* alert($my_edits.length);*/
                if($my_edits.length==my_length){
                    $my_edits.on('click',articleEdit)
                }

                /*每个删除按钮注册事件*/
                var $my_delete = $('.my-delete');
                if($my_delete.length==my_length){
                    $my_delete.on('click',articleDelete)
                }
            })

        }

        /*编辑处理函数*/
        function articleEdit() {
            /*console.log($(this).parents('tr').data('ids'));*/
            var id = $(this).parents('tr').data('ids');
            console.log('编辑的文章id:'+id);
            var current_href = location.href;
            console.log('href'+current_href);
            /* http://localhost:63342/thinkEM/latestInformation.html&flag=1*/
            var new_href = '../thinkEM/articleEdit.html?id='+id+'&type='+passage_type+'&lessonid='+lesson_id+'&chapternumber='+chapter_number;
            console.log('newhref'+new_href);
            location.href =  new_href;
            /*alert($('.layui-layer-title').html())//这里保存的是点击的文章id*/
            /*投稿内容页显示代码ajax请求*/
            /*var id = $('.layui-layer-title').text();*/
            /*alert(id)*/

        }

        /* 删除处理函数*/
        function articleDelete() {
            var id = $(this).parents('tr').data('ids');
            /*alert(id);*/
            /*删除某篇投稿ajax及接口*/
            layer.confirm('确认要删除吗？',function(index){
                //捉到被选中的，发异步进行删除
                $.ajax({
                    url: "../delete/passage",    //请求的url地址
                    dataType: "json",   //返回格式为json
                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                    data: { "passageId": id },
                    type: "POST",   //请求方式
                    success: function(data) {
                    	  /*弹出删除投稿成功并刷新页面*/
                        console.log(id);
                        layer.msg('删除成功', {icon: 1},function () {
                            window.location.reload();
                        });
                    },
                    error: function() {
                        //请求出错处理
                    	layer.msg('删除失败', {icon: 1},function () {
                            window.location.reload();
                        });
                    }
                });
            });

        }

    })

    /*发布文章注册事件*/
    $('#passage_add').on('click',function () {
        var new_href =  '../thinkEM/articleAdd.html?lessonid='+lesson_id+'&chapternumber='+chapter_number+'&passagetype='+passage_type;
        console.log('newhref'+new_href);
        location.href =  new_href;
    })
})