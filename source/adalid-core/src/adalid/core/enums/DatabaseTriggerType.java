/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.enums;

/**
 * @author Jorge Campins
 */
public enum DatabaseTriggerType {

    BEFORE_INSERT_ROW("before", "insert", "row", "new", "bir", "bis", "bix"),
    BEFORE_UPDATE_ROW("before", "update", "row", "new", "bur", "bus", "bux"),
    BEFORE_DELETE_ROW("before", "delete", "row", "old", "bdr", "bds", "bdx"),
    AFTER_INSERT_ROW("after", "insert", "row", "null", "air", "ais", "aix"),
    AFTER_UPDATE_ROW("after", "update", "row", "null", "aur", "aus", "aux"),
    AFTER_DELETE_ROW("after", "delete", "row", "null", "adr", "ads", "adx");

    private final String _adverb;

    private final String _verb;

    private final String _level;

    private final String _variable;

    private final String _acronym;

    private final String _statementAcronym;

    private final String _extensionAcronym;

    private DatabaseTriggerType(String adverb, String verb, String level, String variable, String acronym, String statement, String extension) {
        _adverb = adverb;
        _verb = verb;
        _level = level;
        _variable = variable;
        _acronym = acronym;
        _statementAcronym = statement;
        _extensionAcronym = extension;
    }

    public String getAdverb() {
        return _adverb;
    }

    public String getVerb() {
        return _verb;
    }

    public String getLevel() {
        return _level;
    }

    public String getReturnVariable() {
        return _variable;
    }

    public String getAcronym() {
        return _acronym;
    }

    public String getStatementAcronym() {
        return _statementAcronym;
    }

    public String getExtensionAcronym() {
        return _extensionAcronym;
    }

}
