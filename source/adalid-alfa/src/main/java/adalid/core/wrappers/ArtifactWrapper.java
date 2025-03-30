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

import adalid.commons.bundles.*;
import adalid.commons.i18n.*;
import adalid.commons.interfaces.*;
import adalid.commons.util.*;
import adalid.core.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.primitives.*;
import adalid.core.programmers.*;
import java.text.MessageFormat;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class ArtifactWrapper implements Wrapper {

    private final Artifact _artifact;

    private final BoundedArtifact _boundedArtifact;

    private final DataArtifact _dataArtifact;

    private final EntityCollection _entityCollection;

    private final Property _property;

    public ArtifactWrapper(Artifact artifact) {
        _artifact = artifact;
        _boundedArtifact = _artifact instanceof BoundedArtifact ? (BoundedArtifact) _artifact : null;
        _dataArtifact = _artifact instanceof DataArtifact ? (DataArtifact) _artifact : null;
        _entityCollection = _artifact instanceof EntityCollection ? (EntityCollection) _artifact : null;
        _property = _artifact instanceof Property ? (Property) _artifact : null;
    }

    @Override
    public Artifact getWrapped() {
        return _artifact;
    }

    public String getMaximumValueTag() {
        return _boundedArtifact == null ? null : artifactTag(_boundedArtifact.getMaximumValueTag(), _boundedArtifact.getMaxValue());
    }

    public String getMinimumValueTag() {
        return _boundedArtifact == null ? null : artifactTag(_boundedArtifact.getMinimumValueTag(), _boundedArtifact.getMinValue());
    }

    public String getNullifyingFilterTag() {
        return _entityCollection == null ? null : artifactTag(_entityCollection.getNullifyingFilterTag(), _entityCollection.getNullifyingFilter());
    }

    public String getRenderingFilterTag() {
        return _entityCollection == null ? null : artifactTag(_entityCollection.getRenderingFilterTag(), _entityCollection.getRenderingFilter());
    }

    protected String artifactTag(String tag, Object value) {
        if (StringUtils.isNotBlank(tag)) {
            return tag;
        }
        if (value instanceof Artifact artifact) {
            String description = artifact.getDefaultShortDescription();
            String label = artifact.getDefaultLabel();
            return StringUtils.defaultIfBlank(description, label);
        }
        return null;
    }

    public String getDottedName() {
        String string = _artifact.getName();
        return StrUtils.getLowerCaseIdentifier(string, '.');
    }

    public String getDottedAlias() {
        String string = StrUtils.coalesce(_artifact.getAlias(), _artifact.getName());
        return StrUtils.getLowerCaseIdentifier(string, '.');
    }

    public String getHyphenatedName() {
        String string = _artifact.getName();
        return StrUtils.getLowerCaseIdentifier(string, '-');
    }

    public String getHyphenatedAlias() {
        String string = StrUtils.coalesce(_artifact.getAlias(), _artifact.getName());
        return StrUtils.getLowerCaseIdentifier(string, '-');
    }

    public String getUnderscoredName() {
        String string = _artifact.getName();
        return StrUtils.getLowerCaseIdentifier(string, '_');
    }

    public String getUnderscoredAlias() {
        String string = StrUtils.coalesce(_artifact.getAlias(), _artifact.getName());
        return StrUtils.getLowerCaseIdentifier(string, '_');
    }

    public String getWordyName() {
        String string = _artifact.getName();
        return StrUtils.getWordyString(string);
    }

    public String getWordyAlias() {
        String string = StrUtils.coalesce(_artifact.getAlias(), _artifact.getName());
        return StrUtils.getWordyString(string);
    }

    public String getValidDefaultLabel() {
        return StrUtils.coalesce(getDefaultLabel(), getSomeLabel());
    }

    public String getValidDefaultShortLabel() {
        return getValidDefaultShortLabel(true);
    }

    public String getValidDefaultShortLabel(boolean b) {
        return StrUtils.coalesce(getDefaultShortLabel(), getDefaultLabel(), getSomeShortLabel(b));
    }

    public String getValidDefaultCollectionLabel() {
        return StrUtils.coalesce(getDefaultCollectionLabel(), pluralOfValidDefaultLabel(true));
    }

    private String pluralOfValidDefaultLabel(boolean capitalize) {
        Linguist linguist = Bundle.getLinguist();
        if (linguist == null) {
            return getValidDefaultLabel();
        }
        String plural = linguist.pluralOfMultiwordExpression(getValidDefaultLabel());
        return capitalize ? linguist.capitalize(plural) : plural;
    }

    public String getValidDefaultCollectionShortLabel() {
        return StrUtils.coalesce(getDefaultCollectionShortLabel(), getDefaultCollectionLabel(), pluralOfValidDefaultShortLabel(true));
    }

    private String pluralOfValidDefaultShortLabel(boolean capitalize) {
        Linguist linguist = Bundle.getLinguist();
        if (linguist == null) {
            return getValidDefaultShortLabel();
        }
        String plural = linguist.pluralOfMultiwordExpression(getValidDefaultShortLabel());
        return capitalize ? linguist.capitalize(plural) : plural;
    }

    public String getValidDefaultDescription() {
        return StrUtils.coalesce(getDefaultDescription(), getValidDefaultLabel());
    }

    public String getValidDefaultShortDescription() {
        return StrUtils.coalesce(getDefaultShortDescription(), getValidDefaultDescription());
    }

    public String getValidDefaultTooltip() {
        return StrUtils.coalesce(getDefaultTooltip(), getValidDefaultShortDescription());
    }

    public String getValidDefaultSymbol() {
        return StrUtils.coalesce(getDefaultSymbol(), someSymbol());
    }

    protected String someSymbol() {
        Artifact defaultArtifact = defaultArtifact();
        if (defaultArtifact instanceof NumericPrimitive) {
            NumericPrimitive numericPrimitive = (NumericPrimitive) defaultArtifact;
            return StringUtils.trimToEmpty(numericPrimitive.getSymbol());
        }
        return "";
    }

    protected String getDefaultLabel() {
        return defaultArtifact().getDefaultLabel();
    }

    protected String getDefaultShortLabel() {
        return defaultArtifact().getDefaultShortLabel();
    }

    protected String getDefaultCollectionLabel() {
        return _artifact.getDefaultCollectionLabel();
    }

    protected String getDefaultCollectionShortLabel() {
        return _artifact.getDefaultCollectionShortLabel();
    }

    protected String getDefaultDescription() {
        /*
        return defaultArtifact().getDefaultDescription();
        /**/
        String defaultDescription = defaultArtifact().getDefaultDescription();
        return defaultDescription == null ? linkedPropertyDefaultDescription() : defaultDescription;
    }

    private String linkedPropertyDefaultDescription() {
        Property linkedProperty = linkedProperty();
        return linkedProperty == null ? null : linkedProperty.getDefaultDescription();
    }

    protected String getDefaultShortDescription() {
        /*
        return defaultArtifact().getDefaultShortDescription();
        /**/
        String defaultShortDescription = defaultArtifact().getDefaultShortDescription();
        return defaultShortDescription == null ? linkedPropertyDefaultShortDescription() : defaultShortDescription;
    }

    private String linkedPropertyDefaultShortDescription() {
        Property linkedProperty = linkedProperty();
        return linkedProperty == null ? null : linkedProperty.getDefaultShortDescription();
    }

    protected String getDefaultTooltip() {
        /*
        return defaultArtifact().getDefaultTooltip();
        /**/
        String defaultTooltip = defaultArtifact().getDefaultTooltip();
        return defaultTooltip == null ? linkedPropertyDefaultTooltip() : defaultTooltip;
    }

    private String linkedPropertyDefaultTooltip() {
        Property linkedProperty = linkedProperty();
        return linkedProperty == null ? null : linkedProperty.getDefaultTooltip();
    }

    protected String getDefaultSymbol() {
        /*
        return defaultArtifact().getDefaultSymbol();
        /**/
        String defaultSymbol = defaultArtifact().getDefaultSymbol();
        return defaultSymbol == null ? linkedPropertyDefaultSymbol() : defaultSymbol;
    }

    private String linkedPropertyDefaultSymbol() {
        Property linkedProperty = linkedProperty();
        return linkedProperty == null ? null : linkedProperty.getDefaultSymbol();
    }

    private Artifact defaultArtifact() {
        return _dataArtifact != null && _dataArtifact.isProperty() ? _property.getPropertyAtRoot() : _artifact;
    }

    private Property linkedProperty() {
        if (_dataArtifact != null && _dataArtifact.isParameter()) {
            Parameter parameter = (Parameter) _dataArtifact;
            Property linkedProperty = parameter.getLinkedProperty();
            if (linkedProperty != null) {
                return linkedProperty.getPropertyAtRoot();
            }
        }
        return null;
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
            } else if (_artifact.equals(declaringEntity.getSequenceProperty())) {
                string = labelOf(declaringEntity, KeyProperty.SEQUENCE);
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
            } else if (_artifact.equals(declaringEntity.getImageProperty())) {
                string = labelOf(declaringEntity, KeyProperty.IMAGE);
            } else if (_artifact.equals(declaringEntity.getInactiveIndicatorProperty())) {
                string = labelOf(declaringEntity, KeyProperty.INACTIVE_INDICATOR);
            } else if (_artifact.equals(declaringEntity.getUrlProperty())) {
                string = labelOf(declaringEntity, KeyProperty.URL);
            } else if (_artifact.equals(declaringEntity.getParentProperty())) {
                string = labelOf(declaringEntity, KeyProperty.PARENT);
            } else if (_artifact.equals(declaringEntity.getOwnerProperty())) {
                string = labelOf(declaringEntity, KeyProperty.OWNER);
            } else if (_artifact.equals(declaringEntity.getUserProperty())) {
                string = labelOf(declaringEntity, KeyProperty.USER);
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
            } else if (_artifact.equals(declaringEntity.getSequenceProperty())) {
                string = shortLabelOf(declaringEntity, KeyProperty.SEQUENCE, b);
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
            } else if (_artifact.equals(declaringEntity.getImageProperty())) {
                string = shortLabelOf(declaringEntity, KeyProperty.IMAGE, b);
            } else if (_artifact.equals(declaringEntity.getInactiveIndicatorProperty())) {
                string = shortLabelOf(declaringEntity, KeyProperty.INACTIVE_INDICATOR, b);
            } else if (_artifact.equals(declaringEntity.getUrlProperty())) {
                string = shortLabelOf(declaringEntity, KeyProperty.URL, b);
            } else if (_artifact.equals(declaringEntity.getParentProperty())) {
                string = shortLabelOf(declaringEntity, KeyProperty.PARENT, b);
            } else if (_artifact.equals(declaringEntity.getOwnerProperty())) {
                string = shortLabelOf(declaringEntity, KeyProperty.OWNER, b);
            } else if (_artifact.equals(declaringEntity.getUserProperty())) {
                string = shortLabelOf(declaringEntity, KeyProperty.USER, b);
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
        /*
        return entity.depth() == 0 && KeyProperty.PRIMARY_KEY.equals(keyProperty) ? labelOf(entity)
            : shortWordyLabel(entity, false); // keyLabelOf(entity, keyProperty);
        **/
        if (entity.depth() == 0) {
            if (KeyProperty.PRIMARY_KEY.equals(keyProperty)) {
                return labelOf(entity);
            }
            if (KeyProperty.VERSION.equals(keyProperty)) {
                return keyLabelOf(entity, keyProperty, false);
            }
        }
        if (entity.depth() == 1) {
            if (KeyProperty.BUSINESS_KEY.equals(keyProperty)) {
                return keyLabelOf(entity, keyProperty, false);
            }
            if (KeyProperty.NAME.equals(keyProperty)) {
                return keyLabelOf(entity, keyProperty, false);
            }
        }
        return shortWordyLabel(entity, false);
    }

    private String shortLabelOf(Entity entity, KeyProperty keyProperty, boolean b) {
        if (entity.depth() == 0) {
            if (KeyProperty.PRIMARY_KEY.equals(keyProperty)) {
                return shortLabelOf(entity);
            }
            if (KeyProperty.VERSION.equals(keyProperty)) {
                return keyProperty.getLabel();
            }
            return shortKeyLabelOf(entity); // shortWordyName(entity); // keyProperty.getLabel();
        }
        if (b) {
            if (entity.depth() == 1) {
                if (KeyProperty.BUSINESS_KEY.equals(keyProperty)) {
//                  return labelOf(entity);
//                  return shortWordyLabel(entity, true);
                    return keyLabelOf(entity, keyProperty, true);
                }
                if (KeyProperty.NAME.equals(keyProperty)) {
//                  return shortWordyLabel(entity, true);
                    return keyLabelOf(entity, keyProperty, true);
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
    private String keyLabelOf(Entity entity, KeyProperty keyProperty, boolean shortest) {
        String pattern = keyProperty.getLabelPattern();
        String label = shortest ? shortLabelOf(entity) : labelOf(entity);
        String connector = entity.getResourceGender() == null ? "" : entity.getResourceGender().getConnector();
        String string = MessageFormat.format(pattern, label, connector);
        string = StringUtils.trim(StringUtils.replace(string, "  ", " "));
        return string;
    }

    private String shortKeyLabelOf(Entity entity) {
        String string = getWordyName();
        String declaringEntityName = StrUtils.getWordyString(entity.getName());
        Property property = (Property) _artifact;
        string = StrUtils.removeWords(string, property.getDataType());
        string = StrUtils.removeWholeWord(string, declaringEntityName);
        return string;
    }

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
        return getBundleValueString(getDefaultLabel());
    }

    public String getBundleDefaultShortLabel() {
        return getBundleValueString(getDefaultShortLabel());
    }

    public String getBundleDefaultCollectionLabel() {
        return getBundleValueString(getDefaultCollectionLabel());
    }

    public String getBundleDefaultCollectionShortLabel() {
        return getBundleValueString(getDefaultCollectionShortLabel());
    }

    public String getBundleDefaultDescription() {
        return getBundleValueString(getDefaultDescription());
    }

    public String getBundleDefaultShortDescription() {
        return getBundleValueString(getDefaultShortDescription());
    }

    public String getBundleDefaultTooltip() {
        return getBundleValueString(getDefaultTooltip());
    }

    public String getBundleDefaultSymbol() {
        return getBundleValueString(getDefaultSymbol());
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

    public String getBundleValidDefaultSymbol() {
        return getBundleValueString(getValidDefaultSymbol());
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
        return getJavaClassName(); // 30/04/2021
    }

    public String getJavaUncapitalizedName() {
        return getJavaVariableName(); // 30/04/2021
    }

    public String getJavaWordyName() {
        return getJavaString(getWordyName());
    }

    public String getJavaWordyAlias() {
        return getJavaString(getWordyAlias());
    }

    public String getJavaDefaultLabel() {
        return getJavaString(getDefaultLabel());
    }

    public String getJavaDefaultShortLabel() {
        return getJavaString(getDefaultShortLabel());
    }

    public String getJavaDefaultCollectionLabel() {
        return getJavaString(getDefaultCollectionLabel());
    }

    public String getJavaDefaultCollectionShortLabel() {
        return getJavaString(getDefaultCollectionShortLabel());
    }

    public String getJavaDefaultDescription() {
        return getJavaString(getDefaultDescription());
    }

    public String getJavaDefaultShortDescription() {
        return getJavaString(getDefaultShortDescription());
    }

    public String getJavaDefaultTooltip() {
        return getJavaString(getDefaultTooltip());
    }

    public String getJavaDefaultSymbol() {
        return getJavaString(getDefaultSymbol());
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

    public String getJavaValidDefaultSymbol() {
        return getJavaString(getValidDefaultSymbol());
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
        return getHtmlString(getDefaultLabel());
    }

    public String getHtmlDefaultShortLabel() {
        return getHtmlString(getDefaultShortLabel());
    }

    public String getHtmlDefaultCollectionLabel() {
        return getHtmlString(getDefaultCollectionLabel());
    }

    public String getHtmlDefaultCollectionShortLabel() {
        return getHtmlString(getDefaultCollectionShortLabel());
    }

    public String getHtmlDefaultDescription() {
//      return getHtmlString(getDefaultDescription());
        return getHtmlFormattedString(getDefaultDescription());
    }

    public String getXhtmlDefaultDescription() {
//      return getHtmlString(getDefaultDescription());
        return getXhtmlFormattedString(getDefaultDescription());
    }

    public String getHtmlDefaultShortDescription() {
        return getHtmlString(getDefaultShortDescription());
    }

    public String getHtmlDefaultTooltip() {
        return getHtmlString(getDefaultTooltip());
    }

    public String getHtmlDefaultSymbol() {
        return getHtmlString(getDefaultSymbol());
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

    public String getHtmlValidDefaultSymbol() {
        return getHtmlString(getValidDefaultSymbol());
    }

    protected String getHtmlString(String string) {
        if (StringUtils.isBlank(string)) {
            return null;
        }
        String html = StrUtils.getStringHtml(string);
        return html.replaceAll("\\p{Cntrl}", " ");
    }

    protected String getHtmlFormattedString(String string) {
        return MarkupUtils.getHtmlFormattedString(string);
    }

    protected String getXhtmlString(String string) {
        if (StringUtils.isBlank(string)) {
            return null;
        }
        final String br1 = "<br>";
        final String br2 = "<br/>";
        String html = getHtmlString(string);
        return StringUtils.replace(html, br1, br2);
    }

    protected String getXhtmlFormattedString(String string) {
        return MarkupUtils.getXhtmlFormattedString(string);
    }

    public String getXmlWordyName() {
        return getXmlString(getWordyName());
    }

    public String getXmlWordyAlias() {
        return getXmlString(getWordyAlias());
    }

    public String getXmlDefaultLabel() {
        return getXmlString(getDefaultLabel());
    }

    public String getXmlDefaultShortLabel() {
        return getXmlString(getDefaultShortLabel());
    }

    public String getXmlDefaultCollectionLabel() {
        return getXmlString(getDefaultCollectionLabel());
    }

    public String getXmlDefaultCollectionShortLabel() {
        return getXmlString(getDefaultCollectionShortLabel());
    }

    public String getXmlDefaultDescription() {
        return getXmlString(getDefaultDescription());
    }

    public String getXmlDefaultShortDescription() {
        return getXmlString(getDefaultShortDescription());
    }

    public String getXmlDefaultTooltip() {
        return getXmlString(getDefaultTooltip());
    }

    public String getXmlDefaultSymbol() {
        return getXmlString(getDefaultSymbol());
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

    public String getXmlValidDefaultSymbol() {
        return getXmlString(getValidDefaultSymbol());
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
     * @param maxIdentifierLength max identifier length
     * @return the SQL name
     */
    public String getSqlName(int maxIdentifierLength) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlName(_artifact, maxIdentifierLength);
    }

    /**
     * @param prefix prefix
     * @return the SQL name
     */
    public String getPrefixedSqlName(String prefix) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlName(prefix, _artifact);
    }

    /**
     * @param prefix prefix
     * @param maxIdentifierLength max identifier length
     * @return the SQL name
     */
    public String getPrefixedSqlName(String prefix, int maxIdentifierLength) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlName(prefix, _artifact, maxIdentifierLength);
    }

    /**
     * @param suffix suffix
     * @return the SQL name
     */
    public String getSuffixedSqlName(String suffix) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlName(_artifact, suffix);
    }

    /**
     * @param suffix suffix
     * @param maxIdentifierLength max identifier length
     * @return the SQL name
     */
    public String getSuffixedSqlName(String suffix, int maxIdentifierLength) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlName(_artifact, suffix, maxIdentifierLength);
    }

    /**
     * @param prefix prefix
     * @param suffix suffix
     * @return the SQL name
     */
    public String getAffixedSqlName(String prefix, String suffix) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlName(prefix, _artifact, suffix);
    }

    /**
     * @param prefix prefix
     * @param suffix suffix
     * @param maxIdentifierLength max identifier length
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

    public String getSqlDefaultLabel() {
        return getSqlString(getDefaultLabel());
    }

    public String getSqlDefaultShortLabel() {
        return getSqlString(getDefaultShortLabel());
    }

    public String getSqlDefaultCollectionLabel() {
        return getSqlString(getDefaultCollectionLabel());
    }

    public String getSqlDefaultCollectionShortLabel() {
        return getSqlString(getDefaultCollectionShortLabel());
    }

    public String getSqlDefaultDescription() {
        return getSqlString(getDefaultDescription());
    }

    public String getSqlDefaultShortDescription() {
        return getSqlString(getDefaultShortDescription());
    }

    public String getSqlDefaultTooltip() {
        return getSqlString(getDefaultTooltip());
    }

    public String getSqlDefaultSymbol() {
        return getSqlString(getDefaultSymbol());
    }

    public String getSqlValidDefaultLabel() {
        return getSqlString(getValidDefaultLabel());
    }

    public String getSqlValidDefaultShortLabel() {
        return getSqlString(getValidDefaultShortLabel());
    }

    public String getSqlValidDefaultShortLabel(boolean b) {
        return getSqlString(getValidDefaultShortLabel(b));
    }

    public String getSqlValidDefaultCollectionLabel() {
        return getSqlString(getValidDefaultCollectionLabel());
    }

    public String getSqlValidDefaultCollectionShortLabel() {
        return getSqlString(getValidDefaultCollectionShortLabel());
    }

    public String getSqlValidDefaultDescription() {
        return getSqlString(getValidDefaultDescription());
    }

    public String getSqlValidDefaultShortDescription() {
        return getSqlString(getValidDefaultShortDescription());
    }

    public String getSqlValidDefaultTooltip() {
        return getSqlString(getValidDefaultTooltip());
    }

    public String getSqlValidDefaultSymbol() {
        return getSqlString(getValidDefaultSymbol());
    }

    protected String getSqlString(String string) {
        if (string == null) {
            return null;
        }
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer.getString(string.replaceAll("\\p{Cntrl}", " "));
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
