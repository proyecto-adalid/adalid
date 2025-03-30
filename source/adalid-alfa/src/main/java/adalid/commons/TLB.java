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
package adalid.commons;

import adalid.commons.interfaces.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Jorge Campins
 */
public class TLB { // Thread Local Base Context

    private static final ThreadLocal<Map<String, Programmer>> _programmers = new ThreadLocal<>();

    private static final ThreadLocal<Map<String, Class<? extends Wrapper>>> _wrapperClasses = new ThreadLocal<>();

    static {
        init();
    }

    static void init() {
        setProgrammers(new LinkedHashMap<>());
        setWrapperClasses(new LinkedHashMap<>());
    }

    /*
    static void destroy() {
        removeProgrammers();
        removeWrapperClasses();
    }

    /**/
    public static Map<String, Programmer> getProgrammers() {
        return _programmers.get();
    }

    public static void setProgrammers(Map<String, Programmer> programmers) {
        if (programmers == null) {
            removeProgrammers();
        } else {
            _programmers.set(programmers);
        }
    }

    private static void removeProgrammers() {
        _programmers.remove();
    }

    public static void clearProgrammers() {
        getProgrammers().clear();
    }

    public static Map<String, Programmer> getProgrammersClone() {
        return new LinkedHashMap<>(getProgrammers());
    }

    public static Programmer getProgrammer(Class<? extends Programmer> clazz) {
        if (clazz == null) {
            return null;
        }
        Programmer programmer;
        Object[] keys = getProgrammers().keySet().toArray();
        Arrays.sort(keys);
        for (Object key : keys) {
            programmer = getProgrammers().get(key.toString());
            if (clazz.isAssignableFrom(programmer.getClass())) {
                return programmer;
            }
        }
        return null;
    }

    public static Programmer getProgrammer(String key) {
        return key == null ? null : getProgrammers().get(key);
    }

    public static void setProgrammer(String key, Programmer programmer) {
        if (key != null) {
            if (programmer == null) {
                removeProgrammer(key);
            } else {
                getProgrammers().put(key, programmer);
            }
        }
    }

    static void removeProgrammer(String key) {
        if (key != null) {
            getProgrammers().remove(key);
        }
    }

    public static Map<String, Class<? extends Wrapper>> getWrapperClasses() {
        return _wrapperClasses.get();
    }

    public static void setWrapperClasses(Map<String, Class<? extends Wrapper>> wrappers) {
        if (wrappers == null) {
            removeWrapperClasses();
        } else {
            _wrapperClasses.set(wrappers);
        }
    }

    private static void removeWrapperClasses() {
        _wrapperClasses.remove();
    }

    public static void clearWrapperClasses() {
        getWrapperClasses().clear();
    }

    public static Map<String, Class<? extends Wrapper>> getWrapperClassesClone() {
        return new LinkedHashMap<>(getWrapperClasses());
    }

    public static Class<? extends Wrapper> getWrapperClass(Class<? extends Wrappable> clazz) {
        return clazz == null ? null : getWrapperClass(clazz.getName());
    }

    private static Class<? extends Wrapper> getWrapperClass(String key) {
        return key == null ? null : getWrapperClasses().get(key);
    }

    public static void setWrapperClass(Class<? extends Wrappable> clazz, Class<? extends Wrapper> wrapper) {
        setWrapperClass(clazz.getName(), wrapper);
    }

    private static void setWrapperClass(String key, Class<? extends Wrapper> wrapper) {
        if (key != null) {
            if (wrapper == null) {
                removeWrapperClass(key);
            } else {
                getWrapperClasses().put(key, wrapper);
            }
        }
    }

    static void removeWrapperClass(String key) {
        if (key != null) {
            getWrapperClasses().remove(key);
        }
    }

}
