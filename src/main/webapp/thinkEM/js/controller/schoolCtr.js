var schoolControll = angular.module('schoolControll', []);
schoolControll.controller('school', function($scope, $http,$state, $stateParams,$timeout,$location) {
	$scope.schoollist=[];
	  $scope.getSchool = function() {
		     $http({
		        method: 'post',
		        url: './getSchools',
		        /*data:{

		        }*/
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
		              $scope.dynamicList=response.data.dynamicList;
		               
		            }, function errorCallback(response) {

		        });
		    };  
		    $scope.getSchool();
});

