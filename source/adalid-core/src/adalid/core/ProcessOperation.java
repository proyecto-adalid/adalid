/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.commons.util.StrUtils;
import adalid.core.annotations.ProcessOperationClass;
import adalid.core.enums.Kleenean;
import adalid.core.interfaces.State;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public abstract class ProcessOperation extends Operation {

    private static final Logger logger = Logger.getLogger(Operation.class);

    private boolean _annotatedWithProcessOperationClass;

    private String _processName;

    private String _processingGroup;

    private Kleenean _overloading;

    /**
     * @return true if annotated with ProcessOperationClass; false otherwise
     */
    public boolean isAnnotatedWithProcessOperationClass() {
        return _annotatedWithProcessOperationClass;
    }

    /**
     * @return the process name
     */
    public String getProcessName() {
        return _processName;
    }

    /**
     * @return the processing group identifier
     */
    public String getProcessingGroup() {
        return _processingGroup;
    }

    /**
     * @return the overloading indicator
     */
    public Kleenean getOverloading() {
        return _overloading;
    }

    public void addTransition(State x, State y) {
        Transition transition = new Transition(x, y);
        addTransition(transition);
    }

    public void addTransition(Transition transition) {
        if (transition != null) {
            String key = transition.getName();
            if (key != null) {
                Map<String, Transition> map = getTransitionsMap();
                if (map.containsKey(key)) {
                } else {
                    map.put(key, transition);
                }
            }
        }
    }

    public Trigger trigger(State state) {
        return new Trigger(state, this);
    }

    public void addTriggerOn(State... states) {
        for (State state : states) {
            Trigger trigger = new Trigger(state, this);
            addTriggerOn(trigger);
        }
    }

    private void addTriggerOn(Trigger trigger) {
        String key = trigger.getName();
        if (key != null) {
            Map<String, Trigger> map = getTriggersMap();
            if (map.containsKey(key)) {
            } else {
                map.put(key, trigger);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="annotate">
    @Override
    void initializeAnnotations() {
        super.initializeAnnotations();
        _annotatedWithProcessOperationClass = false;
        _processName = getName();
        _processingGroup = null;
        _overloading = Kleenean.UNSPECIFIED;
    }

    @Override
    void annotate(Class<?> type) {
        super.annotate(type);
        if (type != null) {
            annotateProcessOperationClass(type);
        }
    }

    @Override
    protected List<Class<? extends Annotation>> getValidTypeAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidTypeAnnotations();
        valid.add(ProcessOperationClass.class);
        return valid;
    }

    private void annotateProcessOperationClass(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, ProcessOperationClass.class);
        if (annotatedClass != null) {
            ProcessOperationClass annotation = annotatedClass.getAnnotation(ProcessOperationClass.class);
            if (annotation != null) {
                String name = annotation.name();
                if (StringUtils.isNotBlank(name)) {
                    _processName = name;
                }
                String group = annotation.processingGroup();
                if (StringUtils.isNotBlank(group)) {
                    if (StrUtils.esIdentificadorSqlValido(group)) {
                        if (group.length() <= Constants.DEFAULT_CHARACTER_KEY_MAX_LENGTH) {
                            _processingGroup = group;
                            TLC.getProject().getProcessingGroups().add(group);
                        } else {
                            String message = "invalid processing group identifier \"" + group + "\" at " + getFullName();
                            message += "; identifier length cannot be greater than " + Constants.DEFAULT_CHARACTER_KEY_MAX_LENGTH;
                            logger.error(message);
                            TLC.getProject().getParser().increaseErrorCount();
                        }
                    } else {
                        String message = "invalid processing group identifier \"" + group + "\" at " + getFullName();
                        logger.error(message);
                        TLC.getProject().getParser().increaseErrorCount();
                    }
                }
                _overloading = annotation.overloading();
                _annotatedWithProcessOperationClass = true;
            }
        }
    }
    // </editor-fold>

}
