package com.github.yannicklamprecht.mappingsupdater.downloader.pojo;

import com.google.gson.annotations.SerializedName;

public class Downloads {

    @SerializedName("client")
    private Download client;
    @SerializedName("client_mappings")
    private Download clientMappings;
    @SerializedName("server")
    private Download server;
    @SerializedName("server_mappings")
    private Download serverMappings;

    public Download getClient() {
        return client;
    }

    public void setClient(Download client) {
        this.client = client;
    }

    public Download getClientMappings() {
        return clientMappings;
    }

    public void setClientMappings(Download clientMappings) {
        this.clientMappings = clientMappings;
    }

    public Download getServer() {
        return server;
    }

    public void setServer(Download server) {
        this.server = server;
    }

    public Download getServerMappings() {
        return serverMappings;
    }

    public void setServerMappings(Download serverMappings) {
        this.serverMappings = serverMappings;
    }

    @Override
    public String toString() {
        return "Downloads{" +
                "client=" + client +
                ", clientMappings=" + clientMappings +
                ", server=" + server +
                ", serverMappings=" + serverMappings +
                '}';
    }
}
