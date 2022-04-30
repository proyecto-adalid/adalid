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
package meta.entidad.comun.control.prueba;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.entidad.base.PersistentEntityBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.TESTING, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, onload = SelectOnloadOption.EXECUTE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, inserts = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE)
//@EntityDataGen(start = 1, stop = 10)
@EntityReferenceSearch(searchType = SearchType.LIST)
public class PiezaAmbientePrueba extends PersistentEntityBase {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public PiezaAmbientePrueba(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of PiezaAmbientePrueba's attributes">
        setLocalizedLabel(ENGLISH, "test environment component");
        setLocalizedLabel(SPANISH, "pieza de ambiente de prueba");
        setLocalizedShortLabel(ENGLISH, "environment component");
        setLocalizedShortLabel(SPANISH, "pieza de ambiente");
        setLocalizedCollectionLabel(ENGLISH, "Test Environment Components");
        setLocalizedCollectionLabel(SPANISH, "Piezas de Ambientes de Prueba");
        setLocalizedCollectionShortLabel(ENGLISH, "Environment Components");
        setLocalizedCollectionShortLabel(SPANISH, "Piezas de Ambientes");
        // </editor-fold>
    }

    @BusinessKey
    public StringProperty codigo;

    @NameProperty
    public StringProperty nombre;

    @DescriptionProperty
    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty descripcion;

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public TipoPiezaPrueba tipo;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        tipo.setInitialValue(tipo.SOFTWARE);
        tipo.setDefaultValue(tipo.SOFTWARE);
        // <editor-fold defaultstate="collapsed" desc="localization of PiezaAmbientePrueba's properties">
        codigo.setLocalizedLabel(ENGLISH, "test environment component code");
        codigo.setLocalizedLabel(SPANISH, "código del pieza de ambiente de prueba");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        nombre.setLocalizedLabel(ENGLISH, "test environment component name");
        nombre.setLocalizedLabel(SPANISH, "nombre del pieza de ambiente de prueba");
        nombre.setLocalizedShortLabel(ENGLISH, "name");
        nombre.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        descripcion.setLocalizedLabel(ENGLISH, "test environment component description");
        descripcion.setLocalizedLabel(SPANISH, "descripción del pieza de ambiente de prueba");
        descripcion.setLocalizedShortLabel(ENGLISH, "description");
        descripcion.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        tipo.setLocalizedLabel(ENGLISH, "type");
        tipo.setLocalizedLabel(SPANISH, "tipo");
        // </editor-fold>
    }

}
