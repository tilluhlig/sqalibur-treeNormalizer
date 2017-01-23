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
package treeNormalizer;

import treeNormalizer.structure.treeBucket;

/**
 *
 * @author Till
 */
public class segment {

    /**
     *
     */
    private String name;

    /**
     *      */
    private ruleSet ruleSet;

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param ruleSet
     */
    public void setRuleSet(ruleSet ruleSet) {
        this.ruleSet = ruleSet;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public ruleSet getRuleSet() {
        return ruleSet;
    }

    /**
     *
     * @param transform
     */
    public void performRules(transformation transform) {
        ruleSet.perform(transform);
    }

    /**
     *
     * @param transform
     */
    public void perform(transformation transform) {
        performRules(transform);
    }

}
