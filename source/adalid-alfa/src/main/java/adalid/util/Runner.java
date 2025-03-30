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
        Runner runner = new Runner();
        runner.sprint(mainRunner);
    }

    private void sprint(Class<? extends Runner> mainRunner) {
        long millis = System.currentTimeMillis();
        logger.warn(starting(mainRunner));
        String runner = mainRunner.getCanonicalName();
//      logger.info("runner=" + runner);
        String prefix = StringUtils.substringBeforeLast(mainRunner.getPackageName(), ".");
//      logger.info("prefix=" + prefix);
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
        set.addAll(JavaUtils.getCanonicalNames(ColUtils.filter(masters, (Object o) -> runnableProject(o))));
        /**/
        Set<Class<?>> utilities = JavaUtils.getSubTypes(prefix, Utility.class);
        set.addAll(JavaUtils.getCanonicalNames(ColUtils.filter(utilities, (Object o) -> runnableUtility(o))));
        /**/
        Collection<String> names = set.isEmpty() ? null : ColUtils.filter(set, (Object o) -> fairName(o, null, exclude, runner));
        if (names != null && !names.isEmpty()) {
            execute(showInputDialog(names));
        }
        logger.warn(finished(mainRunner, millis));
    }

    private boolean runnableProject(Object o) {
        Class<?> c = o instanceof Class ? (Class<?>) o : null;
        MasterProject a = c == null ? null : c.getAnnotation(MasterProject.class);
        return a != null && a.runnable();
    }

    private boolean runnableUtility(Object o) {
        Class<?> c = o instanceof Class ? (Class<?>) o : null;
        RunnableClass a = c == null ? null : c.getAnnotation(RunnableClass.class);
        return a != null && a.value();
    }

    private boolean fairName(Object o, String prefix, String exclude, String runner) {
        String name = o == null ? null : o.toString();
        return name != null && (prefix == null || name.startsWith(prefix)) && (exclude == null || !name.contains(exclude)) && !name.equals(runner);
    }

    private String showInputDialog(Collection<String> names) {
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

    private final String LAST_EXECUTED = "${lastExecutedProject}";

    private final String EXECUTE_MESSAGE = "\n\n¿Desea ejecutar la clase?";

    private final String EXECUTE_TITLE = "Ejecutar ";

    private void execute(String name) {
        if (name == null || name.isEmpty()) {
            return;
        }
        String string = getString(name);
        if (string == null) {
            logger.info("execute=" + name);
        } else if (string.contains(LAST_EXECUTED)) {
            String lastExecutedProjectAlias = getLastExecutedProjectAlias();
            String lastExecutedProjectClassName = getLastExecutedProjectClassName();
            string = string.replace(LAST_EXECUTED, lastExecutedProjectAlias + " (" + lastExecutedProjectClassName + ")");
        }
        boolean execute = string == null || showConfirmDialog(split(string) + EXECUTE_MESSAGE, EXECUTE_TITLE + name);
        if (execute) {
            try {
                Class<?> clazz = (Class<?>) Class.forName(name);
                Method method = clazz.getMethod("main", String[].class);
                String[] args = null;
                long millis = System.currentTimeMillis();
                logger.warn(starting(clazz));
                method.invoke(null, (Object) args);
                logger.warn(finished(clazz, millis));
                updateProjectBuilderDictionary(clazz);
            } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                logger.fatal(ThrowableUtils.getString(ex));
            }
        }
    }

    private String getString(String key) {
        String string = fetchString(key);
        if (string == null && key.contains(".")) {
            String simple = key.substring(key.lastIndexOf('.') + 1);
            string = fetchString(simple);
        }
        return string;
    }

    private String fetchString(String key) {
        try {
            String string = RB.getString(key);
            return StringUtils.trimToNull(string);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    private String split(String string) {
        return StrUtils.separateLines(string, 70, "\n", true);
    }

}
