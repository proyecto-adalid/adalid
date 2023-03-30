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
package adalid.commons.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Jorge Campins
 */
public class LoremIpsum {

    /**
     * The standard Lorem Ipsum passage, used since the 1500s
     */
    private static final String S1 = ""
        + "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. "
        + "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
        + "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
        + "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        + "";

    /**
     * Section 1.10.32 of "de Finibus Bonorum et Malorum", written by Cicero in 45 BC
     */
    private static final String S2 = ""
        + "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, "
        + "eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. "
        + "Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, "
        + "sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. "
        + "Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, "
        + "sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. "
        + "Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? "
        + "Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, "
        + "vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?"
        + "";

    /**
     * Section 1.10.33 of "de Finibus Bonorum et Malorum", written by Cicero in 45 BC
     */
    private static final String S3 = ""
        + "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et "
        + "quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, "
        + "id est laborum et dolorum fuga. "
        + "Et harum quidem rerum facilis est et expedita distinctio. "
        + "Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, "
        + "omnis voluptas assumenda est, omnis dolor repellendus. "
        + "Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae "
        + "non recusandae. "
        + "Itaque earum rerum hic tenetur a sapiente delectus, "
        + "ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat."
        + "";

    private static final String[] SX = sx();

    private static final int[] LX = lx();

    private static String[] sx() {
        String[] split = StringUtils.split(S1, ".,");
        String[] array = new String[split.length + 4];
        String string = "";
        String substring, separator;
        int i;
        for (i = 0; i < split.length; i++) {
            substring = split[i].trim();
            separator = i == 0 ? "" : Character.isUpperCase(substring.charAt(0)) ? ". " : ", ";
            string += separator + substring;
            array[i] = string + ".";
        }
        array[i++] = S2;
        array[i++] = S1 + " " + S2;
        array[i++] = S2 + " " + S3;
        array[i++] = S1 + " " + S2 + " " + S3;
        return array;
    }

    private static int[] lx() {
        int[] array = new int[SX.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = SX[i].length();
        }
        return array;
    }

    /*
    public static void main(String[] args) {
        for (int i = 0; i < LX.length; i++) {
            System.out.println(LX[i] + "\t" + SX[i]);
        }
        for (int i = 3; i < 20; i = i + 5) {
            String string = getString(i);
            System.out.println(i + "\t" + string.length() + "\t" + string);
        }
        for (int i = 25; i < 500; i = i + 30) {
            String string = getString(i);
            System.out.println(i + "\t" + string.length() + "\t" + string);
        }
        for (int i = 900; i < 2500; i = i + 300) {
            String string = getString(i);
            System.out.println(i + "\t" + string.length() + "\t" + string);
        }
    }

    /**/
    public static String getString() {
        return SX[LX.length - 1];
    }

    public static String getString(int n) {
        if (n > 0) {
            int m = SX[0].length();
            if (n > m) {
//              System.out.println(n + ">\t" + m);
                for (int i = LX.length - 1; i >= 0; i--) {
                    if (n >= LX[i]) {
                        return SX[i];
                    }
                }
            }
            return StringUtils.substringBeforeLast(SX[0].substring(0, n), " ");
        }
        return null;
    }

}
