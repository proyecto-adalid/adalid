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
package meta.entidad.comun.control.prueba;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import java.lang.reflect.Field;
import meta.entidad.base.PersistentEnumerationEntityBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class TipoDocumentoPrueba extends PersistentEnumerationEntityBase {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public TipoDocumentoPrueba(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of TipoDocumentoPrueba's attributes">
        setLocalizedLabel(ENGLISH, "test document type");
        setLocalizedLabel(SPANISH, "tipo de documento de prueba");
        setLocalizedShortLabel(ENGLISH, "document type");
        setLocalizedShortLabel(SPANISH, "tipo de documento");
        setLocalizedCollectionLabel(ENGLISH, "Test Document Types");
        setLocalizedCollectionLabel(SPANISH, "Tipos de Documentos de Prueba");
        setLocalizedCollectionShortLabel(ENGLISH, "Document Types");
        setLocalizedCollectionShortLabel(SPANISH, "Tipos de Documentos");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoDocumentoPrueba's properties">
        numero.setLocalizedLabel(ENGLISH, "test document type number");
        numero.setLocalizedLabel(SPANISH, "número del tipo de documento de prueba");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "test document type code");
        codigo.setLocalizedLabel(SPANISH, "código del tipo de documento de prueba");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        // </editor-fold>
    }

    static final String TIPO_CASO = "1";

    static final String TIPO_ESCENARIO = "2";

    static final String TIPO_PROGRAMA = "3";

    static final String TIPO_LINEA = "4";

    static final String TIPO_EJECUCION_PROGRAMA = "5";

    static final String TIPO_EJECUCION_LINEA = "6";

    public Instance CASO;

    public Instance ESCENARIO;

    public Instance PROGRAMA;

    public Instance LINEA;

    public Instance EJECUCION_PROGRAMA;

    public Instance EJECUCION_LINEA;

    @Override
    protected void settleInstances() {
        super.settleInstances();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoDocumentoPrueba's instances">
        CASO.newInstanceField(codigo, "Case", ENGLISH);
        CASO.newInstanceField(codigo, "Caso", SPANISH);
        /**/
        ESCENARIO.newInstanceField(codigo, "Scenario", ENGLISH);
        ESCENARIO.newInstanceField(codigo, "Escenario", SPANISH);
        /**/
        PROGRAMA.newInstanceField(codigo, "Program", ENGLISH);
        PROGRAMA.newInstanceField(codigo, "Programa", SPANISH);
        /**/
        LINEA.newInstanceField(codigo, "Line", ENGLISH);
        LINEA.newInstanceField(codigo, "Linea", SPANISH);
        /**/
        EJECUCION_PROGRAMA.newInstanceField(codigo, "Program execution", ENGLISH);
        EJECUCION_PROGRAMA.newInstanceField(codigo, "Ejecucion programa", SPANISH);
        /**/
        EJECUCION_LINEA.newInstanceField(codigo, "Line execution", ENGLISH);
        EJECUCION_LINEA.newInstanceField(codigo, "Ejecucion linea", SPANISH);
        // </editor-fold>
    }

}
