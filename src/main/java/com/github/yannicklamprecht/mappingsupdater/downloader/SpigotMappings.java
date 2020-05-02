package com.github.yannicklamprecht.mappingsupdater.downloader;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SpigotMappings {

    private final File file = new File("buildData");

    private final Git git;

    public SpigotMappings() throws IOException, GitAPIException {
        if (file.exists()) {
            this.git = Git.open(file);
        } else {
            this.git = Git.cloneRepository()
                    .setURI("https://hub.spigotmc.org/stash/scm/spigot/builddata.git")
                    .setDirectory(file)
                    .setCloneAllBranches(true)
                    .call();
        }
    }

    public Git unsafe() {
        return git;
    }

    public boolean deleteLocalData() throws IOException {
        return Files.walk(file.toPath())
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .allMatch(File::delete);
    }

    public void resetToMaster() throws IOException, GitAPIException {
        ObjectId masterCommit = git.getRepository().resolve("origin/master");
        git.checkout().setName(masterCommit.getName()).call();
    }

    public void printMappings(String version) throws GitAPIException {
        Iterable<RevCommit> commits = git.log().call();
        Stream<RevCommit> revCommitStream = StreamSupport.stream(commits.spliterator(), false);
        Optional<RevCommit> optionalRevCommit = revCommitStream.filter(revCommit -> revCommit.getShortMessage().contains(version)).findFirst();
        optionalRevCommit.ifPresent(revCommit -> {
            try {
                git.checkout().setName(revCommit.getId().getName()).call();

                System.out.println("-------------Class Mappings----------");
                File classFile = new File("buildData/mappings/bukkit-" + version + "-cl.csrg");
                Files.readAllLines(classFile.toPath()).forEach(System.out::println);
                System.out.println("-------------Member Mappings----------");
                File memberFile = new File("buildData/mappings/bukkit-" + version + "-members.csrg");
                Files.readAllLines(memberFile.toPath()).forEach(System.out::println);

            } catch (IOException | GitAPIException e) {
                e.printStackTrace();
            }
        });

    }

}
