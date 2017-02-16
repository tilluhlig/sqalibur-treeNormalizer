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
 *
 * @author Till
 */
public class treeUsage {

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

    public static int getDocumentHash(Document tree) {
        return getDocumentHash(tree, false);
    }

    public static int getDocumentHash(Document tree, boolean useSignature) {
        return getDocumentHashAsString(tree, useSignature).hashCode();
    }

    public static String getDocumentHashAsString(Document tree) {
        return getDocumentHashAsString(tree, false);
    }

    public static String getDocumentHashAsString(Document tree, boolean useSignature) {
        if (!tree.hasRootElement()) {
            return "";
        }
        return getElementHashAsString(tree.getRootElement(), useSignature);
    }

    public static int getElementHash(Element e) {
        return getElementHash(e, false);
    }

    public static int getElementHash(Element e, boolean useSignature) {
        return getElementHashAsString(e, useSignature).hashCode();
    }

    public static String getElementHashAsString(Element e) {
        return getElementHashAsString(e, false);
    }

    public static String getElementHashAsString(Element e, boolean useSignature) {
        String signature = null;
        if (useSignature) {
            signature = e.getAttributeValue("signature");
            if (signature != null) {
                return signature;
            }
        }

        List<Element> childs = e.getChildren();

        String label = e.getAttributeValue("label");
        if (label == null) {
            label = "";
        }

        String classText = e.getAttributeValue("class");
        if (classText == null) {
            classText = "";
        }

        if (childs != null && childs.size() > 0) {
            String text = "";
            for (Element a : childs) {
                text += getElementHash(a);
            }
            signature = "{" + e.getName() + "_" + label + "_" + classText + "_" + text + "}";
        } else {
            signature = "{" + e.getName() + "_" + label + "_" + classText + "}";
        }
        if (useSignature) {
            e.setAttribute("signature", signature);
        }
        return signature;
    }

    public void resetSignatures(Document tree) {
        if (!tree.hasRootElement()) {
            return;
        }
        resetSignature(tree.getRootElement());
    }

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
