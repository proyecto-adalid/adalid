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
import adalid.core.parameters.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;

/**
 * @author Jorge Campins
 */
@EntityClass(catalog = Kleenean.TRUE, independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE, responsiveMode = TableResponsiveMode.PRIORITY, quickFilter = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
@EntityReferenceSearch(searchType = SearchType.LIST, listStyle = ListStyle.CHARACTER_KEY_AND_NAME)
public class Aplicacion extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public Aplicacion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    @StringField(maxLength = 100)
    @PropertyField(overlay = Kleenean.FALSE)
    public StringProperty codigoAplicacion;

    @NameProperty
    public StringProperty nombreAplicacion;

    @DescriptionProperty
    @StringField(maxLength = 0)
    public StringProperty descripcionAplicacion;

    @ColumnField(nullable = Kleenean.FALSE)
    @StringField(maxLength = 100)
    public StringProperty servidorAplicacion;

    @ColumnField(nullable = Kleenean.FALSE)
    @StringField(maxLength = 10)
    public StringProperty puertoAplicacion;

    @UrlProperty
    public StringProperty urlAplicacion;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esPublica;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esEspecial;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of Aplicacion's attributes">
        setLocalizedLabel(ENGLISH, "application");
        setLocalizedLabel(SPANISH, "aplicación");
        setLocalizedCollectionLabel(ENGLISH, "Applications");
        setLocalizedCollectionLabel(SPANISH, "Aplicaciones");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Applications") + " represents an "
            + "application including the user interface and client-side logic."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Aplicaciones") + " representa una "
            + "aplicación que incluye la interfaz de usuario y la lógica del lado del cliente."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "application including the user interface and client-side logic");
        setLocalizedShortDescription(SPANISH, "aplicación que incluye la interfaz de usuario y la lógica del lado del cliente");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        setOrderBy(codigoAplicacion);
        /**/
        servidorAplicacion.setInitialValue("localhost");
        servidorAplicacion.setDefaultValue("localhost");
        puertoAplicacion.setInitialValue("8080");
        puertoAplicacion.setDefaultValue("8080");
        esPublica.setInitialValue(false);
        esPublica.setDefaultValue(false);
        esEspecial.setInitialValue(false);
        esEspecial.setDefaultValue(false);
        // <editor-fold defaultstate="collapsed" desc="localization of Aplicacion's properties">
        /**/
        codigoAplicacion.setLocalizedLabel(ENGLISH, "application code");
        codigoAplicacion.setLocalizedLabel(SPANISH, "código de la aplicación");
        codigoAplicacion.setLocalizedShortLabel(ENGLISH, "code");
        codigoAplicacion.setLocalizedShortLabel(SPANISH, "código");
        /**/
        nombreAplicacion.setLocalizedLabel(ENGLISH, "application name");
        nombreAplicacion.setLocalizedLabel(SPANISH, "nombre de la aplicación");
        nombreAplicacion.setLocalizedShortLabel(ENGLISH, "name");
        nombreAplicacion.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        descripcionAplicacion.setLocalizedLabel(ENGLISH, "application description");
        descripcionAplicacion.setLocalizedLabel(SPANISH, "descripción de la aplicación");
        descripcionAplicacion.setLocalizedShortLabel(ENGLISH, "description");
        descripcionAplicacion.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        servidorAplicacion.setLocalizedLabel(ENGLISH, "application server");
        servidorAplicacion.setLocalizedLabel(SPANISH, "servidor aplicación");
        servidorAplicacion.setLocalizedShortLabel(ENGLISH, "server");
        servidorAplicacion.setLocalizedShortLabel(SPANISH, "servidor");
        /**/
        puertoAplicacion.setLocalizedLabel(ENGLISH, "application port");
        puertoAplicacion.setLocalizedLabel(SPANISH, "puerto aplicación");
        puertoAplicacion.setLocalizedShortLabel(ENGLISH, "port");
        puertoAplicacion.setLocalizedShortLabel(SPANISH, "puerto");
        /**/
        urlAplicacion.setLocalizedLabel(ENGLISH, "application URL");
        urlAplicacion.setLocalizedLabel(SPANISH, "URL de la aplicación");
        urlAplicacion.setLocalizedShortLabel(ENGLISH, "URL");
        urlAplicacion.setLocalizedShortLabel(SPANISH, "URL");
        /**/
        esPublica.setLocalizedLabel(ENGLISH, "public");
        esPublica.setLocalizedLabel(SPANISH, "pública");
        /**/
        esEspecial.setLocalizedLabel(ENGLISH, "special");
        esEspecial.setLocalizedLabel(SPANISH, "especial");
        /**/
        // </editor-fold>
    }

    protected EnviarMensaje enviarMensaje;

    @OperationClass(access = OperationAccess.PROTECTED, asynchronous = Kleenean.FALSE)
    @ProcessOperationClass(builtIn = true)
    public class EnviarMensaje extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of EnviarMensaje's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "send message");
            setLocalizedLabel(SPANISH, "enviar mensaje");
            /**/
            // </editor-fold>
        }

        @ParameterField(required = Kleenean.TRUE)
        @StringField(maxLength = 160)
        protected StringParameter mensaje;

        @ParameterField(required = Kleenean.TRUE)
        @StringField(maxLength = Constants.DEFAULT_STRING_FIELD_MAX_LENGTH)
        protected StringParameter numeros;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of EnviarMensaje's parameters">
            /**/
            mensaje.setLocalizedLabel(ENGLISH, "message");
            mensaje.setLocalizedLabel(SPANISH, "mensaje");
            /**/
            numeros.setLocalizedLabel(ENGLISH, "numbers");
            numeros.setLocalizedLabel(SPANISH, "números");
            /**/
            // </editor-fold>
        }

    }

    protected ProcesoDiario diario;

    @OperationClass(access = OperationAccess.PRIVATE, asynchronous = Kleenean.TRUE, shell = Kleenean.TRUE)
    @ProcessOperationClass(bpl = Kleenean.FALSE, sql = Kleenean.TRUE)
    @ProcedureOperationClass(dataType = ProcedureDataType.NULL, type = ProcedureType.VOID)
    public class ProcesoDiario extends ProcedureOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of ProcesoDiario's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "daily process");
            setLocalizedLabel(SPANISH, "proceso diario");
            /**/
            // </editor-fold>
        }

    }

    protected ProcesoSemanal semanal;

    @OperationClass(access = OperationAccess.PRIVATE, asynchronous = Kleenean.TRUE, shell = Kleenean.TRUE)
    @ProcessOperationClass(bpl = Kleenean.FALSE, sql = Kleenean.TRUE)
    @ProcedureOperationClass(dataType = ProcedureDataType.NULL, type = ProcedureType.VOID)
    public class ProcesoSemanal extends ProcedureOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of ProcesoSemanal's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "weekly process");
            setLocalizedLabel(SPANISH, "proceso semanal");
            /**/
            // </editor-fold>
        }

    }

    protected ProcesoMensual mensual;

    @OperationClass(access = OperationAccess.PRIVATE, asynchronous = Kleenean.TRUE, shell = Kleenean.TRUE)
    @ProcessOperationClass(bpl = Kleenean.FALSE, sql = Kleenean.TRUE)
    @ProcedureOperationClass(dataType = ProcedureDataType.NULL, type = ProcedureType.VOID)
    public class ProcesoMensual extends ProcedureOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of ProcesoMensual's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "monthly process");
            setLocalizedLabel(SPANISH, "proceso mensual");
            /**/
            // </editor-fold>
        }

    }

}
