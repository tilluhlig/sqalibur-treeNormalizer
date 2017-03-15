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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class treeUtilitiesTest {

    public treeUtilitiesTest() {
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
