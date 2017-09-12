 var appService = angular.module('appService', []);
 // appService.service('ms', function(){
 //    this.name="123";
 //            this.init=function(obj,args){
 //                return (function(){
 //                    this.fillHtml(obj,args);
 //                    this.bindEvent(obj,args);
 //                })();
 //            },
 //            //填充html
 //            this.fillHtml=function(obj,args){
 //                if (args.pageCount<=1) {
 //                    obj.hide();
 //                }
 //                return (function(){
 //                    obj.empty();
 //                    //填充信息
 //                        obj.append("<p>共"+args.listCount+"条&nbsp;第"+args.current+"页/共"+args.pageCount+"页&nbsp;跳转到<input class='selectPageInput' name='indexS'  type='text'>页&nbsp;<a href='javascript:;' class='selectPage'>&nbsp;跳转&nbsp;</a> </p>")

 //                    //上一页
 //                    if(args.current > 1){
 //                        obj.append('<span>【<a href="javascript:;" class="prevPage">上一页</a>】</span>');
 //                    }else{
 //                        obj.remove('.prevPage');
 //                        obj.append('<span >【<a href="javascript:;" class="disabled">上一页</a>】</span>');
 //                    }


 //                    //中间页码
 //                    if(args.current != 1 && args.current >= 4 && args.pageCount != 4){
 //                        obj.append('<span>【<a href="javascript:;" class="tcdNumber">'+1+'</a>】</span>');
 //                    }
 //                    if(args.current-2 > 2 && args.current <= args.pageCount && args.pageCount > 5){
 //                        obj.append('<span> . . . </span>');
 //                    }
 //                    var start = args.current -2,end = args.current+2;
 //                    if((start > 1 && args.current < 4)||args.current == 1){
 //                        end++;
 //                    }
 //                    if(args.current > args.pageCount-4 && args.current >= args.pageCount){
 //                        start--;
 //                    }
 //                    for (;start <= end; start++) {
 //                        if(start <= args.pageCount && start >= 1){
 //                            if(start != args.current){
 //                                obj.append('<span>【<a href="javascript:;" class="tcdNumber">'+ start +'</a>】</span>');
 //                            }else{
 //                                obj.append('<span class="current">'+ start +'</span>');
 //                            }
 //                        }
 //                    }
 //                    if(args.current + 2 < args.pageCount - 1 && args.current >= 1 && args.pageCount > 5){
 //                        obj.append('<span> . . . </span>');
 //                    }
 //                    if(args.current != args.pageCount && args.current < args.pageCount -2  && args.pageCount != 4){
 //                        obj.append(' <span>【<a href="javascript:;" class="tcdNumber">'+args.pageCount+'</a>】</span>');
 //                    }



 //                    //下一页
 //                    if(args.current < args.pageCount){
 //                        obj.append('<span>【<a href="javascript:;" class="nextPage">下一页</a>】</span>');
 //                    }else{
 //                        obj.remove('.nextPage');
 //                        obj.append('<span>【<a href="javascript:;" class="disabled">下一页</a>】</span>');
 //                    }
                    
 //                })();
 //            },
 //            //绑定事件
 //            this.bindEvent=function(obj,args){
 //                return (function(){
 //                    obj.on("click","a.tcdNumber",function(){
 //                        var current = parseInt($(this).text());
 //                        this.fillHtml(obj,{"listCount":args.listCount,"current":current,"pageCount":args.pageCount});
 //                        if(typeof(args.backFn)=="function"){
 //                            args.backFn(current);
 //                        }
 //                    });
 //                    //上一页
 //                    obj.on("click","a.prevPage",function(){
 //                        var current = parseInt(obj.children("span.current").text())-1;
 //                        this.fillHtml(obj,{"listCount":args.listCount,"current":current,"pageCount":args.pageCount});
 //                        if(typeof(args.backFn)=="function"){
 //                            args.backFn(current);
 //                        }
 //                    });
 //                    //下一页
 //                    obj.on("click","a.nextPage",function(){
 //                        var current = parseInt(obj.children("span.current").text())+1;
 //                        this.fillHtml(obj,{"listCount":args.listCount,"current":current,"pageCount":args.pageCount});
 //                        if(typeof(args.backFn)=="function"){
 //                            args.backFn(current);
 //                        }
 //                    });
 //                    //输入跳转
 //                    obj.on("click","a.selectPage",function(){
 //                        var current = parseInt($(".selectPageInput").val());

 //                        if (current&&current<=args.pageCount&&current>=1&&current!=parseInt(obj.children("span.current").text())) {
 //                            this.fillHtml(obj,{"listCount":args.listCount,"current":current,"pageCount":args.pageCount});
 //                            if(typeof(args.backFn)=="function"){
 //                                args.backFn(current);
 //                            }
 //                        }else {
 //                        if(typeof(args.backFn)=="function"){
 //                                args.backFn("你的输入有误");
 //                            }
 //                        }
                        
 //                    });
 //                })();
 //            },

 //            $.fn.createPage = function(options){
 //                var args = $.extend({
 //                    listCount : 100,
 //                    pageCount : 10,
 //                    current : 1,
 //                    backFn : function(){}
 //                },options);
 //                // ms.init(this,args);
 //            };
 //            this.aaa =function(){
 //                alert("1");
 //            }
 //            // element.createPage({
 //            //         listCount : 100,
 //            //         pageCount : 10,
 //            //         current : 1,
 //            // });
     
 // });
 
