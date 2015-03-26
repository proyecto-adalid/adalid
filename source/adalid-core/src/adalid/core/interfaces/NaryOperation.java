/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.interfaces;

/*
 * An operation is an action or procedure which produces a new value from one or more input values
 */
/**
 * @author Jorge Campins
 */
public interface NaryOperation {

    /**
     * @return the operator
     */
    Operator getOperator();

    /**
     * @return the operands
     */
    Object[] getOperands();

}
