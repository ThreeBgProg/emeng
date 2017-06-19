package com.huiming.emeng.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by LeoMs on 2017/6/9 0009.
 */
public class CreatePassageDataSql {


    public static void printSql() {
        String title;
        String author;
        String link;
        String content;
        String annex;
        int chapter;
        int lesson;
        String publishTime;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");

        Byte[] typeList = new Byte[]{new Byte("0"), new Byte("1"), new Byte("2"),
                new Byte("3"), new Byte("4"),
                new Byte("5"), new Byte("6"),
                new Byte("7"), new Byte("8"),
                new Byte("9"), new Byte("10"),
                new Byte("11"), new Byte("12"),
                new Byte("13"), new Byte("14"),
                new Byte("15"), new Byte("16"),
                new Byte("17"), new Byte("18"),
                new Byte("19"), new Byte("20"),
                new Byte("21"), new Byte("22"),
                new Byte("23"), new Byte("24")};
        Byte type;
        Byte[] stateList = new Byte[]{new Byte("0"), new Byte("1"), new Byte("2")};
        Byte state;
        String sql = "insert into passage " +
                "(titile,author,link,publish_time,content,type,state,annex,lesson,chapter) " +
                "values(";

        for (int i = 0; i < 100000; i++) {
            lesson = (int) (Math.random() * 4 + 1);
            chapter = (int) (Math.random() * 15 + 1);
            if (lesson == 1) {
                type = typeList[13];
            } else if (lesson == 2) {
                type = typeList[14];
            } else if (lesson == 3) {
                type = typeList[15];
            } else {
                type = typeList[16];
            }

            state = stateList[1];
            title = "titiledata" + getRandomString(8);
            author = "Mrs" + getRandomString(5);
            link = "http://www." + getRandomString(8);
            content = "contentdata" + getRandomString(6);
            annex = "annexdata" + getRandomString(4);
            try {
                Date start = simpleDateFormat.parse(" 2008-07-10 19:20:00 ");
                Date end = simpleDateFormat.parse(" 2017-05-10 19:20:00 ");
                long startTime = start.getTime();
                long endTime = end.getTime();
                long betweenTime = endTime - startTime;
                long randomTime = (long) (Math.random() * 10) * betweenTime / 10 + startTime;
                publishTime = getRandomDateString(randomTime);
                System.out.println(sql + "'" + title + "',"
                        + "'" + author + "',"
                        + "'" + link + "',"
                        + "'" + publishTime + "',"
                        + "'" + content + "',"
                        + "'" + type + "',"
                        + "'" + state + "',"
                        + "'" + annex + "',"
                        + "'" + lesson + "',"
                        + "'" + chapter + "');");

            } catch (Exception e) {
                throw new RuntimeException("zhuancuo");
            }


        }
    }

    public static String getRandomString(int length) {

        Random random = new Random();
        String allChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            stringBuffer.append(allChar.charAt(number));
        }
        return stringBuffer.toString();
    }

    public static String getRandomDateString(long date) throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        return simpleDateFormat.format(new Date(date));
    }

    public static void printSql2() {
        String title;
        String author;
        String link;
        String content;
        String annex;
        int recommend;
        Random random = new Random();

        String publishTime;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");

        Byte[] typeList = new Byte[]{new Byte("0"), new Byte("1"), new Byte("2"),
                new Byte("3"), new Byte("4"),
                new Byte("5"), new Byte("6"),
                new Byte("7"), new Byte("8"),
                new Byte("9"), new Byte("10"),
                new Byte("11"), new Byte("12"),
                new Byte("13"), new Byte("14"),
                new Byte("15"), new Byte("16"),
                new Byte("17"), new Byte("18"),
                new Byte("19"), new Byte("20"),
                new Byte("21"), new Byte("22"),
                new Byte("23"), new Byte("24")};
        Byte type;
        Byte[] stateList = new Byte[]{new Byte("0"), new Byte("1"), new Byte("2")};
        Byte state;
        String sql = "insert into passage " +
                "(titile,author,link,publish_time,content,type,state,annex,recommend) " +
                "values(";

        for (int i = 0; i < 1000; i++) {
            recommend = (int)Math.random()* 1000 + 1;
            state = stateList[random.nextInt(3)];
            title = "titiledata" + getRandomString(8);
            author = "Mrs" + getRandomString(5);
            link = "http://www." + getRandomString(8);
            content = "contentdata" + getRandomString(6);
            annex = "annexdata" + getRandomString(4);
            type = typeList[random.nextInt(25)];
            try {
                Date start = simpleDateFormat.parse(" 2008-07-10 19:20:00 ");
                Date end = simpleDateFormat.parse(" 2017-05-10 19:20:00 ");
                long startTime = start.getTime();
                long endTime = end.getTime();
                long betweenTime = endTime - startTime;
                long randomTime = (long) (Math.random() * 10) * betweenTime / 10 + startTime;
                publishTime = getRandomDateString(randomTime);
                System.out.println(sql + "'" + title + "',"
                        + "'" + author + "',"
                        + "'" + link + "',"
                        + "'" + publishTime + "',"
                        + "'" + content + "',"
                        + "'" + type + "',"
                        + "'" + state + "',"
                        + "'" + annex + "',"
                        + "'" + recommend + "');"
                        );

            } catch (Exception e) {
                throw new RuntimeException("zhuancuo");
            }


        }
    }


//    public static void main(String args[]){
////        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
////        try {
////            Date date = simpleDateFormat.parse(" 2008-07-10 19:20:00 ");
////            Date date2 = simpleDateFormat.parse(" 2017-05-10 19:20:00 ");
////            System.out.println(date.getTime());
////            System.out.println(date2.getTime());
////        }catch (Exception e){
////            throw new RuntimeException("zhuancuo");
////        }
////        printSql();
//        printSql2();
//
//   }
}
