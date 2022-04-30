/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.util;

import adalid.commons.*;
import adalid.commons.util.*;
import adalid.core.annotations.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import javax.swing.JOptionPane;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class Runner extends Utility {

    private static final Logger logger = Logger.getLogger(Runner.class);

    private static final ResourceBundle RB = ResourceBundle.getBundle(Runner.class.getCanonicalName());

    public static void run(Class<? extends Runner> mainRunner) {
        String runner = mainRunner.getCanonicalName();
        logger.info("runner=" + runner);
        String prefix = StringUtils.substringBeforeLast(mainRunner.getPackageName(), ".");
        logger.info("prefix=" + prefix);
        String exclude = "." + (WINDOWS ? "linux" : "windows") + ".";
//      logger.info("exclude=" + exclude);
//      String include = "." + (WINDOWS ? "windows" : "linux") + ".";
//      logger.info("include=" + include);
        /**/
        Set<String> set = new LinkedHashSet<>();
        Class<? extends ProjectBuilder> lastExecutedProjectClass = getLastExecutedProjectClass();
        if (lastExecutedProjectClass != null) {
            logger.info("last-executed-project=" + lastExecutedProjectClass.getCanonicalName());
            set.add(lastExecutedProjectClass.getCanonicalName());
        }
        /**/
        Set<Class<?>> masters = JavaUtils.getTypes(prefix, MasterProject.class);
        set.addAll(JavaUtils.getCanonicalNames(masters));
        /**/
        Set<Class<?>> utilities = JavaUtils.getSubTypes(prefix, Utility.class);
        set.addAll(JavaUtils.getCanonicalNames(utilities));
        /**/
        Collection<String> names = set.isEmpty() ? null : ColUtils.filter(set, (Object o) -> fairName(o, prefix, exclude, runner));
        if (names != null && !names.isEmpty()) {
            execute(showInputDialog(names));
        }
    }

    private static boolean fairName(Object o, String prefix, String infix, String runner) {
        String name = o == null ? null : o.toString();
        return name != null && name.startsWith(prefix) && !name.contains(infix) && !name.equals(runner);
    }

    private static String showInputDialog(Collection<String> names) {
//      final Component parent = null;
        final Object message = "Clase";
        final String title = "Seleccione la clase que desea ejecutar";
        final int type = JOptionPane.QUESTION_MESSAGE;
//      final Icon icon = null;
        final Object[] values = names.toArray();
        final Object initialValue = values[0];
        Object value = JOptionPane.showInputDialog(null, message, title, type, null, values, initialValue);
        return value == null ? null : value.toString();
    }

    private static final String EXECUTE_MESSAGE = "\n\n¿Desea ejecutar la clase?";

    private static final String EXECUTE_TITLE = "Ejecutar ";

    private static void execute(String name) {
        if (name == null || name.isEmpty()) {
            return;
        }
        String string = getString(name);
        if (string == null) {
            logger.info("execute=" + name);
        }
        boolean execute = string == null || showConfirmDialog(split(string) + EXECUTE_MESSAGE, EXECUTE_TITLE + name);
        if (execute) {
            try {
                Class<? extends ProjectBuilder> clazz = (Class<? extends ProjectBuilder>) Class.forName(name);
                Method method = clazz.getMethod("main", String[].class);
                String[] args = null;
                method.invoke(null, (Object) args);
                updateProjectBuilderDictionary(clazz);
            } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                logger.fatal(ThrowableUtils.getString(ex));
            }
        }
    }

    private static String getString(String key) {
        try {
            String string = RB.getString(key);
            return StringUtils.trimToNull(string);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    private static String split(String string) {
        int middle = string.indexOf(' ', string.length() / 2);
        return middle < 0 ? string : string.substring(0, middle) + '\n' + string.substring(middle + 1);
    }

}
