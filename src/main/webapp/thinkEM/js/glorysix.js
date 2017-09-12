/**
 * Created by guiliang on 2017/6/23 0023.
 */
$(function () {
    var url = location.href;    //获取iframe的url
    var lesson_id= getUrlParam('lessonid');    //获取参数即课程id
    var chapter_number= getUrlParam('chapternumber');    //获取参数即章节
    console.log('url传过来的'+lesson_id+chapter_number)
    function getUrlParam(name)
    {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r!=null) return unescape(r[2]); return null; //返回参数值
    }

    $('.my-next').on('click',function(){
    	 var type = $(this).parents('tr').children('.my_type').text();
    	 var new_href = '../thinkEM/chapterArticleList.html?lessonid='+lesson_id+'&chapternumber='+chapter_number+'&type='+type;
         console.log('newhref'+new_href);
         location.href =  new_href;
    })
})