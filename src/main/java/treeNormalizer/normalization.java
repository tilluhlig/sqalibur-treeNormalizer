/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treeNormalizer;

import java.util.ArrayList;
import java.util.List;
import org.jdom.Document;

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public abstract class normalization {

    protected List<rule> rules = new ArrayList<rule>();

    /**
     * @return the submission
     */
    public abstract transformation getSubmission();

    /**
     * @param submission the submission to set
     */
    public abstract void setSubmission(transformation submission);

    /**
     * @param submission the submission to set
     */
    public abstract void setSubmission(Document submission);

    /**
     * @return the solution
     */
    public abstract transformation getSolution();

    /**
     * @param solution the solution to set
     */
    public abstract void setSolution(transformation solution);

    /**
     * @param solution the solution to set
     */
    public abstract void setSolution(Document solution);

    /**
     * @return the context
     */
    public abstract Document getContext();

    /**
     * @param context the context to set
     */
    public abstract void setContext(Document context);

    public abstract void perform();

    public abstract boolean equivalent();

    /**
     * @return the rules
     */
    public List<rule> getRules() {
        return rules;
    }

    /**
     * @param rules the rules to set
     */
    public void setRules(List<rule> rules) {
        this.rules = rules;
    }

    public void addRule(rule newRule) {
        this.rules.add(newRule);
    }

    public void resetRules() {
        this.rules.clear();
    }

}