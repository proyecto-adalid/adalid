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

import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public abstract class Primitive extends AbstractDataArtifact implements Expression {

    /**
     *
     */
    private static final Logger logger = Logger.getLogger(Primitive.class);

    @Override
    public Operator getOperator() {
        return ScalarOp.SELF;
    }

    @Override
    public Object[] getOperands() {
        return new Object[]{this};
    }

    @Override
    public String getExpressionString() {
        return getFullName();
    }

    @Override
    public Expression getParentExpression() {
        return null;
    }

    /**
     *
     */
    private final List<ExpressionUsage> _verifiedUsages = new ArrayList<>();

    /**
     * @return the verified usages
     */
    @Override
    public List<ExpressionUsage> getVerifiedUsages() {
        return _verifiedUsages;
    }

    @Override
    public Set<String> getStringsSet() {
        return new LinkedHashSet<>();
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
    public boolean finish() {
        boolean ok = super.finish();
        if (ok) {
            checkAggregates();
        }
        return ok;
    }
//
    // <editor-fold defaultstate="collapsed">
//  @Override
//  public String getSqlExpression() {
//      SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
//      return sqlProgrammer == null ? null : sqlProgrammer.getSqlExpression(this);
//  }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="tag fields getters and setters">
    /**
     * @return the maximum value tag
     */
//  @Override
    public String getMaximumValueTag() {
        return getLocalizedMaximumValueTag(null);
    }

    /**
     * El método setMaximumValueTag se utiliza para establecer la descripción del valor máximo de propiedades y parámetros que se almacena en el
     * archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param tag una o más oraciones que describen muy brevemente el valor máximo de la propiedad o el parámetro
     */
//  @Override
    public void setMaximumValueTag(String tag) {
        setLocalizedMaximumValueTag(null, tag);
    }

    /**
     * @return the minimum value tag
     */
//  @Override
    public String getMinimumValueTag() {
        return getLocalizedMinimumValueTag(null);
    }

    /**
     * El método setMinimumValueTag se utiliza para establecer la descripción del valor mínimo de propiedades y parámetros que se almacena en el
     * archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param tag una o más oraciones que describen muy brevemente el valor mínimo de la propiedad o el parámetro
     */
//  @Override
    public void setMinimumValueTag(String tag) {
        setLocalizedMinimumValueTag(null, tag);
    }

    /**
     * A descriptive word or phrase applied to the maximum value as a label or identifier.
     */
    private final Map<Locale, String> _localizedMaximumValueTag = new LinkedHashMap<>();

    /**
     * @param locale the locale for the tag
     * @return the maximum value tag
     */
//  @Override
    public String getLocalizedMaximumValueTag(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedMaximumValueTag.get(l);
    }

    /**
     * El método setMaximumValueTag se utiliza para establecer la descripción del valor máximo de propiedades y parámetros que se almacena en el
     * archivo de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté
     * disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param tag una o más oraciones que describen muy brevemente el valor máximo de la propiedad o el parámetro
     */
//  @Override
    public void setLocalizedMaximumValueTag(Locale locale, String tag) {
        Locale l = localeWritingKey(locale);
        if (tag == null) {
            _localizedMaximumValueTag.remove(l);
        } else {
            _localizedMaximumValueTag.put(l, tag);
        }
    }

    /**
     * A descriptive word or phrase applied to the minimum value as a label or identifier.
     */
    private final Map<Locale, String> _localizedMinimumValueTag = new LinkedHashMap<>();

    /**
     * @param locale the locale for the tag
     * @return the minimum value tag
     */
//  @Override
    public String getLocalizedMinimumValueTag(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedMinimumValueTag.get(l);
    }

    /**
     * El método setMinimumValueTag se utiliza para establecer la descripción del valor mínimo de propiedades y parámetros que se almacena en el
     * archivo de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté
     * disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param tag una o más oraciones que describen muy brevemente el valor mínimo de la propiedad o el parámetro
     */
//  @Override
    public void setLocalizedMinimumValueTag(Locale locale, String tag) {
        Locale l = localeWritingKey(locale);
        if (tag == null) {
            _localizedMinimumValueTag.remove(l);
        } else {
            _localizedMinimumValueTag.put(l, tag);
        }
    }
    // </editor-fold>

}
