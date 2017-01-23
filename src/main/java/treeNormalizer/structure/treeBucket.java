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
 * Diese Klasse verwaltet eine Menge von Bäumen, möglichst kompakt.
 *
 * @author Till
 */
public class treeBucket {

    /*
     * beim Vergeben von Referenz-IDs merken wir uns hier die aktuelle
     * Obergrenze (also die zuletzt vergebene Position)
     *
     * TODO: es wird derzeit nicht beachtet, dass IDs wiederverwenet werden
     * können.
     */
    private int currentReferencePos = 0;

    /*
     * Die Abbildung wandelt von Knotenbezeichnern nach Knoten um, sodass man
     * bestimmte Knotenarten schnell in der Verwaltung finden kann.
     */
    private final Map<String, ArrayList<treeBucketNode>> nodeNameMap = new HashMap<>();

    /**
     * diese Liste von Knotenreferenzen bilden auf die realen Knoten ab
     */
    private final Map<Integer, treeBucketNode> nodeReference = new HashMap<>();

    /**
     * diese Sammlung enthält die Teilgraphen (also die realen Knoten)
     */
    private Map<Integer, treeBucketNode> nodes = new HashMap<>();

    /**
     * enthält alle Zeiger auf die Wurzeln der Bäume
     */
    private final ArrayList<tree> trees = new ArrayList<>();

    /**
     * initialisiert einen Baumbehälter
     */
    public treeBucket() {
        // Leer
    }

    /**
     *
     * @param tree
     * @param node
     * @return
     */
    private nodeReference addNode(tree tree, treeBucketNode node) {
        node = addNodeSimple(node);
        return createNodeReference(tree, node);
    }

    /**
     *
     * @param node
     * @return
     */
    private treeBucketNode addNodeSimple(treeBucketNode node) {
        if (nodes.containsKey(node.hashCode())) {
            // der Knoten existiert bereits
            treeBucketNode localNode = nodes.get(node.hashCode());
            localNode.addParents(node.getParents());
            mergeNode(localNode, node);

            // nun müssen noch die Kinder verschmolzen werden, wobei
            // die beiden Subgraphen exakt gleich aufgebraut sind
            node = localNode;
        } else {
            // der Knoten existiert so noch nicht
            nodes.put(node.hashCode(), node);
        }
        addNodeToNameMap(node);
        return node;
    }

    /**
     * fügt einen Knoten in die Tokenmap ein (Token -> KnotenA, KnotenB)
     *
     * @param node der Knoten
     */
    private void addNodeToNameMap(treeBucketNode node) {
        String name = node.getName();
        if (nodeNameMap.containsKey(name)) {
            ArrayList<treeBucketNode> tmp = nodeNameMap.get(name);
            if (!tmp.contains(node)) {
                nodeNameMap.get(name).add(node);
            }
        } else {
            ArrayList<treeBucketNode> tmp = new ArrayList<>();
            nodeNameMap.put(name, tmp);
            tmp.add(node);
        }
    }

