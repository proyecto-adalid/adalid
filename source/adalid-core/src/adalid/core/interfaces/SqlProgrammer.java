/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.interfaces;

import adalid.commons.interfaces.Programmer;
import adalid.core.Operation;
import adalid.core.enums.QueryJoinOp;
import adalid.core.enums.SortOption;
import adalid.core.enums.SqlQualifierType;
import adalid.core.enums.StandardRelationalOp;
import adalid.core.enums.ViewFieldAggregation;
import adalid.core.programmers.ParameterizedExpression;
import adalid.core.sql.QueryJoin;
import adalid.core.sql.QueryTable;
import java.util.List;
import java.util.Map;

/**
 * @author Jorge Campins
 */
public interface SqlProgrammer extends Programmer {

    int getMaxIdentifierLength();

    String getDBMS();

    String getSqlishName(Artifact artifact);

    String getSqlName(Artifact artifact);

    String getSqlName(Artifact artifact, int maxIdentifierLength);

    String getSqlName(String prefix, Artifact artifact);

    String getSqlName(String prefix, Artifact artifact, int maxIdentifierLength);

    String getSqlName(Artifact artifact, String suffix);

    String getSqlName(Artifact artifact, String suffix, int maxIdentifierLength);

    String getSqlName(String prefix, Artifact artifact, String suffix);

    String getSqlName(String prefix, Artifact artifact, String suffix, int maxIdentifierLength);

    String getSqlAlias(Property property, QueryTable queryTable);

    String getSqlQualifiedName(Property property, QueryTable queryTable);

    String getSqlQualifiedName(Artifact artifact);

    String getSqlVariableName(Artifact artifact);

    String getSqlVariableName(String name);

    String getSqlDiscriminatorValue(PersistentEntity entity);

    List<String> getSqlDiscriminatorValues(PersistentEntity entity);

    String getSqlSchemaName(PersistentEntity entity);

    String getSqlTableName(PersistentEntity entity);

    String getSqlFunctionName(ViewFieldAggregation aggregation);
//
//  String getSqlBaseTableName(PersistentEntity entity);

    String getSqlSchemaQualifier(PersistentEntity entity);

    String getSqlSchemaQualifiedName(PersistentEntity entity);

    String getSqlSchemaQualifiedShortName(PersistentEntity entity);

    String getSqlSchemaUnqualifiedShortName(PersistentEntity entity);

    String getSqlSchemaQualifiedTableName(PersistentEntity entity);

    String getSqlSchemaQualifiedShortTableName(PersistentEntity entity);

    String getSqlSchemaUnqualifiedShortTableName(PersistentEntity entity);

    String getSqlType(Artifact artifact);

    String getSqlParameterType(Artifact artifact);

    String getSqlNull(Artifact artifact);

    String getSqlInitialValue(Artifact artifact);

    String getSqlInitialValue(Artifact artifact, QueryTable queryTable);

    String getSqlDefaultValue(Artifact artifact);

    String getSqlDefaultValue(Artifact artifact, QueryTable queryTable);

    String getSqlCurrentValue(Artifact artifact);

    String getSqlCurrentValue(Artifact artifact, QueryTable queryTable);

    String getSqlExpression(Object object);

    String getSqlExpression(Object object, QueryTable queryTable);

    String getSqlExpression(Object object, QueryTable queryTable, SqlQualifierType qualifier);

    String getSqlExpression(Object object, Map<String, QueryTable> queryTablesMap);

    String getSqlExpression(Object object, Map<String, QueryTable> queryTablesMap, SqlQualifierType qualifier);

    ParameterizedExpression getSqlParameterizedExpression(Object object);

    ParameterizedExpression getSqlParameterizedExpression(Object object, QueryTable queryTable);

    ParameterizedExpression getSqlParameterizedExpression(Object object, QueryTable queryTable, SqlQualifierType qualifier);

    ParameterizedExpression getSqlParameterizedExpression(Object object, Map<String, QueryTable> queryTablesMap);

    ParameterizedExpression getSqlParameterizedExpression(Object object, Map<String, QueryTable> queryTablesMap, SqlQualifierType qualifier);

    String getSqlOperationFunctionName(Operation operation);

    String getSqlOperationFunctionName(Operation operation, int maxIdentifierLength);

    String getSqlSchemaQualifiedOperationFunctionName(Operation operation);

    String getSqlSchemaQualifiedOperationFunctionName(Operation operation, int maxIdentifierLength);

    String getSqlSchemaQualifiedShortOperationFunctionName(Operation operation);

    String getSqlSchemaQualifiedShortOperationFunctionName(Operation operation, int maxIdentifierLength);

    String getSqlExpressionFunctionName(Expression expression);

    String getSqlExpressionFunctionName(Expression expression, int maxIdentifierLength);

    String getSqlSchemaQualifiedExpressionFunctionName(Expression expression);

    String getSqlSchemaQualifiedExpressionFunctionName(Expression expression, int maxIdentifierLength);

    String getSqlSchemaQualifiedShortExpressionFunctionName(Expression expression);

    String getSqlSchemaQualifiedShortExpressionFunctionName(Expression expression, int maxIdentifierLength);

    String getSqlExpressionSelectFunctionName(Expression expression);

    String getSqlExpressionSelectFunctionName(Expression expression, int maxIdentifierLength);

    String getSqlSchemaQualifiedExpressionSelectFunctionName(Expression expression);

    String getSqlSchemaQualifiedExpressionSelectFunctionName(Expression expression, int maxIdentifierLength);

    String getSqlSchemaQualifiedShortExpressionSelectFunctionName(Expression expression);

    String getSqlSchemaQualifiedShortExpressionSelectFunctionName(Expression expression, int maxIdentifierLength);

    String getSqlOnDeleteAction(PersistentEntityReference entity);

    String getSqlOnUpdateAction(PersistentEntityReference entity);

    String getSqlSortOption(SortOption sortOption);

    String getSqlJoinOperator(QueryJoinOp operator);

    String getSqlJoinQualifier(QueryTable queryTable);

    Map<String, Property> getSelectColumnsMap(QueryTable queryTable);

    String getSqlSelectStatement(Expression expression);

    String getSqlSelectStatement(Expression expression, QueryTable queryTable);

    String getSqlSelectStatement(QueryTable queryTable, List<Property> referencedColumns, boolean into, boolean indent);

    String getSqlSelectStatement(QueryJoin queryJoin, List<Property> referencedColumns, boolean into, boolean where, boolean indent);

    String getSqlStandardRelationalExpression(String arg1, StandardRelationalOp operator);

    String getSqlStandardRelationalExpression(String arg1, StandardRelationalOp operator, String arg2);

}
