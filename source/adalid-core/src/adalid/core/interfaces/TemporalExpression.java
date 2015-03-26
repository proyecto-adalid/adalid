/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.interfaces;

import adalid.core.enums.SpecialTemporalValue;
import adalid.core.expressions.BooleanComparisonX;
import adalid.core.expressions.CharacterScalarX;
import adalid.core.expressions.TemporalOrderedPairX;
import adalid.core.expressions.TemporalScalarX;

/**
 * @author Jorge Campins
 */
public interface TemporalExpression extends Expression {

    BooleanComparisonX isNull();

    BooleanComparisonX isNotNull();

    BooleanComparisonX isEqualTo(java.util.Date y);

    BooleanComparisonX isEqualTo(SpecialTemporalValue y);

    BooleanComparisonX isEqualTo(TemporalExpression y);

    BooleanComparisonX isNotEqualTo(java.util.Date y);

    BooleanComparisonX isNotEqualTo(SpecialTemporalValue y);

    BooleanComparisonX isNotEqualTo(TemporalExpression y);

    BooleanComparisonX isGreaterThan(java.util.Date y);

    BooleanComparisonX isGreaterThan(SpecialTemporalValue y);

    BooleanComparisonX isGreaterThan(TemporalExpression y);

    BooleanComparisonX isGreaterOrEqualTo(java.util.Date y);

    BooleanComparisonX isGreaterOrEqualTo(SpecialTemporalValue y);

    BooleanComparisonX isGreaterOrEqualTo(TemporalExpression y);

    BooleanComparisonX isLessThan(java.util.Date y);

    BooleanComparisonX isLessThan(SpecialTemporalValue y);

    BooleanComparisonX isLessThan(TemporalExpression y);

    BooleanComparisonX isLessOrEqualTo(java.util.Date y);

    BooleanComparisonX isLessOrEqualTo(SpecialTemporalValue y);

    BooleanComparisonX isLessOrEqualTo(TemporalExpression y);

    BooleanComparisonX isNullOrEqualTo(java.util.Date y);

    BooleanComparisonX isNullOrEqualTo(SpecialTemporalValue y);

    BooleanComparisonX isNullOrEqualTo(TemporalExpression y);

    BooleanComparisonX isNullOrNotEqualTo(java.util.Date y);

    BooleanComparisonX isNullOrNotEqualTo(SpecialTemporalValue y);

    BooleanComparisonX isNullOrNotEqualTo(TemporalExpression y);

    BooleanComparisonX isNullOrGreaterThan(java.util.Date y);

    BooleanComparisonX isNullOrGreaterThan(SpecialTemporalValue y);

    BooleanComparisonX isNullOrGreaterThan(TemporalExpression y);

    BooleanComparisonX isNullOrGreaterOrEqualTo(java.util.Date y);

    BooleanComparisonX isNullOrGreaterOrEqualTo(SpecialTemporalValue y);

    BooleanComparisonX isNullOrGreaterOrEqualTo(TemporalExpression y);

    BooleanComparisonX isNullOrLessThan(java.util.Date y);

    BooleanComparisonX isNullOrLessThan(SpecialTemporalValue y);

    BooleanComparisonX isNullOrLessThan(TemporalExpression y);

    BooleanComparisonX isNullOrLessOrEqualTo(java.util.Date y);

    BooleanComparisonX isNullOrLessOrEqualTo(SpecialTemporalValue y);

    BooleanComparisonX isNullOrLessOrEqualTo(TemporalExpression y);

    TemporalOrderedPairX coalesce(java.util.Date y);

    TemporalOrderedPairX coalesce(SpecialTemporalValue y);

    TemporalOrderedPairX coalesce(TemporalExpression y);

    TemporalOrderedPairX nullIf(java.util.Date y);

    TemporalOrderedPairX nullIf(SpecialTemporalValue y);

    TemporalOrderedPairX nullIf(TemporalExpression y);

    TemporalOrderedPairX max(java.util.Date y);

    TemporalOrderedPairX max(SpecialTemporalValue y);

    TemporalOrderedPairX max(TemporalExpression y);

    TemporalOrderedPairX min(java.util.Date y);

    TemporalOrderedPairX min(SpecialTemporalValue y);

    TemporalOrderedPairX min(TemporalExpression y);

    TemporalScalarX defaultWhenNull();

    TemporalScalarX nullWhenDefault();

    TemporalScalarX toDate();

    TemporalScalarX toTime();

    TemporalScalarX toTimestamp();

    CharacterScalarX toCharString();

}
