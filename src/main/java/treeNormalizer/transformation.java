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
package treeNormalizer;

import org.jdom.Document;
import treeNormalizer.utils.treeUtilities;

/**
 * Stellt die Struktur für ein Dokument tree dar, welches transformiert werden
 * soll. Dabei enthält context eventuelle Zusatzinfos für den Kontext
 * (beispielsweise als createTabel Statement) bereit.
 *
 * @author Till
 */
public class transformation {

    /*
     * der Baum der möglicherweise bearbeitet wird
     */
    private Document tree;

    /*
     * ein Baum, welcher den Kontext von tree beschreibt
     */
    private Document context;

    /**
     * initialisiert die Transformation anhand eines Baums
     *
     * @param tree der Baum
     */
    public transformation(Document tree) {
        this.tree = tree;
    }

    /**
     * der Standardkonstruktor
     */
    public transformation() {
        // kein Inhalt
    }

    /**
     * liefert eine Textdarstellung dieses Objects
     *
     * @return String die Textdarstellung
     */
    public String prettyPrint() {
        return "tree:\n" + treeUtilities.printDocument(tree) + "\n\ncontext:\n" + treeUtilities.printDocument(context);
    }

    /**
     * @return the tree
     */
    public Document getTree() {
        return tree;
    }

    /**
     * @param tree the tree to set
     */
    public void setTree(Document tree) {
        this.tree = tree;
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

}
