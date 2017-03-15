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

import treeNormalizer.structure.internal.tree;
import treeNormalizer.structure.internal.edge;
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
     * eine ID des Zielknotens, diese ID wird durch eine übergeordnete
     * Verwaltung (in einer HashMap) behandelt und soll zu der ID einen
     * entsprechenden Knoten liefern (diese id ist also global eindeutig)
     *
     * 0 = keine korrekte ID
     */
    private int id = 0;

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
     * fügt ein Kind ein Wenn das Kind bereits existiert, wird es ignoriert
     *
     * @param child das Kind
     */
    public void addChild(nodeReference child) {
        // prüfe, dass das Kind nicht doppelt existiert
        if (childExists(child)) {
            //throw new IllegalArgumentException("das Kind existiert bereits");
            return;
        }
        childs.add(child);
    }

    /**
     * prüft, ob das Kind im Kinderfeld existiert (der Vergleich erfolgt anhand
     * der ID der Referenz)
     *
     * @param child das Kind
     * @return true = Kind gefunden, false = Kind existiert nicht
     */
    public boolean childExists(nodeReference child) {
        return childs.contains(child);
    }

    /**
     * entfernt alle Verbindungen dieser Referenz zu anderen Referenzen Es wird
     * also die Kanten zu Vaterknoten und alle Kanten zu den Kindern entfernt.
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
     * liefert den Eingangsgrad eines Knotens (ob der Vater gesetzt ist oder
     * nicht)
     *
     * @return der Eingangsgrad (Anzahl der Eltern, also 1 oder 0)
     */
    public int getInDegree() {
        // es ist ein Baum
        return hasParent() ? 1 : 0;
    }

    /**
     * ermittelt das linke Kind
     *
     * @return null = das Kind existiert nicht, sonst = das Kind
     */
    public nodeReference getLeftChild() {
        if (!isChildIndexPossible(0)) {
            return null;
        }
        return childs.get(0);
    }

    /**
     * liefert das Kind an der Position id
     *
     * @param id die Position in der Kinderliste
     * @return das Kind oder null (wenn es nicht existiert oder nicht nicht
     *         gesetzt ist)
     */
    public nodeReference getChild(int id) {
        if (!isChildIndexPossible(id)) {
            return null;
        }
        return childs.get(id);
    }

    /**
     * liefert den Ausgangsgrad eines Knotens (auch leere ausgehende Kanten sind
     * enthalten)
     *
     * @return der Ausgangsgrad (Anzahl der Kindknoten)
     */
    public int getOutDegree() {
        return getChilds().size();
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
     * liefert den Ausgangsgrad eines Knotens (wobei leere Kinder ignoriert
     * werden)
     *
     * @return der Ausgangsgrad (Anzahl der gesetzten Kindknoten)
     */
    public int getRealOutDegree() {
        return getExistingChilds().size();
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
     * @return die Wurzel des Baums und null wenn kein Baum gesetzt ist
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
        for (nodeReference child : childs) {
            if (child != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * prüft, ob ein Knoten einen Vater/Mutter besitzt
     *
     * @return true = hat einen Vater, false = hat keinen Vater
     */
    public boolean hasParent() {
        return parent != null;
    }

    /**
     * Returns a hash code for this node.
     *
     * @return a hash code value for this object. (die id ist der Hash)
     */
    @Override
    public int hashCode() {
        return this.id;
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
        return !(index < 0 || index >= childs.size());
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
     * prüft, ob der Knoten die Wurzel ist (also kein Vater gesetzt ist)
     *
     * @return true = ist Wurzel, false = ist nicht die Wurzel
     */
    public boolean isRoot() {
        return !hasParent();
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
     * entfernt ein Kind aus der Liste und löscht den Kindereintrag
     *
     * @param child das Kind
     */
    public void removeChild(nodeReference child) {
        int i = childs.indexOf(child);
        if (i >= 0) {
            childs.remove(i);
        }
    }

    /**
     * entfernt ein Kind aus der Liste und löscht den Kindereintrag
     *
     * @param childPos die Position in der Kinderliste
     */
    public void removeChild(int childPos) {
        if (isChildIndexPossible(childPos)) {
            childs.remove(childPos);
        }
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
        removeChild(target);
        target.removeParent();
    }

    /**
     * entfernt den Elternknoten aus der Referenz (setzt also den Vater auf
     * null)
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

    /**
     * entfernt ein Kind (setzt die Kante auf null)
     *
     * @param child die Referenz des Kindes
     */
    public void unsetChild(nodeReference child) {
        int i = childs.indexOf(child);
        if (i >= 0) {
            setChild(i, null);
        }
    }

    /**
     * entfernt ein Kind (setzt die Kante auf null)
     *
     * @param childPos die Position in der Kinderliste
     */
    public void unsetChild(int childPos) {
        if (isChildIndexPossible(childPos)) {
            setChild(childPos, null);
        }
    }

    /**
     * setzt einen Kindeintrag von "source" auf null
     *
     * @param source der Quellknoten
     */
    public void unsetEdgeFrom(nodeReference source) {
        source.unsetEdgeTo(this);
    }

    /**
     * setzt einen Kindeintrag zum Knoten "target" auf null
     *
     * @param target der Zielknoten der Kante
     */
    public void unsetEdgeTo(nodeReference target) {
        if (!childExists(target)) {
            throw new IllegalArgumentException("die Kante existiert nicht");
        }

        unsetChild(target);
        target.unsetParent();
    }

    /**
     * setzt den Vater des Knotens auf null (ist aber wie removeParent)
     */
    public void unsetParent() {
        removeParent();
    }

}
