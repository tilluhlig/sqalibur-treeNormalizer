/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treeNormalizer.structure;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Till
 */
public class edgeTest {

    public static tree testTree;
    public static edge testEdge;
    public static nodeReference nodeA;
    public static nodeReference nodeB;
    public static nodeReference nodeC;
    public static nodeReference root;

    public edgeTest() {
        testTree = new tree("test");
        nodeA = new nodeReference(testTree, 100);
        nodeB = new nodeReference(testTree, 200);
        nodeC = new nodeReference(testTree, 300);
        root = new nodeReference(testTree, 400);
        testTree.setRoot(root);
        testEdge = new edge(nodeA, nodeB);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
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
        testEdge.setSource(nodeC);
        nodeReference result = testEdge.getSource();
        assertEquals(nodeC, result);
        assertEquals(300, result.getId());
        assertEquals(testTree, result.getTree());
        assertEquals("test", result.getTree().getName());
        testEdge.setSource(nodeA);
        assertEquals(nodeA, result);
        assertEquals(100, result.getId());
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
        testEdge.setTarget(nodeC);
        nodeReference result = testEdge.getTarget();
        assertEquals(nodeC, result);
        assertEquals(300, result.getId());
        assertEquals(testTree, result.getTree());
        assertEquals("test", result.getTree().getName());
        testEdge.setTarget(nodeB);
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
