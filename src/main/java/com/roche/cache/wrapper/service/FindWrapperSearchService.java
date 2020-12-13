package com.roche.cache.wrapper.service;

import com.roche.cache.wrapper.domain.Method;
import com.roche.cache.wrapper.handler.FindWrapperHandler;
import com.roche.cache.wrapper.logger.FindWrapperLogger;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import static com.roche.cache.wrapper.handler.FindWrapperHandler.retrieveMethodFromStatement;

public class FindWrapperSearchService {
    private Set<Method> methodCollection = new TreeSet<>();

    public void searchTermInFile(File file, String term) throws Exception {
        Path path = FileSystems.getDefault().getPath(file.getPath());

        Files.lines(path)
                .filter(line -> line.toUpperCase().contains(term.toUpperCase()))
                .forEach(line -> {
                    try {
                        addToCollection(file, line, term);
                    } catch (Exception e) {
                        FindWrapperLogger.getLocalizedError(e, file, term, line);
                    }
                });
    }

    public Set<Method> getCollection() {
        return Collections.unmodifiableSet(this.methodCollection);
    }

    public void addToCollection(File file, String line, String term) throws Exception {
        try {
            line = FindWrapperHandler.filterEscapeChars(line);
            if (line.contains("=")) {
                String statement = line.split("=")[1];
                String method = retrieveMethodFromStatement(statement, term);
                if (method != null) {
                    addMethod(file, line, method);

                    if (FindWrapperHandler.termContainsClass(term)) {
                        statement = line.split("=")[0];
                        String argument = FindWrapperHandler.retrieveArgumentFromStatement(statement, term);
                        if (argument != null) {
                            searchTermInFile(file, argument + ".");
                        }
                    }
                }

            } else {
                String method = retrieveMethodFromStatement(line, term);
                if (method != null) {
                    addMethod(file, line, method);
                }
            }
        } catch (Exception e) {
            FindWrapperLogger.getLocalizedError(e, term, line);
        }
    }

    private void addMethod(File file, String line, String method) {
        this.methodCollection.add(Method.builder()
                .fileName(file.getPath())
                .line(line)
                .method(method)
                .build());
    }

}
