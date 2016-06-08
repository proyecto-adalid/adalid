/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.jaas.glassfish;

import adalid.jaas.google.GoogleRecaptcha;
import com.sun.appserv.security.AppservPasswordLoginModule;
import com.sun.enterprise.security.auth.realm.ldap.LDAPRealm;
import java.util.logging.Level;
import javax.security.auth.login.LoginException;

/**
 * @author Jorge Campins
 */
public class LDAPLoginModule extends AppservPasswordLoginModule {

    //  <editor-fold defaultstate="collapsed" desc="quick-setup">
    //  Edit glassfish/domains/domain1/config/login.conf
    //  Find the Realm of the application and replace:
    //      com.sun.enterprise.security.auth.login.LDAPLoginModule required;
    //  with:
    //      adalid.jaas.glassfish.LDAPLoginModule required;
    //
    //  Copy to glassfish/domains/domain1/lib:
    //      adalid-jaas.jar
    //      third-party/lib/httpcomponents-client-4.3.2/lib/*.jar
    //
    //  </editor-fold>
    //
    static {
        _logger.log(Level.INFO, "login-module = {0}", LDAPLoginModule.class.getName());
    }

    com.sun.enterprise.security.auth.login.LDAPLoginModule LDAPLoginModule;

    @Override
    protected void authenticateUser() throws LoginException {
        if (!GoogleRecaptcha.verifyUserResponse()) {
            throw new LoginException(GoogleRecaptcha.RESPONSE_COULD_NOT_BE_VERIFIED);
        }
        /**/
        if (!(_currentRealm instanceof LDAPRealm)) {
            String msg = sm.getString("ldaplm.badrealm");
            throw new LoginException(msg);
        }
        LDAPRealm ldapRealm = ((LDAPRealm) _currentRealm);
        if ((getPasswordChar() == null) || (getPasswordChar().length == 0)) {
            String msg = sm.getString("ldaplm.emptypassword", _username);
            throw new LoginException(msg);
        }
        String mode = _currentRealm.getProperty("mode");
        if ("find-bind".equals(mode)) {
            String[] grpList = ldapRealm.findAndBind(_username, getPasswordChar());
//          commitAuthentication(_username, getPasswordChar(), _currentRealm, grpList);
            commitUserAuthentication(grpList);
        } else {
            String msg = sm.getString("ldaplm.badmode", mode);
            throw new LoginException(msg);
        }
    }

}
