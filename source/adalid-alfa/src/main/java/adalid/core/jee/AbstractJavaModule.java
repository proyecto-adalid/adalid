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
package adalid.core.jee;

import adalid.core.*;

/**
 * @author Jorge Campins
 */
public abstract class AbstractJavaModule extends Project implements JavaModule {

    public AbstractJavaModule() {
        super();
    }

    private JavaModuleType _moduleType = JavaModuleType.UNSPECIFIED;

    /**
     * @return the JEE module type
     */
    @Override
    public JavaModuleType getModuleType() {
        return _moduleType;
    }

    /**
     * @param moduleType the JEE module type to set
     */
    void setModuleType(JavaModuleType moduleType) {
        _moduleType = moduleType;
    }

}
