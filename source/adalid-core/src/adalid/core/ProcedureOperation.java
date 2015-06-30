/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.core.annotations.ProcedureOperationClass;
import adalid.core.enums.ProcedureDataType;
import adalid.core.enums.ProcedureType;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author Jorge Campins
 */
public abstract class ProcedureOperation extends ProcessOperation {

    private boolean _annotatedWithProcedureOperationClass;

//  private String _procedureName;
//
    private ProcedureType _procedureType;

    private ProcedureDataType _procedureDataType;

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
        return _procedureDataType;
    }

    // <editor-fold defaultstate="collapsed" desc="annotate">
    @Override
    void initializeAnnotations() {
        super.initializeAnnotations();
        _annotatedWithProcedureOperationClass = false;
//      _procedureName = getName();
        _procedureType = ProcedureType.SIMPLE;
        _procedureDataType = ProcedureDataType.BIGINT;
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
                _procedureType = annotation.type();
                _procedureDataType = annotation.dataType();
                _annotatedWithProcedureOperationClass = true;
            }
        }
    }
    // </editor-fold>

}
