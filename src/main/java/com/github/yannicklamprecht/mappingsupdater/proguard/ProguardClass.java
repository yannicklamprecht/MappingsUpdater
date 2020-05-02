package com.github.yannicklamprecht.mappingsupdater.proguard;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static com.github.yannicklamprecht.mappingsupdater.proguard.ProGuardRegex.CLASS;

public class ProguardClass {
    private String clearName;
    private String obfName;
    private final List<ProguardField> fields = new ArrayList<>();
    private final List<ProguardMethod> methods = new ArrayList<>();


    private ProguardClass() {
    }

    public static ProguardClass parse(String line) {
        Matcher m = CLASS.matcher(line);
        ProguardClass proGuardMethod = new ProguardClass();
        if (m.matches()) {
            proGuardMethod.clearName = m.group("classname");
            proGuardMethod.obfName = m.group("classnameobf");
        }
        return proGuardMethod;
    }

    public void addMethode(ProguardMethod method) {
        this.methods.add(method);
    }

    public void addField(ProguardField proguardField) {
        this.fields.add(proguardField);
    }

    public static boolean matches(String line) {
        return CLASS.matcher(line).matches();
    }

    @Override
    public String toString() {
        return "ProguardClass{" +
                "clearName='" + clearName + '\'' +
                ", obfName='" + obfName + '\'' +
                ", fields=" + fields +
                ", methods=" + methods +
                '}';
    }

    public void asProGuardMappings(List<String> lines) {
        lines.add(clearName + " -> " + obfName + ":");

        for (ProguardField field : fields) {
            field.asProGuardMappings(lines);
        }
        for (ProguardMethod method : methods) {
            method.asProGuardMappings(lines);
        }
    }
}
