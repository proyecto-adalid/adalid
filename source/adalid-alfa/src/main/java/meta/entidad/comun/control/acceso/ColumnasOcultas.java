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
import meta.entidad.comun.configuracion.basica.Pagina;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.FALSE, access = OperationAccess.PRIVATE)
@EntityInsertOperation(enabled = Kleenean.FALSE, access = OperationAccess.PRIVATE)
@EntityUpdateOperation(enabled = Kleenean.FALSE, access = OperationAccess.PRIVATE)
@EntityDeleteOperation(enabled = Kleenean.FALSE, access = OperationAccess.PRIVATE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class ColumnasOcultas extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public ColumnasOcultas(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of ColumnasOcultas's attributes">
        /**/
        setLocalizedLabel(ENGLISH, "hidden column list");
        setLocalizedLabel(SPANISH, "lista de columnas ocultas");
        setLocalizedCollectionLabel(ENGLISH, "Hidden Column Lists");
        setLocalizedCollectionLabel(SPANISH, "Listas de Columnas Ocultas");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Hidden Column Lists") + " represents a "
            + "list of columns on a tabular layout page that have been hidden by the application user."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Listas de Columnas Ocultas") + " representa una "
            + "lista de columnas en una página de presentación tabular que el usuario de la aplicación ha ocultado."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "hidden column lists");
        setLocalizedShortDescription(SPANISH, "listas de columnas ocultas");
        /**/
        // </editor-fold>
    }

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public Usuario usuario;

    @ColumnField(nullable = Kleenean.FALSE)
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public Pagina pagina;

    @BusinessKey
    @StringField(maxLength = 200)
    @PropertyField(update = Kleenean.FALSE)
    public StringProperty codigo;

    @ColumnField(nullable = Kleenean.FALSE)
    @StringField(maxLength = Constants.MAX_STRING_LENGTH)
    public StringProperty columnas;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of ColumnasOcultas's properties">
        /**/
        usuario.setLocalizedLabel(ENGLISH, "user");
        usuario.setLocalizedLabel(SPANISH, "usuario");
        usuario.setLocalizedShortLabel(ENGLISH, "user");
        usuario.setLocalizedShortLabel(SPANISH, "usuario");
        /**/
        pagina.setLocalizedLabel(ENGLISH, "page code");
        pagina.setLocalizedLabel(SPANISH, "código de la página");
        pagina.setLocalizedShortLabel(ENGLISH, "page");
        pagina.setLocalizedShortLabel(SPANISH, "página");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "list code");
        codigo.setLocalizedLabel(SPANISH, "código de la lista");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        columnas.setLocalizedLabel(ENGLISH, "column list");
        columnas.setLocalizedLabel(SPANISH, "lista de columnas");
        columnas.setLocalizedShortLabel(ENGLISH, "columns");
        columnas.setLocalizedShortLabel(SPANISH, "columnas");
        /**/
        // </editor-fold>
    }

}
