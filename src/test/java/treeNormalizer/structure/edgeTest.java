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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * diese Klasse testet die edge-Klasse
 *
 * @author Till
 */
public class edgeTest {

    /**
     * ein Baum
     */
    public static tree testTree;

    /**
     * eine Kante
     */
    public static edge testEdge;

    /**
     * eine Knotenreferenz
     */
    public static nodeReference nodeA;

    /**
     * eine Knotenreferenz
     */
    public static nodeReference nodeB;

    /**
     * eine Knotenreferenz
     */
    public static nodeReference nodeC;

    /**
     * eine Knotenreferenz (Wurzel)
     */
    public static nodeReference root;

    /**
     * initialisiert die Testdaten
     */
    public edgeTest() {
        testTree = new tree("test");
        nodeA = new nodeReference(testTree, 100);
        nodeB = new nodeReference(testTree, 200);
        nodeC = new nodeReference(testTree, 300);
        root = new nodeReference(testTree, 400);
        testTree.setRoot(root);
        testEdge = new edge(nodeA, nodeB);
    }

    /**
     * Test of getSource method, of class edge.
     */
    @Test
    public void testGetSource() {
        nodeReference result = testEdge.getSource();
        assertEquals(nodeA, result);
        assertEquals(100, result.getId());
        assertEquals(testTree, result.getTree());
        assertEquals("test", result.getTree().getName());
    }

    /**
     * Test of setSource method, of class edge.
     */
    @Test
    public void testSetSource() {
        nodeReference oldSource = testEdge.getSource();

        testEdge.setSource(nodeC);
        nodeReference result = testEdge.getSource();
        assertEquals(nodeC, result);
        assertEquals(300, result.getId());
        assertEquals(testTree, result.getTree());
        assertEquals("test", result.getTree().getName());

        testEdge.setSource(oldSource);
        result = testEdge.getSource();
        assertEquals(oldSource, result);
        assertEquals(oldSource.getId(), result.getId());
    }

    /**
     * Test of getTarget method, of class edge.
     */
    @Test
    public void testGetTarget() {
        nodeReference result = testEdge.getTarget();
        assertEquals(nodeB, result);
        assertEquals(200, result.getId());
        assertEquals(testTree, result.getTree());
        assertEquals("test", result.getTree().getName());
    }

    /**
     * Test of setTarget method, of class edge.
     */
    @Test
    public void testSetTarget() {
        nodeReference oldTarget = testEdge.getTarget();

        testEdge.setTarget(nodeC);
        nodeReference result = testEdge.getTarget();
        assertEquals(nodeC, result);
        assertEquals(300, result.getId());
        assertEquals(testTree, result.getTree());
        assertEquals("test", result.getTree().getName());

        testEdge.setTarget(oldTarget);
        result = testEdge.getTarget();
        assertEquals(oldTarget, result);
        assertEquals(oldTarget.getId(), result.getId());
    }

    /**
     * Test of getTreeRoot method, of class edge.
     */
    @Test
    public void testGetTreeRoot() {
        nodeReference result = testEdge.getTreeRoot();
        assertEquals(root, result);
        assertEquals(400, result.getId());
        assertEquals(testTree, result.getTree());
        assertEquals("test", result.getTree().getName());
    }

}
