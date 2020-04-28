package com.github.yannicklamprecht.mappingsupdater;

import com.github.yannicklamprecht.mappingsupdater.downloader.MojangMappings;
import com.github.yannicklamprecht.mappingsupdater.downloader.pojo.ClientMeta;
import com.github.yannicklamprecht.mappingsupdater.downloader.pojo.Downloads;
import com.github.yannicklamprecht.mappingsupdater.downloader.pojo.MappingUrls;
import com.github.yannicklamprecht.mappingsupdater.downloader.pojo.Version;
import com.github.yannicklamprecht.mappingsupdater.proguard.ProguardMappingTree;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        MojangMappings mojangMappings = new MojangMappings();

        Optional<Version> versionOptional = mojangMappings.download()
                .thenApply(clientMeta -> clientMeta.getVersions().stream().
                        filter(version -> version.getId().equals(clientMeta.getLatest().getSnapshot())).findFirst())
                .get();

        // todo find a solution where tasks are chained and when one completes the other starts to run async
        MappingUrls mappingUrls = mojangMappings.loadVersionDataFor(versionOptional.get().getUrl()).get();

        System.out.println(mappingUrls);
        // todo use mappings urls to download mappings

        /* // todo parse freshly downloaded mappings files
        ProguardMappingTree proguardMappingTree = new ProguardMappingTree();

        File source = new File("server.txt");
        File destination = new File("obs.txt");

        if(!source.exists()){
            System.out.println("The Mojang mappings file server.txt doesn't exist!");
            System.out.println("You need to place one here: "+ source.toPath().toString());
        }

        proguardMappingTree.load(source);
        proguardMappingTree.safe(destination);

         */


    }
}
