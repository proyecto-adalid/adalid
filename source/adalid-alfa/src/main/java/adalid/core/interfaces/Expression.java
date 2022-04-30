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
public interface Expression extends TypedArtifact {

    /**
     * @return the operator
     */
    Operator getOperator();

    /**
     * @return the operands
     */
    Object[] getOperands();

    /**
     * @return the pseudo-expression
     */
    String getExpressionString();

    /**
     * @return the parent expression
     */
    Expression getParentExpression();

    /**
     * @return the verified usages
     */
    List<ExpressionUsage> getVerifiedUsages();

    /**
     * @return the strings set
     */
    Set<String> getStringsSet();

    /**
     * @return the referenced columns list
     */
    List<Property> getReferencedColumnsList();

    /**
     * @return the referenced columns map
     */
    Map<String, Property> getReferencedColumnsMap();

    /**
     * @return the referenced joins list
     */
    List<QueryJoin> getReferencedJoinsList();

    /**
     * @param queryTable query table
     * @return the referenced joins list
     */
    List<QueryJoin> getReferencedJoinsList(QueryTable queryTable);

    /**
     * @return the referenced joins map
     */
    Map<String, QueryJoin> getReferencedJoinsMap();

    /**
     * @param queryTable query table
     * @return the referenced joins map
     */
    Map<String, QueryJoin> getReferencedJoinsMap(QueryTable queryTable);

    /**
     * @return the referenced expressions map
     */
    Set<String> getCrossReferencedExpressionsSet();

    /**
     * @param declaringEntity declaring entity
     * @return the referenced expressions map
     */
    Set<String> getCrossReferencedExpressionsSet(Entity declaringEntity);

    String getCrossReferencedExpressionsKey();

    boolean isCrossReferencedExpression();

    boolean isSingleEntityExpression();

    boolean isSingleEntityExpression(Entity declaringEntity);

}
