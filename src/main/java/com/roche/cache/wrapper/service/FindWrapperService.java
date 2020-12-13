package com.roche.cache.wrapper.service;

import com.roche.cache.wrapper.domain.SearchTerm;
import com.roche.cache.wrapper.logger.FindWrapperLogger;

import java.io.File;

public class FindWrapperService extends FindWrapperSearchService {

    private int fileCount = 0;
    private final SearchTerm searchTerm;
    private final FindWrapperSearchService findWrapperSearchService;

    public FindWrapperService(File directory, String term) {
        this.findWrapperSearchService = new FindWrapperSearchService();
        this.searchTerm = SearchTerm.builder()
                .term(term)
                .directory(directory)
                .build();
    }

    public void execute() {
        FindWrapperLogger.printInfo(searchTerm);
        processFileDirectory(searchTerm.getDirectory(), searchTerm.getTerm());
        FindWrapperLogger.printCollection(this.findWrapperSearchService.getCollection());
    }

    private void processFileDirectory(File directory, String term) {
        try {
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    FindWrapperLogger.printDirectory(file, this.fileCount);
                    processFileDirectory(file, term);

                } else if (file.getName().contains(".xml")) {
                    this.findWrapperSearchService.searchTermInFile(file, term);
                    fileCount++;
                }
            }

        } catch (Exception e) {
            FindWrapperLogger.getLocalizedError(e, directory, term);
        }
    }

}
