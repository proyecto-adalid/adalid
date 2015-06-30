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

    BEFORE_INSERT_ROW("before", "insert", "row", "new", "bir", "bix"),
    BEFORE_UPDATE_ROW("before", "update", "row", "new", "bur", "bux"),
    BEFORE_DELETE_ROW("before", "delete", "row", "old", "bdr", "bdx"),
    AFTER_INSERT_ROW("after", "insert", "row", "null", "air", "aix"),
    AFTER_UPDATE_ROW("after", "update", "row", "null", "aur", "aux"),
    AFTER_DELETE_ROW("after", "delete", "row", "null", "adr", "adx");

    private final String _adverb;

    private final String _verb;

    private final String _level;

    private final String _variable;

    private final String _acronym;

    private final String _anonym;

    private DatabaseTriggerType(String adverb, String verb, String level, String variable, String acronym, String anonym) {
        _adverb = adverb;
        _verb = verb;
        _level = level;
        _variable = variable;
        _acronym = acronym;
        _anonym = anonym;
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

    public String getAnonym() {
        return _anonym;
    }

}
