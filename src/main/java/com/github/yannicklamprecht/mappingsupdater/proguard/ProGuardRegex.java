package com.github.yannicklamprecht.mappingsupdater.proguard;

import java.util.regex.Pattern;

public class ProGuardRegex {
    public static final Pattern CLASS = Pattern.compile("^(?<classname>[\\w.$-]+)\\s->\\s(?<classnameobf>[\\w.$]+):", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    public static final Pattern FIELD = Pattern.compile("^\\s*(?<type>[\\w.$]+(?:\\[])*)\\s(?<name>[\\w_$\\d]+)\\s->\\s(?<obfuscatedname>[\\w+$_]+)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    public static final Pattern METHOD = Pattern.compile("^[\\s]+(?<linenumbers>(?:\\d{1,4}:){0,2})(?<returntype>[\\w.$\\[\\]]+)\\s(?<methodname><?([\\w$\\d])+>?)\\((?<parametertypes>[\\w+.,$\\[\\]]+)?\\)\\s->\\s(?<obfuscatedname><?[\\w_]+>?)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
}
