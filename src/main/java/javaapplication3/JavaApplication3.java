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
package javaapplication3;

import treeNormalizer.structure.treeBucketNode;
import treeNormalizer.structure.nodeReference;
import treeNormalizer.structure.tree;
import treeNormalizer.structure.treeBucket;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.*;
import java.nio.file.Path;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import org.xml.sax.SAXException;
import treeNormalizer.transformation;

import javax.script.*;

/**
 *
 * @author Till
 */
public class JavaApplication3 {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws org.xml.sax.SAXException
     * @throws org.jdom2.JDOMException
     * @throws javax.xml.transform.TransformerConfigurationException
     * @throws javax.xml.transform.TransformerException
     * @throws javax.script.ScriptException
     */
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, TransformerConfigurationException, TransformerException, ScriptException {
        File res = new File("res");
        File[] files = res.listFiles();
        if (files != null) {
            for (File f : files) {
                if (!f.isDirectory()) {
                    f.delete();
                }
            }
        }

        treeBucket q = new treeBucket();
        tree A = q.createTree("BaumA");

        nodeReference plus = q.createNode(A, "+", "binOp");
        q.setTreeRoot(A, plus);
        nodeReference eins = q.createNode(A, "1", "const");
        nodeReference zwei = q.createNode(A, "2", "const");
        writeNodeSetToFile(q);

        q.addEdge(plus, eins);
        q.addEdge(plus, zwei);
        writeNodeSetToFile(q);

        tree B = q.createTree("BaumB");
        nodeReference plus2 = q.createNode(B, "+", "binOp");
        q.setTreeRoot(B, plus2);
        writeNodeSetToFile(q);

        nodeReference eins2 = q.createNode(B, "1", "const");
        nodeReference zwei2 = q.createNode(B, "2", "const");
        writeNodeSetToFile(q);

        q.addEdge(plus2, eins2);
        q.addEdge(plus2, zwei2);
        writeNodeSetToFile(q);

        transformation transformation = new transformation();

        /*
         * String filename = "Messung.xml"; Document document = new
         * SAXBuilder().build(filename);
         * System.out.println(document.getRootElement().getChildren().size());
         * System.out.println(document.getRootElement().getText()); Source
         * xmlFile = new JDOMSource(document);
         *
         * JDOMResult htmlResult = new JDOMResult(); Transformer transformer =
         * TransformerFactory.newInstance().newTransformer( new
         * StreamSource("Messung.xsl")); transformer.transform(xmlFile,
         * htmlResult);
         *
         * XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
         * out.output(document, System.out);
         * out.output(htmlResult.getDocument(), System.out);
         */
        // Script Engine Manager
        // ScriptEngineManager factory = new ScriptEngineManager();
        // JavaScript Engine Nashorn
        // ScriptEngine engine = factory.getEngineByName("nashorn");
        // Evaluate JavaScript code
        // engine.eval("w = window.open('text.txt');w.print();");
        // engine.eval(new FileReader("script.js"));
        System.out.println("ENDE");
    }

    private static int currentWriteFile = 0;

    /**
     *
     * @param set
     * @throws IOException
     */
    public static void writeNodeSetToFile(treeBucket set) throws IOException {
        FileWriter fw = new FileWriter("res\\" + currentWriteFile + ".txt");
        currentWriteFile++;
        try (BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(set.print());
            bw.write("\n");
            for (tree tmp : set.getTrees()) {
                bw.write(tmp.print());
            }
        }
    }

}
