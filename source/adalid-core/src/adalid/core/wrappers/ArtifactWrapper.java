/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.wrappers;

import adalid.commons.bundles.Bundle;
import adalid.commons.i18n.Linguist;
import adalid.commons.interfaces.Wrapper;
import adalid.commons.util.StrUtils;
import adalid.core.Operation;
import adalid.core.enums.KeyProperty;
import adalid.core.interfaces.Artifact;
import adalid.core.interfaces.BundleProgrammer;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.EntityReference;
import adalid.core.interfaces.JavaProgrammer;
import adalid.core.interfaces.Parameter;
import adalid.core.interfaces.Property;
import adalid.core.interfaces.SqlProgrammer;
import adalid.core.programmers.ChiefProgrammer;
import adalid.core.programmers.ParameterizedExpression;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class ArtifactWrapper implements Wrapper {

    private Artifact _artifact;

    private ArtifactWrapper() {
        this(null);
    }

    public ArtifactWrapper(Artifact artifact) {
        _artifact = artifact;
    }

    @Override
    public Artifact getWrapped() {
        return _artifact;
    }

    public String getDottedName() {
        String string = _artifact.getName();
        return StrUtils.getLowerCaseIdentifier(string, '.');
    }

    public String getDottedAlias() {
        String string = StringUtils.defaultIfBlank(_artifact.getAlias(), _artifact.getName());
        return StrUtils.getLowerCaseIdentifier(string, '.');
    }

    public String getHyphenatedName() {
        String string = _artifact.getName();
        return StrUtils.getLowerCaseIdentifier(string, '-');
    }

    public String getHyphenatedAlias() {
        String string = StringUtils.defaultIfBlank(_artifact.getAlias(), _artifact.getName());
        return StrUtils.getLowerCaseIdentifier(string, '-');
    }

    public String getUnderscoredName() {
        String string = _artifact.getName();
        return StrUtils.getLowerCaseIdentifier(string, '_');
    }

    public String getUnderscoredAlias() {
        String string = StringUtils.defaultIfBlank(_artifact.getAlias(), _artifact.getName());
        return StrUtils.getLowerCaseIdentifier(string, '_');
    }

    public String getWordyName() {
        String string = _artifact.getName();
        return StrUtils.getWordyString(string);
    }

    public String getWordyAlias() {
        String string = StringUtils.defaultIfBlank(_artifact.getAlias(), _artifact.getName());
        return StrUtils.getWordyString(string);
    }

    public String getValidDefaultLabel() {
        return StrUtils.coalesce(_artifact.getDefaultLabel(), getSomeLabel());
    }

    public String getValidDefaultShortLabel() {
        return StrUtils.coalesce(_artifact.getDefaultShortLabel(), _artifact.getDefaultLabel(), getSomeShortLabel(true));
    }

    public String getValidDefaultShortLabel(boolean b) {
        return StrUtils.coalesce(_artifact.getDefaultShortLabel(), _artifact.getDefaultLabel(), getSomeShortLabel(b));
    }

    public String getValidDefaultCollectionLabel() {
        return StrUtils.coalesce(_artifact.getDefaultCollectionLabel(), pluralOfValidDefaultLabel());
    }

    private String pluralOfValidDefaultLabel() {
        Linguist linguist = Bundle.getLinguist();
        return linguist == null ? getValidDefaultLabel()
            : linguist.pluralOfMultiwordExpression(getValidDefaultLabel());
    }

    public String getValidDefaultCollectionShortLabel() {
        return StrUtils.coalesce(_artifact.getDefaultCollectionShortLabel(), _artifact.getDefaultCollectionLabel(), pluralOfValidDefaultShortLabel());
    }

    private String pluralOfValidDefaultShortLabel() {
        Linguist linguist = Bundle.getLinguist();
        return linguist == null ? getValidDefaultShortLabel()
            : linguist.pluralOfMultiwordExpression(getValidDefaultShortLabel());
    }

    public String getValidDefaultDescription() {
        return StrUtils.coalesce(_artifact.getDefaultDescription(), getValidDefaultLabel());
    }

    public String getValidDefaultShortDescription() {
        return StrUtils.coalesce(_artifact.getDefaultShortDescription(), _artifact.getDefaultDescription(), getValidDefaultLabel());
    }

    public String getValidDefaultTooltip() {
        return StrUtils.coalesce(_artifact.getDefaultTooltip(), getValidDefaultLabel());
    }

    protected String getSomeLabel() {
        String string = getWordyName();
        Entity declaringEntity = _artifact.getDeclaringEntity();
        Operation declaringOperation = _artifact.getDeclaringOperation();
        Entity declaringOperationEntity = declaringOperation == null ? null : declaringOperation.getDeclaringEntity();
        if (_artifact instanceof Property && declaringEntity != null) {
            Property property = (Property) _artifact;
            if (property.isNotDeclared()) {
            } else if (_artifact.equals(declaringEntity.getPrimaryKeyProperty())) {
                string = labelOf(declaringEntity, KeyProperty.PRIMARY_KEY);
            } else if (_artifact.equals(declaringEntity.getVersionProperty())) {
                string = labelOf(declaringEntity, KeyProperty.VERSION);
            } else if (_artifact.equals(declaringEntity.getBusinessKeyProperty())) {
                string = labelOf(declaringEntity, KeyProperty.BUSINESS_KEY);
            } else if (_artifact.equals(declaringEntity.getNumericKeyProperty())) {
                string = labelOf(declaringEntity, KeyProperty.NUMERIC_KEY);
            } else if (_artifact.equals(declaringEntity.getCharacterKeyProperty())) {
                string = labelOf(declaringEntity, KeyProperty.CHARACTER_KEY);
            } else if (_artifact.equals(declaringEntity.getNameProperty())) {
                string = labelOf(declaringEntity, KeyProperty.NAME);
            } else if (_artifact.equals(declaringEntity.getDescriptionProperty())) {
                string = labelOf(declaringEntity, KeyProperty.DESCRIPTION);
            } else if (_artifact.equals(declaringEntity.getInactiveIndicatorProperty())) {
                string = labelOf(declaringEntity, KeyProperty.INACTIVE_INDICATOR);
            } else if (_artifact.equals(declaringEntity.getUrlProperty())) {
                string = labelOf(declaringEntity, KeyProperty.URL);
            } else if (_artifact.equals(declaringEntity.getParentProperty())) {
                string = labelOf(declaringEntity, KeyProperty.PARENT);
            } else if (_artifact.equals(declaringEntity.getOwnerProperty())) {
                string = labelOf(declaringEntity, KeyProperty.OWNER);
            } else if (_artifact instanceof EntityReference) {
                string = StrUtils.removeWords(string, EntityReference.class);
            } else {
                string = StrUtils.removeWords(string, property.getDataType());
            }
        } else if (_artifact instanceof Parameter && declaringOperationEntity != null) {
            Parameter parameter = (Parameter) _artifact;
            if (parameter.isNotDeclared()) {
            } else if (_artifact instanceof EntityReference) {
                string = StrUtils.removeWords(string, EntityReference.class);
            } else {
                string = StrUtils.removeWords(string, parameter.getDataType());
            }
        }
        string = StringUtils.trim(StringUtils.replace(string, "  ", " "));
        return StringUtils.isNotBlank(string) ? string : getWordyName();
    }

    protected String getSomeShortLabel(boolean b) {
        String string = getWordyName();
        Entity declaringEntity = _artifact.getDeclaringEntity();
        String declaringEntityName = declaringEntity == null ? null : StrUtils.getWordyString(declaringEntity.getName());
        Operation declaringOperation = _artifact.getDeclaringOperation();
        Entity declaringOperationEntity = declaringOperation == null ? null : declaringOperation.getDeclaringEntity();
        String declaringOperationEntityName = declaringOperationEntity == null ? null : StrUtils.getWordyString(declaringOperationEntity.getName());
        if (_artifact instanceof Property && declaringEntity != null) {
            Property property = (Property) _artifact;
            if (property.isNotDeclared()) {
            } else if (_artifact.equals(declaringEntity.getPrimaryKeyProperty())) {
                string = shortLabelOf(declaringEntity, KeyProperty.PRIMARY_KEY, b);
            } else if (_artifact.equals(declaringEntity.getVersionProperty())) {
                string = shortLabelOf(declaringEntity, KeyProperty.VERSION, b);
            } else if (_artifact.equals(declaringEntity.getBusinessKeyProperty())) {
                string = shortLabelOf(declaringEntity, KeyProperty.BUSINESS_KEY, b);
            } else if (_artifact.equals(declaringEntity.getNumericKeyProperty())) {
                string = shortLabelOf(declaringEntity, KeyProperty.NUMERIC_KEY, b);
            } else if (_artifact.equals(declaringEntity.getCharacterKeyProperty())) {
                string = shortLabelOf(declaringEntity, KeyProperty.CHARACTER_KEY, b);
            } else if (_artifact.equals(declaringEntity.getNameProperty())) {
                string = shortLabelOf(declaringEntity, KeyProperty.NAME, b);
            } else if (_artifact.equals(declaringEntity.getDescriptionProperty())) {
                string = shortLabelOf(declaringEntity, KeyProperty.DESCRIPTION, b);
            } else if (_artifact.equals(declaringEntity.getInactiveIndicatorProperty())) {
                string = shortLabelOf(declaringEntity, KeyProperty.INACTIVE_INDICATOR, b);
            } else if (_artifact.equals(declaringEntity.getUrlProperty())) {
                string = shortLabelOf(declaringEntity, KeyProperty.URL, b);
            } else if (_artifact.equals(declaringEntity.getParentProperty())) {
                string = shortLabelOf(declaringEntity, KeyProperty.PARENT, b);
            } else if (_artifact.equals(declaringEntity.getOwnerProperty())) {
                string = shortLabelOf(declaringEntity, KeyProperty.OWNER, b);
            } else if (_artifact instanceof EntityReference) {
                string = StrUtils.removeWords(string, EntityReference.class);
                string = StrUtils.removeWholeWord(string, declaringEntityName);
            } else {
                string = StrUtils.removeWords(string, property.getDataType());
                string = StrUtils.removeWholeWord(string, declaringEntityName);
            }
        } else if (_artifact instanceof Parameter && declaringOperationEntity != null) {
            Parameter parameter = (Parameter) _artifact;
            if (parameter.isNotDeclared()) {
            } else if (_artifact instanceof EntityReference) {
                string = StrUtils.removeWords(string, EntityReference.class);
                string = StrUtils.removeWholeWord(string, declaringOperationEntityName);
            } else {
                string = StrUtils.removeWords(string, parameter.getDataType());
                string = StrUtils.removeWholeWord(string, declaringOperationEntityName);
            }
        }
        string = StringUtils.trim(StringUtils.replace(string, "  ", " "));
        return StringUtils.isNotBlank(string) ? string : getWordyName();
    }

    private String labelOf(Entity entity) {
        String label = entity == null ? null : entity.getDefaultLabel();
        String name = entity == null ? null : entity.getName();
        return StringUtils.isNotBlank(label) ? label : StrUtils.removeWords(StrUtils.getWordyString(name), EntityReference.class);
    }

    private String shortLabelOf(Entity entity) {
        String label = entity == null ? null : entity.getDefaultShortLabel();
        return StringUtils.isNotBlank(label) ? label : labelOf(entity);
    }

    private String labelOf(Entity entity, KeyProperty keyProperty) {
//      return entity.depth() == 0 && KeyProperty.PRIMARY_KEY.equals(keyProperty) ? labelOf(entity)
//          : entity.depth() == 1 && KeyProperty.BUSINESS_KEY.equals(keyProperty) ? labelOf(entity)
//              : shortWordyLabel(entity, false); // keyLabelOf(entity, keyProperty);
        return entity.depth() == 0 && KeyProperty.PRIMARY_KEY.equals(keyProperty) ? labelOf(entity)
            : shortWordyLabel(entity, false); // keyLabelOf(entity, keyProperty);
    }

    private String shortLabelOf(Entity entity, KeyProperty keyProperty, boolean b) {
        if (entity.depth() == 0) {
            if (KeyProperty.PRIMARY_KEY.equals(keyProperty)) {
                return shortLabelOf(entity);
            }
            return shortWordyName(entity); // keyProperty.getLabel()
        }
        if (b) {
            if (entity.depth() == 1) {
                if (KeyProperty.BUSINESS_KEY.equals(keyProperty)) {
//                  return labelOf(entity);
                    return shortWordyLabel(entity, true);
                }
                if (KeyProperty.NAME.equals(keyProperty)) {
                    return shortWordyLabel(entity, true);
                }
            }
//          return keyLabelOf(entity, keyProperty);
        }
        return shortWordyName(entity.getRoot());
    }

//  private String keyLabelOf(Entity entity, KeyProperty keyProperty) {
//      String pattern = keyProperty.getLabelPattern();
//      String label = shortLabelOf(entity);
//      String connector = entity.getResourceGender() == null ? "" : entity.getResourceGender().getConnector();
//      String string = MessageFormat.format(pattern, label, connector);
//      string = StringUtils.trim(StringUtils.replace(string, "  ", " "));
//      return string;
//  }
//
    private String shortWordyLabel(Entity entity, boolean shortest) {
        String name = shortWordyName(entity.getRoot());
        String connector = entity.getResourceGender() == null ? "" : entity.getResourceGender().getConnector();
        String label = shortest ? shortLabelOf(entity) : labelOf(entity);
        String string = StringUtils.trim(StringUtils.replace(name + " " + connector + " " + label, "  ", " "));
        return string;
    }

    private String shortWordyName(Entity entity) {
        return StrUtils.removeWholeWord(getWordyName(), StrUtils.getWordyString(entity.getName()));
    }

    public String getBundleWordyName() {
        return getBundleValueString(getWordyName());
    }

    public String getBundleWordyAlias() {
        return getBundleValueString(getWordyAlias());
    }

    public String getBundleDefaultLabel() {
        return getBundleValueString(_artifact.getDefaultLabel());
    }

    public String getBundleDefaultShortLabel() {
        return getBundleValueString(_artifact.getDefaultShortLabel());
    }

    public String getBundleDefaultCollectionLabel() {
        return getBundleValueString(_artifact.getDefaultCollectionLabel());
    }

    public String getBundleDefaultCollectionShortLabel() {
        return getBundleValueString(_artifact.getDefaultCollectionShortLabel());
    }

    public String getBundleDefaultDescription() {
        return getBundleValueString(_artifact.getDefaultDescription());
    }

    public String getBundleDefaultShortDescription() {
        return getBundleValueString(_artifact.getDefaultShortDescription());
    }

    public String getBundleDefaultTooltip() {
        return getBundleValueString(_artifact.getDefaultTooltip());
    }

    public String getBundleValidDefaultLabel() {
        return getBundleValueString(getValidDefaultLabel());
    }

    public String getBundleValidDefaultShortLabel() {
        return getBundleValueString(getValidDefaultShortLabel());
    }

    public String getBundleValidDefaultCollectionLabel() {
        return getBundleValueString(getValidDefaultCollectionLabel());
    }

    public String getBundleValidDefaultCollectionShortLabel() {
        return getBundleValueString(getValidDefaultCollectionShortLabel());
    }

    public String getBundleValidDefaultDescription() {
        return getBundleValueString(getValidDefaultDescription());
    }

    public String getBundleValidDefaultShortDescription() {
        return getBundleValueString(getValidDefaultShortDescription());
    }

    public String getBundleValidDefaultTooltip() {
        return getBundleValueString(getValidDefaultTooltip());
    }

    /**
     * @return the Bundle key
     */
    public String getBundleKey() {
        BundleProgrammer bundleProgrammer = ChiefProgrammer.getBundleProgrammer();
        return bundleProgrammer == null ? null : bundleProgrammer.getKeyString(_artifact);
    }

    protected String getBundleValueString(String string) {
        BundleProgrammer bundleProgrammer = ChiefProgrammer.getBundleProgrammer();
        return bundleProgrammer == null ? StrUtils.getStringJava(string) : bundleProgrammer.getValueString(string);
    }

    /**
     * @return the Java class name
     */
    public String getJavaClassName() {
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaClassName(_artifact);
    }

    /**
     * @return the Java constant name
     */
    public String getJavaConstantName() {
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaConstantName(_artifact);
    }

    /**
     * @return the Java constant name
     */
    public String getJavaLowerConstantName() {
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaLowerConstantName(_artifact);
    }

    /**
     * @return the Java variable name
     */
    public String getJavaVariableName() {
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaVariableName(_artifact);
    }

    public String getJavaName() {
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaName(_artifact);
    }

    public String getJavaCapitalizedName() {
        return StringUtils.capitalize(getJavaName());
    }

    public String getJavaUncapitalizedName() {
        return StringUtils.uncapitalize(getJavaName());
    }

    public String getJavaWordyName() {
        return getJavaString(getWordyName());
    }

    public String getJavaWordyAlias() {
        return getJavaString(getWordyAlias());
    }

    public String getJavaDefaultLabel() {
        return getJavaString(_artifact.getDefaultLabel());
    }

    public String getJavaDefaultShortLabel() {
        return getJavaString(_artifact.getDefaultShortLabel());
    }

    public String getJavaDefaultCollectionLabel() {
        return getJavaString(_artifact.getDefaultCollectionLabel());
    }

    public String getJavaDefaultCollectionShortLabel() {
        return getJavaString(_artifact.getDefaultCollectionShortLabel());
    }

    public String getJavaDefaultDescription() {
        return getJavaString(_artifact.getDefaultDescription());
    }

    public String getJavaDefaultShortDescription() {
        return getJavaString(_artifact.getDefaultShortDescription());
    }

    public String getJavaDefaultTooltip() {
        return getJavaString(_artifact.getDefaultTooltip());
    }

    public String getJavaValidDefaultLabel() {
        return getJavaString(getValidDefaultLabel());
    }

    public String getJavaValidDefaultShortLabel() {
        return getJavaString(getValidDefaultShortLabel());
    }

    public String getJavaValidDefaultCollectionLabel() {
        return getJavaString(getValidDefaultCollectionLabel());
    }

    public String getJavaValidDefaultCollectionShortLabel() {
        return getJavaString(getValidDefaultCollectionShortLabel());
    }

    public String getJavaValidDefaultDescription() {
        return getJavaString(getValidDefaultDescription());
    }

    public String getJavaValidDefaultShortDescription() {
        return getJavaString(getValidDefaultShortDescription());
    }

    public String getJavaValidDefaultTooltip() {
        return getJavaString(getValidDefaultTooltip());
    }

    protected String getJavaString(String string) {
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? StrUtils.getStringJava(string) : javaProgrammer.getJavaString(string);
    }

    public String getHtmlWordyName() {
        return getHtmlString(getWordyName());
    }

    public String getHtmlWordyAlias() {
        return getHtmlString(getWordyAlias());
    }

    public String getHtmlDefaultLabel() {
        return getHtmlString(_artifact.getDefaultLabel());
    }

    public String getHtmlDefaultShortLabel() {
        return getHtmlString(_artifact.getDefaultShortLabel());
    }

    public String getHtmlDefaultCollectionLabel() {
        return getHtmlString(_artifact.getDefaultCollectionLabel());
    }

    public String getHtmlDefaultCollectionShortLabel() {
        return getHtmlString(_artifact.getDefaultCollectionShortLabel());
    }

    public String getHtmlDefaultDescription() {
        return getHtmlString(_artifact.getDefaultDescription());
    }

    public String getHtmlDefaultShortDescription() {
        return getHtmlString(_artifact.getDefaultShortDescription());
    }

    public String getHtmlDefaultTooltip() {
        return getHtmlString(_artifact.getDefaultTooltip());
    }

    public String getHtmlValidDefaultLabel() {
        return getHtmlString(getValidDefaultLabel());
    }

    public String getHtmlValidDefaultShortLabel() {
        return getHtmlString(getValidDefaultShortLabel());
    }

    public String getHtmlValidDefaultCollectionLabel() {
        return getHtmlString(getValidDefaultCollectionLabel());
    }

    public String getHtmlValidDefaultCollectionShortLabel() {
        return getHtmlString(getValidDefaultCollectionShortLabel());
    }

    public String getHtmlValidDefaultDescription() {
        return getHtmlString(getValidDefaultDescription());
    }

    public String getHtmlValidDefaultShortDescription() {
        return getHtmlString(getValidDefaultShortDescription());
    }

    public String getHtmlValidDefaultTooltip() {
        return getHtmlString(getValidDefaultTooltip());
    }

    protected String getHtmlString(String string) {
        return StrUtils.getStringHtml(string);
    }

    public String getXmlWordyName() {
        return getXmlString(getWordyName());
    }

    public String getXmlWordyAlias() {
        return getXmlString(getWordyAlias());
    }

    public String getXmlDefaultLabel() {
        return getXmlString(_artifact.getDefaultLabel());
    }

    public String getXmlDefaultShortLabel() {
        return getXmlString(_artifact.getDefaultShortLabel());
    }

    public String getXmlDefaultCollectionLabel() {
        return getXmlString(_artifact.getDefaultCollectionLabel());
    }

    public String getXmlDefaultCollectionShortLabel() {
        return getXmlString(_artifact.getDefaultCollectionShortLabel());
    }

    public String getXmlDefaultDescription() {
        return getXmlString(_artifact.getDefaultDescription());
    }

    public String getXmlDefaultShortDescription() {
        return getXmlString(_artifact.getDefaultShortDescription());
    }

    public String getXmlDefaultTooltip() {
        return getXmlString(_artifact.getDefaultTooltip());
    }

    public String getXmlValidDefaultLabel() {
        return getXmlString(getValidDefaultLabel());
    }

    public String getXmlValidDefaultShortLabel() {
        return getXmlString(getValidDefaultShortLabel());
    }

    public String getXmlValidDefaultCollectionLabel() {
        return getXmlString(getValidDefaultCollectionLabel());
    }

    public String getXmlValidDefaultCollectionShortLabel() {
        return getXmlString(getValidDefaultCollectionShortLabel());
    }

    public String getXmlValidDefaultDescription() {
        return getXmlString(getValidDefaultDescription());
    }

    public String getXmlValidDefaultShortDescription() {
        return getXmlString(getValidDefaultShortDescription());
    }

    public String getXmlValidDefaultTooltip() {
        return getXmlString(getValidDefaultTooltip());
    }

    protected String getXmlString(String string) {
        return StrUtils.getStringXml(string);
    }

    /**
     * @return the SQL name
     */
    public String getSqlishName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlishName(_artifact);
    }

    /**
     * @return the SQL name
     */
    public String getSqlName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlName(_artifact);
    }

    /**
     * @param maxIdentifierLength
     * @return the SQL name
     */
    public String getSqlName(int maxIdentifierLength) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlName(_artifact, maxIdentifierLength);
    }

    /**
     * @param prefix
     * @return the SQL name
     */
    public String getPrefixedSqlName(String prefix) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlName(prefix, _artifact);
    }

    /**
     * @param prefix
     * @param maxIdentifierLength
     * @return the SQL name
     */
    public String getPrefixedSqlName(String prefix, int maxIdentifierLength) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlName(prefix, _artifact, maxIdentifierLength);
    }

    /**
     * @param suffix
     * @return the SQL name
     */
    public String getSuffixedSqlName(String suffix) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlName(_artifact, suffix);
    }

    /**
     * @param suffix
     * @param maxIdentifierLength
     * @return the SQL name
     */
    public String getSuffixedSqlName(String suffix, int maxIdentifierLength) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlName(_artifact, suffix, maxIdentifierLength);
    }

    /**
     * @param prefix
     * @param suffix
     * @return the SQL name
     */
    public String getAffixedSqlName(String prefix, String suffix) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlName(prefix, _artifact, suffix);
    }

    /**
     * @param prefix
     * @param suffix
     * @param maxIdentifierLength
     * @return the SQL name
     */
    public String getAffixedSqlName(String prefix, String suffix, int maxIdentifierLength) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlName(prefix, _artifact, suffix, maxIdentifierLength);
    }

    /**
     * @return the SQL qualified name
     */
    public String getSqlQualifiedName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlQualifiedName(_artifact);
    }

    /**
     * @return the SQL variable name
     */
    public String getSqlVariableName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlVariableName(_artifact);
    }

    /**
     * @return the SQL expresion
     */
    public String getSqlExpression() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlExpression(_artifact);
    }

    public ParameterizedExpression getSqlParameterizedExpression() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlParameterizedExpression(_artifact);
    }

}
