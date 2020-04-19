package com.github.yannicklamprecht.mappingsupdater.proguard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ProguardMappingTree {

    private AtomicReference<String> header = new AtomicReference<>();
    private List<ProguardClass> classes = new ArrayList<>();

    public void load(File proguardMappingsSource) throws IOException {
        List<String> lines = Files.readAllLines(proguardMappingsSource.toPath(), StandardCharsets.UTF_8);
        final ProguardClass[] proguardClass = {null};
        lines.forEach(s -> {
            if(s.startsWith("#")){
                header.set(s);
            } else if (ProGuardRegex.CLASS.matcher(s).matches()) {
                if (proguardClass[0] != null) {
                    classes.add(proguardClass[0]);
                }
                proguardClass[0] = ProguardClass.parse(s);
            } else if (ProGuardRegex.FIELD.matcher(s).matches()) {
                ProguardField proguardField = ProguardField.parse(s);
                proguardClass[0].addField(proguardField);
            } else if (ProGuardRegex.METHOD.matcher(s).matches()) {
                ProguardMethod proGuardMethod = ProguardMethod.parse(s);
                proguardClass[0].addMethode(proGuardMethod);
            }
        });
        classes.add(proguardClass[0]);
    }

    public void safe(File proguardMappingsDestination) throws IOException {
        proguardMappingsDestination.createNewFile();
        BufferedWriter writer = Files.newBufferedWriter(proguardMappingsDestination.toPath(), StandardCharsets.UTF_8);

        writer.append(header.get());
        classes.forEach(proguardClass1 -> {
            try {
                proguardClass1.asProGuardMappings(writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.close();


    }
}
