/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.expressions;

import adalid.core.enums.SpecialCharacterValue;
import adalid.core.interfaces.CharacterExpression;
import adalid.core.interfaces.ConditionalX;

/**
 * @author Jorge Campins
 */
public class CharacterOtherwiseX extends AbstractConditionalX implements CharacterExpression {

    CharacterOtherwiseX(ConditionalX conditional, Object value) {
        super(conditional, value);
    }

    // <editor-fold defaultstate="collapsed" desc="CharacterExpression">
    @Override
    public BooleanComparisonX isNull() {
        return XB.Character.Comparison.isNull(this);
    }

    @Override
    public BooleanComparisonX isNotNull() {
        return XB.Character.Comparison.isNotNull(this);
    }

    @Override
    public BooleanComparisonX isEqualTo(String y) {
        return XB.Character.Comparison.isEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isEqualTo(SpecialCharacterValue y) {
        return XB.Character.Comparison.isEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isEqualTo(CharacterExpression y) {
        return XB.Character.Comparison.isEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNotEqualTo(String y) {
        return XB.Character.Comparison.isNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNotEqualTo(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNotEqualTo(CharacterExpression y) {
        return XB.Character.Comparison.isNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isGreaterThan(String y) {
        return XB.Character.Comparison.isGreaterThan(this, y);
    }

    @Override
    public BooleanComparisonX isGreaterThan(SpecialCharacterValue y) {
        return XB.Character.Comparison.isGreaterThan(this, y);
    }

    @Override
    public BooleanComparisonX isGreaterThan(CharacterExpression y) {
        return XB.Character.Comparison.isGreaterThan(this, y);
    }

    @Override
    public BooleanComparisonX isGreaterOrEqualTo(String y) {
        return XB.Character.Comparison.isGreaterOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isGreaterOrEqualTo(SpecialCharacterValue y) {
        return XB.Character.Comparison.isGreaterOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isGreaterOrEqualTo(CharacterExpression y) {
        return XB.Character.Comparison.isGreaterOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isLessThan(String y) {
        return XB.Character.Comparison.isLessThan(this, y);
    }

    @Override
    public BooleanComparisonX isLessThan(SpecialCharacterValue y) {
        return XB.Character.Comparison.isLessThan(this, y);
    }

    @Override
    public BooleanComparisonX isLessThan(CharacterExpression y) {
        return XB.Character.Comparison.isLessThan(this, y);
    }

    @Override
    public BooleanComparisonX isLessOrEqualTo(String y) {
        return XB.Character.Comparison.isLessOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isLessOrEqualTo(SpecialCharacterValue y) {
        return XB.Character.Comparison.isLessOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isLessOrEqualTo(CharacterExpression y) {
        return XB.Character.Comparison.isLessOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX startsWith(String y) {
        return XB.Character.Comparison.startsWith(this, y);
    }

    @Override
    public BooleanComparisonX startsWith(SpecialCharacterValue y) {
        return XB.Character.Comparison.startsWith(this, y);
    }

    @Override
    public BooleanComparisonX startsWith(CharacterExpression y) {
        return XB.Character.Comparison.startsWith(this, y);
    }

    @Override
    public BooleanComparisonX notStartsWith(String y) {
        return XB.Character.Comparison.notStartsWith(this, y);
    }

    @Override
    public BooleanComparisonX notStartsWith(SpecialCharacterValue y) {
        return XB.Character.Comparison.notStartsWith(this, y);
    }

    @Override
    public BooleanComparisonX notStartsWith(CharacterExpression y) {
        return XB.Character.Comparison.notStartsWith(this, y);
    }

    @Override
    public BooleanComparisonX contains(String y) {
        return XB.Character.Comparison.contains(this, y);
    }

    @Override
    public BooleanComparisonX contains(SpecialCharacterValue y) {
        return XB.Character.Comparison.contains(this, y);
    }

    @Override
    public BooleanComparisonX contains(CharacterExpression y) {
        return XB.Character.Comparison.contains(this, y);
    }

    @Override
    public BooleanComparisonX notContains(String y) {
        return XB.Character.Comparison.notContains(this, y);
    }

    @Override
    public BooleanComparisonX notContains(SpecialCharacterValue y) {
        return XB.Character.Comparison.notContains(this, y);
    }

    @Override
    public BooleanComparisonX notContains(CharacterExpression y) {
        return XB.Character.Comparison.notContains(this, y);
    }

    @Override
    public BooleanComparisonX endsWith(String y) {
        return XB.Character.Comparison.endsWith(this, y);
    }

    @Override
    public BooleanComparisonX endsWith(SpecialCharacterValue y) {
        return XB.Character.Comparison.endsWith(this, y);
    }

    @Override
    public BooleanComparisonX endsWith(CharacterExpression y) {
        return XB.Character.Comparison.endsWith(this, y);
    }

    @Override
    public BooleanComparisonX notEndsWith(String y) {
        return XB.Character.Comparison.notEndsWith(this, y);
    }

    @Override
    public BooleanComparisonX notEndsWith(SpecialCharacterValue y) {
        return XB.Character.Comparison.notEndsWith(this, y);
    }

    @Override
    public BooleanComparisonX notEndsWith(CharacterExpression y) {
        return XB.Character.Comparison.notEndsWith(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrEqualTo(String y) {
        return XB.Character.Comparison.isNullOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrEqualTo(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNullOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrEqualTo(CharacterExpression y) {
        return XB.Character.Comparison.isNullOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEqualTo(String y) {
        return XB.Character.Comparison.isNullOrNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEqualTo(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNullOrNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEqualTo(CharacterExpression y) {
        return XB.Character.Comparison.isNullOrNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrGreaterThan(String y) {
        return XB.Character.Comparison.isNullOrGreaterThan(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrGreaterThan(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNullOrGreaterThan(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrGreaterThan(CharacterExpression y) {
        return XB.Character.Comparison.isNullOrGreaterThan(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrGreaterOrEqualTo(String y) {
        return XB.Character.Comparison.isNullOrGreaterOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrGreaterOrEqualTo(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNullOrGreaterOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrGreaterOrEqualTo(CharacterExpression y) {
        return XB.Character.Comparison.isNullOrGreaterOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrLessThan(String y) {
        return XB.Character.Comparison.isNullOrLessThan(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrLessThan(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNullOrLessThan(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrLessThan(CharacterExpression y) {
        return XB.Character.Comparison.isNullOrLessThan(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrLessOrEqualTo(String y) {
        return XB.Character.Comparison.isNullOrLessOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrLessOrEqualTo(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNullOrLessOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrLessOrEqualTo(CharacterExpression y) {
        return XB.Character.Comparison.isNullOrLessOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrStartsWith(String y) {
        return XB.Character.Comparison.isNullOrStartsWith(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrStartsWith(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNullOrStartsWith(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrStartsWith(CharacterExpression y) {
        return XB.Character.Comparison.isNullOrStartsWith(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotStartsWith(String y) {
        return XB.Character.Comparison.isNullOrNotStartsWith(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotStartsWith(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNullOrNotStartsWith(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotStartsWith(CharacterExpression y) {
        return XB.Character.Comparison.isNullOrNotStartsWith(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrContains(String y) {
        return XB.Character.Comparison.isNullOrContains(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrContains(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNullOrContains(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrContains(CharacterExpression y) {
        return XB.Character.Comparison.isNullOrContains(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotContains(String y) {
        return XB.Character.Comparison.isNullOrNotContains(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotContains(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNullOrNotContains(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotContains(CharacterExpression y) {
        return XB.Character.Comparison.isNullOrNotContains(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrEndsWith(String y) {
        return XB.Character.Comparison.isNullOrEndsWith(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrEndsWith(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNullOrEndsWith(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrEndsWith(CharacterExpression y) {
        return XB.Character.Comparison.isNullOrEndsWith(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEndsWith(String y) {
        return XB.Character.Comparison.isNullOrNotEndsWith(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEndsWith(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNullOrNotEndsWith(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEndsWith(CharacterExpression y) {
        return XB.Character.Comparison.isNullOrNotEndsWith(this, y);
    }

    @Override
    public CharacterOrderedPairX coalesce(String y) {
        return XB.Character.OrderedPair.coalesce(this, y);
    }

    @Override
    public CharacterOrderedPairX coalesce(SpecialCharacterValue y) {
        return XB.Character.OrderedPair.coalesce(this, y);
    }

    @Override
    public CharacterOrderedPairX coalesce(CharacterExpression y) {
        return XB.Character.OrderedPair.coalesce(this, y);
    }

    @Override
    public CharacterOrderedPairX nullIf(String y) {
        return XB.Character.OrderedPair.nullIf(this, y);
    }

    @Override
    public CharacterOrderedPairX nullIf(SpecialCharacterValue y) {
        return XB.Character.OrderedPair.nullIf(this, y);
    }

    @Override
    public CharacterOrderedPairX nullIf(CharacterExpression y) {
        return XB.Character.OrderedPair.nullIf(this, y);
    }

    @Override
    public CharacterOrderedPairX max(String y) {
        return XB.Character.OrderedPair.max(this, y);
    }

    @Override
    public CharacterOrderedPairX max(SpecialCharacterValue y) {
        return XB.Character.OrderedPair.max(this, y);
    }

    @Override
    public CharacterOrderedPairX max(CharacterExpression y) {
        return XB.Character.OrderedPair.max(this, y);
    }

    @Override
    public CharacterOrderedPairX min(String y) {
        return XB.Character.OrderedPair.min(this, y);
    }

    @Override
    public CharacterOrderedPairX min(SpecialCharacterValue y) {
        return XB.Character.OrderedPair.min(this, y);
    }

    @Override
    public CharacterOrderedPairX min(CharacterExpression y) {
        return XB.Character.OrderedPair.min(this, y);
    }

    @Override
    public CharacterOrderedPairX concat(String y) {
        return XB.Character.OrderedPair.concat(this, y);
    }

    @Override
    public CharacterOrderedPairX concat(SpecialCharacterValue y) {
        return XB.Character.OrderedPair.concat(this, y);
    }

    @Override
    public CharacterOrderedPairX concat(CharacterExpression y) {
        return XB.Character.OrderedPair.concat(this, y);
    }

    @Override
    public CharacterScalarX defaultWhenNull() {
        return XB.Character.Scalar.defaultWhenNull(this);
    }

    @Override
    public CharacterScalarX nullWhenDefault() {
        return XB.Character.Scalar.nullWhenDefault(this);
    }

    @Override
    public CharacterScalarX lower() {
        return XB.Character.Scalar.lower(this);
    }

    @Override
    public CharacterScalarX upper() {
        return XB.Character.Scalar.upper(this);
    }

    @Override
    public CharacterScalarX capitalize() {
        return XB.Character.Scalar.capitalize(this);
    }

    @Override
    public CharacterScalarX uncapitalize() {
        return XB.Character.Scalar.uncapitalize(this);
    }

    @Override
    public CharacterScalarX trim() {
        return XB.Character.Scalar.trim(this);
    }

    @Override
    public CharacterScalarX ltrim() {
        return XB.Character.Scalar.ltrim(this);
    }

    @Override
    public CharacterScalarX rtrim() {
        return XB.Character.Scalar.rtrim(this);
    }

    @Override
    public CharacterScalarX toChar() {
        return XB.Character.Scalar.toChar(this);
    }

    @Override
    public CharacterScalarX toCharString() {
        return XB.Character.Scalar.toCharString(this);
    }
    // </editor-fold>

}
