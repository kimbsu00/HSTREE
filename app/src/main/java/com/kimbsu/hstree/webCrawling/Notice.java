package com.kimbsu.hstree.webCrawling;

import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Notice implements Serializable {
    //	connUrl + pageNum 형태로 크롤링 할 웹 사이트 링크를 결정한다
    private static final String connUrl = "http://www.hstree.org/c03/c03_03_01.php?mode=list&tblName=c03_04_01&shTitle=&shKey=&sort=&asc=&cate=&start=";
    private static final String connUrl2 = "http://www.hstree.org";
    private static final String replaceUrl = "/c03/c03_03_01.php?mode=list&tblName=c03_04_01&shTitle=&shKey=&sort=&asc=&cate=&start=";
    //	상단 고정 공지사항의 데이터 개수
    private static final int UPPERDATA = 2;
    //	일반 공지사항의 데이터 개수
    private static final int NORDATA = 5;

    private int maxPageNum;
    private int pageNum;

    //	 상단 고정 공지사항(n개)는 공지사항의 페이지 수와 관계없이 항상 고정되어 있기 때문에 처음 한 번만 크롤링 하면 되기 때문에 불린 변수로 체크함
    private boolean isUpperNotice;
    //	상단 고정 공지사항(n개)의 제목과 하이퍼링크를 저장, n*2 사이즈의 배열로 { 0=제목, 1=하이퍼링크 }
    private Vector<String[]> upperNotice;
    public Vector<UpperNotice> m_upperNotice;
    //	일반 공지사항의 제목과 하이퍼링크를 저장, k*5 사이즈의 배열로 { 0=제목, 1=하이퍼링크, 2=작성자, 3=작성일, 4=조회수 }
    private Vector<String[]> notice;
    private Vector<NormalNotice> normalNotice;

    //	사용자가 선택한 공지사항 게시글에 포함되어 있는 다운로드 항목의 제목과 다운로드 링크을 갖는다. { 0=제목, 1=하이퍼링크 }
    private ArrayList<String[]> download;
    //	공지사항 게시글에 등록된 이미지 파일의 하이퍼링크를 갖는다.
//    private String postImg;
    private ArrayList<String> postImg;
    private boolean isImgExist;


    public Notice() {
        init();
//		멤버변수 초기 값 설정
    }

    public Vector<String[]> getUpperNotice() {
        return upperNotice;
    }

    public Vector<UpperNotice> getM_upperNotice() {
        return m_upperNotice;
    }

    public Vector<String[]> getNotice() {
        return notice;
    }

    public Vector<NormalNotice> getNormalNotice() {
        return normalNotice;
    }

    public ArrayList<String[]> getDownload() {
        return download;
    }

    public ArrayList<String> getPostImg() {
        return postImg;
    }

    public int getMaxPageNum() {
        return maxPageNum;
    }

    public int getPageNum() {
        return pageNum;
    }

    public boolean getIsImgExist() {
        return isImgExist;
    }

    public void setIsImgExist(boolean m_isImgExist) {
        this.isImgExist = m_isImgExist;
    }

    public void setPageNum(boolean isPlus) {
//        +10을 해주어야 하는 경우 = 크롤링 오류로 -10이 된 경우
        if (isPlus) {
            this.pageNum += 10;
        }
//        -10을 해주어야 하는 경우 = 크롤링 오유로 +10이 된 경우
        else {
            this.pageNum -= 10;
        }
    }

    public String getListIndex() {
        return ((pageNum / 10) + 1) + " / " + (maxPageNum / 10);
    }

    //	멤버변수 초기 값 설정
    public void init() {
        this.maxPageNum = 10000;
        this.pageNum = 0;

        this.isUpperNotice = false;

        upperNotice = new Vector<String[]>();
        m_upperNotice = new Vector<>();
        notice = new Vector<String[]>();
        normalNotice = new Vector<>();
        download = new ArrayList<String[]>();
        postImg = new ArrayList<String>();

        isImgExist = false;
    }

    //    공지사항 목록을 크롤링 하는 함수
    public void crawlingNotice() throws IOException {
        String connUrl3 = connUrl + pageNum;
        Element doc = Jsoup.connect(connUrl3).userAgent("Chrome/80.0.3987.149").get();
        Element element = doc.getElementById("listBoardFrm");
        if (element != null) {
            Element tbody = element.selectFirst("table").selectFirst("tbody");
            if (tbody != null) {
                List<Element> trTags = tbody.children();
//					일반 공지사항은 페이지가 변경 될 때마다 새롭게 적재되어야 하기 때문에 매번 초기화 해준다
                normalNotice.clear();
                for (int i = 1; i < trTags.size(); i++) {
                    Element trTag = trTags.get(i);
//						확인할 Element가 null이 아닌 경우에만 확인 (안전장치)
                    if (trTag != null) {
//							일반 공지사항의 경우
                        if (trTag.attributes().hasDeclaredValueForKey("onmouseover")) {
                            NormalNotice temp = new NormalNotice();
                            temp.setTitle(trTag.select("td").get(2).selectFirst("a").html());
                            temp.setUrl(connUrl2 + trTag.select("td").get(2).selectFirst("a").attributes().get("href"));
                            temp.setWriter(trTag.select("td").get(3).html());
                            temp.setWriteDay(trTag.select("td").get(4).html());
                            temp.setHits(trTag.select("td").get(5).html());
                            normalNotice.add(temp);
                        }
//							상단 고정 공지사항의 경우
                        else if ((!isUpperNotice)
                                && (!trTag.selectFirst("td").attributes().get("class").equals("line_dot"))
                                && (!trTag.selectFirst("td").attributes().get("class").equals("line_gray02"))) {
                            Element aTag = trTag.select("td").get(1).selectFirst("a");
                            UpperNotice temp = new UpperNotice();
                            temp.setTitle(aTag.html());
                            temp.setUrl(connUrl2 + aTag.attributes().get("href"));
                            m_upperNotice.add(temp);
                        }
                    }
                }
                this.isUpperNotice = true;

            }
        }

//			공지사항의 마지막 페이지 수를 알아내기 위한 작업
        List<Element> elements = doc.getElementsByAttributeValue("alt", "끝페이지");
        element = elements.get(0).parent();
        String maxNum = element.attributes().get("href");
        maxNum = maxNum.replace(replaceUrl, "").trim();
        this.maxPageNum = Integer.parseInt(maxNum);
    }

    public boolean nextPage() throws IOException {
        if (pageNum < maxPageNum) {
            pageNum += 10;
            crawlingNotice();
            return true;
        }
        return false;
    }

    public boolean prevPage() throws IOException {
        if (pageNum > 0) {
            pageNum -= 10;
            crawlingNotice();
            return true;
        }
        return false;
    }

    //    사용자가 선택한 공지사항의 내용을 크롤링 하는 함수
    public void crawlingSelectNotice_r1(String url) throws IOException {
        Element doc = Jsoup.connect(url).userAgent("Chrome/80.0.3987.149").get();
//        공지사항 게시글에 포함된 이미지 크롤링 하는 부분
        Element postTable = doc.getElementsByClass("board_base").get(1);
        List<Element> imgs = postTable.select("tr").get(6).select("img");

        isImgExist = false;
        postImg.clear();

        if (!(imgs.isEmpty())) {
            for (int i = 0; i < imgs.size(); i++) {
                if (!(imgs.get(i).attributes().get("src").contains("http://"))) {
                    String str = connUrl2 + imgs.get(i).attributes().get("src").replace("..", "").trim();
                    this.postImg.add(str);
                    isImgExist = true;
                }
            }
        }

        download.clear();
        if (isImgExist) {
//            첨부파일 크롤링 하는 부분
            Element downloadTable = doc.getElementsByClass("board_base").get(2);
            if (downloadTable != null) {
                List<Element> trTags = downloadTable.select("tr");
                for (int i = 0; i < trTags.size(); i++) {
                    Element a = trTags.get(i).selectFirst("td").selectFirst("a");
                    if (a != null) {
                        String str[] = new String[2];
                        str[0] = a.html();
                        str[1] = a.attributes().get("href");
                        str[1] = connUrl2 + str[1].replace("..", "").trim();
                        download.add(str);
                    }
                }
            }
        }

    }

}
