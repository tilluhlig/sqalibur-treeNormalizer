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
package treeNormalizer.structure;

import java.util.ArrayList;

/**
 * Diese Knotenreferenzen beziehen sich auf treeBucketNode Objekte, sodass diese
 * Klasse die Element zur Nachbildung eines Baums darstellt.
 *
 * @author Till
 */
public class nodeReference extends Object {

    /**
     * die Kinder dieser Referenz
     */
    private ArrayList<nodeReference> childs = new ArrayList<>();

    /**
     * eine ID des Zielknotens, diese ID wird durch durch eine übergeordnete
     * Verwaltung (in einer HashMap) behandelt und soll zu der ID einen
     * entsprechenden Knoten liefern (diese id ist also global eindeutig)
     */
    private int id;

    /**
     * der Elternteil (null = kein Elterknoten)
     */
    private nodeReference parent = null;

    /**
     * der verknüpfte Baum (wir gehören also zu diesem Baum oder sind sogar die
     * Wurzel)
     */
    private tree tree;

    /**
     * erzeugt eine neue Referenz
     *
     * @param tree der zugehörige Baum
     * @param id   die eindeutige Knoten-ID
     */
    public nodeReference(tree tree, int id) {
        this.id = id;
        this.tree = tree;
    }

    /**
     * fügt ein Kind ein
     *
     * @param child das Kind
     */
    public void addChild(nodeReference child) {
        // prüfe, dass das Kind nicht doppelt existiert
        if (childs.contains(child)) {
            //throw new IllegalArgumentException("das Kind existiert bereits");
            return;
        }
        childs.add(child);
    }

    /**
     * prüft, ob das Kind im Kinderfeld existiert
     *
     * @param child das Kind
     * @return true = Kind gefunden, false = Kind existiert nicht
     */
    public boolean childExists(nodeReference child) {
        return childs.indexOf(child) >= 0;
    }

