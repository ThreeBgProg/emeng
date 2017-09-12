package com.huiming.emeng.common;

/**
 * Created by LeoMs on 2017/6/21 0021.
 */
public class CreateChapterDataSql {

    private String name;
    private int lesson;
    private int number;
    public static void getSql(){
        String sql = "insert into chapter (lesson,number,name) values(";
        for(int i = 1; i <= 4; i++){
            for(int j = 1; j <= 15; j++){
                System.out.println(sql + "'" + i + "'," + "'" + j + "'," + "'" + "第 " + j + " 章内容');");
            }
        }
    }

    public static void main(String[] args){
        getSql();
    }
}
