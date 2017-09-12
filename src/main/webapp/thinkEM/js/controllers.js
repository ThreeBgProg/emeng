var appController = angular.module('appController', []);

appController.controller('registerCtrl', function($scope, $http,$state, $stateParams){
    $scope.newuser = {
        username:'',
        mail:'',
        phone:'',
        job_id:'',
        password: '',
        school_id:'',
        province_id:'',
        state:'',
    };
$scope.password2='';
    $scope.showAllErr=false;
    $scope.inputcode;
    $scope.code;
    $scope.allschool=[];
    $scope.inputcode;
    $scope.codeErr=false;
    $scope.random='';
    $scope.randnum=function(){
    		    var x = 120;     
    		    var y = 0;     
    		    var rand = parseInt(Math.random() * (x - y + 1) + y);   
    		    return rand;
    };

    	


      $scope.getcode = function() {
    	    $scope.inputcode="";
    	  	$scope.random='<img src="./image/getRandNum?ID='+ $scope.randnum()+'">';
      }
      $scope.getcode();
    // //获取地区  ./getAllLocation
      $scope.getLocation = function() {
      	 $http({
               method: 'post',
               url: './getAllLocation',
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
                      $scope.allprovince=response.data;
                   }, function errorCallback(response) {
                   	
                   	$scope.allprovince = '';
               });
      }
//      获取学校
      $scope.getschoolByTypeAndP = function() {
          $http({
               method: 'post',
               url: './selectSchoolsSelectiveByPage',
                headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
                data:{
               	currentPage:1,
               	pageSize:1000,//所有
               	provinceId:$scope.newuser.province_id,
                },
                transformRequest: function(obj) {  
                  var str = [];  
                  for(var p in obj){  
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
                  }  
                  return str.join("&");  
                } 
               }).then(function successCallback(response) {
            	   $scope.allschool=response.data.dataList;
            	   $scope.newuser.school_id='';
                   }, function errorCallback(response) {
                	   $scope.allschool=[];
                	   $scope.newuser.school_id=''
               }); 
      }
      
         
      

    //注册postnewuser

  $scope.postnewuser = function() {
            if($scope.myForm.$valid){
                    $http({
                    method: 'post',
                    url: './addUser',
                    data: {
                    	"username":$scope.newuser.username,
                    	"jobId":$scope.newuser.job_id,
                    	"password":$scope.newuser.password,
                    	"schoolId":$scope.newuser.school_id,
                    	"phone":$scope.newuser.phone,
                    	"mail":$scope.newuser.mail,
                    	},  
                    	
                    headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
   		         transformRequest: function(obj) {  
   		           var str = [];  
   		           for(var p in obj){  
   		             str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
   		           }  
   		           return str.join("&");  
   		         } ,
                    }).then(function successCallback(response) {               
                           $scope.massage=response.data;
                           if ($scope.massage=="操作失败 用户名或工号已存在") {
                               alert("操作失败 用户名或工号已存在!~");
                               $scope.getcode();
                           }else{
                        	   alert('注册信息已提交！~请等待审核');
                        	   //清空表单
                        	   $scope.newuser = {
                        		        username:'',
                        		        mail:'',
                        		        phone:'',
                        		        job_id:'',
                        		        password: '',
                        		        school_id:'',
                        		        province_id:'',
                        		        state:'',
                        		    };
                        			$scope.password2='';
                        		    $scope.inputcode='';
                        		    $scope.codeErr=false;
                           }
                        }, function errorCallback(response) {
                            console.log('注册请求失败');
                    });  
               
        };
};
     $scope.save = function() {
            $scope.showAllErr= $scope.myForm.$invalid; //当内容不合法时，显示内容
            if($scope.myForm.$valid&&$scope.password2==$scope.newuser.password){
                    $http({
                    method: 'post',
                    url: './image/validateRandNum',
                    data:{
                        value:$scope.inputcode,
                    },
                    headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
   		         transformRequest: function(obj) {  
   		           var str = [];  
   		           for(var p in obj){  
   		             str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
   		           }  
   		           return str.join("&");  
   		         } ,
                    }).then(function successCallback(response) { 
                    	
                    	
                    	if(response.data){
                      	   $scope.codeErr=false;
                             console.log('验证验证码成功');
                 		    $scope.inputcode='';
                 		    //验证工号是否存在
                 		   $scope.postnewuser();
                             $scope.getcode();
                         }else{
                      	   $scope.codeErr=true;
                             console.log('验证验证码失败');
                             $scope.getcode();
                         }
                       
                        }, function errorCallback(response) {
                            $scope.codeErr=true;
                            console.log('验证验证码请求失败');
                            $scope.getcode();
                          
                    });  
               
               } else{

                $scope.showAllErr= $scope.myForm.$invalid; //当内容不合法时，显示内容

               }
        };
        
   $scope.getLocation();  
        
    	$scope.myFunc=function(){
    			if($scope.newuser.province_id){
    	    		  $scope.getschoolByTypeAndP();
    			}
    	}
         
    	$scope.myFunc()

});




