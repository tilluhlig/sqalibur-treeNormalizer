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

import org.jdom.Content;
import treeNormalizer.structure.reference;

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class bucketContent extends Content {

    protected reference ref = null;

    @Override
    public String getValue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
