/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.operacion.basica;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
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
    @Deprecated
    private RecursoValor() {
        this(null, null);
    }

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
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
//      setSqlName("recurso");
        setDefaultLabel("recurso");
//      setDefaultShortLabel("recurso");
        setDefaultCollectionLabel("recursos");
//      setDefaultCollectionShortLabel("recursos");
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
    }

}
