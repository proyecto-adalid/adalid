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
@EntityTableView(enabled = Kleenean.TRUE, menu = ViewMenuOption.READING)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class Funcion extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public Funcion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    @StringField(maxLength = 200)
    public StringProperty codigoFuncion;

    @NameProperty
    @StringField(maxLength = 200)
    public StringProperty nombreFuncion;

    @StringField(maxLength = 200)
    public StringProperty nombreJava;

    @StringField(maxLength = 200)
    public StringProperty nombreSql;

    @DescriptionProperty
    @StringField(maxLength = 0)
    public StringProperty descripcionFuncion;

    @StringField(maxLength = Project.STRING_FIELD_MAX_LENGTH)
    public StringProperty clausulaWhere;

    @StringField(maxLength = Project.STRING_FIELD_MAX_LENGTH)
    public StringProperty clausulaOrder;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esPublica;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esProgramatica;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esProtegida;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esPersonalizable;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esSegmentable;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esSupervisable;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esConstructor;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esHeredada;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esMetodoClase;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esMetodoInstancia;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of Funcion's attributes">
        setLocalizedLabel(ENGLISH, "function");
        setLocalizedLabel(SPANISH, "función");
        setLocalizedCollectionLabel(ENGLISH, "Functions");
        setLocalizedCollectionLabel(SPANISH, "Funciones");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Functions") + " represents an "
            + "application function (CRUD, business process, generation of files and reports, etc.)."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Funciones") + " representa una "
            + "función de la aplicación (CRUD, proceso de negocio, generación de archivos e informes, etc.)."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "application function (CRUD, business process, generation of files and reports, etc.)");
        setLocalizedShortDescription(SPANISH, "función de la aplicación (CRUD, proceso de negocio, generación de archivos e informes, etc.)");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        setOrderBy(codigoFuncion);
        /**/
        esPublica.setInitialValue(false);
        esPublica.setDefaultValue(false);
        esProgramatica.setInitialValue(false);
        esProgramatica.setDefaultValue(false);
        esProtegida.setInitialValue(false);
        esProtegida.setDefaultValue(false);
        esPersonalizable.setInitialValue(false);
        esPersonalizable.setDefaultValue(false);
        esSegmentable.setInitialValue(false);
        esSegmentable.setDefaultValue(false);
        esSupervisable.setInitialValue(false);
        esSupervisable.setDefaultValue(false);
        esConstructor.setInitialValue(false);
        esConstructor.setDefaultValue(false);
        esHeredada.setInitialValue(false);
        esHeredada.setDefaultValue(false);
        esMetodoClase.setInitialValue(false);
        esMetodoClase.setDefaultValue(false);
        esMetodoInstancia.setInitialValue(false);
        esMetodoInstancia.setDefaultValue(false);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of Funcion's properties">
        /**/
        codigoFuncion.setLocalizedLabel(ENGLISH, "function code");
        codigoFuncion.setLocalizedLabel(SPANISH, "código de la función");
        codigoFuncion.setLocalizedShortLabel(ENGLISH, "code");
        codigoFuncion.setLocalizedShortLabel(SPANISH, "código");
        /**/
        nombreFuncion.setLocalizedLabel(ENGLISH, "function name");
        nombreFuncion.setLocalizedLabel(SPANISH, "nombre de la función");
        nombreFuncion.setLocalizedShortLabel(ENGLISH, "name");
        nombreFuncion.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        nombreJava.setLocalizedLabel(ENGLISH, "java name");
        nombreJava.setLocalizedLabel(SPANISH, "nombre java");
        /**/
        nombreSql.setLocalizedLabel(ENGLISH, "sql name");
        nombreSql.setLocalizedLabel(SPANISH, "nombre sql");
        /**/
        descripcionFuncion.setLocalizedLabel(ENGLISH, "function description");
        descripcionFuncion.setLocalizedLabel(SPANISH, "descripción de la función");
        descripcionFuncion.setLocalizedShortLabel(ENGLISH, "description");
        descripcionFuncion.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        clausulaWhere.setLocalizedLabel(ENGLISH, "where clause");
        clausulaWhere.setLocalizedLabel(SPANISH, "clausula where");
        /**/
        clausulaOrder.setLocalizedLabel(ENGLISH, "order clause");
        clausulaOrder.setLocalizedLabel(SPANISH, "clausula order");
        /**/
        esPublica.setLocalizedLabel(ENGLISH, "public");
        esPublica.setLocalizedLabel(SPANISH, "pública");
        /**/
        esProgramatica.setLocalizedLabel(ENGLISH, "programmatic");
        esProgramatica.setLocalizedLabel(SPANISH, "programática");
        /**/
        esProtegida.setLocalizedLabel(ENGLISH, "protected");
        esProtegida.setLocalizedLabel(SPANISH, "protegida");
        /**/
        esPersonalizable.setLocalizedLabel(ENGLISH, "personalizable");
        esPersonalizable.setLocalizedLabel(SPANISH, "personalizable");
        /**/
        esSegmentable.setLocalizedLabel(ENGLISH, "segmentable");
        esSegmentable.setLocalizedLabel(SPANISH, "segmentable");
        /**/
        esSupervisable.setLocalizedLabel(ENGLISH, "supervisable");
        esSupervisable.setLocalizedLabel(SPANISH, "supervisable");
        /**/
        esConstructor.setLocalizedLabel(ENGLISH, "constructor");
        esConstructor.setLocalizedLabel(SPANISH, "constructor");
        /**/
        esHeredada.setLocalizedLabel(ENGLISH, "inherited");
        esHeredada.setLocalizedLabel(SPANISH, "heredada");
        /**/
        esMetodoClase.setLocalizedLabel(ENGLISH, "class method");
        esMetodoClase.setLocalizedLabel(SPANISH, "método de clase");
        /**/
        esMetodoInstancia.setLocalizedLabel(ENGLISH, "instance method");
        esMetodoInstancia.setLocalizedLabel(SPANISH, "método de instancia");
        /**/
        // </editor-fold>
    }

}
