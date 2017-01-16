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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Till
 */
public class treeBucketNode {

    /**
     * die Attributliste (falls zusätzliche Eigenschaften an den Knoten gebunden
     * werden sollen)
     */
    private Map<String, String> attributes = new HashMap<>();

    /**
     * die Kindknoten
     */
    private ArrayList<treeBucketNode> childs = new ArrayList<>();

    /**
     * der Hashwert
     */
    private int hash = 0; // Default to 0

    /**
     * der Name des Knotens (beispielsweise sowas wie "1" oder "+")
     */
    private String name = "";

    /**
     * Diese List enthält die Knotenreferenzen, welche auf diesen Knoten zeigen.
     * denn ein Knoten kann von mehreren Pfaden genutzt werden
     */
    private ArrayList<nodeReference> nodeReferences = new ArrayList<>();

    /**
     * die Elternknoten
     */
    private ArrayList<treeBucketNode> parents = new ArrayList<>();

    /**
     * die klassifizierung des Knotens (sowas wie binOperator oder const)
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

    }

    /**
     * erzeugt einen neuen Knoten
     *
     * @param name der Name
     */
    public treeBucketNode(String name) {
        this.name = name;
    }

    /**
     * erzeugt einen neuen Knoten
     *
     * @param name der Name
     * @param type der Typ (Klasse)
     */
    public treeBucketNode(String name, String type) {
        this.name = name;
        this.type = type;
    }

    /**
     * erzeugt einen neuen Knoten
     *
     * @param name       der Name
     * @param type       der Typ (Klasse)
     * @param attributes die Attribute
     */
    public treeBucketNode(String name, String type, Map<String, String> attributes) {
        this.name = name;
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
        // ein Kind soll nicht mehrfach existieren
        if (childs.contains(child)) {
            //throw new IllegalArgumentException("das Kind existiert bereits");
            return;
        }
        childs.add(child);
    }

    /**
     * fügt eine Kante, von einem Quellknoten zu diesem Knoten, ein
     *
     * @param sourceNode der Quellknoten
     * @return true = erfolgreich, false nicht erfolgreich
     */
    public boolean addEdgeFrom(treeBucketNode sourceNode) {
        return sourceNode.addEdgeTo(this);
    }

    /**
     * fügt eine Kante zu dem Zielknoten ein
     *
     * @param targetNode der Zielknoten
     * @return true = erfolgreich, false = nicht erfolgreich
     */
    public boolean addEdgeTo(treeBucketNode targetNode) {
        targetNode.addParent(this);
        addChild(targetNode);
        return true;
    }

