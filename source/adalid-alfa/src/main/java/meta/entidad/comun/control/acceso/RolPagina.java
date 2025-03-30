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
import meta.entidad.comun.configuracion.basica.Dominio;
import meta.entidad.comun.configuracion.basica.Pagina;
import meta.entidad.comun.configuracion.basica.TipoPagina;

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
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class RolPagina extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public RolPagina(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings(
            "rol.grupo",
            "pagina.tipoPagina",
            "pagina.dominio",
            "pagina.dominioMaestro",
            "pagina.parametro"
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
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE, viewSequence = 40)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public Rol rol;

//  20171213: remove foreign-key referring to Pagina
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE, quickAdding = QuickAddingFilter.MISSING)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public Pagina pagina;

    @ColumnField(calculable = Kleenean.TRUE)
    @ManyToOne(view = MasterDetailView.NONE)
    @PropertyField(responsivePriority = 6, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public TipoPagina tipoPagina;

    @ColumnField(calculable = Kleenean.TRUE)
    @ManyToOne(view = MasterDetailView.NONE)
    @PropertyField(responsivePriority = 6, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public Dominio dominio;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of RolPagina's attributes">
        /**/
        setLocalizedLabel(ENGLISH, "role/page association");
        setLocalizedLabel(SPANISH, "asociación Rol/Página");
        setLocalizedCollectionLabel(ENGLISH, "Role/Page Associations");
        setLocalizedCollectionLabel(SPANISH, "Asociaciones Rol/Página");
        /**/
        setLocalizedCollectionLabel(ENGLISH, rol, "Favorites by Role");
        setLocalizedCollectionLabel(SPANISH, rol, "Favoritos por Rol");
        setLocalizedCollectionShortLabel(ENGLISH, rol, "Favorites");
        setLocalizedCollectionShortLabel(SPANISH, rol, "Favoritos");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Role/Page Associations") + " represents a "
            + "web page associated with an application role."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Asociaciones Rol/Página") + " representa una "
            + "página web asociada con un rol de la aplicación."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "web page associated with an application role");
        setLocalizedShortDescription(SPANISH, "página web asociada con un rol de la aplicación");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        nombre.setCalculableValueExpression(concatenate(pagina.codigoPagina, SLASH, rol.codigoRol));
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of RolPagina's properties">
        /**/
        rol.setLocalizedLabel(ENGLISH, "role");
        rol.setLocalizedLabel(SPANISH, "rol");
        /**/
        pagina.setLocalizedLabel(ENGLISH, "page");
        pagina.setLocalizedLabel(SPANISH, "página");
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
        uk_rol_pagina_0001.newKeyField(rol, pagina);
        /**/
    }

    protected Check check101;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        check101 = and(
            pagina.esExterna.isFalse(),
            pagina.tipoPagina.isNullOrNotIn(pagina.tipoPagina.DETALLE, pagina.tipoPagina.CONSULTA_DETALLE),
            or(pagina.esEspecial.isTrue(), and(pagina.dominio.isNotNull(), pagina.dominioMaestro.isNull(), pagina.parametro.isNull()))
        );
        /**/
        tipoPagina.setCalculableValueEntityReference(pagina.tipoPagina);
        dominio.setCalculableValueEntityReference(pagina.dominio);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of RolPagina's expressions">
        /**/
        check101.setLocalizedLabel(ENGLISH, "check page");
        check101.setLocalizedLabel(SPANISH, "chequear página");
        check101.setLocalizedDescription(ENGLISH, "the page should not be a special or detail or master/detail page");
        check101.setLocalizedDescription(SPANISH, "la página no debe ser una página especial, ni de detalle, ni de maestro/detalle");
        check101.setLocalizedErrorMessage(ENGLISH, "the page is a special or detail or master/detail page");
        check101.setLocalizedErrorMessage(SPANISH, "la página es una página especial, o de detalle, o de maestro/detalle");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        pagina.setSearchQueryFilter(check101);
        /**/
    }

}
