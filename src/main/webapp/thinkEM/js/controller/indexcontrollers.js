var indexController = angular.module('indexController', []);
indexController.controller('appcontroller', function($scope, $http,$state, $stateParams,$timeout,$location) {

    if (window.screen.width<500) {
        $scope.phone=1;
        $scope.navShow=0;
        $scope.nav2Show=0;
        $scope.showNav = function(aa) {
            if(aa){
            	$scope.navShow=aa;
            	$scope.nav2Show=0;
            }else{
            	$scope.navShow=aa;
            	$scope.nav2Show=aa;
            }

            if (window.screen.width<500) {
                $scope.phone=1;
                }
        };
        $scope.showNav2 = function(aa) {
        	 if(aa){
             	$scope.nav2Show=aa;
             	$scope.navShow=0;
             }else{
             	$scope.navShow=aa;
             	$scope.nav2Show=aa;
             }
        };
    }else{
        $scope.phone=0;
        $scope.navShow=1;
        $scope.nav2Show=1;
        $scope.showNav = function(aa) {
            $scope.navShow=1;
            if (window.screen.width<500) {
                $scope.phone=1;
                }
        };
        $scope.showNav2 = function(aa) {
            $scope.nav2Show=1;
        };
        
    }
    
    
  
    
    //获取登录状态
    $scope.appusername="";
	  $scope.getusername = function() {
		     $http({
		        method: 'post',
		        url: './getUserMessage',
		         headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
		         transformRequest: function(obj) {  
		           var str = [];  
		           for(var p in obj){  
		             str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
		           }  
		           return str.join("&");  
		         } 
		    
		        }).then(function successCallback(response) {
		        	if(response.data){
		        		$scope.appusername=response.data.username;
		        	}else{
		        		$scope.appusername="";
		        	}
		        	
		            }, function errorCallback(response) {
		            	$scope.appusername="";
		               
		        });
		    };  
		    $scope.getusername();
		    
		  //登出
		    $scope.getout = function() {
			     $http({
			        method: 'post',
			        url: './logout',
			        headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
			         transformRequest: function(obj) {  
			           var str = [];  
			           for(var p in obj){  
			             str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
			           }  
			           return str.join("&");  
			         } 
			    
			        }).then(function successCallback(response) {
			            $scope.getusername();
			            }, function errorCallback(response) {
			            	//登出
			              // location.href = './index.html';
			          /*  location.href = './index.html#index';*/
			            $scope.getusername();
                     	   location.reload();			               
			        });
			    };  
			    
			    
			    $scope.submission = function() {
				     $http({
					        method: 'post',
					        url: './getUserMessage',
					         headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
					         transformRequest: function(obj) {  
					           var str = [];  
					           for(var p in obj){  
					             str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
					           }  
					           return str.join("&");  
					         } 
					    
					        }).then(function successCallback(response) {
					        	if(response.data.username){
					        		/*./submisson.html*/
					        		//window.open("http://www.baidu.com");  
					        	}else{
					        		alert("请先登录再投稿！~");
					        	}
					            }, function errorCallback(response) {
					            	console.log("请求投稿失败");		
					               
					        });
				    }; 
		
	$scope.allschool=[];	

	   $scope.getallschooltime=0;
	   $scope.getallschool = function() {
		   $scope.getallschooltime= $scope.getallschooltime+1;
	 	     $http({
	 	        method: 'post',
	 	        url: "./getSchools",
	 	         headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
	 	         transformRequest: function(obj) {  
	 	           var str = [];  
	 	           for(var p in obj){  
	 	             str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
	 	           }  
	 	           return str.join("&");  
	 	         }  
	 	        }).then(function successCallback(response) {
	 	        	if(response.data){
	 	        		$scope.allschool=response.data;
	 	        	}else if($scope.getallschooltime<=5){
	 	        		$scope.getallschool();
	 	        	}
	 	            }, function errorCallback(response) {
	 	        });
	 	    };
	 	   $scope.getallschool();
	 		
	$scope.allsearch={
			value:'',
	};
    $scope.lessonsearch={value:''};
    $scope.teachersearchText={value:''};
		    
		  //初始化分页数据变量  
		    $scope.listRequest='./passage/search/list';
		    $scope.page={
		         requstarray:[1],
		         totalRecord:0,
		         currentPage:1,
		         pageSize:11,
		         totalPage:0,
		         currentindex:0,
		              data:[],
		     } 
		    //设置分页的数据
		    $scope.pagingOptions = {
		       totalRecord:0,
		       currentPage: 0,
		       totalPage:0,
		    };
		    // 当前列表
		    $scope.detaildata=[];
		    //热点列表
		    $scope.recommentListdata=[];   
		    //获取文章列表： 
		    $scope.getsearchData = function() {
		    	if($scope.allsearch.value){
					    $http({
					       method: 'post',
					       url: $scope.listRequest,
					       data: {
					    	   	  searchInfo:$scope.allsearch.value,
					              pageSize:$scope.page.pageSize,
					              pageNum: $scope.page.requstarray[$scope.page.currentindex],
					          } ,
					          headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
					          transformRequest: function(obj) {  
					              var str = [];  
					              for(var p in obj){  
					                str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
					              }  
					              return str.join("&");  
					            } 
					       }).then(function successCallback(response) {
					              $scope.page.data[$scope.page.currentindex]=response.data.dataList;//
					              $scope.page.totalRecord=response.data.totalRecord,
					              $scope.page.totalPage=response.data.totalPage,
					              $scope.page.currentPage=$scope.page.requstarray[$scope.page.currentindex],
					              //传给分页插件
					              $scope.pagingOptions = {
					                   totalRecord: $scope.page.totalRecord,
					                   currentPage: $scope.page.requstarray[$scope.page.currentindex],
					                   totalPage:   $scope.page.totalPage,
					               };
					              	//		              跳转
					              $location.path('/search');
					           }, function errorCallback(response) {
					               $scope.pagingOptions = {
					                   totalRecord: $scope.page.totalRecord,
					                   currentPage: $scope.page.currentPage,
					                   totalPage:   $scope.page.totalPage,
					               };
					       });
		    	};
		    };   
		    
		    $scope.mykey = function (e) {      
	            var keycode = window.event ? e.keyCode : e.which;//获取按键编码  
	            if (keycode == 13) {  
	                $scope.getsearchData();//如果等于回车键编码执行方法  
	            }  
	        };  
	        
	        //获取推荐

	 	   //recommandList
	 	    $scope.allRecommand=[];
	 	    $scope.getRecommandListtimes=0;
	 	  //获取文章列表：
	 	    $scope.getRecommandList = function() {
	 	    	 $scope.getRecommandListtimes= $scope.getRecommandListtimes+1;
	 	     $http({
	 	        method: 'post',
	 	        url: "./passage/passagelist",
	 	        data: {
	 	               passageType:12,
	 	               pageSize:7,
	 	               pageNum:1,
	 	           } ,
	 	         headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
	 	         transformRequest: function(obj) {  
	 	           var str = [];  
	 	           for(var p in obj){  
	 	             str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
	 	           }  
	 	           return str.join("&");  
	 	         }  
	 	        }).then(function successCallback(response) {
	 	        	if(response.data){
	 	        		if(response.data.passagePage){
	 	               $scope.allRecommand=response.data.passagePage.dataList;
	 	               }
	 	               }else if($scope.getRecommandListtimes<=4){
	 	      	 	    $scope.getRecommandList();
	 	               }else{
	 	            	  console.log("推薦请求失败，请刷新页面");
	 	               }
	 	            }, function errorCallback(response) {
	 	        });
	 	    };

	 	    $scope.getRecommandList();
	 	    //获取文章附件
				

});


