/*
 * Copyright (C) 2017 Till Uhlig <till.uhlig@student.uni-halle.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package treeNormalizer.utils;

import java.util.LinkedList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;

/**
 * diese Klasse stellt Hilfsfunktionen zum Umgang mit Bäumen bereit
 *
 * @author Till
 */
public class treeUtilities {

    /**
     * Liefert die Blätter eines Baums
     *
     * @param tree ein Baum als Document
     * @return eine Liste der Blätter
     */
    public static List<Element> getLeafs(Document tree) {
        if (!tree.hasRootElement()) {
            return new LinkedList<>();
        }
        List<Element> leafs = new LinkedList<>();
        leafs.add(tree.getRootElement());
        for (int i = 0; i < leafs.size(); i++) {
            List<Element> childs = leafs.get(i).getChildren();
            if (childs != null && childs.size() > 0) {
                leafs.remove(i);
                leafs.addAll(childs);
                i--;
            }
        }
        return leafs;
    }

    /**
     * liefert den Hash eines Baums
     *
     * @param tree der Baum
     * @return der numerische Hash
     */
    public static int getDocumentHash(Document tree) {
        return getDocumentHash(tree, false);
    }

    /**
     * liefert den Hash eines Baums
     *
     * @param tree         der Baum
     * @param useSignature wenn das Attribut signature bereits gesetzt ist, dann
     *                     führt er keine Neuberechnung aus
     * @return der numerische Hash
     */
    public static int getDocumentHash(Document tree, boolean useSignature) {
        return getDocumentHashAsString(tree, useSignature).hashCode();
    }

    /**
     * liefert den Hash eines Baums
     *
     * @param tree der Baum
     * @return der Hash als Text
     */
    public static String getDocumentHashAsString(Document tree) {
        return getDocumentHashAsString(tree, false);
    }

    /**
     * liefert den Hash eines Baums
     *
     * @param tree         der Baum
     * @param useSignature wenn das Attribut signature bereits gesetzt ist, dann
     *                     führt er keine Neuberechnung aus
     * @return der Hash noch als Text
     */
    public static String getDocumentHashAsString(Document tree, boolean useSignature) {
        if (!tree.hasRootElement()) {
            return "";
        }
        return getElementHashAsString(tree.getRootElement(), useSignature);
    }

    /**
     * liefert den Hash eines Elements
     *
     * @param element das Element
     * @return der numerische Hash
     */
    public static int getElementHash(Element element) {
        return getElementHash(element, false);
    }

    /**
     * liefert den Hash eines Elements
     *
     * @param element      das Element
     * @param useSignature wenn das Attribut signature bereits gesetzt ist, dann
     *                     führt er keine Neuberechnung aus
     * @return der eineutige Hash als Text
     */
    public static int getElementHash(Element element, boolean useSignature) {
        return getElementHashAsString(element, useSignature).hashCode();
    }

    /**
     * liefert den Hash eines Elements
     *
     * @param element das Element
     * @return der eineutige Hash als Text
     */
    public static String getElementHashAsString(Element element) {
        return getElementHashAsString(element, false);
    }

    /**
     * liefert den Hash eines Elements
     *
     * @param element      das Element
     * @param useSignature wenn das Attribut signature bereits gesetzt ist, dann
     *                     führt er keine Neuberechnung aus
     * @return der eineutige Hash als Text
     */
    public static String getElementHashAsString(Element element, boolean useSignature) {
        String signature = null;
        if (useSignature) {
            signature = element.getAttributeValue("signature");
            if (signature != null) {
                return signature;
            }
        }

        List<Element> childs = element.getChildren();

        String label = element.getAttributeValue("label");
        if (label == null) {
            label = "";
        } else {
            label = "_" + label;
        }

        String classText = element.getAttributeValue("class");
        if (classText == null) {
            classText = "";
        } else {
            classText = "_" + classText;
        }

        if (childs != null && childs.size() > 0) {
            String text = "";
            for (Element a : childs) {
                text += getElementHashAsString(a);
            }
            signature = "{" + element.getName() + label + classText + "_" + text + "}";
        } else {
            signature = "{" + element.getName() + label + classText + "}";
        }
        if (useSignature) {
            element.setAttribute("signature", signature);
        }
        return signature;
    }

    /**
     * wandelt einen Baum in Textdarstellung um (also XML)
     *
     * @param tree der Baum
     * @return die Textdarstellung
     */
    public static String printDocument(Document tree) {
        if (tree == null) {
            return "";
        }
        return xsltProcessor.DocumentToXml(tree);
    }

    /**
     * setzt die Signaturattribute eines Baums zurück
     *
     * @param tree der Baum
     */
    public static void resetSignatures(Document tree) {
        if (!tree.hasRootElement()) {
            return;
        }
        resetSignature(tree.getRootElement());
    }

    /**
     * setzt die Signaturattribute des Unterbaums zurück (löscht also das
     * Attribut "signature")
     *
     * @param element das Startelement
     */
    public static void resetSignature(Element element) {
        // element.setAttribute("signature", null);
        element.removeAttribute("signature");
        List<Element> childs = element.getChildren();
        if (childs == null || childs.isEmpty()) {
            return;
        }
        for (Element child : childs) {
            resetSignature(child);
        }
    }

    /**
     * das class-Attribut des ersten Kindes unterhalb von root liefert den Typ
     * (wenn dieser Baum anders strukturiert ist, kann es natürlich sein, dass
     * sich dort kein Statement befindet)
     *
     * @param tree der Baum
     * @return der Anfragetyp
     */
    public static String getQueryType(Document tree) {
        if (!tree.hasRootElement()) {
            return null;
        }
        Element root = tree.getRootElement();
        List<Element> childs = root.getChildren();
        if (childs.size() != 1) {
            return null;
        }
        return childs.get(0).getAttributeValue("class");
    }

}
