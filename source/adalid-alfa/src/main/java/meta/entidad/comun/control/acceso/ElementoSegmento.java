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
import meta.entidad.comun.configuracion.basica.ClaseRecurso;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class ElementoSegmento extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public ElementoSegmento(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings(
            "conjuntoSegmento.grupo",
            "conjuntoSegmento.claseRecurso",
            "conjuntoSegmento.claseFabricador"
        );
    }

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE)
    @ColumnField(nullable = Kleenean.FALSE)
    public ConjuntoSegmento conjuntoSegmento;

    @ColumnField(calculable = Kleenean.TRUE)
    @PropertyField(table = Kleenean.TRUE)
    public ClaseRecurso claseRecurso;

    /*
    @ColumnField(calculable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public StringProperty claseJava;

    /**/
    @VariantString
    @PropertyField(table = Kleenean.TRUE, detail = Kleenean.TRUE, create = Kleenean.TRUE, update = Kleenean.TRUE, required = Kleenean.TRUE)
    public StringProperty segmento;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty segmentoEnteroGrande;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idSegmento;

    @PropertyField(hidden = Kleenean.TRUE)
    public StringProperty codigoSegmento;

    @PropertyField(hidden = Kleenean.TRUE)
    public StringProperty nombreSegmento;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of ElementoSegmento's attributes">
        setLocalizedLabel(ENGLISH, "segment set element");
        setLocalizedLabel(SPANISH, "elemento del conjunto de segmentos");
        setLocalizedShortLabel(ENGLISH, "element");
        setLocalizedShortLabel(SPANISH, "elemento");
        setLocalizedCollectionLabel(ENGLISH, "Segment Set Elements");
        setLocalizedCollectionLabel(SPANISH, "Elementos del Conjunto de Segmentos");
        setLocalizedCollectionShortLabel(ENGLISH, "Elements");
        setLocalizedCollectionShortLabel(SPANISH, "Elementos");
        setLocalizedDescription(ENGLISH, "segment set element; typically represents a row of a table in the database");
        setLocalizedDescription(SPANISH, "elemento de conjunto de segmentos; normalmente representa una fila de una tabla en la base de datos");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        claseRecurso.setCalculableValueEntityReference(conjuntoSegmento.claseRecurso);
        /*
        claseJava.setCalculableValueExpression(conjuntoSegmento.claseRecurso.claseJavaClaseRecurso);
        /**/
        segmento.setTypeNameExpression(conjuntoSegmento.claseRecurso.claseJavaClaseRecurso);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of ElementoSegmento's properties">
        segmento.setLocalizedLabel(ENGLISH, "segment");
        segmento.setLocalizedLabel(SPANISH, "segmento");
        /**/
        segmentoEnteroGrande.setLocalizedLabel(ENGLISH, "segment");
        segmentoEnteroGrande.setLocalizedLabel(SPANISH, "segmento");
        /**/
        idSegmento.setLocalizedLabel(ENGLISH, "segment");
        idSegmento.setLocalizedLabel(SPANISH, "segmento");
        /**/
        codigoSegmento.setLocalizedLabel(ENGLISH, "segment");
        codigoSegmento.setLocalizedLabel(SPANISH, "segmento");
        /**/
        nombreSegmento.setLocalizedLabel(ENGLISH, "segment name");
        nombreSegmento.setLocalizedLabel(SPANISH, "nombre del segmento");
        /**/
        conjuntoSegmento.setLocalizedLabel(ENGLISH, "segment set");
        conjuntoSegmento.setLocalizedLabel(SPANISH, "conjunto de segmentos");
        conjuntoSegmento.setLocalizedShortLabel(ENGLISH, "set");
        conjuntoSegmento.setLocalizedShortLabel(SPANISH, "conjunto");
        /**/
        claseRecurso.setLocalizedLabel(ENGLISH, "resource class");
        claseRecurso.setLocalizedLabel(SPANISH, "clase de recurso");
        claseRecurso.setLocalizedShortLabel(ENGLISH, "class");
        claseRecurso.setLocalizedShortLabel(SPANISH, "clase");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        linkForeignSegmentProperty(conjuntoSegmento.grupo);
    }

    protected Key uk_elemento_segmento_0001;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        uk_elemento_segmento_0001.setUnique(true);
        uk_elemento_segmento_0001.newKeyField(conjuntoSegmento, segmentoEnteroGrande);
    }

    protected Segment modificables;

    protected Segment ordinarios;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        modificables = not(conjuntoSegmento.esConjuntoEspecial);
        /**/
        ordinarios = modificables.and(conjuntoSegmento.claseFabricador.isNull());
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of ElementoSegmento's expressions">
        /**/
        modificables.setLocalizedDescription(ENGLISH, "the set is not a basic configuration set");
        modificables.setLocalizedDescription(SPANISH, "el conjunto no es un conjunto de configuración básica del sistema");
        modificables.setLocalizedErrorMessage(ENGLISH, "the set is a basic configuration set; "
            + "it can't be modified or deleted");
        modificables.setLocalizedErrorMessage(SPANISH, "el conjunto es un conjunto de configuración básica del sistema; "
            + "no se permite modificarlo ni eliminarlo");
        /**/
        ordinarios.setLocalizedDescription(ENGLISH, "the set is not a basic configuration set "
            + "and its elements are explicitly specified");
        ordinarios.setLocalizedDescription(SPANISH, "el conjunto no es un conjunto de configuración básica del sistema "
            + "y sus elementos son especificados explícitamente");
        ordinarios.setLocalizedErrorMessage(ENGLISH, "the set is a basic configuration set "
            + "or its elements are determined by a factory class; "
            + "its elements cannot be explicitly specified");
        ordinarios.setLocalizedErrorMessage(SPANISH, "el conjunto es un conjunto de configuración básica del sistema "
            + "o sus elementos son determinados por una clase de fabricador; "
            + "sus elementos no pueden ser especificados explícitamente");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        setMasterDetailFilter(conjuntoSegmento.ordinarios);
        setInsertFilter(conjuntoSegmento.ordinarios);
        setUpdateFilter(ordinarios);
        setDeleteFilter(ordinarios);
        /**/
        segmento.setModifyingFilter(conjuntoSegmento.isNotNull());
        idSegmento.setModifyingFilter(conjuntoSegmento.isNotNull());
        /**/
    }

}
