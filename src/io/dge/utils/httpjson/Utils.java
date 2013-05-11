package io.dge.utils.httpjson;

import java.io.InputStream;
import java.util.Scanner;

class Utils {
    /**
     * Reads an InputStream into a String
     *
     * @param in    The InputStream
     * @return  A String representation of the contents of the stream, or
     *          null if the stream is empty.
     */
    public static String inputStreamToString (InputStream in) {
        return inputStreamToString(in, "UTF-8");
    }

    /**
     * Same as the above {@link #inputStreamToString(java.io.InputStream)},
     * except that one can specify the encoding.
     *
     * @param in    The InputStream
     * @param encoding  The encoding to use
     * @return  A String representation of the contents of the stream, or
     *          null if the stream is empty.
     */
    public static String inputStreamToString (InputStream in, String encoding) {
        Scanner s = new Scanner(in, encoding).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static class Tuple<F,S> {
        private F first;
        private S second;

        public Tuple(F first, S second) {
            this.first = first;
            this.second = second;
        }

        public F getFirst() {
            return first;
        }

        public S getSecond() {
            return second;
        }
    }
}
