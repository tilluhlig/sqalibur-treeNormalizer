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
package treeNormalizer.structure.internal;

import treeNormalizer.structure.tree;
import treeNormalizer.structure.reference;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Diese Klasse stellt einen Baum dar, dabei ist sie im Wesentlichen ein Zeiger
 * in Form einer nodeReference auf die Wurzel des Baums enthält.
 *
 * Damit ist internalTree eine Hilfskonstrukt.
 *
 * @author Till
 */
public class internalTree implements tree {

    /**
     * der Name des Baums
     */
    private String name;

    /**
     * die Wurzel des Baums
     */
    private nodeReference root = null;

    /**
     * erzeugt einen neuen Baum
     *
     * @param name der Name
     */
    public internalTree(String name) {
        this.name = name;
    }

    /**
     * erzeugt einen neuen Baum
     *
     * @param root die Wurzel
     */
    public internalTree(nodeReference root) {
        this.root = root;
    }

    /**
     * erzeugt einen neuen Baum
     *
     * @param root die Wurzel
     * @param name der Name
     */
    public internalTree(nodeReference root, String name) {
        this.root = root;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final internalTree other = (internalTree) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.root, other.root);
    }

    /**
     * gibt den Namen des Baum zurück
     *
     * @return der Name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * setzt den Namen des Baums
     *
     * @param name der neue Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * liefert den Wurzelknoten
     *
     * @return der Wurzelknoten
     */
    @Override
    public nodeReference getRoot() {
        return root;
    }

    /**
     * setzt den Wurzelknoten
     *
     * @param root der neue Wurzelknoten
     */
    public void setRoot(nodeReference root) {
        this.root = root;

        // es sollte eigentlich nicht nötig sein, den Baum hier extra nochmal
        // zu setzen, aber sicher ist sicher
        if (this.root != null) {
            root.setTree(this);
        }
    }

    /**
     * ob die Wurzel gesetzt ist
     *
     * @return ob die Wurzel gesetzt ist (true = ja, false = sonst)
     */
    @Override
    public boolean hasRoot() {
        return getRoot() != null;
    }

    @Override
    public int hashCode() {
        // der hash bezieht sich nur auf den Namen
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.name);
        return hash;
    }

    /**
     * wandelt den Baum in eine darstellbare Form um
     *
     * @return die Textdarstellung des Baums
     */
    @Override
    public String print() {
        String tmp = "";
        tmp += getName() + ":\n";
        if (hasRoot()) {
            tmp += "ROOT: " + getRoot().getId() + "\n\n";

            ArrayList<nodeReference> tmp2 = new ArrayList<>();
            tmp2.add(getRoot());

            while (!tmp2.isEmpty()) {
                nodeReference ref = tmp2.get(0);
                tmp2.remove(0);

                tmp += ref.getId() + " -> ";
                ArrayList<String> par = new ArrayList<>();
                for (reference child : ref.getChilds()) {
                    int a = child.getId();
                    par.add(Integer.toString(a));
                    tmp2.add((nodeReference) child);
                }
                tmp += String.join(", ", par);
                tmp += "\n";
            }
            tmp += "\n";
        } else {
            tmp = "ROOT: -\n\n";
        }

        return tmp;
    }

}
