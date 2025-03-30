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
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 500)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE, responsiveMode = TableResponsiveMode.PRIORITY)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class ClaseRecurso extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public ClaseRecurso(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    @StringField(maxLength = 100)
    @PropertyField(overlay = Kleenean.TRUE)
    public StringProperty codigoClaseRecurso;

    @NameProperty
    public StringProperty nombreClaseRecurso;

    @DescriptionProperty
    @StringField(maxLength = 0)
    public StringProperty descripcionClaseRecurso;

    @StringField(maxLength = 200)
    public StringProperty paqueteClaseRecurso;

    @StringField(maxLength = 200)
    public StringProperty claseJavaClaseRecurso;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esClaseRecursoIndependiente;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esClaseRecursoSinDetalle;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esClaseRecursoConArbol;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esClaseRecursoSegmento;

    public IntegerProperty limiteFilasConsulta;

    public IntegerProperty limiteFilasInforme;

    public IntegerProperty ordenPresentacion;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(overlay = Kleenean.TRUE)
    public BooleanProperty esClaseRecursoInsertable;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(overlay = Kleenean.TRUE)
    public BooleanProperty esClaseRecursoModificable;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(overlay = Kleenean.TRUE)
    public BooleanProperty esClaseRecursoEliminable;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esClaseRecursoExtendida;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esEntidadDelCatalogo;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esEnumeradorConNumero;

    @StringField(maxLength = 20)
    public StringProperty etiquetaHipervinculo;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of ClaseRecurso's attributes">
        setLocalizedLabel(ENGLISH, "resource class");
        setLocalizedLabel(SPANISH, "clase de recurso");
        setLocalizedShortLabel(ENGLISH, "class");
        setLocalizedShortLabel(SPANISH, "clase");
        setLocalizedCollectionLabel(ENGLISH, "Resource Classes");
        setLocalizedCollectionLabel(SPANISH, "Clases de Recurso");
        setLocalizedCollectionShortLabel(ENGLISH, "Classes");
        setLocalizedCollectionShortLabel(SPANISH, "Clases");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Resource Classes") + " represents a "
            + "persistence domain object that typically represents a table in the database."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Clases de Recurso") + " representa un "
            + "objeto de dominio de persistencia que normalmente representa una tabla en la base de datos."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "persistence domain object that typically represents a table in the database");
        setLocalizedShortDescription(SPANISH, "objeto de dominio de persistencia que normalmente representa una tabla en la base de datos");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        setOrderBy(codigoClaseRecurso);
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
        esEntidadDelCatalogo.setInitialValue(false);
        esEntidadDelCatalogo.setDefaultValue(false);
        esEnumeradorConNumero.setInitialValue(false);
        esEnumeradorConNumero.setDefaultValue(false);
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
        esEntidadDelCatalogo.setLocalizedLabel(ENGLISH, "catalog entity");
        esEntidadDelCatalogo.setLocalizedLabel(SPANISH, "entidad del catálogo");
        /**/
        esEnumeradorConNumero.setLocalizedLabel(ENGLISH, "enumerator with number");
        esEnumeradorConNumero.setLocalizedLabel(SPANISH, "enumerador con número");
        /**/
        // </editor-fold>
    }

}
