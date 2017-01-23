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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import treeNormalizer.structure.tree;

/**
 * Diese Klasse beschreibt ein Muster, nach dem gesucht werden kann und welches,
 * durch einen Sucher, mit Werten befüllt werden kann/soll
 *
 * @author Till
 */
public class pattern {

    /**
     * dieser Baum stellt das Muster dar
     *
     * Der Baum enthält Knoten, welche Variablen sind - der Name ist ein
     * einzelner Großbuchstabe - der Typ ist VAR
     */
    private tree template;

    private Map<String, tree> vars = new HashMap<>();

    public Set<String> getVarNames() {
        return vars.keySet();
    }

    public tree getVar(String varName) {
        if (vars.containsKey(varName)) {
            return vars.get(varName);
        }
        return null;
    }

    public Map<String, tree> getVars() {
        return vars;
    }

    public void resetRule() {
        for (Map.Entry<String, tree> entry : vars.entrySet()) {
            entry.setValue(null);
        }
    }

    public void updateTemplate() {
        extractVarNames();
    }

    private void extractVarNames() {
        // die Variablen müssen aus dem Baum extrahiert werden
        vars.clear();
    }

    public void setTemplate(tree template) {
        updateTemplate();
        this.template = template;
    }

    public tree getTemplate() {
        return template;
    }

}
