/**
 * Created by guiliang on 2017/6/30 0030.
 */
$(function () {
    layui.use(['laypage', 'layer', 'form'], function () {
        var layer = layui.layer;
        $('#login_button').on('click',function(){
        	var username = $('#username').val();
        	var password = $('#pass').val();
            $.ajax({
                url: "../login",    //请求的url地址
                dataType: "json",   //返回格式为json
                async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                data:{'username':username,'password':password},
                type: "POST",   //请求方式
                success: function(data) {
                    //请求出错处理
                    /*alert('获取总页数失败');*/
                	
                	if(data=='index'){
                		 $.ajax({
                	            url: '../getUserMessage',
                	            dataType: "json",   
                	            async: true,   
                	            type: "POST", 
                	            success: function(data) {
                	                //请求成功处理
                	                if(data.id){ var id = data.id;
                			            	$.ajax({
                			                url: '../findUser',
                			                dataType: "json",   
                			                data: {'id':id}, 
                			                async: true, 
                			                type: "POST",  
                			                success: function(data) {
                			                    //请求成功处理
                			                /*    //console.log(data.role.id);
*/                			                    var roleId = data.role.id;
                			                    if(roleId==2||roleId==3){
                			                    	layer.msg("非管理员没有权限访问后台", {icon: 6},function () {
                			                        });
                			                    }else{
                			                    	console.log("管理员成功登录");
                			                    	//跳转
                			                    	layer.msg("登录成功", {icon: 6},function () {
                			                    	    //检测登录账户
                			                        	location.href = './indexEM.html';
                			                        });
                			                    }
                			                },
                			                error: function(data) { 
                			                	layer.msg("非管理员没有权限访问后台,请确认登陆账号为管理员账号再重新登录", {icon: 6},function () {
            			                        });
                			                }
                			            });
                	            	}
                	                
                	               
                	            },
                	            error: function(data) { 
                	            	//alert("你还没有登录哦");
                	            }
                	        });
                		
                		
                		}
                	else {layer.alert('账户或者密码失败', {icon: 9},function () {
                    	location.href = './login.html'
                    });}
                    
                },
                error: function(){
                    layer.alert("请求失败", {icon: 9},function () {
                       
                    	location.href = '../thinkEM/login.html'
                    });

                }
            });
        })
        
    });

})