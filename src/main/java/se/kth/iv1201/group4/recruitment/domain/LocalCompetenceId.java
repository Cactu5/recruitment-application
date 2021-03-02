package se.kth.iv1201.group4.recruitment.domain;

import java.io.Serializable;

public class LocalCompetenceId implements Serializable {
    private static final long serialVersionUID = 1L;

    private Language language;

    private Competence competence;

    public LocalCompetenceId() {
    }

    public Language getLanguage() {
        return language;
    }

    public Competence getCompetence() {
        return competence;
    }

    public void setLanguage(Language l){
        language = l;
    }

    public void setCompetence(Competence c) {
        competence = c;
    }
}
