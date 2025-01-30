/*
 * Copyright 2017 Jorge Campins y David Uzcategui
 *
 * Este archivo forma parte de Adalid.
 *
 * Adalid es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos de la
 * licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Adalid se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA; sin
 * siquiera las garantias implicitas de COMERCIALIZACION e IDONEIDAD PARA UN PROPOSITO PARTICULAR.
 *
 * Para mas detalles vea la licencia "GNU General Public License" en http://www.gnu.org/licenses
 */
package adalid.core.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Jorge Campins
 */
public enum MimeType {

    APPLICATION, AUDIO, CHEMICAL, DRAWING, I_WORLD, IMAGE, MESSAGE, MODEL, MULTIPART, MUSIC, PALEOVU, TEXT, VIDEO, WINDOWS, WWW,
    X_CONFERENCE, X_MUSIC, X_WORLD, XGL;

    private final static String REGEX_PREFIX = "^(.*)(\\.)(";

    private final static String REGEX_SUFFIX = ")$";

    public String getRegex() {
        return getRegex(false);
    }

    public String getRegex(boolean lowerAndUpperCase) {
        String extensions = getRegexExtensions();
        return REGEX_PREFIX + extensions + (lowerAndUpperCase ? "|" + extensions.toUpperCase() : "") + REGEX_SUFFIX;
    }

    public String getRegexExtensions() {
        return Arrays.stream(getExtensions()).map(this::escape).collect(Collectors.joining("|")); // map(Pattern::quote) escape each extension
    }

    private String escape(String string) {
        return string.replaceAll("([\\\\.^$|?*+()\\[\\]{}])", "\\\\$1"); // Escapes special characters for compatibility with JavaScript
    }

