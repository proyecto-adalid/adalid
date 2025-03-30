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

import adalid.core.*;
import adalid.core.enums.*;
import adalid.core.expressions.*;
import adalid.core.sql.*;
import java.util.List;

/**
 * @author Jorge Campins
 */
public interface EntityReference extends Entity, Parameter, Property {

    /**
     * @return the oneToOne indicator
     */
    boolean isOneToOne();

    /**
     * @return the manyToOne indicator
     */
    boolean isManyToOne();

    /**
     * @return true if the entity defines the main relationship
     */
    boolean isMainRelationship();

    /**
     * @return the master/detail view menu option enabled indicator
     */
    boolean isMasterDetailViewMenuOptionEnabled();

    /**
     * @param display the display that is opened by the menu option
     * @return the master/detail view menu option enabled indicator
     */
    boolean isMasterDetailViewMenuOptionEnabled(Display display);

    /**
     * @return the fetch type
     */
    FetchType getFetchType();

    /**
     * @return the cascade type
     */
    CascadeType[] getCascadeType();

    /**
     * @return the cascade type
     */
    String getCascadeTypeString();

    /**
     * @return the navigability
     */
    Navigability getNavigability();

    /**
     * @return the mapped entity collection
     */
    EntityCollection getMappedCollection();

    /**
     * @param collection the mapped entity collection
     */
    void setMappedCollection(EntityCollection collection);

    /**
     * @return the one-to-one detail view indicator
     */
    boolean isOneToOneDetailView();

    /**
     * @return the master/detail view
     */
    MasterDetailView getMasterDetailView();

    /**
     * @return the master/detail view sequence
     */
    int getMasterDetailViewSequence();

    /**
     * @return the master sequence master field indicator
     */
    boolean isMasterSequenceMasterField();

    /**
     * @param b the master sequence master field indicator to set
     */
    void setMasterSequenceMasterField(boolean b);

    /**
     * @return the quick-adding filter
     */
    QuickAddingFilter getQuickAddingFilter();

    /**
     * @return the master-dependent property list
     */
    List<Property> getMasterDependentProperties();

    /**
     * @param properties lista de propiedades dependientes del maestro
     */
    void setMasterDependentProperties(Property... properties);

    /**
     * @return the missing-instance-quick-adding-master override
     */
    EntityReference getMissingInstanceQuickAddingMasterOverride();

    /**
     * @param reference the missing-instance-quick-adding-master override to set
     */
    void setMissingInstanceQuickAddingMasterOverride(EntityReference reference);

    /**
     * @return the key-properties-query-mapping indicator
     */
    boolean isKeyPropertiesQueryMappingEnabled();

    /**
     * El método <b>isIn</b> contruye una expresión lógica que genera la comparación de esta referencia (operando X) con el conjunto de referencias
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si X es igual a algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isIn(EntityReference... y);

    /**
     * El método <b>isNotIn</b> contruye una expresión lógica que genera la comparación de esta referencia (operando X) con el conjunto de referencias
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si X no es igual a algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNotIn(EntityReference... y);

    /**
     * El método <b>isNullOrIn</b> contruye una expresión lógica que genera la comparación de esta referencia (operando X) con el conjunto de
     * referencias que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es igual a
     * algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNullOrIn(EntityReference... y);

    /**
     * El método <b>isNullOrNotIn</b> contruye una expresión lógica que genera la comparación de esta referencia (operando X) con el conjunto de
     * referencias que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X no es igual a
     * algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNullOrNotIn(EntityReference... y);

    /**
     * El método <b>isIn</b> contruye una expresión lógica que genera la comparación de esta referencia (operando X) con el conjunto de instancias que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X es igual a algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isIn(Instance... y);

    /**
     * El método <b>isNotIn</b> contruye una expresión lógica que genera la comparación de esta referencia (operando X) con el conjunto de instancias
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si X no es igual a algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNotIn(Instance... y);

    /**
     * El método <b>isNullOrIn</b> contruye una expresión lógica que genera la comparación de esta referencia (operando X) con el conjunto de
     * instancias que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es igual a
     * algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNullOrIn(Instance... y);

    /**
     * El método <b>isNullOrNotIn</b> contruye una expresión lógica que genera la comparación de esta referencia (operando X) con el conjunto de
     * instancias que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X no es igual a
     * algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNullOrNotIn(Instance... y);

    /**
     * El método <b>isIn</b> contruye una expresión lógica que genera la comparación de esta referencia (operando X) con el conjunto de instancias que
     * se obtiene ejecutando la instrucción SELECT de SQL que recibe como argumento (operando Y). La comparación resulta en verdadero si X es igual a
     * algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isIn(String y);

    /**
     * El método <b>isNotIn</b> contruye una expresión lógica que genera la comparación de esta referencia (operando X) con el conjunto de instancias
     * que se obtiene ejecutando la instrucción SELECT de SQL que recibe como argumento (operando Y). La comparación resulta en verdadero si X no es
     * igual a algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNotIn(String y);

    /**
     * El método <b>isNullOrIn</b> contruye una expresión lógica que genera la comparación de esta referencia (operando X) con el conjunto de
     * instancias que se obtiene ejecutando la instrucción SELECT de SQL que recibe como argumento (operando Y). La comparación resulta en verdadero
     * si el valor del operando X es nulo o si X es igual a algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNullOrIn(String y);

    /**
     * El método <b>isNullOrNotIn</b> contruye una expresión lógica que genera la comparación de esta referencia (operando X) con el conjunto de
     * instancias que se obtiene ejecutando la instrucción SELECT de SQL que recibe como argumento (operando Y). La comparación resulta en verdadero
     * si el valor del operando X es nulo o si X no es igual a algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNullOrNotIn(String y);

    /**
     * El método <b>isIn</b> contruye un segmento de selección para la comparación de esta referencia (operando X) con el resultado del query nativo
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si X es igual a algún elemento de Y.
     *
     * @param y operando Y
     * @return segmento de selección para la comparación de los operandos.
     */
    NativeQuerySegment isIn(NativeQuery y);

    /**
     * El método <b>isNotIn</b> contruye un segmento de selección para la comparación de esta referencia (operando X) con el resultado del query
     * nativo que recibe como argumento (operando Y). La comparación resulta en verdadero si X no es igual a algún elemento de Y.
     *
     * @param y operando Y
     * @return segmento de selección para la comparación de los operandos.
     */
    NativeQuerySegment isNotIn(NativeQuery y);

    /**
     * El método <b>isNullOrIn</b> contruye un segmento de selección para la comparación de esta referencia (operando X) con el resultado del query
     * nativo que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es igual a algún
     * elemento de Y.
     *
     * @param y operando Y
     * @return segmento de selección para la comparación de los operandos.
     */
    NativeQuerySegment isNullOrIn(NativeQuery y);

    /**
     * El método <b>isNullOrNotIn</b> contruye un segmento de selección para la comparación de esta referencia (operando X) con el resultado del query
     * nativo que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X no es igual a
     * algún elemento de Y.
     *
     * @param y operando Y
     * @return segmento de selección para la comparación de los operandos.
     */
    NativeQuerySegment isNullOrNotIn(NativeQuery y);

}
