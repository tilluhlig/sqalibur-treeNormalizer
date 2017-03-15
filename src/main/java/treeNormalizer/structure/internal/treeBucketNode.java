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

import treeNormalizer.structure.internal.nodeReference;
import treeNormalizer.structure.internal.tree;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.ArrayUtils;

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
    private int rawHash = 0; // Default to 0

    /**
     * Unter dieser Id (quasi der Hash) wird dieser Knoten derzeit verwaltet.
     * Die wird benötigt, falls der Knoten verändert wird und wir ihn neu
     * speichern müssen.
     */
    private int storeId = 0;

    /**
     * der Name des Knotens/Bezeichner (beispielsweise sowas wie "1" oder "+").
     * Man könnte das Feld auch content nennen.
     */
    private String label = "";

    /**
     * eine ID des realen Knotens, diese ID wird durch eine übergeordnete
     * Verwaltung (in einer HashMap) behandelt und soll zu der ID einen
     * entsprechenden Knoten liefern (diese id ist also global eindeutig)
     *
     * 0 = keine korrekte ID
     */
    private long id = 0;

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

    /*
     * dieses Flag gibt an, ob der Knoten verändert wurde und daher der Hash neu
     * berechnet werden muss
     */
    private boolean changedNode = true;

    /**
     * setzt den Knoten als verändert (also changedNode = true)
     */
    public void nodeChanged() {
        setChangedNode(true);
    }

    /**
     * erzeugt einen neuen Knoten
     */
    public treeBucketNode() {
        // Leer
    }

    /**
     * erzeugt einen neuen Knoten
     *
     * @param id    die ID des Knotens
     * @param label der Name
     */
    public treeBucketNode(long id, String label) {
        this.label = label;
        this.id = id;
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
     * @param id    die ID des Knotens
     * @param label das Label
     * @param type  der Typ (Klasse)
     */
    public treeBucketNode(long id, String label, String type) {
        this.label = label;
        this.type = type;
        this.id = id;
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
     * @param id         die ID des Knotens
     * @param label      das Label
     * @param type       der Typ (Klasse)
     * @param attributes die Attribute
     */
    public treeBucketNode(long id, String label, String type, Map<String, String> attributes) {
        this.label = label;
        this.type = type;
        this.attributes = attributes;
        this.id = id;
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
        this.getChilds().add(child);
        nodeChanged();
    }

    /**
     * fügt eine Menge von Kindern hinzu
     *
     * @param childs die neuen Kinder
     */
    public void addChilds(List<treeBucketNode> childs) {
        for (treeBucketNode child : childs) {
            this.getChilds().add(child);
        }
        nodeChanged();
    }

    /**
     * löscht alle Kindeinträge dieses Knotens
     */
    public void resetChilds() {
        childs.clear();
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
        this.addChild(targetNode);
        nodeChanged();
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
        cleanParents();
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
     * @param newId die KnotenId des dabei neu entstehenden Knoten
     * @return der neue Knoten
     */
    public treeBucketNode cloneNodeBase(long newId) {
        treeBucketNode tmp = new treeBucketNode(newId, getLabel(), getType(), getAttributes());
        return tmp;
    }

    /**
     * Erzeugt eine Kopie des Knotens (nur die Grunddaten) also: label, type,
     * attributes
     *
     * @return der neue Knoten
     */
    public treeBucketNode cloneNodeBase() {
        // 0 wird dabei als ungültige ID angesehen
        return cloneNodeBase(0);
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
            nodeChanged();
            return getUniqueId();
        }
        setUniqueId(getUniqueId() - 1);
        nodeChanged();
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
        nodeChanged();
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
     * liefert das Kind an der Position pos
     *
     * @param pos die Position
     * @return das Kind oder null
     */
    public treeBucketNode getChild(int pos) {
        if (pos >= 0 && pos < childs.size()) {
            return childs.get(pos);
        }
        return null;
    }

    /**
     * sucht ein Kind anhand des Knotens
     *
     * @param node der zu suchende Knoten
     * @return die Position oder -1 im Fehlerfall
     */
    public int findChild(treeBucketNode node) {
        if (node == null) {
            return -1;
        }
        for (int i = 0; i < childs.size(); i++) {
            if (childs.get(i) == null) {
                continue;
            }
            if (childs.get(i).getId() == node.getId()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * ermittelt alle Kinder des Knotens, welche node sind
     *
     * @param node der zu suchende Knoten
     * @return eine Liste der Kinderpositionen
     */
    public int[] findChilds(treeBucketNode node) {
        List<Integer> list = new ArrayList<>();
        if (node == null) {
            return new int[]{};
        }

        ArrayList<treeBucketNode> list2 = getChilds();
        for (int i = 0; i < list2.size(); i++) {
            if (list2.get(i) == null) {
                continue;
            }
            if (node.getId() == list2.get(i).getId()) {
                list.add(i);
            }
        }
        return ArrayUtils.toPrimitive(list.toArray(new Integer[list.size()]));
    }

    /**
     * setzt die Kinderliste(sollte nicht zum Verschieben oder Kopieren zwischen
     * zwei Knoten verwendet werden, dazu besser addChilds())
     *
     * @param childs die neuen Kinder
     */
    public void setChilds(ArrayList<treeBucketNode> childs) {
        if (childs == null) {
            childs = new ArrayList<>();
        }
        this.childs = childs;
        nodeChanged();
    }

    /**
     * setzt ein Kind an der Position i
     *
     * @param i        die existierende Kind-Position
     * @param newChild das neue Kind
     * @return
     */
    public boolean setChild(int i, treeBucketNode newChild) {
        if (i < 0 || i >= this.childs.size()) {
            return false;
        }

        if (newChild == null) {
            newChild = new treeBucketNode();
        }
        this.childs.set(i, newChild);
        nodeChanged();
        return true;
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

    /**
     * liefert den Vater an der Position pos
     *
     * @param pos die Position
     * @return der Vater oder null
     */
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
        nodeChanged();
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
        if (parents == null) {
            parents = new ArrayList<>();
        }
        this.parents = parents;
    }

    /**
     * entfernt alle Eltern aus der Elternliste
     */
    public void removeAllParents() {
        setParents(null);
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
        nodeChanged();
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
        if (isChangedNode()) {
            rehash();
        }
        return getRawHash();
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
            nodeChanged();
            return 0;
        }
        nodeChanged();
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
        // danach benötigen wir kein rehash mehr
        setChangedNode(false);

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
        setRawHash(tmpHash.hashCode());
    }

    /**
     * entfernt alle Attribute
     */
    public void removeAllAttributes() {
        getAttributes().clear();
        nodeChanged();
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
        nodeChanged();
    }

    /**
     * entfernt ein Kindverweis
     *
     * @param id die Position im Kind-Array
     */
    public void removeChild(int id) {
        getChilds().remove(id);
        nodeChanged();
    }

    /**
     * entfernt ein Kindverweis
     *
     * @param object das Kind
     */
    public void removeChild(treeBucketNode object) {
        getChilds().remove(object);
        nodeChanged();
    }

    /**
     * entfernt alle Verbindungen zu den Kindern dieses Knotens
     */
    public void removeChildEdges() {
        ArrayList<treeBucketNode> list = getChilds();
        for (int i = 0; i < list.size();) {
            treeBucketNode child = list.get(i);
            removeEdgeTo(child);
        }
        nodeChanged();
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
        nodeChanged();
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
        nodeChanged();
    }

    /**
     * setzt ein Kind auf null
     *
     * @param targetNode das betroffene Kind
     */
    public void unsetChild(int targetNode) {
        childs.set(targetNode, null);
        nodeChanged();
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
        ArrayList<treeBucketNode> list = getParents();
        for (int i = 0; i < list.size();) {
            treeBucketNode parent = list.get(i);
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
        nodeChanged();
    }

    /**
     * hashCode() verwenden! (diese Methode führt kein initiales rehash aus)
     *
     * @return the hash (sollte nicht verwendet werden)
     */
    public int getRawHash() {
        return rawHash;
    }

    /**
     * @param hash the hash to set (sollte nicht verwendet werden)
     */
    public void setRawHash(int hash) {
        this.rawHash = hash;
    }

    /**
     * @param uniqueId the uniqueId to set
     */
    public void setUniqueId(int uniqueId) {
        if (uniqueId < 0) {
            uniqueId = 0;
        }
        this.uniqueId = uniqueId;
        nodeChanged();
    }

    /**
     * prüft, ob der Knoten als verändert markiert wurde (er benötigt dann ein
     * rehash)
     *
     * @return the changedNode, true = wurde verändert, false = nicht verändert
     */
    public boolean isChangedNode() {
        return changedNode;
    }

    /**
     * setzt, ob der Knoten verändert wurde oder nicht manuell
     *
     * @param changedNode the changedNode to set (true = veränderter Knoten,
     *                    false = unveränder)
     */
    public void setChangedNode(boolean changedNode) {
        this.changedNode = changedNode;
    }

    /**
     * liefert die storeId (den Speicherort)
     *
     * @return the storeId die derzeitige Id
     */
    public int getStoreId() {
        return storeId;
    }

    /**
     * setzt die storeId (der derzeitige Speicherort)
     *
     * @param storeId setzt den neuen Speicherort
     */
    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    /**
     * setzt die storeId auf den aktuellen Hash
     */
    public void updateStoreId() {
        this.storeId = getRawHash();
    }

    /**
     * liefert die ID dieses Knotens zurück
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * setzt die ID des Knotens
     *
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * prüft, ob die ID gültig ist (also ungleich 0)
     *
     * @return true = hat eine gütige ID, false = nicht
     */
    public boolean hasValidId() {
        return this.id != 0;
    }

}
