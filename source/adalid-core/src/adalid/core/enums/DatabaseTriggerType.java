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

    BEFORE_INSERT_ROW("before", "insert", "row", "new"),
    BEFORE_UPDATE_ROW("before", "update", "row", "new"),
    BEFORE_DELETE_ROW("before", "delete", "row", "old"),
    AFTER_INSERT_ROW("after", "insert", "row", "null"),
    AFTER_UPDATE_ROW("after", "update", "row", "null"),
    AFTER_DELETE_ROW("after", "delete", "row", "null");

    private String _adverb;

    private String _verb;

    private String _level;

    private String _variable;

    DatabaseTriggerType(String adverb, String verb, String level, String variable) {
        _adverb = adverb;
        _verb = verb;
        _level = level;
        _variable = variable;
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

}
