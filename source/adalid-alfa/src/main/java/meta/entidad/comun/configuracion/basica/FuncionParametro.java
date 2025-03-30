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
@EntityClass(catalog = Kleenean.TRUE, independent = Kleenean.FALSE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 500)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE, responsiveMode = TableResponsiveMode.PRIORITY)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class FuncionParametro extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public FuncionParametro(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    @StringField(maxLength = 200)
    @PropertyField(overlay = Kleenean.TRUE)
    public StringProperty codigoFuncionParametro;

    @NameProperty
    @StringField(maxLength = 200)
    public StringProperty nombreFuncionParametro;

    @StringField(maxLength = 200)
    public StringProperty aliasFuncionParametro;

    @StringField(maxLength = Project.STRING_FIELD_MAX_LENGTH)
    public StringProperty columnaFuncionParametro;

    @StringField(maxLength = 200)
    public StringProperty detalleFuncionParametro;

    @DescriptionProperty
    @StringField(maxLength = 0)
    public StringProperty descripcionFuncionParametro;

    @StringField(maxLength = 200)
    public StringProperty claseJavaFuncionParametro;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of FuncionParametro's attributes">
        setLocalizedLabel(ENGLISH, "function parameter");
        setLocalizedLabel(SPANISH, "parámetro de función");
        setLocalizedShortLabel(ENGLISH, "parameter");
        setLocalizedShortLabel(SPANISH, "parámetro");
        setLocalizedCollectionLabel(ENGLISH, "Function Parameters");
        setLocalizedCollectionLabel(SPANISH, "Parámetros de Función");
        setLocalizedCollectionShortLabel(ENGLISH, "Parameters");
        setLocalizedCollectionShortLabel(SPANISH, "Parámetros");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Function Parameters") + " represents a "
            + "parameter of an application function."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Parámetros de Función") + " representa un "
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
        /*
        setOrderBy(codigoFuncionParametro);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of FuncionParametro's properties">
        codigoFuncionParametro.setLocalizedLabel(ENGLISH, "function parameter code");
        codigoFuncionParametro.setLocalizedLabel(SPANISH, "código del parámetro de función");
        codigoFuncionParametro.setLocalizedShortLabel(ENGLISH, "code");
        codigoFuncionParametro.setLocalizedShortLabel(SPANISH, "código");
        /**/
        nombreFuncionParametro.setLocalizedLabel(ENGLISH, "function parameter name");
        nombreFuncionParametro.setLocalizedLabel(SPANISH, "nombre del parámetro de función");
        nombreFuncionParametro.setLocalizedShortLabel(ENGLISH, "name");
        nombreFuncionParametro.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        aliasFuncionParametro.setLocalizedLabel(ENGLISH, "function parameter alias");
        aliasFuncionParametro.setLocalizedLabel(SPANISH, "alias función parámetro");
        aliasFuncionParametro.setLocalizedShortLabel(ENGLISH, "alias");
        aliasFuncionParametro.setLocalizedShortLabel(SPANISH, "alias");
        /**/
        columnaFuncionParametro.setLocalizedLabel(ENGLISH, "function parameter column");
        columnaFuncionParametro.setLocalizedLabel(SPANISH, "columna función parámetro");
        columnaFuncionParametro.setLocalizedShortLabel(ENGLISH, "column");
        columnaFuncionParametro.setLocalizedShortLabel(SPANISH, "columna");
        /**/
        detalleFuncionParametro.setLocalizedLabel(ENGLISH, "function parameter detail");
        detalleFuncionParametro.setLocalizedLabel(SPANISH, "detalle función parámetro");
        detalleFuncionParametro.setLocalizedShortLabel(ENGLISH, "detail");
        detalleFuncionParametro.setLocalizedShortLabel(SPANISH, "detalle");
        /**/
        descripcionFuncionParametro.setLocalizedLabel(ENGLISH, "function parameter description");
        descripcionFuncionParametro.setLocalizedLabel(SPANISH, "descripción del parámetro de función");
        descripcionFuncionParametro.setLocalizedShortLabel(ENGLISH, "description");
        descripcionFuncionParametro.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        claseJavaFuncionParametro.setLocalizedLabel(ENGLISH, "function parameter java class");
        claseJavaFuncionParametro.setLocalizedLabel(SPANISH, "clase java del parámetro de función");
        claseJavaFuncionParametro.setLocalizedShortLabel(ENGLISH, "java class");
        claseJavaFuncionParametro.setLocalizedShortLabel(SPANISH, "clase java");
        /**/
        // </editor-fold>
    }

}
