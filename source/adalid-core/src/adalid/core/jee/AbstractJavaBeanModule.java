/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.jee;

/**
 * @author Jorge Campins
 */
public abstract class AbstractJavaBeanModule extends AbstractJavaModule implements JavaBeanModule {

    public AbstractJavaBeanModule() {
        super();
        setModuleType(JavaModuleType.EJB);
    }

}
