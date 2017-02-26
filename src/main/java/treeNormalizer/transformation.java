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

import treeNormalizer.structure.treeBucket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.jdom.Document;
import treeNormalizer.utils.treeUtilities;

/**
 *
 * @author Till
 */
public class transformation {

    private Document tree;

    private Document context;

    public transformation(Document tree) {
        this.tree = tree;
    }

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
