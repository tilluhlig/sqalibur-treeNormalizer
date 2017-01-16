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
package treeNormalizer;

import treeNormalizer.structure.tree;
import treeNormalizer.structure.treeBucket;
import treeNormalizer.utils.search;

/**
 *
 * @author Till
 */
public class rule {

    /**
     * der optionale Regelname
     */
    private String name;

    /**
     * das Quellmuster
     */
    private pattern sourcePattern = null;
    /**
     * das Zielmuster
     */
    private pattern targetPattern = null;

    /**
     * erzeugt eine neue leere Regel
     */
    public rule() {

    }

    /**
     * erzeugt eine neue Regel
     *
     * @param name der Name
     */
    public rule(String name) {
        this.name = name;
    }

    /**
     * erzeugt eine neue Regel
     *
     * @param name          der Name
     * @param sourcePattern das Quellmuster
     * @param targetPattern das Zielmuster
     */
    public rule(String name, pattern sourcePattern, pattern targetPattern) {
        this.name = name;
        this.targetPattern = targetPattern;
        this.sourcePattern = sourcePattern;
    }

    /**
     * liefert den Namen der Regel
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * setzt den Namen der Regel
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * liefert das Quellmuster
     *
     * @return das Quellmuster
     */
    public pattern getSourcePattern() {
        return sourcePattern;
    }

    /**
     * setzt das Quell
     *
     * @param sourcePattern das Quellmuster
     */
    public void setSourcePattern(pattern sourcePattern) {
        this.sourcePattern = sourcePattern;
    }

    /**
     * liefert das Zielmuster
     *
     * @return das Zielmuster
     */
    public pattern getTargetPattern() {
        return targetPattern;
    }

    /**
     * setzt das Zielmuster
     *
     * @param targetPattern das Zielmuster
     */
    public void setTargetPattern(pattern targetPattern) {
        this.targetPattern = targetPattern;
    }

    /**
     * führt die Regel aus
     *
     * @param transform die Transformationsdaten
     */
    public void perform(transformation transform) {
        if (targetPattern == null || sourcePattern == null) {
            // eines der beiden Muster fehlt
            return;
        }

        // führe nun die Regel aus
        search search = transform.getSearch();
        pattern result = search.fillPattern(sourcePattern, transform);
        
        if (result == null){
            // das Muster konnte nicht befüllt werden, damit ist diese Regel
            // nicht anwendbar
            return;
        }
        
        // nun soll das Muster angwendet werden
    }

}
