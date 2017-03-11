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
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class nodeReference.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of getChilds method, of class nodeReference.
     */
    @Test
    public void testGetChilds() {
        System.out.println("getChilds");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of setChilds method, of class nodeReference.
     */
    @Test
    public void testSetChilds() {
        System.out.println("setChilds");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of getExistingChilds method, of class nodeReference.
     */
    @Test
    public void testGetExistingChilds() {
        System.out.println("getExistingChilds");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of getExistingOutgoingEdges method, of class nodeReference.
     */
    @Test
    public void testGetExistingOutgoingEdges() {
        System.out.println("getExistingOutgoingEdges");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of getId method, of class nodeReference.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        nodeReference instance = new nodeReference(null, 1);
        assertEquals(1, instance.getId());
        instance.setId(5);
        assertEquals(5, instance.getId());
    }

    /**
     * Test of setId method, of class nodeReference.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        nodeReference instance = new nodeReference(null, 1);
        assertEquals(null, instance.getId());
        instance.setId(5);
        assertEquals(5, instance.getId());
    }

    /**
     * Test of getLeftChild method, of class nodeReference.
     */
    @Test
    public void testGetLeftChild() {
        System.out.println("getLeftChild");
        nodeReference instance = new nodeReference(null, 0);
        assertEquals(null, instance.getLeftChild());
        instance.addChild(new nodeReference(null, 1));
        nodeReference a = instance.getLeftChild();
        assertNotEquals(null, a);
        assertEquals(1, a.getId());
    }

    /**
     * Test of getOutgoingEdges method, of class nodeReference.
     */
    @Test
    public void testGetOutgoingEdges() {
        System.out.println("getOutgoingEdges");
        nodeReference instance = new nodeReference(null, 31);
        assertArrayEquals(new edge[]{}, instance.getOutgoingEdges());
        instance.addChild(new nodeReference(null, 15));
        edge[] result = instance.getOutgoingEdges();
        assertEquals(1, result.length);

        // das Ziel muss passen
        nodeReference a = result[0].getTarget();
        assertNotEquals(null, a);
        assertEquals(15, a.getId());

        // die Quelle muss passen
        nodeReference b = result[0].getSource();
        assertNotEquals(null, b);
        assertEquals(31, b.getId());
    }

    /**
     * Test of getParent method, of class nodeReference.
     */
    @Test
    public void testGetParent() {
        System.out.println("getParent");
        nodeReference instance = new nodeReference(null, 31);

        // instance dürfte jetzt keinen Vater haben
        assertEquals(null, instance.getParent());
        instance.addChild(new nodeReference(null, 15));

        // instance dürfte trotzdem noch keinen Vater haben
        assertEquals(null, instance.getParent());
        nodeReference a = instance.getChild(0);
        assertNotEquals(null, a);
        nodeReference aParent = a.getParent();

        // aber das Kind sollte nun einen Vater haben
        assertNotEquals(null, aParent);
        assertEquals(31, aParent.getId());
    }

    /**
     * Test of setParent method, of class nodeReference.
     */
    @Test
    public void testSetParent() {
        System.out.println("setParent");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 25);

        // instance dürfte jetzt keinen Vater haben
        assertEquals(null, instance.getParent());
        assertEquals(false, instance.hasParent());

        instance.setParent(instance2);

        assertEquals(true, instance.hasParent());
        assertEquals(null, instance2.getParent());
        assertEquals(false, instance2.hasParent());

        nodeReference myParent = instance.getParent();

        // aber instance sollte nun einen Vater haben
        assertNotEquals(null, myParent);
        assertEquals(25, myParent.getId());
    }

    /**
     * Test of getRightChild method, of class nodeReference.
     */
    @Test
    public void testGetRightChild() {
        System.out.println("getRightChild");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of getTree method, of class nodeReference.
     */
    @Test
    public void testGetTree() {
        System.out.println("getTree");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of setTree method, of class nodeReference.
     */
    @Test
    public void testSetTree() {
        System.out.println("setTree");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of getTreeRoot method, of class nodeReference.
     */
    @Test
    public void testGetTreeRoot() {
        System.out.println("getTreeRoot");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of hasChilds method, of class nodeReference.
     */
    @Test
    public void testHasChilds() {
        System.out.println("hasChilds");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of hasParent method, of class nodeReference.
     */
    @Test
    public void testHasParent() {
        System.out.println("hasParent");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class nodeReference.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of isChild method, of class nodeReference.
     */
    @Test
    public void testIsChild() {
        System.out.println("isChild");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of isChildIndexPossible method, of class nodeReference.
     */
    @Test
    public void testIsChildIndexPossible() {
        System.out.println("isChildIndexPossible");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of isChildPresent method, of class nodeReference.
     */
    @Test
    public void testIsChildPresent() {
        System.out.println("isChildPresent");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of isLeaf method, of class nodeReference.
     */
    @Test
    public void testIsLeaf() {
        System.out.println("isLeaf");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of isOrphan method, of class nodeReference.
     */
    @Test
    public void testIsOrphan() {
        System.out.println("isOrphan");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of isRoot method, of class nodeReference.
     */
    @Test
    public void testIsRoot() {
        System.out.println("isRoot");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of print method, of class nodeReference.
     */
    @Test
    public void testPrint() {
        System.out.println("print");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of removeChild method, of class nodeReference.
     */
    @Test
    public void testRemoveChild() {
        System.out.println("removeChild");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of removeEdge method, of class nodeReference.
     */
    @Test
    public void testRemoveEdge() {
        System.out.println("removeEdge");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of removeEdgeFrom method, of class nodeReference.
     */
    @Test
    public void testRemoveEdgeFrom() {
        System.out.println("removeEdgeFrom");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of removeEdgeTo method, of class nodeReference.
     */
    @Test
    public void testRemoveEdgeTo() {
        System.out.println("removeEdgeTo");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of removeParent method, of class nodeReference.
     */
    @Test
    public void testRemoveParent() {
        System.out.println("removeParent");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of setChild method, of class nodeReference.
     */
    @Test
    public void testSetChild() {
        System.out.println("setChild");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of getInDegree method, of class nodeReference.
     */
    @Test
    public void testGetInDegree() {
        System.out.println("getInDegree");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of getOutDegree method, of class nodeReference.
     */
    @Test
    public void testGetOutDegree() {
        System.out.println("getOutDegree");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of getRealOutDegree method, of class nodeReference.
     */
    @Test
    public void testGetRealOutDegree() {
        System.out.println("getRealOutDegree");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of removeChild method, of class nodeReference.
     */
    @Test
    public void testRemoveChild_nodeReference() {
        System.out.println("removeChild");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of removeChild method, of class nodeReference.
     */
    @Test
    public void testRemoveChild_int() {
        System.out.println("removeChild");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of unsetChild method, of class nodeReference.
     */
    @Test
    public void testUnsetChild_nodeReference() {
        System.out.println("unsetChild");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of unsetChild method, of class nodeReference.
     */
    @Test
    public void testUnsetChild_int() {
        System.out.println("unsetChild");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of unsetEdgeFrom method, of class nodeReference.
     */
    @Test
    public void testUnsetEdgeFrom() {
        System.out.println("unsetEdgeFrom");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of unsetEdgeTo method, of class nodeReference.
     */
    @Test
    public void testUnsetEdgeTo() {
        System.out.println("unsetEdgeTo");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of unsetParent method, of class nodeReference.
     */
    @Test
    public void testUnsetParent() {
        System.out.println("unsetParent");
        System.out.println("The test case is a prototype.");
    }

}
