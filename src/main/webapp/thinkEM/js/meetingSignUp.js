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
            url: "../applyPage",    //请求的url会议报名分页查询地址地址
            dataType: "json",   //返回格式为json
            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
            data: {},    //参数值 可能不用
            type: "POST",   //请求方式
            success: function(data) {
            	pages = data.applyList.totalRecord%10==0?data.applyList.totalRecord/10:(data.applyList.totalRecord/10+1);
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
               console.log('获取会议报名信息失败');
            }
        });

        //分页跳转函数
        function  laypagesuccess(obj) {
            /* alert(obj.curr);//obj.curr表示选择的页码*/
            console.log(obj.curr);
            //ajax链接列表
            $.ajax({
                url: "../applyPage",    //请求的url地址
                dataType: "json",   //返回格式为json
                async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                data: { "pageNum": obj.curr ,"pageSize":10},    //参数值 可能不用
                type: "POST",   //请求方式
                success: function(data) {
                	ajaxPageContent(data);
                },
                error: function() {
                    //请求出错处理
                	console.log('分页跳转失败');
                    
                }
            });
        }

        //ajax请求报名页函数（含有编辑删除处理函数）
        function ajaxPageContent(data) {
            //渲染函数（其实应该在success里面）
            var $operation = '<td><a title="删除" href="javascript:;"  class="my-delete" style="text-decoration:none"><i class="layui-icon">&#xe640;</i></a></td>';
            var $tbody = $('.iframe-content .layui-table tbody');
            $tbody.html("");
            var my_length = data.applyList.dataList.length;
            $.each(data.applyList.dataList,function (index,element) {
                var $tr = $('<tr></tr>');
                $.ajax({
                    url: "../meetSelByPK",    //请求的url地址
                    dataType: "json",   //返回格式为json
                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                    data: { "id": element.meetingId},    //参数值 可能不用
                    type: "POST",   //请求方式
                    success: function(data) {
                    	 $tr.append("<td class='sign-id'>"+element.id+"</td>");
                         $tr.append("<td >"+element.name+"</td>");
                         $tr.append("<td>"+element.meetingId+"</td>");
                         $tr.append("<td class='limit' >"+data.title+"</td>");
                         $tr.append("<td>"+element.company+"</td>");
                         $tr.append("<td>"+element.duty+"</td>");
                         $tr.append("<td>"+element.phone+"</td>");
                         $tr.append("<td class='limit'title="+element.mail+">"+element.mail+"</td>");
                         $tr.append($operation);
                         $tbody.append($tr);
                         /*每个删除按钮注册事件*/
                         var $my_delete = $('.my-delete');
                         if($my_delete.length==my_length){
                             $my_delete.on('click',contributeDelete)
                         }
                    },
                    error: function() {
                    	console.log('请求失败');
                    	 $tr.append("<td class='sign-id'>"+element.id+"</td>");
                         $tr.append("<td >"+element.name+"</td>");
                         $tr.append("<td>"+element.meetingId+"</td>");
                         $tr.append("<td class='limit' >"+"名称查询失败"+"</td>");
                         $tr.append("<td>"+element.company+"</td>");
                         $tr.append("<td>"+element.duty+"</td>");
                         $tr.append("<td>"+element.phone+"</td>");
                         $tr.append("<td class='limit'title="+element.mail+">"+element.mail+"</td>");
                         $tr.append($operation);
                         $tbody.append($tr);
                         /*每个删除按钮注册事件*/
                         var $my_delete = $('.my-delete');
                         if($my_delete.length==my_length){
                             $my_delete.on('click',contributeDelete)
                         }
                    
                    }
                });
                
               
            })
        }

        /* 删除处理函数*/
        function contributeDelete() {
            var id = $(this).parents('tr').children('.sign-id').text();
            console.log('deleteid'+id)
            layer.confirm('确认要删除吗？',function(index){
                //捉到被选中的，发异步进行删除
                $.ajax({
                    url: "../deleteByPrimaryKey",    //请求的url地址
                    dataType: "json",   //返回格式为json
                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                    data: { "id": id },    //参数值 可能不用
                    type: "POST",   //请求方式
                    success: function(data) {
                    	console.log('删除的报名id'+id);
                        layer.msg('删除成功', {icon: 1},function () {
                            window.location.reload();
                        });
                    },
                    error: function() {
                    	console.log('删除会议报名成功');
                    }
                });
            });

        }
        /* 获取会议名称*/
        function getmeetingname(id) {
        	
                //捉到被选中的，发异步进行删除
                $.ajax({
                    url: "../meetSelByPK",    //请求的url地址
                    dataType: "json",   //返回格式为json
                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                    data: { "id": id },    //参数值 可能不用
                    type: "POST",   //请求方式
                    success: function(data) {
                    	return data.title;
                    },
                    error: function() {
                    	console.log('请求失败');
                    	return "会议ID"+id;
                    }
                });

        }
        
        
        
        
    })
})