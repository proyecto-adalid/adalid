/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.paquete.base;

import adalid.core.predicates.IsEntityNameIncluded;
import meta.enumeracion.base.TipoModuloBase;
import meta.predicado.base.IsModuloConsultaDisplay;
import meta.predicado.base.IsModuloProcesamientoDisplay;
import meta.predicado.base.IsModuloRegistroDisplay;
import meta.proyecto.base.ModuloBase;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public abstract class PaqueteBase extends ModuloBase {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        String[] names = getLocallyDeclaredEntityClassSimpleNamesArray();
        IsEntityNameIncluded entityPredicate = new IsEntityNameIncluded();
        entityPredicate.setIncludedNames(names);
        Predicate pagePredicate = newPagePredicate(entityPredicate);
        setPagePredicate(pagePredicate);
    }

    @Override
    public void setAlias(String alias) {
        String apodo = StringUtils.isBlank(alias) ? getClass().getSimpleName() : StringUtils.capitalize(alias);
        TipoModuloBase tipo = getTipo();
        String prefijo = tipo == null ? null : StringUtils.capitalize(tipo.name().toLowerCase());
        String mote = tipo == null || StringUtils.startsWithIgnoreCase(apodo, prefijo) ? apodo : prefijo + apodo;
        super.setAlias(mote);
    }

    private Predicate newPagePredicate(Predicate entityPredicate) {
        TipoModuloBase tipo = getTipo();
        if (tipo == null) {
            return null;
        }
        Predicate predicate;
        switch (tipo) {
            case CONSULTA:
                predicate = new IsModuloConsultaDisplay(entityPredicate);
                break;
            case PROCESAMIENTO:
                predicate = new IsModuloProcesamientoDisplay(entityPredicate);
                break;
            case REGISTRO:
                predicate = new IsModuloRegistroDisplay(entityPredicate);
                break;
            default:
                predicate = null;
                break;
        }
        return predicate;
    }

}
