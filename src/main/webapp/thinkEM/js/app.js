var routerApp = angular.module('myApp', ['ui.router','appDirective','appController','appService','indexController']);
/**
 * 由于整个应用都会和路由打交道，所以这里把$state和$stateParams这两个对象放到$rootScope上，方便其它地方引用和注入。
 * 这里的run方法只会在angular启动的时候运行一次。
 * @param  {[type]} $rootScope
 * @param  {[type]} $state
 * @param  {[type]} $stateParams
 * @return {[type]}
 */
routerApp.run(function($rootScope, $state, $stateParams) {
	$rootScope.$state = $state;
	$rootScope.$stateParams = $stateParams;
});
   routerApp.config(function($stateProvider, $urlRouterProvider) {  
	$urlRouterProvider.otherwise('/index');  
	$stateProvider 

	//名师页面
	.state('teacher', {  
		url: '/teacher',  
		 views: {
				'':{  templateUrl: 'templates/teacherContent.html', },
				 'hotlist@teacher': {templateUrl: 'templates/hotContent.html' },
			  }
	})    
	 .state('teacher.taptype', {  
		url: '/:taptype',  
		views: {
				 'detail@teacher': { 
				 	templateUrl: function($stateParams){
				 		if ($stateParams.taptype=='elegant'||$stateParams.taptype=='rookie') {
				 			return 'templates/teacherList.html';
				 		}else if ($stateParams.taptype=='search') {
				 			return 'templates/teachersearch.html';
				 		}
				 		return 'templates/newsList.html';
				 	},
					controller:  function($scope, $state, $stateParams) {
						$scope.showNav(0);
					    $scope.current.related=false;
					    $scope.page.currentindex=$scope.Map[$stateParams.taptype];
					    // 不同tap对应不同请求
					    if ($stateParams.taptype=='elegant'||$stateParams.taptype=='rookie') {
					 			$scope.getTeacherData();
					 		}else if($stateParams.taptype=='search') {
					 		}
					    	else{
					    		$scope.getPagingData();
					 		}
					    },
					},
			  }
	}) 

	 .state('teacher.taptype.articleid', {  
		url: '/:articleid',  
		views: {
				 'detail@teacher': {
					templateUrl: function($stateParams){
				 		if ($stateParams.taptype=='elegant'||$stateParams.taptype=='rookie') {
				 			return 'templates/teacherDetail.html';
				 		}
				 		return 'templates/articalT2.html';
				 	},
				 	controller:function($scope, $state, $stateParams) {
				 		$scope.showNav(0);
				 		 // 不同tap对应不同请求
					    $scope.page.currentindex=$scope.Map[$stateParams.taptype];
						    if ($stateParams.taptype=='elegant'||$stateParams.taptype=='rookie') {
						 			$scope.getTeacherdetail();
						 		}else{
						 			$scope.getPassagedetail();
						 		}
					 		$scope.current.related=false;
					    },
				},
				'relatedlist@teacher': {
					// 不同tap对应不同请求
					templateUrl: function($stateParams){
				 		if ($stateParams.taptype=='elegant'||$stateParams.taptype=='rookie') {
				 			return 'templates/teacherrelatedContent.html' ;
				 		}
				 		return 'templates/blank.html';
				 	},
			 },
			}
	}) 

	.state('teacher.taptype.articleid.related', {  
		url: '/related',  
		views: {
				 'detail@teacher': {
					templateUrl: 'templates/teacherrelatedlist.html',
					controller:  function($scope, $state, $stateParams) {
						$scope.showNav(0);
					    $scope.current.related=true;
					    $scope.page.currentindex=5;
					    $scope.page.currentPage[5]=1;
					    $scope.page.requstarray[5]=1;
					    $scope.page.data[5]=[];
					    $scope.getTeacherRelative();
					},
				},
				'relatedlist@teacher': {template: '' },
			}
	})
	/*.state('teacher.taptype.articleid.related.relatedid', {  
		url: '/:relatedid',  
		views: {
				 'detail@teacher': {
					templateUrl: 'templates/articalT2.html',
					controller:  function($scope, $state, $stateParams) {
					    $scope.current.related=true;
					    $scope.getTeacherRelatedDetail();					
					},
				},
				'relatedlist@teacher': {template: ''},
			}
	})  
*/



	 .state('authority', {  
		url: '/authority',  
		views:{
			'':{ templateUrl: 'templates/authorityContent.html',},
			'hotlist@authority': { templateUrl: 'templates/hotContent.html'},
		},
	})  
	 .state('authority.taptype', {  
		url: '/:taptype',  
		views: {
				 'detail@authority': {templateUrl: 'templates/newsList.html',
					controller: function($scope, $state, $stateParams) {
						$scope.showNav(0);
						$scope.page.currentindex=$scope.Map[$stateParams.taptype];
					    $scope.getPagingData();					
					},

				},
			}
	}) 
	 .state('authority.taptype.articleid', {  
		url: '/:articleid',  
		views: {
				 'detail@authority': {templateUrl: 'templates/articalT2.html',
					controller: function($scope, $state, $stateParams) {
						$scope.showNav(0);
						$scope.page.currentindex=$scope.Map[$stateParams.taptype];
					    $scope.getPassagedetail();					
					},
				},
			}
	})  

	
	

.state('horse', {  
		url: '/horse',  
		views:{
			'':{ templateUrl: 'templates/horseContent.html',},
			'hotlist@horse': { templateUrl: 'templates/hotContent.html'},
		},
	})  
	 .state('horse.taptype', {  
		url: '/:taptype',  
		views: {
				 'detail@horse': {templateUrl: 'templates/newsList.html',
					controller: function($scope, $state, $stateParams) {
						$scope.showNav(0);
						$scope.page.currentindex=$scope.Map[$stateParams.taptype];
					    $scope.getPagingData();	
					},

				},
			}
	}) 
	 .state('horse.taptype.articleid', {  
		url: '/:articleid',  
		views: {
				 'detail@horse': {templateUrl: 'templates/articalT2.html',
					controller: function($scope, $state, $stateParams) {
						$scope.showNav(0);
						  $scope.page.currentindex=$scope.Map[$stateParams.taptype];
					    $scope.getPassagedetail();					
					},
				},
			}
	})  


	.state('passage', {  
		url: '/passage',  
		views: {				
				'':{templateUrl: 'templates/newsContent.html' },
			}
	})  
	.state('passage.taptype', {  
		url: '/:taptype',  
		views: {
				'detail@passage': {
					templateUrl: 'templates/newsList.html' ,
					controller: function($scope, $state, $stateParams) {
						$scope.showNav(0);
						$scope.page.currentindex=$scope.Map[$stateParams.taptype];
						$scope.getPagingData();
					}
			},
				'hotlist@passage': {templateUrl: 'templates/hotContent.html'},
			}
	})  
	 .state('passage.taptype.articleid', {  
		url: '/:articleid',  
		views: {
				 'detail@passage': {templateUrl: 'templates/articalT2.html',
				 controller: function($scope, $state, $stateParams) {
					 $scope.showNav(0);
						$scope.getPassagedetail();
					}
				},
				 'guess@passage': {templateUrl: 'templates/guessContent.html'},
			}
	})   



//在线课程
.state('onlinelesson', {  
		url: '/onlinelesson',  
		views: {
				'':{templateUrl: 'templates/onlineContent.html',
					controller: function($scope, $state, $stateParams) {
						 $scope.showNav(0);
						}				
				
				},
				
				 'hotlist@onlinelesson': {templateUrl: 'templates/hotContent.html'},
			}
	})

//搜索
.state('search', {  
		url: '/search',  
		views: {
				'':{templateUrl: 'templates/searcher.html', 
					controller: function($scope, $state, $stateParams) {
						$scope.showNav(0);
					}
				
				},
				
				'detail@search': {templateUrl: 'templates/searchlist.html' ,},
				'hotlist@search': {templateUrl: 'templates/hotContent.html'},
			}
	})

.state('saying', {  
		url: '/saying',  
		templateUrl: 'templates/sayingContent.html',
	})  
	 .state('saying.taptype', {  
		url: '/:taptype',  
		views: {
				 'detail@saying': { 
					templateUrl:  function($stateParams){return 'templates/'+$stateParams.taptype+'.html';},
					controller:  function($scope, $state, $stateParams) {
						$scope.showNav(0);
					    $scope.page.currentindex=$scope.Map[$stateParams.taptype];
					    if ($stateParams.taptype!='post') {
					            $scope.getPagingData();
					    }
					},
				},

			}
	}) 
	 .state('saying.taptype.articleid', {  
		url: '/:articleid',  
		views: {
				 'detail@saying': {
					templateUrl: 'templates/sayingDetail.html',
					controller:  function($scope, $state, $stateParams) {
						$scope.showNav(0);
					  $scope.getPassagedetail();
					  $scope.upputsaying(); 
					},
				},
			}
	})   

.state('lesson',{  
		 url: '/lesson',//书本列表
		 views: {
				 '':{
					templateUrl: 'templates/lessonContent.html',
					   controller: function() {
					}
				 },
				 'detail@lesson': {
					templateUrl: 'templates/lessonList.html',
					controller: function($scope, $state, $stateParams) {
						$scope.showNav(0);
					//	$scope.getLessonList();
					}
				},
				'hotlist@lesson': {
					templateUrl: 'templates/hotContent.html'
				},
			}
	})  
  

 .state('lesson.bookName', {  
	url: '/:bookName',  //目录，阅读书目，精品在线
	views: {
		},
}) 
	 .state('lesson.bookName.catalogue', {  
		url: '/catalogue',  
		views: {
				 'detail@lesson': {
					templateUrl: 'templates/lessonCatalohue.html',
					 controller: function($scope, $state, $stateParams) {
						 $scope.showNav(0);
					   $scope.page.currentindex=8;
					   $scope.page.requstarray[$scope.page.currentindex]=1,
                    	$scope.current.ifCata='catalogue';
                    	 $scope.getCatalogueData();
					},
				},
			},
	}) 
	
	
		.state('lesson.bookName.search', {  
		url: '/search',  
		views: {
				 'detail@lesson': {
					// templateUrl: 'templates/lessonreadbook.html',
					templateUrl: 'templates/lessonSH.html',
					 controller: function($scope, $state, $stateParams) {
						 $scope.showNav(0);
						$scope.page.currentindex=7;
						$scope.page.requstarray[$scope.page.currentindex]=1,
                        $scope.current.ifCata='search';
						$scope.searchlesson();
					}
				},
			},
	}) 
	 .state('lesson.bookName.booklist', {  
		url: '/booklist',  
		views: {
				 'detail@lesson': {
					 templateUrl: 'templates/lessonreadBook.html',
					 controller: function($scope, $state, $stateParams) {
						 $scope.showNav(0);
                        $scope.current.ifCata='booklist';
 					   $scope.page.currentindex=6;
					   $scope.page.requstarray[$scope.page.currentindex]=1,
                        $scope.getBooklisrData();
					}
				},
			},
	}) 
	
	.state('lesson.bookName.booklist.articleid', {  
		url: '/:articleid',  
		views: {
				 'detail@lesson': {
					// templateUrl: 'templates/lessonreadBookdetail.html',
					templateUrl: 'templates/articalT2.html',
					 controller: function($scope, $state, $stateParams) {
						 $scope.showNav(0);
                        $scope.current.ifCata='booklist';
						  $scope.page.currentindex=6;
						   $scope.getBookdetailData();
					}
				},
			},
	}) 
	.state('lesson.bookName.catalogue.chapter', {  
		url: '/:chapter',  
		views: {
				 'detail@lesson': {
					templateUrl: 'templates/lessonCatalohue2.html',
					 controller: function($scope, $state, $stateParams) {
						 $scope.showNav(0);
					 	$scope.getChapterData();
					 	$scope.getchapterName();
					}
				},
			},
	}) 
	
	.state('lesson.bookName.catalogue.chapter.passageType', {  
		url: '/:passageType',  
		views: {
				 'detail@lesson': {
					templateUrl: 'templates/lessonpassagetype.html',
					 controller: function($scope, $state, $stateParams) {
						 $scope.showNav(0);
					    $scope.page.currentindex=$scope.Map[$stateParams.passageType];
						$scope.page.requstarray[$scope.page.currentindex]=1,
					    $scope.getPassageListData();
					    $scope.getchapterName();
					}
				},
			},
	}) 

	.state('lesson.bookName.catalogue.chapter.passageType.articleid', {  
		url: '/:articleid',  
		views: {
				 'detail@lesson': {
					templateUrl: 'templates/articalT1.html',
					 controller: function($scope, $state, $stateParams) {
						 $scope.showNav(0);
						 $scope.getPassagedetailData();
						 $scope.getchapterName();
					}
				},
			},
	}) 

 
	.state('meeting', {  
		url: '/meeting',  
		views: {				
				'':{templateUrl: 'templates/guesscontent2.html' },				
				'detail@meeting': {templateUrl: 'templates/meetinglist.html' ,
					controller: function($scope, $state, $stateParams) {
						 $scope.showNav(0);
						}	
				},
				'hotlist@meeting': {templateUrl: 'templates/hotContent.html'},
			}
	})  
	.state('meeting.articleid', {  
		url: '/:articleid',  
		views: {
				'detail@meeting': {templateUrl: 'templates/articleT3.html' ,
				 controller: function($scope, $state, $stateParams) {
					 $scope.showNav(0);
					 $scope.getPassagedetail();
					}
			},
			'guess@meeting': {templateUrl: 'templates/guessContent.html'},
			}
	})  
	.state('school', {  
		url: '/school',  
		views: {				
				'':{templateUrl: 'templates/school.html'},				
			}
	})  
	.state('school.taptype', {  
		url: '/:taptype',  
		views: {
				'detail@school': {templateUrl: 'templates/schoollist.html' ,
					 controller: function($scope, $state, $stateParams) {
						 $scope.showNav(0);

						  $scope.page.currentindex=$scope.Map[$stateParams.taptype];
						 $scope.getschoolByType();
					 }
						}
				},
	})  
	
	
	.state('links', {  
		url: '/links',  
		views: {				
				'':{templateUrl: 'templates/links.html' ,
					 controller: function($scope, $state, $stateParams) {
						 $scope.showNav(0);

						  
					 }
				},				
			}
	})  
	.state('login', {  
		url: '/login',  
		views: {				
				'':{templateUrl: 'templates/login.html',
					controller: function($scope, $state, $stateParams) {
						 $scope.showNav(0);
					 }},				
			}
	})  
	.state('register', {  
		url: '/register',  
		views: {				
				'':{templateUrl: 'templates/register.html',
					controller: function($scope, $state, $stateParams) {
						 $scope.showNav(0);

						  
					 }	
				},				
			}
	})  
	.state('index', {
			url: '/index',  
			templateUrl: 'templates/indexNav.html',
		   controller: function($scope, $state, $stateParams) {
			   			$scope.getusername();
			   		 $scope.showNav(0);
					}
	});  
});  




   
