package com.kimbsu.hstree.webCrawling;

import java.io.Serializable;

public class UpperNotice implements Serializable {
    private String title;
    private String url;

    public UpperNotice() {
        title = "";
        url = "";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
