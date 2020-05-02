package com.github.yannicklamprecht.mappingsupdater.proguard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ProguardMappingTree {

    private final AtomicReference<String> header = new AtomicReference<>();
    private final List<ProguardClass> classes = new ArrayList<>();

    public void from(List<String> lines) {
        final ProguardClass[] proguardClass = {null};
        lines.forEach(s -> {
            if (s.startsWith("#")) {
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

    public void to(List<String> lines) {
        lines.add(header.get());
        classes.forEach(proguardClass1 -> {
            proguardClass1.asProGuardMappings(lines);
        });
    }
}
