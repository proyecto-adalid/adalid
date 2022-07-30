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
package adalid.util.sql;

import adalid.commons.*;
import adalid.commons.interfaces.*;
import adalid.commons.properties.*;
import adalid.commons.util.*;
import adalid.util.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import javax.swing.JOptionPane;
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

    protected int _argIndex;

    protected String[] _args;

    private boolean _argx;
    // </editor-fold>

    public SqlUtil() {
        args();
    }

    public SqlUtil(String[] args) {
        if (args == null || args.length == 0) {
            args();
        } else {
            _args = args;
            _argx = true;
            _initialised = true;
            _initialised = _initialised && dbms(_argIndex++, args);
            init();
        }
    }

    private void args() {
        if (dbms()) {
            String name = getClass().getName() + "." + _dbms;
            _args = Utility.getArguments(name);
            _initialised = _args != null && _args.length > 0;
            init();
        }
    }

    private void init() {
        _initialised = _initialised && host(_argIndex++, _args);
        _initialised = _initialised && port(_argIndex++, _args);
        _initialised = _initialised && user(_argIndex++, _args);
        _initialised = _initialised && password(_argIndex++, _args);
        _initialised = _initialised && database(_argIndex++, _args);
        _initialised = _initialised && schema(_argIndex++, _args);
    }

    // <editor-fold defaultstate="collapsed" desc="args">
    private boolean dbms() {
//      final Component parent = null;
        final Object message = "DBMS";
        final String title = "Choose";
        final int type = JOptionPane.QUESTION_MESSAGE;
//      final Icon icon = null;
        final Object[] values = {"postgresql", "oracle"};
        final Object initialValue = values[0];
        logger.info(title + " " + message + " " + Arrays.toString(values));
        Object value = JOptionPane.showInputDialog(null, message, title, type, null, values, initialValue);
        _dbms = value == null ? null : value.toString();
        return dbms(false);
    }

    private boolean dbms(int i, String[] args) {
        _dbms = arg(i, args).toLowerCase();
        return dbms(true);
    }

    private boolean dbms(boolean logSyntaxError) {
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
        if (logSyntaxError) {
            logSyntaxError();
        }
        return false;
    }

    private boolean host(int i, String[] args) {
        _host = arg(i, args);
        if (StringUtils.isNotBlank(_host)) {
            logValidArgument("host", _host);
            return true;
        }
        logInvalidArgument("host", _host);
        logSyntaxError();
        return false;
    }

    private boolean port(int i, String[] args) {
        _port = arg(i, args);
        if (StringUtils.isNotBlank(_port)) {
            logValidArgument("port", _port);
            return true;
        }
        logInvalidArgument("port", _port);
        logSyntaxError();
        return false;
    }

    private boolean user(int i, String[] args) {
        _user = arg(i, args);
        if (StringUtils.isNotBlank(_user)) {
            logValidArgument("user", _user);
            return true;
        }
        logInvalidArgument("user", _user);
        logSyntaxError();
        return false;
    }

    private boolean password(int i, String[] args) {
        _password = arg(i, args);
        if (StringUtils.isNotBlank(_password)) {
//          logValidArgument("password", _password);
            return true;
        }
        logInvalidArgument("password", _password);
        logSyntaxError();
        return false;
    }

    private boolean database(int i, String[] args) {
        _database = arg(i, args);
        if (StringUtils.isNotBlank(_database)) {
            logValidArgument("database", _database);
            return true;
        }
        logInvalidArgument("database", _database);
        logSyntaxError();
        return false;
    }

    private boolean schema(int i, String[] args) {
        _schema = arg(i, args);
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
        if (arg == null) {
            logger.error(param + " is null");
        } else {
            logger.error(param + " \"" + arg + "\" " + "is invalid");
        }
    }

    protected void logSyntaxError() {
        logger.error(getSyntax());
    }

    protected String getSyntax() {
        return getSqlUtilSyntax();
    }

    protected final String getSqlUtilSyntax() {
        String dbms = _argx ? " dbms," : "";
        return "Syntax: " + getClass().getSimpleName() + dbms + " host, port, user, password, database, schema";
    }

    protected Object getNewInstanceForName(String className) {
        Class<?> clazz = getClassForName(className);
        if (clazz == null) {
            return null;
        }
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException ex) {
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
            boolean oracle = StringUtils.equalsIgnoreCase(_dbms, "oracle");
            String user = oracle ? _user.toUpperCase() : _user;
            _url = _urlPattern;
            _url = _url.replace("%dbhost%", _host);
            _url = _url.replace("%dbport%", _port);
            _url = _url.replace("%dbuser%", user);
            _url = _url.replace("%dbpass%", _password);
            _url = _url.replace("%dbname%", _database);
            try {
                Class.forName(_driver);
                _connection = DriverManager.getConnection(_url, user, _password);
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

    protected boolean isRemoteConnection() {
        return _host != null && !_host.equals("127.0.0.1") && !_host.equalsIgnoreCase("localhost");
    }

    protected boolean executeStatement(String statement) throws SQLException {
        try {
            logger.debug(_url + " " + statement);
            PreparedStatement prepareStatement = _connection.prepareStatement(statement);
            return prepareStatement.execute();
        } catch (SQLException ex) {
            logger.fatal(statement, ex);
            throw ex;
        }
    }

    protected PreparedStatement prepareStatement(String statement) {
        try {
            logger.debug(_url + " " + statement);
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
        String path1 = bootstrapping.getString("metajava.path");
        String path2 = FilUtils.fixPath(path1);
        String path3 = FilUtils.slashedPath(path2);
        return path3;
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
            return executeQuery(null);
        }

        public ResultSet executeQuery(Object[] args) throws SQLException {
            int n = args == null ? 0 : args.length;
            if (n > 0) {
                for (int i = 0; i < n; i++) {
                    if (args[i] == null) {
//                      _preparedStatement.setNull(i + 1, java.sql.Types.OTHER);
                        _preparedStatement.setNull(i + 1, java.sql.Types.NULL);
                    } else {
                        _preparedStatement.setObject(i + 1, args[i]);
                    }
                }
            }
            return _preparedStatement.executeQuery();
        }

        @Override
        public String toString() {
            return _statement;
        }

    }

}
