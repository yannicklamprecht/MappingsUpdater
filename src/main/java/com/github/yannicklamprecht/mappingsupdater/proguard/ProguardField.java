package com.github.yannicklamprecht.mappingsupdater.proguard;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.regex.Matcher;

import static com.github.yannicklamprecht.mappingsupdater.proguard.ProGuardRegex.FIELD;

public class ProguardField {

    private String clearName;
    private String obfName;
    private String type;



    private ProguardField() {
    }

    public static ProguardField parse(String line) {
        Matcher m = FIELD.matcher(line);
        ProguardField proGuardMethod = new ProguardField();
        if (m.matches()) {
            proGuardMethod.clearName = m.group("name");
            proGuardMethod.type = m.group("type");
            proGuardMethod.obfName = m.group("obfuscatedname");
        }

        return proGuardMethod;
    }

    @Override
    public String toString() {
        return "ProguardField{" +
                "clearName='" + clearName + '\'' +
                ", obfName='" + obfName + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public static boolean matches(String line) {
        return FIELD.matcher(line).matches();
    }

    public void asProGuardMappings(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.newLine();
        bufferedWriter.append("    "+ type + " " + clearName + " -> " + obfName);
    }
}
