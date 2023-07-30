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
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
//@EntityTriggers(afterCheck = Kleenean.TRUE)
public class OpcionMenu extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public OpcionMenu(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    @StringField(maxLength = 200)
    public StringProperty codigoOpcionMenu;

    @NameProperty
    @StringField(maxLength = 200)
    public StringProperty nombreOpcionMenu;

    @DescriptionProperty
    @StringField(maxLength = 0)
    public StringProperty descripcionOpcionMenu;

    @UrlProperty
    public StringProperty urlOpcionMenu;

    public IntegerProperty secuenciaOpcionMenu;

    @StringField(maxLength = 30)
    public StringProperty claveOpcionMenu;

    @InactiveIndicator
    @PropertyField(table = Kleenean.FALSE)
    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esOpcionMenuInactiva;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esOpcionMenuSincronizada;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esEspecial;

    @ParentProperty
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE)
    public OpcionMenu opcionMenuSuperior;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public TipoNodo tipoNodo;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public NivelOpcionMenu nivelOpcionMenu;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of OpcionMenu's attributes">
        setLocalizedLabel(ENGLISH, "menu option");
        setLocalizedLabel(SPANISH, "opción de menú");
        setLocalizedShortLabel(ENGLISH, "option");
        setLocalizedShortLabel(SPANISH, "opción");
        setLocalizedCollectionLabel(ENGLISH, "Menu Options");
        setLocalizedCollectionLabel(SPANISH, "Opciones de Menú");
        setLocalizedCollectionShortLabel(ENGLISH, "Options");
        setLocalizedCollectionShortLabel(SPANISH, "Opciones");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        setOrderBy(codigoOpcionMenu);
        /**/
        esOpcionMenuInactiva.setInitialValue(false);
        esOpcionMenuInactiva.setDefaultValue(false);
        esOpcionMenuSincronizada.setInitialValue(false);
        esOpcionMenuSincronizada.setDefaultValue(false);
        esEspecial.setInitialValue(false);
        esEspecial.setDefaultValue(false);
        // <editor-fold defaultstate="collapsed" desc="localization of OpcionMenu's properties">
        codigoOpcionMenu.setLocalizedLabel(ENGLISH, "menu option code");
        codigoOpcionMenu.setLocalizedLabel(SPANISH, "código de la opción de menú");
        codigoOpcionMenu.setLocalizedShortLabel(ENGLISH, "code");
        codigoOpcionMenu.setLocalizedShortLabel(SPANISH, "código");
        /**/
        nombreOpcionMenu.setLocalizedLabel(ENGLISH, "menu option name");
        nombreOpcionMenu.setLocalizedLabel(SPANISH, "nombre de la opción de menú");
        nombreOpcionMenu.setLocalizedShortLabel(ENGLISH, "name");
        nombreOpcionMenu.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        descripcionOpcionMenu.setLocalizedLabel(ENGLISH, "menu option description");
        descripcionOpcionMenu.setLocalizedLabel(SPANISH, "descripción de la opción de menú");
        descripcionOpcionMenu.setLocalizedShortLabel(ENGLISH, "description");
        descripcionOpcionMenu.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        urlOpcionMenu.setLocalizedLabel(ENGLISH, "menu option URL");
        urlOpcionMenu.setLocalizedLabel(SPANISH, "URL de la opción de menú");
        urlOpcionMenu.setLocalizedShortLabel(ENGLISH, "URL");
        urlOpcionMenu.setLocalizedShortLabel(SPANISH, "URL");
        /**/
        secuenciaOpcionMenu.setLocalizedLabel(ENGLISH, "menu option sequence");
        secuenciaOpcionMenu.setLocalizedLabel(SPANISH, "secuencia opción menú");
        secuenciaOpcionMenu.setLocalizedShortLabel(ENGLISH, "sequence");
        secuenciaOpcionMenu.setLocalizedShortLabel(SPANISH, "secuencia");
        /**/
        claveOpcionMenu.setLocalizedLabel(ENGLISH, "menu option key");
        claveOpcionMenu.setLocalizedLabel(SPANISH, "clave opción menú");
        claveOpcionMenu.setLocalizedShortLabel(ENGLISH, "key");
        claveOpcionMenu.setLocalizedShortLabel(SPANISH, "clave");
        /**/
        esOpcionMenuInactiva.setLocalizedLabel(ENGLISH, "inactive menu option");
        esOpcionMenuInactiva.setLocalizedLabel(SPANISH, "opción de menú inactiva");
        esOpcionMenuInactiva.setLocalizedShortLabel(ENGLISH, "inactive");
        esOpcionMenuInactiva.setLocalizedShortLabel(SPANISH, "inactiva");
        /**/
        esOpcionMenuSincronizada.setLocalizedLabel(ENGLISH, "synchronized menu option");
        esOpcionMenuSincronizada.setLocalizedLabel(SPANISH, "opción menú sincronizada");
        esOpcionMenuSincronizada.setLocalizedShortLabel(ENGLISH, "synchronized");
        esOpcionMenuSincronizada.setLocalizedShortLabel(SPANISH, "sincronizada");
        /**/
        esEspecial.setLocalizedLabel(ENGLISH, "special");
        esEspecial.setLocalizedLabel(SPANISH, "especial");
        /**/
        opcionMenuSuperior.setLocalizedLabel(ENGLISH, "parent option");
        opcionMenuSuperior.setLocalizedLabel(SPANISH, "opción superior");
        /**/
        tipoNodo.setLocalizedLabel(ENGLISH, "node type");
        tipoNodo.setLocalizedLabel(SPANISH, "tipo de nodo");
        /**/
        nivelOpcionMenu.setLocalizedLabel(ENGLISH, "menu option level");
        nivelOpcionMenu.setLocalizedLabel(SPANISH, "nivel de opción de menú");
        nivelOpcionMenu.setLocalizedShortLabel(ENGLISH, "level");
        nivelOpcionMenu.setLocalizedShortLabel(SPANISH, "nivel");
        // </editor-fold>
    }

    // <editor-fold defaultstate="collapsed" desc="Operations">
    protected Desactivar desactivar;

    @ProcessOperationClass
    public class Desactivar extends ProcessOperation {

        @InstanceReference
        protected OpcionMenu opcionMenu;

        /**
         * settle Desactivar's attributes.
         */
        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Desactivar's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "deactivate");
            setLocalizedLabel(SPANISH, "desactivar");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "the menu option was deactivated");
            setLocalizedSuccessMessage(SPANISH, "la opción del menú fue desactivada");
            /**/
            // </editor-fold>
        }

        /**
         * settle Desactivar's parameters.
         */
        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of Desactivar's parameters">
            opcionMenu.setLocalizedLabel(ENGLISH, "menu option");
            opcionMenu.setLocalizedLabel(SPANISH, "opción de menú");
            opcionMenu.setLocalizedShortLabel(ENGLISH, "option");
            opcionMenu.setLocalizedShortLabel(SPANISH, "opción");
            // </editor-fold>
        }

    }

    protected Reactivar reactivar;

    @ProcessOperationClass
    public class Reactivar extends ProcessOperation {

        @InstanceReference
        protected OpcionMenu opcionMenu;

        /**
         * settle Reactivar's attributes.
         */
        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Reactivar's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "reactivate");
            setLocalizedLabel(SPANISH, "reactivar");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "the menu option was reactivated");
            setLocalizedSuccessMessage(SPANISH, "la opción del menú fue reactivada");
            /**/
            // </editor-fold>
        }

        /**
         * settle Reactivar's parameters.
         */
        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of Reactivar's parameters">
            opcionMenu.setLocalizedLabel(ENGLISH, "menu option");
            opcionMenu.setLocalizedLabel(SPANISH, "opción de menú");
            opcionMenu.setLocalizedShortLabel(ENGLISH, "option");
            opcionMenu.setLocalizedShortLabel(SPANISH, "opción");
            // </editor-fold>
        }

    }

    protected Reconstruir reconstruir;

    @OperationClass(asynchronous = Kleenean.TRUE)
    @ProcessOperationClass
    public class Reconstruir extends ProcessOperation {

        /**
         * settle Reconstruir's attributes.
         */
        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Reconstruir's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "rebuild");
            setLocalizedLabel(SPANISH, "reconstruir");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "the menu was rebuilt");
            setLocalizedSuccessMessage(SPANISH, "el menú fue reconstruido");
            /**/
            // </editor-fold>
        }

    }
    // </editor-fold>

}
