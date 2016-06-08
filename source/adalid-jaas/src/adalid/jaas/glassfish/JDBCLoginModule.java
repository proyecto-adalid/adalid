/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.jaas.glassfish;

import adalid.jaas.google.GoogleRecaptcha;
import com.sun.appserv.security.AppservPasswordLoginModule;
import com.sun.enterprise.security.auth.realm.jdbc.JDBCRealm;
import java.util.Arrays;
import java.util.logging.Level;
import javax.security.auth.login.LoginException;

/**
 * @author Jorge Campins
 */
public class JDBCLoginModule extends AppservPasswordLoginModule {

    //  <editor-fold defaultstate="collapsed" desc="quick-setup">
    //  Edit glassfish/domains/domain1/config/login.conf
    //  Find the Realm of the application and replace:
    //      com.sun.enterprise.security.ee.auth.login.JDBCLoginModule required;
    //  with:
    //      adalid.jaas.glassfish.JDBCLoginModule required;
    //
    //  Copy to glassfish/domains/domain1/lib:
    //      adalid-jaas.jar
    //      third-party/lib/httpcomponents-client-4.3.2/lib/*.jar
    //
    //  </editor-fold>
    //
    static {
        _logger.log(Level.INFO, "login-module = {0}", JDBCLoginModule.class.getName());
    }

    com.sun.enterprise.security.ee.auth.login.JDBCLoginModule JDBCLoginModule;

    @Override
    protected void authenticateUser() throws LoginException {
        if (!GoogleRecaptcha.verifyUserResponse()) {
            throw new LoginException(GoogleRecaptcha.RESPONSE_COULD_NOT_BE_VERIFIED);
        }
        /**/
        if (!(_currentRealm instanceof JDBCRealm)) {
            String msg = sm.getString("jdbclm.badrealm");
            throw new LoginException(msg);
        }
        JDBCRealm jdbcRealm = (JDBCRealm) _currentRealm;
        if ((_username == null) || (_username.length() == 0)) {
            String msg = sm.getString("jdbclm.nulluser");
            throw new LoginException(msg);
        }
        String[] grpList = jdbcRealm.authenticate(_username, getPasswordChar());
        if (grpList == null) {
            String msg = sm.getString("jdbclm.loginfail", _username);
            throw new LoginException(msg);
        }
        _logger.log(Level.FINEST, "JDBC login succeeded for: {0} groups:{1}", new Object[]{_username, Arrays.toString(grpList)});
//      commitAuthentication(_username, getPasswordChar(), _currentRealm, grpList);
        commitUserAuthentication(grpList);
    }

}
