/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.wrappers;

import adalid.commons.util.StrUtils;
import adalid.core.Operation;
import adalid.core.interfaces.SqlProgrammer;
import adalid.core.programmers.ChiefProgrammer;

/**
 * @author Jorge Campins
 */
public class OperationWrapper extends ArtifactWrapper {

    private Operation _operation;

    private OperationWrapper() {
        this(null);
    }

    public OperationWrapper(Operation operation) {
        super(operation);
        _operation = operation;
    }

    /**
     * @return the wrapped operation
     */
    @Override
    public Operation getWrapped() {
        return _operation;
    }

    public String getUnderscoredProperName() {
        String string = _operation.getProperName();
        return StrUtils.getLowerCaseIdentifier(string, '_');
    }

    /**
     * @return the SQL operation function name
     */
    public String getSqlOperationFunctionName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlOperationFunctionName(_operation);
    }

    /**
     * @param maxIdentifierLength
     * @return the SQL operation function name
     */
    public String getSqlOperationFunctionName(int maxIdentifierLength) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlOperationFunctionName(_operation, maxIdentifierLength);
    }

    /**
     * @return the SQL schema quailified operation function name
     */
    public String getSqlSchemaQualifiedOperationFunctionName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaQualifiedOperationFunctionName(_operation);
    }

    /**
     * @param maxIdentifierLength
     * @return the SQL schema quailified operation function name
     */
    public String getSqlSchemaQualifiedOperationFunctionName(int maxIdentifierLength) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaQualifiedOperationFunctionName(_operation, maxIdentifierLength);
    }

    /**
     * @return the SQL schema quailified short operation function name
     */
    public String getSqlSchemaQualifiedShortOperationFunctionName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaQualifiedShortOperationFunctionName(_operation);
    }

    /**
     * @param maxIdentifierLength
     * @return the SQL schema quailified short operation function name
     */
    public String getSqlSchemaQualifiedShortOperationFunctionName(int maxIdentifierLength) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaQualifiedShortOperationFunctionName(_operation, maxIdentifierLength);
    }

}
