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

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class simpleNormalization extends normalization {

    /**
     * prüft die Äquivalenz von Einsendung und Musterlösung
     *
     * @return true = sind gleich, false = nicht gleich
     */
    @Override
    public boolean equivalent() {
        Document solutionDocument = solution.getTree();
        Document submissionDocument = submission.getTree();
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

        // sie müssen die gleiche Anzahl an Kindern besitzen
        if (A.getChildren().size() != B.getChildren().size()) {
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

        // wenn die Normalisierung noch weitere Attribute setzt, dann
        // ist uns das egal
        return true;
    }

    /**
     * führt die Normalisierung der Einsendung und der Musterlösung durch
     */
    @Override
    public void perform() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
