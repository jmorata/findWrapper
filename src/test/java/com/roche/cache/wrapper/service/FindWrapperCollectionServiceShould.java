package com.roche.cache.wrapper.service;

import com.roche.cache.wrapper.domain.Method;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class FindWrapperCollectionServiceShould {

    private FindWrapperSearchService findWrapperSearchService;
    private static final File file = new File("");

    @Test
    @Parameters({
            "Return (objSQL.ActivationDate = \"\"), objSQL, ActivationDate",
            "\t#Dim objSQL As %SQL.StatementResult = ##class(%SQL.Statement).%ExecDirect(\\, strSQL\\, objBracketingRule.ID), ##class(%SQL.Statement), %ExecDirect"
    })

    public void addToCollection(String line, String term, String foundTerm) throws Exception {
        findWrapperSearchService = new FindWrapperSearchService();
        findWrapperSearchService.addToCollection(file, line, term + ".");
        assertThat(foundTerm.equals(getFirstCollectionEntry()));
    }

    private Optional<Method> getFirstCollectionEntry() {
        return findWrapperSearchService.getCollection().stream().findFirst();
    }

}