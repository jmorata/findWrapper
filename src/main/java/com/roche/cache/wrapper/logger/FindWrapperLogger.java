package com.roche.cache.wrapper.logger;

import com.roche.cache.wrapper.domain.Method;
import com.roche.cache.wrapper.domain.SearchTerm;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class FindWrapperLogger {
    //private static final Logger LOGGER = Logger.getLogger(FindWrapperLogger.class.getName());

    public static void printInfo(SearchTerm searchTerm) {
        System.out.println("Directory: \"" + searchTerm.getDirectory().getPath() + "\"");
        System.out.println("Search Term: \"" + searchTerm.getTerm() + "\"\n");
    }

    public static void printCollection(Set<Method> searchTermCollection) {
        System.out.println("\nMethods found: " + searchTermCollection.size());
        System.out.println("=================");
        searchTermCollection.stream().forEach(entry -> System.out.println(entry));
    }

    public static void printDirectory(File file, int fileCount) throws IOException {
        System.out.print("(" + fileCount + " XML files) | searching in " + file.getPath() + "\r");
    }

    public static void getLocalizedError(Exception e, File file, String term, String line) {
        System.out.println("\nError: " + e.getLocalizedMessage()
                + "\n" + file.getPath() + "\n" + line + "\n\"" + term + "\"");
    }

    public static void getLocalizedError(Exception e, File file, String term) {
        System.out.println("\nError: " + e.getLocalizedMessage()
                + "\n" + file.getPath() + "\n\"" + term + "\"");
    }

    public static void getLocalizedError(Exception e, String term, String line) {
        System.out.println("\nError: " + e.getLocalizedMessage()
                + "\n" + line + "\n\"" + term + "\"");
    }

}
