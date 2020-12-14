package com.roche.cache.wrapper.service;

import com.roche.cache.wrapper.domain.Method;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.junit.Assert.assertTrue;

@RunWith(JUnitParamsRunner.class)
public class FindWrapperCollectionServiceShould {

    private FindWrapperSearchService findWrapperSearchService;
    private static final File file = new File("");

    @Test
    @Parameters({
            "Return (objSQL.ActivationDate = \"\"), objSQL, ",
            "\t#Dim objSQL As %SQL.StatementResult = ##class(%SQL.Statement).%ExecDirect(\\, strSQL\\, objBracketingRule.ID), ##class(%SQL.Statement), %ExecDirect",
            "Return ##class(%File).GetDirectoryPiece(path\\, 1), ##class(%File), GetDirectoryPiece",
            "Quit $Extract(pstrPatientID1\\, 0\\, intInitialPos - 1)_\"@\"_$Extract(pstrPatientID1\\, objSQL.CharactersNumber + intInitialPos\\, $Length(pstrPatientID1)), objSQL, ",
            "##class(sp.spMisc).spConvertToRegional(ssrSQL.ValueResultDate\\,\"date\"\\,pstrRegionalSettingsLIS)_$Char(172)_, ssrSQL, "
    })
    public void addToCollection(String line, String term, String foundTerm) throws Exception {
        findWrapperSearchService = new FindWrapperSearchService();
        findWrapperSearchService.addToCollection(file, line, term + ".");

        assertTrue(findWrapperSearchService.getCollection().size()<=1);
        assertTrue(foundTerm.equals(getFirstCollectionEntry().getMethod()));
    }

    private Method getFirstCollectionEntry() {
        return findWrapperSearchService.getCollection().stream().findFirst().orElse(Method.builder().method("").build());
    }

}