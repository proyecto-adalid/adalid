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
package meta.entidad.comun.configuracion.basica.ext;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.entidad.comun.configuracion.basica.TipoClaseRecurso;
import meta.entidad.comun.configuracion.basica.TipoRecurso;

/**
 * @author Jorge Campins
 */
@EntityClass(catalog = Kleenean.TRUE, independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 500)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
@EntityReferenceDisplay(style = EntityReferenceStyle.NAME_AND_CHARACTER_KEY)
public class ClaseRecurso extends meta.entidad.comun.configuracion.basica.ClaseRecurso {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public ClaseRecurso(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    public TipoClaseRecurso tipoClaseRecurso;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    public TipoRecurso tipoRecurso;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public Funcion funcionSeleccion;

    @StringField(maxLength = 200)
    public StringProperty paginaSeleccion;

    @StringField(maxLength = 200)
    public StringProperty paginaDetalle;

    @StringField(maxLength = 200)
    public StringProperty paginaFuncion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public ClaseRecurso claseRecursoMaestro;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public ClaseRecurso claseRecursoSegmento;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public ClaseRecurso claseRecursoBase;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        esClaseRecursoIndependiente.setInitialValue(false);
        esClaseRecursoIndependiente.setDefaultValue(false);
        esClaseRecursoSinDetalle.setInitialValue(false);
        esClaseRecursoSinDetalle.setDefaultValue(false);
        esClaseRecursoConArbol.setInitialValue(false);
        esClaseRecursoConArbol.setDefaultValue(false);
        esClaseRecursoSegmento.setInitialValue(false);
        esClaseRecursoSegmento.setDefaultValue(false);
        esClaseRecursoInsertable.setInitialValue(true);
        esClaseRecursoInsertable.setDefaultValue(true);
        esClaseRecursoModificable.setInitialValue(true);
        esClaseRecursoModificable.setDefaultValue(true);
        esClaseRecursoEliminable.setInitialValue(true);
        esClaseRecursoEliminable.setDefaultValue(true);
        esClaseRecursoExtendida.setInitialValue(false);
        esClaseRecursoExtendida.setDefaultValue(false);
        esEnumeradorConNumero.setInitialValue(false);
        esEnumeradorConNumero.setDefaultValue(false);
        /**/
        tipoClaseRecurso.setInitialValue(tipoClaseRecurso.TABLA);
        tipoClaseRecurso.setDefaultValue(tipoClaseRecurso.TABLA);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of ClaseRecurso's properties">
        /**/
        codigoClaseRecurso.setLocalizedLabel(ENGLISH, "resource class code");
        codigoClaseRecurso.setLocalizedLabel(SPANISH, "código de la clase de recurso");
        codigoClaseRecurso.setLocalizedShortLabel(ENGLISH, "code");
        codigoClaseRecurso.setLocalizedShortLabel(SPANISH, "código");
        /**/
        nombreClaseRecurso.setLocalizedLabel(ENGLISH, "resource class name");
        nombreClaseRecurso.setLocalizedLabel(SPANISH, "nombre de la clase de recurso");
        nombreClaseRecurso.setLocalizedShortLabel(ENGLISH, "name");
        nombreClaseRecurso.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        descripcionClaseRecurso.setLocalizedLabel(ENGLISH, "resource class description");
        descripcionClaseRecurso.setLocalizedLabel(SPANISH, "descripción de la clase de recurso");
        descripcionClaseRecurso.setLocalizedShortLabel(ENGLISH, "description");
        descripcionClaseRecurso.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        paqueteClaseRecurso.setLocalizedLabel(ENGLISH, "resource class package");
        paqueteClaseRecurso.setLocalizedLabel(SPANISH, "paquete clase recurso");
        paqueteClaseRecurso.setLocalizedShortLabel(ENGLISH, "package");
        paqueteClaseRecurso.setLocalizedShortLabel(SPANISH, "paquete");
        /**/
        esClaseRecursoIndependiente.setLocalizedLabel(ENGLISH, "independent resource class");
        esClaseRecursoIndependiente.setLocalizedLabel(SPANISH, "clase recurso independiente");
        esClaseRecursoIndependiente.setLocalizedShortLabel(ENGLISH, "independent");
        esClaseRecursoIndependiente.setLocalizedShortLabel(SPANISH, "independiente");
        /**/
        esClaseRecursoSinDetalle.setLocalizedLabel(ENGLISH, "resource class without detail");
        esClaseRecursoSinDetalle.setLocalizedLabel(SPANISH, "clase recurso sin detalle");
        esClaseRecursoSinDetalle.setLocalizedShortLabel(ENGLISH, "without detail");
        esClaseRecursoSinDetalle.setLocalizedShortLabel(SPANISH, "sin detalle");
        /**/
        esClaseRecursoConArbol.setLocalizedLabel(ENGLISH, "resource class with tree");
        esClaseRecursoConArbol.setLocalizedLabel(SPANISH, "clase recurso con arbol");
        esClaseRecursoConArbol.setLocalizedShortLabel(ENGLISH, "with tree");
        esClaseRecursoConArbol.setLocalizedShortLabel(SPANISH, "con arbol");
        /**/
        esClaseRecursoSegmento.setLocalizedLabel(ENGLISH, "segment resource class");
        esClaseRecursoSegmento.setLocalizedLabel(SPANISH, "clase recurso segmento");
        esClaseRecursoSegmento.setLocalizedShortLabel(ENGLISH, "segment");
        esClaseRecursoSegmento.setLocalizedShortLabel(SPANISH, "segmento");
        /**/
        limiteFilasConsulta.setLocalizedLabel(ENGLISH, "query rows limit");
        limiteFilasConsulta.setLocalizedLabel(SPANISH, "limite filas consulta");
        /**/
        limiteFilasInforme.setLocalizedLabel(ENGLISH, "report rows limit");
        limiteFilasInforme.setLocalizedLabel(SPANISH, "limite filas informe");
        /**/
        ordenPresentacion.setLocalizedLabel(ENGLISH, "rendering order");
        ordenPresentacion.setLocalizedLabel(SPANISH, "orden presentación");
        /**/
        esClaseRecursoInsertable.setLocalizedLabel(ENGLISH, "insertable resource class");
        esClaseRecursoInsertable.setLocalizedLabel(SPANISH, "clase recurso insertable");
        esClaseRecursoInsertable.setLocalizedShortLabel(ENGLISH, "insertable");
        esClaseRecursoInsertable.setLocalizedShortLabel(SPANISH, "insertable");
        /**/
        esClaseRecursoModificable.setLocalizedLabel(ENGLISH, "updatable resource class");
        esClaseRecursoModificable.setLocalizedLabel(SPANISH, "clase recurso modificable");
        esClaseRecursoModificable.setLocalizedShortLabel(ENGLISH, "updatable");
        esClaseRecursoModificable.setLocalizedShortLabel(SPANISH, "modificable");
        /**/
        esClaseRecursoEliminable.setLocalizedLabel(ENGLISH, "deletable resource class");
        esClaseRecursoEliminable.setLocalizedLabel(SPANISH, "clase recurso eliminable");
        esClaseRecursoEliminable.setLocalizedShortLabel(ENGLISH, "deletable");
        esClaseRecursoEliminable.setLocalizedShortLabel(SPANISH, "eliminable");
        /**/
        esClaseRecursoExtendida.setLocalizedLabel(ENGLISH, "extended resource class");
        esClaseRecursoExtendida.setLocalizedLabel(SPANISH, "clase recurso extendida");
        esClaseRecursoExtendida.setLocalizedShortLabel(ENGLISH, "extended");
        esClaseRecursoExtendida.setLocalizedShortLabel(SPANISH, "extendida");
        /**/
        etiquetaHipervinculo.setLocalizedLabel(ENGLISH, "hyperlink label");
        etiquetaHipervinculo.setLocalizedLabel(SPANISH, "etiqueta hipervinculo");
        /**/
        esEnumeradorConNumero.setLocalizedLabel(ENGLISH, "enumerator with number");
        esEnumeradorConNumero.setLocalizedLabel(SPANISH, "enumerador con número");
        /**/
        tipoClaseRecurso.setLocalizedLabel(ENGLISH, "resource class type");
        tipoClaseRecurso.setLocalizedLabel(SPANISH, "tipo de clase de recurso");
        tipoClaseRecurso.setLocalizedShortLabel(ENGLISH, "type");
        tipoClaseRecurso.setLocalizedShortLabel(SPANISH, "tipo");
        /**/
        tipoRecurso.setLocalizedLabel(ENGLISH, "resource type");
        tipoRecurso.setLocalizedLabel(SPANISH, "tipo de recurso");
        /**/
        funcionSeleccion.setLocalizedLabel(ENGLISH, "select function");
        funcionSeleccion.setLocalizedLabel(SPANISH, "función de selección");
        /**/
        paginaSeleccion.setLocalizedLabel(ENGLISH, "select page");
        paginaSeleccion.setLocalizedLabel(SPANISH, "página de selección");
        /**/
        paginaDetalle.setLocalizedLabel(ENGLISH, "detail page");
        paginaDetalle.setLocalizedLabel(SPANISH, "página de detalle");
        /**/
        paginaFuncion.setLocalizedLabel(ENGLISH, "function page");
        paginaFuncion.setLocalizedLabel(SPANISH, "página de función");
        /**/
        claseRecursoMaestro.setLocalizedLabel(ENGLISH, "master resource class");
        claseRecursoMaestro.setLocalizedLabel(SPANISH, "clase de recurso maestro");
        claseRecursoMaestro.setLocalizedShortLabel(ENGLISH, "master class");
        claseRecursoMaestro.setLocalizedShortLabel(SPANISH, "clase de maestro");
        /**/
        claseRecursoSegmento.setLocalizedLabel(ENGLISH, "segment resource class");
        claseRecursoSegmento.setLocalizedLabel(SPANISH, "clase de recurso segmento");
        claseRecursoSegmento.setLocalizedShortLabel(ENGLISH, "segment class");
        claseRecursoSegmento.setLocalizedShortLabel(SPANISH, "clase de segmento");
        /**/
        claseRecursoBase.setLocalizedLabel(ENGLISH, "base resource class");
        claseRecursoBase.setLocalizedLabel(SPANISH, "clase de recurso base");
        claseRecursoBase.setLocalizedShortLabel(ENGLISH, "base class");
        claseRecursoBase.setLocalizedShortLabel(SPANISH, "clase base");
        /**/
        // </editor-fold>
    }

