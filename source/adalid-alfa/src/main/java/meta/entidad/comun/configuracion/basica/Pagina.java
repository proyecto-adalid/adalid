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
package meta.entidad.comun.configuracion.basica;

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
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, onload = SelectOnloadOption.EXECUTE, rowsLimit = 500)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE, responsiveMode = TableResponsiveMode.PRIORITY, quickFilter = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class Pagina extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public Pagina(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    @StringField(maxLength = 100)
    @PropertyField(update = Kleenean.FALSE/*, overlay = Kleenean.FALSE*/)
    public StringProperty codigoPagina;

    @NameProperty
    @PropertyField(update = Kleenean.FALSE)
    public StringProperty nombrePagina;

    @DescriptionProperty
    @PropertyField(update = Kleenean.FALSE)
    @StringField(maxLength = 0)
    public StringProperty descripcionPagina;

    @UrlProperty(urlType = UrlType.INTERNAL)
    @PropertyField(update = Kleenean.FALSE)
    public StringProperty urlPagina;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(responsivePriority = 6, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty esPublica;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(responsivePriority = 6, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty esEspecial;

    @ColumnField(nullable = Kleenean.FALSE)
//  @PropertyField(update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty esExterna;

    @ColumnField(nullable = Kleenean.FALSE)
//  @PropertyField(update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty opcionInicio;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(responsivePriority = 6, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty opcionMenu;

    @EmbeddedDocument(sourceURLs = EMBED_YOUTUBE, style = EmbeddedDocumentStyle.POPUP, frameBorder = Kleenean.FALSE, fullScreen = Kleenean.TRUE)
    @PropertyField(update = Kleenean.TRUE)
    public StringProperty tutorial;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public Aplicacion aplicacion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(responsivePriority = 5, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public TipoPagina tipoPagina;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(update = Kleenean.FALSE)
    public Dominio dominio;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(update = Kleenean.FALSE)
    public Dominio dominioMaestro;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(update = Kleenean.FALSE)
    public Parametro parametro;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of Pagina's attributes">
        setLocalizedLabel(ENGLISH, "page");
        setLocalizedLabel(SPANISH, "página");
        setLocalizedCollectionLabel(ENGLISH, "Pages");
        setLocalizedCollectionLabel(SPANISH, "Páginas");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Pages") + " represents a "
            + "web application page."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Páginas") + " representa una "
            + "página web de la aplicación."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "web application page");
        setLocalizedShortDescription(SPANISH, "página web de la aplicación");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        setOrderBy(codigoPagina);
        /**/
        esPublica.setInitialValue(false);
        esPublica.setDefaultValue(false);
        /**/
        esEspecial.setInitialValue(false);
        esEspecial.setDefaultValue(false);
        /**/
        esExterna.setInitialValue(false);
        esExterna.setDefaultValue(false);
        /**/
        opcionInicio.setInitialValue(false);
        opcionInicio.setDefaultValue(false);
        /**/
        opcionMenu.setInitialValue(false);
        opcionMenu.setDefaultValue(false);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of Pagina's properties">
        /**/
        codigoPagina.setLocalizedLabel(ENGLISH, "page code");
        codigoPagina.setLocalizedLabel(SPANISH, "código de la página");
        codigoPagina.setLocalizedShortLabel(ENGLISH, "code");
        codigoPagina.setLocalizedShortLabel(SPANISH, "código");
        /**/
        nombrePagina.setLocalizedLabel(ENGLISH, "page name");
        nombrePagina.setLocalizedLabel(SPANISH, "nombre de la página");
        nombrePagina.setLocalizedShortLabel(ENGLISH, "name");
        nombrePagina.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        descripcionPagina.setLocalizedLabel(ENGLISH, "page description");
        descripcionPagina.setLocalizedLabel(SPANISH, "descripción de la página");
        descripcionPagina.setLocalizedShortLabel(ENGLISH, "description");
        descripcionPagina.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        urlPagina.setLocalizedLabel(ENGLISH, "page URL");
        urlPagina.setLocalizedLabel(SPANISH, "URL de la página");
        urlPagina.setLocalizedShortLabel(ENGLISH, "URL");
        urlPagina.setLocalizedShortLabel(SPANISH, "URL");
        /**/
        esPublica.setLocalizedLabel(ENGLISH, "public");
        esPublica.setLocalizedLabel(SPANISH, "pública");
        /**/
        esEspecial.setLocalizedLabel(ENGLISH, "special");
        esEspecial.setLocalizedLabel(SPANISH, "especial");
        /**/
        esExterna.setLocalizedLabel(ENGLISH, "external");
        esExterna.setLocalizedLabel(SPANISH, "externa");
        /**/
        opcionInicio.setLocalizedLabel(ENGLISH, "start option");
        opcionInicio.setLocalizedLabel(SPANISH, "opción de inicio");
        /**/
        opcionMenu.setLocalizedLabel(ENGLISH, "menu option");
        opcionMenu.setLocalizedLabel(SPANISH, "opción de menú");
        /**/
        tutorial.setLocalizedLabel(ENGLISH, "page tutorial");
        tutorial.setLocalizedLabel(SPANISH, "tutorial de la página");
        tutorial.setLocalizedShortLabel(ENGLISH, "tutorial");
        tutorial.setLocalizedShortLabel(SPANISH, "tutorial");
        /**/
        aplicacion.setLocalizedLabel(ENGLISH, "application");
        aplicacion.setLocalizedLabel(SPANISH, "aplicación");
        /**/
        tipoPagina.setLocalizedLabel(ENGLISH, "page type");
        tipoPagina.setLocalizedLabel(SPANISH, "tipo de página");
        tipoPagina.setLocalizedShortLabel(ENGLISH, "type");
        tipoPagina.setLocalizedShortLabel(SPANISH, "tipo");
        /**/
        dominio.setLocalizedLabel(ENGLISH, "domain");
        dominio.setLocalizedLabel(SPANISH, "dominio");
        /**/
        dominioMaestro.setLocalizedLabel(ENGLISH, "master domain");
        dominioMaestro.setLocalizedLabel(SPANISH, "dominio maestro");
        dominioMaestro.setLocalizedShortLabel(ENGLISH, "master");
        dominioMaestro.setLocalizedShortLabel(SPANISH, "maestro");
        /**/
        parametro.setLocalizedLabel(ENGLISH, "parameter");
        parametro.setLocalizedLabel(SPANISH, "parámetro");
        /**/
        // </editor-fold>
    }

}
