/*
 * The MIT License
 *
 * Copyright 2018 Leif Lindbäck <leifl@kth.se>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
// Borrowed from https://github.com/KTH-IV1201/bank
package se.kth.iv1201.group4.recruitment.presentation;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Creates hamcrest matchers for an html document.
 */
public class HtmlMatchers {
    private static final String HTML_START = "<html";
    private static final String HTML_END = "</html>";

    /**
     * Creates a matcher that matches when the examined html document contains one
     * or more elements with the specified css selector.
     *
     * @param cssSelector What to search for in the examined html document.
     * @return The desired matcher.
     */
    static ElementExists containsElement(String cssSelector) {
        return new ElementExists(cssSelector);
    }

    /**
     * Creates a matcher that matches when the examined html document contains no
     * elements with the specified css selector.
     *
     * @param cssSelector What to search for in the examined html document.
     * @return The desired matcher.
     */
    static ElementDoesNotExist doesNotContainElement(String cssSelector) {
        return new ElementDoesNotExist(cssSelector);
    }

    public static class ElementExists extends TypeSafeMatcher<String> {
        private final String cssSelector;

        ElementExists(String cssSelector) {
            this.cssSelector = cssSelector;
        }

        @Override
        protected boolean matchesSafely(String httpResponse) {
            String html = extractHtmlDoc(httpResponse);
            Document htmlDoc = Jsoup.parse(html);
            Elements elements = htmlDoc.select(cssSelector);
            return !elements.isEmpty();
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("document contains element matching \"" + cssSelector + "\"");
        }
    }

    public static class ElementDoesNotExist extends TypeSafeMatcher<String> {
        private final String cssSelector;

        ElementDoesNotExist(String cssSelector) {
            this.cssSelector = cssSelector;
        }

        @Override
        protected boolean matchesSafely(String httpResponse) {
            String html = extractHtmlDoc(httpResponse);
            Document htmlDoc = Jsoup.parse(html);
            Elements elements = htmlDoc.select(cssSelector);
            return elements.isEmpty();
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("document does not contain element matching \"" + cssSelector + "\"");
        }
    }

    private static String extractHtmlDoc(String httpResponse) {
        int htmlStartPos = httpResponse.indexOf(HTML_START);
        int htmlEndPos = httpResponse.indexOf(HTML_END) + HTML_END.length();
        return httpResponse.substring(htmlStartPos, htmlEndPos);
    }
}
