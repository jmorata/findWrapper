package com.roche.cache.wrapper.handler;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class FindWrapperHandler {

    public static boolean termContainsClass(String term) {
        return term.toUpperCase().startsWith("##CLASS");
    }

    public static String filterEscapeChars(String line) {
        return line.replace("\t", "");
    }

    public static String retrieveMethodFromStatement(String statement, String term) {
        if (statement.contains(term)) {
            String method = statement.substring(statement.indexOf(term));
            method = method.replace(term, "");
            method = getOnlyMethodName(method);

            if (method.contains("(")) {
                method = method.split("\\(")[0];
                if (isValidLine(method)) {
                    return method;
                }
            }
        }

        return null;
    }

    private static String getOnlyMethodName(String method) {
        method = method.split(" ")[0];
        method = method.split("_")[0];
        method = method.split("\\)")[0];
        method = method.split("\\.")[0];
        method = method.split(",")[0];

        return method;
    }

    private static boolean isValidLine(String line) {
        return Pattern.compile("^[A-Z%]").matcher(line).find();
    }

    public static String retrieveArgumentFromStatement(String statement, String term) {
        if (isVariableDefiniton(statement)) {
            String[] splitString = splitStringWithoutSpaces(statement);

            int pos = indexOf(splitString, "AS");
            if (pos > 0 && !isSameClass(splitString[pos + 1], term)) {
                return null;
            }
            for (String argument : splitString) {
                if (!isVariableDefiniton(argument) && !argument.isEmpty()) {
                    return argument;
                }
            }
        }

        return null;
    }

    private static String[] splitStringWithoutSpaces(String statement) {
        return Arrays.stream(statement.split(" ")).filter(s -> !s.isEmpty()).toArray(String[]::new);
    }

    private static boolean isSameClass(String className, String term) {
        term = term.split("\\(")[1];
        term = term.split("\\)")[0];
        return className.equals(term);
    }

    private static boolean isVariableDefiniton(String line) {
        return line.toUpperCase().startsWith("#DIM") || line.toUpperCase().startsWith("SET");
    }

    private static int indexOf(String[] split, String value) {
        return IntStream.range(0, split.length).filter(i -> split[i].toUpperCase().equalsIgnoreCase(value))
                .findFirst().orElse(-1);
    }

}
