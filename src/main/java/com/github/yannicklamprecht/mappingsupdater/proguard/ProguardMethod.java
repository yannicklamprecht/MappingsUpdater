package com.github.yannicklamprecht.mappingsupdater.proguard;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;

import static com.github.yannicklamprecht.mappingsupdater.proguard.ProGuardRegex.METHOD;

public class ProguardMethod {
    private String clearName;
    private String obfName;
    private String[] lineNumbers = new String[0];
    private String returnType;
    private String[] parameters = new String[0];


    private ProguardMethod() {
    }

    public static ProguardMethod parse(String line) {
        Matcher m = METHOD.matcher(line);
        ProguardMethod proGuardMethod = new ProguardMethod();
        if (m.matches()) {
            proGuardMethod.lineNumbers = m.group("linenumbers").replace(":", " ").trim().split(" ");
            proGuardMethod.returnType = m.group("returntype");
            proGuardMethod.clearName = m.group("methodname");
            if (m.group("parametertypes") != null) {
                proGuardMethod.parameters = m.group("parametertypes").split(",");
            }
            proGuardMethod.obfName = m.group("obfuscatedname");
        }

        return proGuardMethod;
    }

    @Override
    public String toString() {
        return "ProGuardMethod{" +
                "clearName='" + clearName + '\'' +
                ", obfName='" + obfName + '\'' +
                ", lineNumbers=" + Arrays.toString(lineNumbers) +
                ", returnType='" + returnType + '\'' +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }

    public static boolean matches(String line) {
        return METHOD.matcher(line).matches();
    }

    public void asProGuardMappings(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.newLine();
        bufferedWriter.append("    "+((lineNumbers.length>0 && !lineNumbers[0].isEmpty()) ? (String.join(":", lineNumbers) + ":"): "") + returnType + " " + clearName + "(" + String.join(",", parameters) + ") -> " + obfName);
    }
}
