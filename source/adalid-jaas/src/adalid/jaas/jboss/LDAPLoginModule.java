/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.jaas.jboss;

import adalid.jaas.google.GoogleRecaptcha;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginException;

/**
 * @author Jorge Campins
 */
public class LDAPLoginModule extends org.jboss.security.auth.spi.LdapExtLoginModule {

    //  <editor-fold defaultstate="collapsed" desc="quick-setup">
    //  Edit jboss/standalone/configuration/standalone-full.xml
    //  Find the <security-domain> of the application and replace:
    //      <login-module code="LdapExtended" flag="required">
    //  with:
    //      <login-module code="adalid.jaas.jboss.LDAPLoginModule" flag="required" module="adalid.jaas.jboss">
    //
    //  </editor-fold>
    //
    private static final Logger logger = Logger.getLogger(LDAPLoginModule.class.getName());

    static {
        logger.log(Level.INFO, "login-module = {0}", LDAPLoginModule.class.getName());
    }

    @Override
    public boolean login() throws LoginException {
        if (!GoogleRecaptcha.verifyUserResponse()) {
            throw new LoginException(GoogleRecaptcha.RESPONSE_COULD_NOT_BE_VERIFIED);
        }
        return super.login();
    }

}
