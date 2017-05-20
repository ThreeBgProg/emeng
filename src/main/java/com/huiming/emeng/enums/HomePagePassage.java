package com.huiming.emeng.enums;

/**
 * Created by LeoMs on 2017/5/21 0021.
 */
public enum HomePagePassage {
    //最新资料，在主页显示7条
    NEWESTINFORMATION(1,7),
    //思政动态，在主页显示12条
    IDEOLOGICALANDPOLITICALDYNAMIC(2,12),
    //马院头条，在主页显示12条
    MAYUANHEADLINE(3,12),
    //教师新论，在主页显示3条
    TEACHERPASSAGE(4,3);


    //文章类型
    private Integer type;

    //在主页显示的数目
    private Integer displayNums;

    HomePagePassage(Integer type, Integer displayNums) {
        this.type = type;
        this.displayNums = displayNums;
    }
}
