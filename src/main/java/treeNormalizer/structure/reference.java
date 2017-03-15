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
package treeNormalizer.structure;

import java.util.ArrayList;
import treeNormalizer.structure.internal.internalEdge;
import treeNormalizer.structure.internal.internalTree;
import treeNormalizer.structure.internal.nodeReference;

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public interface reference {

    /**
     * prüft, ob das Kind im Kinderfeld existiert (der Vergleich erfolgt anhand
     * der ID der Referenz)
     *
     * @param child das Kind
     * @return true = Kind gefunden, false = Kind existiert nicht
     */
    boolean childExists(nodeReference child);

    @Override
    boolean equals(Object obj);

    /**
     * liefert das Kind an der Position id
     *
     * @param id die Position in der Kinderliste
     * @return das Kind oder null (wenn es nicht existiert oder nicht nicht
     *         gesetzt ist)
     */
    nodeReference getChild(int id);

    /**
     * liefert alle Kinder eines Knotens
     *
     * @return die Kinder
     */
    ArrayList<nodeReference> getChilds();

    /**
     * liefert eine Liste der Kinder, deren Felder tatsächlich mit Knoten belegt
     * sind
     *
     * @return die Kinder
     */
    ArrayList<nodeReference> getExistingChilds();

    /**
     * liefert die ausgehenden Kanten, welche tatsächlich mit Knoten verbunden
     * sind
     *
     * @return die ausgehenden Kanten
     */
    internalEdge[] getExistingOutgoingEdges();

    /**
     * liefert die ID der Referenz
     *
     * @return die ID
     */
    int getId();

    /**
     * liefert den Eingangsgrad eines Knotens (ob der Vater gesetzt ist oder
     * nicht)
     *
     * @return der Eingangsgrad (Anzahl der Eltern, also 1 oder 0)
     */
    int getInDegree();

    /**
     * ermittelt das linke Kind
     *
     * @return null = das Kind existiert nicht, sonst = das Kind
     */
    nodeReference getLeftChild();

    /**
     * liefert den Ausgangsgrad eines Knotens (auch leere ausgehende Kanten sind
     * enthalten)
     *
     * @return der Ausgangsgrad (Anzahl der Kindknoten)
     */
    int getOutDegree();

    /**
     * liefert die ausgehenden Kanten
     *
     * @return die Kanten
     */
    internalEdge[] getOutgoingEdges();

    /**
     * liefert den Vater/die Mutter eines Knotens
     *
     * @return die Eltern
     */
    nodeReference getParent();

    /**
     * liefert den Ausgangsgrad eines Knotens (wobei leere Kinder ignoriert
     * werden)
     *
     * @return der Ausgangsgrad (Anzahl der gesetzten Kindknoten)
     */
    int getRealOutDegree();

    /**
     * ermittelt das letzte/ rechte Kind
     *
     * @return null = das Kind existiert nicht , sonst = das Kind
     */
    nodeReference getRightChild();

    /**
     * liefert den verknüpften Baum
     *
     * @return der Baum
     */
    internalTree getTree();

    /**
     * liefert den Wurzelknoten eines Baums
     *
     * @return die Wurzel des Baums und null wenn kein Baum gesetzt ist
     */
    nodeReference getTreeRoot();

    /**
     * prüft, ob ein Knoten Kinder besitzt (existierende Kinder)
     *
     * @return true = hat keine Kinder, false = hat Kinder
     */
    boolean hasChilds();

    /**
     * prüft, ob ein Knoten einen Vater/Mutter besitzt
     *
     * @return true = hat einen Vater, false = hat keinen Vater
     */
    boolean hasParent();

    /**
     * prüft, ob der Knoten ein Kind ist (bzw. ob er Eltern hat)
     *
     * @return true = ist ein Kind, false = ist kein Kind
     */
    boolean isChild();

    /**
     * prüft, ob der index ausserhalb des Bereichs liegt
     *
     * @param index der Index
     * @return
     */
    boolean isChildIndexPossible(int index);

    /**
     * gibt aus, ob das Kindfeld mit einem Knoten besetzt ist
     *
     * @param index der Index
     * @return true = das Feld child[i] hat einen Knoten, false = es existiert
     *         kein Knoten
     */
    boolean isChildPresent(int index);

    /**
     * prüft, ob der Knoten ein Blatt ist (also keine Kinder hat)
     *
     * @return true = ist ein Blatt, false = ist kein Blatt
     */
    boolean isLeaf();

    /**
     * prüft, ob der Knoten keine Eltern hat
     *
     * @return true = keine Eltern, false = hat Eltern
     */
    boolean isOrphan();

    /**
     * prüft, ob der Knoten die Wurzel ist (also kein Vater gesetzt ist)
     *
     * @return true = ist Wurzel, false = ist nicht die Wurzel
     */
    boolean isRoot();

    /**
     * wandelt die Knotenreferenz in eine darstellbare Form um
     *
     * @return die Textdarstellung des Knotens
     */
    String print();

}
