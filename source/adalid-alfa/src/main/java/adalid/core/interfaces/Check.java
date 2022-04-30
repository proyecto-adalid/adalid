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
package adalid.core.interfaces;

import adalid.core.enums.*;

/**
 * @author Jorge Campins
 */
public interface Check extends BooleanExpression {

    /**
     * @return the check event
     */
    CheckEvent getCheckEvent();

    /**
     * El método setCheckEvent se utiliza para establecer en que evento o eventos se debe evaluar la expresión.
     *
     * @param checkEvent evento o eventos donde se debe evaluar la expresión. Su valor es uno de los elementos de la enumeración CheckEvent.
     * Seleccione INSERT o UPDATE si la expresión se debe evaluar solamente al agregar o al actualizar, respectivamente. Seleccione INSERT_AND_UPDATE
     * para evaluar la expresión en ambos eventos; esta es la opción predeterminada.
     */
    void setCheckEvent(CheckEvent checkEvent);

    /**
     * @return the checkpoint
     */
    Checkpoint getCheckpoint();

    /**
     * El método setCheckpoint se utiliza para establecer en que componente o componentes se debe evaluar la expresión.
     *
     * @param checkpoint componente o componentes donde se debe evaluar la expresión. Su valor es uno de los elementos de la enumeración Checkpoint.
     * Seleccione DATABASE_TRIGGER o USER_INTERFACE si la expresión se debe evaluar solamente en un disparador (trigger) de la base de datos o en la
     * interfaz de usuario, respectivamente. Seleccione WHEREVER_POSSIBLE para evaluar la expresión en ambos componentes, siempre que sea posible;
     * esta es la opción predeterminada.
     */
    void setCheckpoint(Checkpoint checkpoint);

}