    /**
     * fügt einen Baum in die Verwaltung ein
     *
     * @param tree der Baum
     * @return der Baum oder null, wenn nichts eingefügt werden konnte
     */
    private tree addTree(tree tree) {
        // der Name des Baums soll eindeutig sein
        if (getTreeByName(tree.getName()) != null) {
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
    private nodeReference createNodeReference(tree tree, treeBucketNode node) {
        nodeReference tmp = new nodeReference(tree, currentReferencePos);
        nodeReference.put(currentReferencePos, node);
        node.addNodeReference(tmp);
        currentReferencePos++;
        return tmp;
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
        while (nodeExists(node)) {
            node.increaseUniqueId();
        }
        return node;
    }

    /**
     * vereint zwei Graphen miteinander, wobei targetNode den Graphen sourceNode
     * aufnimmt
     *
     * @param targetNode der Zielgraph
     * @param sourceNode der Quellgraph
     */
    private void mergeNode(treeBucketNode targetNode, treeBucketNode sourceNode) {
        targetNode.addNodeReferences(sourceNode.getNodeReferences());

        // setzt alle alten Referenzen auf den neuen Knoten
        sourceNode.getNodeReferences().forEach((ref) -> {
            nodeReference.replace(ref.getId(), targetNode);
        });

        // enfernt den alten Knoten aus der Tokenliste
        removeNodeFromNameMap(sourceNode);

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
    }

    /**
     *
     * @param node
     * @return
     */
    private boolean nodeExists(treeBucketNode node) {
        return nodes.containsKey(node.hashCode());
    }

    /**
     * aktualisiert einen Knoten und den Elternpfad
     *
     * @param node der Knoten
     */
    private void propagadeNode(treeBucketNode node) {
        int oldHash = node.hashCode();

        // nun wird der neue Hash berechnet
        node.resetUniqueId();
        node.rehash();

        // wenn sich der Hash geändert hat, müssen wir handeln
        if (oldHash != node.hashCode()) {
            // entferne den Knoten aus der Verwaltung
            removeNodeFromNameMap(node);
            nodes.remove(oldHash);

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

        for (treeBucketNode parent : parents) {
            propagadeNode(parent);
        }

        removeNodeFromNameMap(node);
        nodes.remove(node.hashCode());
    }

    /**
     * entfernt einen Knoten aus der Tokenliste (Token -> KnotenA, KnotenB)
     *
     * @param node der Knoten, der entfernt werden soll
     */
    private void removeNodeFromNameMap(treeBucketNode node) {
        String name = node.getName();
        if (nodeNameMap.containsKey(name)) {
            nodeNameMap.get(name).remove(node);
        } else {
            throw new IllegalArgumentException("der Bezeichner existiert nicht in der NameMap");
        }
    }

    /**
     * entfernt eine Knotenreferenz
     *
     * @param node der Knoten
     */
    private void removeNodeReference(nodeReference node) {
        node.disconnect();
        nodeReference.remove(node.getId());
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
        if (realNode.getNodeReferences().size() <= 1) {
            return realNode;
        }

        // nun wird die Referenz aus dem Knoten entfernt
        realNode.removeNodeReference(node);

        // jetzt muss ein neuer Knoten erzeugt werden
        treeBucketNode splittedNode = realNode.cloneNodeBase();
        splittedNode.addNodeReference(node);

        // der neue Knoten soll eindeutig sein, damit er beim Einfügen nicht
        // mit einem anderen vereint wird
        splittedNode = makeNodeUnique(splittedNode);
        addNodeSimple(splittedNode);

        // die Knotenreferenz soll nun auf einen neuen realen Knoten zeigen
        nodeReference.replace(node.getId(), splittedNode);

        splittedNode.setChilds(realNode.getChilds());

        // nun muss der Pfad über die Eltern bis zur Wurzel des Baums
        // aufgespalten werden
        if (node.hasParent()) {
            // möglicherweise gibt es noch Elternknoten des Baums
            nodeReference parent = node.getParent();

            // die Eltern werden ebenfalls in den neuen Baum überführt
            treeBucketNode realParent = getInternalNodeByReference(parent);
            treeBucketNode splittedParent = splitNode(parent);
            splittedNode.addParent(splittedParent);
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
     * fügt eine Kante ein
     *
     * @param nodeA der Startknoten
     * @param nodeB der Zielknoten
     */
    public void addEdge(nodeReference nodeA, nodeReference nodeB) {
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

        nodeA.addChild(nodeB);
        nodeB.setParent(nodeA);
        nA.addEdgeTo(nB); // ???

        // nodeA hat sich verändert
        propagadeNode(nodeA);
    }

    /**
     * fügt eine Kante ein
     *
     * @param edge die Definition der Kante
     */
    public void addEdge(edge edge) {
        addEdge(edge.getSource(), edge.getTarget());
    }

    /**
     * fügt einen Knoten in einen Baum ein
     *
     * @param tree der Baum
     * @param name der Name des Knotens
     * @return die Referenz auf den Knoten
     */
    public nodeReference createNode(tree tree, String name) {
        return createNode(tree, name, "");
    }

    /**
     * fügt einen Knoten in einen Baum ein
     *
     * @param tree der Baum
     * @param name der Name des Knotens
     * @param type der Typ des Knotens
     * @return die Referenz auf den Knoten
     */
    public nodeReference createNode(tree tree, String name, String type) {
        treeBucketNode tmp = new treeBucketNode(name, type);
        return addNode(tree, tmp);
    }

    /**
     * erzeugt einen neuen Baum
     *
     * @param name der Name
     * @return der neue Baum
     */
    public tree createTree(String name) {
        tree tmp = new tree(name);
        tmp = addTree(tmp);
        return tmp;
    }

    /**
     * liefert zu einer Knotenreferenz den internen Knoten
     *
     * @param node die Referenz
     * @return der Knoten
     */
    public treeBucketNode getInternalNodeByReference(nodeReference node) {
        return nodeReference.get(node.getId());
    }

    /**
     * liefert zu der Referenz einen Knoten, welcher für eine Änderung
     * vorbereitet wurd
     *
     * @param node der Knoten
     * @return der änderbare Knoten
     */
    public treeBucketNode getPreparedNode(nodeReference node) {
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
    public tree getTreeByName(String name) {
        for (tree tmp : getTrees()) {
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
    public void setTreeRoot(nodeReference root) {
        tree tree = root.getTree();

        // wenn root nicht neu ist, muss auch nichts gemacht werden
        if (tree.getRoot() == root) {
            return;
        }

        // nun kann die neue Wurzel gesetzt werden
        tree.setRoot(root);
    }

    /**
     * liefert alle Bäume des Behälters
     *
     * @return die Bäume
     */
    public ArrayList<tree> getTrees() {
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
        if (treeA.equals(treeB)) {
            return true;
        }

        if (treeA.getRoot().equals(treeB.getRoot())) {
            return true;
        }

        if (treeA.getRoot() == null) {
            return false;
        }

        if (treeB.getRoot() == null) {
            return false;
        }

        treeBucketNode nodeA = getInternalNodeByReference(treeA.getRoot());
        treeBucketNode nodeB = getInternalNodeByReference(treeB.getRoot());

        return nodeA.equals(nodeB);
    }

    /**
     * prüft, ob die beiden Bäume gleich sind (also auf die gleiche Wurzel
     * verweisen)
     *
     * @param nodeA ein Knoten des ersten Baums
     * @param nodeB ein Knoten des zweiten Baums
     * @return true = sind gleich, false = sind nicht gleich
     */
    public boolean isTreeEquivalentTo(nodeReference nodeA, nodeReference nodeB) {
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
     * entfernt eine Kante
     *
     * @param nodeA der Startknoten
     * @param nodeB der Zielknoten
     */
    public void removeEdge(nodeReference nodeA, nodeReference nodeB) {
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
     * entfernt einen Knoten sauber aus der Struktur/Verwaltung
     *
     * @param node der Knoten
     */
    public void removeNode(nodeReference node) {
        treeBucketNode realNode = getInternalNodeByReference(node);

        if (realNode.numberOfNodeReferences() == 1) {
            // der Knoten wird von niemandem mehr genutzt, also kann er
            // entfernt werden
            removeNodeReference(node);
            removeInternalNode(realNode);
        } else if (realNode.numberOfNodeReferences() > 1) {
            // der Knoten wird noch von anderen Referenzen genutzt
            nodeReference parent = node.getParent();
            removeNodeReference(node);

            if (parent != null) {
                // es existierte ein Vater
                treeBucketNode parentNode = getInternalNodeByReference(parent);

                if (parentNode.numberOfNodeReferences() > 1) {
                    // der Vater benötigt einen eigenen Zweig, denn eine andere
                    // Referenz nutzt diesen Knoten weiterhin
                    splitNode(parent);
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
    public void removeSubtree(nodeReference node) {
        for (nodeReference child : node.getExistingChilds()) {
            removeSubtree(child);
        }

        removeNode(node);
    }

    /**
     *
     * @param tree
     */
    public void removeTree(tree tree) {
        nodeReference root = tree.getRoot();
        if (root != null) {
            // der Baum bestitzt eine Wurzel
            removeSubtree(root);
        }
        trees.remove(tree);
    }

    /**
     * benennt einen Knoten um
     *
     * @param node der Knoten
     * @param name der neue Name
     */
    public void renameNode(nodeReference node, String name) {
        tree tree = node.getTree();

        // der Untergraph muss eventuell aufgespalten werden
        treeBucketNode tmp = getInternalNodeByReference(node);

        if (name.equals(tmp.getName())) {
            // der Name hat sich nicht geändert
            return;
        }

        treeBucketNode preparedNode = getPreparedNode(node);

        removeNodeFromNameMap(preparedNode);
        preparedNode.setName(name);
        addNodeToNameMap(preparedNode);
        propagadeNode(preparedNode);
    }

    /**
     * benennt einen Knoten um der Baum ist hierbei eigentlich nicht notwendig
     *
     * @param tree der Baum
     * @param node der Knoten
     * @param name der neue Name
     */
    public void renameNode(tree tree, nodeReference node, String name) {
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
     */
    public void renameTree(tree tree, String name) {
        if (tree.getName() == name) {
            return;
        }

        if (tree.getName() == null ? name == null : tree.getName().equals(name)) {
            return;
        }

        // der Name eines Baums soll eindeutig sein
        if (getTreeByName(tree.getName()) != null) {
            return;
        }

        tree.setName(name);
    }

    /**
     * benennt einen Baum um
     *
     * @param node ein Knoten, welcher zum Baum gehört
     * @param name der neue Name
     */
    public void renameTree(nodeReference node, String name) {
        renameTree(node.getTree(), name);
    }

    /**
     * benennt einen Baum um
     *
     * @param edge eine Kante des Baums
     * @param name der neue Name
     */
    public void renameTree(edge edge, String name) {
        nodeReference tmp = edge.getSource();
        if (tmp == null) {
            throw new IllegalArgumentException("die Kante besitzt keine Quelle");
        }

        renameTree(tmp.getTree(), name);
    }

    /**
     * setzt den Wurzelknoten des Baums, wobei der Parameter tree eigentlich
     * nicht notwendig ist (dient nur der lesbarkeit)
     *
     * @param tree der Baum
     * @param root der Wurzelknoten
     */
    public void setTreeRoot(tree tree, nodeReference root) {
        if (root.getTree() != tree) {
            throw new IllegalArgumentException("die beiden Elemente gehören nicht zueinander");
        }

        setTreeRoot(root);
    }

}
