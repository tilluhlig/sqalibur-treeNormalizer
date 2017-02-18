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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
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
     * @param tree
     * @return
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
     *
     * @param tree
     * @param useSignature
     * @return
     */
    public static int getDocumentHash(Document tree, boolean useSignature) {
        return getDocumentHashAsString(tree, useSignature).hashCode();
    }

    /**
     *
     * @param tree
     * @return
     */
    public static String getDocumentHashAsString(Document tree) {
        return getDocumentHashAsString(tree, false);
    }

    /**
     *
     * @param tree
     * @param useSignature
     * @return
     */
    public static String getDocumentHashAsString(Document tree, boolean useSignature) {
        if (!tree.hasRootElement()) {
            return "";
        }
        return getElementHashAsString(tree.getRootElement(), useSignature);
    }

    /**
     *
     * @param element
     * @return
     */
    public static int getElementHash(Element element) {
        return getElementHash(element, false);
    }

    /**
     *
     * @param element
     * @param useSignature
     * @return
     */
    public static int getElementHash(Element element, boolean useSignature) {
        return getElementHashAsString(element, useSignature).hashCode();
    }

    /**
     *
     * @param element
     * @return
     */
    public static String getElementHashAsString(Element element) {
        return getElementHashAsString(element, false);
    }

    /**
     *
     * @param element
     * @param useSignature
     * @return
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
     * setzt die Signaturattribute eines Baums zurück
     *
     * @param tree der Baum
     */
    public void resetSignatures(Document tree) {
        if (!tree.hasRootElement()) {
            return;
        }
        resetSignature(tree.getRootElement());
    }

    /**
     * setzt die Signaturattribute Unterbaums zurück
     *
     * @param element das Startelement
     */
    public void resetSignature(Element element) {
        element.setAttribute("signature", null);
        List<Element> childs = element.getChildren();
        if (childs == null || childs.isEmpty()) {
            return;
        }
        for (Element child : childs) {
            resetSignature(child);
        }
    }

}
