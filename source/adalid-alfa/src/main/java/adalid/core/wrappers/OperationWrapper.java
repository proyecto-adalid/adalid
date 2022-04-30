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
package adalid.core.wrappers;

import adalid.commons.util.*;
import adalid.core.*;
import adalid.core.interfaces.*;
import adalid.core.programmers.*;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class OperationWrapper extends ArtifactWrapper {

    private final Operation _operation;

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

    // <editor-fold defaultstate="collapsed" desc="metodos para obtener el mensaje de confirmación">
    public String getValidDefaultConfirmationMessage() {
        return StringUtils.trimToEmpty(getDefaultConfirmationMessage());
    }

    protected String getDefaultConfirmationMessage() {
        return _operation.getDefaultConfirmationMessage();
    }

    public String getBundleDefaultConfirmationMessage() {
        return getBundleValueString(getDefaultConfirmationMessage());
    }

    public String getBundleValidDefaultConfirmationMessage() {
        return getBundleValueString(getValidDefaultConfirmationMessage());
    }

    public String getJavaDefaultConfirmationMessage() {
        return getJavaString(getDefaultConfirmationMessage());
    }

    public String getJavaValidDefaultConfirmationMessage() {
        return getJavaString(getValidDefaultConfirmationMessage());
    }

    public String getHtmlDefaultConfirmationMessage() {
        return getHtmlString(getDefaultConfirmationMessage());
    }

    public String getHtmlValidDefaultConfirmationMessage() {
        return getHtmlString(getValidDefaultConfirmationMessage());
    }

    public String getXmlDefaultConfirmationMessage() {
        return getXmlString(getDefaultConfirmationMessage());
    }

    public String getXmlValidDefaultConfirmationMessage() {
        return getXmlString(getValidDefaultConfirmationMessage());
    }

    public String getSqlDefaultConfirmationMessage() {
        return getSqlString(getDefaultConfirmationMessage());
    }

    public String getSqlValidDefaultConfirmationMessage() {
        return getSqlString(getValidDefaultConfirmationMessage());
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="metodos para obtener el mensaje de éxito">
    public String getValidDefaultSuccessMessage() {
        return StringUtils.trimToEmpty(getDefaultSuccessMessage());
    }

    protected String getDefaultSuccessMessage() {
        return _operation.getDefaultSuccessMessage();
    }

    public String getBundleDefaultSuccessMessage() {
        return getBundleValueString(getDefaultSuccessMessage());
    }

    public String getBundleValidDefaultSuccessMessage() {
        return getBundleValueString(getValidDefaultSuccessMessage());
    }

    public String getJavaDefaultSuccessMessage() {
        return getJavaString(getDefaultSuccessMessage());
    }

    public String getJavaValidDefaultSuccessMessage() {
        return getJavaString(getValidDefaultSuccessMessage());
    }

    public String getHtmlDefaultSuccessMessage() {
        return getHtmlString(getDefaultSuccessMessage());
    }

    public String getHtmlValidDefaultSuccessMessage() {
        return getHtmlString(getValidDefaultSuccessMessage());
    }

    public String getXmlDefaultSuccessMessage() {
        return getXmlString(getDefaultSuccessMessage());
    }

    public String getXmlValidDefaultSuccessMessage() {
        return getXmlString(getValidDefaultSuccessMessage());
    }

    public String getSqlDefaultSuccessMessage() {
        return getSqlString(getDefaultSuccessMessage());
    }

    public String getSqlValidDefaultSuccessMessage() {
        return getSqlString(getValidDefaultSuccessMessage());
    }
    // </editor-fold>

    public String getBundleExportReportOperationLabel() {
        return getBundleValueString(getExportReportOperationLabel());
    }

    public String getExportReportOperationLabel() {
        String label = _operation.getDefaultCollectionLabel();
        if (StringUtils.isNotBlank(label)) {
            return label;
        }
        View view
            = _operation instanceof ExportOperation ? ((ExportOperation) _operation).getView()
                : _operation instanceof ReportOperation ? ((ReportOperation) _operation).getView()
                    : null;
        /**/
        if (view != null) {
            label = view.getDefaultLabel();
            if (StringUtils.isNotBlank(label)) {
                return label;
            }
        }
        return null;
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
     * @param maxIdentifierLength max identifier length
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
     * @param maxIdentifierLength max identifier length
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
     * @param maxIdentifierLength max identifier length
     * @return the SQL schema quailified short operation function name
     */
    public String getSqlSchemaQualifiedShortOperationFunctionName(int maxIdentifierLength) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaQualifiedShortOperationFunctionName(_operation, maxIdentifierLength);
    }

}
