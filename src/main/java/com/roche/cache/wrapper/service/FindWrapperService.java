package com.roche.cache.wrapper.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.TreeSet;

public class FindWrapperService {
    private final File directory;
    private final String searchTerm;

    private Set<String> searchTermCollection = new TreeSet<>();
    private int fileCount = 0;

    public FindWrapperService(File directory, String searchTerm) {
        this.directory = directory;
        this.searchTerm = searchTerm;
    }

    public void execute() {
        printConsoleInfo();
        processDirectory(this.directory, parseTerm(this.searchTerm));
        printConsoleCollection();
    }

    private void printConsoleInfo() {
        System.out.println("Directory: \"" + directory + "\"");
        System.out.println("Search Term: \"" + searchTerm + "\"\n");
    }

    private String parseTerm(String searchTerm) {
        if (!searchTerm.contains("##class")) {
            return "##class(" + searchTerm + ").";
        }

        return searchTerm;
    }

    private void printConsoleCollection() {
        System.out.println("\n\nMethods found: " + this.searchTermCollection.size());
        System.out.println("=================");
        this.searchTermCollection.stream().forEach(entry -> System.out.println(entry));
    }

    private void processDirectory(File directory, String searchTerm) {
        try {
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    printConsoleDirectory(file);
                    processDirectory(file, parseTerm(searchTerm));
                } else {
                    searchString(file, searchTerm);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printConsoleDirectory(File file) throws IOException {
        System.out.print("(" + fileCount + " XML files) | searching in " + file.getCanonicalPath() + "\r");
    }

    private void searchString(File file, String searchString) throws IOException {
        if (file.getName().contains(".xml")) {
            Path path = FileSystems.getDefault().getPath(file.getPath());
            Files.lines(path).filter(line -> line.toUpperCase().contains(searchString.toUpperCase())).forEach(line -> {
                try {
                    addToCollection(file, line, searchString);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            fileCount++;
        }
    }

    private void addToCollection(File file, String line, String searchString) throws IOException {
        addCollection(line, searchString);

        if (containsEqualAtRight(line, searchString)) {
            searchString = line.split("=")[0];
            String[] splitString = searchString.split(" ");
            searchString = " " + splitString[splitString.length - 1] + ".";
            searchString(file, searchString);
        }
    }

    private boolean containsEqualAtRight(String line, String searchString) {
        return !line.split("=")[0].contains(searchString);
    }

    private void addCollection(String line, String searchString) {
        try {
            line = line.substring(line.indexOf(searchString));
            line = line.replace(searchString, "");
            line = line.substring(0, line.indexOf("("));

            this.searchTermCollection.add(line);

        } catch (StringIndexOutOfBoundsException e) {
            // none
        }
    }

}
