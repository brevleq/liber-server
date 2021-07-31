/*
 * Copyright (c) 2020 - 2021 Hudson Orsine Assumpção.
 *
 * This file is part of Liber Server.
 *
 * Liber Server is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later
 * version.
 *
 * Liber Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Liber Server.  If not, see <https://www.gnu.org/licenses/>
 */

package org.liber.utils;

import org.owasp.html.AttributePolicy;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;


public class SanitizeUtils {
    private static final AttributePolicy INTEGER = new AttributePolicy() {
        public String apply(
            String elementName, String attributeName, String value) {
            int n = value.length();
            if (n == 0) {
                return null;
            }
            for (int i = 0; i < n; ++i) {
                char ch = value.charAt(i);
                if (ch == '.') {
                    if (i == 0) {
                        return null;
                    }
                    return value.substring(0, i);  // truncate to integer.
                } else if (!('0' <= ch && ch <= '9')) {
                    return null;
                }
            }
            return value;
        }
    };

    //    CHANGE IN DEFAULT POLICY TO ALLOW "DATA"
    private static final PolicyFactory IMAGES = new HtmlPolicyBuilder()
        .allowUrlProtocols("http", "https", "data").allowElements("img")
        .allowAttributes("alt", "src").onElements("img")
        .allowAttributes("border", "height", "width").matching(INTEGER)
        .onElements("img")
        .toFactory();

    public static final String sanitizeContent(String original) {
        PolicyFactory policy = Sanitizers.FORMATTING
            .and(Sanitizers.LINKS)
            .and(Sanitizers.BLOCKS)
            .and(IMAGES)
            .and(Sanitizers.STYLES)
            .and(Sanitizers.TABLES);
        return policy.sanitize(original);
    }
}
