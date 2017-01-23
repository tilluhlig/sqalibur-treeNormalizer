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
package treeNormalizer;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Collection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.RepaintManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

//import org.w3c.dom.ElementTraversal;
/**
 * Diese Klasse enthält eine Vergleichsquelle, welche aus mehreren Segmenten besteht
 * @author Till
 */
public class test {

   /* public String out() throws IOException {
        DirectedGraph<String, DefaultEdge> directedGraph
                = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        directedGraph.addVertex("a");
        directedGraph.addVertex("b");
        directedGraph.addVertex("c");
        directedGraph.addVertex("d");
        directedGraph.addVertex("e");
        directedGraph.addVertex("f");
        directedGraph.addVertex("g");
        directedGraph.addVertex("h");
        directedGraph.addVertex("i");
        directedGraph.addEdge("a", "b");
        directedGraph.addEdge("b", "c");
        directedGraph.addEdge("c", "d");
        directedGraph.addEdge("a", "e");
        directedGraph.addEdge("e", "f");
        directedGraph.addEdge("b", "g");
        directedGraph.addEdge("e", "h");
        directedGraph.addEdge("e", "i");
        directedGraph.addEdge("c", "e");
        directedGraph.addEdge("c", "g");

        Path path = Paths.get("C:\\xampp\\htdocs\\uebungsplattform\\aa.svg");
        try {
            Files.createFile(path);
        } catch (IOException e) {

        }

        JGraph graph = new JGraph(new JGraphModelAdapter(directedGraph));
        graph.setPortsVisible(false);

        JGraphLayout layout = new JGraphCompactTreeLayout(); // or whatever layouting algorithm
        
        ConnectivityInspector sci
                = new ConnectivityInspector(directedGraph);
        List stronglyConnectedSubgraphs = sci.connectedSets();
        HashSet k = (HashSet) stronglyConnectedSubgraphs.get(0);

        JGraphFacade facade = new JGraphFacade(graph);
        layout.run(facade);

        // computes all the strongly connected components of the directed graph
        Map nested = facade.createNestedMap(false, false);
        Iterator entries = nested.entrySet().iterator();
        while (entries.hasNext()) {
            Entry thisEntry = (Entry) entries.next();
            Hashtable localcell = (Hashtable) thisEntry.getValue();
            localcell.put("borderColor", Color.red);
            localcell.replace("backgroundColor", Color.orange);
            localcell.replace("background", Color.orange);
            localcell.put("labelEnabled", false);
            localcell.put("border", BorderFactory.createLineBorder(Color.red));
            Rectangle2D bound = (Rectangle2D) localcell.get("bounds");
            localcell.put("bounds", new Rectangle2D.Double(bound.getX(), bound.getY(), 40, 20));
        }
        graph.getGraphLayoutCache().edit(nested);
    
        CellView[] cells = graph.getGraphLayoutCache().getRoots();
        for (Object entry : cells) {
            if (entry instanceof EdgeView) {
                EdgeView tmp = (EdgeView) entry;
//tmp.addExtraLabel(new Point(0, 0), "sss");
                DefaultGraphCell eval = (DefaultGraphCell) tmp.getCell();
                eval.setUserObject("");
            } else 
            if (entry instanceof VertexView) {
              VertexView tmp = (VertexView) entry;
            }

        }

        OutputStream outputStream = new FileOutputStream("C:\\xampp\\htdocs\\uebungsplattform\\aa.svg");
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);

        // Object[] cells = graph.getRoots();
        //  Rectangle2D bounds = graph.toScreen(graph.getCellBounds(cells));
        DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
        Document document = domImpl.createDocument(null, "svg", null);

        SVGGraphics2D svgGraphics = new SVGGraphics2D(document);
        //  svgGraphics.setSVGCanvasSize(new Dimension((int) Math.ceil(bounds.getWidth()), (int) Math.ceil(bounds.getHeight())));
        svgGraphics.setSVGCanvasSize(graph.getMaximumSize());
        RepaintManager repaintManager = RepaintManager.currentManager(graph);
        repaintManager.setDoubleBufferingEnabled(false);

        BasicGraphUI gui = (BasicGraphUI) graph.getUI(); // The magic is those two lines
        gui.drawGraph(svgGraphics, graph.getViewPortBounds());

        svgGraphics.stream(writer, false);
        writer.close();
        return "ENDE";
    }*/
}
