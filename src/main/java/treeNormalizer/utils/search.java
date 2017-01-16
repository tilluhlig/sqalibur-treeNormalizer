/*
 * Copyright (C) 2016 Till Uhlig <till.uhlig@student.uni-halle.de>
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

import treeNormalizer.pattern;
import treeNormalizer.transformation;

/**
 * Ein Sucher versucht ein Muster mit Werten einer Transformation zu befüllen
 *
 * @author Till
 */
public abstract class search {

    /**
     * versucht ein Muster mit Werten zu befüllen
     *
     * @param pattern das Muster
     * @param transformation die Umwandlungsumgebung
     * @return das befüllte Muster oder null
     */
    public abstract pattern fillPattern(pattern pattern, transformation transformation);

}
