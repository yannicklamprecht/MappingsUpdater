package com.github.yannicklamprecht.mappingsupdater.downloader.pojo.mojang;

public class MappingUrls {
    private String id;
    private Downloads downloads;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Downloads getDownloads() {
        return downloads;
    }

    public void setDownloads(Downloads downloads) {
        this.downloads = downloads;
    }


    @Override
    public String toString() {
        return "MappingUrls{" +
                "id='" + id + '\'' +
                ", downloads=" + downloads +
                '}';
    }
}
