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
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class DominioParametro extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public DominioParametro(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    @StringField(maxLength = 100)
    public StringProperty codigoDominioParametro;

    @ColumnField(nullable = Kleenean.FALSE)
    @StringField(maxLength = Project.STRING_FIELD_MAX_LENGTH)
    public StringProperty columna;

    @ColumnField(nullable = Kleenean.FALSE)
    @StringField(maxLength = 200)
    public StringProperty alias;

    @StringField(maxLength = 100)
    public StringProperty etiquetaParametro;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    public Dominio dominio;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    public Parametro parametro;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    public TipoParametroDom tipoParametroDom;

    protected Key uk_dominio_parametro_0001;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setOrderBy(codigoDominioParametro);
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of DominioParametro's attributes">
        setLocalizedLabel(ENGLISH, "domain parameter");
        setLocalizedLabel(SPANISH, "parámetro de dominio");
        setLocalizedShortLabel(ENGLISH, "parameter");
        setLocalizedShortLabel(SPANISH, "parámetro");
        setLocalizedCollectionLabel(ENGLISH, "Domain Parameters");
        setLocalizedCollectionLabel(SPANISH, "Parámetros de Dominio");
        setLocalizedCollectionShortLabel(ENGLISH, "Parameters");
        setLocalizedCollectionShortLabel(SPANISH, "Parámetros");
        setLocalizedDescription(ENGLISH, "persistence domain object's property that typically represents a table's column in the database");
        setLocalizedDescription(SPANISH, "propiedad del objeto de dominio de persistencia que normalmente representa la columna de una tabla en la base de datos");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of DominioParametro's properties">
        codigoDominioParametro.setLocalizedLabel(ENGLISH, "domain parameter code");
        codigoDominioParametro.setLocalizedLabel(SPANISH, "código del parámetro de dominio");
        codigoDominioParametro.setLocalizedShortLabel(ENGLISH, "code");
        codigoDominioParametro.setLocalizedShortLabel(SPANISH, "código");
        /**/
        columna.setLocalizedLabel(ENGLISH, "column");
        columna.setLocalizedLabel(SPANISH, "columna");
        /**/
        alias.setLocalizedLabel(ENGLISH, "alias");
        alias.setLocalizedLabel(SPANISH, "alias");
        /**/
        etiquetaParametro.setLocalizedLabel(ENGLISH, "parameter label");
        etiquetaParametro.setLocalizedLabel(SPANISH, "etiqueta parámetro");
        /**/
        dominio.setLocalizedLabel(ENGLISH, "domain");
        dominio.setLocalizedLabel(SPANISH, "dominio");
        /**/
        parametro.setLocalizedLabel(ENGLISH, "parameter");
        parametro.setLocalizedLabel(SPANISH, "parámetro");
        /**/
        tipoParametroDom.setLocalizedLabel(ENGLISH, "domain parameter type");
        tipoParametroDom.setLocalizedLabel(SPANISH, "tipo de parámetro de dominio");
        tipoParametroDom.setLocalizedShortLabel(ENGLISH, "parameter type");
        tipoParametroDom.setLocalizedShortLabel(SPANISH, "tipo de parámetro");
        // </editor-fold>
    }

    @Override
    protected void settleKeys() {
        super.settleKeys();
        uk_dominio_parametro_0001.setUnique(true);
        uk_dominio_parametro_0001.newKeyField(dominio, tipoParametroDom);
    }

}
