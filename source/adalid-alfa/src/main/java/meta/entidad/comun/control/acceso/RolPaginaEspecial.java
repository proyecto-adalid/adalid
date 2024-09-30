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
package meta.entidad.comun.control.acceso;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import adalid.core.properties.ext.*;
import java.lang.reflect.Field;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, responsiveMode = TableResponsiveMode.PRIORITY)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class RolPaginaEspecial extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public RolPaginaEspecial(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings(
            "rol.grupo"
        );
    }

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @NameProperty
    @ColumnField(calculable = Kleenean.TRUE)
    public CloakedStringProperty nombre;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE, viewSequence = 30)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public Rol rol;

//  20171213: remove foreign-key referring to PaginaEspecial
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE, quickAdding = QuickAddingFilter.MISSING)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public PaginaEspecial paginaEspecial;

    @ColumnField(calculable = Kleenean.TRUE)
    @BooleanField(displayType = BooleanDisplayType.TOGGLE)
    @PropertyField(responsivePriority = 6, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty inactiva;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of RolPagina's attributes">
        /**/
        setLocalizedLabel(ENGLISH, "role/special page association");
        setLocalizedLabel(SPANISH, "asociación Rol/Página Especial");
        setLocalizedCollectionLabel(ENGLISH, "Role/Special Page Associations");
        setLocalizedCollectionLabel(SPANISH, "Asociaciones Rol/Página Especial");
        /**/
        setLocalizedCollectionLabel(ENGLISH, rol, "Special Pages by Role");
        setLocalizedCollectionLabel(SPANISH, rol, "Páginas Especiales por Rol");
        setLocalizedCollectionShortLabel(ENGLISH, rol, "Special Pages");
        setLocalizedCollectionShortLabel(SPANISH, rol, "Páginas Especiales");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Role/Special Page Associations") + " represents a "
            + "special web page associated with an application role."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Asociaciones Rol/Página Especial") + " representa una "
            + "página web especial asociada con un rol de la aplicación."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "special web page associated with an application role");
        setLocalizedShortDescription(SPANISH, "página web especial asociada con un rol de la aplicación");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        nombre.setCalculableValueExpression(concatenate(paginaEspecial.codigo, SLASH, rol.codigoRol));
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of RolPagina's properties">
        /**/
        rol.setLocalizedLabel(ENGLISH, "role");
        rol.setLocalizedLabel(SPANISH, "rol");
        /**/
        paginaEspecial.setLocalizedLabel(ENGLISH, "special page");
        paginaEspecial.setLocalizedLabel(SPANISH, "página especial");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        /**/
        linkForeignSegmentProperty(rol.grupo);
        /**/
    }

    protected Key uk_rol_pagina_0001;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        /**/
        uk_rol_pagina_0001.setUnique(true);
        uk_rol_pagina_0001.newKeyField(rol, paginaEspecial);
        /**/
    }

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        inactiva.setCalculableValueExpression(paginaEspecial.inactiva);
        /**/
    }

}
