/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.wrappers;

import adalid.core.interfaces.Entity;
import adalid.core.interfaces.EntityReference;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class EntityWrapper extends DataArtifactWrapper {

    private Entity _entity;

    private EntityWrapper() {
        this(null);
    }

    public EntityWrapper(Entity entity) {
        super(entity);
        _entity = entity;
    }

    @Override
    public Entity getWrapped() {
        return _entity;
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
