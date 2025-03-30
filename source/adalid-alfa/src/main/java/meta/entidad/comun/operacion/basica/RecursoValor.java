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
package meta.entidad.comun.operacion.basica;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;

/**
 * @author Jorge Campins
 */
@EntityClass(base = Kleenean.FALSE, independent = Kleenean.FALSE, variant = Kleenean.TRUE, resourceType = ResourceType.UNSPECIFIED, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.FALSE)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class RecursoValor extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public RecursoValor(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty id;
//
//  @VersionProperty
//  public LongProperty version;

    @BusinessKey
    public StringProperty codigo;

    @NameProperty
    public StringProperty nombre;
//
//  public LongProperty propietario;
//
//  public LongProperty segmento;
//
//  public LongProperty superior;
//
//  public LongProperty maestro;
//
//  public BooleanProperty inactivo;
//
//  public IntegerProperty numero;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
//      setSqlName("recurso");
        // <editor-fold defaultstate="collapsed" desc="localization of RecursoValor's attributes">
        setLocalizedLabel(ENGLISH, "resource");
        setLocalizedLabel(SPANISH, "recurso");
        setLocalizedCollectionLabel(ENGLISH, "Resources");
        setLocalizedCollectionLabel(SPANISH, "Recursos");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Resources") + " represents a "
            + "reference to a row of a table in the database."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Recursos") + " representa una "
            + "referencia a una fila de una tabla en la base de datos."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "reference to a row of a table in the database");
        setLocalizedShortDescription(SPANISH, "referencia a una fila de una tabla en la base de datos");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
//      id.setSqlName("id_recurso");
//      version.setSqlName("version_recurso");
//      codigo.setSqlName("codigo_recurso");
//      nombre.setSqlName("nombre_recurso");
//      propietario.setSqlName("id_propietario_recurso");
//      segmento.setSqlName("id_segmento_recurso");
//      superior.setSqlName("id_recurso_superior");
//      maestro.setSqlName("id_recurso_maestro");
//      inactivo.setSqlName("es_recurso_inactivo");
//      numero.setSqlName("numero_recurso");
        // <editor-fold defaultstate="collapsed" desc="localization of RecursoValor's properties">
        codigo.setLocalizedLabel(ENGLISH, "resource code");
        codigo.setLocalizedLabel(SPANISH, "código del recurso");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        nombre.setLocalizedLabel(ENGLISH, "resource name");
        nombre.setLocalizedLabel(SPANISH, "nombre del recurso");
        nombre.setLocalizedShortLabel(ENGLISH, "name");
        nombre.setLocalizedShortLabel(SPANISH, "nombre");
        // </editor-fold>
    }

}
