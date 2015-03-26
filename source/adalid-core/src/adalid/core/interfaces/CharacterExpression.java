/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.interfaces;

import adalid.core.enums.SpecialCharacterValue;
import adalid.core.expressions.BooleanComparisonX;
import adalid.core.expressions.CharacterOrderedPairX;
import adalid.core.expressions.CharacterScalarX;

/**
 * @author Jorge Campins
 */
public interface CharacterExpression extends Expression {

    BooleanComparisonX isNull();

    BooleanComparisonX isNotNull();

    BooleanComparisonX isEqualTo(String y);

    BooleanComparisonX isEqualTo(SpecialCharacterValue y);

    BooleanComparisonX isEqualTo(CharacterExpression y);

    BooleanComparisonX isNotEqualTo(String y);

    BooleanComparisonX isNotEqualTo(SpecialCharacterValue y);

    BooleanComparisonX isNotEqualTo(CharacterExpression y);

    BooleanComparisonX isGreaterThan(String y);

    BooleanComparisonX isGreaterThan(SpecialCharacterValue y);

    BooleanComparisonX isGreaterThan(CharacterExpression y);

    BooleanComparisonX isGreaterOrEqualTo(String y);

    BooleanComparisonX isGreaterOrEqualTo(SpecialCharacterValue y);

    BooleanComparisonX isGreaterOrEqualTo(CharacterExpression y);

    BooleanComparisonX isLessThan(String y);

    BooleanComparisonX isLessThan(SpecialCharacterValue y);

    BooleanComparisonX isLessThan(CharacterExpression y);

    BooleanComparisonX isLessOrEqualTo(String y);

    BooleanComparisonX isLessOrEqualTo(SpecialCharacterValue y);

    BooleanComparisonX isLessOrEqualTo(CharacterExpression y);

    BooleanComparisonX startsWith(String y);

    BooleanComparisonX startsWith(SpecialCharacterValue y);

    BooleanComparisonX startsWith(CharacterExpression y);

    BooleanComparisonX notStartsWith(String y);

    BooleanComparisonX notStartsWith(SpecialCharacterValue y);

    BooleanComparisonX notStartsWith(CharacterExpression y);

    BooleanComparisonX contains(String y);

    BooleanComparisonX contains(SpecialCharacterValue y);

    BooleanComparisonX contains(CharacterExpression y);

    BooleanComparisonX notContains(String y);

    BooleanComparisonX notContains(SpecialCharacterValue y);

    BooleanComparisonX notContains(CharacterExpression y);

    BooleanComparisonX endsWith(String y);

    BooleanComparisonX endsWith(SpecialCharacterValue y);

    BooleanComparisonX endsWith(CharacterExpression y);

    BooleanComparisonX notEndsWith(String y);

    BooleanComparisonX notEndsWith(SpecialCharacterValue y);

    BooleanComparisonX notEndsWith(CharacterExpression y);

    BooleanComparisonX isNullOrEqualTo(String y);

    BooleanComparisonX isNullOrEqualTo(SpecialCharacterValue y);

    BooleanComparisonX isNullOrEqualTo(CharacterExpression y);

    BooleanComparisonX isNullOrNotEqualTo(String y);

    BooleanComparisonX isNullOrNotEqualTo(SpecialCharacterValue y);

    BooleanComparisonX isNullOrNotEqualTo(CharacterExpression y);

    BooleanComparisonX isNullOrGreaterThan(String y);

    BooleanComparisonX isNullOrGreaterThan(SpecialCharacterValue y);

    BooleanComparisonX isNullOrGreaterThan(CharacterExpression y);

    BooleanComparisonX isNullOrGreaterOrEqualTo(String y);

    BooleanComparisonX isNullOrGreaterOrEqualTo(SpecialCharacterValue y);

    BooleanComparisonX isNullOrGreaterOrEqualTo(CharacterExpression y);

    BooleanComparisonX isNullOrLessThan(String y);

    BooleanComparisonX isNullOrLessThan(SpecialCharacterValue y);

    BooleanComparisonX isNullOrLessThan(CharacterExpression y);

    BooleanComparisonX isNullOrLessOrEqualTo(String y);

    BooleanComparisonX isNullOrLessOrEqualTo(SpecialCharacterValue y);

    BooleanComparisonX isNullOrLessOrEqualTo(CharacterExpression y);

    BooleanComparisonX isNullOrStartsWith(String y);

    BooleanComparisonX isNullOrStartsWith(SpecialCharacterValue y);

    BooleanComparisonX isNullOrStartsWith(CharacterExpression y);

    BooleanComparisonX isNullOrNotStartsWith(String y);

    BooleanComparisonX isNullOrNotStartsWith(SpecialCharacterValue y);

    BooleanComparisonX isNullOrNotStartsWith(CharacterExpression y);

    BooleanComparisonX isNullOrContains(String y);

    BooleanComparisonX isNullOrContains(SpecialCharacterValue y);

    BooleanComparisonX isNullOrContains(CharacterExpression y);

    BooleanComparisonX isNullOrNotContains(String y);

    BooleanComparisonX isNullOrNotContains(SpecialCharacterValue y);

    BooleanComparisonX isNullOrNotContains(CharacterExpression y);

    BooleanComparisonX isNullOrEndsWith(String y);

    BooleanComparisonX isNullOrEndsWith(SpecialCharacterValue y);

    BooleanComparisonX isNullOrEndsWith(CharacterExpression y);

    BooleanComparisonX isNullOrNotEndsWith(String y);

    BooleanComparisonX isNullOrNotEndsWith(SpecialCharacterValue y);

    BooleanComparisonX isNullOrNotEndsWith(CharacterExpression y);

    CharacterOrderedPairX coalesce(String y);

    CharacterOrderedPairX coalesce(SpecialCharacterValue y);

    CharacterOrderedPairX coalesce(CharacterExpression y);

    CharacterOrderedPairX nullIf(String y);

    CharacterOrderedPairX nullIf(SpecialCharacterValue y);

    CharacterOrderedPairX nullIf(CharacterExpression y);

    CharacterOrderedPairX max(String y);

    CharacterOrderedPairX max(SpecialCharacterValue y);

    CharacterOrderedPairX max(CharacterExpression y);

    CharacterOrderedPairX min(String y);

    CharacterOrderedPairX min(SpecialCharacterValue y);

    CharacterOrderedPairX min(CharacterExpression y);

    CharacterOrderedPairX concat(String y);

    CharacterOrderedPairX concat(SpecialCharacterValue y);

    CharacterOrderedPairX concat(CharacterExpression y);

    CharacterScalarX defaultWhenNull();

    CharacterScalarX nullWhenDefault();

    CharacterScalarX lower();

    CharacterScalarX upper();

    CharacterScalarX capitalize();

    CharacterScalarX uncapitalize();

    CharacterScalarX trim();

    CharacterScalarX ltrim();

    CharacterScalarX rtrim();

    CharacterScalarX toChar();

    CharacterScalarX toCharString();

}
