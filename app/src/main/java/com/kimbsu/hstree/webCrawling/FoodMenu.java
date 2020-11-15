package com.kimbsu.hstree.webCrawling;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class FoodMenu implements Serializable {
    //	connUrl + 날짜정보 형태로 크롤링 할 웹 사이트 링크를 결정한다
    private static final String connUrl = "http://www.hstree.org/admin_hs/main/z1_food1.php?gmglory=1&page_no=34&years=";
    private static final int YEARNUM = 1900;
    private static final int MONTHNUM = 1;

    private int year;
    private int month;
    private int day;
    private int date;
    private String s_date;
    private int week;

    // 파싱까지 끝낸 날짜 그리고 1관, 2관의 메뉴
    private String[] menu_day;
    private String[][] menu_1gn;
    private String[][] menu_2gn;

    public FoodMenu() {
//		멤버변수 초기 값 설정
        init();
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public String getMonthString() {
        String ret = "";
        if (month < 10)
            ret = "0" + month;
        else
            ret = Integer.toString(month);
        return ret;
    }

    public int getDay() {
        return day;
    }

    public String getDayString() {
        String ret = "";
        if (day < 10)
            ret = "0" + day;
        else
            ret = Integer.toString(day);
        return ret;
    }

    public String getTodayString() {
        return year + "년 " + month + "월 " + day + "일";
    }

    public String[] getMenu_day() {
        String[] ret = new String[7];
        for (int i = 0; i < 7; i++) {
            if (!(menu_day[i].equals(""))) {
                String[] str = menu_day[i].split("/");
                String[] str2 = str[1].split(" ");
                ret[i] = year + "년 " + str[0] + "월 " + str2[0] + "일 " + str2[1];
            } else {
                ret[i] = "";
            }
        }
        return ret;
    }

    public String[][] getMenu_1gn() {
        return menu_1gn;
    }

    public String[][] getMenu_2gn() {
        return menu_2gn;
    }

    public int calculateDate(String m_date, String date_format) throws ParseException {
        int ret = 0;

        SimpleDateFormat dateFormat = new SimpleDateFormat(date_format);
        Date nDate = dateFormat.parse(m_date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nDate);
        ret = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        return ret;
    }

    //	향후에 안드로이드 스튜디오에서 달력 컴포넌트로 날짜 입력 받고, 입력받은 날짜에 해당하는 식단표를 보여주기 위함
    public boolean setDate(int year, int month, int day) throws ParseException {
        if (this.year != year)
            return false;

        this.month = month;
        this.day = day;

        this.date = calculateDate(year + "-" + getMonthString() + "-" + getDayString(), "yyyy-MM-dd");
        switch (this.date) {
            case 0:
                this.s_date = "일";
                break;
            case 1:
                this.s_date = "월";
                break;
            case 2:
                this.s_date = "화";
                break;
            case 3:
                this.s_date = "수";
                break;
            case 4:
                this.s_date = "목";
                break;
            case 5:
                this.s_date = "금";
                break;
            case 6:
                this.s_date = "토";
                break;
        }
        this.week = 1;
//		week 값은 데이터를 읽어오는데 아무런 상관이 없는 듯 하다

        return true;
    }

    //	멤버변수 초기 값 설정
    @SuppressWarnings("deprecation")
    public void init() {
        Date date = new Date();
        this.year = date.getYear() + YEARNUM;
//		getYear()는 (현재 년도-1900) 을 반환함
        this.month = date.getMonth() + MONTHNUM;
//		getMonth()는 (현재 월-1) 을 반환함
        this.day = date.getDate();
//		getDate()는 현재 일을 반환함
        this.date = date.getDay();
//		getDay()는 현재 요일을 Sun=0, Mon=1, ..., Sat=6 형태의 정수로 반환함
        switch (this.date) {
            case 0:
                this.s_date = "일";
                break;
            case 1:
                this.s_date = "월";
                break;
            case 2:
                this.s_date = "화";
                break;
            case 3:
                this.s_date = "수";
                break;
            case 4:
                this.s_date = "목";
                break;
            case 5:
                this.s_date = "금";
                break;
            case 6:
                this.s_date = "토";
                break;
        }
        this.week = 1;
//		week 값은 데이터를 읽어오는데 아무런 상관이 없는 듯 하다

//        this.month = 2;
//        this.day = 12;
//        this.date = 3;
//        this.s_date = "수";    // test code

//		this.month = 1;
//		this.day = 28;
//		this.s_date = "화";	// test code
    }

    public String[] todayMenu() {
        // 0~2 번째 방에는 1관의 메뉴, 3~5 번째 방에는 2관의 메뉴가 아침-점심-저녁 순서로 저장된다
        String ret[] = new String[6];

        String str = "";
        if (month > 9) {
            if (day > 9)
                str = month + "/" + day + " (" + s_date + ")";
            else
                str = month + "/0" + day + " (" + s_date + ")";
        } else {
            if (day > 9)
                str = "0" + month + "/" + day + " (" + s_date + ")";
            else
                str = "0" + month + "/0" + day + " (" + s_date + ")";
        }

        int index = -1;
        for (int i = 0; i < 7; i++) {
            if (menu_day[i].equals(str)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            for (int i = 0; i < 3; i++) {
                ret[i] = menu_1gn[i][index];
                ret[i + 3] = menu_2gn[i][index];
            }
        }

        System.out.println();
        return ret;
    }

    public void weekMenu() throws IOException {
        String menu[][] = new String[4][7];
//		설날이나 추석같은 명절에는 장학관 단기휴관 가능성 존재 -> menu 배열에 항상 데이터가 모두 존재하지는 않다.

        String connUrl2 = "";
        if (month > 9) {
            connUrl2 = connUrl + year + "&months=" + month + "&days=" + day + "&week=" + week;
        } else {
            connUrl2 = connUrl + year + "&months=0" + month + "&days=" + day + "&week=" + week;
        }

        Document doc = Jsoup.connect(connUrl2).userAgent("Chrome/80.0.3987.149").get();
        Element element = doc.selectFirst("tbody");
        if (element != null) {
            List<Element> menuList = element.children();
//				<tr> 태그들의 집합
            for (int row = 0; row < 4; row++) {
                List<Element> underTR = menuList.get(row).children();
//					<tr> 태그 아래에 있는 <th> 태그 또는 <td> 태그들의 집합을 가져온다
                for (int col = 0; col < 7; col++) {
                    menu[row][col] = underTR.get(col + 1).html();
                }
            }
//				<th> 태그와 <td> 태그의 innerHtml 값을 2차원 배열 menu에 저장함 -> 필요한 데이터만 파싱하면 됨
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 7; j++) {
                    menu[i][j] = menu[i][j].replace("<br>", "");
                    menu[i][j] = menu[i][j].replace("<hr>", "");
                    menu[i][j] = menu[i][j].replace("</strong></small>", "");
                    menu[i][j] = menu[i][j].replace("<small><strong>1", "");
                    menu[i][j] = menu[i][j].replace("<small><strong>2", "");
                    menu[i][j] = menu[i][j].replace("\n", "");
//						필요하지 않은 문자열들을 제거하는 작업
                }
            }

            menu_day = new String[7]; // 날짜 정보
            menu_1gn = new String[3][7]; // 1관 메뉴
            menu_2gn = new String[3][7]; // 2관 메뉴
            for (int col = 0; col < 7; col++) {
                menu_day[col] = menu[0][col];
            }
            for (int row = 1; row < 4; row++) {
                for (int col = 0; col < 7; col++) {
                    String str[] = menu[row][col].split("관");
                    if (str.length > 2) {
                        menu_1gn[row - 1][col] = str[1];
                        menu_2gn[row - 1][col] = str[2];
                    } else {
                        menu_1gn[row - 1][col] = menu_2gn[row - 1][col] = "금일 등록된 식단표가 없습니다.";
                    }
                }
            }
            menu = null;
//				사용하지 않는 메모리는 가비지 컬렉터가 제거하도록 함

            return;
        }
    }
}