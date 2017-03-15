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
package treeNormalizer.structure.document;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.NotImplementedException;
import org.jdom.Content;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Parent;
import org.jdom.filter.Filter;
import treeNormalizer.structure.*;

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class bucketDocument extends Document {

    private treeBucket bucket = null;

    private tree documentObject = null;

    public bucketDocument() {
        throw new NotImplementedException("The method is a prototype.");
    }

    public bucketDocument(List content) {
        throw new NotImplementedException("The method is a prototype.");
    }

    public bucketDocument(Element rootElement) {
        throw new NotImplementedException("The method is a prototype.");
    }

    public bucketDocument(Element rootElement, DocType docType) {
        throw new NotImplementedException("The method is a prototype.");
    }

    public bucketDocument(Element rootElement, DocType docType, String baseURI) {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public Document addContent(int index, Collection c) {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public Document addContent(int index, Content child) {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public Document addContent(Collection c) {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public Document addContent(Content child) {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public Object clone() {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public List cloneContent() {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public Element detachRootElement() {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public List getContent() {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public Content getContent(int index) {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public List getContent(Filter filter) {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public int getContentSize() {
        if (!documentObject.hasRoot()) {
            return 0;
        }
        return documentObject.getRoot().getChilds().size();
    }

    @Override
    public Iterator getDescendants() {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public Iterator getDescendants(Filter filter) {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public Document getDocument() {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public Parent getParent() {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public Object getProperty(String id) {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public Element getRootElement() {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public boolean hasRootElement() {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public int indexOf(Content child) {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public List removeContent() {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public Content removeContent(int index) {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public boolean removeContent(Content child) {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public List removeContent(Filter filter) {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public Document setContent(int index, Collection collection) {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public Document setContent(int index, Content child) {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public Document setContent(Collection newContent) {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public Document setContent(Content child) {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public void setProperty(String id, Object value) {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public Document setRootElement(Element rootElement) {
        throw new NotImplementedException("The method is a prototype.");
    }

    @Override
    public String toString() {
        throw new NotImplementedException("The method is a prototype.");
    }

}
