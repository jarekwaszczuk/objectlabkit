/*
 * $Id$
 * 
 * Copyright 2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.objectlab.kit.datecalc.jdk;

import java.util.Calendar;

import net.objectlab.kit.datecalc.common.AbstractPeriodCountCalculatorTest;
import net.objectlab.kit.datecalc.common.PeriodCountCalculator;
import net.objectlab.kit.datecalc.common.Utils;

public class JdkCalendarPeriodCountCalculatorTest extends AbstractPeriodCountCalculatorTest<Calendar> {

    @Override
    public PeriodCountCalculator<Calendar> getPeriodCountCalculator() {
        return CalendarKitCalculatorsFactory.getDefaultInstance().getPeriodCountCalculator();
    }

    @Override
    public Calendar parseDate(final String string) {
        return Utils.createCalendar(string);
    }

    @Override
    public Calendar getDate() {
        return Calendar.getInstance();
    }

}