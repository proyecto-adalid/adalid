/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.jaas.jboss;

import adalid.jaas.google.GoogleRecaptcha;
import javax.security.auth.login.LoginException;

/**
 * @author Jorge Campins
 */
public class JDBCLoginModule extends org.jboss.security.auth.spi.DatabaseServerLoginModule {

    //  <editor-fold defaultstate="collapsed" desc="quick-setup">
    //  Edit jboss/standalone/configuration/standalone-full.xml
    //  Find the <security-domain> of the application and replace:
    //      <login-module code="Database" flag="required">
    //  with:
    //      <login-module code="adalid.jaas.jboss.JDBCLoginModule" flag="required" module="adalid.jaas.jboss">
    //
    //  </editor-fold>
    //
    @Override
    public boolean login() throws LoginException {
        if (!GoogleRecaptcha.verifyUserResponse()) {
            throw new LoginException(GoogleRecaptcha.RESPONSE_COULD_NOT_BE_VERIFIED);
        }
        return super.login();
    }

}
