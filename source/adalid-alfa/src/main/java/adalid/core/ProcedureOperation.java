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
package adalid.core;

import adalid.core.annotations.*;
import adalid.core.enums.*;
import java.lang.annotation.Annotation;
import java.util.List;

import static adalid.core.enums.ProcedureDataType.*;
import static adalid.core.enums.ProcedureType.*;

/**
 * @author Jorge Campins
 */
public abstract class ProcedureOperation extends ProcessOperation {

    private boolean _annotatedWithProcedureOperationClass;

//  private String _procedureName;
//
    private final ProcedureType _procedureType = VOID; // VOID and final since 24/10/2021

    private ProcedureDataType _procedureDataType = BIGINT;

    /**
     * @return true if annotated with ProcedureOperationClass; false otherwise
     */
    public boolean isAnnotatedWithProcedureOperationClass() {
        return _annotatedWithProcedureOperationClass;
    }

    /**
     * @return the procedure name
     */
    public String getProcedureName() {
//      return _procedureName;
        return getProcessName();
    }

    /**
     * @return the procedure type
     */
    public ProcedureType getProcedureType() {
        return _procedureType;
    }

    /**
     * @return the procedure data type
     */
    public ProcedureDataType getProcedureDataType() {
        return VOID.equals(_procedureType) ? NULL : COMPOUND.equals(_procedureType) ? OTHER : _procedureDataType;
    }

    /**
     * @return the asynchronous execution indicator
     */
    @Override
    public boolean isAsynchronous() {
        return VOID.equals(_procedureType) || super.isAsynchronous();
    }

    /**
     * @return the procedure shell enabled indicator
     */
    @Override
    public Kleenean getShellEnabled() {
        return VOID.equals(_procedureType) ? Kleenean.TRUE : super.getShellEnabled();
    }

    // <editor-fold defaultstate="collapsed" desc="annotate">
    @Override
    void initializeAnnotations() {
        super.initializeAnnotations();
//      _procedureName = StringUtils.defaultIfBlank(_procedureName, getName());
    }

    @Override
    void annotate(Class<?> type) {
        super.annotate(type);
        if (type != null) {
            annotateProcedureOperationClass(type);
        }
    }

    @Override
    protected List<Class<? extends Annotation>> getValidTypeAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidTypeAnnotations();
        valid.add(ProcedureOperationClass.class);
        return valid;
    }

    private void annotateProcedureOperationClass(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, ProcedureOperationClass.class);
        if (annotatedClass != null) {
            ProcedureOperationClass annotation = annotatedClass.getAnnotation(ProcedureOperationClass.class);
            if (annotation != null) {
//              String name = annotation.name();
//              if (StringUtils.isNotBlank(name)) {
//                  _procedureName = name;
//              }
//              _procedureType = annotation.type(); // En esta versión todos los procedimientos son VOID.
                _procedureDataType = annotation.dataType(); // En esta versión todos los procedimientos son VOID y, por lo tanto, retornan NULL.
                _annotatedWithProcedureOperationClass = true;
            }
        }
    }
    // </editor-fold>

}
