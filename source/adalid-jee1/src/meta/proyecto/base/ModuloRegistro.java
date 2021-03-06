/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.base;

import meta.enumeracion.base.TipoModuloBase;
import meta.predicado.base.IsModuloRegistroResidualDisplay;

/**
 * @author Jorge Campins
 */
public class ModuloRegistro extends ModuloBase {

    public ModuloRegistro() {
        super();
        setAlias("Registro");
        setDefaultLabel("Registro de Recursos");
        setDefaultDescription("Registro de Recursos");
        IsModuloRegistroResidualDisplay pagePredicate = new IsModuloRegistroResidualDisplay(this);
        setPagePredicate(pagePredicate);
    }

    @Override
    public final TipoModuloBase getTipo() {
        return TipoModuloBase.REGISTRO;
    }

}
