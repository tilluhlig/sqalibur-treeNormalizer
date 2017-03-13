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
import static javaapplication3.JavaApplication3.writeNodeSetToFile;
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
     * Test of addEdge method, of class treeBucket.
     */
    @Test
    public void testAddEdge_nodeReference_nodeReference() {
        System.out.println("addEdge");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of addEdge method, of class treeBucket.
     */
    @Test
    public void testAddEdge_edge() {
        System.out.println("addEdge");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of createNode method, of class treeBucket.
     */
    @Test
    public void testCreateNode_tree_String() {
        System.out.println("createNode");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of createNode method, of class treeBucket.
     */
    @Test
    public void testCreateNode_3args() {
        System.out.println("createNode");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of createTree method, of class treeBucket.
     */
    @Test
    public void testCreateTree() {
        System.out.println("createTree");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of getInternalNodeByReference method, of class treeBucket.
     */
    @Test
    public void testGetInternalNodeByReference() {
        System.out.println("getInternalNodeByReference");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of getPreparedNode method, of class treeBucket.
     */
    @Test
    public void testGetPreparedNode() {
        System.out.println("getPreparedNode");
        System.out.println("The test case is a prototype.");
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
     * Test of setTreeRoot method, of class treeBucket.
     */
    @Test
    public void testSetTreeRoot_nodeReference() {
        System.out.println("setTreeRoot");
        System.out.println("The test case is a prototype.");
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
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of isTreeEquivalentTo method, of class treeBucket.
     */
    @Test
    public void testIsTreeEquivalentTo_nodeReference_nodeReference() {
        System.out.println("isTreeEquivalentTo");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of print method, of class treeBucket.
     */
    @Test
    public void testPrint() {
        System.out.println("print");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of removeEdge method, of class treeBucket.
     */
    @Test
    public void testRemoveEdge_nodeReference_nodeReference() {
        System.out.println("removeEdge");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of removeEdge method, of class treeBucket.
     */
    @Test
    public void testRemoveEdge_edge() {
        System.out.println("removeEdge");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of removeNode method, of class treeBucket.
     */
    @Test
    public void testRemoveNode() {
        System.out.println("removeNode");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of removeSubtree method, of class treeBucket.
     */
    @Test
    public void testRemoveSubtree() {
        System.out.println("removeSubtree");
        System.out.println("The test case is a prototype.");
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
     * Test of renameNode method, of class treeBucket.
     */
    @Test
    public void testRenameNode_nodeReference_String() {
        System.out.println("renameNode");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of renameNode method, of class treeBucket.
     */
    @Test
    public void testRenameNode_3args() {
        System.out.println("renameNode");
        System.out.println("The test case is a prototype.");
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
    public void testRenameTree_nodeReference_String() {
        System.out.println("renameTree");
        treeBucket instance = initMinimalBucket();
        tree treeA = instance.getTreeByName("BaumA");
        nodeReference nodeA = treeA.getRoot();
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
     * Test of renameTree method, of class treeBucket.
     */
    @Test
    public void testRenameTree_edge_String() {
        System.out.println("renameTree");
        treeBucket instance = initMinimalBucket();
        tree treeA = instance.getTreeByName("BaumA");
        nodeReference nodeA = treeA.getRoot();
        edge edgeA = new edge(nodeA, nodeA);

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
        nodeReference nodeA = instance.createNode(treeA, "", "sub");
        instance.setTreeRoot(treeA, nodeA);
        assertEquals(nodeA, treeA.getRoot());
    }

    private treeBucket initMinimalBucket() {
        treeBucket instance = new treeBucket();
        tree treeA = instance.createTree("BaumA");
        nodeReference plus = instance.createNode(treeA, "", "addition");
        instance.setTreeRoot(treeA, plus);

        nodeReference plus2 = instance.createNode(treeA, "", "addition");
        nodeReference plus3 = instance.createNode(treeA, "", "addition");
        instance.addEdge(plus, plus2);
        instance.addEdge(plus, plus3);

        nodeReference eins = instance.createNode(treeA, "1", "const");
        nodeReference zwei = instance.createNode(treeA, "2", "const");
        instance.addEdge(plus2, eins);
        instance.addEdge(plus3, zwei);

        instance.createTree("BaumB");
        instance.createTree("BaumC");
        return instance;
    }

}
