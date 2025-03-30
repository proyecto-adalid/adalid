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
public class PaquetePrueba extends PersistentEntityBase {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public PaquetePrueba(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of PaquetePrueba's attributes">
        setLocalizedLabel(ENGLISH, "test package");
        setLocalizedLabel(SPANISH, "paquete de prueba");
        setLocalizedShortLabel(ENGLISH, "package");
        setLocalizedShortLabel(SPANISH, "paquete");
        setLocalizedCollectionLabel(ENGLISH, "Test Packages");
        setLocalizedCollectionLabel(SPANISH, "Paquetes de Prueba");
        setLocalizedCollectionShortLabel(ENGLISH, "Packages");
        setLocalizedCollectionShortLabel(SPANISH, "Paquetes");
        // </editor-fold>
    }

    @BusinessKey
    @StringField(maxLength = 100)
    public StringProperty codigo;

    @NameProperty
    public StringProperty nombre;

    @DescriptionProperty
    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty descripcion;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of PaquetePrueba's properties">
        codigo.setLocalizedLabel(ENGLISH, "test package code");
        codigo.setLocalizedLabel(SPANISH, "código del paquete de prueba");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        nombre.setLocalizedLabel(ENGLISH, "test package name");
        nombre.setLocalizedLabel(SPANISH, "nombre del paquete de prueba");
        nombre.setLocalizedShortLabel(ENGLISH, "name");
        nombre.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        descripcion.setLocalizedLabel(ENGLISH, "test package description");
        descripcion.setLocalizedLabel(SPANISH, "descripción del paquete de prueba");
        descripcion.setLocalizedShortLabel(ENGLISH, "description");
        descripcion.setLocalizedShortLabel(SPANISH, "descripción");
        // </editor-fold>
    }

}
