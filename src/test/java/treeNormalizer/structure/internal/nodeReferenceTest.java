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
import treeNormalizer.structure.internal.edge;
import java.util.ArrayList;
import java.util.List;
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

        assertFalse(instance.hasChilds());
        assertNull(instance.getLeftChild());
        assertNull(instance.getRightChild());
        assertEquals(instance.getChilds(), new ArrayList<>());
        assertEquals(instance.getExistingChilds(), new ArrayList<>());

        instance.addChild(child);

        assertNull(instance.getParent());
        assertFalse(instance.hasParent());
        assertEquals(instance.getChilds(), testChildList);
        assertEquals(instance.getExistingChilds(), testChildList);
        assertEquals(instance.getLeftChild(), child);
        assertEquals(instance.getRightChild(), child);
        assertTrue(instance.hasChilds());
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

        assertFalse(instance.childExists(child));
        assertFalse(instance.hasChilds());

        instance.addChild(child);

        assertTrue(instance.hasChilds());
        assertTrue(instance.childExists(child));

        instance.addChild(child2);

        assertTrue(instance.childExists(child));
        assertTrue(instance.childExists(child2));
    }

    /**
     * Test of disconnect method, of class nodeReference.
     */
    @Test
    public void testDisconnect() {
        System.out.println("disconnect");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 1);
        nodeReference instance3 = new nodeReference(null, 2);
        nodeReference instance4 = new nodeReference(null, 3);
        instance.disconnect();
        assertFalse(instance.hasParent());
        assertFalse(instance.hasChilds());

        instance.setParent(instance2);
        instance.addChild(instance3);
        instance.addChild(instance4);
        instance.disconnect();
        assertFalse(instance.hasParent());
        assertFalse(instance.hasChilds());
    }

    /**
     * Test of equals method, of class nodeReference.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 15);
        nodeReference instance3 = new nodeReference(null, 15);
        assertFalse(instance.equals(instance2));
        assertTrue(instance2.equals(instance3));
    }

    /**
     * Test of getChilds method, of class nodeReference.
     */
    @Test
    public void testGetChilds() {
        System.out.println("getChilds");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 1);
        nodeReference instance3 = new nodeReference(null, 2);
        assertEquals(0, instance.getChilds().size());
        instance.addChild(instance2);
        assertArrayEquals(new nodeReference[]{instance2}, instance.getChilds().toArray(new nodeReference[0]));
        instance.addChild(instance3);
        assertArrayEquals(new nodeReference[]{instance2, instance3}, instance.getChilds().toArray(new nodeReference[0]));
        instance.removeChild(instance2);
        assertArrayEquals(new nodeReference[]{instance3}, instance.getChilds().toArray(new nodeReference[0]));
    }

    /**
     * Test of setChilds method, of class nodeReference.
     */
    @Test
    public void testSetChilds() {
        System.out.println("setChilds");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 1);
        nodeReference instance3 = new nodeReference(null, 2);

        instance.setChilds(null);
        assertNull(instance.getChilds());

        ArrayList<nodeReference> childs = new ArrayList<>();
        childs.add(instance2);
        childs.add(instance3);
        instance.setChilds(childs);
        assertArrayEquals(new nodeReference[]{instance2, instance3}, instance.getChilds().toArray(new nodeReference[0]));
    }

    /**
     * Test of getExistingChilds method, of class nodeReference.
     */
    @Test
    public void testGetExistingChilds() {
        System.out.println("getExistingChilds");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 1);
        nodeReference instance3 = new nodeReference(null, 2);
        assertEquals(0, instance.getExistingChilds().size());

        // jetzt werden Kinder eingefügt
        instance.addChild(instance2);
        assertEquals(1, instance.getExistingChilds().size());
        assertArrayEquals(new nodeReference[]{instance2}, instance.getExistingChilds().toArray(new nodeReference[0]));
        instance.addChild(instance3);
        assertEquals(2, instance.getExistingChilds().size());
        assertArrayEquals(new nodeReference[]{instance2, instance3}, instance.getExistingChilds().toArray(new nodeReference[0]));

        // jetzt werden Kinder entfernt
        instance.unsetChild(0);
        assertEquals(1, instance.getExistingChilds().size());
        assertArrayEquals(new nodeReference[]{instance3}, instance.getExistingChilds().toArray(new nodeReference[0]));
    }

    /**
     * Test of getExistingOutgoingEdges method, of class nodeReference.
     */
    @Test
    public void testGetExistingOutgoingEdges() {
        System.out.println("getExistingOutgoingEdges");
        nodeReference instance = new nodeReference(null, 31);
        assertArrayEquals(new edge[]{}, instance.getExistingOutgoingEdges());
        instance.addChild(new nodeReference(null, 15));
        edge[] result = instance.getExistingOutgoingEdges();
        assertEquals(1, result.length);

        // wenn wir das Kind nochmal einfügen, dann sollte es keine Veränderung geben
        instance.addChild(new nodeReference(null, 15));
        assertEquals(1, instance.getExistingOutgoingEdges().length);

        // das Ziel muss passen
        nodeReference a = result[0].getTarget();
        assertNotEquals(null, a);
        assertEquals(15, a.getId());

        // die Quelle muss passen
        nodeReference b = result[0].getSource();
        assertNotEquals(null, b);
        assertEquals(31, b.getId());

        // wir wollen noch ein weiteres Kind einfügen
        instance.addChild(new nodeReference(null, 17));
        assertEquals(2, instance.getExistingOutgoingEdges().length);
        instance.unsetChild(0);
        assertEquals(1, instance.getExistingOutgoingEdges().length);
        instance.unsetChild(1);
        assertEquals(0, instance.getExistingOutgoingEdges().length);
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
        assertEquals(1, instance.getId());
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
        assertNull(instance.getLeftChild());
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
        nodeReference instance2 = new nodeReference(null, 25);

        // instance dürfte jetzt keinen Vater haben
        assertNull(instance.getParent());

        instance2.setParent(instance);

        // aber das Kind sollte nun einen Vater haben
        nodeReference aParent = instance2.getParent();
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
        assertNull(instance.getParent());
        assertFalse(instance.hasParent());

        instance.setParent(instance2);

        assertTrue(instance.hasParent());
        assertNull(instance2.getParent());
        assertFalse(instance2.hasParent());

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
        nodeReference instance = new nodeReference(null, 0);

        // er hat noch kein Kind
        assertNull(instance.getRightChild());
        instance.addChild(new nodeReference(null, 1));

        nodeReference a = instance.getRightChild();
        assertNotEquals(null, a);
        assertEquals(1, a.getId());

        instance.addChild(new nodeReference(null, 2));
        nodeReference b = instance.getRightChild();
        assertNotEquals(null, b);
        assertEquals(2, b.getId());
    }

    /**
     * Test of getTree method, of class nodeReference.
     */
    @Test
    public void testGetTree() {
        System.out.println("getTree");
        nodeReference instance = new nodeReference(null, 0);
        assertNull(instance.getTree());
        instance.setTree(new tree("test"));

        tree a = instance.getTree();
        assertNotEquals(null, a);
        assertEquals("test", a.getName());
    }

    /**
     * Test of setTree method, of class nodeReference.
     */
    @Test
    public void testSetTree() {
        System.out.println("setTree");
        nodeReference instance = new nodeReference(null, 0);
        assertNull(instance.getTree());

        instance.setTree(new tree("test"));

        tree a = instance.getTree();
        assertNotEquals(null, a);
        assertEquals("test", a.getName());

        instance.setTree(new tree("test2"));

        tree b = instance.getTree();
        assertNotEquals(null, b);
        assertEquals("test2", b.getName());
    }

    /**
     * Test of getTreeRoot method, of class nodeReference.
     */
    @Test
    public void testGetTreeRoot() {
        System.out.println("getTreeRoot");
        nodeReference instance2 = new nodeReference(null, 15);

        nodeReference instance = new nodeReference(null, 0);
        assertNull(instance.getTree());

        instance.setTree(new tree("test"));

        tree a = instance.getTree();
        assertNotEquals(null, a);
        assertEquals("test", a.getName());
        assertNull(a.getRoot());

        a.setRoot(instance2);
        nodeReference aRoot = a.getRoot();
        assertNotEquals(null, aRoot);
        assertEquals(15, aRoot.getId());
    }

    /**
     * Test of hasChilds method, of class nodeReference.
     */
    @Test
    public void testHasChilds() {
        System.out.println("hasChilds");
        nodeReference instance = new nodeReference(null, 0);
        assertFalse(instance.hasChilds());
        instance.addChild(new nodeReference(null, 1));
        assertTrue(instance.hasChilds());
        instance.addChild(new nodeReference(null, 2));
        assertTrue(instance.hasChilds());
        instance.removeChild(0);
        assertTrue(instance.hasChilds());
    }

    /**
     * Test of hasParent method, of class nodeReference.
     */
    @Test
    public void testHasParent() {
        System.out.println("hasParent");
        nodeReference instance = new nodeReference(null, 31);

        // instance dürfte jetzt keinen Vater haben
        assertFalse(instance.hasParent());
        instance.addChild(new nodeReference(null, 15));

        // instance dürfte trotzdem noch keinen Vater haben
        assertFalse(instance.hasParent());
        nodeReference a = instance.getChild(0);
        assertNotEquals(null, a);
        assertFalse(a.hasParent());
    }

    /**
     * Test of isChild method, of class nodeReference.
     */
    @Test
    public void testIsChild() {
        System.out.println("isChild");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 15);
        assertFalse(instance.isChild());
        instance2.setParent(instance);
        assertFalse(instance.isChild());
        assertTrue(instance2.isChild());
    }

    /**
     * Test of isChildIndexPossible method, of class nodeReference.
     */
    @Test
    public void testIsChildIndexPossible() {
        System.out.println("isChildIndexPossible");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 15);
        nodeReference instance3 = new nodeReference(null, 16);
        assertFalse(instance.isChildIndexPossible(0));
        instance.addChild(instance2);
        assertTrue(instance.isChildIndexPossible(0));
        assertFalse(instance.isChildIndexPossible(1));
        instance.addChild(instance3);
        assertTrue(instance.isChildIndexPossible(0));
        assertTrue(instance.isChildIndexPossible(1));
        instance.removeChild(0);
        assertTrue(instance.isChildIndexPossible(0));
        instance.removeChild(0);
        assertFalse(instance.isChildIndexPossible(0));
    }

    /**
     * Test of isChildPresent method, of class nodeReference.
     */
    @Test
    public void testIsChildPresent() {
        System.out.println("isChildPresent");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 15);
        assertFalse(instance.isChildPresent(0));
        instance.addChild(instance2);
        assertTrue(instance.isChildPresent(0));
        assertFalse(instance.isChildPresent(1));
        instance.unsetChild(0);
        assertFalse(instance.isChildPresent(0));
    }

    /**
     * Test of isLeaf method, of class nodeReference.
     */
    @Test
    public void testIsLeaf() {
        System.out.println("isLeaf");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 15);
        assertTrue(instance.isLeaf());
        instance.addChild(instance2);
        instance2.setParent(instance);
        assertFalse(instance.isLeaf());
        assertTrue(instance2.isLeaf());
    }

    /**
     * Test of isOrphan method, of class nodeReference.
     */
    @Test
    public void testIsOrphan() {
        System.out.println("isOrphan");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 15);
        assertTrue(instance.isOrphan());
        instance.addChild(instance2);
        instance2.setParent(instance);
        assertTrue(instance.isOrphan());
        assertFalse(instance2.isOrphan());
    }

    /**
     * Test of isRoot method, of class nodeReference.
     */
    @Test
    public void testIsRoot() {
        System.out.println("isRoot");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 15);
        assertTrue(instance.isRoot());

        instance.addChild(instance2);
        instance2.setParent(instance);
        assertTrue(instance.isRoot());
        assertFalse(instance2.isRoot());
    }

    /**
     * Test of print method, of class nodeReference.
     */
    @Test
    public void testPrint() {
        System.out.println("print");
        nodeReference instance = new nodeReference(null, 31);
        assertNotNull(instance.print());
    }

    /**
     * Test of removeChild method, of class nodeReference.
     */
    @Test
    public void testRemoveChild() {
        System.out.println("removeChild");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 15);
        instance.addChild(instance2);
        assertTrue(instance.hasChilds());
        instance.removeChild(0);
        assertFalse(instance.hasChilds());
    }

    /**
     * Test of removeEdgeFrom method, of class nodeReference.
     */
    @Test
    public void testRemoveEdgeFrom() {
        System.out.println("removeEdgeFrom");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 15);
        instance.addChild(instance2);
        instance2.setParent(instance);
        instance2.removeEdgeFrom(instance);
        assertFalse(instance.hasChilds());
        assertFalse(instance2.hasChilds());
        assertFalse(instance.hasParent());
        assertFalse(instance2.hasParent());
    }

    /**
     * Test of removeEdgeTo method, of class nodeReference.
     */
    @Test
    public void testRemoveEdgeTo() {
        System.out.println("removeEdgeTo");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 15);
        instance.addChild(instance2);
        instance2.setParent(instance);
        instance.removeEdgeTo(instance2);
        assertFalse(instance.hasChilds());
        assertFalse(instance2.hasChilds());
        assertFalse(instance.hasParent());
        assertFalse(instance2.hasParent());
    }

    /**
     * Test of removeParent method, of class nodeReference.
     */
    @Test
    public void testRemoveParent() {
        System.out.println("removeParent");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 15);
        instance.setParent(instance2);
        assertTrue(instance.hasParent());
        instance.removeParent();
        assertFalse(instance.hasParent());
    }

    /**
     * Test of setChild method, of class nodeReference.
     */
    @Test
    public void testSetChild() {
        System.out.println("setChild");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 15);
        nodeReference instance3 = new nodeReference(null, 14);
        instance.addChild(instance2);
        assertEquals(15, instance.getChild(0).getId());
        instance.setChild(0, instance3);
        assertEquals(14, instance.getChild(0).getId());
    }

    /**
     * Test of getInDegree method, of class nodeReference.
     */
    @Test
    public void testGetInDegree() {
        System.out.println("getInDegree");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 15);
        assertEquals(0, instance.getInDegree());
        instance.setParent(instance2);
        assertEquals(1, instance.getInDegree());
    }

    /**
     * Test of getOutDegree method, of class nodeReference.
     */
    @Test
    public void testGetOutDegree() {
        System.out.println("getOutDegree");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 15);
        assertEquals(0, instance.getOutDegree());
        instance.addChild(instance2);
        assertEquals(1, instance.getOutDegree());
    }

    /**
     * Test of getRealOutDegree method, of class nodeReference.
     */
    @Test
    public void testGetRealOutDegree() {
        System.out.println("getRealOutDegree");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 15);
        assertEquals(0, instance.getRealOutDegree());
        instance.addChild(instance2);
        assertEquals(1, instance.getRealOutDegree());
        instance.unsetChild(0);
        assertEquals(0, instance.getRealOutDegree());
    }

    /**
     * Test of removeChild method, of class nodeReference.
     */
    @Test
    public void testRemoveChild_nodeReference() {
        System.out.println("removeChild");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 15);
        instance.addChild(instance2);
        assertTrue(instance.hasChilds());
        instance.removeChild(instance2);
        assertFalse(instance.hasChilds());
    }

    /**
     * Test of removeChild method, of class nodeReference.
     */
    @Test
    public void testRemoveChild_int() {
        System.out.println("removeChild");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 15);
        instance.addChild(instance2);
        assertTrue(instance.hasChilds());
        instance.removeChild(0);
        assertFalse(instance.hasChilds());
    }

    /**
     * Test of unsetChild method, of class nodeReference.
     */
    @Test
    public void testUnsetChild_nodeReference() {
        System.out.println("unsetChild");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 15);
        instance.addChild(instance2);
        assertNotEquals(null, instance.getChild(0));
        instance.unsetChild(instance2);
        assertNull(instance.getChild(0));
        assertEquals(0, instance.getExistingChilds().size());
        assertEquals(1, instance.getChilds().size());
        instance.unsetChild(instance2);
        assertNull(instance.getChild(0));
        assertEquals(0, instance.getExistingChilds().size());
        assertEquals(1, instance.getChilds().size());
    }

    /**
     * Test of unsetChild method, of class nodeReference.
     */
    @Test
    public void testUnsetChild_int() {
        System.out.println("unsetChild");
        System.out.println("unsetChild");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 15);
        instance.addChild(instance2);
        assertNotEquals(null, instance.getChild(0));
        instance.unsetChild(0);
        assertNull(instance.getChild(0));
        assertEquals(0, instance.getExistingChilds().size());
        assertEquals(1, instance.getChilds().size());
        instance.unsetChild(0);
        assertNull(instance.getChild(0));
        assertEquals(0, instance.getExistingChilds().size());
        assertEquals(1, instance.getChilds().size());
    }

    /**
     * Test of unsetEdgeFrom method, of class nodeReference.
     */
    @Test
    public void testUnsetEdgeFrom() {
        System.out.println("unsetEdgeFrom");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 15);
        instance.addChild(instance2);
        instance2.setParent(instance);
        instance2.unsetEdgeFrom(instance);
        assertFalse(instance.hasChilds());
        assertFalse(instance2.hasChilds());
        assertFalse(instance.hasParent());
        assertFalse(instance2.hasParent());
    }

    /**
     * Test of unsetEdgeTo method, of class nodeReference.
     */
    @Test
    public void testUnsetEdgeTo() {
        System.out.println("unsetEdgeTo");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 15);
        instance.addChild(instance2);
        instance2.setParent(instance);
        instance.unsetEdgeTo(instance2);
        assertFalse(instance.hasChilds());
        assertFalse(instance2.hasChilds());
        assertFalse(instance.hasParent());
        assertFalse(instance2.hasParent());
    }

    /**
     * Test of unsetParent method, of class nodeReference.
     */
    @Test
    public void testUnsetParent() {
        System.out.println("unsetParent");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 15);
        assertFalse(instance.hasParent());
        instance.setParent(instance2);
        assertTrue(instance.hasParent());
        instance.unsetParent();
        assertFalse(instance.hasParent());
        assertNull(instance.getParent());
    }

    /**
     * Test of getChild method, of class nodeReference.
     */
    @Test
    public void testGetChild() {
        System.out.println("getChild");
        nodeReference instance = new nodeReference(null, 31);
        nodeReference instance2 = new nodeReference(null, 1);
        nodeReference instance3 = new nodeReference(null, 2);
        nodeReference instance4 = new nodeReference(null, 3);
        instance.addChild(instance2);
        instance.addChild(instance3);
        instance.addChild(null);
        instance.addChild(instance4);
        assertEquals(instance2,instance.getChild(0));
        assertEquals(instance3,instance.getChild(1));
        assertEquals(null,instance.getChild(2));
        assertEquals(instance4,instance.getChild(3));
    }

    /**
     * Test of hashCode method, of class nodeReference.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        System.out.println("The test case is a prototype.");
    }

}
