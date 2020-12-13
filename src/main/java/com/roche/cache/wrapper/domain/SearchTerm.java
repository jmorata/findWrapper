package com.roche.cache.wrapper.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.io.File;

@Builder
@lombok.Data
@EqualsAndHashCode
@lombok.ToString
public class SearchTerm {

    private static final String CLASS = "##class";

    @Setter(AccessLevel.NONE)
    private File directory;

    @Setter(AccessLevel.NONE)
    private String term;

    public static class SearchTermBuilder {
        public SearchTermBuilder term(String term) {
            if (!term.contains(CLASS)) {
                this.term = CLASS + "(" + term + ").";
            }
            return this;
        }
    }

}
