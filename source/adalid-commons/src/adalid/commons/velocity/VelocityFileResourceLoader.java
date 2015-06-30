/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los términos
 * de la licencia "GNU General Public License" publicada por la Fundación "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser útil, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas información.
 */
package adalid.commons.velocity;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jorge Campins
 */
public class VelocityFileResourceLoader extends org.apache.velocity.runtime.resource.loader.FileResourceLoader {

    @Override
    public synchronized InputStream getResourceStream(String name) {
        return new IndentationFilterInputStream(super.getResourceStream(name));
    }

    public static class IndentationFilterInputStream extends FilterInputStream {

        protected String line;

        protected int next;

        protected boolean eol;

        protected boolean eof;

        public IndentationFilterInputStream(InputStream resourceStream) {
            super(resourceStream);
            eol = true;
            eof = false;
        }

        @Override
        public int read() throws IOException {
            int ch = -1;
            if (eol) {
                if (eof) {
                    return ch;
                }
                readLine();
            }
            if (next < line.length()) {
                ch = (int) line.charAt(next++);
            }
            eol = (next == line.length());
            return ch;
        }

        @Override
        public int read(byte[] b) throws IOException {
            return read(b, 0, b.length);
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            int i;
            int ok = 0;
            while (len-- > 0) {
                i = read();
                if (i == -1) {
                    return (ok == 0) ? -1 : ok;
                }
                b[off++] = (byte) i;
                ok++;
            }
            return ok;
        }

        private void readLine() throws IOException {
            line = "";
            next = 0;
            int ch;
            search:
            while (true) {
                ch = in.read();
                switch (ch) {
                    case -1:
                        eof = true;
                        break search;
                    case '\n':
                        line += '\n';
                        break search;
                    default:
                        line += (char) ch;
                        break;
                }
            }
            boolean startsWithDots = false;
            for (int i = 0; i < line.length(); i++) {
                ch = (int) line.charAt(i);
                if (ch == '.') {
                    startsWithDots = true;
                    continue;
                }
                if (startsWithDots && (i % 4) == 0) {
                    next = i;
                    break;
                }
                if (ch != 32) {
                    if (ch == '#') {
                        next = i;
                    }
                    break;
                }
            }
        }

        @Override
        public boolean markSupported() {
            return false;
        }

    }

}
