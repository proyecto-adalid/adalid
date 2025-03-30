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
package meta.paquete.base;

import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.predicates.*;
import meta.enumeracion.base.TipoModuloBase;
import meta.modulo.base.ModuloBase;
import meta.predicado.base.IsModuloConsultaDisplay;
import meta.predicado.base.IsModuloProcesamientoDisplay;
import meta.predicado.base.IsModuloRegistroDisplay;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
@ProjectModuleDocGen(classDiagram = Kleenean.FALSE)
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
        predicate = switch (tipo) {
            case CONSULTA ->
                new IsModuloConsultaDisplay(entityPredicate);
            case PROCESAMIENTO ->
                new IsModuloProcesamientoDisplay(entityPredicate);
            case REGISTRO ->
                new IsModuloRegistroDisplay(entityPredicate);
            default ->
                null;
        };
        return predicate;
    }

}
