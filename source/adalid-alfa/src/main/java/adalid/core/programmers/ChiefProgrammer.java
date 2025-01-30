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
package adalid.core.programmers;

import adalid.commons.*;
import adalid.commons.interfaces.*;
import adalid.core.interfaces.*;

/**
 * @author Jorge Campins
 */
public class ChiefProgrammer extends AbstractProgrammer {

    public static BundleProgrammer getBundleProgrammer() {
        Class<? extends Programmer> clazz = BundleProgrammer.class;
        Programmer programmer = TLB.getProgrammer(clazz);
        if (programmer instanceof BundleProgrammer bundleProgrammer) {
            return bundleProgrammer;
        }
        throw new RuntimeException(message(clazz));
    }

    public static JavaProgrammer getJavaProgrammer() {
        Class<? extends Programmer> clazz = JavaProgrammer.class;
        Programmer programmer = TLB.getProgrammer(clazz);
        if (programmer instanceof JavaProgrammer javaProgrammer) {
            return javaProgrammer;
        }
        throw new RuntimeException(message(clazz));
    }

    public static SqlProgrammer getSqlProgrammer() {
        Class<? extends Programmer> clazz = SqlProgrammer.class;
        Programmer programmer = TLB.getProgrammer(clazz);
        if (programmer instanceof SqlProgrammer sqlProgrammer) {
            return sqlProgrammer;
        }
        throw new RuntimeException(message(clazz));
    }

    private static String message(Class<?> clazz) {
        String hint = EOL$ + "hint: " + "check properties at platform''s properties file";
        String pattern1 = "programmers map contains no mapping for {0}" + hint;
        String message = format(pattern1, clazz);
        return message;
    }

}