appController.controller('loginCtrl', function($scope, $http,$state, $stateParams){
    $scope.loginUser = {
        username:'',
        password: '',
    };

    $scope.showAllErr=false;
    $scope.inputcode;
    $scope.code;
    $scope.codeErr=false;
    $scope.randnum=function(){
	    var x = 120;     
	    var y = 0;     
	    var rand = parseInt(Math.random() * (x - y + 1) + y);   
	    return rand;
};

$scope.getcode = function() {
	$scope.inputcode="";
  	$scope.random='<img src="./image/getRandNum?ID='+ $scope.randnum()+'">';
  }
    $scope.random='';
    $scope.getcode();
      //登陆请求
  $scope.login = function() {
            if($scope.loginForm.$valid){
                    $http({
                    method: 'post',
                    url: './login',
                    data:{
                        username:$scope.loginUser.username,
                    	password:$scope.loginUser.password
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
                           if(response.data=="index"){
                        	   //location.reload();
                            location.href = './index.html#index';
                            $("#nav-header .login_btn .login").hover(function(){
                    			$(this).removeClass("btn-default").addClass("btn-danger");
                    			$("#nav-header .login_btn .regis").removeClass("btn-danger").addClass("btn-default");
                    		});
                              //location.path('/index');
                              // $location.path('/teacher/search');

                           }
                           if(response.data=="用户名或密码错误"){
                        	   alert("用户名或密码错误")
                           }
                        }, function errorCallback(response) {
                            console.log('登录请求失败');
                    });  
        };
}

     $scope.save= function() {
            $scope.showAllErr= $scope.loginForm.$invalid; //当内容不合法时，显示内容不合法
            if($scope.loginForm.$valid){
            	 $http({
                     method: 'post',
                     url: './image/validateRandNum',
                     data:{
                         value:$scope.inputcode,
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
                            if(response.data){
                         	   $scope.codeErr=false;
                                console.log('验证验证码成功');
                                $scope.login();
                                $scope.getcode();

                            }else{
                         	   $scope.codeErr=true;
                                console.log('验证验证码失败');
                                $scope.getcode();
                            }
                         }, function errorCallback(response) {
                             $scope.codeErr=true;
                             console.log('验证验请求证码失败');
                             $scope.getcode();
                             return $scope.codeErr;
                     });  
               
               } else{

                $scope.showAllErr= $scope.loginForm.$invalid; //当内容不合法时，显示内容

               }
        };
});




//guiliang

appController.controller('adCtr', function($scope, $http,$state, $stateParams,$timeout,$location) {
	$scope.getadtimes=0;
	$scope.getad = function() {
		$scope.getadtimes=$scope.getadtimes+1;
		     $http({
		        method: 'post',
		        url: './adverPage',
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
		        		if(response.data.advertisementList){
				               $scope.adList=response.data.advertisementList.dataList;}		        		
		        	}else if($scope.getadtimes<=4){
		        		$scope.getad();
		        	}
		               
		            }, function errorCallback(response) {

		               
		        });
		    };  
		  $scope.getad();
});



//光平学校部分
appController.controller('school', function($scope, $http,$state, $stateParams,$timeout,$location) {
	$scope.province='blank';
	$scope.schoollist=[];
 $scope.locationList=[];
	
	//初始化分页数据变量
/*	$scope.typeName=[  ' 改革动态',         '学科建设',        '评估排行',       '科研前沿'];
*/	
 $scope.urlName=[    'undergraduate','independent','height',];
	$scope.listRequest='./passage/passagelist';
	$scope.Map={
			undergraduate:0,
			independent:1,
			height:2,
	};
	$scope.$watch('$stateParams', function(newValue, oldValue, scope) {
	            $("[active]").children('li').toggleClass('active',false);
	            $("[active]").children('li').eq($scope.Map[$stateParams.taptype]).toggleClass('active',true);
	        }, true);

	    //初始化请求的类型的页面数据
	  $scope.page={
	        requstarray:[1,1,1],
	        totalRecord:[0,0,0],
	        currentPage:[1,1,1],
	        pageSize:[18,18,18],
	        
	        totalPage:[0,0,0],
	        currentindex:0,
	        passageType:[1,2,3],
	    } 
	//设置分页的数据
	$scope.pagingOptions = {
	    totalRecord:0,
	    currentPage: 0,
	    totalPage:0,
	};


 $scope.getschoolByType = function() {
 	$scope.province='';
     $http({
          method: 'post',
          url: './selectSchoolsSelectiveByPage',
           headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
           data:{
          	 type:$scope.page.passageType[$scope.page.currentindex],
          	currentPage:$scope.page.requstarray[$scope.page.currentindex],
          	pageSize:$scope.page.pageSize[$scope.page.currentindex],
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
              $scope.schoollist=response.data.dataList;
              $scope.page.totalRecord[$scope.page.currentindex]=response.data.totalRecord,
              $scope.page.totalPage[$scope.page.currentindex]=response.data.totalPage, 
              $scope.page.currentPage[$scope.page.currentindex]=$scope.page.requstarray[$scope.page.currentindex]
              //传给分页插件
	           $scope.pagingOptions = {
	                totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
	                currentPage: $scope.page.requstarray[$scope.page.currentindex],
	                totalPage:   $scope.page.totalPage[$scope.page.currentindex],
	            };
              
              }, function errorCallback(response) {
             	 $scope.schoollist = '';
             	  $scope.pagingOptions = {
       	                totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
       	                currentPage: $scope.page.currentPage[$scope.page.currentindex],
       	                totalPage:   $scope.page.totalPage[$scope.page.currentindex],
       	            };
          }); 
 }
	
	

 $scope.getschoolByTypeAndP = function() {

     $http({
          method: 'post',
          url: './selectSchoolsSelectiveByPage',
           headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
           data:{
          	 type:$scope.page.passageType[$scope.page.currentindex],
          	currentPage:$scope.page.requstarray[$scope.page.currentindex],
          	pageSize:$scope.page.pageSize[$scope.page.currentindex],
          	provinceId:$scope.province,
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
              $scope.schoollist=response.data.dataList;
              $scope.page.totalRecord[$scope.page.currentindex]=response.data.totalRecord,
              $scope.page.totalPage[$scope.page.currentindex]=response.data.totalPage, 
              $scope.page.currentPage[$scope.page.currentindex]=$scope.page.requstarray[$scope.page.currentindex]
              //传给分页插件
	           $scope.pagingOptions = {
	                totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
	                currentPage: $scope.page.requstarray[$scope.page.currentindex],
	                totalPage:   $scope.page.totalPage[$scope.page.currentindex],
	            };
              
              }, function errorCallback(response) {
             	 $scope.schoollist = '';
             	  $scope.pagingOptions = {
       	                totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
       	                currentPage: $scope.page.currentPage[$scope.page.currentindex],
       	                totalPage:   $scope.page.totalPage[$scope.page.currentindex],
       	            };
          }); 
 }
	
	
	
	
 $scope.getLocation = function() {
 	 $http({
          method: 'post',
          url: './getAllLocation',
           headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
/*              data:{
               value:$scope.inputcode,
           },*/
           transformRequest: function(obj) {  
             var str = [];  
             for(var p in obj){  
               str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
             }  
             return str.join("&");  
           } 
      
          }).then(function successCallback(response) {
               // 赋值
                 $scope.locationList=response.data;
              }, function errorCallback(response) {
              	
              	$scope.schoollist = '';
          });
 }
 
     $scope.getLocation();  
    
});



appController.controller('linksCrl', function($scope, $http,$state, $stateParams,$timeout,$location) {
	$scope.linkList1=[];
	$scope.getLinks = function() {
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
		                $scope.linkList1=response.data.linkList;
		            }, function errorCallback(response) {

		        });
		    };  
		    $scope.getLinks();
});








//名师页面
appController.controller('teacherlistCtrl', function($scope, $http,$state, $stateParams,$timeout,$location) {
 //初始化分页数据变量
 $scope.typeName=[  '名师风采',       '名师新论',       '教坛新秀',       '新秀论坛','搜索结果'];
 $scope.urlName=[   'elegant',        'newtheory',      'rookie',         'forum','search'];
 //对应接口
 $scope.listRequest=['./teacher/getAllTeacherByPage','./passage/passagelist','./teacher/getAllTeacherByPage','./passage/passagelist','./teacher/passagelist'];
 $scope.detailRequest=['./teacher/selectByPrimaryKey','./passage/main','./teacher/selectByPrimaryKey','./passage/main','./passage/main'];
 $scope.type=[0,25,1,26,0];
 $scope.Map={
       elegant:0,
     newtheory:1,
        rookie:2,
         forum:3,
         search:4,
         
 };
 $scope.teacherSchool=[];
  $scope.getallschool();
/*
 $scope.getschoolnames = function(a,s) {
	   if($scope.allschool.length==0){
		   $http({
	        method: 'post',
	        url: "./getSchools",
	        }).then(function successCallback(response) {
	       	 alert($scope.allschool.length);

	        	if(response.data){
	        		$scope.allschool=response.data;
	        		for(i=0;i<$scope.allschool.length;i++){
	        			
	 	        		if($scope.allschool[i].school.id==s){
	 	        			$scope.teacherSchool[a] = $scope.allschool[i].school.name;
	 	        		}
	 	        	}
	        		$scope.teacherSchool[a]=$scope.schoolname[a]?$scope.schoolname[a]:'未知学校';
	        	}
	        	
	           }, function errorCallback(response) {
	        		$scope.teacherSchool[a]=$scope.schoolname[a]?$scope.schoolname[a]:'未知学校';

	        });	 
		   
	   }else{
		  
		  for(i=0;i<$scope.allschool.length;i++){
       		if($scope.allschool[i].school.id==s){
       			$scope.teacherSchool[a] = $scope.allschool[i].school.name;
       		}
       	}
  		$scope.teacherSchool[a]=$scope.schoolname[a]?$scope.schoolname[a]:'未知学校';
	   }
	   
   };*/
 
 
 //根据$stateParams，标志红色背景
 $scope.$watch('$stateParams', function(newValue, oldValue, scope) {
             $("[active]").children('a').toggleClass('active',false);
             $("[active]").children('a').eq($scope.Map[$stateParams.taptype]).toggleClass('active',true);
             $("[active2]").children('a').toggleClass('active',false);
             $("[active2]").children('a').eq($scope.Map[$stateParams.taptype]).toggleClass('active',true);
         }, true);
 
 

 //分页插件数据
 $scope.pagingOptions = {
     totalRecord:0,
     currentPage: 0,
     totalPage:0,
 };

 //初始化请求的类型的页面数据
   $scope.page={
         requstarray:[1,1,1,1,1,1],
         totalRecord:[0,0,0,0,0,0],
         currentPage:[1,1,1,1,1,1],
         pageSize:[6,12,6,12,6,12],
         totalPage:[0,0,0,0,0,0],
         currentindex:0,
         data:[[],[],[],[],[],[],[]],
     } 

 // 当前文章
 $scope.detaildata=[];
 //热点推荐列表
 $scope.recommendListdata=[];
 //相关文论列表
 $scope.relativelistdata=[]; 

 // 当前位置数据
 $scope.current={
     related:false,
     teacherName:'',//根据文章作者
 }
 $scope.teacherSchool=[]; 
 $scope.getschoolnames = function(a,s) {
	   for(j=0;j<$scope.allschool.length;j++){
	 	       if($scope.allschool[j].school.id==s){
	 	        $scope.teacherSchool[a] = $scope.allschool[j].school.name;
	 	        }
	 	      }
	       $scope.teacherSchool[a]=$scope.teacherSchool[a]?$scope.teacherSchool[a]:'';
   };

 //获取名师列表：
 $scope.getTeacherData = function() {
 
  $http({
     method: 'post',
     url: $scope.listRequest[$scope.page.currentindex],
     data: { 
         currentPage: $scope.page.requstarray[$scope.page.currentindex],
         pageSize:$scope.page.pageSize[$scope.page.currentindex],
         type:$scope.type[$scope.page.currentindex],
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
            $scope.page.data[$scope.page.currentindex]=response.data.dataList;//
            
            $scope.page.totalRecord[$scope.page.currentindex]=response.data.totalRecord,
            $scope.page.totalPage[$scope.page.currentindex]=response.data.totalPage,
            $scope.page.currentPage[$scope.page.currentindex]=$scope.page.requstarray[$scope.page.currentindex],
            //传给分页插件
            $scope.pagingOptions = {
                 totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
                 currentPage: $scope.page.requstarray[$scope.page.currentindex],
                 totalPage:   $scope.page.totalPage[$scope.page.currentindex],
             };
            if($scope.allschool.length==0){
     		   $http({
     	        method: 'post',
     	        url: "./getSchools",
     	        }).then(function successCallback(response) {
     	        	if(response.data){
     	        		$scope.allschool=response.data;
     	        		 for(i=0;i<$scope.page.data[$scope.page.currentindex].length;i++){
     	        			 $scope.getschoolnames(i,$scope.page.data[$scope.page.currentindex][i].schoolId);
     	                }
     	        	}
     	           }, function errorCallback(response) {
     	        });	 
     	   }else{
     		  for(i=0;i<$scope.page.data[$scope.page.currentindex].length;i++){
     			 // alert($scope.page.data[$scope.page.currentindex][i].schoolId);
              	 $scope.getschoolnames(i,$scope.page.data[$scope.page.currentindex][i].schoolId);
              }
     	   }
           
         }, function errorCallback(response) {
        	 $scope.teacherSchool=[];
             $scope.pagingOptions = {
                 totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
                 currentPage: $scope.page.currentPage[$scope.page.currentindex],
                 totalPage:   $scope.page.totalPage[$scope.page.currentindex],
             };
     });
 };

//获取文章列表：
 $scope.getPagingData = function() {
  $http({
     method: 'post',
     url: $scope.listRequest[$scope.page.currentindex],
     data: {
            passageType:$scope.type[$scope.page.currentindex],
            pageSize:$scope.page.pageSize[$scope.page.currentindex],
            pageNum: $scope.page.requstarray[$scope.page.currentindex],
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

            $scope.page.data[$scope.page.currentindex]=response.data.passagePage.dataList;//
            $scope.recommendListdata=response.data.recommendList;//
            $scope.page.totalRecord[$scope.page.currentindex]=response.data.passagePage.totalRecord,
            $scope.page.totalPage[$scope.page.currentindex]=response.data.passagePage.totalPage,               
            $scope.page.currentPage[$scope.page.currentindex]=$scope.page.requstarray[$scope.page.currentindex],

            //传给分页插件
            $scope.pagingOptions = {
                 totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
                 currentPage: $scope.page.requstarray[$scope.page.currentindex],
                 totalPage:   $scope.page.totalPage[$scope.page.currentindex],
             };
         }, function errorCallback(response) {
             $scope.pagingOptions = {
                 totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
                 currentPage: $scope.page.currentPage[$scope.page.currentindex],
                 totalPage:   $scope.page.totalPage[$scope.page.currentindex],
             };

     });
 };

$scope.getTeacherdetail = function() {
$http({
     method: 'post',
     url: $scope.detailRequest[$scope.Map[$stateParams.taptype]],
     data: {
               id:$stateParams.articleid,
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
            $scope.detaildata=response.data;
            $scope.current.teacherName=response.data.name;
            $scope.getTeacherRelatedPre();
            $scope.current.teacherName=response.data.name;
            if($scope.allschool.length==0){
      		   $http({
      	        method: 'post',
      	        url: "./getSchools",
      	        }).then(function successCallback(response) {
      	        	if(response.data){
      	        		$scope.allschool=response.data;
      	        		$scope.getschoolnames(6,$scope.detaildata.schoolId);
      	        	}
      	           }, function errorCallback(response) {
      	        });	 
      	   }else{
               	 $scope.getschoolnames(6,$scope.detaildata.schoolId);
               }
      	   
         }, function errorCallback(response) {
        	 $scope.teacherSchool[6]="";
             console.log('请求失败');
         $scope.detaildata='';
         $scope.relativelistdata=[]; 
         $scope.current.teacherName="";
     });
 };

 

 $scope.getTeacherRelative = function() {
 $http({
      method: 'post',
      url: $scope.detailRequest[$scope.Map[$stateParams.taptype]],
      data: {
                id:$stateParams.articleid,
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
             $scope.detaildata=response.data;
             $scope.current.teacherName=response.data.name;
             $scope.getRelated();
             $scope.current.teacherName=response.data.name;
          }, function errorCallback(response) {
              console.log('请求失败');
          $scope.detaildata='';
          $scope.relativelistdata=[]; 
          $scope.current.teacherName="";
      });
  };
 
 
 
$scope.getPassagedetail = function() {
$http({
     method: 'post',
     url: $scope.detailRequest[$scope.Map[$stateParams.taptype]],
     data: {
             passageId:$stateParams.articleid,
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
            $scope.detaildata=response.data.passageMainList;
            $scope.recommendListdata=response.data.recommendList;
         }, function errorCallback(response) {
             console.log('请求失败');
             $scope.detaildata=[];
     });
 };
 
 // 相关文论列表
 // 先获得autor
 $scope.getRelated = function() {
   $http({
         method: 'post',
         url: './teacher/passagelist',
         data: {
                   author:$scope.current.teacherName,
                   pageSize:$scope.page.pageSize[$scope.page.currentindex],
                   pageNum:$scope.page.requstarray[$scope.page.currentindex],
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
            $scope.page.data[$scope.page.currentindex]=response.data.passagePage.dataList;//
            $scope.page.totalRecord[$scope.page.currentindex]=response.data.passagePage.totalRecord,
            $scope.page.totalPage[$scope.page.currentindex]=response.data.passagePage.totalPage,              
            $scope.page.currentPage[$scope.page.currentindex]=$scope.page.requstarray[$scope.page.currentindex],
            //传给分页插件
            $scope.pagingOptions = {
                 totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
                 currentPage: $scope.page.requstarray[$scope.page.currentindex],
                 totalPage:   $scope.page.totalPage[$scope.page.currentindex],
             };
         }, function errorCallback(response) {
          
             $scope.pagingOptions = {
                 totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
                 currentPage: $scope.page.currentPage[$scope.page.currentindex],
                 totalPage:   $scope.page.totalPage[$scope.page.currentindex],
             };

     });
 };
 
 
//相关文论小窗  
 $scope.getTeacherRelatedPre = function() {
     $http({
           method: 'post',
           url: './teacher/passagelist',
           data: {
                     author:$scope.current.teacherName,
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
              $scope.relativelistdata=response.data.passagePage.dataList;
           }, function errorCallback(response) {
               $scope.page.totalRecord[$scope.page.currentindex]=[]
       });
   };
/*
//也要获取作者名字
$scope.getTeacherRelatedDetail = function() {
$http({
     method: 'post',
     url: './passage/main',
     data: {
             passageId:$stateParams.relatedid,
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
            $scope.detaildata=response.data.passageMainList;
            $scope.recommendListdata=response.data.recommendList;
            $scope.current.teacherName=response.data.passageMainList[1].author;
            $scope.recommendListdata=response.data.recommendList;
         }, function errorCallback(response) {
             console.log('请求失败');
             $scope.detaildata=[];
     });
 };*/

 
 $scope.searchteacher = function() {
 	$scope.page.currentindex=4;
 	if($scope.teachersearchText.value){
			    $http({
			       method: 'post',
			       url: "./teacher/selectByTeacherSelective",
			       data: {
			    	   	  content:$scope.teachersearchText.value,
	                      pageSize:$scope.page.pageSize[$scope.page.currentindex],
	                      currentPage:$scope.page.requstarray[$scope.page.currentindex],
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
			    	   //console.log(response.data.dataList);
		               $scope.page.totalRecord[$scope.page.currentindex]=response.data.totalRecord,
		               $scope.page.totalPage[$scope.page.currentindex]=response.data.totalPage, 
		               $scope.page.currentPage[$scope.page.currentindex]=$scope.page.requstarray[$scope.page.currentindex],
		               //传给分页插件
		               $scope.pagingOptions = {
		                    totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
		                    currentPage: $scope.page.requstarray[$scope.page.currentindex],
		                    totalPage:   $scope.page.totalPage[$scope.page.currentindex],
		                };
//		              	//		              跳转
			            $location.path('/teacher/search');
		            }, function errorCallback(response) {
		               传给分页插件
		                $scope.pagingOptions = {
		                    totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
		                    currentPage: $scope.page.currentPage[$scope.page.currentindex],
		                    totalPage:   $scope.page.totalPage[$scope.page.currentindex],
		                };
		        });
		    };
 }
 
 $scope.teacherkey = function (e) {      
     var keycode = window.event ? e.keyCode : e.which;//获取按键编码  
     if (keycode == 13) {  
         $scope.searchteacher();//如果等于回车键编码执行方法  
     }  
 };      
 
 
});





//权威发布
appController.controller('authoritylistCtrl', function($scope, $http,$state, $stateParams,$timeout) {
//初始化分页数据变量
    $scope.typeName=[  '经典作家',         '领导讲话',        '厅部文件',       '通知报告'];
    $scope.urlName=[    'author',          'speach',          'document',       'inform'];
    $scope.listRequest='./passage/passagelist';
    $scope.detailRequest='./passage/main';
    $scope.Map={
          author:0,
          speach:1,
        document:2,
          inform:3,
    };
    $scope.$watch('$stateParams', function(newValue, oldValue, scope) {
                $("[active]").children('a').toggleClass('active',false);
                $("[active]").children('a').eq($scope.Map[$stateParams.taptype]).toggleClass('active',true);
                $("[active2]").children('a').toggleClass('active',false);
                $("[active2]").children('a').eq($scope.Map[$stateParams.taptype]).toggleClass('active',true);
            }, true);


        //初始化请求的类型的页面数据
      $scope.page={
            requstarray:[1,1,1,1],
            totalRecord:[0,0,0,0],
            currentPage:[1,1,1,1],
            pageSize:[12,12,12,12],
            totalPage:[0,0,0,0],
            currentindex:0,
            passageType:[4,5,6,7],
            data:[[],[],[],[],[]],
        } 
    //设置分页的数据
    $scope.pagingOptions = {
        totalRecord:0,
        currentPage: 0,
        totalPage:0,
    };
    // 当前正文
    $scope.detaildata=[];
    //热点列表
    $scope.recommendListdata=[];
    
 //获取文章列表：
    $scope.getPagingData = function() {
     $http({
        method: 'post',
        url: $scope.listRequest,
        data: {
               passageType:$scope.page.passageType[$scope.page.currentindex],
               pageSize:$scope.page.pageSize[$scope.page.currentindex],
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
              $scope.page.data[$scope.page.currentindex]=response.data.passagePage.dataList;//
               $scope.recommendListdata=response.data.recommendList;//
               $scope.page.totalRecord[$scope.page.currentindex]=response.data.passagePage.totalRecord,
               $scope.page.totalPage[$scope.page.currentindex]=response.data.passagePage.totalPage, 
               $scope.page.currentPage[$scope.page.currentindex]=$scope.page.requstarray[$scope.page.currentindex],

               //传给分页插件
               $scope.pagingOptions = {
                    totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
                    currentPage: $scope.page.requstarray[$scope.page.currentindex],
                    totalPage:   $scope.page.totalPage[$scope.page.currentindex],
                };
            }, function errorCallback(response) {
                $scope.pagingOptions = {
                    totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
                    currentPage: $scope.page.currentPage[$scope.page.currentindex],
                    totalPage:   $scope.page.totalPage[$scope.page.currentindex],
                };
        });
    };

 $scope.getPassagedetail = function() {
  $http({
        method: 'post',
        url: $scope.detailRequest,
        data: {
               passageId:$stateParams.articleid,
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
                $scope.detaildata=response.data.passageMainList;
               $scope.recommendListdata=response.data.recommendList;
            }, function errorCallback(response) {
                console.log('请求失败');
                 $scope.detaildata=[];
        });
    };

});





//马院风采
appController.controller('horseCtrl', function($scope, $http,$state, $stateParams,$timeout) {
//初始化分页数据变量
  $scope.typeName=[  ' 改革动态',         '学科建设',        '评估排行',       '科研前沿'];
  $scope.urlName=[    'reform',          'constrution',          'range',       'science'];
  $scope.listRequest='./passage/passagelist';
  $scope.detailRequest='./passage/main';
  $scope.Map={
		 reform:0,
        constrution:1,
        range:2,
      science:3,
  };
  $scope.$watch('$stateParams', function(newValue, oldValue, scope) {
              $("[active]").children('a').toggleClass('active',false);
              $("[active]").children('a').eq($scope.Map[$stateParams.taptype]).toggleClass('active',true);
              $("[active2]").children('a').toggleClass('active',false);
              $("[active2]").children('a').eq($scope.Map[$stateParams.taptype]).toggleClass('active',true);
          }, true);


      //初始化请求的类型的页面数据
    $scope.page={
          requstarray:[1,1,1,1],
          totalRecord:[0,0,0,0],
          currentPage:[1,1,1,1],
          pageSize:[12,12,12,12],
          totalPage:[0,0,0,0],
          currentindex:0,
          passageType:[8,9,10,11],
          data:[[],[],[],[],[]],
      } 
  //设置分页的数据
  $scope.pagingOptions = {
      totalRecord:0,
      currentPage: 0,
      totalPage:0,
  };
  // 当前正文
  $scope.detaildata=[];
  //热点列表
  $scope.recommendListdata=[];
  
//获取文章列表：
  $scope.getPagingData = function() {
   $http({
      method: 'post',
      url: $scope.listRequest,
      data: {
             passageType:$scope.page.passageType[$scope.page.currentindex],
             pageSize:$scope.page.pageSize[$scope.page.currentindex],
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
    	  
            $scope.page.data[$scope.page.currentindex]=response.data.passagePage.dataList;//
             $scope.recommendListdata=response.data.recommendList;//
             $scope.page.totalRecord[$scope.page.currentindex]=response.data.passagePage.totalRecord,
             $scope.page.totalPage[$scope.page.currentindex]=response.data.passagePage.totalPage, 
             $scope.page.currentPage[$scope.page.currentindex]=$scope.page.requstarray[$scope.page.currentindex],

             //传给分页插件
             $scope.pagingOptions = {
                  totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
                  currentPage: $scope.page.requstarray[$scope.page.currentindex],
                  totalPage:   $scope.page.totalPage[$scope.page.currentindex],
              };
          }, function errorCallback(response) {
              $scope.pagingOptions = {
                  totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
                  currentPage: $scope.page.currentPage[$scope.page.currentindex],
                  totalPage:   $scope.page.totalPage[$scope.page.currentindex],
              };
      });
  };

$scope.getPassagedetail = function() {
$http({
      method: 'post',
      url: $scope.detailRequest,
      data: {
             passageId:$stateParams.articleid,
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
              $scope.detaildata=response.data.passageMainList;
             $scope.recommendListdata=response.data.recommendList;
          }, function errorCallback(response) {
              console.log('请求失败');
               $scope.detaildata=[];
      });
  };

});


//普通单层页面：链接->单列表->文章详细
appController.controller('passage', function($scope, $http,$state, $stateParams,$timeout) {
//初始化分页数据变量
    $scope.typeName=[  '最新资讯',         '热点推荐',        '思政动态',       '马院头条','精品在线'];
    $scope.urlName=[    'latestnews',    'recommend',        'IPstate',     'headline',  'qualityOnline'];
    $scope.listRequest='./passage/passagelist';
    $scope.detailRequest='./passage/main';
    $scope.Map={
          latestnews:0,
          recommend:1,
        IPstate:2,
          headline:3,
          qualityOnline:4,
    };

        //初始化请求的类型的页面数据
    $scope.page={
          requstarray:[1,1,1,1,1],
          totalRecord:[0,0,0,0,0],
          currentPage:[1,1,1,1,1],
          pageSize:[12,12,12,12,12],
          totalPage:[0,0,0,0,0],
          currentindex:0,
          passageType:[1,12,2,3,24],
          data:[[],[],[],[],[],[]],
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
    $scope.recommendListdata=[];
    // $scope.guessListdata=[];
   
 //获取文章列表：
    $scope.getPagingData = function() {
     $http({
        method: 'post',
        url: $scope.listRequest,
        data: {
               passageType:$scope.page.passageType[$scope.page.currentindex],
               pageSize:$scope.page.pageSize[$scope.page.currentindex],
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
               $scope.page.data[$scope.page.currentindex]=response.data.passagePage.dataList;//
               $scope.recommendListdata=response.data.recommendList;//
               $scope.page.totalRecord[$scope.page.currentindex]=response.data.passagePage.totalRecord,
               $scope.page.totalPage[$scope.page.currentindex]=response.data.passagePage.totalPage,                $scope.page.currentPage[$scope.page.currentindex]=$scope.page.requstarray[$scope.page.currentindex],

               //传给分页插件
               $scope.pagingOptions = {
                    totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
                    currentPage: $scope.page.requstarray[$scope.page.currentindex],
                    totalPage:   $scope.page.totalPage[$scope.page.currentindex],
                };
            }, function errorCallback(response) {
                $scope.pagingOptions = {
                    totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
                    currentPage: $scope.page.currentPage[$scope.page.currentindex],
                    totalPage:   $scope.page.totalPage[$scope.page.currentindex],
                };
        });
    };
               // passageType:$scope.page.passageType[$scope.page.currentindex],

 $scope.getPassagedetail = function() {
  $http({
        method: 'post',
        url: $scope.detailRequest,
        data: {
               passageId:$stateParams.articleid,
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
                $scope.detaildata=response.data.passageMainList;
               $scope.recommendListdata=response.data.recommendList;
            }, function errorCallback(response) {
                console.log('请求失败');
                 $scope.detaildata='';
        });
    };

});


// onlinelesson
appController.controller('onlinelesson', function($scope, $http,$state, $stateParams,$timeout) {
//初始化分页数据变量  
    $scope.listRequest='./videoPage';
        //初始化请求的类型的页面数据
    $scope.page={
          requstarray:[1],
          totalRecord:0,
          currentPage:1,
          pageSize:6,
          totalPage:0,
          currentindex:0,
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
    $scope.recommendListdata=[];   
 //获取文章列表： 
   $scope.getPagingData = function() {
     $http({
        method: 'post',
        url: $scope.listRequest,
        data: {
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
        	if(response.data.videoList){
               $scope.detaildata=response.data.videoList.dataList;
               $scope.page.totalRecord=response.data.videoList.totalRecord,
               $scope.page.totalPage=response.data.videoList.totalPage,
               $scope.page.currentPage=$scope.page.requstarray[$scope.page.currentindex],
               //传给分页插件
               $scope.pagingOptions = {
                    totalRecord: $scope.page.totalRecord,
                    currentPage: $scope.page.requstarray[$scope.page.currentindex],
                    totalPage:   $scope.page.totalPage,
                };
        	}else{
        	    $scope.detaildata=[];

        	}
        	
            }, function errorCallback(response) {
                $scope.pagingOptions = {
                    totalRecord: $scope.page.totalRecord,
                    currentPage: $scope.page.currentPage,
                    totalPage:   $scope.page.totalPage,
                };
        });
    };   
    $scope.getPagingData();
});






//meeting
appController.controller('meeting', function($scope, $http,$state, $stateParams,$timeout) {
	
	
	
	
//初始化分页数据变量  
$scope.listRequest='./meetingPage';
$scope.detailRequest='./meetSelByPK';
$scope.meetingName={
		 value:'',
		 ID:'',
};

$scope.page={
     requstarray:[1],
     totalRecord:0,
     currentPage:1,
     pageSize:12,
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
$scope.getPagingData = function() {
$http({
   method: 'post',
   url: $scope.listRequest,
   data: {
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
	   if(response.data.meetingList){
          $scope.page.data[$scope.page.currentindex]=response.data.meetingList.dataList;//
          $scope.page.totalRecord=response.data.meetingList.totalRecord,
          $scope.page.totalPage=response.data.meetingList.totalPage,
          $scope.page.currentPage=$scope.page.requstarray[$scope.page.currentindex],
          //传给分页插件
          $scope.pagingOptions = {
               totalRecord: $scope.page.totalRecord,
               currentPage: $scope.page.requstarray[$scope.page.currentindex],
               totalPage:   $scope.page.totalPage,
           };
	   }
       }, function errorCallback(response) {
           $scope.pagingOptions = {
               totalRecord: $scope.page.totalRecord,
               currentPage: $scope.page.currentPage,
               totalPage:   $scope.page.totalPage,
           };
   });
};   
$scope.getPagingData();

$scope.getPassagedetail = function() {
 $http({
       method: 'post',
       url: $scope.detailRequest,
       data: {
              id:$stateParams.articleid,
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
              $scope.detaildata=response.data;
              $scope.meetingName.value=response.data.title;
              $scope.meetingName.ID=response.data.id;
           }, function errorCallback(response) {
               console.log('请求失败');
                $scope.detaildata='';
                $scope.meetingName.value='';
       });
   };
   
   
   $scope.myModal = "";
 //会议报名权限
   $scope.ApplyIF = function() {
	    if($scope.appusername){
	        console.log("用户已登录，有权限访问");
	        $('#myModal').modal('show');
	    }else{
	        alert("请先登录再报名会议！~");
	        $('#myModal').modal('hide');
	    }
      };   
   
   
});









//attent

//会议报名
appController.controller("apllyCtrl", function($scope, $http,$state, $stateParams){

	$scope.checkboxIF="";
  $scope.showAllErr=false;
  $scope.meetingApplyR='./userApplyform';
  $scope.apply={
 		 name:"",
 		 company:"",
 		 duty:"",
 		 jobTitle:"",
 		 phone:"",
 		 email:"",
 		 code:"",
  }

    //请求
$scope.attentmeeting = function() {
          if($scope.apllyForm.$valid){
          	$scope.showAllErr=false;
          	if($scope.checkboxIF){
          	    $scope.meetingApplyR='./userApplyformCode';
          	}else{
          	    $scope.meetingApplyR='./userApplyform';
          	}
                  $http({
                  method: 'post',
                  url: $scope.meetingApplyR,
                  data:{
                  	meetingId:$scope.meetingName.ID,
                  	name:$scope.apply.name,
                  	company:$scope.apply.company,
                  	duty:$scope.apply.duty,
                  	phone:$scope.apply.phone,
                  	mail:$scope.apply.email,
                  	jobTitle:$scope.apply.jobTitle,
                  	code:$scope.apply.code,
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
                        alert(response.data.message);
                        if(response.data.message=="报名成功"){
                      	    $scope.apply={
                      	      		 name:"",
                      	      		 company:"",
                      	      		 duty:"",
                      	      		 jobTitle:"",
                      	      		 phone:"",
                      	      		 email:"",
                      	      		 code:"",
                      	       }
                        }
                      }, function errorCallback(response) {
                          console.log('报名请求失败');
                  });  
      }else{
      	$scope.showAllErr=true;
      };
  }  
});






//  我有话说 
appController.controller('saying', function($scope, $http,$state, $stateParams,$timeout) {
//初始化分页数据变量
    $scope.typeName=[  '热帖榜',         '实时更新',        '编辑发帖'];
    $scope.urlName=[    'hotPost',          'update',          'post'];
    $scope.listRequest=['./userpostPageVist', './userpostPage', './postinsert'];
    $scope.detailRequest='./userpostsePK';
    $scope.good;
    

    $scope.Map={
          hotPost:0,
          update:1,
            post:2    
        };
    $scope.$watch('$stateParams', function(newValue, oldValue, scope) {
                $("[active]").children('a').toggleClass('active',false);
                $("[active]").children('a').eq($scope.Map[$stateParams.taptype]).toggleClass('active',true);
            }, true);

     $scope.page={
          requstarray:[1,1,1],
          totalRecord:[0,0,0],
          currentPage:[1,1,1],
          pageSize:[15,12,1],
          totalPage:[0,0,0],
          currentindex:0,
          data:[[],[],[]],
      } 

    //设置分页的数据
    $scope.pagingOptions = {
        totalRecord:0,
        currentPage: 0,
        totalPage:0,
    };

    // 当前列表
    $scope.detaildata=[];

 //获取文章列表：
    $scope.getPagingData = function() {
     $http({
        method: 'post',
        url: $scope.listRequest[$scope.page.currentindex],
        data: {
               pageSize:$scope.page.pageSize[$scope.page.currentindex],
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
        	if(response.data.postList){
               $scope.page.data[$scope.page.currentindex]=response.data.postList.dataList;//
               $scope.page.totalRecord[$scope.page.currentindex]=response.data.postList.totalRecord,
               $scope.page.totalPage[$scope.page.currentindex]=response.data.postList.totalPage,
               $scope.page.currentPage[$scope.page.currentindex]=$scope.page.requstarray[$scope.page.currentindex],
                //传给分页插件
               $scope.pagingOptions = {
                    totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
                    currentPage: $scope.page.requstarray[$scope.page.currentindex],
                    totalPage:   $scope.page.totalPage[$scope.page.currentindex],
                };
        		}
            }, function errorCallback(response) {
                $scope.pagingOptions = {
                    totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
                    currentPage: $scope.page.currentPage[$scope.page.currentindex],
                    totalPage:   $scope.page.totalPage[$scope.page.currentindex],
                };
        });
    };

    //点赞,，页面绑定
    $scope.upputsaying = function() {
      $http({
            method: 'post',
            url: './post_like',
            data: {
                   post_id:$stateParams.articleid,
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
            	    $scope.good=response.data;
                }, function errorCallback(response) {
                    console.log('点赞请求失败');
            });
        };
       
    
 $scope.getPassagedetail = function() {
  $http({
        method: 'post',
        url: $scope.detailRequest,
        data: {
               id:$stateParams.articleid,
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
               $scope.detaildata=response.data;
               $scope.upputsaying(); 
            }, function errorCallback(response) {
                console.log('请求失败');
                 $scope.detaildata=[];
        });
    };

//
////用户插入论坛信息，页面绑定
// $scope.upputsaying = function() {
//  $http({
//        method: 'post',
//        url: $scope.detailRequest[$scope.Map[$stateParams.taptype]],
//        data: {
//               title:'',//??？？
//               context:'',
//               release_time:'',//获取当前时间
//           },
//         headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
//         transformRequest: function(obj) {  
//           var str = [];  
//           for(var p in obj){  
//             str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
//           }  
//           return str.join("&");  
//         } 
//        }).then(function successCallback(response) {               
//              alert(response.data.massage);
//            }, function errorCallback(response) {
//                console.log('请求失败');
//        });
//    };
     
    
});




 // 课程资源
appController.controller('lesson', function($scope, $http,$state, $stateParams,$timeout,$location) {
    //判断是否登录
	
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
	        		if($scope.appusername){
	        	        console.log("用户已登录，有权限访问");
	        	    }else{
	        	        //alert("注册成为会员才能查看课程资源！~");
	        	        //跳转
	        	        //location.href = './index.html#login';
	        	    }
	        	}
	            }, function errorCallback(response) {
	            	$scope.appusername="";
	               
	        });
	
    //初始化分页数据变量
    $scope.bookName=[  '基础', '原理', '概论', '纲要'];
    $scope.bookUrl=[    'foundation',        'principle',   'generality',       'essential'];
    $scope.passageTypeName=[    '热点难点',  '案例资源',   '视频资源',   '推荐教案'  ,'参考资料', '精品课件'];
    $scope.passageTypeUrl=[    'theories',    'cases',       'vidios',   'schemes' ,'references' ,'courseware']; 
    $scope.getCatalogueUrl=[    './1/chapter', './3/chapter',  './2/chapter',   './4/chapter' ]; 
    $scope.pageSize=11;
    $scope.Map={
          foundation:0,
          principle:1,
          generality:2,
           
          essential:3,
          theories:0,
          cases:1,
          vidios:2,
          schemes:3,
          references:4,
          courseware:5,
    };
    //当前位置数据
    $scope.current={
        ifCata:'',//章节|阅读书目
    };
    $scope.$watch('$stateParams', function(newValue, oldValue, scope) {
    			$scope.showNav(0);

                $("[active2]").children('a').toggleClass('active',false);
                $("[active2]").children('a').eq($scope.Map[$stateParams.bookName]).toggleClass('active',true);
                $("[active]").children('a').toggleClass('active',false);
                $("[active]").children('a').eq($scope.Map[$stateParams.bookName]).toggleClass('active',true);
                if($location.url().indexOf("catalogue") > 0 ){
                    $scope.current.ifCata='catalogue';
                } else if($location.url().indexOf("booklist") > 0 ){
                    $scope.current.ifCata='booklist';
                }else if($location.url().indexOf("search") > 0 ){
                    $scope.current.ifCata='search';
                }
            }, true);

    //设置分页的数据
    $scope.pagingOptions = {
        totalRecord:0,
        currentPage: 0,
        totalPage:0,
    };

     //初始化请求的类型的页面数据
    $scope.page={
          requstarray:[1,1,1,1,1,1,1,1,1],
          totalRecord:[0,0,0,0,0,0,0,0,0],
          currentPage:[1,1,1,1,1,1,1,1,1],
          totalPage:[0,0,0,0,0,0,0,0,0],
          currentindex:0,
          passageType:[17,19,22,20,18,21,23,0,0],//+6booklist+8chapter+7search
          data:[[],[],[],[],[],[],[],[],[]],
      } 

    // // 定义列表
    // 书本列表
    $scope.booklistdata=[];
    //章节列表
    $scope.chapterListdata=[];
    //阅读书目列表
    $scope.readbookListdata=[];
    //精品在线列表
    $scope.boutiqueListdata=[];   
    // //passage通用列表
    // $scope.detaillistdata=[];    
    // passage通用文章详细
    $scope.detaildata=[];
    // //热点列表
    $scope.recommendListdata=[];
   //章节后的模块
       $scope.chapterDetail={
          theories:[],
          cases:[],
          vidios:[],
          schemes:[],
          references:[],
          courseware:[],
    };
    //获取书本列表请求
//    $scope.getLessonList = function() {
//         $http({
//            method: 'post',
//            url: "./lesson",
//         headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
//         transformRequest: function(obj) {  
//           var str = [];  
//           for(var p in obj){  
//             str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
//           }  
//           return str.join("&");  
//         } 
//            }).then(function successCallback(response) {
//                   $scope.booklistdata=response.data.lessonList;
//                   $scope.recommendListdata=response.data.recommendList;
//                   //console.log($scope.booklistdata);
//                }, function errorCallback(response) {
//            });
//        };


    //获取目录请求
    $scope.getCatalogueData = function() {
    	$scope.page.currentindex=8;
         $http({
            method: 'post',
            url: $scope.getCatalogueUrl[$scope.Map[$stateParams.bookName]],
            data:{
                pageNum: $scope.page.requstarray[$scope.page.currentindex],
            	pageSize:20,
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
            	if(response.data){
                   $scope.chapterListdata=response.data.chapterList.dataList;
                   $scope.readbookListdata=response.data.readPassageList;
                   $scope.boutiqueListdata=response.data.qualityOnlinePassageList;
                   $scope.page.totalRecord[$scope.page.currentindex]=response.data.chapterList.totalRecord,
                   $scope.page.totalPage[$scope.page.currentindex]=response.data.chapterList.totalPage, 
                   $scope.page.currentPage[$scope.page.currentindex]=$scope.page.requstarray[$scope.page.currentindex],
                   //传给分页插件
                   $scope.pagingOptions = {
                        totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
                        currentPage: $scope.page.requstarray[$scope.page.currentindex],
                        totalPage:  $scope.page.totalPage[$scope.page.currentindex],
                    };}
                }, function errorCallback(response) {
                	//传给分页插件
                    $scope.pagingOptions = {
                        totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
                        currentPage: $scope.page.currentPage[$scope.page.currentindex],
                        totalPage:   $scope.page.totalPage[$scope.page.currentindex],
                    };
            });
        };

     // 章节后的模块
     $scope.getChapterData = function() {
         $http({
            method: 'post',
            url: "./"+($scope.Map[$stateParams.bookName]+1)+"/"+$stateParams.chapter+"/block",
         headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
         transformRequest: function(obj) {  
           var str = [];  
           for(var p in obj){  
             str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
           }  
           return str.join("&");  
         } 
            }).then(function successCallback(response) {
                 $scope.chapterDetail={
                      theories:response.data.theoryAnalyseList,
                      cases:response.data.caseResourceList,
                      vidios:response.data.onlineVedioList,
                      schemes:response.data.recommendResourcesList,
                      references:response.data.referenceResourcesList,
                      courseware:response.data.PPTSourcesList,
                };
                }, function errorCallback(response) {
                    //请求失败后清空，避免张冠李戴
                       $scope.chapterDetail={
                              theories:[],
                              cases:[],
                              vidios:[],
                              schemes:[],
                              references:[],
                              courseware:[],
                        };
            });
        };

  //获取章节下某文章类型请求
  ///{lessonId}/{chapterId}/blockpassagelist
    $scope.getPassageListData = function() {
         $http({
            method: 'post',
            url: "./"+($scope.Map[$stateParams.bookName]+1)+"/"+$stateParams.chapter+"/blockpassagelist",
            data: {
                passageType:$scope.page.passageType[$scope.page.currentindex],
                pageNum: $scope.page.requstarray[$scope.page.currentindex],
                pageSize:$scope.pageSize,//***
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
     
               $scope.page.data[$scope.page.currentindex]=response.data.pagingResult.dataList;//
               $scope.recommendListdata=response.data.recommendList;//
               $scope.page.totalRecord[$scope.page.currentindex]=response.data.pagingResult.totalRecord,
               $scope.page.totalPage[$scope.page.currentindex]=response.data.pagingResult.totalPage, 
               $scope.page.currentPage[$scope.page.currentindex]=$scope.page.requstarray[$scope.page.currentindex],
               //传给分页插件
               $scope.pagingOptions = {
                    totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
                    currentPage: $scope.page.requstarray[$scope.page.currentindex],
                    totalPage:   $scope.page.totalPage[$scope.page.currentindex],
                };
            }, function errorCallback(response) {
               //传给分页插件
                $scope.pagingOptions = {
                    totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
                    currentPage: $scope.page.currentPage[$scope.page.currentindex],
                    totalPage:   $scope.page.totalPage[$scope.page.currentindex],
                };
        });
    };
    $scope.chapterName={
    		value: "",
    }
  //获取章节下某文章类型下某文章详细请求
    $scope.getPassagedetailData = function() {
         $http({
            method: 'post',
            url: "./lesson/chapter/lessonpassage",
            data: {
                      passageId:$stateParams.articleid,
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
            	//console.log(response);
               $scope.detaildata=response.data.passage;
               $scope.recommendListdata=response.data.recommendList;

            }, function errorCallback(response) {
                console.log('请求失败');
                 $scope.detaildata=[];
            });
        };
        //获取章节名称
        $scope.getchapterName = function() {
             $http({
                method: 'post',
                url: "./selectChapter",
                data:{
                	chapterId:$stateParams.chapter,
                	lessonId:$scope.Map[$stateParams.bookName]+1,
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
                   $scope.chapterName.value=response.data.name;
                }, function errorCallback(response) {
                    console.log('请求章名失败');
                    $scope.chapterName.value='';
                });
        };
        
        
    //获取阅读书目列表
    $scope.getBooklisrData = function() {
     	$scope.page.currentindex=6;
         $http({
            method: 'post',
            url: "./passage/passagelist",
            data: {
               passageType:$scope.page.passageType[$scope.page.currentindex],
               pageSize:$scope.pageSize,
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
               $scope.page.data[$scope.page.currentindex]=response.data.passagePage.dataList;//
               $scope.page.totalRecord[$scope.page.currentindex]=response.data.passagePage.totalRecord,
               $scope.page.totalPage[$scope.page.currentindex]=response.data.passagePage.totalPage, 
               $scope.page.currentPage[$scope.page.currentindex]=$scope.page.requstarray[$scope.page.currentindex],
               //传给分页插件
               $scope.pagingOptions = {
                    totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
                    currentPage: $scope.page.requstarray[$scope.page.currentindex],
                    totalPage:   $scope.page.totalPage[$scope.page.currentindex],
                };
            }, function errorCallback(response) {
               //传给分页插件
                $scope.pagingOptions = {
                    totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
                    currentPage: $scope.page.currentPage[$scope.page.currentindex],
                    totalPage:   $scope.page.totalPage[$scope.page.currentindex],
                };
        });
    };


    //获取阅读书目下某书本详细
    $scope.getBookdetailData = function() {
         $http({
            method: 'post',
            url: "./passage/main",
            data: {
                   passageId:$stateParams.articleid,
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
                $scope.detaildata=response.data.passageMainList;
               $scope.recommendListdata=response.data.recommendList;

            }, function errorCallback(response) {
                console.log('请求失败');
                 $scope.detaildata=[];
            });
        };
        
        
        $scope.searchlesson = function() {
         	$scope.page.currentindex=7;
	    	if($scope.lessonsearch.value){
				    $http({
				       method: 'post',
				       url: "./lessonPassage/search/list",
				       data: {
				    	   	  lessonId:$scope.Map[$stateParams.bookName]+1,
				    	   	  searchInfo:$scope.lessonsearch.value,
				              pageSize:$scope.pageSize,
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
			               $scope.page.totalRecord[$scope.page.currentindex]=response.data.totalRecord,
			               $scope.page.totalPage[$scope.page.currentindex]=response.data.totalPage, 
			               $scope.page.currentPage[$scope.page.currentindex]=$scope.page.requstarray[$scope.page.currentindex],
			               //传给分页插件
			               $scope.pagingOptions = {
			                    totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
			                    currentPage: $scope.page.requstarray[$scope.page.currentindex],
			                    totalPage:   $scope.page.totalPage[$scope.page.currentindex],
			                };
			              	//		              跳转
				            $location.path('/lesson/'+$stateParams.bookName+'/search');
			            }, function errorCallback(response) {
			               //传给分页插件
			                $scope.pagingOptions = {
			                    totalRecord: $scope.page.totalRecord[$scope.page.currentindex],
			                    currentPage: $scope.page.currentPage[$scope.page.currentindex],
			                    totalPage:   $scope.page.totalPage[$scope.page.currentindex],
			                };
			        });
			    };
        }
	    
	    $scope.lessonkey = function (e) {      
            var keycode = window.event ? e.keyCode : e.which;//获取按键编码  
            if (keycode == 13) {  
                $scope.searchlesson();//如果等于回车键编码执行方法  
            }  
        };  
        
        
});
//首页
//注册
//登陆
//单页面：学校
//单页面：links
//会议论坛