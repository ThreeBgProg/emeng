/**
 * Created by guiliang on 2017/6/16 0016.
 */
$(function () {
    layui.use(['laypage','layer','form'], function() {
        var laypage = layui.laypage;
        var layer = layui.layer;
        var form = layui.form();

        var nums = 10;  //一页10条
        var pages = 10;  //共10页 模拟 实际用ajax获取到
        //ajax获取链接总页数
        $.ajax({
            url: '../linkPage',   //请求的链接管理总页数url地址"http://localhost:8080/emeng/linkPage",
            dataType: "json",   //返回格式为json
            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
            data: {},    //不用
            type: "POST",   //请求方式
            success: function(data) {   //linkList为链接请求数据返回的json名字
               /* console.log(data.linkList.totalRecord);*/
                pages = data.linkList.totalRecord%10==0?data.linkList.totalRecord:(data.linkList.totalRecord/10+1);
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
            /* alert(obj.curr);//obj.curr表示选择的页码*/
            //ajax链接列表
            $.ajax({
                url: "../linkPage",    //请求的url地址
                dataType: "json",   //返回格式为json
                async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                data: { "pageNum": obj.curr,"pageSize":10},    //参数值 可能不用
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

        //ajax请求链接页函数（含有编辑删除处理函数）
        function ajaxPageContent(data) {
        	console.log(data.linkList.dataList);
            var $operation = '<td class="td-manage"><a title="编辑" href="javascript:;" class="my-edit" style="text-decoration:none"><i class="layui-icon">&#xe642;</i></a><a title="删除" href="javascript:;"  class="my-delete" style="text-decoration:none"><i class="layui-icon">&#xe640;</i></a></td>';
            var $tbody = $('.iframe-content .layui-table tbody');
            $tbody.html("");
            var my_length = data.linkList.dataList.length;
            $.each(data.linkList.dataList,function (index,element) {
                var $tr = $('<tr></tr>');
                $tr.append("<td class='link-id'>"+element.id+"</td>");
                $tr.append("<td >"+element.name+"</td>");
                $tr.append("<td class='limit'>"+element.link+"</td>");
                $tr.append("<td>"+element.order+"</td>");
                $tr.append($operation);
                $tbody.append($tr);

                var $my_edits = $('.my-edit i');
                /* alert($my_edits.length);*/
                if($my_edits.length==my_length){
                    $my_edits.on('click',contributeEdit)
                }
                /*每个删除按钮注册事件*/
                var $my_delete = $('.my-delete');
                if($my_delete.length==my_length){
                    $my_delete.on('click',contributeDelete)
                }
            })
        }

        /*编辑处理函数*/
        function contributeEdit() {
            var id = parseInt($(this).parents('tr').children('.link-id').text());
            layer.open({
                type: 2,
                area: ['530px', '530px'],
                fix: false, //不固定
                maxmin: true,
                shadeClose: true,
                shade:0.4,
                title: id,
                offset: '30px',
                content: 'linkEdit.html',
                end: function () {
                    location.reload();
                },//请求方式
                success:function () {
                	console.log('bianjiid'+id);
                	id = parseInt(id);
                	console.log(typeof(id));
                    $.ajax({
                        url: "../linksselectPK",    //请求的url地址
                        dataType: "json",   //返回格式为json
                        async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                         data: { "id": id},    //参数值
                        type: "POST",
                        success: function(data) {
                            //请求成功时处理
                        	console.log(data.links);
                        	 console.log(113);
                        	  $("#layui-layer-iframe1").contents().find("#link_name").val(data.name);
                              $("#layui-layer-iframe1").contents().find("#link").val(data.link);
                              $("#layui-layer-iframe1").contents().find("#link_sort").val(data.order);
                              $("#layui-layer-iframe1").contents().find("#link_edit_form").on('submit',function (e) {
                                  var link_name =  $("#layui-layer-iframe1").contents().find("#link_name").val();
                                  var link = $("#layui-layer-iframe1").contents().find("#link").val();
                                  var link_sort = $("#layui-layer-iframe1").contents().find("#link_sort").val();
                                  //把投稿数据传到后台
                                  $.ajax({
                                      url: "../linksupdPK",    //请求的url地址
                                      dataType: "json",   //返回格式为json
                                      async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                                      data: { "id": id,'name':link_name,'link':link,'order':link_sort },    //参数值 可能不用
                                      type: "POST",   //请求方式
                                      success: function(data) {
                                          //请求出错处理
                                          /*alert('获取总页数失败');*/
                                          layer.alert("编辑链接成功", {icon: 6},function () {
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
                        },
                        error: function() {
                          
                        	/*console.log('返回id指定链接失败');*/
                        }
                    });
                }
            });
            /*alert($('.layui-layer-title').html())//这里保存的是点击的文章id*/
            /*投稿内容页显示代码ajax请求*/
            var id = $('.layui-layer-title').text();
            /*alert(id)*/

        }

        /* 删除处理函数*/
        function contributeDelete() {
            var id = $(this).parents('tr').children('.link-id').text();
            layer.confirm('确认要删除吗？',function(index){
                //捉到被选中的，发异步进行删除
                $.ajax({
                    url: "../linksdeletePK",    //请求的url地址
                    dataType: "json",   //返回格式为json
                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                    data: { "id": id },    //参数值 可能不用
                    type: "POST",   //请求方式
                    success: function(data) {
                        //请求成功时处理
                    	console.log(data);
                        console.log('删除的链接id'+id);
                        layer.msg('删除成功', {icon: 1},function () {
                            window.location.reload();
                        });
                    },
                    error: function() {
                        //请求出错处理
                        console.log('删除失败');
                        
                    }
                });
            });

        }

       /* 添加链接*/
        $('#form_add_url').on('submit',function (even) {
        	even.preventDefault();
            var $link_name = $('#add_link_name').val();
            var $link = $('#add_link').val();
            var $link_sort = $('#add_link_sort').val();
            console.log('链接名字'+$link_name);
            console.log('链接'+$link);
            console.log('链接order'+$link_sort);
            $.ajax({
                url: "../linksinsert",    //请求的url地址
                dataType: "json",   //返回格式为json
                async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                data: { "name": $link_name,'link':$link,'order':$link_sort },    //参数值 可能不用
                type: "POST",   //请求方式
                success: function(data) {
                    //请求成功时处理
                	console.log(data);
                	layer.msg('增加链接成功', {icon: 1},function () {
                        window.location.reload();
                    });
                },
                error: function() {
                    console.log('添加友情链接请求失败')
                }
            });
        })


    })
})