$(document).ready(function(){
	var checkLogin=function(){
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
		                    //console.log(data.role.id);
		                    var roleId = data.role.id;
		                    if(roleId==2||roleId==3){
		                    	alert("你没有权限访问后台");
		                    }else{
		                    	console.log("管理员成功登录");
		                    	//跳转
		                    	location.href = './indexEM.html';
		                    }
		                },
		                error: function(data) { 
		                	alert("请求失败,请重新登录");
		                }
		            });
            	}
                
               
            },
            error: function(data) { 
            	//alert("你还没有登录哦");
            }
        });
	}
	
	
})