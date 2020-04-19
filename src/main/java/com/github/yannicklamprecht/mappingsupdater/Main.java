package com.github.yannicklamprecht.mappingsupdater;

import com.github.yannicklamprecht.mappingsupdater.proguard.ProguardMappingTree;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        ProguardMappingTree proguardMappingTree = new ProguardMappingTree();

        File source = new File("server.txt");
        File destination = new File("obs.txt");

        if(!source.exists()){
            System.out.println("The Mojang mappings file server.txt doesn't exist!");
            System.out.println("You need to place one here: "+ source.toPath().toString());
        }

        proguardMappingTree.load(source);
        proguardMappingTree.safe(destination);


    }
}
