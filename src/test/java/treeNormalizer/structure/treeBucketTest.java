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

import treeNormalizer.structure.internal.internalEdge;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class treeBucketTest {

    public treeBucketTest() {
    }

    /**
     * Test of createNode method, of class treeBucket.
     */
    @Test
    public void testCreateNode_tree_String() {
        System.out.println("createNode");
        fail("The test case is a prototype.");
    }

    /**
     * Test of createNode method, of class treeBucket.
     */
    @Test
    public void testCreateNode_3args() {
        System.out.println("createNode");
        fail("The test case is a prototype.");
    }

    /**
     * Test of createTree method, of class treeBucket.
     */
    @Test
    public void testCreateTree() {
        System.out.println("createTree");
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTreeByName method, of class treeBucket.
     */
    @Test
    public void testGetTreeByName() {
        System.out.println("getTreeByName");
        treeBucket instance = initMinimalBucket();
        tree a = instance.getTreeByName("BaumB");
        assertNotNull(a);
        assertEquals("BaumB", a.getName());
    }

    /**
     * Test of getTrees method, of class treeBucket.
     */
    @Test
    public void testGetTrees() {
        System.out.println("getTrees");
        treeBucket instance = initMinimalBucket();
        assertEquals(3, instance.getTrees().size());
    }

    /**
     * Test of isTreeEquivalentTo method, of class treeBucket.
     */
    @Test
    public void testIsTreeEquivalentTo_tree_tree() {
        System.out.println("isTreeEquivalentTo");
        fail("The test case is a prototype.");
    }

    /**
     * Test of isTreeEquivalentTo method, of class treeBucket.
     */
    @Test
    public void testIsTreeEquivalentTo_nodeReference_nodeReference() {
        System.out.println("isTreeEquivalentTo");
        fail("The test case is a prototype.");
    }

    /**
     * Test of print method, of class treeBucket.
     */
    @Test
    public void testPrint() {
        System.out.println("print");
        treeBucket instance = initMinimalBucket();
        assertNotNull(instance.print());
    }

    /**
     * Test of removeEdge method, of class treeBucket.
     */
    @Test
    public void testRemoveEdge_nodeReference_nodeReference() {
        System.out.println("removeEdge");
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeEdge method, of class treeBucket.
     */
    @Test
    public void testRemoveEdge_edge() {
        System.out.println("removeEdge");
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeNode method, of class treeBucket.
     */
    @Test
    public void testRemoveNode() {
        System.out.println("removeNode");
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeSubtree method, of class treeBucket.
     */
    @Test
    public void testRemoveSubtree() {
        System.out.println("removeSubtree");
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeTree method, of class treeBucket.
     */
    @Test
    public void testRemoveTree() {
        System.out.println("removeTree");
        treeBucket instance = new treeBucket();
        tree treeA = instance.createTree("BaumA");
        tree treeB = instance.createTree("BaumB");
        assertEquals(2, instance.getTrees().size());
        instance.removeTree(treeA);
        assertEquals(1, instance.getTrees().size());
        assertArrayEquals(new tree[]{treeB}, instance.getTrees().toArray(new tree[0]));
    }

    /**
     * Test of renameTree method, of class treeBucket.
     */
    @Test
    public void testRenameTree_tree_String() {
        System.out.println("renameTree");
        treeBucket instance = initMinimalBucket();
        tree treeA = instance.getTreeByName("BaumA");
        assertEquals("BaumA", treeA.getName());
        boolean result = instance.renameTree(treeA, "BaumARenamed");
        assertTrue(result);
        assertEquals("BaumARenamed", treeA.getName());

        // ein Baumname darf nicht doppelt existieren
        result = instance.renameTree(treeA, "BaumARenamed");
        assertFalse(result);
        assertEquals("BaumARenamed", treeA.getName());
    }

    /**
     * Test of renameTree method, of class treeBucket.
     */
    @Test
    public void testRenameTree_edge_String() {
        System.out.println("renameTree");
        treeBucket instance = initMinimalBucket();
        tree treeA = instance.getTreeByName("BaumA");
        reference nodeA = treeA.getRoot();
        edge edgeA = new internalEdge(nodeA, nodeA);

        assertEquals("BaumA", treeA.getName());
        boolean result = instance.renameTree(edgeA, "BaumARenamed");
        assertTrue(result);
        assertEquals("BaumARenamed", treeA.getName());

        // ein Baumname darf nicht doppelt existieren
        result = instance.renameTree(edgeA, "BaumARenamed");
        assertFalse(result);
        assertEquals("BaumARenamed", treeA.getName());
    }

    /**
     * Test of setTreeRoot method, of class treeBucket.
     */
    @Test
    public void testSetTreeRoot_tree_nodeReference() {
        System.out.println("setTreeRoot");
        treeBucket instance = initMinimalBucket();
        tree treeA = instance.getTreeByName("BaumA");
        reference nodeA = instance.createNode(treeA, "", "sub");
        instance.setTreeRoot(treeA, nodeA);
        assertEquals(nodeA, treeA.getRoot());
    }

    private treeBucket initMinimalBucket() {
        treeBucket instance = new treeBucket();
        tree treeA = instance.createTree("BaumA");
        reference plus = instance.createNode(treeA, "", "addition");
        instance.setTreeRoot(treeA, plus);

        reference plus2 = instance.createNode(treeA, "", "addition");
        reference plus3 = instance.createNode(treeA, "", "addition");
        instance.addEdge(plus, plus2);
        instance.addEdge(plus, plus3);

        reference eins = instance.createNode(treeA, "1", "const");
        reference zwei = instance.createNode(treeA, "2", "const");
        instance.addEdge(plus2, eins);
        instance.addEdge(plus3, zwei);

        instance.createTree("BaumB");
        instance.createTree("BaumC");

        return instance;
    }

    /**
     * Test of referenceExists method, of class treeBucket.
     */
    @Test
    public void testReferenceExists() {
        System.out.println("referenceExists");
        fail("The test case is a prototype.");
    }

    @Test
    public void testRandom() {
        System.out.println("random.nextPositive");
        for (int i = 0; i < 25; i++) {
            if (treeBucket.random.nextPositive() < 1) {
                fail("der Zufallswert soll nicht kleiner 1 sein.");
            }
        }
    }

    /**
     * Test of createNode method, of class treeBucket.
     */
    @Test
    public void testCreateNode_3args_1() {
        System.out.println("createNode");
        fail("The test case is a prototype.");
    }

    /**
     * Test of createNode method, of class treeBucket.
     */
    @Test
    public void testCreateNode_3args_2() {
        System.out.println("createNode");
        fail("The test case is a prototype.");
    }

    /**
     * Test of createNode method, of class treeBucket.
     */
    @Test
    public void testCreateNode_4args() {
        System.out.println("createNode");
        fail("The test case is a prototype.");
    }

    /**
     * Test of simplePrint method, of class treeBucket.
     */
    @Test
    public void testSimplePrint() {
        System.out.println("simplePrint");
        treeBucket instance = initMinimalBucket();
        assertNotNull(instance.simplePrint());
    }

    /**
     * Test of addEdge method, of class treeBucket.
     */
    @Test
    public void testAddEdge_reference_reference() {
        System.out.println("addEdge");
        fail("The test case is a prototype.");
    }

    /**
     * Test of attributeExists method, of class treeBucket.
     */
    @Test
    public void testAttributeExists() {
        System.out.println("attributeExists");
        treeBucket instance = new treeBucket();
        tree treeA = instance.createTree("A");
        reference refA = instance.createNode(treeA, "1", "2");
        instance.setTreeRoot(refA);
        assertTrue(instance.attributeExists(refA, "label"));
        assertTrue(instance.attributeExists(refA, "type"));
        assertFalse(instance.attributeExists(refA, "blabla"));
    }

    /**
     * Test of changeNodeType method, of class treeBucket.
     */
    @Test
    public void testChangeNodeType() {
        System.out.println("changeNodeType");
        treeBucket instance = new treeBucket();
        tree treeA = instance.createTree("A");
        reference refA = instance.createNode(treeA, "1", "2");
        instance.setTreeRoot(refA);
        assertEquals("2", instance.getAttribute(refA, "type"));
        instance.changeNodeType(refA, "3");
        assertEquals("3", instance.getAttribute(refA, "type"));
        instance.changeNodeType(null, "3");
    }

    /**
     * Test of getAttribute method, of class treeBucket.
     */
    @Test
    public void testGetAttribute() {
        System.out.println("getAttribute");
        treeBucket instance = new treeBucket();
        tree treeA = instance.createTree("A");
        reference refA = instance.createNode(treeA, "1", "2");
        instance.setTreeRoot(refA);
        assertEquals("1", instance.getAttribute(refA, "label"));
        assertEquals("2", instance.getAttribute(refA, "type"));
        assertEquals(null, instance.getAttribute(refA, "blabla"));
        assertEquals(null, instance.getAttribute(null, "type"));
    }

    /**
     * Test of getNumberOfInternalNodes method, of class treeBucket.
     */
    @Test
    public void testGetNumberOfInternalNodes() {
        System.out.println("getNumberOfInternalNodes");
        treeBucket instance = initMinimalBucket();
        assertEquals(5, instance.getNumberOfInternalNodes());
    }

    /**
     * Test of setTreeRoot method, of class treeBucket.
     */
    @Test
    public void testSetTreeRoot_reference() {
        System.out.println("setTreeRoot");
        fail("The test case is a prototype.");
    }

    /**
     * Test of isTreeEquivalentTo method, of class treeBucket.
     */
    @Test
    public void testIsTreeEquivalentTo_reference_reference() {
        System.out.println("isTreeEquivalentTo");
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeAttribute method, of class treeBucket.
     */
    @Test
    public void testRemoveAttribute() {
        System.out.println("removeAttribute");
        treeBucket instance = new treeBucket();
        tree treeA = instance.createTree("A");
        reference refA = instance.createNode(treeA, "1", "2");
        instance.setTreeRoot(refA);
        instance.setAttribute(refA, "blabla", "5");
        
        instance.removeAttribute(null, "type");
        assertEquals("2", instance.getAttribute(refA, "type"));
        instance.removeAttribute(refA, "type");
        assertEquals(null, instance.getAttribute(refA, "type"));
        instance.removeAttribute(refA, "label");
        assertEquals(null, instance.getAttribute(refA, "type"));
        instance.removeAttribute(refA, "blublub");
    }

    /**
     * Test of removeEdge method, of class treeBucket.
     */
    @Test
    public void testRemoveEdge_reference_reference() {
        System.out.println("removeEdge");
        fail("The test case is a prototype.");
    }

    /**
     * Test of renameNode method, of class treeBucket.
     */
    @Test
    public void testRenameNode_reference_String() {
        System.out.println("renameNode");
        fail("The test case is a prototype.");
    }

    /**
     * Test of renameNode method, of class treeBucket.
     */
    @Test
    public void testRenameNode_3args() {
        System.out.println("renameNode");
        fail("The test case is a prototype.");
    }

    /**
     * Test of renameTree method, of class treeBucket.
     */
    @Test
    public void testRenameTree_reference_String() {
        System.out.println("renameTree");
        treeBucket instance = initMinimalBucket();
        tree treeA = instance.getTreeByName("BaumA");
        reference nodeA = treeA.getRoot();
        assertEquals("BaumA", treeA.getName());
        boolean result = instance.renameTree(nodeA, "BaumARenamed");
        assertTrue(result);
        assertEquals("BaumARenamed", treeA.getName());

        // ein Baumname darf nicht doppelt existieren
        result = instance.renameTree(nodeA, "BaumARenamed");
        assertFalse(result);
        assertEquals("BaumARenamed", treeA.getName());
    }

    /**
     * Test of setAttribute method, of class treeBucket.
     */
    @Test
    public void testSetAttribute() {
        System.out.println("setAttribute");
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTreeRoot method, of class treeBucket.
     */
    @Test
    public void testSetTreeRoot_tree_reference() {
        System.out.println("setTreeRoot");
        fail("The test case is a prototype.");
    }

}
