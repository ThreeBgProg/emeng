/**
 * Created by guiliang on 2017/6/6 0006.
 */
layui.use(['element'], function() {
    $ = layui.jquery;
    element = layui.element();

   /* $('#contribute_list').on('click',function () {
        var _href = $(this).data('href');
        console.log(_href);
        $('.myframe').attr('src',_href);
    })*/
    $.ajax({
        url: "../getUserMessage",    //请求的url地址
        dataType: "json",   //返回格式为json
        async: true, //请求是否异步，默认为异步，这也是ajax重要特性
        data: { },    //参数值 可能不用
        type: "POST",   //请求方式
        success: function(data) {
            $('#B_username').html(data.username);
        },
        error: function() {
            //请求出错处理
            /*alert('获取总页数失败');*/

        }
    });

    
    $('#B_logout').on('click',function(){
    	
    	location.href='./login.html';
    	 $.ajax({
        url: "../logout",    //请求的url地址
        dataType: "json",   //返回格式为json
        async: true, //请求是否异步，默认为异步，这也是ajax重要特性
        data: { },    //参数值 可能不用
        type: "POST",   //请求方式
        success: function(data) {
            
        },
        error: function() {
           /* //请求出错处理
            alert('获取总页数失败');*/

        }
    });
    })

$('.left .layui-side-scroll ul li a[data-href]').on('click',function () {
    var data_href = $(this).data('href');
    console.log(data_href);
    $('.myframe').attr('src',data_href);
})
})