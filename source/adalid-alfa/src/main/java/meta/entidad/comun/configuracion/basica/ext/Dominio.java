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

import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import java.lang.reflect.Field;
import meta.entidad.comun.configuracion.basica.TipoDominio;

/**
 * @author Jorge Campins
 */
@EntityClass(catalog = Kleenean.TRUE, independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class Dominio extends meta.entidad.comun.configuracion.basica.Dominio {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public Dominio(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings("claseRecurso.claseRecursoSegmento");
    }

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(search = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public TipoDominio tipoDominio;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(search = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public ClaseRecurso claseRecurso;

    @ColumnField(calculable = Kleenean.TRUE)
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(search = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public ClaseRecurso claseRecursoSegmento;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public Funcion funcionSeleccion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public Dominio dominioSegmento;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of Dominio's properties">
        /**/
        codigoDominio.setLocalizedLabel(ENGLISH, "domain code");
        codigoDominio.setLocalizedLabel(SPANISH, "código del dominio");
        codigoDominio.setLocalizedShortLabel(ENGLISH, "code");
        codigoDominio.setLocalizedShortLabel(SPANISH, "código");
        /**/
        nombreDominio.setLocalizedLabel(ENGLISH, "domain name");
        nombreDominio.setLocalizedLabel(SPANISH, "nombre del dominio");
        nombreDominio.setLocalizedShortLabel(ENGLISH, "name");
        nombreDominio.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        descripcionDominio.setLocalizedLabel(ENGLISH, "domain description");
        descripcionDominio.setLocalizedLabel(SPANISH, "descripción del dominio");
        descripcionDominio.setLocalizedShortLabel(ENGLISH, "description");
        descripcionDominio.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        nombreTabla.setLocalizedLabel(ENGLISH, "table name");
        nombreTabla.setLocalizedLabel(SPANISH, "nombre tabla");
        /**/
        tipoDominio.setLocalizedLabel(ENGLISH, "domain type");
        tipoDominio.setLocalizedLabel(SPANISH, "tipo de dominio");
        tipoDominio.setLocalizedShortLabel(ENGLISH, "type");
        tipoDominio.setLocalizedShortLabel(SPANISH, "tipo");
        /**/
        claseRecurso.setLocalizedLabel(ENGLISH, "resource class");
        claseRecurso.setLocalizedLabel(SPANISH, "clase de recurso");
        claseRecurso.setLocalizedShortLabel(ENGLISH, "class");
        claseRecurso.setLocalizedShortLabel(SPANISH, "clase");
        /**/
        claseRecursoSegmento.setLocalizedLabel(ENGLISH, "segment resource class");
        claseRecursoSegmento.setLocalizedLabel(SPANISH, "clase de recurso segmento");
        claseRecursoSegmento.setLocalizedShortLabel(ENGLISH, "segment class");
        claseRecursoSegmento.setLocalizedShortLabel(SPANISH, "clase de segmento");
        /**/
        funcionSeleccion.setLocalizedLabel(ENGLISH, "select function");
        funcionSeleccion.setLocalizedLabel(SPANISH, "función de selección");
        /**/
        dominioSegmento.setLocalizedLabel(ENGLISH, "domain segment");
        dominioSegmento.setLocalizedLabel(SPANISH, "dominio segmento");
        dominioSegmento.setLocalizedShortLabel(ENGLISH, "segment");
        dominioSegmento.setLocalizedShortLabel(SPANISH, "segmento");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        claseRecursoSegmento.linkCalculableValueEntityReference(claseRecurso.claseRecursoSegmento);
    }

}
