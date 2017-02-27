/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treeNormalizer.simpleNormalization;

import java.util.List;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import treeNormalizer.normalization;
import treeNormalizer.rule;
import treeNormalizer.transformation;

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class simpleNormalization extends normalization {

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
     * prüft die Äquivalenz von Einsendung und Musterlösung
     *
     * @return true = sind gleich, false = nicht gleich
     */
    @Override
    public boolean equivalent() {
        Document solutionDocument = getSolution().getTree();
        Document submissionDocument = getSubmission().getTree();
        if (solutionDocument == null && submissionDocument == null) {
            return true;
        }
        if (solutionDocument == null && submissionDocument != null) {
            return false;
        }
        if (solutionDocument != null && submissionDocument == null) {
            return false;
        }

        return elementEquivalence(submissionDocument.getRootElement(), solutionDocument.getRootElement());
    }

    /**
     * prüft, ob zwei Elemente gleich sind
     *
     * @param A das erste Element
     * @param B das zweite Element
     * @return true = sind gleich, false = nicht gleich
     */
    protected boolean elementEquivalence(Element A, Element B) {
        // wenn eines der Elemente null ist, dann können sie nicht gleich sein
        if (A == null && B != null) {
            return false;
        }
        if (A != null && B == null) {
            return false;
        }

        List<Element> childsA = A.getChildren();
        List<Element> childsB = B.getChildren();

        // sie müssen die gleiche Anzahl an Kindern besitzen
        if (childsA.size() != childsB.size()) {
            return false;
        }
        // der Knotenbezeichner muss gleich sein
        if (A.getName() != B.getName()) {
            return false;
        }
        // label und class müssen gleich sein
        if (A.getAttributeValue("label") != B.getAttributeValue("label")) {
            return false;
        }
        if (A.getAttributeValue("class") != B.getAttributeValue("class")) {
            return false;
        }

        // jetzt werden die Kinder rekursiv gegeneinander geprüft
        for (int i = 0; i < childsA.size(); i++) {
            if (!elementEquivalence(childsA.get(i), childsB.get(i))) {
                return false;
            }
        }

        // wenn die Normalisierung noch weitere Attribute setzt, dann
        // ist uns das egal
        return true;
    }

    @Override
    public Document getContext() {
        return this.context;
    }

    @Override
    public void setContext(Document context) {
        this.context = context;
    }

    @Override
    public transformation getSolution() {
        return solution;
    }

    @Override
    public void setSolution(Document solution) {
        this.solution = new transformation(solution);
    }

    @Override
    public void setSolution(transformation solution) {
        this.solution = solution;
    }

    @Override
    public transformation getSubmission() {
        return this.submission;
    }

    @Override
    public void setSubmission(Document submission) {
        this.submission = new transformation(submission);
    }

    @Override
    public void setSubmission(transformation submission) {
        this.submission = submission;
    }

    /**
     * führt die Normalisierung der Einsendung und der Musterlösung durch
     */
    @Override
    public void perform() {
        // setzt den Kontext für die Musterlösung und die Einsendung
        submission.setContext(context);
        solution.setContext(context);

        // jetzt werden alle Regeln nacheinander angewendet
        for (rule r : this.rules) {
            boolean a = r.perform(solution);
            boolean a2 = r.perform(submission);

            // nur wenn eine der beiden Regeln ihr Dokument verändert hat,
            // dann denken wir weiter darüber nach
            if (a || a2) {

                // wenn nach dieser Regelanwendung bereits beide äquivalent
                // sind, dann können wir den Vorgang beenden
                if (this.equivalent()) {
                    return;
                }
            }
        }
    }

}