//导航栏
indexController.controller('navigationCtr', function($scope, $http,$state, $stateParams,$timeout,$location) {
	  $scope.getnavigationtimes=0;
	  $scope.getnavigation = function() {
		  $scope.getnavigationtimes=$scope.getnavigationtimes+1;
		     $http({
		        method: 'post',
		        url: './naviselect',
		        }).then(function successCallback(response) {
		        	
		             // 赋值
		         if(response.data){
		                $scope.navigationList=response.data;
		        	 }else if($scope.getnavigationtimes<=6){
		        		 $scope.getnavigation();
		        	 }else{
		        		 alert("导航栏请求失败，请刷新页面");
		        	 }
		            }, function errorCallback(response) {
		        });
		    };  
		$scope.getnavigation();
});

//轮播图
indexController.controller('playCtr', function($scope, $http,$state, $stateParams,$timeout,$location) {
	$scope.player=[];
	$scope.getPlayertimes=0;
	$scope.getPlayer = function() {
		$scope.getPlayertimes=$scope.getPlayertimes+1;
		     $http({
		        method: 'post',
		        url: './banner/getAllBanner',
		         headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
		         transformRequest: function(obj) {  
		           var str = [];  
		           for(var p in obj){  
		             str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
		           }  
		           return str.join("&");  
		         } 
		        }).then(function successCallback(response) {
		             // 赋值
		        	if(response.data){
		                $scope.player=response.data; 
		                }else if($scope.getPlayertimes<=3){
		                	$scope.getPlayer();
		                }else{
		                	console.log("加载数据出错");
		                }
		            }, function errorCallback(response) {
		              
		        });
		    };  
		    $scope.getPlayer();
		    $(function(){
                $("#myCarousel").carousel('cycle');
        });
});


