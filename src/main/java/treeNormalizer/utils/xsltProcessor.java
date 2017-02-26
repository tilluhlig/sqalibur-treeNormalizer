/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this xslScript file, choose Tools | Templates
 * and open the xslScript in the editor.
 */
package treeNormalizer.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.xml.parsers.*;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.SAXException;
import org.xml.sax.*;

import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.transform.JDOMResult;
import org.jdom.transform.JDOMSource;
import treeNormalizer.structure.tree;

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class xsltProcessor {
    // private static 

    private Source source = null;
    private String xslScript = "";
    private final String templateBottom = "</xsl:stylesheet>";
    private final String templateHead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" xmlns:ext=\"http://exslt.org/common\" exclude-result-prefixes=\"ext\" version=\"1.0\"><xsl:output method=\"xml\"/><xsl:strip-space elements=\"*\"/>";

    /**
     * wandelt die source mittels des xslScript um
     *
     * @return das transformierte Dokument
     */
    public Document transform() {
        try {
            String template = templateHead + xslScript + templateBottom;

            InputStream stream = new ByteArrayInputStream(template.getBytes(StandardCharsets.UTF_8));
            Document document = new SAXBuilder().build(stream);
            Source xslt = new JDOMSource(document);

            JDOMResult result = new JDOMResult();
            Transformer transformer = null;
            try {
                transformer = TransformerFactory.newInstance().newTransformer(xslt);
            } catch (TransformerConfigurationException ex) {
                Logger.getLogger(xsltProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (transformer != null) {
                try {
                    transformer.transform(source, result);
                } catch (TransformerException ex) {
                    Logger.getLogger(xsltProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
            //out.output(result, System.out);

            //out.output(result.getDocument(), System.out);
            return result.getDocument();
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(xsltProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * wandelt ein Document in eine XML Darstellung um
     * @param document ein Document
     * @return das Dokument als XML
     */
    public static String DocumentToXml(Document document) {
        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        Writer writer = new StringWriter();
        try {
            out.output(document, writer);
        } catch (IOException ex) {
            Logger.getLogger(xsltProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        String data = writer.toString();
        return data;
    }

    /**
     * fügt Text zum xsl-Skript hinzu (anhand einer Ressource)
     *
     * @param path der Ressourcenpfad
     */
    public void addResourceToScript(String path) {
        BufferedReader in = null;
        try {
            URL url = getClass().getClassLoader().getResource(path);
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            String res = "";
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                res += inputLine;
            }
            in.close();
            addTextToScript(res);
        } catch (IOException ex) {
            Logger.getLogger(xsltProcessor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(xsltProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *
     * @param path
     */
    public void includeResourceToScript(String path) {
        addTextToScript("<xsl:include href=\"loader/" + path + "\"/>");
    }

    /**
     * fügt Text zum xsl-Skript hinzu
     *
     * @param content mehrere einzufügende Texte
     */
    public void addTextToScript(String[] content) {
        String res = "";
        for (String element : content) {
            res = res + element;
        }
        addTextToScript(res);
    }

    /**
     * fügt Text zum xsl-Skript hinzu
     *
     * @param content der Text
     */
    public void addTextToScript(String content) {
        setXslScript(getXslScript() + content);
    }

    /**
     * setzt die XML-Quelle mittels eines Document
     *
     * @param content das Document
     */
    public void setSource(Document content) {
        //System.out.println(content.getRootElement().getChildren().size());
        //System.out.println(document.getRootElement().getText());
        source = new JDOMSource(content);

        // wir müssen zwischen XML und dem Rest hin und her umformen
        ////////////////JDOMSource q = (JDOMSource) source;
        /////////////////// Document p = q.getDocument();
        //////////////////////////////  List a = q.getNodes();
    }

    /**
     * setzt die XML-Quelle mittels eines InputStream
     *
     * @param content der InputStream
     */
    public void setSource(InputStream content) {
        try {
            Document document = new SAXBuilder().build(content);
            setSource(document);
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(xsltProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * setzt die XML-Quelle mittels eines Textes
     *
     * @param content der Text
     */
    public void setSource(String content) {
        InputStream stream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        setSource(stream);
    }

    /**
     * setzt die XML-Quelle anhand einer Datei
     *
     * @param fileName die Datei
     */
    public void setSourceByFile(String fileName) {
        try {
            Document document = new SAXBuilder().build(fileName);
            setSource(document);
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(xsltProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param path
     */
    public void setSourceByResource(String path) {
        try {
            BufferedReader in;
            URL url = getClass().getClassLoader().getResource(path);
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            String res = "";
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                res += inputLine;
            }
            in.close();
            setSource(res);
        } catch (IOException ex) {
            Logger.getLogger(xsltProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * liefert den Inhalt des aktuellen Skripts
     *
     * @return the xslScript
     */
    public String getXslScript() {
        return xslScript;
    }

    /**
     * setzt den Inhalt des Skripts
     *
     * @param template the xslScript to set
     */
    public void setXslScript(String template) {
        this.xslScript = template;
    }

}
