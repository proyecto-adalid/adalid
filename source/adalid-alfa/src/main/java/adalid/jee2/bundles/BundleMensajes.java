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
package adalid.jee2.bundles;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Jorge Campins
 */
public class BundleMensajes extends BundleAbstracto {

    private static final Map<Locale, ResourceBundle> bundles = new LinkedHashMap<>();

    private static final Map<Locale, Set<String>> bundleKeys = new LinkedHashMap<>();

    private static final Map<Locale, List<String>> bundleRows = new LinkedHashMap<>();

    private static final Map<Locale, Locale> locales = new LinkedHashMap<>();

    private static boolean load = true;

    public BundleMensajes() {
        super(load);
        load = false;
    }

    @Override
    Map<Locale, ResourceBundle> bundles() {
        return bundles;
    }

    @Override
    Map<Locale, Set<String>> bundleKeys() {
        return bundleKeys;
    }

    @Override
    Map<Locale, List<String>> bundleRows() {
        return bundleRows;
    }

    @Override
    Map<Locale, Locale> locales() {
        return locales;
    }

}