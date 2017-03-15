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

import treeNormalizer.structure.internal.nodeReference;

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public interface edge {

    /**
     * liefert den Startknoten
     *
     * @return der Startknoten
     */
    nodeReference getSource();

    /**
     * liefert den Zielknoten
     *
     * @return der Zielknoten
     */
    nodeReference getTarget();

    /**
     * liefert den Wurzelknoten eines Baums
     *
     * @return die Wurzel des Baums
     */
    nodeReference getTreeRoot();
    
}
