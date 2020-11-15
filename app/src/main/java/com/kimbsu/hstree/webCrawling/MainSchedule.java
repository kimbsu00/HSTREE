package com.kimbsu.hstree.webCrawling;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

public class MainSchedule implements Serializable {
    private static final String connUrl = "http://www.hstree.org/c03/c03_03_02.php?cur_ym=";
    private static final String connUrl2 = "http://www.hstree.org";
    private static final int YEARNUM = 1900;
    private static final int MONTHNUM = 1;

    private int year;
    private int month;
    private String s_month;

    private int[] weekDay;
    //	해당 월의 1일이 되는 날짜의 요일을 의미한다. { Sun=0, Mon=1, ..., Sat=6 }
    private int startDay;
    //	월간 주요 일정의 데이터를 저장하는 n*2 사이즈의 벡터이다.
//	n은 해당 월에 존재하는 일정의 수를 의미한다. 두 번째 인덱스는 { 0=날짜, 1=일정 정보 }
    private Vector<String[]> monthContent;
    private String yearContentSrc;

    public MainSchedule() {
        init();
//		멤버변수 초기 값 설정
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int[] getWeekDay() {
        return weekDay;
    }

    public int getStartDay() {
        return startDay;
    }

    public Vector<String[]> getMonthContent() {
        return monthContent;
    }

    public String getYearContentSrc() {
        return yearContentSrc;
    }

    public void setDate(int year, int month) {
        this.year = year;
        this.month = month;
        if (month > 9)
            this.s_month = Integer.toString(month);
        else
            this.s_month = "0" + Integer.toString(month);

//        crawlingSchedule();
    }

    //	멤버변수 초기 값 설정
    public void init() {
        Date date = new Date();
        this.year = date.getYear() + YEARNUM;
//		getYear()는 (현재 년도-1900) 을 반환함
        this.month = date.getMonth() + MONTHNUM;
//		getMonth()는 (현재 월-1) 을 반환함
        if (month > 9)
            this.s_month = Integer.toString(month);
        else
            this.s_month = "0" + Integer.toString(month);

//		하루에 주요일정이 3개(최대값)인 경우
//        this.year = 2019;
//        this.s_month = "11";    // test code

//		1일이 일요일인 경우
//		this.year = 2019;
//		this.s_month = "12";	// test code

        this.startDay = -1;
        this.monthContent = new Vector<String[]>();
        this.yearContentSrc = "";

        weekDay = new int[7];
    }

    public void crawlingSchedule() throws IOException {
        this.monthContent.clear();

        Document doc = Jsoup.connect(connUrl + year + s_month).userAgent("Chrome/80.0.3987.149").get();
        Element element = doc.getElementsByClass("content_wrap nanum").get(0);
//			System.out.println();	// test code for debug
        if (element != null) {
            Element content = element.getElementsByClass("content").get(0);
            if (content != null) {
                Element tbody = content.selectFirst("tbody");
                if (tbody != null) {
                    List<Element> trTags = tbody.select("tr");
                    boolean isStartDay = false;
                    for (int i = 0; i < trTags.size(); i++) {
                        List<Element> tdTags = trTags.get(i).children();
                        if (tdTags != null) {
                            for (int j = 0; j < tdTags.size(); j++) {

//									이번 달의 1일이 무슨 요일인지 알아내는 작업
                                if ((!isStartDay) && (!tdTags.get(j).html().equals("&nbsp;"))) {
                                    this.startDay = j;
                                    isStartDay = true;
                                }

//									이번 주에 일정이 있는지 확인하는 작업
                                if (tdTags.get(j).attributes().hasDeclaredValueForKey("onclick")) {
                                    String str[] = new String[2];
                                    str[0] = tdTags.get(j).selectFirst("label").html();
                                    str[1] = "";
                                    String s_date = "";
                                    if (Integer.parseInt(str[0]) > 9)
                                        s_date = str[0];
                                    else
                                        s_date = "0" + str[0];
//										화성시 장학관 주요 일정 사이트에는 하루에 최대 3개의 일정이 등록 될 수 있는 것 같다
                                    for (int k = 0; k < 3; k++) {
                                        Element table = content.getElementById(this.year + s_month + s_date + k);
                                        if (table != null && k == 0) {
                                            str[1] += table.select("tr").get(1).selectFirst("label").html();
                                        } else if (table != null) {
                                            str[1] += "\n" + table.select("tr").get(1).selectFirst("label").html();
                                        }
                                    }
                                    str[1].trim();
//                                    str[1] += "delete";
//                                    str[1].replace("\n\rdelete", "");

                                    this.monthContent.add(str);
                                }
                            }
                        }
                    }

                }
            }
        }

        Element content2 = doc.getElementsByClass("content2").get(0);
        this.yearContentSrc = connUrl2 + content2.selectFirst("img").attributes().get("src");
//			System.out.println(); // test code for debug


        int[] temp = {0, 1, 2, 3, 4, 5, 6};
        for (int i = 0; i < 7; i++) {
            temp[i] -= (this.startDay - 1);
            if (temp[i] < 0)
                temp[i] += 7;
            if (temp[i] >= 7)
                temp[i] %= 7;
        }

        for (int i = 0; i < 7; i++) {
//            Log.e("temp[" + i + "]", String.valueOf(temp[i]));
            weekDay[temp[i]] = i;
        }
    }

}
