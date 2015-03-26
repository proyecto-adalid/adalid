/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.interfaces;

import adalid.core.enums.SpecialNumericValue;
import adalid.core.expressions.BooleanComparisonX;
import adalid.core.expressions.CharacterScalarX;
import adalid.core.expressions.NumericOrderedPairX;
import adalid.core.expressions.NumericScalarX;

/**
 * @author Jorge Campins
 */
public interface NumericExpression extends Expression {

    BooleanComparisonX isNull();

    BooleanComparisonX isNotNull();

    BooleanComparisonX isEqualTo(Number y);

    BooleanComparisonX isEqualTo(SpecialNumericValue y);

    BooleanComparisonX isEqualTo(NumericExpression y);

    BooleanComparisonX isNotEqualTo(Number y);

    BooleanComparisonX isNotEqualTo(SpecialNumericValue y);

    BooleanComparisonX isNotEqualTo(NumericExpression y);

    BooleanComparisonX isGreaterThan(Number y);

    BooleanComparisonX isGreaterThan(SpecialNumericValue y);

    BooleanComparisonX isGreaterThan(NumericExpression y);

    BooleanComparisonX isGreaterOrEqualTo(Number y);

    BooleanComparisonX isGreaterOrEqualTo(SpecialNumericValue y);

    BooleanComparisonX isGreaterOrEqualTo(NumericExpression y);

    BooleanComparisonX isLessThan(Number y);

    BooleanComparisonX isLessThan(SpecialNumericValue y);

    BooleanComparisonX isLessThan(NumericExpression y);

    BooleanComparisonX isLessOrEqualTo(Number y);

    BooleanComparisonX isLessOrEqualTo(SpecialNumericValue y);

    BooleanComparisonX isLessOrEqualTo(NumericExpression y);

    BooleanComparisonX isNullOrEqualTo(Number y);

    BooleanComparisonX isNullOrEqualTo(SpecialNumericValue y);

    BooleanComparisonX isNullOrEqualTo(NumericExpression y);

    BooleanComparisonX isNullOrNotEqualTo(Number y);

    BooleanComparisonX isNullOrNotEqualTo(SpecialNumericValue y);

    BooleanComparisonX isNullOrNotEqualTo(NumericExpression y);

    BooleanComparisonX isNullOrGreaterThan(Number y);

    BooleanComparisonX isNullOrGreaterThan(SpecialNumericValue y);

    BooleanComparisonX isNullOrGreaterThan(NumericExpression y);

    BooleanComparisonX isNullOrGreaterOrEqualTo(Number y);

    BooleanComparisonX isNullOrGreaterOrEqualTo(SpecialNumericValue y);

    BooleanComparisonX isNullOrGreaterOrEqualTo(NumericExpression y);

    BooleanComparisonX isNullOrLessThan(Number y);

    BooleanComparisonX isNullOrLessThan(SpecialNumericValue y);

    BooleanComparisonX isNullOrLessThan(NumericExpression y);

    BooleanComparisonX isNullOrLessOrEqualTo(Number y);

    BooleanComparisonX isNullOrLessOrEqualTo(SpecialNumericValue y);

    BooleanComparisonX isNullOrLessOrEqualTo(NumericExpression y);

    NumericOrderedPairX coalesce(Number y);

    NumericOrderedPairX coalesce(SpecialNumericValue y);

    NumericOrderedPairX coalesce(NumericExpression y);

    NumericOrderedPairX nullIf(Number y);

    NumericOrderedPairX nullIf(SpecialNumericValue y);

    NumericOrderedPairX nullIf(NumericExpression y);

    NumericOrderedPairX max(Number y);

    NumericOrderedPairX max(SpecialNumericValue y);

    NumericOrderedPairX max(NumericExpression y);

    NumericOrderedPairX min(Number y);

    NumericOrderedPairX min(SpecialNumericValue y);

    NumericOrderedPairX min(NumericExpression y);

    NumericOrderedPairX plus(Number y);

    NumericOrderedPairX plus(SpecialNumericValue y);

    NumericOrderedPairX plus(NumericExpression y);

    NumericOrderedPairX minus(Number y);

    NumericOrderedPairX minus(SpecialNumericValue y);

    NumericOrderedPairX minus(NumericExpression y);

    NumericOrderedPairX times(Number y);

    NumericOrderedPairX times(SpecialNumericValue y);

    NumericOrderedPairX times(NumericExpression y);

    NumericOrderedPairX over(Number y);

    NumericOrderedPairX over(SpecialNumericValue y);

    NumericOrderedPairX over(NumericExpression y);

    NumericOrderedPairX toThe(Number y);

    NumericOrderedPairX toThe(SpecialNumericValue y);

    NumericOrderedPairX toThe(NumericExpression y);

    NumericScalarX defaultWhenNull();

    NumericScalarX nullWhenDefault();

    NumericScalarX abs();

    NumericScalarX chs();

    NumericScalarX inv();

    NumericScalarX toBigDecimal();

    NumericScalarX toBigInteger();

    NumericScalarX toByte();

    NumericScalarX toShort();

    NumericScalarX toInteger();

    NumericScalarX toLong();

    NumericScalarX toDouble();

    NumericScalarX toFloat();

    CharacterScalarX toCharString();

}
