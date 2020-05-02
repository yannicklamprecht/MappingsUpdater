package com.github.yannicklamprecht.mappingsupdater.downloader.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Latest {

    @SerializedName("release")
    @Expose
    private String release;
    @SerializedName("snapshot")
    @Expose
    private String snapshot;

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    @Override
    public String toString() {
        return "Latest{" +
                "release='" + release + '\'' +
                ", snapshot='" + snapshot + '\'' +
                '}';
    }
}