    public String[] getExtensions() {
        return switch (this) {
            case APPLICATION ->
                new String[]{"a", "aab", "aam", "aas", "ai", "aim", "ani", "aos", "aps", "arc", "arj", "asx", "avi", "bcpio", "bin", "boo",
                    "book", "boz", "bsh", "bz", "bz2", "cat", "ccad", "cco", "cdf", "cer", "cha", "chat", "class", "com", "cpio", "cpt", "crl", "crt",
                    "csh", "css", "dcr", "deepv", "der", "dir", "doc", "docb", "docm", "docx", "dot", "dotm", "dotx", "dp", "drw", "dump", "dvi",
                    "dwg", "dxf", "dxr", "elc", "env", "eps", "es", "evy", "exe", "fdf", "fif", "frl", "gsp", "gss", "gtar", "gz", "gzip", "hdf",
                    "help", "hgl", "hlp", "hpg", "hpgl", "hqx", "hta", "iges", "igs", "ima", "imap", "inf", "ins", "ip", "iv", "ivy", "jcm", "js",
                    "ksh", "latex", "lha", "lhx", "lsp", "ltx", "lzh", "lzx", "man", "map", "mbd", "mc$", "mcd", "mcp", "me", "mid", "midi", "mif",
                    "mm", "mme", "mpc", "mpp", "mpt", "mpv", "mpx", "mrc", "ms", "mzz", "nc", "ncm", "nix", "nsc", "nvd", "o", "oda", "omc", "omcd",
                    "omcr", "p10", "p12", "p7a", "p7c", "p7m", "p7r", "p7s", "part", "pcl", "pdf", "pkg", "pko", "plx", "pm4", "pm5", "pnm", "pot",
                    "potm", "potx", "ppa", "ppam", "pps", "ppsm", "ppsx", "ppt", "pptm", "pptx", "ppz", "pre", "prt", "ps", "psd", "pwz", "pyc", "ras",
                    "rm", "rng", "rnx", "roff", "rtf", "rtx", "saveme", "sbk", "scm", "sdp", "sdr", "sea", "set", "sh", "shar", "sit", "skd", "skm",
                    "skp", "skt", "sl", "sldm", "sldx", "smi", "smil", "sol", "spc", "spl", "spr", "sprite", "src", "ssm", "sst", "step", "stl", "stp",
                    "sv4cpio", "sv4crc", "svr", "swf", "t", "tar", "tbk", "tcl", "tex", "texi", "texinfo", "text", "tgz", "tr", "tsp", "unv", "ustar",
                    "uu", "vcd", "vda", "vew", "vmd", "vmf", "vrml", "vsd", "vst", "vsw", "w60", "w61", "w6w", "wb1", "wbk", "web", "wiz", "wk1",
                    "wmlc", "wmlsc", "word", "wp", "wp5", "wp6", "wpd", "wq1", "wri", "wrl", "wsrc", "wtk", "xl", "xla", "xlam", "xlb", "xlc", "xld",
                    "xlk", "xll", "xlm", "xls", "xlsb", "xlsm", "xlsx", "xlt", "xltm", "xltx", "xlv", "xlw", "xml", "xpix", "z", "zip", "zoo"};
            case AUDIO ->
                new String[]{"aif", "aifc", "aiff", "au", "funk", "gsd", "gsm", "it", "jam", "kar", "la", "lam", "lma", "m2a", "m3u", "mid",
                    "midi", "mjf", "mod", "mp2", "mp3", "mpa", "mpg", "mpga", "my", "pfunk", "qcp", "ra", "ram", "rm", "rmi", "rmm", "rmp", "rpm",
                    "s3m", "sid", "snd", "tsi", "tsp", "voc", "vox", "vqe", "vqf", "vql", "wav", "xm"};
            case CHEMICAL ->
                new String[]{"pdb", "xyz"};
            case DRAWING ->
                new String[]{"dwf"};
            case I_WORLD ->
                new String[]{"ivr"};
            case IMAGE ->
                new String[]{"art", "bm", "bmp", "dwg", "dxf", "fif", "flo", "fpx", "g3", "gif", "ico", "ief", "iefs", "jfif", "jfif-tbnl",
                    "jpe", "jpeg", "jpg", "jps", "jut", "mcf", "nap", "naplps", "nif", "niff", "pbm", "pct", "pcx", "pgm", "pic", "pict", "pm", "png",
                    "pnm", "ppm", "qif", "qti", "qtif", "ras", "rast", "rf", "rgb", "rp", "svf", "tif", "tiff", "turbot", "wbmp", "x-png", "xbm",
                    "xif", "xpm", "xwd"};
            case MESSAGE ->
                new String[]{"mht", "mhtml", "mime"};
            case MODEL ->
                new String[]{"dwf", "iges", "igs", "pov", "vrml", "wrl", "wrz"};
            case MULTIPART ->
                new String[]{"gzip", "ustar", "zip"};
            case MUSIC ->
                new String[]{"kar", "mid", "midi"};
            case PALEOVU ->
                new String[]{"pvu"};
            case TEXT ->
                new String[]{"abc", "acgi", "aip", "asm", "asp", "c", "c++", "cc", "com", "conf", "cpp", "csh", "css", "cxx", "def", "el",
                    "etx", "f", "f77", "f90", "flx", "for", "g", "h", "hh", "hlb", "htc", "htm", "html", "htmls", "htt", "htx", "idc", "jav", "java",
                    "js", "ksh", "list", "log", "lsp", "lst", "lsx", "m", "mar", "mcf", "p", "pas", "pl", "pm", "properties", "py", "rexx", "rt", "rtf", "rtx", "s",
                    "scm", "sdml", "sgm", "sgml", "sh", "shtml", "spc", "ssi", "talk", "tcl", "tcsh", "text", "tsv", "txt", "uil", "uni", "unis",
                    "uri", "uris", "uu", "uue", "vcs", "wml", "wmls", "wsc", "xml", "zsh"};
            case VIDEO ->
                new String[]{"afl", "asf", "asx", "avi", "avs", "dif", "dl", "dv", "fli", "fmf", "gl", "isu", "m1v", "m2v", "mjpg", "moov",
                    "mov", "movie", "mp2", "mp3", "mp4", "mpa", "mpe", "mpeg", "mpg", "mv", "qt", "qtc", "rv", "scm", "vdo", "viv", "vivo", "vos",
                    "xdr", "xsr"};
            case WINDOWS ->
                new String[]{"wmf"};
            case WWW ->
                new String[]{"mime"};
            case X_CONFERENCE ->
                new String[]{"ice"};
            case X_MUSIC ->
                new String[]{"mid", "midi"};
            case X_WORLD ->
                new String[]{"3dm", "3dmf", "qd3", "qd3d", "svr", "vrml", "vrt", "wrl", "wrz"};
            case XGL ->
                new String[]{"xgz", "xmz"};
            default ->
                null;
        };
    }

}
