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
@EntityReferenceDisplay(filterType = EntityReferenceFilterType.LIST)
public class TipoRol extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public TipoRol(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    public Instance OPERADOR;

    public Instance REGISTRADOR;

    public Instance PROCESADOR;

    public Instance LECTOR;

    public Instance CONFIGURADOR;

    public Instance GESTOR;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of TipoRol's attributes">
        setLocalizedLabel(ENGLISH, "role type");
        setLocalizedLabel(SPANISH, "tipo de rol");
//      setLocalizedShortLabel(ENGLISH, "type");
//      setLocalizedShortLabel(SPANISH, "tipo");
        setLocalizedCollectionLabel(ENGLISH, "Role Types");
        setLocalizedCollectionLabel(SPANISH, "Tipos de Rol");
//      setLocalizedCollectionShortLabel(ENGLISH, "Types");
//      setLocalizedCollectionShortLabel(SPANISH, "Tipos");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoRol's properties">
        numero.setLocalizedLabel(ENGLISH, "role type number");
        numero.setLocalizedLabel(SPANISH, "número del tipo de rol");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "role type code");
        codigo.setLocalizedLabel(SPANISH, "código del tipo de rol");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        OPERADOR.newInstanceField(numero, 0);
        OPERADOR.newInstanceField(codigo, "Operador");
        REGISTRADOR.newInstanceField(numero, 1);
        REGISTRADOR.newInstanceField(codigo, "Registrador");
        PROCESADOR.newInstanceField(numero, 2);
        PROCESADOR.newInstanceField(codigo, "Procesador");
        LECTOR.newInstanceField(numero, 4);
        LECTOR.newInstanceField(codigo, "Lector");
        CONFIGURADOR.newInstanceField(numero, 8);
        CONFIGURADOR.newInstanceField(codigo, "Configurador");
        GESTOR.newInstanceField(numero, 16);
        GESTOR.newInstanceField(codigo, "Gestor");
        // <editor-fold defaultstate="collapsed" desc="localization of TipoRol's instances">
        OPERADOR.newInstanceField(codigo, "Operator", ENGLISH);
        OPERADOR.newInstanceField(codigo, "Operador", SPANISH);
        /**/
        REGISTRADOR.newInstanceField(codigo, "Data Entry Operator", ENGLISH);
        REGISTRADOR.newInstanceField(codigo, "Registrador", SPANISH);
        /**/
        PROCESADOR.newInstanceField(codigo, "Processing Manager", ENGLISH);
        PROCESADOR.newInstanceField(codigo, "Procesador", SPANISH);
        /**/
        LECTOR.newInstanceField(codigo, "Reader", ENGLISH);
        LECTOR.newInstanceField(codigo, "Lector", SPANISH);
        /**/
        CONFIGURADOR.newInstanceField(codigo, "Configuration Manager", ENGLISH);
        CONFIGURADOR.newInstanceField(codigo, "Configurador", SPANISH);
        /**/
        GESTOR.newInstanceField(codigo, "Manager", ENGLISH);
        GESTOR.newInstanceField(codigo, "Gestor", SPANISH);
        // </editor-fold>
    }

}
