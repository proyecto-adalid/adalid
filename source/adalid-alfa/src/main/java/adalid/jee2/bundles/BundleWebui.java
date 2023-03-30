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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Jorge Campins
 */
public class BundleWebui extends BundleAbstracto {

    private static final Locale[] supported_locales = adalid.commons.bundles.Bundle.getSupportedLocales();

    private static final Map<Locale, ResourceBundle> bundles = new LinkedHashMap<>();

    private static final Map<Locale, Set<String>> bundleKeys = new LinkedHashMap<>();

    private static final Map<Locale, List<String>> bundleRows = new LinkedHashMap<>();

    private static final Map<Locale, Locale> locales = new LinkedHashMap<>();

    private static final Set<String> warnings = new LinkedHashSet<>();

    private static final Set<String> errors = new LinkedHashSet<>();

    private static boolean load = true;

    public BundleWebui() {
        super(load);
        load = false;
    }

    @Override
    protected Locale[] getSupportedLocales() {
        return supported_locales;
    }

    @Override
    protected Map<Locale, ResourceBundle> bundles() {
        return bundles;
    }

    @Override
    protected Map<Locale, Set<String>> bundleKeys() {
        return bundleKeys;
    }

    @Override
    protected Map<Locale, List<String>> bundleRows() {
        return bundleRows;
    }

    @Override
    protected Map<Locale, Locale> locales() {
        return locales;
    }

    @Override
    protected Set<String> warnings() {
        return warnings;
    }

    @Override
    protected Set<String> errors() {
        return errors;
    }

}
