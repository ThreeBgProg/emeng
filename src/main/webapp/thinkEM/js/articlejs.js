/**
 * Created by guiliang on 2017/6/14 0014.
 */
$(function () {
	var if_isbanner = 0;
	var annex;
    layui.use(['laypage','layer','form','layedit'], function() {
        var laypage = layui.laypage;
        var layer = layui.layer;
        var form = layui.form();
        layedit = layui.layedit;
        var current_page;

        var nums = 10;  //一页10条
        var pages= 10;  //共10页 模拟 实际用ajax获取到
        var title;

        var tag = $('.iframe-content').data('tag');


        //ajax获取总页数,根据不同type获取
        $.ajax({
            url: '../passage/passagelist',   
            dataType: "json",   //返回格式为json
            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
            data: { "passageType": tag },    //可以不用
            type: "POST",   //请求方式
            success: function(data) {
            	console.log(data);
                pages = data.passagePage.totalRecord%10==0?data.passagePage.totalRecord/10:(data.passagePage.totalRecord/10+1);
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
                url: "../passage/passagelist",    //请求的url地址  url_link_passages[tag]
                dataType: "json",   //返回格式为json
                async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                data: {  "passageType": tag,"pageNum": obj.curr ,"pageSize":10},    //参数值 可能不用
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
            //渲染数据
            var $operation_up = '<td class="td-manage"><a title="编辑" href="javascript:;" class="my-edit" style="text-decoration:none"><i class="layui-icon">&#xe642;</i></a><a title="删除" href="javascript:;"  class="my-delete" style="text-decoration:none"><i class="layui-icon">&#xe640;</i></a><a title="置顶" href="javascript:;" class="my-up" style="text-decoration:none"><i class="layui-icon">&#xe62f;</i></a></td>';
            var $operation_down = '<td class="td-manage"><a title="编辑" href="javascript:;" class="my-edit" style="text-decoration:none"><i class="layui-icon">&#xe642;</i></a><a title="删除" href="javascript:;"  class="my-delete" style="text-decoration:none"><i class="layui-icon">&#xe640;</i></a><a title="取消置顶" href="javascript:;" class="my-down" style="text-decoration:none"><i class="layui-icon">&#xe601;</i></a></td>';
            var $tbody = $('.iframe-content .layui-table tbody');
            $tbody.html("");
            var my_length = data.passagePage.dataList.length;
            $.each(data.passagePage.dataList,function (index,element) {
                var $tr = $('<tr></tr>');
                $tr.data('ids',element.id);
                $tr.append("<td class='article-title' style='width: 20em'>"+element.titile+"</td>");
                $tr.append("<td>"+element.author+"</td>");
                $tr.append("<td>"+new Date(element.publishTime).toLocaleString()+"</td>");
                if(tag==1)
                    {$tr.append($operation_up);}
                else $tr.append($operation);
                $tbody.append($tr);

                /*/var $my_tr = $('.layui-table tr');
                 if($my_tr.length==11)
                 for(i=0;i<11;i++)
                 console.log($($my_tr[i]).data['ids']);*/
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
               /* 每个置顶按钮注册事件*/
                var $my_ups = $('.my-up');
                if($my_delete.length==my_length)
                {
                    /*console.log('置顶数目'+$my_ups.length);*/
                    $my_ups.on('click',articleUp)
                }
                /*每个取消置顶按钮注册事件*/
                var $my_downs = $('.my-down');
                if($my_delete.length==my_length)
                {
                    /*console.log('置顶数目'+$my_ups.length);*/
                    $my_downs.on('click',articleDown);
                }

            })

        }

        /*显示所有置顶事件*/
        var $show_all_up = $('#show_all_up');
        $show_all_up.on('click',function () {
            $.ajax({
                url: "../banner/getAllBanner",    //请求的url地址  url_link_passages[tag]
                dataType: "json",   //返回格式为json
                async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                data: {},    //参数值 可能不用
                type: "POST",   //请求方式
                success: function(data) {
                	 ajaxShowAllUp(data);
                },
                error: function() {
                    //请求出错处理
                    /*alert('显示所有置顶')*/
                   
                }
            });
        })

        /*显示所有置顶文章*/
        function ajaxShowAllUp(data) {
            //渲染函数（其实应该在success里面）
            $('#page').hide();
            $('#passage_add').hide();
            var $operation_down = '<td class="td-manage"><a title="编辑" href="javascript:;" class="my-edit" style="text-decoration:none"><i class="layui-icon">&#xe642;</i></a><a title="取消置顶" href="javascript:;" class="my-down" style="text-decoration:none"><i class="layui-icon">&#xe601;</i></a></td>';
            var $tbody = $('.iframe-content .layui-table tbody');
            $tbody.html("");
            $.each(data,function (index,element) {
                var $tr = $('<tr></tr>');
                $tr.data('pid',element.passage.id);
                $tr.data('ids',element.banner.id);
                $tr.data('src',element.banner.pic);
                $tr.append("<td class='article-title' style='width: 20em'>"+element.passage.titile+"</td>");
                $tr.append("<td>"+element.passage.author+"</td>");
                $tr.append("<td>"+new Date(element.passage.publishTime).toLocaleString()+"</td>");
                $tr.append($operation_down);
                $tbody.append($tr);
               
            })
            var $my_edits = $('.my-edit i');
            /* alert($my_edits.length);*/
           
                $my_edits.on('click',bannerEdit)
            

            /*每个取消置顶按钮注册事件*/
            var $my_downs = $('.my-down');
           
                /*console.log('置顶数目'+$my_ups.length);*/
                $my_downs.on('click',bannerDown);

        }
        //导航编辑函数
        function bannerEdit(){
        	//获取当前文章在banner表中的id不是passageid,以及src(对应oic属性）
        	var id = $(this).parents('tr').data('ids');
        	var pid = $(this).parents('tr').data('pid');
        	var src = $(this).parents('tr').data('src');
        	console.log(id);
        	console.log(src);
        	   layer.open({
   	            type: 2,
   	            area: ['530px', '530px'],
   	            fix: false, //不固定
   	            maxmin: true,
   	            shadeClose: true,
   	            shade:0.4,
   	            offset: '30px',
   	            content: 'banerAdd.html',
   	            end: function () {
   	                location.reload();
   	            },//请求方式
   	            success:function () {
   	            	
   	            	$("#layui-layer-iframe1").contents().find("#banner_upload").attr('src',src);
   	                /*文件上传*/
   	                $("#layui-layer-iframe1").contents().find("#banner_file").on('change',function () {
   	                    var fileinput = $("#layui-layer-iframe1").contents().find("#banner_file");
   	                    var file = fileinput[0].files[0];
   	                    var formData1 = new FormData();
   	                    formData1.append("file",file);

   	                    $.ajax({
   	                        url: "../picupload",    //请求的url地址
   	                        dataType: "json",   //返回格式为json
   	                        async: true, //请求是否异步，默认为异步，这也是ajax重要特性
   	                        processData: false,    //必须设置
   	                        contentType: false,    //必须设置
   	                        data:formData1,
   	                        /*data: {'title':meeting_title,'content':meeting_content,'link':meeting_link,'type':meeting_type,'release_date':new Date(meeting_releaseDate),'code':meeting_code,'annex':formData},    //参数值 可能不用*/
   	                        type: "POST",   //请求方式
   	                        success: function(data) {
   	                        	 
   	                            console.log(data.data.src);
   	                            annex = data.data.src;
   	                            $("#layui-layer-iframe1").contents().find("#banner_upload").attr('src',annex);

   	                        },
   	                        error: function(){
   	                            console.log('上传文件失败');
   	                        }
   	                    });

   	                })


   	                $("#layui-layer-iframe1").contents().find("#banner_add_form").on('submit',function (e) {
   	                	   
   	                	   //fenge
   	                       $.ajax({
   	                           url: "../banner/updateBanner",    //请求的url地址
   	                           dataType: "json",   //返回格式为json
   	                           async: true, //请求是否异步，默认为异步，这也是ajax重要特性
   	                           data: { 'id':id,"passageId": pid,'pic':annex},    //参数值 可能不用
   	                           type: "POST",   //请求方式
   	                           success: function(data) {
   	                        	layer.msg('修改置顶成功', {icon: 1},function () {
                                       window.location.reload();
                                   });
                                   console.log('成功修改置顶文章id'+pid);
   	                        	   
   	                        	   
   	                           },
   	                           error: function() {
   	                               
   	                           }
   	                       });
   	             
   	                    return false;
   	                })
   	            }
   	        });
        	
        }
        
        
        //取消轮播
        function bannerDown(){
        	var id = $(this).parents('tr').data('ids');
        	 $.ajax({
                    url: "../banner/deleteBannerPrimaryKey",    //请求的url地址
                    dataType: "json",   //返回格式为json
                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                    data: { 'id':id},    //参数值 可能不用
                    type: "POST",   //请求方式
                    success: function(data) {
                 	layer.msg('取消置顶成功', {icon: 1},function () {
                         window.location.reload();
                     });
                     console.log('成功修改置顶轮播id'+id);
                 	   
                 	   
                    },
                    error: function() {
                        
                    }
                });
      
        }
        
        
        

        /*编辑处理函数*/
        function articleEdit() {
            /*console.log($(this).parents('tr').data('ids'));*/
            var id = $(this).parents('tr').data('ids');
            console.log('编辑的文章id:'+id);
            var current_href = location.href;
            console.log('href'+current_href);
           /* http://localhost:63342/thinkEM/latestInformation.html&flag=1*/
            var new_href = '../thinkEM/articleEdit.html?id='+id+'&type='+tag;
            console.log('newhref'+new_href);
            location.href =  new_href;
            /*alert($('.layui-layer-title').html())//这里保存的是点击的文章id*/
            /*投稿内容页显示代码ajax请求*/
            var id = $('.layui-layer-title').text();
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
                    data: { "passageId": id},    //参数值 可能不用
                    type: "POST",   //请求方式
                    success: function(data) {
                    	//请求出错处理
                    	console.log(id);
                        layer.msg('删除成功', {icon: 1},function () {
                        	   $.ajax({
                                   url: "../banner/deleteBannerByPassageId",    //请求的url地址
                                   dataType: "json",   //返回格式为json
                                   async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                                   data: { "passageId": id},    //参数值 可能不用
                                   type: "POST",   //请求方式
                                   success: function(data) {
                                   	
                                   },
                                   error: function() {
                                       
                                      
                                   }
                               });
                            window.location.reload();
                        });
                    },
                    error: function() {
                        
                        //ajax获取总页数,根据不同type获取实现功能是当编辑或删除及其他操作后回到刷新当前页面而不是总的页面
                       /* $.ajax({
                            url: ''/!*url_link_pages[tag]*!/,    //请求的url地址
                            dataType: "json",   //返回格式为json
                            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                            data: { "type": tag },    //可以不用
                            type: "POST",   //请求方式
                            success: function(data) {
                                //请求成功时处理
                                pages = data.nums;
                            },
                            error: function() {
                                //请求出错处理
                                pages = 15;
                                /!*console.log(current_page)*!/
                                laypage({
                                    cont: 'page'
                                    ,pages:pages //得到总页数
                                    ,skip:true
                                    ,curr:current_page
                                    ,jump: function (obj,first) {
                                        laypagesuccess(obj);
                                    }
                                });
                            }
                        });*/
                    }
                });
            });

        }

        /*置顶处理函数*/
        function articleUp() {
            var id = $(this).parents('tr').data('ids');
            console.log('置顶文章id'+id);
            var bannerids = [];
            //得到置顶文章的id
            $.ajax({
                url: "../banner/getAllBanner",    //请求的url地址
                dataType: "json",   //返回格式为json
                async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                data: {},    //参数值 可能不用
                type: "POST",   //请求方式
                success: function(data) {
                    //请求成功时处理
                   $.each(data,function(index,element){
                	   bannerids[index]=element.passage.id;
                   })
                   
                   console.log('置顶文章id'+bannerids);
                   //判断是否是banner文章
                   $.each(bannerids,function(index,element){
                   	console.log(id);
                   	console.log(element);
                   	if(id==element){
                   		if_isbanner = 1;
                   		layer.msg('该文章已经置顶', {icon: 5},function () {
                            window.location.reload();
                        });
                   	}
                   })
                   
                   if(if_isbanner==0){
                	   //置顶操作
                	   //不是轮播文章但是轮播已满
                	   if(bannerids.length==4){
                		   layer.msg('轮播数量已满，请点击显示已有轮播删除',{icon: 5},function () {
                               window.location.reload();
                           });
                		   
                		    
                	   }
                	   //满足可以置顶轮播的全部
                	   else{
                		   layer.open({
                	            type: 2,
                	            area: ['530px', '530px'],
                	            fix: false, //不固定
                	            maxmin: true,
                	            shadeClose: true,
                	            shade:0.4,
                	            offset: '30px',
                	            content: 'banerAdd.html',
                	            end: function () {
                	                location.reload();
                	            },//请求方式
                	            success:function () {
                	                /*文件上传*/
                	                $("#layui-layer-iframe1").contents().find("#banner_file").on('change',function () {
                	                    var fileinput = $("#layui-layer-iframe1").contents().find("#banner_file");
                	                    var file = fileinput[0].files[0];
                	                    var formData1 = new FormData();
                	                    formData1.append("file",file);

                	                    $.ajax({
                	                        url: "../picupload",    //请求的url地址
                	                        dataType: "json",   //返回格式为json
                	                        async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                	                        processData: false,    //必须设置
                	                        contentType: false,    //必须设置
                	                        data:formData1,
                	                        /*data: {'title':meeting_title,'content':meeting_content,'link':meeting_link,'type':meeting_type,'release_date':new Date(meeting_releaseDate),'code':meeting_code,'annex':formData},    //参数值 可能不用*/
                	                        type: "POST",   //请求方式
                	                        success: function(data) {
                	                        	 
                	                            console.log(data.data.src);
                	                            annex = data.data.src;
                	                            $("#layui-layer-iframe1").contents().find("#banner_upload").attr('src',annex);

                	                        },
                	                        error: function(){
                	                            console.log('上传文件失败');
                	                        }
                	                    });

                	                })


                	                $("#layui-layer-iframe1").contents().find("#banner_add_form").on('submit',function (e) {
                	                	   
                	                	   //fenge
                	                       $.ajax({
                	                           url: "../banner/addBanner",    //请求的url地址
                	                           dataType: "json",   //返回格式为json
                	                           async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                	                           data: { "passageId": id,'pic':annex},    //参数值 可能不用
                	                           type: "POST",   //请求方式
                	                           success: function(data) {
                	                        	   if(data=='操作成功'){
                	                        		   layer.msg('置顶成功', {icon: 1},function () {
                	                                       window.location.reload();
                	                                   });
                	                                   console.log('成功置顶文章id'+id);
                	                        	   }
                	                        	   
                	                        	   
                	                           },
                	                           error: function() {
                	                               
                	                           }
                	                       });
                	             
                	                    return false;
                	                })
                	            }
                	        });
                	   }
                   }
                },
                error: function() {
                    //请求出错处理
                   
                }
            });
            
            
           
        }

        /*  取消置顶处理函数*/
         function articleDown() {
          var id = $(this).parents('tr').data('ids');
          console.log('取消置顶文章id'+id);
          layer.confirm('确认要取消置顶该文章吗？',function(index){
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
                      /*弹出删除置顶成功并刷新页面*/
                      /* layer.msg('取消置顶失败', {icon: 5})*/
                      layer.msg('取消置顶成功', {icon: 1},function () {
                          window.location.reload();
                      });
                      console.log('成功取消置顶文章id'+id);
                  }
              });
          });
      }


    });

    /*发布文章注册事件*/
    $('#passage_add').on('click',function () {
        console.log('add');
        var tag = $('.iframe-content').data('tag');
        console.log(tag);
        var new_href = '../thinkEM/articleAdd.html?tag='+tag;
        console.log('newhref'+new_href);
        location.href =  new_href;
    })

   /*/!* 查询文章按钮注册事件*!/
   $('#button_search').on('click',function () {
       var search_info = $('#search_info').val();
       layui.use(['laypage','layer','form','layedit'], function() {
           var laypage = layui.laypage;
           var layer = layui.layer;
           var form = layui.form();
           layedit = layui.layedit;
           var current_page;

           var nums = 10;  //一页10条
           var pages = 10;  //共10页 模拟 实际用ajax获取到
           var title;

           var tag = $('.iframe-content').data('tag');


           //ajax获取总页数,根据不同type获取
           $.ajax({
               url: '../passage/search/list',
               dataType: "json",   //返回格式为json
               async: true, //请求是否异步，默认为异步，这也是ajax重要特性
               data: {"searchInfo": search_info},    //可以不用
               type: "POST",   //请求方式
               success: function (data) {
                   console.log(data);
                   pages = data.totalRecord % 10 == 0 ? data.totalRecord / 10 : (data.totalRecord / 10 + 1);
                   laypage({
                       cont: 'page'
                       , pages: pages //得到总页数
                       , skip: true
                       , jump: function (obj, first) {
                           current_page = obj.curr;
                           laypagesuccess(obj);
                       }
                   });
               },
               error: function () {
                   //请求出错处理

               }
           });


           //分页跳转函数
           function laypagesuccess(obj) {
               /!* alert(obj.curr);//obj.curr表示选择的页码*!/
               console.log('当前页:' + obj.curr);
               //ajax获取文章列表
               $.ajax({
                   url: "../passage/passagelist",    //请求的url地址  url_link_passages[tag]
                   dataType: "json",   //返回格式为json
                   async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                   data: {"passageType": tag, "pageNum": obj.curr, "pageSize": 10},    //参数值 可能不用
                   type: "POST",   //请求方式
                   success: function (data) {
                       ajaxPageContent(data);
                   },
                   error: function () {
                       //请求出错处理


                   }
               });
           }

           //ajax请求文章页函数（含有编辑删除处理函数）
           function ajaxPageContent(data) {
               var $operation = '<td class="td-manage"><a title="编辑" href="javascript:;" class="my-edit" style="text-decoration:none"><i class="layui-icon">&#xe642;</i></a><a title="删除" href="javascript:;"  class="my-delete" style="text-decoration:none"><i class="layui-icon">&#xe640;</i></a></td>';
               //渲染数据
               var $operation_up = '<td class="td-manage"><a title="编辑" href="javascript:;" class="my-edit" style="text-decoration:none"><i class="layui-icon">&#xe642;</i></a><a title="删除" href="javascript:;"  class="my-delete" style="text-decoration:none"><i class="layui-icon">&#xe640;</i></a><a title="置顶" href="javascript:;" class="my-up" style="text-decoration:none"><i class="layui-icon">&#xe62f;</i></a></td>';
               var $operation_down = '<td class="td-manage"><a title="编辑" href="javascript:;" class="my-edit" style="text-decoration:none"><i class="layui-icon">&#xe642;</i></a><a title="删除" href="javascript:;"  class="my-delete" style="text-decoration:none"><i class="layui-icon">&#xe640;</i></a><a title="取消置顶" href="javascript:;" class="my-down" style="text-decoration:none"><i class="layui-icon">&#xe601;</i></a></td>';
               var $tbody = $('.iframe-content .layui-table tbody');
               $tbody.html("");
               var my_length = data.passagePage.dataList.length;
               $.each(data.passagePage.dataList, function (index, element) {
                   var $tr = $('<tr></tr>');
                   $tr.data('ids', element.id);
                   $tr.append("<td class='article-title' style='width: 20em'>" + element.titile + "</td>");
                   $tr.append("<td>" + element.author + "</td>");
                   $tr.append("<td>" + new Date(element.publishTime).toLocaleString() + "</td>");
                   if (tag == 1) {
                       $tr.append($operation_up);
                   }
                   else $tr.append($operation);
                   $tbody.append($tr);

                   /!*!/var $my_tr = $('.layui-table tr');
                    if($my_tr.length==11)
                    for(i=0;i<11;i++)
                    console.log($($my_tr[i]).data['ids']);*!/
                   /!*每个编辑按钮的注册事件*!/
                   var $my_edits = $('.my-edit i');
                   /!* alert($my_edits.length);*!/
                   if ($my_edits.length == my_length) {
                       $my_edits.on('click', articleEdit)
                   }

                   /!*每个删除按钮注册事件*!/
                   var $my_delete = $('.my-delete');
                   if ($my_delete.length == my_length) {
                       $my_delete.on('click', articleDelete)
                   }
                   /!* 每个置顶按钮注册事件*!/
                   var $my_ups = $('.my-up');
                   if ($my_delete.length == my_length) {
                       /!*console.log('置顶数目'+$my_ups.length);*!/
                       $my_ups.on('click', articleUp)
                   }
                   /!*每个取消置顶按钮注册事件*!/
                   var $my_downs = $('.my-down');
                   if ($my_delete.length == my_length) {
                       /!*console.log('置顶数目'+$my_ups.length);*!/
                       $my_downs.on('click', articleDown);
                   }

               })

           }
       })
   })*/
})



