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

import treeNormalizer.structure.edge;

/**
 * Diese Klasse stellt Kanten zwischen Knotenreferenzen dar. Dabei enthalten
 * Knotenreferenzen bereits implizite Kanten durch die Verwaltung von Kindern,
 * für Knotenreferenzen.
 *
 * Damit ist diese Klasse ein Hilfskonstrukt.
 *
 * @author Till
 */
public class internalEdge implements edge {

    /**
     * der Startknoten der Kante
     */
    private nodeReference source = null;

    /**
     * der Endknoten der Kante
     */
    private nodeReference target = null;

    /**
     * erzeugt eine neue Kante
     *
     * @param source der Quellknoten
     * @param target der Zielknoten
     */
    public internalEdge(nodeReference source, nodeReference target) {
        if (source.getTree() != target.getTree()) {
            throw new IllegalArgumentException("der Baum von Quelle und Ziel müssen übereinstimmen");
        }

        this.source = source;
        this.target = target;
    }

    /**
     * liefert den Startknoten
     *
     * @return der Startknoten
     */
    @Override
    public nodeReference getSource() {
        return source;
    }

    /**
     * setzt den Startknoten
     *
     * @param source der Startknoten
     */
    public void setSource(nodeReference source) {
        this.source = source;
    }

    /**
     * liefert den Zielknoten
     *
     * @return der Zielknoten
     */
    @Override
    public nodeReference getTarget() {
        return target;
    }

    /**
     * setzt den Zielknoten
     *
     * @param target der Zielknoten
     */
    public void setTarget(nodeReference target) {
        this.target = target;
    }

    /**
     * liefert den Wurzelknoten eines Baums
     *
     * @return die Wurzel des Baums
     */
    @Override
    public nodeReference getTreeRoot() {
        nodeReference tmp = source;

        if (tmp == null) {
            return null;
        }

        internalTree tree = tmp.getTree();

        if (tree == null) {
            return null;
        }

        return tree.getRoot();
    }

}
