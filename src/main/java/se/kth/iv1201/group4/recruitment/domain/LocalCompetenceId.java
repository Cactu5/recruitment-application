package se.kth.iv1201.group4.recruitment.domain;

import java.io.Serializable;

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
