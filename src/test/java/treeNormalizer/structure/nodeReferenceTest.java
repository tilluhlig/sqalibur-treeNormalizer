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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class nodeReferenceTest {

    /**
     * der Konstruktur
     */
    public nodeReferenceTest() {
        // kein Inhalt
    }

    /**
     * Test of addChild method, of class nodeReference.
     */
    @Test
    public void testAddChild() {
        System.out.println("addChild");
        tree a = new tree("test");
        nodeReference child = new nodeReference(a, 100);
        nodeReference instance = new nodeReference(a, 200);

        ArrayList<nodeReference> testChildList = new ArrayList<>();
        testChildList.add(child);

        assertEquals(instance.hasChilds(), false);
        assertEquals(instance.getLeftChild(), null);
        assertEquals(instance.getRightChild(), null);
        assertEquals(instance.getChilds(), new ArrayList<>());
        assertEquals(instance.getExistingChilds(), new ArrayList<>());

        instance.addChild(child);

        assertEquals(instance.getParent(), null);
        assertEquals(instance.hasParent(), false);
        assertEquals(instance.getChilds(), testChildList);
        assertEquals(instance.getExistingChilds(), testChildList);
        assertEquals(instance.getLeftChild(), child);
        assertEquals(instance.getRightChild(), null);
        assertEquals(instance.hasChilds(), true);
    }

    /**
     * Test of childExists method, of class nodeReference.
     */
    @Test
    public void testChildExists() {
        System.out.println("childExists");
        tree a = new tree("test");
        nodeReference child = new nodeReference(a, 100);
        nodeReference child2 = new nodeReference(a, 300);
        nodeReference instance = new nodeReference(a, 200);
        
        assertEquals(false, instance.childExists(child));
        assertEquals(instance.hasChilds(), false);
        
        instance.addChild(child);
        
        assertEquals(instance.hasChilds(), true);
        assertEquals(true, instance.childExists(child));
        
        instance.addChild(child2);
        
        assertEquals(true, instance.childExists(child));
        assertEquals(true, instance.childExists(child2));
    }

    /**
     * Test of disconnect method, of class nodeReference.
     */
    @Test
    public void testDisconnect() {
        System.out.println("disconnect");
        nodeReference instance = null;
        instance.disconnect();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class nodeReference.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        nodeReference instance = null;
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getChilds method, of class nodeReference.
     */
    @Test
    public void testGetChilds() {
        System.out.println("getChilds");
        nodeReference instance = null;
        ArrayList<nodeReference> expResult = null;
        ArrayList<nodeReference> result = instance.getChilds();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setChilds method, of class nodeReference.
     */
    @Test
    public void testSetChilds() {
        System.out.println("setChilds");
        ArrayList<nodeReference> childs = null;
        nodeReference instance = null;
        instance.setChilds(childs);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getExistingChilds method, of class nodeReference.
     */
    @Test
    public void testGetExistingChilds() {
        System.out.println("getExistingChilds");
        nodeReference instance = null;
        ArrayList<nodeReference> expResult = null;
        ArrayList<nodeReference> result = instance.getExistingChilds();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getExistingOutgoingEdges method, of class nodeReference.
     */
    @Test
    public void testGetExistingOutgoingEdges() {
        System.out.println("getExistingOutgoingEdges");
        nodeReference instance = null;
        edge[] expResult = null;
        edge[] result = instance.getExistingOutgoingEdges();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getId method, of class nodeReference.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        nodeReference instance = null;
        int expResult = 0;
        int result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setId method, of class nodeReference.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        int id = 0;
        nodeReference instance = null;
        instance.setId(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLeftChild method, of class nodeReference.
     */
    @Test
    public void testGetLeftChild() {
        System.out.println("getLeftChild");
        nodeReference instance = null;
        nodeReference expResult = null;
        nodeReference result = instance.getLeftChild();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOutgoingEdges method, of class nodeReference.
     */
    @Test
    public void testGetOutgoingEdges() {
        System.out.println("getOutgoingEdges");
        nodeReference instance = null;
        edge[] expResult = null;
        edge[] result = instance.getOutgoingEdges();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getParent method, of class nodeReference.
     */
    @Test
    public void testGetParent() {
        System.out.println("getParent");
        nodeReference instance = null;
        nodeReference expResult = null;
        nodeReference result = instance.getParent();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setParent method, of class nodeReference.
     */
    @Test
    public void testSetParent() {
        System.out.println("setParent");
        nodeReference parent = null;
        nodeReference instance = null;
        instance.setParent(parent);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRightChild method, of class nodeReference.
     */
    @Test
    public void testGetRightChild() {
        System.out.println("getRightChild");
        nodeReference instance = null;
        nodeReference expResult = null;
        nodeReference result = instance.getRightChild();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTree method, of class nodeReference.
     */
    @Test
    public void testGetTree() {
        System.out.println("getTree");
        nodeReference instance = null;
        tree expResult = null;
        tree result = instance.getTree();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTree method, of class nodeReference.
     */
    @Test
    public void testSetTree() {
        System.out.println("setTree");
        tree tree = null;
        nodeReference instance = null;
        instance.setTree(tree);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTreeRoot method, of class nodeReference.
     */
    @Test
    public void testGetTreeRoot() {
        System.out.println("getTreeRoot");
        nodeReference instance = null;
        nodeReference expResult = null;
        nodeReference result = instance.getTreeRoot();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasChilds method, of class nodeReference.
     */
    @Test
    public void testHasChilds() {
        System.out.println("hasChilds");
        nodeReference instance = null;
        boolean expResult = false;
        boolean result = instance.hasChilds();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasParent method, of class nodeReference.
     */
    @Test
    public void testHasParent() {
        System.out.println("hasParent");
        nodeReference instance = null;
        boolean expResult = false;
        boolean result = instance.hasParent();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class nodeReference.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        nodeReference instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isChild method, of class nodeReference.
     */
    @Test
    public void testIsChild() {
        System.out.println("isChild");
        nodeReference instance = null;
        boolean expResult = false;
        boolean result = instance.isChild();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isChildIndexPossible method, of class nodeReference.
     */
    @Test
    public void testIsChildIndexPossible() {
        System.out.println("isChildIndexPossible");
        int index = 0;
        nodeReference instance = null;
        boolean expResult = false;
        boolean result = instance.isChildIndexPossible(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isChildPresent method, of class nodeReference.
     */
    @Test
    public void testIsChildPresent() {
        System.out.println("isChildPresent");
        int index = 0;
        nodeReference instance = null;
        boolean expResult = false;
        boolean result = instance.isChildPresent(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isLeaf method, of class nodeReference.
     */
    @Test
    public void testIsLeaf() {
        System.out.println("isLeaf");
        nodeReference instance = null;
        boolean expResult = false;
        boolean result = instance.isLeaf();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isOrphan method, of class nodeReference.
     */
    @Test
    public void testIsOrphan() {
        System.out.println("isOrphan");
        nodeReference instance = null;
        boolean expResult = false;
        boolean result = instance.isOrphan();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isRoot method, of class nodeReference.
     */
    @Test
    public void testIsRoot() {
        System.out.println("isRoot");
        nodeReference instance = null;
        boolean expResult = false;
        boolean result = instance.isRoot();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of print method, of class nodeReference.
     */
    @Test
    public void testPrint() {
        System.out.println("print");
        nodeReference instance = null;
        String expResult = "";
        String result = instance.print();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeChild method, of class nodeReference.
     */
    @Test
    public void testRemoveChild() {
        System.out.println("removeChild");
        nodeReference child = null;
        nodeReference instance = null;
        instance.removeChild(child);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeEdge method, of class nodeReference.
     */
    @Test
    public void testRemoveEdge() {
        System.out.println("removeEdge");
        int index = 0;
        nodeReference instance = null;
        instance.removeEdge(index);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeEdgeFrom method, of class nodeReference.
     */
    @Test
    public void testRemoveEdgeFrom() {
        System.out.println("removeEdgeFrom");
        nodeReference source = null;
        nodeReference instance = null;
        instance.removeEdgeFrom(source);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeEdgeTo method, of class nodeReference.
     */
    @Test
    public void testRemoveEdgeTo() {
        System.out.println("removeEdgeTo");
        nodeReference target = null;
        nodeReference instance = null;
        instance.removeEdgeTo(target);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeParent method, of class nodeReference.
     */
    @Test
    public void testRemoveParent() {
        System.out.println("removeParent");
        nodeReference instance = null;
        instance.removeParent();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setChild method, of class nodeReference.
     */
    @Test
    public void testSetChild() {
        System.out.println("setChild");
        int index = 0;
        nodeReference child = null;
        nodeReference instance = null;
        instance.setChild(index, child);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInDegree method, of class nodeReference.
     */
    @Test
    public void testGetInDegree() {
        System.out.println("getInDegree");
        nodeReference instance = null;
        int expResult = 0;
        int result = instance.getInDegree();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOutDegree method, of class nodeReference.
     */
    @Test
    public void testGetOutDegree() {
        System.out.println("getOutDegree");
        nodeReference instance = null;
        int expResult = 0;
        int result = instance.getOutDegree();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRealOutDegree method, of class nodeReference.
     */
    @Test
    public void testGetRealOutDegree() {
        System.out.println("getRealOutDegree");
        nodeReference instance = null;
        int expResult = 0;
        int result = instance.getRealOutDegree();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeChild method, of class nodeReference.
     */
    @Test
    public void testRemoveChild_nodeReference() {
        System.out.println("removeChild");
        nodeReference child = null;
        nodeReference instance = null;
        instance.removeChild(child);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeChild method, of class nodeReference.
     */
    @Test
    public void testRemoveChild_int() {
        System.out.println("removeChild");
        int childPos = 0;
        nodeReference instance = null;
        instance.removeChild(childPos);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unsetChild method, of class nodeReference.
     */
    @Test
    public void testUnsetChild_nodeReference() {
        System.out.println("unsetChild");
        nodeReference child = null;
        nodeReference instance = null;
        instance.unsetChild(child);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unsetChild method, of class nodeReference.
     */
    @Test
    public void testUnsetChild_int() {
        System.out.println("unsetChild");
        int childPos = 0;
        nodeReference instance = null;
        instance.unsetChild(childPos);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unsetEdgeFrom method, of class nodeReference.
     */
    @Test
    public void testUnsetEdgeFrom() {
        System.out.println("unsetEdgeFrom");
        nodeReference source = null;
        nodeReference instance = null;
        instance.unsetEdgeFrom(source);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unsetEdgeTo method, of class nodeReference.
     */
    @Test
    public void testUnsetEdgeTo() {
        System.out.println("unsetEdgeTo");
        nodeReference target = null;
        nodeReference instance = null;
        instance.unsetEdgeTo(target);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unsetParent method, of class nodeReference.
     */
    @Test
    public void testUnsetParent() {
        System.out.println("unsetParent");
        nodeReference instance = null;
        instance.unsetParent();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
