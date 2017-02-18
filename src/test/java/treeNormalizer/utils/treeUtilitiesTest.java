/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treeNormalizer.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class treeUtilitiesTest {

    public treeUtilitiesTest() {
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
     * Test of getLeafs method, of class treeUtilities.
     */
    @Test
    public void testGetLeafs() throws JDOMException {
        run("<a><b></b><c><d></d></c></a>",
                "[[Element: <b/>], [Element: <d/>]]");
    }

    @Test
    public void testGetLeafs2() throws JDOMException {
        run("<a><b><c><d><e/></d></c></b></a>",
                "[[Element: <e/>]]");
    }

    @Test
    public void testGetLeafs3() throws JDOMException {
        run("<a><b></b><c><d><e><f/></e></d></c></a>",
                "[[Element: <b/>], [Element: <f/>]]");
    }

    @Test(expected=JDOMException.class)
    public void testGetLeafs4() throws JDOMException {
        // eine leere Eingabe
        run("",
                "[]");
    }

    private void run(String input, String expected) throws JDOMException {
        try {
            String content = input;
            InputStream stream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
            Document document = new SAXBuilder().build(stream);
            List<Element> res = treeUtilities.getLeafs(document);
            assertEquals(expected, res.toString());
        } catch (IOException ex) {
            Logger.getLogger(treeUtilitiesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
