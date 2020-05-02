package com.github.yannicklamprecht.mappingsupdater.downloader.pojo.mojang;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClientMeta {

    @SerializedName("latest")
    @Expose
    private Latest latest;
    @SerializedName("versions")
    @Expose
    private List<Version> versions = null;

    public Latest getLatest() {
        return latest;
    }

    public void setLatest(Latest latest) {
        this.latest = latest;
    }

    public List<Version> getVersions() {
        return versions;
    }

    public void setVersions(List<Version> versions) {
        this.versions = versions;
    }

    @Override
    public String toString() {
        return "ClientMeta{" +
                "latest=" + latest +
                ", versions=" + versions +
                '}';
    }
}
