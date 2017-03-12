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
import java.util.HashMap;
import java.util.Map;

/**
 * Diese Klasse stellt die real existierenden Knoten einer treeBucket dar. Dabei
 * können diese Kinder besitzen und mehrere Eltern haben, sodass diese Knoten
 * selbst keine Bäume darstellen sondern erst durch Interpretation der
 * nodeReferences zu Bäumen werden.
 *
 * Jeder Knoten kann mehrere Kinder und mehrere Eltern besitzen.
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class treeBucketNode {

    /**
     * die Attributliste (falls zusätzliche Eigenschaften an den Knoten gebunden
     * werden sollen) Damit kann auch der Kontext gespeichert werden.
     */
    private Map<String, String> attributes = new HashMap<>();

    /**
     * die Kindknoten
     */
    private ArrayList<treeBucketNode> childs = new ArrayList<>();

    /**
     * der Hashwert (wird durch rehash berechnet)
     */
    private int hash = 0; // Default to 0

    /**
     * der Name des Knotens/Bezeichner (beispielsweise sowas wie "1" oder "+").
     * Man könnte das Feld auch content nennen.
     */
    private String label = "";

    /**
     * Diese Liste enthält die Knotenreferenzen, welche auf diesen Knoten
     * zeigen. denn ein Knoten kann von mehreren Pfaden genutzt werden
     */
    private ArrayList<nodeReference> nodeReferences = new ArrayList<>();

    /**
     * die Elternknoten
     */
    private ArrayList<treeBucketNode> parents = new ArrayList<>();

    /**
     * die klassifizierung des Knotens (sowas wie binOperator, const oder
     * select)
     */
    private String type = "";

    /**
     * die uniqueID wird genutzt, um unterschiedliche hash-Werte für eigentlich
     * gleich Knoten zu erzeugen, indem beide Knoten verschiedene uniqueIDs
     * bekommen
     */
    private int uniqueId = 0;

    /**
     * erzeugt einen neuen Knoten
     */
    public treeBucketNode() {
        // Leer
    }

    /**
     * erzeugt einen neuen Knoten
     *
     * @param label der Name
     */
    public treeBucketNode(String label) {
        this.label = label;
    }

    /**
     * erzeugt einen neuen Knoten
     *
     * @param label das Label
     * @param type  der Typ (Klasse)
     */
    public treeBucketNode(String label, String type) {
        this.label = label;
        this.type = type;
    }

    /**
     * erzeugt einen neuen Knoten
     *
     * @param label      das Label
     * @param type       der Typ (Klasse)
     * @param attributes die Attribute
     */
    public treeBucketNode(String label, String type, Map<String, String> attributes) {
        this.label = label;
        this.type = type;
        this.attributes = attributes;
    }

    /**
     * fügt eine Attribut ein
     *
     * @param name  der Name
     * @param value der Wert
     */
    public void addAttribute(String name, String value) {
        setAttribute(name, value);
    }

    /**
     * fügt ein Knoten als Kind hinzu (also eine Kante)
     *
     * @param child das neue Kind
     */
    public void addChild(treeBucketNode child) {
        getChilds().add(child);
    }

    /**
     * fügt einen Knoten als Kind ein und eine Kante, von einem Quellknoten zu
     * diesem Knoten, ein
     *
     * @param sourceNode der Quellknoten
     */
    public void addEdgeFrom(treeBucketNode sourceNode) {
        sourceNode.addEdgeTo(this);
    }

    /**
     * fügt einen Knoten als Kind ein und eine Kante zu dem Zielknoten ein
     *
     * @param targetNode der Zielknoten
     */
    public void addEdgeTo(treeBucketNode targetNode) {
        targetNode.addParent(this);
        addChild(targetNode);
    }

    /**
     * fügt eine Knotenreferenz hinzu (darf noch nicht existieren)
     *
     * @param nodeReference die neue Referenz
     */
    public void addNodeReference(nodeReference nodeReference) {
        if (!nodeReferences.contains(nodeReference)) {
            getNodeReferences().add(nodeReference);
        }
    }

    /**
     * fügt eine Menge von Referenzen ein
     *
     * @param nodeReferences die Referenzen
     */
    public void addNodeReferences(ArrayList<nodeReference> nodeReferences) {
        for (nodeReference ref : nodeReferences) {
            addNodeReference(ref);
        }
    }

    /**
     * fügt einen Elternknoten hinzu (ein Elternknoten kann auch mehrfach
     * existieren)
     *
     * @param parent der neue Elternteil
     */
    public void addParent(treeBucketNode parent) {
        getParents().add(parent);
    }

    /**
     * fügt neue Eltern ein
     *
     * @param parents die neuen Eltern
     */
    public void addParents(ArrayList<treeBucketNode> parents) {
        parents.forEach((parent) -> {
            addParent(parent);
        });
    }

    /**
     * nimmt ein Kind auf (ein Kante von diesem Knoten zum Kind)
     *
     * @param child das neue Kind
     */
    public void adoptChild(treeBucketNode child) {
        addChild(child);
    }

    /**
     * entfernt doppelte Elternknoten
     */
    public void cleanParents() {
        // TODO: die Umsetzung ist noch sehr ineffizient
        ArrayList<treeBucketNode> newParents = new ArrayList<>();
        for (treeBucketNode parent : getParents()) {
            if (!newParents.contains(parent)) {
                newParents.add(parent);
            }
        }
        setParents(newParents);
    }

    /**
     * Erzeugt eine Kopie des Knotens (nur die Grunddaten) also: label, type,
     * attributes
     *
     * @return der neue Knoten
     */
    public treeBucketNode cloneNodeBase() {
        treeBucketNode tmp = new treeBucketNode(getLabel(), getType(), getAttributes());
        return tmp;
    }

    /**
     * prüft, ob der Baum in Referenzenliste auftaucht
     *
     * @param tree der Baum
     * @return ob der Baum in der Liste auftaucht
     */
    public boolean containsReferencedTree(tree tree) {
        for (nodeReference tmp : getNodeReferences()) {
            if (tmp == null) {
                continue;
            }
            if (!tmp.getTree().equals(tree)) {
                // es ist ein anderer Baum
            } else {
                // der Baum wurde gefunden
                return true;
            }
        }
        return false;
    }

    /**
     * verringert die uniqueId um 1
     *
     * @return die neue uniqueId (maximal auf 0)
     */
    public int decreaseUniqueId() {
        if (getUniqueId() == 0) {
            // TODO: was nun?
            return getUniqueId();
        }
        setUniqueId(getUniqueId() - 1);
        return getUniqueId();
    }

    /**
     * entfernt alle Verbindungen dieses Knotens zu anderen Knoten
     */
    public void disconnect() {
        removeChildEdges();
        removeParentEdges();
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
        final treeBucketNode other = (treeBucketNode) obj;
        return this.hashCode() == other.hashCode();
    }

    /**
     * liefert den Wert eines Attributs
     *
     * @param name der Name
     * @return der Wert (wenn es nicht existiert kommt null)
     */
    public String getAttribute(String name) {
        if (attributeExists(name)) {
            return getAttributes().get(name);
        }
        return null;
    }

    /**
     * prüft, ob ein Attribut existiert
     *
     * @param name der Name
     * @return true = existiert, false = existiert nicht
     */
    public boolean attributeExists(String name) {
        return getAttributes().containsKey(name);
    }

    /**
     * liefert alle Attribute
     *
     * @return die Attribute
     */
    public Map<String, String> getAttributes() {
        return attributes;
    }

    /**
     * setzt die Attributeliste
     *
     * @param attributes die Attribute
     */
    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    /**
     * liefert alle Kinder
     *
     * @return die Kinder
     */
    public ArrayList<treeBucketNode> getChilds() {
        return childs;
    }

    public treeBucketNode getChild(int pos) {
        if (pos >= 0 && pos < childs.size()) {
            return childs.get(pos);
        }
        return null;
    }

    /**
     * setzt die Kinderliste
     *
     * @param childs die neuen Kinder
     */
    public void setChilds(ArrayList<treeBucketNode> childs) {
        if (childs == null) {
            childs = new ArrayList<>();
        }
        this.childs = childs;
    }

    /**
     * liefert den ersten Elternknoten
     *
     * @return der erste Elternknoten
     */
    public treeBucketNode getFirstParent() {
        if (hasParents()) {
            return getParents().get(0);
        }
        return null;
    }

    public treeBucketNode getParent(int pos) {
        if (pos >= 0 && pos < getParents().size()) {
            return getParents().get(pos);
        }
        return null;
    }

    /**
     * liefert den Namen des Knotens
     *
     * @return der Name
     */
    public String getLabel() {
        return label;
    }

    /**
     * setzt den Namen des Knotens
     *
     * @param label der neue Name
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * liefert die Knotenreferenzen
     *
     * @return die Referenzen
     */
    public ArrayList<nodeReference> getNodeReferences() {
        return nodeReferences;
    }

    /**
     * liefert alle ausgehenden Kanten (also Kinder)
     *
     * @return die ausgehenden Kanten
     */
    public ArrayList<treeBucketNode> getOutgoingEdges() {
        return getChilds();
    }

    /**
     * liefert alle Eltern
     *
     * @return die Eltern
     */
    public ArrayList<treeBucketNode> getParents() {
        return parents;
    }

    /**
     * setzt die Elternliste
     *
     * @param parents die neuen Eltern
     */
    public void setParents(ArrayList<treeBucketNode> parents) {
        this.parents = parents;
    }

    /**
     * liefert alle verknüpften Bäume
     *
     * @return die zugehörigen Bäume
     */
    public ArrayList<tree> getReferencedTrees() {
        ArrayList<tree> tmp = new ArrayList<tree>();
        for (nodeReference ref : getNodeReferences()) {
            if (!tmp.contains(ref.getTree())) {
                tmp.add(ref.getTree());
            }
        }
        return tmp;
    }

    /**
     * liefert den Typ des Knotens
     *
     * @return der Typ
     */
    public String getType() {
        return type;
    }

    /**
     * setzt den Knotentyp (Klasse)
     *
     * @param type der Typ
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * liefert die uniqueId
     *
     * @return die uniqueId
     */
    public int getUniqueId() {
        return uniqueId;
    }

    /**
     * prüft, ob der Knoten Kinder hat
     *
     * @return true = hat Kinder, false = hat keine Kinder
     */
    public boolean hasChilds() {
        return !isLeaf();
    }

    /**
     * prüft, ob der Knoten Eltern hat
     *
     * @return true = hat Eltern, false = hat keine Eltern
     */
    public boolean hasParents() {
        return !parents.isEmpty();
    }

    /**
     * Returns a hash code for this treeBucketNode.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        if (getHash() == 0) {
            rehash();
        }
        return getHash();
    }

    /**
     * gibt die Anzahl der eingehenden Kanten des Knotens
     *
     * @return die Anzahl der eingehenden Kanten
     */
    public int inDegree() {
        // obwohl es hier irgendwie um Bäume geht, kann der Eingangsgrad > 1
        // sein, weil treeBucketNode sich selbst nicht als Baum betrachtet (ein Knoten
        // kann hier mehrere Eltern haben)
        return getParents().size();
    }

    /**
     * erhöht den Wert der uniqueID um 1
     *
     * @return die neue uniqueId
     */
    public int increaseUniqueId() {
        setUniqueId(getUniqueId() + 1);
        if (getUniqueId() < 0) {
            return 0;
        }
        return getUniqueId();
    }

    /**
     * prüft, ob der Knoten ein Kind ist (also Eltern hat)
     *
     * @return true = ist ein Kind, false = ist kein Kind
     */
    public boolean isChild() {
        return hasParents();
    }

    /**
     * prüft, ob der Knoten Kinderlos ist
     *
     * @return true = hat keine Kinder, false = hat Kinder
     */
    public boolean isChildless() {
        return !hasChilds();
    }

    /**
     * prüft, ob der Knoten ein Blatt ist (keine weiteren Kinder)
     *
     * @return true = ist ein Blatt, false = kein Blatt
     */
    public boolean isLeaf() {
        return getChilds().isEmpty();
    }

    /**
     * prüft, ob der Knoten keine Eltern hat
     *
     * @return true = keine Eltern, false = hat Eltern
     */
    public boolean isOrphan() {
        return !hasParents();
    }

    /**
     * prüft, ob der Knoten eine Wurzel ist (keine Eltern hat)
     *
     * @return true = ist eine Wurzel, false = ist nicht die Wurzel
     */
    public boolean isRoot() {
        return !hasParents();
    }

    /**
     * prüft, ob der Knoten mit dem Baum verbunden ist
     *
     * @param tree der Baum
     * @return ob der Knoten zum Baum gehört
     */
    public boolean isTreeReferenced(tree tree) {
        return containsReferencedTree(tree);
    }

    /**
     * liefert die Anzahl der Knotenreferenzen
     *
     * @return die Anzahl der Knotenreferenzen
     */
    public int numberOfNodeReferences() {
        return getNodeReferences().size();
    }

    /**
     * liefert die Anzahl der Elternknoten
     *
     * @return die Anzahl der Eltern
     */
    public int numberOfParents() {
        return getParents().size();
    }

    /**
     * liefert die Anzahl der verknüpften Bäume
     *
     * @return die Anzahl der Bäume
     */
    public int numberOfReferencedTrees() {
        return getReferencedTrees().size();
    }

    /**
     * liefert die Anzahl der ausgehenden Kanten
     *
     * @return die ausgehenden Kanten
     */
    public int outDegree() {
        return getChilds().size();
    }

    /**
     * erzeugt eine druckbare Darstellung des Knotens
     *
     * @return die Textdarstellung
     */
    public String print() {
        String tmp = getLabel() + "[" + getType() + "] #" + hashCode() + "\n";

        if (hasParents()) {
            tmp += "parents: ";
            ArrayList<String> par = new ArrayList<>();
            getParents().forEach((parent) -> {
                par.add(parent.getLabel() + " #" + parent.hashCode());
            });
            tmp += String.join(", ", par);
            tmp += "\n";
        }

        if (hasChilds()) {
            tmp += "childs: ";
            ArrayList<String> par = new ArrayList<>();
            getChilds().forEach((child) -> {
                par.add(child.getLabel() + " #" + child.hashCode());
            });
            tmp += String.join(", ", par);
            tmp += "\n";
        }

        if (numberOfNodeReferences() > 0) {
            tmp += "refs: ";
            ArrayList<String> par = new ArrayList<>();
            getNodeReferences().forEach((nodeId) -> {
                par.add(nodeId.getId() + "=>" + nodeId.getTree().getName());
            });
            tmp += String.join(", ", par);
            tmp += "\n";
        }

        return tmp;
    }

    /**
     * aktualisiert den Hashwert des Objekts
     */
    public void rehash() {
        // label, type, children, attributes
        String tmpHash = getLabel() + "_" + getUniqueId() + "_" + getType();
        for (treeBucketNode child : getChilds()) {
            if (child == null) {
                tmpHash += "_null";
            } else {
                tmpHash += "_" + child.hashCode();
            }
        }
        for (Map.Entry<String, String> attribute : getAttributes().entrySet()) {
            tmpHash += "." + attribute.getKey() + "=" + attribute.getValue();
        }
        setHash(tmpHash.hashCode());
    }

    /**
     * entfernt alle Attribute
     */
    public void removeAllAttributes() {
        getAttributes().clear();
    }

    /**
     * entfernt alle Knotenreferenzen
     */
    public void removeAllNodeReferences() {
        getNodeReferences().clear();
    }

    /**
     * entfernt einen Attributeintrag
     *
     * @param name der Name
     */
    public void removeAttribute(String name) {
        getAttributes().remove(name);
    }

    /**
     * entfernt ein Kindverweis
     *
     * @param id die Position im Kind-Array
     */
    public void removeChild(int id) {
        getChilds().remove(id);
    }

    /**
     * entfernt ein Kindverweis
     *
     * @param object das Kind
     */
    public void removeChild(treeBucketNode object) {
        getChilds().remove(object);
    }

    /**
     * entfernt alle Verbindungen zu den Kindern dieses Knotens
     */
    public void removeChildEdges() {
        for (treeBucketNode child : getChilds()) {
            removeEdgeTo(child);
        }
    }

    /**
     * entfernt eine Kante von einem anderen Knoten zu diesem
     *
     * @param sourceNode der Quellknoten
     */
    public void removeEdgeFrom(treeBucketNode sourceNode) {
        sourceNode.removeEdgeTo(this);
    }

    /**
     * entfernt eine Kante zu einem anderen Knoten
     *
     * @param targetNode der Zielknoten
     */
    public void removeEdgeTo(treeBucketNode targetNode) {
        targetNode.removeParent(this);
        removeChild(targetNode);
    }

    /**
     * setzt eine Kante zu einem Kind auf null und entfernt diesen Knoten dort
     * als Vater
     *
     * @param targetNode
     */
    public void unsetEdgeTo(treeBucketNode targetNode) {
        targetNode.removeParent(this);
        unsetChild(targetNode);
    }

    /**
     * setzt ein Kind auf null
     *
     * @param targetNode das betroffene Kind
     */
    public void unsetChild(treeBucketNode targetNode) {
        if (childs.indexOf(targetNode) >= 0) {
            int a = childs.indexOf(targetNode);
            unsetChild(childs.indexOf(targetNode));
        }
    }

    /**
     * setzt ein Kind auf null
     *
     * @param targetNode das betroffene Kind
     */
    public void unsetChild(int targetNode) {
        childs.set(targetNode, null);
    }

    /**
     * enfernt eine Referenz
     *
     * @param id die Referenz-ID der Referenz
     */
    public void removeNodeReference(int id) {
        if (id < 0 || id >= getNodeReferences().size()) {
            return;
        }
        getNodeReferences().remove(id);
    }

    /**
     * enfernt eine Referenz
     *
     * @param nodeReference die Referenz
     */
    public void removeNodeReference(nodeReference nodeReference) {
        int id = getNodeReferences().indexOf(nodeReference);
        if (id >= 0) {
            getNodeReferences().remove(id);
        }
    }

    /**
     * enfernt einen Vater
     *
     * @param parent der Vater
     */
    public void removeParent(treeBucketNode parent) {
        getParents().remove(parent);
    }

    /**
     * enfernt einen Vater
     *
     * @param id die Position im Parent-Array
     */
    public void removeParent(int id) {
        getParents().remove(id);
    }

    /**
     * entfernt alle Verbindungen zu den Eltern dieses Knotens
     */
    public void removeParentEdges() {
        for (treeBucketNode parent : getParents()) {
            removeEdgeFrom(parent);
        }
    }

    /**
     * setzt die eindeutige ID zurück auf 0 (der Standardwert)
     */
    public void resetUniqueId() {
        setUniqueId(0);
    }

    /**
     * setzt das Attribut label auf value
     *
     * @param name  der Name des Attributs
     * @param value der neue Wert
     */
    public void setAttribute(String name, String value) {
        if (attributeExists(name)) {
            getAttributes().replace(name, value);
        } else {
            getAttributes().put(name, value);
        }
    }

    /**
     * hashCode() verwenden! (diese Methode führt kein rehash aus)
     *
     * @return the hash (sollte nicht verwendet werden)
     */
    public int getHash() {
        return hash;
    }

    /**
     * @param hash the hash to set (sollte nicht verwendet werden)
     */
    public void setHash(int hash) {
        this.hash = hash;
    }

    /**
     * @param uniqueId the uniqueId to set
     */
    public void setUniqueId(int uniqueId) {
        if (uniqueId < 0) {
            uniqueId = 0;
        }
        this.uniqueId = uniqueId;
    }

}
