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
@EntityClass(base = Kleenean.FALSE, independent = Kleenean.TRUE, resourceType = ResourceType.UNSPECIFIED, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(sql = Kleenean.FALSE, bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.FALSE)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
@EntityWarnings(aboutVisibleFields = Kleenean.FALSE)
public class TareaVirtual extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public TareaVirtual(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty id;

    public LongProperty idFuncion;

    public LongProperty idClaseRecursoValor;

    public LongProperty idRecursoValor;

    public StringProperty codigoRecursoValor;

    public StringProperty nombreRecursoValor;

    public StringProperty descripcionRecursoValor;

    public LongProperty idPropietario;

    public LongProperty idSegmento;

    public StringProperty listaFunciones;

    public LongProperty idUsuario;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of TareaVirtual's attributes">
        setLocalizedLabel(ENGLISH, "virtual task");
        setLocalizedLabel(SPANISH, "tarea virtual");
        setLocalizedCollectionLabel(ENGLISH, "Virtual Tasks");
        setLocalizedCollectionLabel(SPANISH, "Tareas Virtuales");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Virtual Tasks") + " represents a "
            + "virtual task notification."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Tareas Virtuales") + " representa una "
            + "notificación de tarea virtual."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "virtual task notification");
        setLocalizedShortDescription(SPANISH, "notificación de tarea virtual");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of TareaVirtual's properties">
        /**/
        idFuncion.setLocalizedLabel(ENGLISH, "function");
        idFuncion.setLocalizedLabel(SPANISH, "función");
        /**/
        idClaseRecursoValor.setLocalizedLabel(ENGLISH, "resource class");
        idClaseRecursoValor.setLocalizedLabel(SPANISH, "clase de recurso");
        /**/
        idRecursoValor.setLocalizedLabel(ENGLISH, "resource");
        idRecursoValor.setLocalizedLabel(SPANISH, "recurso valor");
        /**/
        codigoRecursoValor.setLocalizedLabel(ENGLISH, "resource code");
        codigoRecursoValor.setLocalizedLabel(SPANISH, "código recurso valor");
        /**/
        nombreRecursoValor.setLocalizedLabel(ENGLISH, "resource name");
        nombreRecursoValor.setLocalizedLabel(SPANISH, "nombre recurso valor");
        /**/
        descripcionRecursoValor.setLocalizedLabel(ENGLISH, "resource description");
        descripcionRecursoValor.setLocalizedLabel(SPANISH, "descripción del recurso");
        /**/
        idPropietario.setLocalizedLabel(ENGLISH, "owner");
        idPropietario.setLocalizedLabel(SPANISH, "propietario");
        /**/
        idSegmento.setLocalizedLabel(ENGLISH, "segment");
        idSegmento.setLocalizedLabel(SPANISH, "segmento");
        /**/
        listaFunciones.setLocalizedLabel(ENGLISH, "function list");
        listaFunciones.setLocalizedLabel(SPANISH, "lista de funciones");
        /**/
        idUsuario.setLocalizedLabel(ENGLISH, "user");
        idUsuario.setLocalizedLabel(SPANISH, "usuario");
        /**/
        // </editor-fold>
    }

}
