/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.interfaces;

import adalid.core.sql.QueryJoin;
import adalid.core.sql.QueryTable;
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
     * @return the parent expression
     */
    Expression getParentExpression();

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
     * @return the referenced joins list
     */
    List<QueryJoin> getReferencedJoinsList(QueryTable queryTable);

    /**
     * @return the referenced joins map
     */
    Map<String, QueryJoin> getReferencedJoinsMap();

    /**
     * @return the referenced joins map
     */
    Map<String, QueryJoin> getReferencedJoinsMap(QueryTable queryTable);

    /**
     * @return the referenced expressions map
     */
    Set<String> getCrossReferencedExpressionsSet();

    /**
     * @return the referenced expressions map
     */
    Set<String> getCrossReferencedExpressionsSet(Entity declaringEntity);

    String getCrossReferencedExpressionsKey();

    boolean isCrossReferencedExpression();

    boolean isSingleEntityExpression();

    boolean isSingleEntityExpression(Entity declaringEntity);

    boolean isFinalised();

    void finalise();

}
