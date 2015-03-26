/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.wrappers;

import adalid.core.interfaces.DataArtifact;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.JavaProgrammer;
import adalid.core.interfaces.SqlProgrammer;
import adalid.core.programmers.ChiefProgrammer;
import adalid.core.sql.QueryTable;

/**
 * @author Jorge Campins
 */
public class DataArtifactWrapper extends ArtifactWrapper {

    private DataArtifact _dataArtifact;

    private DataArtifactWrapper() {
        this(null);
    }

    public DataArtifactWrapper(DataArtifact dataArtifact) {
        super(dataArtifact);
        _dataArtifact = dataArtifact;
    }

    @Override
    public DataArtifact getWrapped() {
        return _dataArtifact;
    }

    /**
     * @return the Java primitive type
     */
    public String getJavaPrimitiveType() {
        Entity entity = _dataArtifact instanceof Entity ? (Entity) _dataArtifact : null;
        DataArtifact dataArtifact = entity != null ? entity.getPrimaryKeyProperty() : _dataArtifact;
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaType(dataArtifact);
    }

    /**
     * @return the Java primitive type
     */
    public String getJavaPrimitiveTypeName() {
        Entity entity = _dataArtifact instanceof Entity ? (Entity) _dataArtifact : null;
        DataArtifact dataArtifact = entity != null ? entity.getPrimaryKeyProperty() : _dataArtifact;
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaTypeName(dataArtifact);
    }

    /**
     * @return the Java type simple name
     */
    public String getJavaTypeSimpleName() {
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaTypeSimpleName(_dataArtifact);
    }

    /**
     * @return the Java initial value
     */
    public String getJavaInitialValue() {
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaInitialValue(_dataArtifact);
    }

    /**
     * @return the Java default value
     */
    public String getJavaDefaultValue() {
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaDefaultValue(_dataArtifact);
    }

    /**
     * @return the Java current value
     */
    public String getJavaCurrentValue() {
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaCurrentValue(_dataArtifact);
    }

    public String getJavaPrimitiveValue(Object object) {
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaPrimitiveValue(object, getJavaPrimitiveType());
    }

    /**
     * @return the SQL type
     */
    public String getSqlType() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlType(_dataArtifact);
    }

    /**
     * @return the SQL parameter type
     */
    public String getSqlParameterType() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlParameterType(_dataArtifact);
    }

    /**
     * @return the SQL null clause
     */
    public String getSqlNull() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlNull(_dataArtifact);
    }

    /**
     * @return the SQL initial value
     */
    public String getSqlInitialValue() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlInitialValue(_dataArtifact);
    }

    /**
     * @return the SQL initial value
     */
    public String getSqlInitialValue(QueryTable queryTable) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlInitialValue(_dataArtifact, queryTable);
    }

    /**
     * @return the SQL default value
     */
    public String getSqlDefaultValue() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlDefaultValue(_dataArtifact);
    }

    /**
     * @return the SQL default value
     */
    public String getSqlDefaultValue(QueryTable queryTable) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlDefaultValue(_dataArtifact, queryTable);
    }

    /**
     * @return the SQL current value
     */
    public String getSqlCurrentValue() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlCurrentValue(_dataArtifact);
    }

    /**
     * @return the SQL current value
     */
    public String getSqlCurrentValue(QueryTable queryTable) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlCurrentValue(_dataArtifact, queryTable);
    }

}
