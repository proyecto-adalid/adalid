#set($dollar = '$')
#set($pound = '#')
#set($backslash = '\')
/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package ${package}.meta.entidad.modelo;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.expressions.*;
import adalid.core.interfaces.*;
import adalid.core.parameters.*;
import adalid.core.properties.*;
import adalid.jee2.constants.*;
import java.lang.reflect.Field;

/**
 * @author nombre del responsable de la entidad
 */
public class Enumeracion extends AbstractPersistentEnumerationEntity {

    public Enumeracion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
//      super.addAllocationStrings("fee", "faa", "foo");
    }

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
    }

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    @Override
    protected void settleProperties() {
        super.settleProperties();
    }

    public Instance A, B, C;

    @Override
    protected void settleInstances() {
        super.settleInstances();
    }

}
