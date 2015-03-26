/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.core.interfaces.Entity;
import adalid.core.interfaces.Expression;
import adalid.core.interfaces.Operator;
import adalid.core.interfaces.PersistentEntity;
import adalid.core.interfaces.Property;
import adalid.core.sql.QueryJoin;
import adalid.core.sql.QueryTable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Jorge Campins
 */
public abstract class Primitive extends AbstractDataArtifact implements Expression {

    @Override
    public Operator getOperator() {
        return null;
    }

    @Override
    public Object[] getOperands() {
        return new Object[]{this};
    }

    @Override
    public Expression getParentExpression() {
        return null;
    }

    @Override
    public List<Property> getReferencedColumnsList() {
        return new ArrayList<>(getReferencedColumnsMap().values());
    }

    @Override
    public Map<String, Property> getReferencedColumnsMap() {
        Map<String, Property> map = new LinkedHashMap<>();
        map.put(getPathString(), this);
        return map;
    }

    @Override
    public List<QueryJoin> getReferencedJoinsList() {
        return new ArrayList<>(getReferencedJoinsMap().values());
    }

    @Override
    public List<QueryJoin> getReferencedJoinsList(QueryTable queryTable) {
        return new ArrayList<>(getReferencedJoinsMap(queryTable).values());
    }

    @Override
    public Map<String, QueryJoin> getReferencedJoinsMap() {
        Entity declaringEntity = getDeclaringEntity();
        if (declaringEntity instanceof PersistentEntity) {
            PersistentEntity pent = (PersistentEntity) declaringEntity;
            QueryTable queryTable = pent.getQueryTable();
            return queryTable.getReferencedJoinsMap(this);
        }
        return new LinkedHashMap<>();
    }

    @Override
    public Map<String, QueryJoin> getReferencedJoinsMap(QueryTable queryTable) {
        return queryTable.getReferencedJoinsMap(this);
    }

    Set<String> crossReferencedExpressionsSet;

    @Override
    public Set<String> getCrossReferencedExpressionsSet() {
        return getCrossReferencedExpressionsSet(getDeclaringEntity());
    }

    @Override
    public Set<String> getCrossReferencedExpressionsSet(Entity declaringEntity) {
        if (crossReferencedExpressionsSet == null) {
            crossReferencedExpressionsSet = new LinkedHashSet<>();
        }
        return crossReferencedExpressionsSet;
    }

    @Override
    public String getCrossReferencedExpressionsKey() {
        return null;
    }

    @Override
    public boolean isCrossReferencedExpression() {
        return false;
    }

    @Override
    public boolean isSingleEntityExpression() {
        Entity declaringEntity = getDeclaringEntity();
        return declaringEntity != null;
    }

    @Override
    public boolean isSingleEntityExpression(Entity declaringEntity) {
        return declaringEntity != null;
    }

    @Override
    public boolean isFinalised() {
        return true;
    }

    @Override
    public void finalise() {
        XS1.checkAccess();
    }
//
    // <editor-fold defaultstate="collapsed">
//  @Override
//  public String getSqlExpression() {
//      SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
//      return sqlProgrammer == null ? null : sqlProgrammer.getSqlExpression(this);
//  }
    // </editor-fold>

}
