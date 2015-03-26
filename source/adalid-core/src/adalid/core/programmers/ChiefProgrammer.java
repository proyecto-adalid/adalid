/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.programmers;

import adalid.commons.TLB;
import adalid.commons.interfaces.Programmer;
import adalid.core.interfaces.BundleProgrammer;
import adalid.core.interfaces.JavaProgrammer;
import adalid.core.interfaces.SqlProgrammer;
import java.text.MessageFormat;

/**
 * @author Jorge Campins
 */
public class ChiefProgrammer extends AbstractProgrammer {

    public static BundleProgrammer getBundleProgrammer() {
        Class<? extends Programmer> clazz = BundleProgrammer.class;
        Programmer programmer = TLB.getProgrammer(clazz);
        if (programmer instanceof BundleProgrammer) {
            return (BundleProgrammer) programmer;
        }
        throw new RuntimeException(message(clazz));
    }

    public static JavaProgrammer getJavaProgrammer() {
        Class<? extends Programmer> clazz = JavaProgrammer.class;
        Programmer programmer = TLB.getProgrammer(clazz);
        if (programmer instanceof JavaProgrammer) {
            return (JavaProgrammer) programmer;
        }
        throw new RuntimeException(message(clazz));
    }

    public static SqlProgrammer getSqlProgrammer() {
        Class<? extends Programmer> clazz = SqlProgrammer.class;
        Programmer programmer = TLB.getProgrammer(clazz);
        if (programmer instanceof SqlProgrammer) {
            return (SqlProgrammer) programmer;
        }
        throw new RuntimeException(message(clazz));
    }

    private static String message(Class<?> clazz) {
        String hint = EOL$ + "hint: " + "check properties at platform''s properties file";
        String pattern1 = "programmers map contains no mapping for {0}" + hint;
        String message = MessageFormat.format(pattern1, clazz);
        return message;
    }

}
