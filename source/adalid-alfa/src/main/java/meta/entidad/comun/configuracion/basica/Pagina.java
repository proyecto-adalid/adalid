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
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE)
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
    @PropertyField(update = Kleenean.FALSE)
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
    @PropertyField(update = Kleenean.FALSE)
    public BooleanProperty esPublica;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(update = Kleenean.FALSE)
    public BooleanProperty esEspecial;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(update = Kleenean.FALSE)
    public BooleanProperty opcionMenu;

    @EmbeddedDocument(sourceURLs = EMBED_YOUTUBE, style = EmbeddedDocumentStyle.POPUP, frameBorder = Kleenean.FALSE, fullScreen = Kleenean.TRUE)
    @PropertyField(table = Kleenean.TRUE)
    public StringProperty tutorial;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public Aplicacion aplicacion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(update = Kleenean.FALSE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public TipoPagina tipoPagina;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(update = Kleenean.FALSE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public Dominio dominio;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(update = Kleenean.FALSE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public Dominio dominioMaestro;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(update = Kleenean.FALSE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public Parametro parametro;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setOrderBy(codigoPagina);
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of Pagina's attributes">
        setLocalizedLabel(ENGLISH, "page");
        setLocalizedLabel(SPANISH, "página");
        setLocalizedCollectionLabel(ENGLISH, "Pages");
        setLocalizedCollectionLabel(SPANISH, "Páginas");
        setLocalizedDescription(ENGLISH, "web application page");
        setLocalizedDescription(SPANISH, "página de la aplicación web");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        esPublica.setInitialValue(false);
        esPublica.setDefaultValue(false);
        /**/
        esEspecial.setInitialValue(false);
        esEspecial.setDefaultValue(false);
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
        opcionMenu.setLocalizedLabel(ENGLISH, "menu option");
        opcionMenu.setLocalizedLabel(SPANISH, "opción de menú");
        /**/
        tutorial.setLocalizedLabel(ENGLISH, "tutorial");
        tutorial.setLocalizedLabel(SPANISH, "tutorial");
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
