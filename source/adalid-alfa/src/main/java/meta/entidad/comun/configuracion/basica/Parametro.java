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
@EntityClass(catalog = Kleenean.TRUE, independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class Parametro extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public Parametro(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    @StringField(maxLength = 200)
    public StringProperty codigoParametro;

    @NameProperty
    @StringField(maxLength = 200)
    public StringProperty nombreParametro;

    @StringField(maxLength = 200)
    public StringProperty detalleParametro;

    @DescriptionProperty
    @StringField(maxLength = 0)
    public StringProperty descripcionParametro;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    public TipoDatoPar tipoDatoPar;

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public ClaseJava claseJava;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public TipoParametroDom tipoParametroDom;

    public BooleanProperty anulable;

    public BooleanProperty formateado;

    public IntegerProperty longitud;

    public IntegerProperty precision;

    public IntegerProperty escala;

    public IntegerProperty pixeles;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of Parametro's attributes">
        setLocalizedLabel(ENGLISH, "parameter");
        setLocalizedLabel(SPANISH, "parámetro");
        setLocalizedCollectionLabel(ENGLISH, "Parameters");
        setLocalizedCollectionLabel(SPANISH, "Parámetros");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Parameters") + " represents a "
            + "parameter of an application function."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Parámetros") + " representa un "
            + "parámetro de una función de la aplicación."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "parameter of an application function");
        setLocalizedShortDescription(SPANISH, "parámetro de una función de la aplicación");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        setOrderBy(codigoParametro);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of Parametro's properties">
        codigoParametro.setLocalizedLabel(ENGLISH, "parameter code");
        codigoParametro.setLocalizedLabel(SPANISH, "código del parámetro");
        codigoParametro.setLocalizedShortLabel(ENGLISH, "code");
        codigoParametro.setLocalizedShortLabel(SPANISH, "código");
        /**/
        nombreParametro.setLocalizedLabel(ENGLISH, "parameter name");
        nombreParametro.setLocalizedLabel(SPANISH, "nombre del parámetro");
        nombreParametro.setLocalizedShortLabel(ENGLISH, "name");
        nombreParametro.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        detalleParametro.setLocalizedLabel(ENGLISH, "parameter detail");
        detalleParametro.setLocalizedLabel(SPANISH, "detalle parámetro");
        detalleParametro.setLocalizedShortLabel(ENGLISH, "detail");
        detalleParametro.setLocalizedShortLabel(SPANISH, "detalle");
        /**/
        descripcionParametro.setLocalizedLabel(ENGLISH, "parameter description");
        descripcionParametro.setLocalizedLabel(SPANISH, "descripción del parámetro");
        descripcionParametro.setLocalizedShortLabel(ENGLISH, "description");
        descripcionParametro.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        tipoDatoPar.setLocalizedLabel(ENGLISH, "parameter data type");
        tipoDatoPar.setLocalizedLabel(SPANISH, "tipo de dato de parámetro");
        tipoDatoPar.setLocalizedShortLabel(ENGLISH, "data type");
        tipoDatoPar.setLocalizedShortLabel(SPANISH, "tipo de dato");
        /**/
        tipoParametroDom.setLocalizedLabel(ENGLISH, "domain parameter type");
        tipoParametroDom.setLocalizedLabel(SPANISH, "tipo de parámetro de dominio");
        tipoParametroDom.setLocalizedShortLabel(ENGLISH, "parameter type");
        tipoParametroDom.setLocalizedShortLabel(SPANISH, "tipo de parámetro");
        /**/
        claseJava.setLocalizedLabel(ENGLISH, "data class");
        claseJava.setLocalizedLabel(SPANISH, "clase de dato");
        claseJava.setLocalizedShortLabel(ENGLISH, "class");
        claseJava.setLocalizedShortLabel(SPANISH, "clase");
        /**/
        anulable.setLocalizedLabel(ENGLISH, "nullable");
        anulable.setLocalizedLabel(SPANISH, "anulable");
        /**/
        formateado.setLocalizedLabel(ENGLISH, "formatted");
        formateado.setLocalizedLabel(SPANISH, "formateado");
        /**/
        longitud.setLocalizedLabel(ENGLISH, "length");
        longitud.setLocalizedLabel(SPANISH, "longitud");
        /**/
        precision.setLocalizedLabel(ENGLISH, "precision");
        precision.setLocalizedLabel(SPANISH, "precisión");
        /**/
        escala.setLocalizedLabel(ENGLISH, "scale");
        escala.setLocalizedLabel(SPANISH, "escala");
        /**/
        pixeles.setLocalizedLabel(ENGLISH, "pixels");
        pixeles.setLocalizedLabel(SPANISH, "pixeles");
        // </editor-fold>
    }

}