    /**
     * fügt eine Knotenreferenz hinzu
     *
     * @param nodeReference die neue Referenz
     */
    public void addNodeReference(nodeReference nodeReference) {
        if (!nodeReferences.contains(nodeReference)) {
            nodeReferences.add(nodeReference);
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
     * fügt einen Elternknoten hinzu
     *
     * @param parent der neue Elternteil
     */
    public void addParent(treeBucketNode parent) {
        // ein Vaterknoten soll nicht mehrfach existieren
        if (parents.contains(parent)) {
            //throw new IllegalArgumentException("das Kind existiert bereits");
            return;
        }
        parents.add(parent);
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
        for (treeBucketNode parent : parents) {
            if (!newParents.contains(parent)) {
                newParents.add(parent);
            }
        }
        parents = newParents;
    }

    /**
     * Erzeugt eine Kopie des Knotens (nur die Grunddaten) also: name, type,
     * attributes
     *
     * @return der neue Knoten
     */
    public treeBucketNode cloneNodeBase() {
        treeBucketNode tmp = new treeBucketNode(name, type, attributes);
        return tmp;
    }

    /**
     * prüft, ob der Baum in Referenzenliste auftaucht
     *
     * @param tree der Baum
     * @return ob der Baum in der Liste auftaucht
     */
    public boolean containsReferencedTree(tree tree) {
        for (nodeReference tmp : nodeReferences) {
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
     */
    public void decreaseUniqueId() {
        uniqueId--;
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
        return this.hash == other.hash;
    }

    /**
     * liefert den Wert eines Attributs
     *
     * @param name der Name
     * @return der Wert
     */
    public String getAttribute(String name) {
        return attributes.get(name);
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

    /**
     * setzt die Kinderliste
     *
     * @param childs die neuen Kinder
     */
    public void setChilds(ArrayList<treeBucketNode> childs) {
        this.childs = childs;
    }

    /**
     * liefert den ersten Elternknoten
     *
     * @return der erste Elternknoten
     */
    public treeBucketNode getFirstParent() {
        if (hasParents()) {
            return parents.get(0);
        }
        return null;
    }

    /**
     * liefert den Namen des Knotens
     *
     * @return der Name
     */
    public String getName() {
        return name;
    }

    /**
     * setzt den Namen des Knotens
     *
     * @param name der neue Name
     */
    public void setName(String name) {
        this.name = name;
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
        for (nodeReference ref : nodeReferences) {
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
        if (hash == 0) {
            rehash();
        }
        return hash;
    }

    /**
     * gibt die Anzahl der eingehenden Kanten des Knotens
     *
     * @return
     */
    public int inDegree() {
        // obwohl es hier irgendwie um Bäume geht, kann der Eingangsgrad > 1
        // sein, weil treeBucketNode sich selbst nicht als Baum betrachtet (ein Knoten
        // kann hier mehrere Eltern haben)
        return getParents().size();
    }

    /**
     * erhöht den Wert der uniqueID um 1
     */
    public void increaseUniqueId() {
        uniqueId++;
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
        return childs.isEmpty();
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
        return nodeReferences.size();
    }

    /**
     * liefert die Anzahl der Elternknoten
     *
     * @return die Anzahl der Eltern
     */
    public int numberOfParents() {
        return parents.size();
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
        String tmp = getName() + "[" + getType() + "] #" + hashCode() + "\n";

        if (hasParents()) {
            tmp += "parents: ";
            ArrayList<String> par = new ArrayList<>();
            getParents().forEach((parent) -> {
                par.add(parent.getName() + " #" + parent.hashCode());
            });
            tmp += String.join(", ", par);
            tmp += "\n";
        }

        if (hasChilds()) {
            tmp += "childs: ";
            ArrayList<String> par = new ArrayList<>();
            getChilds().forEach((child) -> {
                par.add(child.getName() + " #" + child.hashCode());
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
        // name, type, children, attributes
        String tmpHash = getName() + "_" + uniqueId + "_" + getType();
        for (treeBucketNode child : getChilds()) {
            tmpHash += "_" + child.hashCode();
        }
        for (Map.Entry<String, String> attribute : getAttributes().entrySet()) {
            tmpHash += "." + attribute.getKey() + "=" + attribute.getValue();
        }
        hash = tmpHash.hashCode();
    }

    /**
     * entfernt alle Attribute
     */
    public void removeAllAttributes() {
        attributes.clear();
    }

    /**
     * entfernt alle Knotenreferenzen
     */
    public void removeAllNodeReferences() {
        nodeReferences.clear();
    }

    /**
     * entfernt einen Attributeintrag
     *
     * @param name der Name
     */
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    /**
     * entfernt ein Kindverweis
     *
     * @param id die Position im Kind-Array
     */
    public void removeChild(int id) {
        childs.remove(id);
    }

    /**
     * entfernt ein Kindverweis
     *
     * @param object das Kind
     */
    public void removeChild(treeBucketNode object) {
        childs.remove(object);
    }

    /**
     * entfernt alle Verbindungen zu den Kindern dieses Knotens
     */
    public void removeChildEdges() {
        for (treeBucketNode child : childs) {
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
     * enfernt eine Referenz
     *
     * @param id die Referenz-ID der Referenz
     */
    public void removeNodeReference(int id) {
        nodeReferences.remove(id);
    }

    /**
     * enfernt eine Referenz
     *
     * @param nodeReference die Referenz
     */
    public void removeNodeReference(nodeReference nodeReference) {
        int id = nodeReferences.indexOf(nodeReference);
        if (id >= 0){
            nodeReferences.remove(id);        
        }
    }

    /**
     * enfernt einen Vater
     *
     * @param parent der Vater
     */
    public void removeParent(treeBucketNode parent) {
        parents.remove(parent);
    }

    /**
     * enfernt einen Vater
     *
     * @param id die Position im Parent-Array
     */
    public void removeParent(int id) {
        parents.remove(id);
    }

    /**
     * entfernt alle Verbindungen zu den Eltern dieses Knotens
     */
    public void removeParentEdges() {
        for (treeBucketNode parent : parents) {
            removeEdgeFrom(parent);
        }
    }

    /**
     * setzt die eindeutige ID zurück auf 0 (der Standardwert)
     */
    public void resetUniqueId() {
        uniqueId = 0;
    }

    /**
     * setzt das Attribut name auf value
     *
     * @param name  der Name des Attributs
     * @param value der neue Wert
     */
    public void setAttribute(String name, String value) {
        if (attributes.containsKey(name)) {
            attributes.replace(name, value);
        } else {
            attributes.put(name, value);
        }
    }

}