//首页
indexController.controller('indexALL', function($scope, $http,$state, $stateParams,$timeout,$location) {
	$scope.getHometimes=0;
	$scope.getHome = function() {
		$scope.getHometimes=$scope.getHometimes+1;
		     $http({
		        method: 'post',
		        url: './//main',
		         headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
		         transformRequest: function(obj) {  
		           var str = [];  
		           for(var p in obj){  
		             str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
		           }  
		           return str.join("&");  
		         } 
		    
		        }).then(function successCallback(response) {
		             // 赋值
		        	if(response.data){
			        	if(response.data.dynamicList){
			                $scope.dynamicList=response.data.dynamicList;}
			        	if(response.data.headlineList){
			                $scope.headlineList=response.data.headlineList;}
			        	if(response.data.linkList){
			                $scope.linkList=response.data.linkList.slice(0,30);}
			        	if(response.data.meeting){
			                $scope.meeting=response.data.meeting.dataList;}
			        	if(response.data.newestPassageList){
			                $scope.newestPassageList=response.data.newestPassageList;}
			        	if(response.data.postList){
			                $scope.postList=response.data.postList;}
		        	}else if($scope.getHometimes<=4){
		        		$scope.getHome();
		        	}else{
		        		alert("数据加载出错，请刷新页面");
		        	}
		            }, function errorCallback(response) {

		               
		        });
		    };  
		    $scope.getHome();
		    
    
    
    $scope.schoollist=[];
    $scope.getschoolByType2times=0;
    $scope.getschoolByType2 = function(a) {
    	$scope.getschoolByType2times=$scope.getschoolByType2times+1;
        $("[active]").children('li').toggleClass('active',false);
        $("[active]").children('li').eq(a-1).toggleClass('active',true);
    	$scope.province='';
        $http({
             method: 'post',
             url: './selectSchoolsSelectiveByPage',
              headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
              data:{
             	 type:a,
             	currentPage:1,
             	pageSize:6,
              },
              transformRequest: function(obj) {  
                var str = [];  
                for(var p in obj){  
                  str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
                }  
                return str.join("&");  
              } 
             }).then(function successCallback(response) {
                  // 赋值
            	 if(response.data){
            		 $scope.schoollist=response.data.dataList;
            		    $scope.getschoolByType2times=1;
            	 }else if($scope.getschoolByType2times<=4){
            		 $scope.getschoolByType2(a);
            	 }else{
            		 //alert("加载数据出错，请刷新页面");
            	 }
                
                 }, function errorCallback(response) {
             }); 
    }
	
    $scope.getschoolByType2(1);  
    
  //论坛
	$scope.indexSaying=[];
    $scope.indexSayinglistRequest=['./userpostPageVist', './userpostPage'];
	//获取文章列indexSaying表：
    $scope.sayingtypeAr=['hotPost','update'];
    $scope.sayingtype=$scope.sayingtypeAr[0] 
    $scope.getSayingDatatimes=0;
    $scope.getSayingData= function(type) {
    	$scope.getSayingDatatimes=$scope.getSayingDatatimes+1;
    	$scope.sayingtype=$scope.sayingtypeAr[type] 
     $(".sayingTab").toggleClass('active',false);
	 $(".sayingTab").eq(type).toggleClass('active',true);
     $http({
        method: 'post',
        url: $scope.indexSayinglistRequest[type],
        data: {
               pageSize:8,
               pageNum: 1,
           } ,
         headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
         transformRequest: function(obj) {  
           var str = [];  
           for(var p in obj){  
             str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
           }  
           return str.join("&");  
         }  
        }).then(function successCallback(response) {
        	
        	if(response.data){
        		if(response.data.postList){
                    $scope.indexSaying=response.data.postList.dataList;//
             	}
        		$scope.getSayingDatatimes=1;
        	}else if($scope.getSayingDatatimes<=4){
        		$scope.getSayingData(type);
        	}
        	
            }, function errorCallback(response) {
               $scope.indexSaying=[]//
        });
    };
    $scope.getSayingData(1);
    
	  
    //首页名师
    $scope.schoolname=[];	
	   $scope.getschoolname = function(a,s) {
			console.log("获取学校的名称")
		  for(i=0;i<$scope.allschool.length;i++){
     		if($scope.allschool[i].school.id==s){
     			$scope.schoolname[a] = $scope.allschool[i].school.name;
     		}
     	}
		$scope.schoolname[a]=$scope.schoolname[a]?$scope.schoolname[a]:'';

		if( $scope.schoolname[a]){
			
		}else{
 		   $scope.getindexTeacherData();
		}
		
		
	 	};
    
    
    $scope.indexTeacher=[];
    //获取名师列表：
    $scope.getindexTeacherDatatimes=0;
    $scope.getindexTeacherData = function() {
    	 $scope.getindexTeacherDatatimes= $scope.getindexTeacherDatatimes+1;
     $http({
        method: 'post',
        url: './teacher/getAllTeacherByPage',
        data: { 
            currentPage: 1,
            pageSize:3,
            type:0,
           },
         headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
         transformRequest: function(obj) {  
           var str = [];  
           for(var p in obj){  
             str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
           }  
           return str.join("&");  
         } 
       }).then(function successCallback(response) {
             // 赋值
    	   if(response.data&&$scope.getindexTeacherDatatimes<=4){
    		   $scope.indexTeacher=response.data.dataList;//
    		   if($scope.allschool.length==0){
	 			   $http({
		 	        method: 'post',
		 	        url: "./getSchools",
		 	        }).then(function successCallback(response) {
		 	        	if(response.data){
		 	        		$scope.allschool=response.data;
		 	        		for(k=0;k<$scope.indexTeacher.length;k++){
		 	                $scope.getschoolname(k,$scope.indexTeacher[k].schoolId);}
		 	        	}
		 	        	console.log("获取所有学校")
		 	           }, function errorCallback(response) {

		 	        });	 
	 		   }else{
	 		    	console.log("已获取所有学校")
	 			    for(k=0;k<$scope.indexTeacher.length;k++){
	                $scope.getschoolname(k,$scope.indexTeacher[k].schoolId);}
	 		   }
    		   
    	   }else if($scope.getindexTeacherDatatimes<=4){
    		   $scope.getindexTeacherData();
    		  
    	   }else{
    		   console.log("加载名师出错");
    	   }
               
            }, function errorCallback(response){
        });
    };
    $scope.getindexTeacherData();
 	
});



