package com.github.yannicklamprecht.mappingsupdater;

import com.github.yannicklamprecht.mappingsupdater.downloader.MojangMappings;
import com.github.yannicklamprecht.mappingsupdater.downloader.SpigotMappings;
import com.github.yannicklamprecht.mappingsupdater.downloader.pojo.mojang.MappingUrls;
import com.github.yannicklamprecht.mappingsupdater.downloader.pojo.mojang.Version;
import com.github.yannicklamprecht.mappingsupdater.proguard.ProguardMappingTree;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException, GitAPIException, ExecutionException, InterruptedException {
        new Main();
    }

    public Main() throws IOException, GitAPIException, ExecutionException, InterruptedException {

        SpigotMappings spigotMappings = new SpigotMappings();
        spigotMappings.resetToMaster();

        String mcVersion = "1.15.2";

        spigotMappings.printMappings(mcVersion);

        MojangMappings mojangMappings = new MojangMappings();

        Optional<Version> versionOptional = mojangMappings.download()
                .thenApply(clientMeta -> clientMeta.getVersions().stream().
                        filter(version -> version.getId().equals(clientMeta.getLatest().getSnapshot())).findFirst())
                .get();

        MappingUrls mappingUrls = mojangMappings
                .loadVersionDataFor(versionOptional.get().getUrl()).get();


        String mappingsString = mojangMappings.fetch(mappingUrls).get();
        List<String> lines = mappingsString.lines().collect(Collectors.toList());

        ProguardMappingTree proguardMappingTree = new ProguardMappingTree();
        proguardMappingTree.from(lines);

        File destination = new File("obs.txt");
        this.save(proguardMappingTree, destination);

    }

    public void save(ProguardMappingTree proguardMappingTree, File destination) throws IOException {
        List<String> safelines = new ArrayList<>();
        proguardMappingTree.to(safelines);

        try (BufferedWriter writer = Files.newBufferedWriter(destination.toPath(), StandardCharsets.UTF_8, StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
            safelines.forEach(line -> {
                try {
                    writer.write(line);
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