    /**
     * entfernt alle Verbindungen dieser Referenz zu anderen Referenzen
     */
    public void disconnect() {
        // entfernt die Kante zum Elternknoten
        if (hasParent()) {
            removeEdgeFrom(parent);
        }

        // entfernt die Kanten zu den Kindern
        if (hasChilds()) {
            for (nodeReference child : getExistingChilds()) {
                removeEdgeTo(child);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        final nodeReference other = (nodeReference) obj;
        return (this.id == other.id && this.tree == other.tree);
    }

    /**
     * liefert alle Kinder eines Knotens
     *
     * @return die Kinder
     */
    public ArrayList<nodeReference> getChilds() {
        return childs;
    }

    /**
     * setzt die Kinderknoten
     *
     * @param childs die Kinder
     */
    public void setChilds(ArrayList<nodeReference> childs) {
        this.childs = childs;
    }

    /**
     * liefert eine Liste der Kinder, deren Felder tatsächlich mit Knoten belegt
     * sind
     *
     * @return die Kinder
     */
    public ArrayList<nodeReference> getExistingChilds() {
        ArrayList<nodeReference> res = new ArrayList<>();
        for (nodeReference child : childs) {
            if (child != null) {
                res.add(child);
            }
        }
        return res;
    }

    /**
     * liefert die ausgehenden Kanten, welche tatsächlich mit Knoten verbunden
     * sind
     *
     * @return die ausgehenden Kanten
     */
    public edge[] getExistingOutgoingEdges() {
        ArrayList<nodeReference> existingChilds = getExistingChilds();
        edge[] res = new edge[existingChilds.size()];
        int i = 0;
        for (nodeReference node : existingChilds) {
            res[i] = new edge(this, node);
            i++;
        }
        return res;
    }

    /**
     * liefert die ID der Referenz
     *
     * @return die ID
     */
    public int getId() {
        return id;
    }

    /**
     * setzt die ID der Referenz
     *
     * @param id die ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * ermittelt das linke Kind
     *
     * @return null = das Kind existiert nicht , sonst = das Kind
     */
    public nodeReference getLeftChild() {
        if (!isChildIndexPossible(0)) {
            return null;
        }
        return childs.get(0);
    }

    /**
     * liefert die ausgehenden Kanten
     *
     * @return die Kanten
     */
    public edge[] getOutgoingEdges() {
        edge[] res = new edge[childs.size()];
        int i = 0;
        for (nodeReference node : childs) {
            res[i] = new edge(this, node);
            i++;
        }
        return res;
    }

    /**
     * liefert den Vater/die Mutter eines Knotens
     *
     * @return die Eltern
     */
    public nodeReference getParent() {
        return parent;
    }

    /**
     * setzt den Elternknoten
     *
     * @param parent der Elternknoten
     */
    public void setParent(nodeReference parent) {
        this.parent = parent;
    }

    /**
     * ermittelt das letzte/ rechte Kind
     *
     * @return null = das Kind existiert nicht , sonst = das Kind
     */
    public nodeReference getRightChild() {
        if (!isChildIndexPossible(0)) {
            return null;
        }
        return childs.get(childs.size() - 1);
    }

    /**
     * liefert den verknüpften Baum
     *
     * @return der Baum
     */
    public tree getTree() {
        return tree;
    }

    /**
     * setzt den zugehörigen Baum
     *
     * @param tree der Baum
     */
    public void setTree(tree tree) {
        this.tree = tree;
    }

    /**
     * liefert den Wurzelknoten eines Baums
     *
     * @return die Wurzel des Baums
     */
    public nodeReference getTreeRoot() {
        if (tree == null) {
            return null;
        }
        return tree.getRoot();
    }

    /**
     * prüft, ob ein Knoten Kinder besitzt (existierende Kinder)
     *
     * @return true = hat keine Kinder, false = hat Kinder
     */
    public boolean hasChilds() {
        // die Kinder könnten leer sein
        int anz = 0;
        for (nodeReference child : childs) {
            if (child != null) {
                anz++;
            }
        }
        return anz > 0;
    }

    /**
     * prüft, ob ein Knoten Eltern besitzt
     *
     * @return true = hat Eltern, false = hat keine Eltern
     */
    public boolean hasParent() {
        return parent != null;
    }

    /**
     * Returns a hash code for this node.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return this.id;
    }

    /**
     * liefert den Eingangsgrad eines Knotens
     *
     * @return der Eingangsgrad (Anzahl der Eltern)
     */
    public int inDegree() {
        // es ist ein Baum
        if (hasParent()) {
            return 1;
        }
        return 0;
    }

    /**
     * prüft, ob der Knoten ein Kind ist (bzw. ob er Eltern hat)
     *
     * @return true = ist ein Kind, false = ist kein Kind
     */
    public boolean isChild() {
        return hasParent();
    }

    /**
     * prüft, ob der index ausserhalb des Bereichs liegt
     *
     * @param index der Index
     * @return
     */
    public boolean isChildIndexPossible(int index) {
        if (index < 0 || index >= childs.size()) {
            return false;
        }
        return true;
    }

    /**
     * gibt aus, ob das Kindfeld mit einem Knoten besetzt ist
     *
     * @param index der Index
     * @return true = das Feld child[i] hat einen Knoten, false = es existiert
     *         kein Knoten
     */
    public boolean isChildPresent(int index) {
        if (!isChildIndexPossible(index)) {
            return false;
        }
        return childs.get(index) != null;
    }

    /**
     * prüft, ob der Knoten ein Blatt ist (also keine Kinder hat)
     *
     * @return true = ist ein Blatt, false = ist kein Blatt
     */
    public boolean isLeaf() {
        return !hasChilds();
    }

    /**
     * prüft, ob der Knoten keine Eltern hat
     *
     * @return true = keine Eltern, false = hat Eltern
     */
    public boolean isOrphan() {
        return !hasParent();
    }

    /**
     * prüft, ob der Knoten die Wurzel ist
     *
     * @return true = ist Wurzel, false = ist nicht die Wurzel
     */
    public boolean isRoot() {
        return !hasParent();
    }

    /**
     * liefert den Ausgangsgrad eines Knotens
     *
     * @return der Ausgangsgrad (Anzahl der Kindknoten)
     */
    public int outDegree() {
        return getChilds().size();
    }

    /**
     * wandelt die Knotenreferenz in eine darstellbare Form um
     *
     * @return die Textdarstellung des Knotens
     */
    public String print() {
        String tmp = "";
        // ausfüllen
        // ausfüllen
        // ausfüllen
        return tmp;
    }

    /**
     * entfernt ein Kind (setzt die Kante auf null)
     *
     * @param child das Kind
     */
    public void removeChild(nodeReference child) {
        int i = childs.indexOf(child);
        if (i >= 0) {
            setChild(i, null);
        }
    }

    /**
     * entfernt eine bestimmte Kante
     *
     * @param index der Index (also das Kind)
     */
    public void removeEdge(int index) {
        if (!isChildIndexPossible(index)) {
            throw new IllegalArgumentException("die Kante existiert nicht");
        }

        setChild(index, null);
    }

    /**
     * entfernt eine Kante vom Knoten "source"
     *
     * @param source der Quellknoten
     */
    public void removeEdgeFrom(nodeReference source) {
        source.removeEdgeTo(this);
    }

    /**
     * entfernt eine Kante zum Knoten "target"
     *
     * @param target der Zielknoten der Kante
     */
    public void removeEdgeTo(nodeReference target) {
        if (!childExists(target)) {
            throw new IllegalArgumentException("die Kante existiert nicht");
        }

        removeChild(target);
        target.removeParent();
    }

    /**
     * entfernt den Elternknoten aus der Referenz
     */
    public void removeParent() {
        parent = null;
    }

    /**
     * setzt ein Kind (also eine Kante)
     *
     * @param index der Index (welches Kind)
     * @param child der neue Knoten
     */
    public void setChild(int index, nodeReference child) {
        if (!isChildIndexPossible(index)) {
            throw new ArrayIndexOutOfBoundsException("das Kind existiert nicht");
        }
        childs.set(index, child);
    }

}
