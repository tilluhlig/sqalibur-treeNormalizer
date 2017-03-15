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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class treeBucketNodeTest {

    public treeBucketNodeTest() {
    }

    /**
     * Test of addAttribute method, of class treeBucketNode.
     */
    @Test
    public void testAddAttribute() {
        System.out.println("addAttribute");
        treeBucketNode instance = new treeBucketNode();
        assertFalse(instance.attributeExists("test"));
        instance.addAttribute("test", "2");
        assertTrue(instance.attributeExists("test"));
        assertEquals("2", instance.getAttribute("test"));

        instance.addAttribute("test2", null);
        assertTrue(instance.attributeExists("test2"));
        assertEquals(null, instance.getAttribute("test2"));

        instance.addAttribute("test", "qq");
        assertTrue(instance.attributeExists("test"));
        assertEquals("qq", instance.getAttribute("test"));
    }

    /**
     * Test of addChild method, of class treeBucketNode.
     */
    @Test
    public void testAddChild() {
        System.out.println("addChild");
        treeBucketNode instance = new treeBucketNode("A");
        treeBucketNode instance2 = new treeBucketNode("B");
        treeBucketNode instance3 = new treeBucketNode("C");
        instance.addChild(instance2);
        instance.addChild(instance3);
        assertEquals(2, instance.getChilds().size());
        instance.addChild(instance2);
        assertEquals(3, instance.getChilds().size());
    }

    /**
     * Test of addEdgeFrom method, of class treeBucketNode.
     */
    @Test
    public void testAddEdgeFrom() {
        System.out.println("addEdgeFrom");
        treeBucketNode instance = new treeBucketNode("A");
        treeBucketNode instance2 = new treeBucketNode("B");
        instance2.addEdgeFrom(instance);
        assertTrue(instance.hasChilds());
        assertTrue(instance2.hasParents());
        assertTrue(instance.isChangedNode());
    }

    /**
     * Test of addEdgeTo method, of class treeBucketNode.
     */
    @Test
    public void testAddEdgeTo() {
        System.out.println("addEdgeTo");
        treeBucketNode instance = new treeBucketNode("A");
        treeBucketNode instance2 = new treeBucketNode("B");
        instance.addEdgeTo(instance2);
        assertTrue(instance.hasChilds());
        assertTrue(instance2.hasParents());
        assertTrue(instance.isChangedNode());
    }

    /**
     * Test of addNodeReference method, of class treeBucketNode.
     */
    @Test
    public void testAddNodeReference() {
        System.out.println("addNodeReference");
        treeBucketNode instance = new treeBucketNode("A");
        internalTree treeA = new internalTree("test");
        internalTree treeB = new internalTree("test2");

        nodeReference instance2 = new nodeReference(treeA, 2);
        nodeReference instance3 = new nodeReference(treeB, 3);

        assertFalse(instance.containsReferencedTree(treeA));
        assertEquals(0, instance.getNodeReferences().size());
        assertEquals(0, instance.getReferencedTrees().size());

        instance.addNodeReference(instance2);

        assertTrue(instance.containsReferencedTree(treeA));
        assertEquals(1, instance.getNodeReferences().size());
        assertEquals(1, instance.getReferencedTrees().size());

        instance.addNodeReference(instance3);

        assertTrue(instance.containsReferencedTree(treeB));
        assertEquals(2, instance.getNodeReferences().size());
        assertEquals(2, instance.getReferencedTrees().size());
    }

    /**
     * Test of addNodeReferences method, of class treeBucketNode.
     */
    @Test
    public void testAddNodeReferences() {
        System.out.println("addNodeReferences");
        treeBucketNode instance = new treeBucketNode("A");
        internalTree treeA = new internalTree("test");
        internalTree treeB = new internalTree("test2");

        nodeReference instance2 = new nodeReference(treeA, 2);
        nodeReference instance3 = new nodeReference(treeB, 3);

        assertFalse(instance.containsReferencedTree(treeA));
        assertEquals(0, instance.getNodeReferences().size());
        assertEquals(0, instance.getReferencedTrees().size());

        ArrayList<nodeReference> list = new ArrayList<>();
        list.add(instance2);
        list.add(instance3);
        instance.addNodeReferences(list);

        assertTrue(instance.containsReferencedTree(treeA));
        assertTrue(instance.containsReferencedTree(treeB));
        assertEquals(2, instance.getNodeReferences().size());
        assertEquals(2, instance.getReferencedTrees().size());
    }

    /**
     * Test of addParent method, of class treeBucketNode.
     */
    @Test
    public void testAddParent() {
        System.out.println("addParent");
        treeBucketNode instance = new treeBucketNode("A");
        treeBucketNode instance2 = new treeBucketNode("B");
        instance.addParent(instance2);
        assertTrue(instance.hasParents());
        assertFalse(instance.isRoot());
        assertEquals(1, instance.getParents().size());
        assertEquals("B", instance.getFirstParent().getLabel());
    }

    /**
     * Test of addParents method, of class treeBucketNode.
     */
    @Test
    public void testAddParents() {
        System.out.println("addParents");
        treeBucketNode instance = new treeBucketNode("A");
        ArrayList<treeBucketNode> parents = new ArrayList<>();
        parents.add(new treeBucketNode("B"));
        parents.add(new treeBucketNode("C"));

        instance.addParents(parents);
        assertTrue(instance.hasParents());
        assertFalse(instance.isRoot());
        assertEquals(2, instance.getParents().size());
        assertEquals("B", instance.getFirstParent().getLabel());
        assertEquals("C", instance.getParent(1).getLabel());

        ArrayList<treeBucketNode> parents2 = new ArrayList<>();
        parents2.add(new treeBucketNode("D"));
        instance.addParents(parents2);
        assertEquals(3, instance.getParents().size());
        assertEquals("D", instance.getParent(2).getLabel());
    }

    /**
     * Test of adoptChild method, of class treeBucketNode.
     */
    @Test
    public void testAdoptChild() {
        System.out.println("adoptChild");
        treeBucketNode instance = new treeBucketNode("A");
        instance.adoptChild(new treeBucketNode("B"));
        assertTrue(instance.hasChilds());
        assertFalse(instance.isChild());
        assertEquals("B", instance.getChild(0).getLabel());
    }

    /**
     * Test of cleanParents method, of class treeBucketNode.
     */
    @Test
    public void testCleanParents() {
        System.out.println("cleanParents");
        // kein Test, die Methode soll doppelte Elternknoten entfernen
    }

    /**
     * Test of containsReferencedTree method, of class treeBucketNode.
     */
    @Test
    public void testContainsReferencedTree() {
        System.out.println("containsReferencedTree");
        treeBucketNode instance = new treeBucketNode("A");
        internalTree treeA = new internalTree("test");
        internalTree treeB = new internalTree("test2");

        nodeReference instance2 = new nodeReference(treeA, 2);
        nodeReference instance3 = new nodeReference(treeB, 3);

        assertFalse(instance.containsReferencedTree(treeA));

        instance.addNodeReference(null);
        instance.addNodeReference(instance2);

        assertTrue(instance.containsReferencedTree(treeA));

        instance.addNodeReference(null);

        assertTrue(instance.containsReferencedTree(treeA));
    }

    /**
     * Test of decreaseUniqueId method, of class treeBucketNode.
     */
    @Test
    public void testDecreaseUniqueId() {
        System.out.println("decreaseUniqueId");
        treeBucketNode instance = new treeBucketNode("A");
        instance.decreaseUniqueId();
        assertEquals(0, instance.getUniqueId());
        instance.setUniqueId(2);
        instance.decreaseUniqueId();
        assertEquals(1, instance.getUniqueId());
        instance.decreaseUniqueId();
        assertEquals(0, instance.getUniqueId());
    }

    /**
     * Test of disconnect method, of class treeBucketNode.
     */
    @Test
    public void testDisconnect() {
        System.out.println("disconnect");
        treeBucketNode instance = new treeBucketNode("A");
        instance.addParent(new treeBucketNode("B"));
        instance.addParent(new treeBucketNode("D"));
        instance.addChild(new treeBucketNode("C"));
        instance.addChild(new treeBucketNode("E"));
        instance.disconnect();
        assertFalse(instance.hasChilds());
        assertFalse(instance.hasParents());
    }

    /**
     * Test of equals method, of class treeBucketNode.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertTrue(new treeBucketNode("").equals(new treeBucketNode("")));
        assertTrue(new treeBucketNode("A").equals(new treeBucketNode("A")));
        assertFalse(new treeBucketNode("A").equals(new treeBucketNode("B")));
        assertTrue(new treeBucketNode("A", "typeA").equals(new treeBucketNode("A", "typeA")));
        assertFalse(new treeBucketNode("A", "typeA").equals(new treeBucketNode("A", "typeB")));
        assertFalse(new treeBucketNode("A", "typeA").equals(new treeBucketNode("B", "typeA")));
        assertTrue(new treeBucketNode("A", "typeA", new HashMap<>()).equals(new treeBucketNode("A", "typeA", new HashMap<>())));
    }

    /**
     * Test of getAttribute method, of class treeBucketNode.
     */
    @Test
    public void testGetAttribute() {
        System.out.println("getAttribute");
        treeBucketNode instance = new treeBucketNode("A");
        assertNull(instance.getAttribute("test"));
        instance.addAttribute("test", "2");
        assertEquals("2", instance.getAttribute("test"));
    }

    /**
     * Test of attributeExists method, of class treeBucketNode.
     */
    @Test
    public void testAttributeExists() {
        System.out.println("attributeExists");
        treeBucketNode instance = new treeBucketNode("A");
        assertFalse(instance.attributeExists("test"));
        instance.addAttribute("test", "2");
        assertTrue(instance.attributeExists("test"));
        instance.setAttribute("test", null);
        assertTrue(instance.attributeExists("test"));
    }

    /**
     * Test of getAttributes method, of class treeBucketNode.
     */
    @Test
    public void testGetAttributes() {
        System.out.println("getAttributes");
        treeBucketNode instance = new treeBucketNode("A");
        assertEquals(1, instance.getAttributes().size());
        instance.addAttribute("test", "2");
        instance.addAttribute("testA", "3");
        instance.addAttribute("testB", null);
        assertEquals(4, instance.getAttributes().size());
    }

    /**
     * Test of setAttributes method, of class treeBucketNode.
     */
    @Test
    public void testSetAttributes() {
        System.out.println("setAttributes");
        treeBucketNode instance = new treeBucketNode("A");
        Map<String, String> attributes = new HashMap<>();
        instance.setAttributes(attributes);
        assertEquals(0, instance.getAttributes().size());
        attributes.put("test", "2");
        attributes.put("testA", "3");
        instance.setAttributes(attributes);
        assertEquals(2, instance.getAttributes().size());
    }

    /**
     * Test of getChilds method, of class treeBucketNode.
     */
    @Test
    public void testGetChilds() {
        System.out.println("getChilds");
        treeBucketNode instance = new treeBucketNode("A");
        treeBucketNode instance2 = new treeBucketNode("B");
        treeBucketNode instance3 = new treeBucketNode("C");
        assertEquals(0, instance.getChilds().size());
        instance.addChild(instance2);
        instance.addChild(instance3);
        assertEquals(2, instance.getChilds().size());
        assertEquals(0, instance2.getChilds().size());
        assertEquals(0, instance3.getChilds().size());
    }

    /**
     * Test of setChilds method, of class treeBucketNode.
     */
    @Test
    public void testSetChilds() {
        System.out.println("setChilds");
        treeBucketNode instance = new treeBucketNode("A");
        instance.setChilds(null);
        assertEquals(0, instance.getChilds().size());
        ArrayList<treeBucketNode> list = new ArrayList<>();
        instance.setChilds(list);
        assertEquals(0, instance.getChilds().size());
        list.add(new treeBucketNode("B"));
        list.add(new treeBucketNode("C"));
        instance.setChilds(list);
        assertEquals(2, instance.getChilds().size());
    }

    /**
     * Test of getFirstParent method, of class treeBucketNode.
     */
    @Test
    public void testGetFirstParent() {
        System.out.println("getFirstParent");
        treeBucketNode instance = new treeBucketNode("A");
        treeBucketNode instance2 = new treeBucketNode("B");
        treeBucketNode instance3 = new treeBucketNode("C");
        instance.addParent(instance2);
        instance.addParent(instance3);
        assertEquals(instance2, instance.getFirstParent());
        instance.removeParent(0);
        assertEquals(instance3, instance.getFirstParent());
    }

    /**
     * Test of getLabel method, of class treeBucketNode.
     */
    @Test
    public void testGetLabel() {
        System.out.println("getLabel");
        treeBucketNode instance = new treeBucketNode("A");
        assertEquals("A", instance.getLabel());
        instance.setLabel(null);
        assertEquals(null, instance.getLabel());
    }

    /**
     * Test of setLabel method, of class treeBucketNode.
     */
    @Test
    public void testSetLabel() {
        System.out.println("setLabel");
        treeBucketNode instance = new treeBucketNode("A");
        instance.setLabel("B");
        assertEquals("B", instance.getLabel());
        instance.setLabel(null);
        assertEquals(null, instance.getLabel());
    }

    /**
     * Test of getNodeReferences method, of class treeBucketNode.
     */
    @Test
    public void testGetNodeReferences() {
        System.out.println("getNodeReferences");
        treeBucketNode instance = new treeBucketNode("A");
        assertEquals(0, instance.getNodeReferences().size());
        instance.addNodeReference(new nodeReference(null, 1));
        instance.addNodeReference(new nodeReference(null, 2));
        assertEquals(2, instance.getNodeReferences().size());
        instance.addNodeReference(new nodeReference(null, 1));
        assertEquals(2, instance.getNodeReferences().size());
    }

    /**
     * Test of getOutgoingEdges method, of class treeBucketNode.
     */
    @Test
    public void testGetOutgoingEdges() {
        System.out.println("getOutgoingEdges");
        treeBucketNode instance = new treeBucketNode("A");
        instance.addChild(new treeBucketNode("B"));
        instance.addChild(new treeBucketNode("C"));
        assertArrayEquals(new treeBucketNode[]{new treeBucketNode("B"), new treeBucketNode("C")}, instance.getOutgoingEdges().toArray(new treeBucketNode[0]));
    }

    /**
     * Test of getParents method, of class treeBucketNode.
     */
    @Test
    public void testGetParents() {
        System.out.println("getParents");
        treeBucketNode instance = new treeBucketNode("A");
        assertEquals(0, instance.getParents().size());
        ArrayList<treeBucketNode> list = new ArrayList<>();
        list.add(new treeBucketNode("B"));
        list.add(new treeBucketNode("C"));
        instance.addParents(list);
        assertEquals(2, instance.getParents().size());
        assertArrayEquals(new treeBucketNode[]{new treeBucketNode("B"), new treeBucketNode("C")}, instance.getParents().toArray(new treeBucketNode[0]));
    }

    /**
     * Test of setParents method, of class treeBucketNode.
     */
    @Test
    public void testSetParents() {
        System.out.println("setParents");
        treeBucketNode instance = new treeBucketNode("A");
        assertEquals(0, instance.getParents().size());
        instance.setParents(new ArrayList<>());
        assertEquals(0, instance.getParents().size());
        instance.setParents(null);
        assertEquals(0, instance.getParents().size());

        ArrayList<treeBucketNode> list = new ArrayList<>();
        list.add(new treeBucketNode("B"));
        list.add(new treeBucketNode("C"));
        instance.setParents(list);
        assertEquals(2, instance.getParents().size());
        assertArrayEquals(new treeBucketNode[]{new treeBucketNode("B"), new treeBucketNode("C")}, instance.getParents().toArray(new treeBucketNode[0]));
    }

    /**
     * Test of getReferencedTrees method, of class treeBucketNode.
     */
    @Test
    public void testGetReferencedTrees() {
        System.out.println("getReferencedTrees");
        treeBucketNode instance = new treeBucketNode("A");
        assertEquals(0, instance.getReferencedTrees().size());
        instance.addNodeReference(new nodeReference(new internalTree("A"), 1));
        instance.addNodeReference(new nodeReference(new internalTree("B"), 2));
        assertEquals(2, instance.getReferencedTrees().size());
        assertArrayEquals(new internalTree[]{new internalTree("A"), new internalTree("B")}, instance.getReferencedTrees().toArray(new internalTree[0]));
    }

    /**
     * Test of getType method, of class treeBucketNode.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        treeBucketNode instance = new treeBucketNode("A");
        assertEquals(null, instance.getType());
    }

    /**
     * Test of setType method, of class treeBucketNode.
     */
    @Test
    public void testSetType() {
        System.out.println("setType");
        treeBucketNode instance = new treeBucketNode("A");
        instance.setType("B");
        assertEquals("B", instance.getType());
        instance.setType(null);
        assertEquals(null, instance.getType());
    }

    /**
     * Test of getUniqueId method, of class treeBucketNode.
     */
    @Test
    public void testGetUniqueId() {
        System.out.println("getUniqueId");
        treeBucketNode instance = new treeBucketNode("A");
        assertEquals(0, instance.getUniqueId());
        instance.setUniqueId(15);
        assertEquals(15, instance.getUniqueId());
    }

    /**
     * Test of hasChilds method, of class treeBucketNode.
     */
    @Test
    public void testHasChilds() {
        System.out.println("hasChilds");
        treeBucketNode instance = new treeBucketNode("A");
        assertFalse(instance.hasChilds());
        instance.addChild(new treeBucketNode("B"));
        assertTrue(instance.hasChilds());
        instance.addChild(new treeBucketNode("C"));
        assertTrue(instance.hasChilds());
    }

    /**
     * Test of hasParents method, of class treeBucketNode.
     */
    @Test
    public void testHasParents() {
        System.out.println("hasParents");
        treeBucketNode instance = new treeBucketNode("A");
        assertFalse(instance.hasParents());
        instance.addParent(new treeBucketNode("B"));
        assertTrue(instance.hasParents());
        instance.addParent(new treeBucketNode("C"));
        assertTrue(instance.hasParents());
    }

    /**
     * Test of hashCode method, of class treeBucketNode.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        treeBucketNode instance = new treeBucketNode("A");
        assertEquals(0, instance.getRawHash());
        assertNotEquals(0, instance.hashCode()); // sehr wahrscheinlich
    }

    /**
     * Test of inDegree method, of class treeBucketNode.
     */
    @Test
    public void testInDegree() {
        System.out.println("inDegree");
        treeBucketNode instance = new treeBucketNode("A");
        assertEquals(0, instance.inDegree());
        instance.addParent(new treeBucketNode("B"));
        assertEquals(1, instance.inDegree());
        instance.addParent(new treeBucketNode("C"));
        assertEquals(2, instance.inDegree());
    }

    /**
     * Test of increaseUniqueId method, of class treeBucketNode.
     */
    @Test
    public void testIncreaseUniqueId() {
        System.out.println("increaseUniqueId");
        treeBucketNode instance = new treeBucketNode("A");
        assertEquals(0, instance.getUniqueId());
        instance.increaseUniqueId();
        assertEquals(1, instance.getUniqueId());
    }

    /**
     * Test of isChild method, of class treeBucketNode.
     */
    @Test
    public void testIsChild() {
        System.out.println("isChild");
        treeBucketNode instance = new treeBucketNode("A");
        assertFalse(instance.isChild());
        instance.addParent(new treeBucketNode("B"));
        assertTrue(instance.isChild());
    }

    /**
     * Test of isChildless method, of class treeBucketNode.
     */
    @Test
    public void testIsChildless() {
        System.out.println("isChildless");
        treeBucketNode instance = new treeBucketNode("A");
        assertTrue(instance.isChildless());
        instance.addChild(new treeBucketNode("B"));
        assertFalse(instance.isChildless());
    }

    /**
     * Test of isLeaf method, of class treeBucketNode.
     */
    @Test
    public void testIsLeaf() {
        System.out.println("isLeaf");
        treeBucketNode instance = new treeBucketNode("A");
        assertTrue(instance.isLeaf());
        instance.addChild(new treeBucketNode("B"));
        assertFalse(instance.isLeaf());
    }

    /**
     * Test of isOrphan method, of class treeBucketNode.
     */
    @Test
    public void testIsOrphan() {
        System.out.println("isOrphan");
        treeBucketNode instance = new treeBucketNode("A");
        assertTrue(instance.isOrphan());
        instance.addParent(new treeBucketNode("B"));
        assertFalse(instance.isOrphan());
    }

    /**
     * Test of isRoot method, of class treeBucketNode.
     */
    @Test
    public void testIsRoot() {
        System.out.println("isRoot");
        treeBucketNode instance = new treeBucketNode("A");
        assertTrue(instance.isRoot());
        instance.addParent(new treeBucketNode("B"));
        assertFalse(instance.isRoot());
    }

    /**
     * Test of isTreeReferenced method, of class treeBucketNode.
     */
    @Test
    public void testIsTreeReferenced() {
        System.out.println("isTreeReferenced");
        treeBucketNode instance = new treeBucketNode("A");
        internalTree treeA = new internalTree("A");
        nodeReference nodeA = new nodeReference(treeA, 1);
        assertFalse(instance.isTreeReferenced(treeA));
        instance.addNodeReference(nodeA);
        assertTrue(instance.isTreeReferenced(treeA));
    }

    /**
     * Test of numberOfNodeReferences method, of class treeBucketNode.
     */
    @Test
    public void testNumberOfNodeReferences() {
        System.out.println("numberOfNodeReferences");
        treeBucketNode instance = new treeBucketNode("A");
        assertEquals(0, instance.numberOfNodeReferences());
        instance.addNodeReference(new nodeReference(null, 1));
        instance.addNodeReference(new nodeReference(null, 2));
        assertEquals(2, instance.numberOfNodeReferences());
    }

    /**
     * Test of numberOfParents method, of class treeBucketNode.
     */
    @Test
    public void testNumberOfParents() {
        System.out.println("numberOfParents");
        treeBucketNode instance = new treeBucketNode("A");
        assertEquals(0, instance.numberOfParents());
        instance.addParent(new treeBucketNode("B"));
        instance.addParent(new treeBucketNode("C"));
        assertEquals(2, instance.numberOfParents());
    }

    /**
     * Test of numberOfReferencedTrees method, of class treeBucketNode.
     */
    @Test
    public void testNumberOfReferencedTrees() {
        System.out.println("numberOfReferencedTrees");
        treeBucketNode instance = new treeBucketNode("A");
        assertEquals(0, instance.numberOfReferencedTrees());
        instance.addNodeReference(new nodeReference(new internalTree("A"), 1));
        instance.addNodeReference(new nodeReference(new internalTree("B"), 2));
        assertEquals(2, instance.numberOfReferencedTrees());
    }

    /**
     * Test of outDegree method, of class treeBucketNode.
     */
    @Test
    public void testOutDegree() {
        System.out.println("outDegree");
        treeBucketNode instance = new treeBucketNode("A");
        assertEquals(0, instance.outDegree());
        instance.addChild(new treeBucketNode("B"));
        instance.addChild(new treeBucketNode("C"));
        assertEquals(2, instance.outDegree());
    }

    /**
     * Test of print method, of class treeBucketNode.
     */
    @Test
    public void testPrint() {
        System.out.println("print");
        treeBucketNode instance = new treeBucketNode("A");
        assertNotNull(instance.print());
    }

    /**
     * Test of rehash method, of class treeBucketNode.
     */
    @Test
    public void testRehash() {
        System.out.println("rehash");
        treeBucketNode instance = new treeBucketNode("A");
        assertEquals(0, instance.getRawHash());
        instance.rehash();
        assertNotEquals(0, instance.getRawHash()); // sehr wahrscheinlich
        int a = instance.getRawHash();
        instance.rehash();
        assertEquals(a, instance.getRawHash()); // das muss so sein
        instance.addAttribute("test", "2");
        instance.rehash();
        assertNotEquals(a, instance.getRawHash()); // sehr wahrscheinlich

    }

    /**
     * Test of removeAllAttributes method, of class treeBucketNode.
     */
    @Test
    public void testRemoveAllAttributes() {
        System.out.println("removeAllAttributes");
        treeBucketNode instance = new treeBucketNode("A");
        instance.removeAllAttributes();
        instance.addAttribute("test", "2");
        instance.addAttribute("test2", "3");
        instance.removeAllAttributes();
        assertEquals(0, instance.getAttributes().size());
    }

    /**
     * Test of removeAllNodeReferences method, of class treeBucketNode.
     */
    @Test
    public void testRemoveAllNodeReferences() {
        System.out.println("removeAllNodeReferences");
        treeBucketNode instance = new treeBucketNode("A");
        instance.removeAllNodeReferences();
        assertEquals(0, instance.numberOfNodeReferences());
        instance.addNodeReference(new nodeReference(new internalTree("A"), 1));
        instance.addNodeReference(new nodeReference(new internalTree("B"), 2));
        instance.removeAllNodeReferences();
        assertEquals(0, instance.numberOfNodeReferences());
    }

    /**
     * Test of removeAttribute method, of class treeBucketNode.
     */
    @Test
    public void testRemoveAttribute() {
        System.out.println("removeAttribute");
        treeBucketNode instance = new treeBucketNode("A");
        instance.removeAttribute("test");
        instance.addAttribute("test", "2");
        assertTrue(instance.attributeExists("test"));
        instance.removeAttribute("test");
        assertFalse(instance.attributeExists("test"));
    }

    /**
     * Test of removeChild method, of class treeBucketNode.
     */
    @Test
    public void testRemoveChild_int() {
        System.out.println("removeChild");
        treeBucketNode instance = new treeBucketNode("A");
        treeBucketNode instance2 = new treeBucketNode("B");
        treeBucketNode instance3 = new treeBucketNode("C");
        treeBucketNode instance4 = new treeBucketNode("D");
        instance.addChild(instance2);
        instance.addChild(instance3);
        instance.addChild(instance4);
        instance.removeChild(1);
        assertArrayEquals(new treeBucketNode[]{instance2, instance4}, instance.getChilds().toArray(new treeBucketNode[0]));
        instance.removeChild(1);
        assertArrayEquals(new treeBucketNode[]{instance2}, instance.getChilds().toArray(new treeBucketNode[0]));
    }

    /**
     * Test of removeChild method, of class treeBucketNode.
     */
    @Test
    public void testRemoveChild_treeBucketNode() {
        System.out.println("removeChild");
        treeBucketNode instance = new treeBucketNode("A");
        treeBucketNode instance2 = new treeBucketNode("B");
        treeBucketNode instance3 = new treeBucketNode("C");
        treeBucketNode instance4 = new treeBucketNode("D");
        instance.addChild(instance2);
        instance.addChild(instance3);
        instance.addChild(instance4);
        instance.removeChild(instance3);
        assertArrayEquals(new treeBucketNode[]{instance2, instance4}, instance.getChilds().toArray(new treeBucketNode[0]));
        instance.removeChild(instance4);
        assertArrayEquals(new treeBucketNode[]{instance2}, instance.getChilds().toArray(new treeBucketNode[0]));
    }

    /**
     * Test of removeChildEdges method, of class treeBucketNode.
     */
    @Test
    public void testRemoveChildEdges() {
        System.out.println("removeChildEdges");
        treeBucketNode instance = new treeBucketNode("A");
        treeBucketNode instance2 = new treeBucketNode("B");
        instance.removeChildEdges(); // ruhig mal so ausführen
        instance.addChild(instance2);
        instance2.addParent(instance);
        instance.removeChildEdges(); // jetzt sollten wir ein Kind entfernen können
        assertFalse(instance.hasChilds());
        assertFalse(instance2.hasParents());
        instance2.removeChildEdges(); // sollte trotzdem noch gehen
    }

    /**
     * Test of removeEdgeFrom method, of class treeBucketNode.
     */
    @Test
    public void testRemoveEdgeFrom() {
        System.out.println("removeEdgeFrom");
        treeBucketNode instance = new treeBucketNode("A");
        treeBucketNode instance2 = new treeBucketNode("B");
        instance2.removeEdgeFrom(instance); // ruhig mal so ausführen
        instance.addChild(instance2);
        instance2.addParent(instance);
        instance2.removeEdgeFrom(instance); // jetzt sollten wir ein Kind entfernen können
        assertFalse(instance.hasChilds());
        assertFalse(instance2.hasParents());
        instance2.removeEdgeFrom(instance); // sollte trotzdem noch gehen
    }

    /**
     * Test of removeEdgeTo method, of class treeBucketNode.
     */
    @Test
    public void testRemoveEdgeTo() {
        System.out.println("removeEdgeTo");
        treeBucketNode instance = new treeBucketNode("A");
        treeBucketNode instance2 = new treeBucketNode("B");
        instance.removeEdgeTo(instance2); // ruhig mal so ausführen
        instance.addChild(instance2);
        instance2.addParent(instance);
        instance.removeEdgeTo(instance2); // jetzt sollten wir ein Kind entfernen können
        assertFalse(instance.hasChilds());
        assertFalse(instance2.hasParents());
        instance2.removeEdgeTo(instance2); // sollte trotzdem noch gehen
    }

    /**
     * Test of unsetEdgeTo method, of class treeBucketNode.
     */
    @Test
    public void testUnsetEdgeTo() {
        System.out.println("unsetEdgeTo");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of unsetChild method, of class treeBucketNode.
     */
    @Test
    public void testUnsetChild_treeBucketNode() {
        System.out.println("unsetChild");
        treeBucketNode instance = new treeBucketNode("A");
        treeBucketNode instance2 = new treeBucketNode("B");
        treeBucketNode instance3 = new treeBucketNode("C");
        treeBucketNode instance4 = new treeBucketNode("D");
        instance.addChild(instance2);
        instance.addChild(instance3);
        instance.addChild(instance4);
        instance.unsetChild(instance3);
        assertArrayEquals(new treeBucketNode[]{instance2, null, instance4}, instance.getChilds().toArray(new treeBucketNode[0]));
        instance.unsetChild(instance4);
        assertArrayEquals(new treeBucketNode[]{instance2, null, null}, instance.getChilds().toArray(new treeBucketNode[0]));
    }

    /**
     * Test of unsetChild method, of class treeBucketNode.
     */
    @Test
    public void testUnsetChild_int() {
        System.out.println("unsetChild");
        treeBucketNode instance = new treeBucketNode("A");
        treeBucketNode instance2 = new treeBucketNode("B");
        treeBucketNode instance3 = new treeBucketNode("C");
        treeBucketNode instance4 = new treeBucketNode("D");
        instance.addChild(instance2);
        instance.addChild(instance3);
        instance.addChild(instance4);
        instance.unsetChild(1);
        assertArrayEquals(new treeBucketNode[]{instance2, null, instance4}, instance.getChilds().toArray(new treeBucketNode[0]));
        instance.unsetChild(2);
        assertArrayEquals(new treeBucketNode[]{instance2, null, null}, instance.getChilds().toArray(new treeBucketNode[0]));
    }

    /**
     * Test of removeNodeReference method, of class treeBucketNode.
     */
    @Test
    public void testRemoveNodeReference_int() {
        System.out.println("removeNodeReference");
        treeBucketNode instance = new treeBucketNode("A");
        nodeReference nodeA = new nodeReference(null, 1);
        nodeReference nodeB = new nodeReference(null, 2);
        instance.removeNodeReference(0);
        instance.addNodeReference(nodeA);
        instance.addNodeReference(nodeB);
        instance.removeNodeReference(0);
        assertArrayEquals(new nodeReference[]{nodeB}, instance.getNodeReferences().toArray(new nodeReference[0]));
        instance.removeNodeReference(0);
        assertArrayEquals(new nodeReference[]{}, instance.getNodeReferences().toArray(new nodeReference[0]));
    }

    /**
     * Test of removeNodeReference method, of class treeBucketNode.
     */
    @Test
    public void testRemoveNodeReference_nodeReference() {
        System.out.println("removeNodeReference");
        treeBucketNode instance = new treeBucketNode("A");
        nodeReference nodeA = new nodeReference(null, 1);
        nodeReference nodeB = new nodeReference(null, 2);
        instance.removeNodeReference(nodeA);
        instance.addNodeReference(nodeA);
        instance.addNodeReference(nodeB);
        instance.removeNodeReference(nodeA);
        assertArrayEquals(new nodeReference[]{nodeB}, instance.getNodeReferences().toArray(new nodeReference[0]));
        instance.removeNodeReference(nodeB);
        assertArrayEquals(new nodeReference[]{}, instance.getNodeReferences().toArray(new nodeReference[0]));
    }

    /**
     * Test of removeParent method, of class treeBucketNode.
     */
    @Test
    public void testRemoveParent_treeBucketNode() {
        System.out.println("removeParent");
        treeBucketNode instance = new treeBucketNode("A");
        instance.addParent(new treeBucketNode("B"));
        instance.addParent(new treeBucketNode("C"));
        instance.removeParent(new treeBucketNode("B"));
        assertArrayEquals(new treeBucketNode[]{new treeBucketNode("C")}, instance.getParents().toArray(new treeBucketNode[0]));
        instance.removeParent(new treeBucketNode("C"));
        assertArrayEquals(new treeBucketNode[]{}, instance.getParents().toArray(new treeBucketNode[0]));
    }

    /**
     * Test of removeParent method, of class treeBucketNode.
     */
    @Test
    public void testRemoveParent_int() {
        System.out.println("removeParent");
        treeBucketNode instance = new treeBucketNode("A");
        instance.addParent(new treeBucketNode("B"));
        instance.addParent(new treeBucketNode("C"));
        instance.removeParent(0);
        assertArrayEquals(new treeBucketNode[]{new treeBucketNode("C")}, instance.getParents().toArray(new treeBucketNode[0]));
        instance.removeParent(0);
        assertArrayEquals(new treeBucketNode[]{}, instance.getParents().toArray(new treeBucketNode[0]));
    }

    /**
     * Test of removeParentEdges method, of class treeBucketNode.
     */
    @Test
    public void testRemoveParentEdges() {
        System.out.println("removeParentEdges");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of resetUniqueId method, of class treeBucketNode.
     */
    @Test
    public void testResetUniqueId() {
        System.out.println("resetUniqueId");
        treeBucketNode instance = new treeBucketNode("A");
        assertEquals(0, instance.getUniqueId());
        instance.setUniqueId(15);
        instance.resetUniqueId();
        assertEquals(0, instance.getUniqueId());
    }

    /**
     * Test of setAttribute method, of class treeBucketNode.
     */
    @Test
    public void testSetAttribute() {
        System.out.println("setAttribute");
        treeBucketNode instance = new treeBucketNode("A");
        instance.setAttribute("test", "2");
        assertEquals("2", instance.getAttribute("test"));
        instance.setAttribute("test", null);
        assertEquals(null, instance.getAttribute("test"));
    }

    /**
     * Test of getRawHash method, of class treeBucketNode.
     */
    @Test
    public void testGetRawHash() {
        System.out.println("getRawHash");
        // diese Methode sollte nicht verwendet werden
        treeBucketNode instance = new treeBucketNode("A");
        assertEquals(0, instance.getRawHash());
        instance.setRawHash(15);
        assertEquals(15, instance.getRawHash());
    }

    /**
     * Test of setRawHash method, of class treeBucketNode.
     */
    @Test
    public void testSetRawHash() {
        System.out.println("setRawHash");
        // diese Methode sollte nicht verwendet werden
        treeBucketNode instance = new treeBucketNode("A");
        instance.setRawHash(15);
        assertEquals(15, instance.getRawHash());
    }

    /**
     * Test of setUniqueId method, of class treeBucketNode.
     */
    @Test
    public void testSetUniqueId() {
        System.out.println("setUniqueId");
        treeBucketNode instance = new treeBucketNode("A");
        assertEquals(0, instance.getUniqueId());
        instance.setUniqueId(15);
        assertEquals(15, instance.getUniqueId());
        instance.setUniqueId(Integer.MAX_VALUE + 1);
        assertEquals(0, instance.getUniqueId());
    }

    /**
     * Test of getChild method, of class treeBucketNode.
     */
    @Test
    public void testGetChild() {
        System.out.println("getChild");
        treeBucketNode instance = new treeBucketNode("A");
        treeBucketNode instance2 = new treeBucketNode("B");
        treeBucketNode instance3 = new treeBucketNode("C");
        assertNull(instance.getChild(0));
        instance.addChild(instance2);
        instance.addChild(instance3);
        assertEquals(instance3, instance.getChild(1));
    }

    /**
     * Test of setChild method, of class treeBucketNode.
     */
    @Test
    public void testSetChild() {
        System.out.println("setChild");
        treeBucketNode instance = new treeBucketNode("A");
        treeBucketNode instance2 = new treeBucketNode("B");
        treeBucketNode instance3 = new treeBucketNode("C");
        instance.addChild(instance2);
        assertEquals(instance2, instance.getChild(0));
        assertTrue(instance.setChild(0, instance3));
        assertEquals(instance3, instance.getChild(0));

        assertFalse(instance.setChild(15, instance3));
    }

    /**
     * Test of getParent method, of class treeBucketNode.
     */
    @Test
    public void testGetParent() {
        System.out.println("getParent");
        treeBucketNode instance = new treeBucketNode("A");
        treeBucketNode instance2 = new treeBucketNode("B");
        assertNull(instance.getParent(0));
        instance.addParent(instance2);
        assertEquals(instance2, instance.getParent(0));
    }

    /**
     * Test of nodeChanged method, of class treeBucketNode.
     */
    @Test
    public void testNodeChanged() {
        System.out.println("nodeChanged");
        treeBucketNode instance = new treeBucketNode("A");
        instance.hashCode();
        assertFalse(instance.isChangedNode());
        instance.nodeChanged();
        assertTrue(instance.isChangedNode());
    }

    /**
     * Test of removeAllParents method, of class treeBucketNode.
     */
    @Test
    public void testRemoveAllParents() {
        System.out.println("removeAllParents");
        treeBucketNode instance = new treeBucketNode("A");
        instance.removeAllParents();
        assertFalse(instance.hasParents());
        instance.addParent(new treeBucketNode("B"));
        instance.addParent(new treeBucketNode("C"));
        instance.removeAllParents();
        assertFalse(instance.hasParents());
    }

    /**
     * Test of isChangedNode method, of class treeBucketNode.
     */
    @Test
    public void testIsChangedNode() {
        System.out.println("isChangedNode");
        treeBucketNode instance = new treeBucketNode("A");
        instance.setChangedNode(false);
        assertFalse(instance.isChangedNode());
        instance.setChangedNode(true);
        assertTrue(instance.isChangedNode());
    }

    /**
     * Test of setChangedNode method, of class treeBucketNode.
     */
    @Test
    public void testSetChangedNode() {
        System.out.println("setChangedNode");
        treeBucketNode instance = new treeBucketNode("A");
        instance.setChangedNode(true);
        instance.setChangedNode(false);
        assertFalse(instance.isChangedNode());
    }

    /**
     * Test of findChild method, of class treeBucketNode.
     */
    @Test
    public void testFindChild() {
        System.out.println("findChild");
        treeBucketNode instance = new treeBucketNode(1, "A");
        treeBucketNode instance2 = new treeBucketNode(2, "B");
        treeBucketNode instance3 = new treeBucketNode(3, "C");
        instance.addChild(instance2);
        instance.addChild(null);
        instance.addChild(instance3);
        assertEquals(0, instance.findChild(instance2));
        assertEquals(-1, instance.findChild(null));
        assertEquals(2, instance.findChild(instance3));
        assertEquals(-1, instance.findChild(new treeBucketNode(4, "D")));
    }

    /**
     * Test of getStoreId method, of class treeBucketNode.
     */
    @Test
    public void testGetStoreId() {
        System.out.println("getStoreId");
        treeBucketNode instance = new treeBucketNode("A");
        assertEquals(0, instance.getStoreId());
    }

    /**
     * Test of setStoreId method, of class treeBucketNode.
     */
    @Test
    public void testSetStoreId() {
        System.out.println("setStoreId");
        treeBucketNode instance = new treeBucketNode("A");
        instance.setStoreId(15);
        assertEquals(15, instance.getStoreId());
    }

    /**
     * Test of cloneNodeBase method, of class treeBucketNode.
     */
    @Test
    public void testCloneNodeBase_int() {
        System.out.println("cloneNodeBase");
        treeBucketNode instance = new treeBucketNode(2, "A");
        treeBucketNode instance2 = instance.cloneNodeBase(3);
        assertEquals(3, instance2.getId());
        assertEquals(instance.getLabel(), instance2.getLabel());
        assertEquals(instance.getType(), instance2.getType());
        assertEquals(instance.getAttributes(), instance2.getAttributes());
        assertNotEquals(System.identityHashCode(instance), System.identityHashCode(instance2));
        instance.setLabel("B");
        assertNotEquals(instance.getLabel(), instance2.getLabel());
    }

    /**
     * Test of cloneNodeBase method, of class treeBucketNode.
     */
    @Test
    public void testCloneNodeBase_0args() {
        System.out.println("cloneNodeBase");
        treeBucketNode instance = new treeBucketNode("A");
        treeBucketNode instance2 = instance.cloneNodeBase();
        assertEquals(instance.getLabel(), instance2.getLabel());
        assertEquals(instance.getType(), instance2.getType());
        assertEquals(instance.getAttributes(), instance2.getAttributes());
        assertNotEquals(System.identityHashCode(instance), System.identityHashCode(instance2));
        instance2.setLabel("B");
        assertNotEquals(instance.getLabel(), instance2.getLabel());
    }

    /**
     * Test of findChilds method, of class treeBucketNode.
     */
    @Test
    public void testFindChilds() {
        System.out.println("findChilds");
        treeBucketNode instance = new treeBucketNode(1, "A");
        treeBucketNode instance2 = new treeBucketNode(2, "B");
        treeBucketNode instance3 = new treeBucketNode(3, "C");
        instance.addChild(instance2);
        instance.addChild(instance3);
        instance.addChild(null);
        instance.addChild(instance3);
        assertArrayEquals(new int[]{0}, instance.findChilds(instance2));
        assertArrayEquals(new int[]{}, instance.findChilds(null));
        assertArrayEquals(new int[]{1, 3}, instance.findChilds(instance3));
        assertArrayEquals(new int[]{}, instance.findChilds(new treeBucketNode(4, "D")));
    }

    /**
     * Test of updateStoreId method, of class treeBucketNode.
     */
    @Test
    public void testUpdateStoreId() {
        System.out.println("updateStoreId");
        treeBucketNode instance = new treeBucketNode("A");
        int current = instance.getStoreId();
        instance.setRawHash(15);
        instance.updateStoreId();
        assertEquals(15, instance.getStoreId());
    }

    /**
     * Test of getId method, of class treeBucketNode.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        treeBucketNode instance = new treeBucketNode("A");
        instance.setId(99);
        assertEquals(99, instance.getId());
    }

    /**
     * Test of setId method, of class treeBucketNode.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        treeBucketNode instance = new treeBucketNode("A");
        instance.setId(0);
        assertEquals(0, instance.getId());
        instance.setId(14);
        instance.setId(-5);
        assertEquals(-5, instance.getId());
    }

    /**
     * Test of hasValidId method, of class treeBucketNode.
     */
    @Test
    public void testHasValidId() {
        System.out.println("hasValidId");
        treeBucketNode instance = new treeBucketNode("A");
        instance.setId(0);
        assertFalse(instance.hasValidId()); // 0 ist ungültig
        instance.setId(15);
        assertTrue(instance.hasValidId());
    }

    /**
     * Test of addChilds method, of class treeBucketNode.
     */
    @Test
    public void testAddChilds() {
        System.out.println("addChilds");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of resetChilds method, of class treeBucketNode.
     */
    @Test
    public void testResetChilds() {
        System.out.println("resetChilds");
        System.out.println("The test case is a prototype.");
    }

}
