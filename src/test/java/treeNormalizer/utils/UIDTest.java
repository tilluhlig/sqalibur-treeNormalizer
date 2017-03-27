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
package treeNormalizer.utils;

import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Till Uhlig {@literal <till.uhlig@student.uni-halle.de>}
 */
public class UIDTest {
    
    public UIDTest() {
    }

    /**
     * Test of nextUID method, of class UID.
     */
    @Test
    public void testNextUID() {
        System.out.println("nextUID");
        UID uid = new UID();
        HashMap<Long,Integer> list = new HashMap<>();
        for (int i=0;i<25;i++){
            long a = uid.nextUID();
            if (a==0){
                fail("darf nicht zero sein");
            }
            if (list.containsKey(a)){
                fail("eine doppelte Id");                
            }
            list.put(a, i);
        }
    }
    
}
