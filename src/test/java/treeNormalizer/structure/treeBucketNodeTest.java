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
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of addEdgeFrom method, of class treeBucketNode.
     */
    @Test
    public void testAddEdgeFrom() {
        System.out.println("addEdgeFrom");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of addEdgeTo method, of class treeBucketNode.
     */
    @Test
    public void testAddEdgeTo() {
        System.out.println("addEdgeTo");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of addNodeReference method, of class treeBucketNode.
     */
    @Test
    public void testAddNodeReference() {
        System.out.println("addNodeReference");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of addNodeReferences method, of class treeBucketNode.
     */
    @Test
    public void testAddNodeReferences() {
        System.out.println("addNodeReferences");
        System.out.println("The test case is a prototype.");
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
     * Test of cloneNodeBase method, of class treeBucketNode.
     */
    @Test
    public void testCloneNodeBase() {
        System.out.println("cloneNodeBase");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of containsReferencedTree method, of class treeBucketNode.
     */
    @Test
    public void testContainsReferencedTree() {
        System.out.println("containsReferencedTree");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of decreaseUniqueId method, of class treeBucketNode.
     */
    @Test
    public void testDecreaseUniqueId() {
        System.out.println("decreaseUniqueId");
        treeBucketNode instance = new treeBucketNode("A");
        instance.decreaseUniqueId();
        assertEquals(0,instance.getUniqueId());
        instance.setUniqueId(2);
        instance.decreaseUniqueId();
        assertEquals(1,instance.getUniqueId());
        instance.decreaseUniqueId();
        assertEquals(0,instance.getUniqueId());
    }

    /**
     * Test of disconnect method, of class treeBucketNode.
     */
    @Test
    public void testDisconnect() {
        System.out.println("disconnect");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class treeBucketNode.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of getAttribute method, of class treeBucketNode.
     */
    @Test
    public void testGetAttribute() {
        System.out.println("getAttribute");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of attributeExists method, of class treeBucketNode.
     */
    @Test
    public void testAttributeExists() {
        System.out.println("attributeExists");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of getAttributes method, of class treeBucketNode.
     */
    @Test
    public void testGetAttributes() {
        System.out.println("getAttributes");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of setAttributes method, of class treeBucketNode.
     */
    @Test
    public void testSetAttributes() {
        System.out.println("setAttributes");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of getChilds method, of class treeBucketNode.
     */
    @Test
    public void testGetChilds() {
        System.out.println("getChilds");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of setChilds method, of class treeBucketNode.
     */
    @Test
    public void testSetChilds() {
        System.out.println("setChilds");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of getFirstParent method, of class treeBucketNode.
     */
    @Test
    public void testGetFirstParent() {
        System.out.println("getFirstParent");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of getLabel method, of class treeBucketNode.
     */
    @Test
    public void testGetLabel() {
        System.out.println("getLabel");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of setLabel method, of class treeBucketNode.
     */
    @Test
    public void testSetLabel() {
        System.out.println("setLabel");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of getNodeReferences method, of class treeBucketNode.
     */
    @Test
    public void testGetNodeReferences() {
        System.out.println("getNodeReferences");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of getOutgoingEdges method, of class treeBucketNode.
     */
    @Test
    public void testGetOutgoingEdges() {
        System.out.println("getOutgoingEdges");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of getParents method, of class treeBucketNode.
     */
    @Test
    public void testGetParents() {
        System.out.println("getParents");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of setParents method, of class treeBucketNode.
     */
    @Test
    public void testSetParents() {
        System.out.println("setParents");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of getReferencedTrees method, of class treeBucketNode.
     */
    @Test
    public void testGetReferencedTrees() {
        System.out.println("getReferencedTrees");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of getType method, of class treeBucketNode.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of setType method, of class treeBucketNode.
     */
    @Test
    public void testSetType() {
        System.out.println("setType");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of getUniqueId method, of class treeBucketNode.
     */
    @Test
    public void testGetUniqueId() {
        System.out.println("getUniqueId");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of hasChilds method, of class treeBucketNode.
     */
    @Test
    public void testHasChilds() {
        System.out.println("hasChilds");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of hasParents method, of class treeBucketNode.
     */
    @Test
    public void testHasParents() {
        System.out.println("hasParents");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class treeBucketNode.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of inDegree method, of class treeBucketNode.
     */
    @Test
    public void testInDegree() {
        System.out.println("inDegree");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of increaseUniqueId method, of class treeBucketNode.
     */
    @Test
    public void testIncreaseUniqueId() {
        System.out.println("increaseUniqueId");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of isChild method, of class treeBucketNode.
     */
    @Test
    public void testIsChild() {
        System.out.println("isChild");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of isChildless method, of class treeBucketNode.
     */
    @Test
    public void testIsChildless() {
        System.out.println("isChildless");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of isLeaf method, of class treeBucketNode.
     */
    @Test
    public void testIsLeaf() {
        System.out.println("isLeaf");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of isOrphan method, of class treeBucketNode.
     */
    @Test
    public void testIsOrphan() {
        System.out.println("isOrphan");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of isRoot method, of class treeBucketNode.
     */
    @Test
    public void testIsRoot() {
        System.out.println("isRoot");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of isTreeReferenced method, of class treeBucketNode.
     */
    @Test
    public void testIsTreeReferenced() {
        System.out.println("isTreeReferenced");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of numberOfNodeReferences method, of class treeBucketNode.
     */
    @Test
    public void testNumberOfNodeReferences() {
        System.out.println("numberOfNodeReferences");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of numberOfParents method, of class treeBucketNode.
     */
    @Test
    public void testNumberOfParents() {
        System.out.println("numberOfParents");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of numberOfReferencedTrees method, of class treeBucketNode.
     */
    @Test
    public void testNumberOfReferencedTrees() {
        System.out.println("numberOfReferencedTrees");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of outDegree method, of class treeBucketNode.
     */
    @Test
    public void testOutDegree() {
        System.out.println("outDegree");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of print method, of class treeBucketNode.
     */
    @Test
    public void testPrint() {
        System.out.println("print");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of rehash method, of class treeBucketNode.
     */
    @Test
    public void testRehash() {
        System.out.println("rehash");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of removeAllAttributes method, of class treeBucketNode.
     */
    @Test
    public void testRemoveAllAttributes() {
        System.out.println("removeAllAttributes");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of removeAllNodeReferences method, of class treeBucketNode.
     */
    @Test
    public void testRemoveAllNodeReferences() {
        System.out.println("removeAllNodeReferences");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of removeAttribute method, of class treeBucketNode.
     */
    @Test
    public void testRemoveAttribute() {
        System.out.println("removeAttribute");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of removeChild method, of class treeBucketNode.
     */
    @Test
    public void testRemoveChild_int() {
        System.out.println("removeChild");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of removeChild method, of class treeBucketNode.
     */
    @Test
    public void testRemoveChild_treeBucketNode() {
        System.out.println("removeChild");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of removeChildEdges method, of class treeBucketNode.
     */
    @Test
    public void testRemoveChildEdges() {
        System.out.println("removeChildEdges");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of removeEdgeFrom method, of class treeBucketNode.
     */
    @Test
    public void testRemoveEdgeFrom() {
        System.out.println("removeEdgeFrom");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of removeEdgeTo method, of class treeBucketNode.
     */
    @Test
    public void testRemoveEdgeTo() {
        System.out.println("removeEdgeTo");
        System.out.println("The test case is a prototype.");
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
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of unsetChild method, of class treeBucketNode.
     */
    @Test
    public void testUnsetChild_int() {
        System.out.println("unsetChild");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of removeNodeReference method, of class treeBucketNode.
     */
    @Test
    public void testRemoveNodeReference_int() {
        System.out.println("removeNodeReference");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of removeNodeReference method, of class treeBucketNode.
     */
    @Test
    public void testRemoveNodeReference_nodeReference() {
        System.out.println("removeNodeReference");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of removeParent method, of class treeBucketNode.
     */
    @Test
    public void testRemoveParent_treeBucketNode() {
        System.out.println("removeParent");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of removeParent method, of class treeBucketNode.
     */
    @Test
    public void testRemoveParent_int() {
        System.out.println("removeParent");
        System.out.println("The test case is a prototype.");
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
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of setAttribute method, of class treeBucketNode.
     */
    @Test
    public void testSetAttribute() {
        System.out.println("setAttribute");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of getHash method, of class treeBucketNode.
     */
    @Test
    public void testGetHash() {
        System.out.println("getHash");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of setHash method, of class treeBucketNode.
     */
    @Test
    public void testSetHash() {
        System.out.println("setHash");
        System.out.println("The test case is a prototype.");
    }

    /**
     * Test of setUniqueId method, of class treeBucketNode.
     */
    @Test
    public void testSetUniqueId() {
        System.out.println("setUniqueId");
        System.out.println("The test case is a prototype.");
    }

}
