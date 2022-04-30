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
import adalid.core.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Jorge Campins
 */
public interface PersistentEntity extends DatabaseEntity {

    /**
     * @return the base table class
     */
    Class<?> getBaseTableClass();

    /**
     * @return the root entity instance of the base table class
     */
    PersistentEntity getBaseTableRoot();

    /**
     * @return the inheritanceMappingStrategy
     */
    InheritanceMappingStrategy getInheritanceMappingStrategy();

    /**
     * @return the discriminator property
     */
    Property getDiscriminatorProperty();

    /**
     * @return the discriminatorValue
     */
    String getDiscriminatorValue();

    /**
     * @return the triggerBeforeValueEnabled
     */
    boolean isTriggerBeforeValueEnabled();

    /**
     * @return the triggerAfterValueEnabled
     */
    boolean isTriggerAfterValueEnabled();

    /**
     * @return the triggerBeforeCheckEnabled
     */
    boolean isTriggerBeforeCheckEnabled();

    /**
     * @return the triggerAfterCheckEnabled
     */
    boolean isTriggerAfterCheckEnabled();

    /**
     * @return the table indicator
     */
    boolean isTable();

    /**
     * @return the table indicator
     */
    boolean isNotTable();

    /**
     * @return the joined table indicator
     */
    boolean isJoinedTable();

    /**
     * @return the joined table indicator
     */
    boolean isNotJoinedTable();
//
    // <editor-fold defaultstate="collapsed">
//
//  /**
//   * @return the table name
//   */
//  String getTableName();
//
//  /**
//   * @return the base table name
//   */
//  String getBaseTableName();
    // </editor-fold>

    /**
     * @return the join base entity
     */
    PersistentEntity getJoinBaseEntity();

    /**
     * @return the properties that are columns
     */
    List<Property> getColumnsList();

    /**
     * @return the properties that are columns of a unique key
     */
    List<Property> getUniqueKeyPropertiesList();

    /**
     * @return the properties that are columns
     */
    List<Property> getDataProviderColumnsList();
//
    // <editor-fold defaultstate="collapsed">
//  /**
//   * @return the expressions that are checks
//   */
//  List<Expression> getChecksList();
//
//  /**
//   * @return the insertable rows
//   */
//  List<Instance> getInsertableRowsList();
    // </editor-fold>

    /**
     * @return the queryTable
     */
    QueryTable getQueryTable();

    /**
     * @return the searchQueryTable
     */
    QueryTable getSearchQueryTable();

    /**
     * @return the searchQueryPropertiesList
     */
    List<Property> getSearchQueryPropertiesList();

    /**
     * @return the searchQueryPropertiesMap
     */
    Map<String, Property> getSearchQueryPropertiesMap();
//
    // <editor-fold defaultstate="collapsed">
//  /**
//   * @return the referenced columns list
//   */
//  List<Property> getChecksColumnsList();
//
//  /**
//   * @return the referenced columns map
//   */
//  Map<String, Property> getChecksColumnsMap();
//
//  /**
//   * @return the referenced joins list
//   */
//  List<QueryJoin> getChecksJoinsList();
//
//  /**
//   * @return the referenced joins map
//   */
//  Map<String, QueryJoin> getChecksJoinsMap();
//
//  /**
//   * @return the referenced columns list
//   */
//  List<Property> getInitialValueColumnsList();
//
//  /**
//   * @return the referenced columns map
//   */
//  Map<String, Property> getInitialValueColumnsMap();
//
//  /**
//   * @return the referenced joins list
//   */
//  List<QueryJoin> getInitialValueJoinsList();
//
//  /**
//   * @return the referenced joins map
//   */
//  Map<String, QueryJoin> getInitialValueJoinsMap();
//
//  /**
//   * @return the referenced columns list
//   */
//  List<Property> getDefaultValueColumnsList();
//
//  /**
//   * @return the referenced columns map
//   */
//  Map<String, Property> getDefaultValueColumnsMap();
//
//  /**
//   * @return the referenced joins list
//   */
//  List<QueryJoin> getDefaultValueJoinsList();
//
//  /**
//   * @return the referenced joins map
//   */
//  Map<String, QueryJoin> getDefaultValueJoinsMap();
    // </editor-fold>

    List<Property> getJoinedTablePropertiesList();

    Map<String, Property> getJoinedTablePropertiesMap();

    List<Property> getJoinedTableMatchingPropertiesList(Map<String, Property> someProperties);

    Map<String, Property> getJoinedTableMatchingPropertiesMap(Map<String, Property> someProperties);

    List<Property> getSingleJoinedTablePropertiesList(Map<String, Property> someProperties);

    Map<String, Property> getSingleJoinedTablePropertiesMap(Map<String, Property> someProperties);

    Set<String> getCrossReferencedExpressionsSet();

}
