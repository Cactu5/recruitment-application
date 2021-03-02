package se.kth.iv1201.group4.recruitment.domain;

import java.io.Serializable;

/**
 * A class that represents the composite primary key of
 * the LocalCompetence entity. JPA requires that the key fields
 * are not objects of custom classes, which is why they are represented
 * as longs here, i.e. their primary key datatype.
 */
public class LocalCompetenceId implements Serializable {
    private static final long serialVersionUID = 1L;

    private long language;

    private long competence;

    public LocalCompetenceId() {
    }

    public long getLanguage() {
        return language;
    }

    public long getCompetence() {
        return competence;
    }

    public void setLanguage(long l){
        language = l;
    }

    public void setCompetence(long c) {
        competence = c;
    }
}
