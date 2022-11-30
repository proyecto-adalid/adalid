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

import adalid.commons.util.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
@EntityClass(resourceType = ResourceType.CONFIGURATION)
@EntitySelectOperation(enabled = Kleenean.TRUE, onload = SelectOnloadOption.EXECUTE, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityReportOperation(enabled = Kleenean.FALSE)
@EntityExportOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.UNSPECIFIED)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
@EntityReferenceSearch(searchType = SearchType.LIST)
public abstract class AbstractPersistentEnumerationEntity extends AbstractPersistentEntity implements PersistentEnumerationEntityReference {

    public AbstractPersistentEnumerationEntity(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }

    /**
     *
     */
    private static final Logger logger = Logger.getLogger(Entity.class);

    @Override
    public boolean finalise() {
        boolean ok = super.finalise();
        if (ok) {
            check();
        }
        return ok;
    }

    private void check() {
        if (isRootInstance()) {
            if (getBusinessKeyProperty() == null) {
                String message = getName() + " does not have a busines key property";
                logger.error(message);
                Project.increaseParserErrorCount();
            }
            checkInstances();
        }
    }

    private void checkInstances() {
        List<Instance> instances = getInstancesList();
        if (instances.isEmpty()) {
            logger.error("enumeration entity " + getFullName() + " has no instances");
            Project.increaseParserErrorCount();
        } else {
            Set<Locale> supportedLocales = TLC.getProject().getSupportedLocales();
            if (supportedLocales != null && !supportedLocales.isEmpty()) {
                String name, value;
                List<String> labels = new ArrayList<>();
                Property businessKeyProperty = getBusinessKeyProperty();
                localeLoop:
                for (Locale locale : supportedLocales) {
                    labels.clear();
                    instanceLoop:
                    for (Instance instance : instances) {
                        name = instance.getFullName();
                        fieldLoop:
                        for (InstanceField field : instance.getInstanceFieldsList()) {
                            if (businessKeyProperty.equals(field.getProperty())) {
                                value = field.getLocalizedValue(locale);
                                if (value == null) {
                                    value = StrUtils.getWordyString(instance.getName());
                                }
                                if (labels.contains(value)) {
                                    logger.error(name + " has duplicated business key \"" + value + "\" for locale \"" + locale + "\"");
                                    Project.increaseParserErrorCount();
                                } else {
                                    labels.add(value);
                                }
                                continue instanceLoop;
                            }
                        }
                        logger.error(name + " has no business key value for locale \"" + locale + "\"");
                        Project.increaseParserErrorCount();
                    }
                }
            }
            // tagging since 22/08/2022
            _customTagging = true;
            for (Instance instance : instances) {
                _customTagging &= instance.getCustomTag() != null;
            }
            /**/
        }
    }

    private boolean _customTagging;

    public boolean isCustomTagging() {
        return _customTagging;
    }

    /**
     * @return the insert enabled indicator
     */
    @Override
    public boolean isInsertEnabled() {
        return false;
    }

    /**
     * @return the update enabled indicator
     */
    @Override
    public boolean isUpdateEnabled() {
        if (super.isUpdateEnabled()) {
            List<Property> list = getPropertiesList();
            for (Property property : list) {
                if (property.isUpdateField()) {
                    if (property.isTableField() || (property.isDetailField() && isDetailViewEnabled())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @return the delete enabled indicator
     */
    @Override
    public boolean isDeleteEnabled() {
        return false;
    }

    @Override
    protected List<Class<? extends Annotation>> getValidFieldAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidFieldAnnotations();
        if (isProperty()) {
            valid.add(DiscriminatorColumn.class);
            valid.add(StateProperty.class);
        }
        return valid;
    }

    private Instance[] _removeInstanceArray;

    /**
     * @return true if either the remove instance array or the search instance array is not empty; false otherwise
     */
    public boolean isSelectItemsWithFilter() {
        return (_removeInstanceArray != null && _removeInstanceArray.length > 0)
            || (_searchInstanceArray != null && _searchInstanceArray.length > 0);
    }

    /**
     * @return the select items array
     */
    public Instance[] getSelectItemsArray() {
        List<Instance> list = getSelectItemsList();
        Instance[] array = new Instance[list.size()];
        return list.toArray(array);
    }

    /**
     * @return the select items list
     */
    public List<Instance> getSelectItemsList() {
        List<Instance> list = getInstancesList();
        if (_removeInstanceArray != null && _removeInstanceArray.length > 0) {
            List<Instance> remove = Arrays.asList(_removeInstanceArray);
            list.removeAll(remove);
        }
        if (_searchInstanceArray != null && _searchInstanceArray.length > 0) {
            List<Instance> retain = Arrays.asList(_searchInstanceArray);
            list.retainAll(retain);
        }
        return list;
    }

    /**
     * @return the remove instance array
     */
    public Instance[] getRemoveInstanceArray() {
        return _removeInstanceArray;
    }

    /**
     * El método setRemoveInstanceArray se utiliza para establecer el filtro de búsqueda del valor de la referencia (propiedad o parámetro que hace
     * referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la entidad. Este método solo se utiliza
     * cuando la referencia representa una enumeración (entidad que implementa la interfaz EnumerationEntity).
     *
     * @param instances una o más instancias de la enumeración que no se pueden utilizar como valor de la propiedad o parámetro
     */
    public void setRemoveInstanceArray(Instance... instances) {
        _removeInstanceArray = instances;
        _searchInstanceArray = null;
    }

    public String[] getRemoveInstanceStringArray() {
        if (_removeInstanceArray == null || _removeInstanceArray.length == 0) {
            return null;
        }
        int n = _removeInstanceArray.length;
        String[] strings = new String[n];
        for (int i = 0; i < n; i++) {
            strings[i] = _removeInstanceArray[i].getInstanceKeyValue().toString();
        }
        return strings;
    }

    private Instance[] _searchInstanceArray;

    /**
     * @return the search instance array
     */
    public Instance[] getSearchInstanceArray() {
        if (_searchInstanceArray == null && _removeInstanceArray != null && _removeInstanceArray.length > 0) {
            _searchInstanceArray = getInsertableRowsList().toArray(ArrUtils.arrayOf(Instance.class));
            for (Instance instance : _removeInstanceArray) {
                _searchInstanceArray = (Instance[]) ArrayUtils.removeElement(_searchInstanceArray, instance);
            }
        }
        return _searchInstanceArray;
    }

    /**
     * El método setSearchInstanceArray se utiliza para establecer el filtro de búsqueda del valor de la referencia (propiedad o parámetro que hace
     * referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la entidad. Este método solo se utiliza
     * cuando la referencia representa una enumeración (entidad que implementa la interfaz EnumerationEntity).
     *
     * @param instances una o más instancias de la enumeración que se pueden utilizar como valor de la propiedad o parámetro
     */
    public void setSearchInstanceArray(Instance... instances) {
        _searchInstanceArray = instances;
        _removeInstanceArray = null;
    }

    public String[] getSearchInstanceStringArray() {
        if (_searchInstanceArray == null || _searchInstanceArray.length == 0) {
            return null;
        }
        int n = _searchInstanceArray.length;
        String[] strings = new String[n];
        for (int i = 0; i < n; i++) {
            strings[i] = _searchInstanceArray[i].getInstanceKeyValue().toString();
        }
        return strings;
    }

}
