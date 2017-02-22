/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treeNormalizer;

import org.jdom.Document;

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public abstract class normalization {

    /*
     * die Einsendung eines Studenten
     */
    private transformation submission;

    /*
     * die Musterlösung
     */
    private transformation solution;

    /*
     * der Kontext
     */
    private Document context;

    /**
     * @return the submission
     */
    public transformation getSubmission() {
        return submission;
    }

    /**
     * @param submission the submission to set
     */
    public void setSubmission(transformation submission) {
        this.submission = submission;
    }

    /**
     * @param submission the submission to set
     */
    public void setSubmission(Document submission) {
        this.submission = new transformation(submission);
    }

    /**
     * @return the solution
     */
    public transformation getSolution() {
        return solution;
    }

    /**
     * @param solution the solution to set
     */
    public void setSolution(transformation solution) {
        this.solution = solution;
    }

    /**
     * @param solution the solution to set
     */
    public void setSolution(Document solution) {
        this.solution = new transformation(solution);
    }

    /**
     * @return the context
     */
    public Document getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(Document context) {
        this.context = context;
    }

    public abstract void perform();

}
