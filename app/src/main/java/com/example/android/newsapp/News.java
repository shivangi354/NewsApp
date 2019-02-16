package com.example.android.newsapp;

/**
 * Created by Shivangi on 13-11-2018.
 */

import java.util.ArrayList;

class News {
    private String title;
    private String section;
    private String webPublicationDateAndTime;
    private String webUrl;
    private ArrayList<String> authors;

    News(String title, String section, String webPublicationDateAndTime, String webUrl, ArrayList<String> authors) {
        this.title = title;
        this.section = section;
        this.webPublicationDateAndTime = webPublicationDateAndTime;
        this.webUrl = webUrl;
        this.authors = authors;
    }

    String getTitle() {
        return title;
    }

    String getSection() {
        return section;
    }

    String getWebPublicationDateAndTime() {
        return webPublicationDateAndTime;
    }

    String getWebUrl() {
        return webUrl;
    }

    ArrayList<String> getAuthors() {
        return authors;
    }
}