/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this xslScript file, choose Tools | Templates
 * and open the xslScript in the editor.
 */
package treeNormalizer.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Stream;
import javax.xml.parsers.*;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.SAXException;
import org.xml.sax.*;

import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.transform.JDOMResult;
import org.jdom.transform.JDOMSource;

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
     */
    public void transform() {
        String template = templateHead + xslScript + templateBottom;
    }

    /**
     * fügt Text zum xsl-Skript hinzu (anhand einer Ressource)
     *
     * @param path der Ressourcenpfad
     * @throws IOException
     */
    public void addResource(String path) throws IOException {
        URL url = getClass().getClassLoader().getResource(path);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        String res = "";
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            res += inputLine;
        }
        in.close();
        addText(res);
    }

    /**
     * fügt Text zum xsl-Skript hinzu
     *
     * @param content mehrere einzufügende Texte
     */
    public void addText(String[] content) {
        String res = "";
        for (String element : content) {
            res = res + "<xsl:include href=\"rules/wurzel.xsl\"/>";
        }
        addText(res);
    }

    /**
     * fügt Text zum xsl-Skript hinzu
     *
     * @param content der Text
     */
    public void addText(String content) {
        setXslScript(getXslScript() + content);
    }

    /**
     * setzt die XML-Quelle mittels eines Document
     *
     * @param content das Document
     * @throws TransformerConfigurationException
     * @throws TransformerException
     * @throws IOException
     */
    public void setSource(Document content) throws TransformerConfigurationException, TransformerException, IOException {
        //System.out.println(content.getRootElement().getChildren().size());
        //System.out.println(document.getRootElement().getText());
        source = new JDOMSource(content);

        JDOMResult result = new JDOMResult();
        Transformer transformer
                = TransformerFactory.newInstance().newTransformer(new StreamSource("Messung.xsl"));
        transformer.transform(source, result);

        //XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        //out.output(content, System.out);
        //out.output(result.getDocument(), System.out);
    }

    /**
     * setzt die XML-Quelle mittels eines InputStream
     *
     * @param content der InputStream
     * @throws JDOMException
     * @throws IOException
     * @throws TransformerException
     */
    public void setSource(InputStream content) throws JDOMException, IOException, TransformerException {
        Document document = new SAXBuilder().build(content);
        setSource(document);
    }

    /**
     * setzt die XML-Quelle mittels eines Textes
     *
     * @param content der Text
     * @throws JDOMException
     * @throws IOException
     * @throws TransformerException
     */
    public void setSource(String content) throws JDOMException, IOException, TransformerException {
        InputStream stream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        setSource(stream);
    }

    /**
     * setzt die XML-Quelle anhand einer Datei
     *
     * @param fileName die Datei
     * @throws JDOMException
     * @throws IOException
     * @throws TransformerConfigurationException
     * @throws TransformerException
     */
    public void setSourceFile(String fileName) throws JDOMException, IOException, TransformerConfigurationException, TransformerException {
        Document document = new SAXBuilder().build(fileName);
        setSource(document);
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
