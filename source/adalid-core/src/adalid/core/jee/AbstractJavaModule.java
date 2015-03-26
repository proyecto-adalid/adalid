/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.jee;

import adalid.core.Project;

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
