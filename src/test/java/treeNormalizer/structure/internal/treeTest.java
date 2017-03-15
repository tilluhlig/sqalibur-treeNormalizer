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
import treeNormalizer.structure.internal.internalTree;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class treeTest {

    public treeTest() {
    }

    /**
     * Test of equals method, of class internalTree.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        internalTree instance = new internalTree("test");
        internalTree instance2 = new internalTree("test");
        nodeReference nodeA = new nodeReference(null, 1);
        nodeReference nodeB = new nodeReference(null, 2);

        assertEquals(true, instance.equals(instance2));
        instance.setName("test2");
        assertEquals(false, instance.equals(instance2));
        instance.setName("test");
        instance.setRoot(nodeA);
        assertEquals(false, instance.equals(instance2));
        instance2.setRoot(nodeB);
        assertEquals(false, instance.equals(instance2));
        instance2.setRoot(nodeA);
        assertEquals(true, instance.equals(instance2));
    }

    /**
     * Test of getName method, of class internalTree.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        internalTree instance = new internalTree("test");
        assertEquals("test", instance.getName());
    }

    /**
     * Test of setName method, of class internalTree.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        internalTree instance = new internalTree("test");
        assertEquals("test", instance.getName());
        instance.setName("test2");
        assertEquals("test2", instance.getName());
        instance.setName(null);
        assertEquals(null, instance.getName());
    }

    /**
     * Test of getRoot method, of class internalTree.
     */
    @Test
    public void testGetRoot() {
        System.out.println("getRoot");
        internalTree instance = new internalTree("test");
        nodeReference instance2 = new nodeReference(null, 31);
        assertEquals(null, instance.getRoot());

        instance.setRoot(instance2);
        assertEquals(instance2, instance.getRoot());
    }

    /**
     * Test of setRoot method, of class internalTree.
     */
    @Test
    public void testSetRoot() {
        System.out.println("setRoot");
        internalTree instance = new internalTree("test");
        nodeReference instance2 = new nodeReference(null, 31);

        instance.setRoot(instance2);
        assertEquals(instance2, instance.getRoot());

        instance.setRoot(null);
        assertEquals(null, instance.getRoot());
    }

    /**
     * Test of hasRoot method, of class internalTree.
     */
    @Test
    public void testHasRoot() {
        System.out.println("hasRoot");
        internalTree instance = new internalTree("test");
        nodeReference instance2 = new nodeReference(null, 31);

        assertEquals(false, instance.hasRoot());
        instance.setRoot(instance2);
        assertEquals(true, instance.hasRoot());
    }

    /**
     * Test of print method, of class internalTree.
     */
    @Test
    public void testPrint() {
        System.out.println("print");
        // kein Test (was da lesbar gedruckt wird, ist mir egal
    }

}
