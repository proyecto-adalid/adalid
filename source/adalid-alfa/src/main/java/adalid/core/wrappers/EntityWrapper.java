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
import adalid.core.AbstractEntity;
import adalid.core.interfaces.*;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class EntityWrapper extends DataArtifactWrapper {

    private final Entity _entity;

    private final AbstractEntity _abstractEntity;

    public EntityWrapper(Entity entity) {
        super(entity);
        _entity = entity;
        _abstractEntity = _entity instanceof AbstractEntity ? (AbstractEntity) _entity : null;
    }

    @Override
    public Entity getWrapped() {
        return _entity;
    }

    public String getSelectFilterTag() {
        return _abstractEntity == null ? null : artifactTag(_abstractEntity.getSelectFilterTag(), _abstractEntity.getSelectFilter());
    }

    public String getInsertFilterTag(EntityReference reference) {
        return _abstractEntity == null ? null : artifactTag(_abstractEntity.getInsertFilterTag(), _abstractEntity.getInsertFilter(reference));
    }

    public String getUpdateFilterTag() {
        return _abstractEntity == null ? null : artifactTag(_abstractEntity.getUpdateFilterTag(), _abstractEntity.getUpdateFilter());
    }

    public String getDeleteFilterTag() {
        return _abstractEntity == null ? null : artifactTag(_abstractEntity.getDeleteFilterTag(), _abstractEntity.getDeleteFilter());
    }

    public String getSearchQueryFilterTag() {
        return _abstractEntity == null ? null : artifactTag(_abstractEntity.getSearchQueryFilterTag(), _abstractEntity.getSearchQueryFilter());
    }

    public String getMasterDetailFilterTag(EntityReference reference) {
        return _abstractEntity == null ? null : artifactTag(_abstractEntity.getMasterDetailFilterTag(), _abstractEntity.getMasterDetailFilter(reference));
    }

    public String getValidDefaultLabel(EntityReference reference) {
        String string = reference == null ? null : _entity.getDefaultLabel(reference);
        return StringUtils.isNotBlank(string) ? string : getValidDefaultLabel();
    }

    public String getValidDefaultShortLabel(EntityReference reference) {
        String string = reference == null ? null : _entity.getDefaultShortLabel(reference);
        return StringUtils.isNotBlank(string) ? string : getValidDefaultShortLabel();
    }

    public String getValidDefaultCollectionLabel(EntityReference reference) {
        String string = reference == null ? null : _entity.getDefaultCollectionLabel(reference);
        return StringUtils.isNotBlank(string) ? string : getValidDefaultCollectionLabel();
    }

    public String getValidDefaultCollectionShortLabel(EntityReference reference) {
        String string = reference == null ? null : _entity.getDefaultCollectionShortLabel(reference);
        return StringUtils.isNotBlank(string) ? string : getValidDefaultCollectionShortLabel();
    }

    @Override
    public String getValidDefaultTooltip() {
        return StrUtils.coalesce(getDefaultTooltip(), getDefaultShortDescription(), getValidDefaultLabel());
    }

    public String getBundleValidDefaultLabel(EntityReference reference) {
        return getBundleValueString(getValidDefaultLabel(reference));
    }

    public String getBundleValidDefaultShortLabel(EntityReference reference) {
        return getBundleValueString(getValidDefaultShortLabel(reference));
    }

    public String getBundleValidDefaultCollectionLabel(EntityReference reference) {
        return getBundleValueString(getValidDefaultCollectionLabel(reference));
    }

    public String getBundleValidDefaultCollectionShortLabel(EntityReference reference) {
        return getBundleValueString(getValidDefaultCollectionShortLabel(reference));
    }

    public String getJavaValidDefaultLabel(EntityReference reference) {
        return getJavaString(getValidDefaultLabel(reference));
    }

    public String getJavaValidDefaultShortLabel(EntityReference reference) {
        return getJavaString(getValidDefaultShortLabel(reference));
    }

    public String getJavaValidDefaultCollectionLabel(EntityReference reference) {
        return getJavaString(getValidDefaultCollectionLabel(reference));
    }

    public String getJavaValidDefaultCollectionShortLabel(EntityReference reference) {
        return getJavaString(getValidDefaultCollectionShortLabel(reference));
    }

    public String getXmlValidDefaultLabel(EntityReference reference) {
        return getXmlString(getValidDefaultLabel(reference));
    }

    public String getXmlValidDefaultShortLabel(EntityReference reference) {
        return getXmlString(getValidDefaultShortLabel(reference));
    }

    public String getXmlValidDefaultCollectionLabel(EntityReference reference) {
        return getXmlString(getValidDefaultCollectionLabel(reference));
    }

    public String getXmlValidDefaultCollectionShortLabel(EntityReference reference) {
        return getXmlString(getValidDefaultCollectionShortLabel(reference));
    }

}
