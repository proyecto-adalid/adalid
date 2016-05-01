/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.util.sql;

import adalid.commons.TLB;
import adalid.commons.interfaces.Programmer;
import adalid.commons.properties.PropertiesHandler;
import adalid.commons.util.FilUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class SqlUtil {

    private static final Logger logger = Logger.getLogger(SqlUtil.class);

    private static final ExtendedProperties bootstrapping = PropertiesHandler.getBootstrapping();

    static String highlight(String text) {
        return "WATCH OUT! " + text;
    }

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    protected String _dbms;

    protected String _host;

    protected String _port;

    protected String _user;

    protected String _password;

    protected String _database;

    protected String _schema;

    protected String _urlPattern;

    protected String _driver;

    protected String _programmer;

    protected String _url;

    protected Connection _connection;

    protected boolean _initialised;
    // </editor-fold>

    public SqlUtil(String[] args) {
        if (args == null || args.length == 0) {
            illegalArguments();
        } else {
            _initialised = true;
            _initialised = _initialised && dbms(args);
            _initialised = _initialised && host(args);
            _initialised = _initialised && port(args);
            _initialised = _initialised && user(args);
            _initialised = _initialised && password(args);
            _initialised = _initialised && database(args);
            _initialised = _initialised && schema(args);
        }
    }

    private void illegalArguments() {
        logSyntaxError();
    }

    // <editor-fold defaultstate="collapsed" desc="args">
    private boolean dbms(String[] args) {
        _dbms = arg(0, args).toLowerCase();
        if (StringUtils.isNotBlank(_dbms)) {
            _urlPattern = bootstrapping.getString(_dbms + ".string");
            _driver = bootstrapping.getString(_dbms + ".driver");
            _programmer = bootstrapping.getString(_dbms + ".programmer");
            if (StringUtils.isNotBlank(_urlPattern) && StringUtils.isNotBlank(_driver) && StringUtils.isNotBlank(_programmer)) {
                Object object = getNewInstanceForName(_programmer);
                if (object instanceof Programmer) {
                    TLB.setProgrammer(_dbms, (Programmer) object);
                    logValidArgument("dbms", _dbms);
                    return true;
                }
            }
        }
        logInvalidArgument("dbms", _dbms);
        logSyntaxError();
        return false;
    }

    private boolean host(String[] args) {
        _host = arg(1, args);
        if (StringUtils.isNotBlank(_host)) {
            logValidArgument("host", _host);
            return true;
        }
        logInvalidArgument("host", _host);
        logSyntaxError();
        return false;
    }

    private boolean port(String[] args) {
        _port = arg(2, args);
        if (StringUtils.isNotBlank(_port)) {
            logValidArgument("port", _port);
            return true;
        }
        logInvalidArgument("port", _port);
        logSyntaxError();
        return false;
    }

    private boolean user(String[] args) {
        _user = arg(3, args);
        if (StringUtils.isNotBlank(_user)) {
            logValidArgument("user", _user);
            return true;
        }
        logInvalidArgument("user", _user);
        logSyntaxError();
        return false;
    }

    private boolean password(String[] args) {
        _password = arg(4, args);
        if (StringUtils.isNotBlank(_password)) {
//          logValidArgument("password", _password);
            return true;
        }
        logInvalidArgument("password", _password);
        logSyntaxError();
        return false;
    }

    private boolean database(String[] args) {
        _database = arg(5, args);
        if (StringUtils.isNotBlank(_database)) {
            logValidArgument("database", _database);
            return true;
        }
        logInvalidArgument("database", _database);
        logSyntaxError();
        return false;
    }

    private boolean schema(String[] args) {
        _schema = arg(6, args);
        if (StringUtils.isNotBlank(_schema)) {
            logValidArgument("schema", _schema);
            return true;
        }
        logInvalidArgument("schema", _schema);
        logSyntaxError();
        return false;
    }

    protected String arg(int i, String[] args) {
        return args != null && args.length > i && args[i] != null ? args[i].trim() : "";
    }

    protected void logValidArgument(String param, String arg) {
        logger.info(param + " = \"" + arg + "\" ");
    }

    protected void logInvalidArgument(String param, String arg) {
        logger.error(param + " \"" + arg + "\" " + "is missing or invalid");
    }

    protected void logSyntaxError() {
        logger.error("Syntax: " + getClass().getSimpleName() + " dbms, host, port, user, password, database, schema");
    }

    protected Object getNewInstanceForName(String className) {
        Class<?> clazz = getClassForName(className);
        if (clazz == null) {
            return null;
        }
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected Class<?> getClassForName(String className) {
        if (StringUtils.isBlank(className)) {
            return null;
        }
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="connect & close">
    protected boolean connect() {
        logger.info("connect");
        if (_connection == null) {
            _url = _urlPattern;
            _url = _url.replace("%dbhost%", _host);
            _url = _url.replace("%dbport%", _port);
            _url = _url.replace("%dbuser%", _user);
            _url = _url.replace("%dbpass%", _password);
            _url = _url.replace("%dbname%", _database);
            try {
                Class.forName(_driver);
                _connection = DriverManager.getConnection(_url, _user, _password);
                logger.info(_url + " connection opened");
            } catch (ClassNotFoundException | SQLException ex) {
                _connection = null;
                logger.fatal(_url, ex);
            }
        }
        return _connection != null;
    }

    protected boolean close() {
        logger.info("close");
        if (_connection != null) {
            try {
                if (!_connection.isClosed()) {
                    _connection.close();
                }
                logger.info(_url + " connection closed ");
                _connection = null;
                _url = null;
            } catch (SQLException ex) {
                logger.fatal(_url, ex);
            }
        }
        return _connection == null;
    }

    protected boolean executeStatement(String statement) throws SQLException {
        try {
            logger.debug(statement);
            PreparedStatement prepareStatement = _connection.prepareStatement(statement);
            return prepareStatement.execute();
        } catch (SQLException ex) {
            logger.fatal(statement, ex);
            throw ex;
        }
    }

    protected PreparedStatement prepareStatement(String statement) {
        try {
            logger.debug(statement);
            return _connection.prepareStatement(statement);
        } catch (SQLException ex) {
            logger.fatal(statement, ex);
        }
        return null;
    }

    protected void close(PreparedStatementWrapper wrapper) {
        if (wrapper != null) {
            close(wrapper.getPreparedStatement());
        }
    }

    protected void close(PreparedStatement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
                logger.fatal(statement, ex);
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getters">
    public String getDbms() {
        return _dbms;
    }

    public String getHost() {
        return _host;
    }

    public String getPort() {
        return _port;
    }

    public String getUser() {
        return _user;
    }

    public String getPassword() {
        return _password;
    }

    public String getDatabase() {
        return _database;
    }

    public String getSchema() {
        return _schema;
    }

    public String getDriver() {
        return _driver;
    }

    public String getUrl() {
        return _url;
    }

    public boolean isInitialised() {
        return _initialised;
    }

    public String getMetajavaPath() {
        return FilUtils.fixPath(bootstrapping.getString(("metajava.path")));
    }

    public String getCommonsPackage() {
        return bootstrapping.getString("commons.package.name");
    }

    public String getDefaultPackage() {
        return bootstrapping.getString("default.package.name");
    }

    public String getSourceMetajavaPackage() {
        return getCommonsPackage();
    }

    public String getTargetMetajavaPackage() {
        return getDefaultPackage() + "." + _database.toLowerCase();
    }
    // </editor-fold>

    protected class PreparedStatementWrapper {

        String _statement;

        PreparedStatement _preparedStatement;

        public PreparedStatementWrapper(String statement) {
            _statement = statement;
            _preparedStatement = prepareStatement(statement);
        }

        public String getStatement() {
            return _statement;
        }

        public PreparedStatement getPreparedStatement() {
            return _preparedStatement;
        }

        public ResultSet executeQuery() throws SQLException {
            return _preparedStatement.executeQuery();
        }

        @Override
        public String toString() {
            return _statement;
        }

    }

}
