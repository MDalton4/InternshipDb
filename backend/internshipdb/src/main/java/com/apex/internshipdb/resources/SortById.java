/*
 * Copyright (C) 2019  apex
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.apex.internshipdb.resources;

import com.apex.internshipdb.models.Internship;

import java.util.Comparator;

public class SortById implements Comparator<Internship> {

    @Override
    public int compare(Internship i1, Internship i2) {
        if(i1.getId() > i2.getId()){
            return 1;
        } else if (i1.getId() == i2.getId()){
            return 0;
        } else {
            return -1;
        }
    }
}
