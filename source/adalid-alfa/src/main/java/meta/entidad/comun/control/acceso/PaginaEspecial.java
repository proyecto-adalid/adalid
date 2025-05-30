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
import java.lang.reflect.Field;

/**
 * @author Jorge Campins
 */
@EntityClass(catalog = Kleenean.TRUE, independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE, responsiveMode = TableResponsiveMode.PRIORITY)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
@EntityReferenceSearch(searchType = SearchType.LIST, listStyle = ListStyle.NAME)
public class PaginaEspecial extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public PaginaEspecial(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of PaginaEspecial's attributes">
        setLocalizedLabel(ENGLISH, "special page");
        setLocalizedLabel(SPANISH, "página especial");
        setLocalizedCollectionLabel(ENGLISH, "Special Pages");
        setLocalizedCollectionLabel(SPANISH, "Páginas Especiales");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Special Pages") + " represents a "
            + "special (handmade, not generated) web application page."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Páginas Especiales") + " representa una "
            + "página web especial (hecha a mano, no generada) de la aplicación."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "special (handmade, not generated) web application page");
        setLocalizedShortDescription(SPANISH, "página web especial (hecha a mano, no generada) de la aplicación");
        /**/
        // </editor-fold>
    }

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    @PropertyField(hidden = Kleenean.TRUE)
    public StringProperty codigo;

    @NameProperty
    public StringProperty descripcion;

    @UrlProperty(urlType = UrlType.EXTERNAL, urlDisplayType = UrlDisplayType.HYPERLINK)
    @PropertyField(responsivePriority = 6, create = Kleenean.TRUE, update = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public StringProperty uri;

    @ColumnField(nullable = Kleenean.FALSE)
//  @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty publica;

    @ColumnField(nullable = Kleenean.FALSE)
//  @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty externa;

    @ColumnField(nullable = Kleenean.FALSE)
//  @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty opcionInicio;

    @ColumnField(nullable = Kleenean.FALSE)
//  @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty opcionMenu;

    @InactiveIndicator
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE, overlay = Kleenean.FALSE)
    public BooleanProperty inactiva;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /*
        setOrderBy(codigo);
        /*
        codigo.setDefaultValue(concat("@", id.toZeroPaddedString(19)));
        /**/
        publica.setInitialValue(false);
        publica.setDefaultValue(false);
        /**/
        externa.setInitialValue(false);
        externa.setDefaultValue(false);
        /**/
        opcionInicio.setInitialValue(false);
        opcionInicio.setDefaultValue(false);
        /**/
        opcionMenu.setInitialValue(false);
        opcionMenu.setDefaultValue(false);
        /**/
        inactiva.setInitialValue(false);
        inactiva.setDefaultValue(false);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of PaginaEspecial's properties">
        /**/
        codigo.setLocalizedLabel(ENGLISH, "special page code");
        codigo.setLocalizedLabel(SPANISH, "código de la página especial");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        descripcion.setLocalizedLabel(ENGLISH, "special page description");
        descripcion.setLocalizedLabel(SPANISH, "descripción de la página especial");
        descripcion.setLocalizedShortLabel(ENGLISH, "description");
        descripcion.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        uri.setLocalizedLabel(ENGLISH, "URI");
        uri.setLocalizedLabel(SPANISH, "URI");
        uri.setLocalizedDescription(ENGLISH, "Uniform Resource Identifier of the page");
        uri.setLocalizedDescription(SPANISH, "Identificador de recursos uniforme o URI (del inglés Uniform Resource Identifier) de la página");
        /**/
        publica.setLocalizedLabel(ENGLISH, "public");
        publica.setLocalizedLabel(SPANISH, "pública");
        publica.setLocalizedDescription(ENGLISH, "public page indicator");
        publica.setLocalizedDescription(SPANISH, "indicador de página pública");
        /**/
        externa.setLocalizedLabel(ENGLISH, "external");
        externa.setLocalizedLabel(SPANISH, "externa");
        externa.setLocalizedDescription(ENGLISH, "external page indicator");
        externa.setLocalizedDescription(SPANISH, "indicador de página externa");
        /**/
        opcionInicio.setLocalizedLabel(ENGLISH, "start option");
        opcionInicio.setLocalizedLabel(SPANISH, "opción de inicio");
        opcionInicio.setLocalizedDescription(ENGLISH, "start option indicator");
        opcionInicio.setLocalizedDescription(SPANISH, "indicador de opción de inicio");
        /**/
        opcionMenu.setLocalizedLabel(ENGLISH, "menu option");
        opcionMenu.setLocalizedLabel(SPANISH, "opción de menú");
        opcionMenu.setLocalizedDescription(ENGLISH, "menu option indicator");
        opcionMenu.setLocalizedDescription(SPANISH, "indicador de opción de menú");
        /**/
        inactiva.setLocalizedLabel(ENGLISH, "inactive");
        inactiva.setLocalizedLabel(SPANISH, "inactiva");
        inactiva.setLocalizedDescription(ENGLISH, "inactive page indicator");
        inactiva.setLocalizedDescription(SPANISH, "indicador de página inactiva");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
    }

    protected Desactivar desactivar;

    @ProcessOperationClass
    public class Desactivar extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Desactivar's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "deactivate");
            setLocalizedLabel(SPANISH, "desactivar");
            /**/
            setLocalizedDescription(ENGLISH, "deactivate a special page");
            setLocalizedDescription(SPANISH, "desactivar una página especial");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "the special page was deactivated");
            setLocalizedSuccessMessage(SPANISH, "la página especial fue desactivada");
            /**/
            // </editor-fold>
        }

        @InstanceReference
        protected PaginaEspecial pagina;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            pagina.inactiva.setCurrentValue(true);
            // <editor-fold defaultstate="collapsed" desc="localization of Desactivar's parameters">
            pagina.setLocalizedLabel(ENGLISH, "page");
            pagina.setLocalizedLabel(SPANISH, "página");
            pagina.setLocalizedDescription(ENGLISH, "special page that you want to deactivate");
            pagina.setLocalizedDescription(SPANISH, "página especial que desea desactivar");
            // </editor-fold>
        }

        protected Check check101;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            check101 = pagina.inactiva.isFalse();
            // <editor-fold defaultstate="collapsed" desc="localization of Desactivar's expressions">
            check101.setLocalizedDescription(ENGLISH, "the page is not inactive");
            check101.setLocalizedDescription(SPANISH, "la página no está inactiva");
            check101.setLocalizedErrorMessage(ENGLISH, "the page is already inactive");
            check101.setLocalizedErrorMessage(SPANISH, "la página ya está inactiva");
            // </editor-fold>
        }

    }

    protected Reactivar reactivar;

    @ProcessOperationClass
    public class Reactivar extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Reactivar's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "reactivate");
            setLocalizedLabel(SPANISH, "reactivar");
            /**/
            setLocalizedDescription(ENGLISH, "reactivate a special page");
            setLocalizedDescription(SPANISH, "reactivar una página especial");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "the special page was reactivated");
            setLocalizedSuccessMessage(SPANISH, "la página especial fue reactivada");
            /**/
            // </editor-fold>
        }

        @InstanceReference
        protected PaginaEspecial pagina;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            pagina.inactiva.setCurrentValue(false);
            // <editor-fold defaultstate="collapsed" desc="localization of Reactivar's parameters">
            pagina.setLocalizedLabel(ENGLISH, "page");
            pagina.setLocalizedLabel(SPANISH, "página");
            pagina.setLocalizedDescription(ENGLISH, "special page that you want to reactivate");
            pagina.setLocalizedDescription(SPANISH, "página especial que desea reactivar");
            // </editor-fold>
        }

        protected Check check101;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            check101 = pagina.inactiva.isTrue();
            // <editor-fold defaultstate="collapsed" desc="localization of Reactivar's expressions">
            check101.setLocalizedDescription(ENGLISH, "the page is inactive");
            check101.setLocalizedDescription(SPANISH, "la página está inactiva");
            check101.setLocalizedErrorMessage(ENGLISH, "the page is already active");
            check101.setLocalizedErrorMessage(SPANISH, "la página ya está activa");
            // </editor-fold>
        }

    }

}
