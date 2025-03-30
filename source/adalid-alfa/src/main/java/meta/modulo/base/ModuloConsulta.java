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
package meta.modulo.base;

import meta.enumeracion.base.TipoModuloBase;
import meta.predicado.base.IsModuloConsultaResidualDisplay;

/**
 * @author Jorge Campins
 */
public class ModuloConsulta extends ModuloBase {

    public ModuloConsulta() {
        super();
        init();
    }

    private void init() {
        setAlias("Consulta");
        IsModuloConsultaResidualDisplay pagePredicate = new IsModuloConsultaResidualDisplay(this);
        setPagePredicate(pagePredicate);
        // <editor-fold defaultstate="collapsed" desc="localization of ModuloConsulta's attributes">
        setLocalizedLabel(ENGLISH, "Resource Inquiry");
        setLocalizedLabel(SPANISH, "Consulta de Recursos");
        setLocalizedDescription(ENGLISH, "Resource Inquiry");
        setLocalizedDescription(SPANISH, "Consulta de Recursos");
        // </editor-fold>
    }

    @Override
    public final TipoModuloBase getTipo() {
        return TipoModuloBase.CONSULTA;
    }

}
