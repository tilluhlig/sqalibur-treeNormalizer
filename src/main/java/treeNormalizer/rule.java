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

/**
 * diese Klasse stellt den Aufbau einer Regel dar (die Klasse wurde nicht als
 * abstract implementiert, weil static nicht mit abstract zusammen arbeitet)
 *
 * @author Till Uhlig {@literal <till.uhlig@student.uni-halle.de>}
 */
public class rule {

    /**
     * der optionale Regelname
     */
    protected String name;

    /**
     * erzeugt eine neue leere Regel
     */
    public rule() {
        // kein Inhalt
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
     * führt die Regel aus
     *
     * @param context
     * @return true = Regel wurde angewendet, false = Regel wurde nicht
     *         angewendet
     */
    public boolean perform(transformation context) {
        return rule.performRule(context);
    }

    /**
     * führt die Regel aus
     *
     * @param context die Transformationsdaten
     * @return true = Regel wurde angewendet, false = Regel wurde nicht
     *         angewendet
     */
    public static boolean performRule(transformation context) {
        return false;
    }

}
