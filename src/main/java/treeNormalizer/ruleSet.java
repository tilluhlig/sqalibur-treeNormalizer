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
import java.util.ArrayList;

/**
 *
 * @author Till
 */
public class ruleSet {

    /**
     * der Name des Regelsatzes
     */
    private String name;

    /**
     * der Regelsatz
     */
    private ArrayList<rule> rules = new ArrayList<>();

    /**
     * fügt eine weitere Regel hinzu
     *
     * @param rule
     */
    public void addRule(rule rule) {
        rules.add(rule);
    }

    /**
     * gibt den Namen zurück
     *
     * @return der Name
     */
    public String getName() {
        return name;
    }

    /**
     * setzt den Namen
     *
     * @param name der Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * liefert eine Regel
     *
     * @param id die Position in der Regelliste
     * @return
     */
    public rule getRule(int id) {
        return rules.get(id);
    }

    /**
     * liefert alle Regeln
     *
     * @return die Regeln
     */
    public ArrayList<rule> getRules() {
        return rules;
    }

    /**
     * setzt die Regelmenge
     *
     * @param rules die Regeln
     */
    public void setRules(ArrayList<rule> rules) {
        this.rules = rules;
    }

    /**
     * führt die Regeln des Regelsatztes aus
     *
     * @param transform
     */
    public void perform(transformation transform) {
        for (rule rule : rules) {
            rule.perform(transform);
        }
    }

    /**
     * entfernt eine Regel
     *
     * @param id die Position in der Regelliste
     */
    public void removeRule(int id) {
        rules.remove(id);
    }

}
