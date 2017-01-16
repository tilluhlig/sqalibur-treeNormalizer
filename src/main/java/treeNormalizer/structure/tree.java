/*
 * Copyright (C) 2016 Till Uhlig <till.uhlig@student.uni-halle.de>
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
package treeNormalizer.structure;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Till
 */
public class tree {

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
    public tree(String name) {
        this.name = name;
    }

    /**
     * erzeugt einen neuen Baum
     *
     * @param root die Wurzel
     */
    public tree(nodeReference root) {
        this.root = root;
    }

    /**
     * erzeugt einen neuen Baum
     *
     * @param root die Wurzel
     * @param name der Name
     */
    public tree(nodeReference root, String name) {
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
        final tree other = (tree) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.root, other.root)) {
            return false;
        }
        return true;
    }

    /**
     * gibt den Namen des Baum zurück
     *
     * @return der Name
     */
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
        root.setTree(this);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.name);
        return hash;
    }

    public boolean hasRoot() {
        return getRoot() != null;
    }

    /**
     * wandelt den Baum in eine darstellbare Form um
     *
     * @return die Textdarstellung des Baums
     */
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
                for (nodeReference child : ref.getChilds()) {
                    int a = child.getId();
                    par.add(Integer.toString(a));
                    tmp2.add(child);
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
