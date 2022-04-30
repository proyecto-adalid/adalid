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
import java.util.List;

/**
 * @author Jorge Campins
 */
public class CrudJavaServerPage extends JavaServerPage {

    public CrudJavaServerPage(String name) {
        super(name);
    }

    private List<PageField> _fields;

    private List<PageField> _masterFields;

    /**
     * @return the fields list
     */
    @Override
    public List<PageField> getFields() {
        Entity entity, reference;
        PersistentEntity persistentEntity;
        QueryTable queryTable;
        List<Property> columns;
        List<EntityCollection> collections;
        Property keyProperty;
        PageField field, child;
        if (_fields == null) {
            _fields = new ArrayList<>();
            entity = getEntity();
            if (entity instanceof PersistentEntity) {
                persistentEntity = (PersistentEntity) entity;
                queryTable = persistentEntity.getQueryTable();
                setQueryTable(queryTable);
                columns = persistentEntity.getDataProviderColumnsList();
                for (Property column : columns) {
                    if (column.isHiddenField()) {
                        continue;
                    }
                    field = new PageField(this, column);
                    _fields.add(field);
                    if (column instanceof Entity) {
                        reference = (Entity) column;
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
                collections = persistentEntity.getEntityCollectionsList();
                for (EntityCollection collection : collections) {
                    field = new PageField(this, collection);
                    _fields.add(field);
                }
            }
        }
        return _fields;
    }

    /**
     * @return the master heading fields list
     */
    @Override
    public List<PageField> getMasterHeadingFields() {
        Entity master, reference;
        PersistentEntity persistentEntity;
        QueryTable queryTable;
        List<Property> columns;
        Property keyProperty;
        PageField field, child;
        if (_masterFields == null) {
            _masterFields = new ArrayList<>();
            master = getMaster();
            if (master instanceof PersistentEntity) {
                persistentEntity = (PersistentEntity) master;
                queryTable = persistentEntity.getQueryTable();
                setMasterQueryTable(queryTable);
                columns = persistentEntity.getDataProviderColumnsList();
                for (Property column : columns) {
                    if (column.isHeadingField()) {
                        field = new PageField(this, column);
                        _masterFields.add(field);
                        if (column instanceof Entity) {
                            reference = (Entity) column;
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

}
