/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.paquete.base;

import meta.enumeracion.base.TipoModuloBase;

/**
 * @author Jorge Campins
 */
public abstract class PaqueteRegistroBase extends PaqueteBase {

    @Override
    public final TipoModuloBase getTipo() {
        return TipoModuloBase.REGISTRO;
    }

}
