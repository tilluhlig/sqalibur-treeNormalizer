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

import treeNormalizer.structure.internal.treeBucketNode;
import treeNormalizer.structure.internal.nodeReference;
import treeNormalizer.structure.internal.internalTree;
import treeNormalizer.structure.internal.internalEdge;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.commons.lang.StringUtils;
import treeNormalizer.utils.UID;

/**
 * Diese Klasse verwaltet eine Menge von Bäumen, möglichst kompakt.
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class treeBucket {

    /*
     * hier wird die Position vermerkt, an der er mit der Vergabe der IDs für
     * Referenzen weitermachen soll. Wenn beispielsweise eine ID gelöscht wird,
     * soll er vielleicht dort erstmal anfragen.
     */
    private int possibleNextReferenceId = 1;

    /*
     * erzeugt für uns eindeutige IDs
     */
    private UID UID_object = null;

    /**
     * diese Liste von Knotenreferenzen bilden auf die realen Knoten ab
     */
    private final Map<Integer, treeBucketNode> nodeReference = new HashMap<>();

    /**
     * diese Sammlung enthält die Teilgraphen (also die realen Knoten anhand
     * deren Hash)
     */
    private final Map<Integer, treeBucketNode> nodes = new HashMap<>();

    /**
     * enthält alle Zeiger auf die Wurzeln der Bäume
     */
    private final ArrayList<internalTree> trees = new ArrayList<>();

    /**
     * initialisiert einen Baumbehälter
     */
    public treeBucket() {
        this.UID_object = new UID();
    }

    /**
     * fügt einen Knoten der Verwaltung hinzu
     *
     * @param tree der Zielbaum
     * @param node der Knoten
     * @return die eindeutige Knotenreferenz auf diesen Knoten bzw. den Inhalt
     *         den er darstellt
     */
    private nodeReference addNode(internalTree tree, treeBucketNode node) {
        node = addNodeSimple(node);
        return createNodeReference(tree, node);
    }

    /**
     * fügt einen Knoten in die Verwaltung ein, wenn der Knoten so schon
     * existiert, dann wird der bestehende Knoten mit diesem verschmolzen
     *
     * @param node der einzufügende Knoten
     * @return der übergebene Knoten oder ein bereits vorhandener Knoten
     */
    private treeBucketNode addNodeSimple(treeBucketNode node) {
        if (nodes.containsKey(node.hashCode())) {
            // der Knoten existiert bereits
            treeBucketNode localNode = nodes.get(node.hashCode());
            mergeNode(localNode, node);
            node = localNode;
            node.updateStoreId();
        } else {
            // der Knoten existiert so noch nicht
            nodes.put(node.hashCode(), node);
            node.updateStoreId();
        }
        return node;
    }

    /**
     * fügt einen Baum in die Verwaltung ein
     *
     * @param tree der Baum
     * @return der Baum oder null, wenn nichts eingefügt werden konnte
     */
    private internalTree addTree(internalTree tree) {
        // der Name des Baums soll eindeutig sein
        if (getTreeByName(tree.getName()) != null) {
            // der Baum existiert bereits
            return null;
        }

        trees.add(tree);
        return tree;
    }

    /**
     * erzeugt eine neue Referenz
     *
     * @param tree der Baum
     * @param node der Knoten
     * @return die neue Referenz
     */
    private nodeReference createNodeReference(internalTree tree, treeBucketNode node) {
        int newId = getNextReferenceId();
        nodeReference tmp = new nodeReference(tree, newId);
        nodeReference.put(newId, node);
        node.addNodeReference(tmp);
        return tmp;
    }

    /**
     * liefert die nächste eindeutige Id für eine neue Referenz
     *
     * @return die neue ReferenzId
     */
    private int getNextReferenceId() {
        if (nodeReference.containsKey(possibleNextReferenceId)) {
            possibleNextReferenceId = random.nextNonZero();
            while (nodeReference.containsKey(increaseToNextPossibleReferenceId())) {
                // kann endlos laufen
            }
        }
        int a = possibleNextReferenceId;
        increaseToNextPossibleReferenceId();
        return a;
    }

    /**
     * liefert die nächste eindeutige Id einen treeBucketNode
     *
     * @return die neue KnotenId
     */
    private long getNextNodeId() {
        return this.UID_object.nextUID();
    }

    /**
     * liefert alle internen Knoten
     *
     * @return die Knoten
     */
    private Map<Integer, treeBucketNode> getInternalNodes() {
        return nodes;
    }

    /**
     * sorgt dafür, das ein Knoten aufgenommen werden kann, ohne das er mit
     * einem anderen verschmelzt wird
     *
     * @param node der Knoten
     * @return der angepasst (eindeutige) Knoten
     */
    private treeBucketNode makeNodeUnique(treeBucketNode node) {
        // wenn wir viele dieser Knoten nacheinander unique machen wollen,
        // dann möchte ich nicht immer bei 0 beginnen müssen
        node.setUniqueId(random.nextPositive());

        while (nodeExists(node)) {
            node.increaseUniqueId();
        }
        return node;
    }

    /**
     * vereint zwei Graphen miteinander, wobei targetNode den Graphen sourceNode
     * aufnimmt
     *
     * @param targetNode der Zielknoten
     * @param sourceNode der Quellknoten
     */
    private void mergeNode(treeBucketNode targetNode, treeBucketNode sourceNode) {
        // der Zielknoten erhält nun die Eltern vom Quellknoten
        targetNode.addParents(sourceNode.getParents());
        targetNode.cleanParents(); // doppelte Eltern werden entfernt
        // die bisherigen Eltern des Quellknoten erhalten nun den Zielknoten
        // als Kind
        sourceNode.getParents().forEach((parent) -> {
            int sourceid = parent.findChild(sourceNode);
            parent.setChild(sourceid, targetNode);
        });

        // der Zielknoten nimmt nun die Referenzen des Quellknoten auf
        targetNode.addNodeReferences(sourceNode.getNodeReferences());

        // setzt alle alten Referenzen auf den neuen Knoten (in der Verwaltung)
        sourceNode.getNodeReferences().forEach((ref) -> {
            nodeReference.replace(ref.getId(), targetNode);
        });

        // die Kinder der beiden müssen auch verschmolzen werden
        if (targetNode.hasChilds()) {
            // der Graph ist noch nicht unten angekommen
            ArrayList<treeBucketNode> targetChilds = targetNode.getChilds();
            ArrayList<treeBucketNode> sourceChilds = sourceNode.getChilds();
            for (int i = 0; i < targetChilds.size(); i++) {
                if (targetChilds.get(i) != sourceChilds.get(i)) {
                    mergeNode(targetChilds.get(i), sourceChilds.get(i));
                } else {
                    // die beiden Kinder sind bereits verschmolzen, sodass nun
                    // doppelte Eltern entstehen
                    //targetChilds.get(i).cleanParents();
                    targetChilds.get(i).removeParent(sourceNode);
                }
            }
        }
        // source node muss noch entfernt werden
    }

    /**
     * prüft, ob ein Knoten in dieser Form bereits existiert
     *
     * @param node der zu prüfende Knoten
     * @return true = Knoten existiert bereits, false = sonst
     */
    private boolean nodeExists(treeBucketNode node) {
        return nodes.containsKey(node.hashCode());
    }

    /**
     * prüft, ob eine Knotenreferenz bereits in der Verwaltung existiert
     *
     * @param node die zu prüfende Referenz
     * @return true = vorhanden, false = nicht vorhanden
     */
    public boolean referenceExists(nodeReference node) {
        if (node.getId() == 0) {
            return false;
        }
        return nodeReference.containsKey(node.getId());
    }

    /**
     * aktualisiert einen Knoten und den Elternpfad
     *
     * @param node der Knoten
     */
    private void propagadeNode(treeBucketNode node) {
        // nun wird der neue Hash berechnet
        node.resetUniqueId();
        node.rehash(); // damit werden auch die Kinder rehasht

        // wenn sich der Hash geändert hat, müssen wir handeln
        if (node.getStoreId() != node.hashCode()) {
            // entferne den Knoten aus der Verwaltung
            nodes.remove(node.getStoreId());

            // nun den Knoten wieder neu einfügen, wobei der Knoten eventuell
            // mit einem anderen verschmolzen wird
            node = addNodeSimple(node);

            // eventuell haben sich die Elternknoten auch geändert
            for (treeBucketNode parent : node.getParents()) {
                propagadeNode(parent);
            }
        }
    }

    /**
     * aktualisiert einen Knoten und den Elternpfad
     *
     * @param node der Knoten
     */
    private void propagadeNode(nodeReference node) {
        treeBucketNode tmp = getInternalNodeByReference(node);

        if (tmp == null) {
            // die Referenz existiert nicht
            return;
        }
        propagadeNode(tmp);
    }

    /**
     * entfernt einen interen Knoten (also den konkreten Knoten)
     *
     * @param node der Knoten
     */
    private void removeInternalNode(treeBucketNode node) {
        ArrayList<treeBucketNode> parents = node.getParents();
        node.disconnect();

        parents.forEach((parent) -> {
            propagadeNode(parent);
        });

        nodes.remove(node.hashCode());
    }

    /**
     * entfernt eine Knotenreferenz
     *
     * @param node der Knoten
     */
    private void removeNodeReference(nodeReference node) {
        node.disconnect();
        nodeReference.remove(node.getId());
        possibleNextReferenceId = node.getId();
        node.setId(0); // quasi ein zurücksetzen der ID
    }

    private treeBucketNode getParent(nodeReference node) {
        if (!node.hasParent()) {
            return null;
        }
        return getInternalNodeByReference(node.getParent());
    }

    /**
     * spaltet eine Kante ab und macht daraus einen eigenständigen Arm diese
     * Operation bereitet eine Änderung an einem Knoten vor, wobei immer ein
     * propagade() folgen muss, um den neuen Arm korrekt in die Verwaltung zu
     * integrieren
     *
     * @param node der Knoten
     * @return der neue Knoten
     */
    private treeBucketNode splitNode(nodeReference node) {
        // der reale Knoten wird nun ermittelt
        treeBucketNode realNode = getInternalNodeByReference(node);

        // wenn der referenzierte Knoten alleine in diesem Knoten ist,
        // muss er nicht aufgespalten werden
        if (realNode.getNodeReferences().size() <= 1) { // obwohl 0 nicht möglich ist
            // damit gab es auch keine weitere Anpassung
            return realNode;
        }

        // nun wird die Referenz aus dem Knoten entfernt
        realNode.removeNodeReference(node);

        // um Fehler zu vermeiden, setzen wir die Referenz vorübergehend auf
        // etwas Ungültiges
        nodeReference.replace(node.getId(), null);

        // jetzt muss ein neuer Knoten erzeugt werden (eine Kopie)
        // (aber ohne Kinder und Eltern)
        long nextId = getNextNodeId();
        treeBucketNode splittedNode = realNode.cloneNodeBase(nextId);
        splittedNode.addNodeReference(node);

        // der neue Knoten soll eindeutig sein, damit er beim Einfügen nicht
        // mit einem anderen vereint wird
        splittedNode = makeNodeUnique(splittedNode);
        addNodeSimple(splittedNode); // fügt den Knoten ein

        // die Knotenreferenz soll nun auf einen neuen realen Knoten zeigen
        nodeReference.replace(node.getId(), splittedNode);

        // der neue Knoten bekommt die Kinder des ursprünglichen Knotens
        // (darunter bleibt ja alles gleich)
        splittedNode.addChilds(realNode.getChilds());

        // nun muss der Pfad über die Eltern bis zur Wurzel des Baums
        // aufgespalten werden
        if (node.hasParent()) {
            // möglicherweise gibt es noch Elternknoten des Baums
            nodeReference parent = node.getParent();

            // die Eltern werden ebenfalls in den neuen Baum überführt
            treeBucketNode realParent = getInternalNodeByReference(parent);

            int myChildPos = realParent.findChild(realNode);

            if (myChildPos == -1) {
                // das ist ein Problem
            }

            treeBucketNode splittedParent = splitNode(parent);
            splittedNode.addParent(splittedParent);

            // ich muss mich bei meinem Vater noch als Kind korrekt setzen
            splittedParent.setChild(myChildPos, splittedNode);
        }

        return splittedNode;
    }

    /**
     * aktualisiert eine Knotenreferenz
     *
     * @param id   die ID der Referenz
     * @param node der neue Zielknoten
     */
    private void updateNodeReference(int id, treeBucketNode node) {
        nodeReference.replace(id, node);
    }

    /**
     * aktualisiert eine Knotenreferenz
     *
     * @param nodeReference die Referenz
     * @param node          der neue Zielknoten
     */
    private void updateNodeReference(nodeReference nodeReference, treeBucketNode node) {
        this.nodeReference.replace(nodeReference.getId(), node);
    }

    /**
     * liefert die Anzahl der real existierenden Knoten
     *
     * @return die Anzahl
     */
    public int getNumberOfInternalNodes() {
        return nodes.size();
    }

    /**
     * fügt eine Kante ein
     *
     * @param referenceA der Startknoten
     * @param referenceB der Zielknoten
     */
    public void addEdge(reference referenceA, reference referenceB) {
        nodeReference nodeA = (nodeReference) referenceA;
        nodeReference nodeB = (nodeReference) referenceB;
        treeBucketNode nA = splitNode(nodeA);
        treeBucketNode nB = getInternalNodeByReference(nodeB);

        if (nA == null) {
            throw new NullPointerException("der Knoten A existiert nicht");
        }

        if (nB == null) {
            throw new NullPointerException("der Knoten B existiert nicht");
        }

        if (nodeB.hasParent()) {
            throw new IllegalArgumentException("der Knoten hat bereits einen Vater");
        }

        if (!nodeA.getTree().equals(nodeB.getTree())) {
            throw new IllegalArgumentException("die Knoten gehören nicht zum gleichen Baum");
        }

        if (nodeA.getTreeRoot().equals(nodeB)) {
            throw new IllegalArgumentException("es darf keine Kante zur Wurzel gezogen werden");
        }

        // jetzt werden die zwei miteinander verbunden, dabei werden die
        // Referenzen und die realen Knoten verknüpft
        nodeA.addChild(nodeB);
        nodeB.setParent(nodeA);
        nA.addEdgeTo(nB);

        // nodeA hat sich verändert
        propagadeNode(nodeA);
    }

    /**
     * fügt eine Kante ein
     *
     * @param edge die Definition der Kante
     */
    public void addEdge(internalEdge edge) {
        addEdge(edge.getSource(), edge.getTarget());
    }

    /**
     * fügt einen Knoten in einen Baum ein
     *
     * @param tree der Baum
     * @param name der Name des Knotens
     * @return die Referenz auf den Knoten
     */
    public nodeReference createNode(internalTree tree, String name) {
        return createNode(getNextNodeId(), tree, name, "");
    }

    /**
     * fügt einen Knoten in einen Baum ein
     *
     * @param tree der Baum
     * @param name der Name des Knotens
     * @param type der Typ des Knotens
     * @return die Referenz auf den Knoten
     */
    public nodeReference createNode(internalTree tree, String name, String type) {
        return createNode(getNextNodeId(), tree, name, type);
    }

    /**
     * fügt einen Knoten in einen Baum ein
     *
     * @param newId die neue ID des Knotens (normalerweise automatisch gesetzt)
     *              (wir vorallem für Tests genutzt)
     * @param tree  der Baum
     * @param name  der Name des Knotens
     * @param type  der Typ des Knotens
     * @return die Referenz auf den Knoten
     */
    private nodeReference createNode(long newId, internalTree tree, String name, String type) {
        treeBucketNode tmp = new treeBucketNode(newId, name, type);
        return addNode(tree, tmp);

    }

    /**
     * erzeugt einen neuen Baum
     *
     * @param name der Name
     * @return der neue Baum
     */
    public internalTree createTree(String name) {
        internalTree tmp = new internalTree(name);
        tmp = addTree(tmp);
        return tmp;
    }

    /**
     * liefert zu einer Knotenreferenz den internen Knoten
     *
     * @param node die Referenz
     * @return der Knoten
     */
    private treeBucketNode getInternalNodeByReference(nodeReference node) {
        return nodeReference.get(node.getId());
    }

    /**
     * liefert zu der Referenz einen Knoten, welcher für eine Änderung
     * vorbereitet wurde (er muss danach mit propagade wieder normal in die
     * Verwaltung übernommen werden)
     *
     * @param node der Knoten
     * @return der änderbare Knoten
     */
    private treeBucketNode getPreparedNode(nodeReference node) {
        treeBucketNode realNode = getInternalNodeByReference(node);

        if (realNode.numberOfNodeReferences() == 1) {
            return realNode;
        } else if (realNode.numberOfNodeReferences() > 1) {
            return splitNode(node);
        }

        /*
         * Referenzen: 0 der Knoten wird nicht genutzt, dieser Fall sollte
         * niemals eintreten
         */
        return null;
    }

    /**
     * ermittelt einen Baum anhand seines Namens
     *
     * @param name der Name
     * @return der Baum
     */
    public internalTree getTreeByName(String name) {
        for (internalTree tmp : getTrees()) {
            if (name.equals(tmp.getName())) {
                return tmp;
            }
        }
        return null;
    }

    /**
     * setzt den Wurzelknoten des Baums
     *
     * @param root der Wurzelknoten
     */
    public void setTreeRoot(reference root) {
        internalTree tree = root.getTree();

        // wenn root nicht neu ist, muss auch nichts gemacht werden
        if (tree.getRoot() == root) {
            return;
        }

        // nun kann die neue Wurzel gesetzt werden
        tree.setRoot((nodeReference) root);
    }

    /**
     * liefert alle Bäume des Behälters
     *
     * @return die Bäume
     */
    public ArrayList<internalTree> getTrees() {
        return trees;
    }

    /**
     * prüft, ob die beiden Bäume gleich sind (also auf die gleiche Wurzel
     * verweisen)
     *
     * @param treeA der erste Baum
     * @param treeB der zweite Baum
     * @return true = sind gleich, false = sind nicht gleich
     */
    public boolean isTreeEquivalentTo(tree treeA, tree treeB) {
        internalTree realA = (internalTree) treeA;
        internalTree realB = (internalTree) treeB;

        if (realA.equals(realB)) {
            return true;
        }

        if (realA.getRoot().equals(realB.getRoot())) {
            return true;
        }

        if (realA.getRoot() == null) {
            return false;
        }

        if (realB.getRoot() == null) {
            return false;
        }

        treeBucketNode nodeA = getInternalNodeByReference(realA.getRoot());
        treeBucketNode nodeB = getInternalNodeByReference(realB.getRoot());

        return nodeA.getId() == nodeB.getId();
    }

    /**
     * prüft, ob die beiden Bäume gleich sind (also auf die gleiche Wurzel
     * verweisen)
     *
     * @param nodeA ein Knoten des ersten Baums
     * @param nodeB ein Knoten des zweiten Baums
     * @return true = sind gleich, false = sind nicht gleich
     */
    public boolean isTreeEquivalentTo(reference nodeA, reference nodeB) {
        return isTreeEquivalentTo(nodeA.getTree(), nodeB.getTree());
    }

    /**
     * liefert eine Textdarstellung des Baumbehälters
     *
     * @return die Textdarstellung
     */
    public String print() {
        String tmp = "";
        tmp += "Knoten: " + nodes.size() + "\n";
        for (Map.Entry<Integer, treeBucketNode> nodeEntry : nodes.entrySet()) {
            tmp += "\n";
            tmp += nodeEntry.getValue().print();
        }

        return tmp;
    }

    /**
     * druckt eine Darstellung der realen Knoten, wobei nur deren interen
     * Knotennummern aufgelistet werden, mit Kindern
     *
     * @return die Textdarstellung der Struktur der realen Knoten
     */
    public String simplePrint() {
        String tmp = "";
        for (Map.Entry<Integer, treeBucketNode> nodeEntry : nodes.entrySet()) {
            treeBucketNode a = nodeEntry.getValue();
            List<String> collect = new ArrayList<>();
            a.getChilds().forEach((b) -> {
                collect.add(String.valueOf(b.getId()));
            });
            tmp += "{" + a.getId() + "[" + StringUtils.join(collect, ",") + "]}";
        }
        return tmp;
    }

    /**
     * entfernt eine Kante
     *
     * @param refA der Startknoten
     * @param refB der Zielknoten
     */
    public void removeEdge(reference refA, reference refB) {
        nodeReference nodeA = (nodeReference) refA;
        nodeReference nodeB = (nodeReference) refB;
        // dazu muss die Kante aus den Referenzen entfernt werden
        nodeA.removeEdgeTo(nodeB);

        // der Knoten nodeA hat sich nun verändert
        propagadeNode(nodeA);
    }

    /**
     * entfernt eine Kante
     *
     * @param edge die Kante
     */
    public void removeEdge(edge edge) {
        removeEdge(edge.getSource(), edge.getTarget());
    }

    /**
     * entfernt eine Knotenreferenz sauber aus der Struktur/Verwaltung
     *
     * @param ref der Knoten
     */
    public void removeNode(reference ref) {
        nodeReference node = (nodeReference) ref;
        treeBucketNode realNode = getInternalNodeByReference(node);

        if (realNode.numberOfNodeReferences() == 1) {
            // der Knoten wird von niemandem mehr genutzt, also kann er
            // entfernt werden
            removeNodeReference(node);
            removeInternalNode(realNode);

            nodeReference parent = node.getParent();
            if (parent != null) {
                parent.removeEdgeTo(node);
                treeBucketNode parentNode = getInternalNodeByReference(parent);
                propagadeNode(parentNode);
            }
        } else if (realNode.numberOfNodeReferences() > 1) {
            // der Knoten wird noch von anderen Referenzen genutzt
            nodeReference parent = node.getParent();
            removeNodeReference(node);

            if (parent != null) {
                parent.removeEdgeTo(node);

                // es existierte ein Vater
                treeBucketNode parentNode = getInternalNodeByReference(parent);

                if (parentNode.numberOfNodeReferences() > 1) {
                    // der Vater benötigt einen eigenen Zweig, denn eine andere
                    // Referenz nutzt diesen Knoten weiterhin
                    treeBucketNode splittedParent = splitNode(parent);
                    propagadeNode(splittedParent); // TODO: stimmt das so?
                }
            }
        } else {
            // der Knoten ist bereits leer und diese Situation ist unlogisch
        }
    }

    /**
     * entfernt einen Knoten und dessen Untergraph
     *
     * @param node der Startknoten
     */
    public void removeSubtree(reference node) {
        // dazu wandern wir zunächst Rekursiv bis zu den Kindern und beginnen
        // dort mit dem Löschen
        node.getExistingChilds().forEach((child) -> {
            removeSubtree(child);
        });

        // wenn die Kinder dieses Knotens weg sind, dann können wir diesen
        // Knoten löschen (erst ist dann also ein Blatt)
        removeNode(node);
    }

    /**
     * entfernt einen kompletten Baum aus der Verwaltung
     *
     * @param tree der zu entfernende Baum
     */
    public void removeTree(tree tree) {
        internalTree realTree = (internalTree) tree;
        nodeReference root = realTree.getRoot();
        if (root != null) {
            // der Baum bestitzt eine Wurzel
            removeSubtree(root);
        }
        trees.remove(realTree);
    }

    /**
     * benennt einen Knoten um (der Knoten wir anschließend automatisch in der
     * Verwaltung aktualisiert)
     *
     * @param ref     der Knoten
     * @param newName der neue Name
     */
    public void renameNode(reference ref, String newName) {
        nodeReference node = (nodeReference) ref;
        // der Knoten muss eventuell aufgespalten werden
        treeBucketNode tmp = getInternalNodeByReference(node);

        if (newName.equals(tmp.getLabel())) {
            // der Name hat sich nicht geändert
            return;
        }

        treeBucketNode preparedNode = getPreparedNode(node);
        preparedNode.setLabel(newName);
        propagadeNode(preparedNode);
    }

    /**
     * ändert den Typbezeichner eines Knotens (der Knoten wir anschließend
     * automatisch in der Verwaltung aktualisiert)
     *
     * @param ref     die Referenz auf den Knoten
     * @param newType der neue Bezeichner
     */
    public void changeNodeType(reference ref, String newType) {
        nodeReference node = (nodeReference) ref;
        // der Knoten muss eventuell aufgespalten werden
        treeBucketNode tmp = getInternalNodeByReference(node);

        if (newType.equals(tmp.getType())) {
            // der Name hat sich nicht geändert
            return;
        }

        treeBucketNode preparedNode = getPreparedNode(node);
        preparedNode.setType(newType);
        propagadeNode(preparedNode);
    }

    public void setAttribute(reference ref, String name, String value) {
        nodeReference node = (nodeReference) ref;
        // der Knoten muss eventuell aufgespalten werden
        treeBucketNode tmp = getInternalNodeByReference(node);

        if (tmp.attributeExists(name)) {
            if (tmp.getAttribute(name).equals(value)) {
                // der Wert existiert so bereits
                return;
            }
        }

        treeBucketNode preparedNode = getPreparedNode(node);
        preparedNode.setAttribute(name, value);
        propagadeNode(preparedNode);
    }

    public String getAttribute(reference ref, String name) {
        nodeReference node = (nodeReference) ref;
        treeBucketNode tmp = getInternalNodeByReference(node);
        return tmp.getAttribute(name);
    }

    public boolean attributeExists(reference ref, String name) {
        nodeReference node = (nodeReference) ref;
        treeBucketNode tmp = getInternalNodeByReference(node);
        return tmp.attributeExists(name);
    }

    public void removeAttribute(reference ref, String name) {
        nodeReference node = (nodeReference) ref;
        // der Knoten muss eventuell aufgespalten werden
        treeBucketNode tmp = getInternalNodeByReference(node);

        if (!tmp.attributeExists(name)) {
            // das Attribut existiert nicht, also muss es nicht entfernt werden
            return;
        }

        treeBucketNode preparedNode = getPreparedNode(node);
        preparedNode.removeAttribute(name);
        propagadeNode(preparedNode);
    }

    /**
     * benennt einen Knoten um der Baum ist hierbei eigentlich nicht notwendig
     *
     * @param tree der Baum
     * @param node der Knoten
     * @param name der neue Name
     */
    public void renameNode(tree tree, reference node, String name) {
        if (node.getTree() != tree) {
            throw new IllegalArgumentException("die beiden Elemente gehören nicht zueinander");
        }
        renameNode(node, name);
    }

    /**
     * benennt einen Baum um
     *
     * @param tree der Baum
     * @param name der neue Name
     * @return true = Umbenennung war erfolgreich, false = sonst
     */
    public boolean renameTree(tree tree, String name) {
        internalTree realTree = (internalTree) tree;
        if (name == null) {
            return false;
        }

        if (realTree.getName().equals(name)) {
            return false;
        }

        // der Name eines Baums soll eindeutig sein
        if (getTreeByName(name) != null) {
            return false;
        }

        realTree.setName(name);
        return true;
    }

    /**
     * benennt einen Baum um
     *
     * @param node ein Knoten, welcher zum Baum gehört
     * @param name der neue Name
     * @return true = Umbenennung war erfolgreich, false = sonst
     */
    public boolean renameTree(reference node, String name) {
        return renameTree(node.getTree(), name);
    }

    /**
     * benennt einen Baum um
     *
     * @param edge eine Kante des Baums
     * @param name der neue Name
     * @return true = Umbenennung war erfolgreich, false = sonst
     */
    public boolean renameTree(edge edge, String name) {
        nodeReference tmp = edge.getSource();
        if (tmp == null) {
            throw new IllegalArgumentException("die Kante besitzt keine Quelle");
        }

        return renameTree(tmp.getTree(), name);
    }

    /**
     * setzt den Wurzelknoten des Baums, wobei der Parameter internalTree
     * eigentlich nicht notwendig ist (dient nur der lesbarkeit)
     *
     * @param tree der Baum
     * @param root der Wurzelknoten
     */
    public void setTreeRoot(tree tree, reference root) {
        if (root.getTree() != tree) {
            throw new IllegalArgumentException("die beiden Elemente gehören nicht zueinander");
        }

        setTreeRoot(root);
    }

    /**
     * setzt den possibleNextReferenceId-Zeiger auf den nächsten möglichen Wert
     *
     * @return der nächste Wert
     */
    private int increaseToNextPossibleReferenceId() {
        possibleNextReferenceId++;
        if (possibleNextReferenceId == 0) {
            possibleNextReferenceId = 1;
        }
        return possibleNextReferenceId;
    }

    /**
     * bietet Zufallszahlen an
     */
    public static class random {

        private static Random rnd = null;

        /**
         * liefert eine Zahl zwischen 1 und Integer.Size-1
         *
         * @return die Zufallszahl
         */
        public static int nextPositive() {
            if (rnd == null) {
                rnd = new Random();
            }
            return 1 + rnd.nextInt(Integer.SIZE - 2);
        }

        /**
         * liefert eine Zahl ungleich 0
         *
         * @return die Zufallszahl
         */
        public static int nextNonZero() {
            if (rnd == null) {
                rnd = new Random();
            }
            int r = rnd.nextInt();
            if (r == 0) {
                r = 1;
            }
            return r;
        }

    }

}
