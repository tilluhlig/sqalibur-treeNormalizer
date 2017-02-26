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
import java.util.List;

/**
 *
 * @author Till
 */
public class ruleSet extends rule {

    /**
     * der Regelsatz
     */
    protected List<rule> rules = new ArrayList<>();

    /**
     * fügt eine weitere Regel hinzu (Regeln können auch mehrfach aufgenommen
     * werden)
     *
     * @param rule die einzufügende Regel
     */
    public void addRule(rule rule) {
        rules.add(rule);
    }

    /**
     * fügt eine weitere Regel hinzu (doppelte Regeln werden ignoriert)
     *
     * @param rule die einzufügende Regel
     */
    public void addRuleDistinct(rule rule) {
        if (rules.contains(rule)) {
            return;
        }
        addRule(rule);
    }

    /**
     * liefert eine Regel
     *
     * @param id die Position in der Regelliste
     * @return die gewählte Regel oder null
     */
    public rule getRule(int id) {
        if (id < 0 || id >= rules.size()) {
            return null;
        }
        return rules.get(id);
    }

    /**
     * liefert alle Regeln
     *
     * @return die Regeln
     */
    public List<rule> getRules() {
        return rules;
    }

    /**
     * setzt die Regelmenge
     *
     * @param rules die Regeln
     */
    public void setRules(List<rule> rules) {
        this.rules = rules;
    }

    /**
     * entfernt alle Regeln aus dem Regelsatz
     */
    public void resetRules() {
        this.rules.clear();
    }

    /**
     * führt die Regeln des Regelsatztes aus
     *
     * @param transform
     * @return true = eine Regel wurde angewendet, false = keine Regel wurde
     *         angewendet
     */
    @Override
    public boolean perform(transformation transform) {
        boolean result = false;
        for (rule rule : rules) {
            result |= rule.perform(transform);
        }
        return result;
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