indexController.filter('htmlContent', function($sce) {
  return function(input){
    return $sce.trustAsHtml(input);
  }
});


indexController.filter('reverseX', function() { //可以注入依赖
    return function(text) {
        var objtime=new Date(text);
        var year = objtime.getFullYear(); //getFullYear getYear
        var month = objtime.getMonth()+1;
        var date = objtime.getDate();
        var hour = objtime.getHours();
        var minu = objtime.getMinutes();
        var sec = objtime.getSeconds();
        if (month < 10) month = "0" + month;
        if (date < 10) date = "0" + date;
        if (hour < 10) hour = "0" + hour;
        if (minu < 10) minu = "0" + minu;
        if (sec < 10) sec = "0" + sec;
        if (window.screen.width<500) {
        	var nowtime=new Date();
            var objtime=new Date(text);
        	 if(nowtime.getFullYear()!=objtime.getFullYear()){//年份不一样
                 return year+'/'+month;//显示为年月
             }else{//年份一样
                 if(nowtime.getMonth()!=objtime.getMonth()||nowtime.getDate()!=objtime.getDate()){//年份一样，判断是否同一日，不同一日
                     return month+'/'+date;//显示月日
                 }else{
                     return hour+':'+minu//显示时间时分
                 }
             }
             return '';
        }
        return  year + "/" + month + "/" + date + " " + hour + ":" + minu ;
    }
});


