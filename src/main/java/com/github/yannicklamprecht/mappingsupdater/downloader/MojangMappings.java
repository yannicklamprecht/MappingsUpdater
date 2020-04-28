package com.github.yannicklamprecht.mappingsupdater.downloader;

import com.github.yannicklamprecht.mappingsupdater.downloader.pojo.ClientMeta;
import com.github.yannicklamprecht.mappingsupdater.downloader.pojo.Downloads;
import com.github.yannicklamprecht.mappingsupdater.downloader.pojo.MappingUrls;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class MojangMappings {
    private final String mojangClientUrl = "https://launchermeta.mojang.com/mc/game/version_manifest.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    public MojangMappings() {
    }


    public CompletableFuture<ClientMeta> download() {
        return CompletableFuture.supplyAsync(() -> {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response;
            try {
                response = httpClient.send(HttpRequest.newBuilder()
                        .GET().uri(URI.create(mojangClientUrl)).build(), HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    String responseBody = response.body();
                    return gson.fromJson(responseBody, ClientMeta.class);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public CompletableFuture<MappingUrls> loadVersionDataFor(String url) {
        return CompletableFuture.supplyAsync(() -> {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response;

            try {
                response = httpClient.send(HttpRequest.newBuilder()
                        .GET().uri(URI.create(url)).build(), HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    String responseBody = response.body();
                    return gson.fromJson(responseBody, MappingUrls.class);
                }
            } catch (IOException | InterruptedException e) {
                CompletableFuture.failedFuture(e);
            }
            return null;
        });
    }


}
