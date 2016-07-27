/*
 * Copyright 2016  Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wirez.forms.client.fields.util;

import java.util.List;

/**
 * String utility functions
 */
public class StringUtils {

    /**
     * Puts strings inside quotes and numerics are left as they are.
     * @param str
     * @return
     */
    public static String createQuotedConstant(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return "\"" + str + "\"";
        }
        return str;
    }

    /**
     * Removes double-quotes from around a string
     * @param str
     * @return
     */
    public static String createUnquotedConstant(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        if (str.startsWith("\"")) {
            str = str.substring(1);
        }
        if (str.endsWith("\"")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public static String getStringForList(List<? extends Object> objects) {
        StringBuilder sb = new StringBuilder();
        for (Object o : objects) {
            sb.append(o.toString()).append(',');
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

}
