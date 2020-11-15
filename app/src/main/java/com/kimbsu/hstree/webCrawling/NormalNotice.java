package com.kimbsu.hstree.webCrawling;

import java.io.Serializable;

public class NormalNotice implements Serializable {
    private String title;       // 제목
    private String url;         // 하이퍼링크
    private String writer;      // 작성자
    private String writeDay;    // 작성일
    private String hits;        // 조회수

    public NormalNotice() {
        title = "";
        url = "";
        writer = "";
        writeDay = "";
        hits = "";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setWriteDay(String writeDay) {
        this.writeDay = writeDay;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getWriter() {
        return writer;
    }

    public String getWriteDay() {
        return writeDay;
    }

    public String getHits() {
        return hits;
    }
}
