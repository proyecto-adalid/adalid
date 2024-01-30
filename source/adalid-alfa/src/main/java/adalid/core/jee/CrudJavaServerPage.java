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
package adalid.core.jee;

import adalid.core.*;
import adalid.core.interfaces.*;
import adalid.core.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Jorge Campins
 */
public class CrudJavaServerPage extends JavaServerPage {

    public CrudJavaServerPage(String name) {
        super(name);
    }

    private List<PageField> _fields;

    private List<PageField> _masterFields;

    private Set<Entity> _entitiesReferencedByFields;

    /**
     * @param hidden whether hidden fields should be included in the list or not
     * @return the fields list
     */
    @Override
    public List<PageField> getFields(boolean hidden) {
        Property keyProperty;
        PageField field, child;
        boolean excludeHiddenFields = !hidden;
        if (_fields == null) {
            _fields = new ArrayList<>();
            Entity entity = getEntity();
            if (entity instanceof PersistentEntity persistentEntity) {
                QueryTable queryTable = persistentEntity.getQueryTable();
                setQueryTable(queryTable);
                List<Property> columns = persistentEntity.getDataProviderColumnsList();
                for (Property column : columns) {
                    if (column.isHiddenField() && excludeHiddenFields) {
                        continue;
                    }
                    field = new PageField(this, column);
                    _fields.add(field);
                    if (column instanceof Entity reference) {
                        keyProperty = reference.getBusinessKeyProperty();
                        child = addChildField(_fields, queryTable, keyProperty, field);
                        if (child != null) {
                            child.setForeignCode(true);
                        }
                        keyProperty = reference.getNameProperty();
                        child = addChildField(_fields, queryTable, keyProperty, field);
                        if (child != null) {
                            child.setForeignName(true);
                        }
                    }
                }
                List<EntityCollection> collections = persistentEntity.getEntityCollectionsList();
                for (EntityCollection collection : collections) {
                    field = new PageField(this, collection);
                    _fields.add(field);
                }
            }
        }
        return _fields;
    }

    /**
     * @param hidden whether hidden fields should be included in the list or not
     * @return the master heading fields list
     */
    @Override
    public List<PageField> getMasterHeadingFields(boolean hidden) {
        Property keyProperty;
        PageField field, child;
        if (_masterFields == null) {
            _masterFields = new ArrayList<>();
            Entity master = getMaster();
            if (master instanceof PersistentEntity persistentEntity) {
                QueryTable queryTable = persistentEntity.getQueryTable();
                setMasterQueryTable(queryTable);
                List<Property> columns = persistentEntity.getDataProviderColumnsList();
                for (Property column : columns) {
                    if (column.isHeadingField()) {
                        field = new PageField(this, column);
                        _masterFields.add(field);
                        if (column instanceof Entity reference) {
                            keyProperty = reference.getBusinessKeyProperty();
                            child = addChildField(_masterFields, queryTable, keyProperty, field);
                            if (child != null) {
                                child.setForeignCode(true);
                            }
                            keyProperty = reference.getNameProperty();
                            child = addChildField(_masterFields, queryTable, keyProperty, field);
                            if (child != null) {
                                child.setForeignName(true);
                            }
                        }
                    }
                }
            }
        }
        return _masterFields;
    }

    private PageField addChildField(List<PageField> list, QueryTable queryTable, Property keyProperty, PageField parent) {
        if (keyProperty != null && queryTable.contains(keyProperty)) {
            PageField field = new PageField(this, queryTable, keyProperty, parent);
            list.add(field);
            return field;
        }
        return null;
    }

    /**
     * @param hidden whether hidden fields should be included in the set or not
     * @return the list of entities referenced by fields
     */
    @Override
    public Set<Entity> getEntitiesReferencedByFields(boolean hidden) {
        if (_entitiesReferencedByFields == null) {
            _entitiesReferencedByFields = new LinkedHashSet<>();
            addEntitiesReferencedByFields(getFields(hidden));
            addEntitiesReferencedByFields(getMasterHeadingFields(hidden));
        }
        return _entitiesReferencedByFields;
    }

    private void addEntitiesReferencedByFields(List<PageField> fields) {
        for (PageField field : fields) {
            DataArtifact dataArtifact = field.getDataArtifact();
            if (dataArtifact instanceof Entity reference) {
                _entitiesReferencedByFields.add(reference);
            }
        }
    }

}
