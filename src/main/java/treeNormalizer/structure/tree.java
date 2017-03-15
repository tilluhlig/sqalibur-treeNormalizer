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

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public interface tree {

    @Override
    boolean equals(Object obj);

    /**
     * gibt den Namen des Baum zurück
     *
     * @return der Name
     */
    String getName();

    /**
     * liefert den Wurzelknoten
     *
     * @return der Wurzelknoten
     */
    reference getRoot();

    /**
     * ob die Wurzel gesetzt ist
     *
     * @return ob die Wurzel gesetzt ist (true = ja, false = sonst)
     */
    boolean hasRoot();

    /**
     * wandelt den Baum in eine darstellbare Form um
     *
     * @return die Textdarstellung des Baums
     */
    String print();

}