    protected Tab tab110, tab120;

    @Override
    protected void settleTabs() {
        super.settleTabs();
        /**/
        tab110.newTabField(descripcionClaseRecurso, etiquetaHipervinculo);
        tab110.newTabField(tipoClaseRecurso, tipoRecurso, claseRecursoBase, claseRecursoMaestro, claseRecursoSegmento, funcionSeleccion);
        tab110.newTabField(paginaSeleccion, paginaDetalle, paginaFuncion);
        /**/
        tab120.newTabField(paqueteClaseRecurso, claseJavaClaseRecurso);
        tab120.newTabField(esClaseRecursoIndependiente, esClaseRecursoSinDetalle, esClaseRecursoConArbol, esClaseRecursoSegmento);
        tab120.newTabField(esClaseRecursoInsertable, esClaseRecursoModificable, esClaseRecursoEliminable, esClaseRecursoExtendida, esEnumeradorConNumero);
        tab120.newTabField(limiteFilasConsulta, limiteFilasInforme, ordenPresentacion);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of Funcion's tabs">
        /**/
        tab110.setLocalizedLabel(ENGLISH, "general");
        tab110.setLocalizedLabel(SPANISH, "general");
        /**/
        tab120.setLocalizedLabel(ENGLISH, "details");
        tab120.setLocalizedLabel(SPANISH, "detalles");
        /**/
        // </editor-fold>
    }

}