indexController.filter('reverse', function() { //可以注入依赖
    return function(text) {
        var objtime=new Date(text);
        var year = objtime.getFullYear(); //getFullYear getYear
        var month = objtime.getMonth()+1;
        var date = objtime.getDate();
        var hour = objtime.getHours();
        var minu = objtime.getMinutes();
        var sec = objtime.getSeconds();
        if (month < 10) month = "0" + month;
        if (date < 10) date = "0" + date;
        if (hour < 10) hour = "0" + hour;
        if (minu < 10) minu = "0" + minu;
        if (sec < 10) sec = "0" + sec;
        if (window.screen.width<500) {
        	var nowtime=new Date();
            var objtime=new Date(text);
        	 if(nowtime.getFullYear()!=objtime.getFullYear()){//年份不一样
                 return year+'/'+month;//显示为年月
             }else{//年份一样
                 if(nowtime.getMonth()!=objtime.getMonth()||nowtime.getDate()!=objtime.getDate()){//年份一样，判断是否同一日，不同一日
                     return month+'/'+date;//显示月日
                 }else{
                     return hour+':'+minu;//显示时间时分
                 }
             }
             return '';
        }
        return  year + "/" + month + "/" + date ;
    }
});

indexController.filter('reverse2', function() { //可以注入依赖
    return function(text) {
        var objtime=new Date(text);
        var year = objtime.getFullYear(); //getFullYear getYear
        var month = objtime.getMonth()+1;
        var date = objtime.getDate();
        var hour = objtime.getHours();
        var minu = objtime.getMinutes();
        if (month < 10) month = "0" + month;
        if (date < 10) date = "0" + date;
        if (hour < 10) hour = "0" + hour;
        if (minu < 10) minu = "0" + minu;
        return  year + "/" + month + "/" + date + " " + hour + ":" + minu ;
    }
});


indexController.filter('reverse3', function() { //可以注入依赖
    return function(type) {
//    	1-最新资料，2-思政动态，
//    	3-马院头条，
//    	4-经典作家，5-领导讲话，6-厅部文件，7-通知公告，
//    	8-改革动态，9学科建设，10-评估排行，11-科研前沿，
//    	12-热点难 ，
////    13-课程基础，14-课程概论，15-热点难点，
////    16-课程纲要，
// //   	17-理论剖析，18-参考资料，19-案例资源，20-教案推荐，21-精品课件，22-视频资源，//23-阅读书目，
//    	24-精品在线,25名师新论26新秀论坛
    	var map=[
    	    "#/index",
			"#/passage/latestnews/",//1
			"#/passage/IPstate/",
			"#/passage/headline/",
            "#/authority/author/",
            "#/authority/speach/",//5
            "#/authority/document/",
            "#/authority/inform/",
            "#/horse/reform/",
            "#/horse/constrution/",
            "#/horse/range/",//10
            "#/horse/science/",//11
            "#/passage/recommend/",//12
            "#/lesson/foundation/catalogue/",//13
            "#/lesson/generality/catalogue/",
            "#/lesson/principle/catalogue/",//15
            "#/lesson/essential/catalogue/",
            "/theories/",//17
	        "/references/",//18
	        "/cases/",//19
	        "/schemes/",//20
	        "/courseware/",//21
	        "/vidios/",//22
	        "#/lesson/foundation/booklist/",
	        "#/passage/qualityOnline/",//
			"#/teacher/newtheory/",
			"#/teacher/forum/",//26
    	     ]
        return map[type];
    }
    
});


indexController.filter('reverse4', function() { //可以注入依赖
    return function(type) {

    	var map=[
    	    "#/teacher/elegant/",
			"#/teacher/rookie/",//1
    	     ]
        return map[type];
    }
});
indexController.filter('reverse5', function() { //可以注入依赖
    return function(msg) {
    	var map=[
    	    "images/good.jpg",//1
			"images/good-orange.jpg",//2
    	     ]
        return map[msg-1];
    }
});

indexController.filter('reverse6', function() { //可以注入依赖
    return function(s) {
    	if(s){
    	var a=s.substr(s.lastIndexOf("\/")+1);
        var b=s.substr(s.lastIndexOf("\\")+1);
        if(a.length>b.length){
        	return b;
        }
        return a;
    	}
    }
});

indexController.filter('reverseDate', function() { //可以注入依赖
    return function(s) {
//当前时间new Date();
//请求获得的时间new Date(text).toLocaleDateString();
    	
        return a;
    }
});


