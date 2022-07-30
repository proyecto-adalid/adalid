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
package adalid.core.expressions;

import adalid.core.*;
import adalid.core.enums.*;
import adalid.core.exceptions.*;
import adalid.core.interfaces.*;
import adalid.core.sql.*;

/**
 * @author Jorge Campins
 */
public class XB {

    public static EntityExpressionBuilder Entity;

    public static BooleanExpressionBuilder Boolean;

    public static CharacterExpressionBuilder Character;

    public static NumericExpressionBuilder Numeric;

    public static TemporalExpressionBuilder Temporal;

    public static AnyTypeExpressionBuilder AnyType;

    static XB instance = new XB();

    private XB() {
        Entity = new EntityExpressionBuilder();
        Boolean = new BooleanExpressionBuilder();
        Character = new CharacterExpressionBuilder();
        Numeric = new NumericExpressionBuilder();
        Temporal = new TemporalExpressionBuilder();
        AnyType = new AnyTypeExpressionBuilder();
    }

    // <editor-fold defaultstate="collapsed" desc="aggregate">
    @SuppressWarnings("unchecked")
    private <T> Object[] aggregate(T operand1, T operand2, T... extraOperands) {
        int l = 0;
        if (operand1 != null) {
            l++;
        }
        if (operand2 != null) {
            l++;
        }
        int xol = extraOperands == null ? 0 : extraOperands.length;
        for (int i = 0; i < xol; i++) {
            if (extraOperands[i] != null) {
                l++;
            }
        }
        if (l < 2) {
            String errmsg = "failed to initialise aggregate expression; less than 2 non-null operands found";
            throw new InstantiationRuntimeException(errmsg);
        }
        Object[] operands = new Object[l];
        int i = 0;
        if (operand1 != null) {
            operands[i++] = operand1;
        }
        if (operand2 != null) {
            operands[i++] = operand2;
        }
        for (int j = 0; j < xol; j++) {
            if (extraOperands[j] != null) {
                operands[i + j] = extraOperands[j];
            }
        }
        return operands;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Entity">
    public class EntityExpressionBuilder {

        private final Class<? extends VariantX> EntityX = EntityX.class;

        public EntityExpression getForeignExpression(String name, Class<? extends Entity> clazz) {
            return (EntityExpression) XS2.getForeignExpression(EntityX, name, clazz);
        }

        public Comparison Comparison = new Comparison();

        public Conditional Conditional = new Conditional();

        public DataAggregate DataAggregate = new DataAggregate();

        public OrderedPair OrderedPair = new OrderedPair();

        public RowsAggregate RowsAggregate = new RowsAggregate();

        public Scalar Scalar = new Scalar();

        // <editor-fold defaultstate="collapsed" desc="Comparison">
        public class Comparison {

            public BooleanComparisonX isNull(Entity x) {
                return x == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL);
            }

            public BooleanComparisonX isNotNull(Entity x) {
                return x == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NOT_NULL);
            }

            public BooleanComparisonX isEqualTo(Entity x, Entity y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.EQ, y);
            }

            public BooleanComparisonX isEqualTo(Entity x, Instance y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.EQ, y);
            }

            public BooleanComparisonX isEqualTo(Entity x, EntityExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.EQ, y);
            }

            /*
            public BooleanComparisonX isEqualTo(Entity x, SpecialEntityValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.EQ, y);
            }

            /**/
            public BooleanComparisonX isNotEqualTo(Entity x, Entity y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NEQ, y);
            }

            public BooleanComparisonX isNotEqualTo(Entity x, Instance y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NEQ, y);
            }

            public BooleanComparisonX isNotEqualTo(Entity x, EntityExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NEQ, y);
            }

            /*
            public BooleanComparisonX isNotEqualTo(Entity x, SpecialEntityValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NEQ, y);
            }

            /**/
            public BooleanComparisonX isNullOrEqualTo(Entity x, Entity y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_EQ, y);
            }

            public BooleanComparisonX isNullOrEqualTo(Entity x, Instance y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_EQ, y);
            }

            public BooleanComparisonX isNullOrEqualTo(Entity x, EntityExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_EQ, y);
            }

            /*
            public BooleanComparisonX isNullOrEqualTo(Entity x, SpecialEntityValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_EQ, y);
            }

            /**/
            public BooleanComparisonX isNullOrNotEqualTo(Entity x, Entity y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NEQ, y);
            }

            public BooleanComparisonX isNullOrNotEqualTo(Entity x, Instance y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NEQ, y);
            }

            public BooleanComparisonX isNullOrNotEqualTo(Entity x, EntityExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NEQ, y);
            }

            /*
            public BooleanComparisonX isNullOrNotEqualTo(Entity x, SpecialEntityValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NEQ, y);
            }

            /**/
            public BooleanComparisonX isNull(EntityExpression x) {
                return x == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL);
            }

            public BooleanComparisonX isNotNull(EntityExpression x) {
                return x == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NOT_NULL);
            }

            public BooleanComparisonX isEqualTo(EntityExpression x, Entity y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.EQ, y);
            }

            public BooleanComparisonX isEqualTo(EntityExpression x, Instance y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.EQ, y);
            }

            public BooleanComparisonX isEqualTo(EntityExpression x, EntityExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.EQ, y);
            }

            /*
            public BooleanComparisonX isEqualTo(EntityExpression x, SpecialEntityValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.EQ, y);
            }

            /**/
            public BooleanComparisonX isNotEqualTo(EntityExpression x, Entity y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NEQ, y);
            }

            public BooleanComparisonX isNotEqualTo(EntityExpression x, Instance y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NEQ, y);
            }

            public BooleanComparisonX isNotEqualTo(EntityExpression x, EntityExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NEQ, y);
            }

            /*
            public BooleanComparisonX isNotEqualTo(EntityExpression x, SpecialEntityValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NEQ, y);
            }

            /**/
            public BooleanComparisonX isNullOrEqualTo(EntityExpression x, Entity y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_EQ, y);
            }

            public BooleanComparisonX isNullOrEqualTo(EntityExpression x, Instance y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_EQ, y);
            }

            public BooleanComparisonX isNullOrEqualTo(EntityExpression x, EntityExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_EQ, y);
            }

            /*
            public BooleanComparisonX isNullOrEqualTo(EntityExpression x, SpecialEntityValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_EQ, y);
            }

            /**/
            public BooleanComparisonX isNullOrNotEqualTo(EntityExpression x, Entity y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NEQ, y);
            }

            public BooleanComparisonX isNullOrNotEqualTo(EntityExpression x, Instance y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NEQ, y);
            }

            public BooleanComparisonX isNullOrNotEqualTo(EntityExpression x, EntityExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NEQ, y);
            }

            /*
            public BooleanComparisonX isNullOrNotEqualTo(EntityExpression x, SpecialEntityValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NEQ, y);
            }
            /**/
            public BooleanComparisonX isIn(EntityReference x, EntityReference... y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_IN, y);
            }

            public BooleanComparisonX isIn(EntityReference x, Instance... y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_IN, y);
            }

            public BooleanComparisonX isIn(EntityReference x, NativeQuery y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_IN, y);
            }

            public BooleanComparisonX isNotIn(EntityReference x, EntityReference... y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NOT_IN, y);
            }

            public BooleanComparisonX isNotIn(EntityReference x, Instance... y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NOT_IN, y);
            }

            public BooleanComparisonX isNotIn(EntityReference x, NativeQuery y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NOT_IN, y);
            }

            public BooleanComparisonX isNullOrIn(EntityReference x, EntityReference... y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_IN, y);
            }

            public BooleanComparisonX isNullOrIn(EntityReference x, Instance... y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_IN, y);
            }

            public BooleanComparisonX isNullOrIn(EntityReference x, NativeQuery y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_IN, y);
            }

            public BooleanComparisonX isNullOrNotIn(EntityReference x, EntityReference... y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NOT_IN, y);
            }

            public BooleanComparisonX isNullOrNotIn(EntityReference x, Instance... y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NOT_IN, y);
            }

            public BooleanComparisonX isNullOrNotIn(EntityReference x, NativeQuery y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NOT_IN, y);
            }

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Conditional">
        public class Conditional {

            public EntityConditionalX then(BooleanExpression booleanExpression, Entity value) {
                return booleanExpression == null || value == null ? null
                    : new EntityConditionalX(booleanExpression, value);
            }

            public EntityConditionalX then(BooleanExpression booleanExpression, Instance value) {
                return booleanExpression == null || value == null ? null
                    : new EntityConditionalX(booleanExpression, value);
            }

            public EntityConditionalX then(BooleanExpression booleanExpression, SpecialEntityValue value) {
                return booleanExpression == null || value == null ? null
                    : new EntityConditionalX(booleanExpression, value);
            }

            public EntityConditionalX then(BooleanExpression booleanExpression, EntityExpression value) {
                return booleanExpression == null || value == null ? null
                    : new EntityConditionalX(booleanExpression, value);
            }

            public EntityOtherwiseX otherwise(EntityConditionalX conditional, Entity value) {
                return conditional == null || value == null ? null
                    : new EntityOtherwiseX((ConditionalX) conditional, value);
            }

            public EntityOtherwiseX otherwise(EntityConditionalX conditional, Instance value) {
                return conditional == null || value == null ? null
                    : new EntityOtherwiseX((ConditionalX) conditional, value);
            }

            public EntityOtherwiseX otherwise(EntityConditionalX conditional, SpecialEntityValue value) {
                return conditional == null || value == null ? null
                    : new EntityOtherwiseX((ConditionalX) conditional, value);
            }

            public EntityOtherwiseX otherwise(EntityConditionalX conditional, EntityExpression value) {
                return conditional == null || value == null ? null
                    : new EntityOtherwiseX((ConditionalX) conditional, value);
            }

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="DataAggregate">
        public class DataAggregate {

            public EntityDataAggregateX coalesce(Entity operand1, Entity operand2, Entity... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new EntityDataAggregateX(DataAggregateOp.COALESCE, operands);
            }

            public NumericDataAggregateX count(Entity operand1, Entity operand2, Entity... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new NumericDataAggregateX(DataAggregateOp.COUNT, operands);
            }

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="OrderedPair">
        public class OrderedPair {

            public EntityOrderedPairX coalesce(Entity x, Entity y) {
                return x == null || y == null ? null : new EntityOrderedPairX(x, OrderedPairOp.COALESCE, y);
            }

            public EntityOrderedPairX coalesce(Entity x, Instance y) {
                return x == null || y == null ? null : new EntityOrderedPairX(x, OrderedPairOp.COALESCE, y);
            }

            public EntityOrderedPairX coalesce(Entity x, EntityExpression y) {
                return x == null || y == null ? null : new EntityOrderedPairX(x, OrderedPairOp.COALESCE, y);
            }

            public EntityOrderedPairX coalesce(EntityExpression x, Entity y) {
                return x == null || y == null ? null : new EntityOrderedPairX(x, OrderedPairOp.COALESCE, y);
            }

            public EntityOrderedPairX coalesce(EntityExpression x, Instance y) {
                return x == null || y == null ? null : new EntityOrderedPairX(x, OrderedPairOp.COALESCE, y);
            }

            public EntityOrderedPairX coalesce(EntityExpression x, EntityExpression y) {
                return x == null || y == null ? null : new EntityOrderedPairX(x, OrderedPairOp.COALESCE, y);
            }

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="RowsAggregate">
        public class RowsAggregate {

            // <editor-fold defaultstate="collapsed" desc="rowcount">
            public NumericRowsAggregateX rowcount(Entity measure) {
                return measure == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.COUNT, measure);
            }

            public NumericRowsAggregateX rowcount(Entity measure, Segment filter) {
                return measure == null || filter == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.COUNT, measure, filter);
            }

            public NumericRowsAggregateX rowcount(Entity measure, Entity dimension) {
                return measure == null || dimension == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.COUNT, measure, dimension);
            }

            public NumericRowsAggregateX rowcount(Entity measure, Segment filter, Entity dimension) {
                return measure == null || filter == null || dimension == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.COUNT, measure, filter, dimension);
            }
            // </editor-fold>

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Scalar">
        public class Scalar {

            public EntityScalarX self(Entity x) {
                return x == null ? null : new EntityScalarX(ScalarOp.SELF, x);
            }

            public EntityScalarX cast(Instance x) {
                return x == null ? null : new EntityScalarX(ScalarOp.SELF, x);
            }

        }
        // </editor-fold>

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean">
    public class BooleanExpressionBuilder {

        private final Class<? extends VariantX> BooleanX = BooleanX.class;

        public BooleanExpression getForeignExpression(String name, Class<? extends Entity> clazz) {
            return (BooleanExpression) XS2.getForeignExpression(BooleanX, name, clazz);
        }

        public Comparison Comparison = new Comparison();

        public Conditional Conditional = new Conditional();

        public DataAggregate DataAggregate = new DataAggregate();

        public OrderedPair OrderedPair = new OrderedPair();

        public RowsAggregate RowsAggregate = new RowsAggregate();

        public Scalar Scalar = new Scalar();

        // <editor-fold defaultstate="collapsed" desc="Comparison">
        public class Comparison {

            public BooleanComparisonX isNull(BooleanExpression x) {
                return x == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL);
            }

            public BooleanComparisonX isNotNull(BooleanExpression x) {
                return x == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NOT_NULL);
            }

            public BooleanComparisonX isTrue(BooleanExpression x) {
                return x == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_TRUE);
            }

            public BooleanComparisonX isFalse(BooleanExpression x) {
                return x == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_FALSE);
            }

            public BooleanComparisonX isNullOrTrue(BooleanExpression x) {
                return x == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_TRUE);
            }

            public BooleanComparisonX isNullOrFalse(BooleanExpression x) {
                return x == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_FALSE);
            }

            public BooleanComparisonX isEqualTo(BooleanExpression x, BooleanExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.EQ, y);
            }

            public BooleanComparisonX isNotEqualTo(BooleanExpression x, BooleanExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NEQ, y);
            }

            public BooleanComparisonX isNullOrEqualTo(BooleanExpression x, BooleanExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_EQ, y);
            }

            public BooleanComparisonX isNullOrNotEqualTo(BooleanExpression x, BooleanExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NEQ, y);
            }

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Conditional">
        public class Conditional {

            public BooleanConditionalX then(BooleanExpression booleanExpression, Boolean value) {
                return booleanExpression == null || value == null ? null
                    : new BooleanConditionalX(booleanExpression, value);
            }

            public BooleanConditionalX then(BooleanExpression booleanExpression, SpecialBooleanValue value) {
                return booleanExpression == null || value == null ? null
                    : new BooleanConditionalX(booleanExpression, value);
            }

            public BooleanConditionalX then(BooleanExpression booleanExpression, BooleanExpression value) {
                return booleanExpression == null || value == null ? null
                    : new BooleanConditionalX(booleanExpression, value);
            }

            public BooleanOtherwiseX otherwise(BooleanConditionalX conditional, Boolean value) {
                return conditional == null || value == null ? null
                    : new BooleanOtherwiseX((ConditionalX) conditional, value);
            }

            public BooleanOtherwiseX otherwise(BooleanConditionalX conditional, SpecialBooleanValue value) {
                return conditional == null || value == null ? null
                    : new BooleanOtherwiseX((ConditionalX) conditional, value);
            }

            public BooleanOtherwiseX otherwise(BooleanConditionalX conditional, BooleanExpression value) {
                return conditional == null || value == null ? null
                    : new BooleanOtherwiseX((ConditionalX) conditional, value);
            }

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="DataAggregate">
        public class DataAggregate {

            public BooleanDataAggregateX coalesce(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new BooleanDataAggregateX(DataAggregateOp.COALESCE, operands);
            }

            public BooleanDataAggregateX and(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new BooleanDataAggregateX(DataAggregateOp.AND, operands);
            }

            public BooleanDataAggregateX nand(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new BooleanDataAggregateX(DataAggregateOp.NAND, operands);
            }

            public BooleanDataAggregateX or(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new BooleanDataAggregateX(DataAggregateOp.OR, operands);
            }

            public BooleanDataAggregateX nor(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new BooleanDataAggregateX(DataAggregateOp.NOR, operands);
            }

            public BooleanDataAggregateX naxor(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new BooleanDataAggregateX(DataAggregateOp.NAXOR, operands);
            }

            public BooleanDataAggregateX naxnor(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new BooleanDataAggregateX(DataAggregateOp.NAXNOR, operands);
            }

            public BooleanDataAggregateX norOrNaxor(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new BooleanDataAggregateX(DataAggregateOp.NOR_OR_NAXOR, operands);
            }

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="OrderedPair">
        public class OrderedPair {

            public BooleanOrderedPairX and(BooleanExpression x, BooleanExpression y) {
                return x == null || y == null ? null : new BooleanOrderedPairX(x, OrderedPairOp.AND, y);
            }

            public BooleanOrderedPairX nand(BooleanExpression x, BooleanExpression y) {
                return x == null || y == null ? null : new BooleanOrderedPairX(x, OrderedPairOp.NAND, y);
            }

            public BooleanOrderedPairX or(BooleanExpression x, BooleanExpression y) {
                return x == null || y == null ? null : new BooleanOrderedPairX(x, OrderedPairOp.OR, y);
            }

            public BooleanOrderedPairX nor(BooleanExpression x, BooleanExpression y) {
                return x == null || y == null ? null : new BooleanOrderedPairX(x, OrderedPairOp.NOR, y);
            }

            public BooleanOrderedPairX xor(BooleanExpression x, BooleanExpression y) {
                return x == null || y == null ? null : new BooleanOrderedPairX(x, OrderedPairOp.XOR, y);
            }

            public BooleanOrderedPairX xnor(BooleanExpression x, BooleanExpression y) {
                return x == null || y == null ? null : new BooleanOrderedPairX(x, OrderedPairOp.XNOR, y);
            }

            public BooleanOrderedPairX xImpliesY(BooleanExpression x, BooleanExpression y) {
                return x == null || y == null ? null : new BooleanOrderedPairX(x, OrderedPairOp.X_IMPLIES_Y, y);
            }

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="RowsAggregate">
        public class RowsAggregate {

            // <editor-fold defaultstate="collapsed" desc="rowcount">
            public NumericRowsAggregateX rowcount(BooleanExpression measure) {
                return measure == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.COUNT, measure);
            }

            public NumericRowsAggregateX rowcount(BooleanExpression measure, Segment filter) {
                return measure == null || filter == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.COUNT, measure, filter);
            }

            public NumericRowsAggregateX rowcount(BooleanExpression measure, Entity dimension) {
                return measure == null || dimension == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.COUNT, measure, dimension);
            }

            public NumericRowsAggregateX rowcount(BooleanExpression measure, Segment filter, Entity dimension) {
                return measure == null || filter == null || dimension == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.COUNT, measure, filter, dimension);
            }
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="and">
            public BooleanRowsAggregateX and(BooleanExpression measure) {
                return measure == null
                    ? null : new BooleanRowsAggregateX(RowsAggregateOp.AND, measure);
            }

            public BooleanRowsAggregateX and(BooleanExpression measure, Segment filter) {
                return measure == null || filter == null
                    ? null : new BooleanRowsAggregateX(RowsAggregateOp.AND, measure, filter);
            }

            public BooleanRowsAggregateX and(BooleanExpression measure, Entity dimension) {
                return measure == null || dimension == null
                    ? null : new BooleanRowsAggregateX(RowsAggregateOp.AND, measure, dimension);
            }

            public BooleanRowsAggregateX and(BooleanExpression measure, Segment filter, Entity dimension) {
                return measure == null || filter == null || dimension == null
                    ? null : new BooleanRowsAggregateX(RowsAggregateOp.AND, measure, filter, dimension);
            }
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="or">
            public BooleanRowsAggregateX or(BooleanExpression measure) {
                return measure == null
                    ? null : new BooleanRowsAggregateX(RowsAggregateOp.OR, measure);
            }

            public BooleanRowsAggregateX or(BooleanExpression measure, Segment filter) {
                return measure == null || filter == null
                    ? null : new BooleanRowsAggregateX(RowsAggregateOp.OR, measure, filter);
            }

            public BooleanRowsAggregateX or(BooleanExpression measure, Entity dimension) {
                return measure == null || dimension == null
                    ? null : new BooleanRowsAggregateX(RowsAggregateOp.OR, measure, dimension);
            }

            public BooleanRowsAggregateX or(BooleanExpression measure, Segment filter, Entity dimension) {
                return measure == null || filter == null || dimension == null
                    ? null : new BooleanRowsAggregateX(RowsAggregateOp.OR, measure, filter, dimension);
            }
            // </editor-fold>

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Scalar">
        public class Scalar {

            public BooleanScalarX not(BooleanExpression x) {
                return x == null ? null : new BooleanScalarX(ScalarOp.NOT, x);
            }

            public BooleanScalarX toBoolean(Object x) {
                return x == null ? null : new BooleanScalarX(ScalarOp.TO_BOOLEAN, x);
            }

        }
        // </editor-fold>

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Character">
    public class CharacterExpressionBuilder {

        private final Class<? extends VariantX> CharacterX = CharacterX.class;

        public CharacterExpression getForeignExpression(String name, Class<? extends Entity> clazz) {
            return (CharacterExpression) XS2.getForeignExpression(CharacterX, name, clazz);
        }

        public Comparison Comparison = new Comparison();

        public Conditional Conditional = new Conditional();

        public DataAggregate DataAggregate = new DataAggregate();

        public NaryVector NaryVector = new NaryVector();

        public OrderedPair OrderedPair = new OrderedPair();

        public RowsAggregate RowsAggregate = new RowsAggregate();

        public Scalar Scalar = new Scalar();

        // <editor-fold defaultstate="collapsed" desc="Comparison">
        public class Comparison {

            public BooleanComparisonX isNull(CharacterExpression x) {
                return x == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL);
            }

            public BooleanComparisonX isNotNull(CharacterExpression x) {
                return x == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NOT_NULL);
            }

            public BooleanComparisonX isEqualTo(CharacterExpression x, String y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.EQ, y);
            }

            public BooleanComparisonX isEqualTo(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.EQ, y);
            }

            public BooleanComparisonX isEqualTo(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.EQ, y);
            }

            public BooleanComparisonX isNotEqualTo(CharacterExpression x, String y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NEQ, y);
            }

            public BooleanComparisonX isNotEqualTo(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NEQ, y);
            }

            public BooleanComparisonX isNotEqualTo(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NEQ, y);
            }

            public BooleanComparisonX isGreaterThan(CharacterExpression x, String y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.GT, y);
            }

            public BooleanComparisonX isGreaterThan(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.GT, y);
            }

            public BooleanComparisonX isGreaterThan(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.GT, y);
            }

            public BooleanComparisonX isGreaterOrEqualTo(CharacterExpression x, String y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.GTEQ, y);
            }

            public BooleanComparisonX isGreaterOrEqualTo(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.GTEQ, y);
            }

            public BooleanComparisonX isGreaterOrEqualTo(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.GTEQ, y);
            }

            public BooleanComparisonX isLessThan(CharacterExpression x, String y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.LT, y);
            }

            public BooleanComparisonX isLessThan(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.LT, y);
            }

            public BooleanComparisonX isLessThan(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.LT, y);
            }

            public BooleanComparisonX isLessOrEqualTo(CharacterExpression x, String y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.LTEQ, y);
            }

            public BooleanComparisonX isLessOrEqualTo(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.LTEQ, y);
            }

            public BooleanComparisonX isLessOrEqualTo(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.LTEQ, y);
            }

            public BooleanComparisonX startsWith(CharacterExpression x, String y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.STARTS_WITH, y);
            }

            public BooleanComparisonX startsWith(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.STARTS_WITH, y);
            }

            public BooleanComparisonX startsWith(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.STARTS_WITH, y);
            }

            public BooleanComparisonX notStartsWith(CharacterExpression x, String y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NOT_STARTS_WITH, y);
            }

            public BooleanComparisonX notStartsWith(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NOT_STARTS_WITH, y);
            }

            public BooleanComparisonX notStartsWith(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NOT_STARTS_WITH, y);
            }

            public BooleanComparisonX contains(CharacterExpression x, String y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.CONTAINS, y);
            }

            public BooleanComparisonX contains(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.CONTAINS, y);
            }

            public BooleanComparisonX contains(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.CONTAINS, y);
            }

            public BooleanComparisonX notContains(CharacterExpression x, String y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NOT_CONTAINS, y);
            }

            public BooleanComparisonX notContains(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NOT_CONTAINS, y);
            }

            public BooleanComparisonX notContains(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NOT_CONTAINS, y);
            }

            public BooleanComparisonX endsWith(CharacterExpression x, String y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.ENDS_WITH, y);
            }

            public BooleanComparisonX endsWith(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.ENDS_WITH, y);
            }

            public BooleanComparisonX endsWith(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.ENDS_WITH, y);
            }

            public BooleanComparisonX notEndsWith(CharacterExpression x, String y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NOT_ENDS_WITH, y);
            }

            public BooleanComparisonX notEndsWith(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NOT_ENDS_WITH, y);
            }

            public BooleanComparisonX notEndsWith(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NOT_ENDS_WITH, y);
            }

            public BooleanComparisonX isIn(CharacterExpression x, CharacterExpression... y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_IN, y);
            }

            public BooleanComparisonX isIn(CharacterExpression x, NativeQuery y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_IN, y);
            }

            public BooleanComparisonX isNotIn(CharacterExpression x, CharacterExpression... y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NOT_IN, y);
            }

            public BooleanComparisonX isNotIn(CharacterExpression x, NativeQuery y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NOT_IN, y);
            }

            public BooleanComparisonX isBetween(CharacterExpression x, CharacterExpression y, CharacterExpression z) {
                return x == null || y == null || z == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_BETWEEN, y, z);
            }

            public BooleanComparisonX isNotBetween(CharacterExpression x, CharacterExpression y, CharacterExpression z) {
                return x == null || y == null || z == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NOT_BETWEEN, y, z);
            }

            public BooleanComparisonX isNullOrEqualTo(CharacterExpression x, String y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_EQ, y);
            }

            public BooleanComparisonX isNullOrEqualTo(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_EQ, y);
            }

            public BooleanComparisonX isNullOrEqualTo(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_EQ, y);
            }

            public BooleanComparisonX isNullOrNotEqualTo(CharacterExpression x, String y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NEQ, y);
            }

            public BooleanComparisonX isNullOrNotEqualTo(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NEQ, y);
            }

            public BooleanComparisonX isNullOrNotEqualTo(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NEQ, y);
            }

            public BooleanComparisonX isNullOrGreaterThan(CharacterExpression x, String y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_GT, y);
            }

            public BooleanComparisonX isNullOrGreaterThan(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_GT, y);
            }

            public BooleanComparisonX isNullOrGreaterThan(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_GT, y);
            }

            public BooleanComparisonX isNullOrGreaterOrEqualTo(CharacterExpression x, String y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_GTEQ, y);
            }

            public BooleanComparisonX isNullOrGreaterOrEqualTo(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_GTEQ, y);
            }

            public BooleanComparisonX isNullOrGreaterOrEqualTo(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_GTEQ, y);
            }

            public BooleanComparisonX isNullOrLessThan(CharacterExpression x, String y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_LT, y);
            }

            public BooleanComparisonX isNullOrLessThan(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_LT, y);
            }

            public BooleanComparisonX isNullOrLessThan(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_LT, y);
            }

            public BooleanComparisonX isNullOrLessOrEqualTo(CharacterExpression x, String y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_LTEQ, y);
            }

            public BooleanComparisonX isNullOrLessOrEqualTo(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_LTEQ, y);
            }

            public BooleanComparisonX isNullOrLessOrEqualTo(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_LTEQ, y);
            }

            public BooleanComparisonX isNullOrStartsWith(CharacterExpression x, String y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_STARTS_WITH, y);
            }

            public BooleanComparisonX isNullOrStartsWith(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_STARTS_WITH, y);
            }

            public BooleanComparisonX isNullOrStartsWith(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_STARTS_WITH, y);
            }

            public BooleanComparisonX isNullOrNotStartsWith(CharacterExpression x, String y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NOT_STARTS_WITH, y);
            }

            public BooleanComparisonX isNullOrNotStartsWith(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NOT_STARTS_WITH, y);
            }

            public BooleanComparisonX isNullOrNotStartsWith(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NOT_STARTS_WITH, y);
            }

            public BooleanComparisonX isNullOrContains(CharacterExpression x, String y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_CONTAINS, y);
            }

            public BooleanComparisonX isNullOrContains(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_CONTAINS, y);
            }

            public BooleanComparisonX isNullOrContains(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_CONTAINS, y);
            }

            public BooleanComparisonX isNullOrNotContains(CharacterExpression x, String y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NOT_CONTAINS, y);
            }

            public BooleanComparisonX isNullOrNotContains(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NOT_CONTAINS, y);
            }

            public BooleanComparisonX isNullOrNotContains(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NOT_CONTAINS, y);
            }

            public BooleanComparisonX isNullOrEndsWith(CharacterExpression x, String y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_ENDS_WITH, y);
            }

            public BooleanComparisonX isNullOrEndsWith(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_ENDS_WITH, y);
            }

            public BooleanComparisonX isNullOrEndsWith(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_ENDS_WITH, y);
            }

            public BooleanComparisonX isNullOrNotEndsWith(CharacterExpression x, String y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NOT_ENDS_WITH, y);
            }

            public BooleanComparisonX isNullOrNotEndsWith(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NOT_ENDS_WITH, y);
            }

            public BooleanComparisonX isNullOrNotEndsWith(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NOT_ENDS_WITH, y);
            }

            public BooleanComparisonX isNullOrIn(CharacterExpression x, CharacterExpression... y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_IN, y);
            }

            public BooleanComparisonX isNullOrIn(CharacterExpression x, NativeQuery y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_IN, y);
            }

            public BooleanComparisonX isNullOrNotIn(CharacterExpression x, CharacterExpression... y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NOT_IN, y);
            }

            public BooleanComparisonX isNullOrNotIn(CharacterExpression x, NativeQuery y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NOT_IN, y);
            }

            public BooleanComparisonX isNullOrBetween(CharacterExpression x, CharacterExpression y, CharacterExpression z) {
                return x == null || y == null || z == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_BETWEEN, y, z);
            }

            public BooleanComparisonX isNullOrNotBetween(CharacterExpression x, CharacterExpression y, CharacterExpression z) {
                return x == null || y == null || z == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NOT_BETWEEN, y, z);
            }

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Conditional">
        public class Conditional {

            public CharacterConditionalX then(BooleanExpression booleanExpression, String value) {
                return booleanExpression == null || value == null ? null
                    : new CharacterConditionalX(booleanExpression, value);
            }

            public CharacterConditionalX then(BooleanExpression booleanExpression, SpecialCharacterValue value) {
                return booleanExpression == null || value == null ? null
                    : new CharacterConditionalX(booleanExpression, value);
            }

            public CharacterConditionalX then(BooleanExpression booleanExpression, CharacterExpression value) {
                return booleanExpression == null || value == null ? null
                    : new CharacterConditionalX(booleanExpression, value);
            }

            public CharacterOtherwiseX otherwise(CharacterConditionalX conditional, String value) {
                return conditional == null || value == null ? null
                    : new CharacterOtherwiseX((ConditionalX) conditional, value);
            }

            public CharacterOtherwiseX otherwise(CharacterConditionalX conditional, SpecialCharacterValue value) {
                return conditional == null || value == null ? null
                    : new CharacterOtherwiseX((ConditionalX) conditional, value);
            }

            public CharacterOtherwiseX otherwise(CharacterConditionalX conditional, CharacterExpression value) {
                return conditional == null || value == null ? null
                    : new CharacterOtherwiseX((ConditionalX) conditional, value);
            }

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="DataAggregate">
        public class DataAggregate {

            public CharacterDataAggregateX coalesce(CharacterExpression operand1, CharacterExpression operand2, CharacterExpression... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new CharacterDataAggregateX(DataAggregateOp.COALESCE, operands);
            }

            public NumericDataAggregateX count(CharacterExpression operand1, CharacterExpression operand2, CharacterExpression... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new NumericDataAggregateX(DataAggregateOp.COUNT, operands);
            }

            public CharacterDataAggregateX max(CharacterExpression operand1, CharacterExpression operand2, CharacterExpression... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new CharacterDataAggregateX(DataAggregateOp.MAXIMUM, operands);
            }

            public CharacterDataAggregateX min(CharacterExpression operand1, CharacterExpression operand2, CharacterExpression... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new CharacterDataAggregateX(DataAggregateOp.MINIMUM, operands);
            }

            public CharacterDataAggregateX concat(CharacterExpression operand1, CharacterExpression operand2, CharacterExpression... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new CharacterDataAggregateX(DataAggregateOp.CONCAT, operands);
            }

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Nary">
        public class NaryVector {

            public CharacterNaryVectorX substr(CharacterExpression x, NumericExpression pos, NumericExpression len) {
                return x == null ? null : new CharacterNaryVectorX(NaryVectorOp.SUBSTR, x, pos, len);
            }

            public CharacterNaryVectorX substr(CharacterExpression x, NumericExpression pos, int len) {
                return x == null ? null : new CharacterNaryVectorX(NaryVectorOp.SUBSTR, x, pos, len);
            }

            public CharacterNaryVectorX substr(CharacterExpression x, int pos, NumericExpression len) {
                return x == null ? null : new CharacterNaryVectorX(NaryVectorOp.SUBSTR, x, pos, len);
            }

            public CharacterNaryVectorX substr(CharacterExpression x, int pos, int len) {
                return x == null ? null : new CharacterNaryVectorX(NaryVectorOp.SUBSTR, x, pos, len);
            }

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="OrderedPair">
        public class OrderedPair {

            public CharacterOrderedPairX coalesce(CharacterExpression x, String y) {
                return x == null || y == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.COALESCE, y);
            }

            public CharacterOrderedPairX coalesce(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.COALESCE, y);
            }

            public CharacterOrderedPairX coalesce(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.COALESCE, y);
            }

            public CharacterOrderedPairX nullIf(CharacterExpression x, String y) {
                return x == null || y == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.NULLIF, y);
            }

            public CharacterOrderedPairX nullIf(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.NULLIF, y);
            }

            public CharacterOrderedPairX nullIf(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.NULLIF, y);
            }

            public CharacterOrderedPairX max(CharacterExpression x, String y) {
                return x == null || y == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.MAXIMUM, y);
            }

            public CharacterOrderedPairX max(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.MAXIMUM, y);
            }

            public CharacterOrderedPairX max(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.MAXIMUM, y);
            }

            public CharacterOrderedPairX min(CharacterExpression x, String y) {
                return x == null || y == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.MINIMUM, y);
            }

            public CharacterOrderedPairX min(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.MINIMUM, y);
            }

            public CharacterOrderedPairX min(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.MINIMUM, y);
            }

            public CharacterOrderedPairX ascii(CharacterExpression x, String y) {
                return x == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.ASCII, y);
            }

            public CharacterOrderedPairX diacriticlessAscii(CharacterExpression x, String y) {
                return x == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.DIACRITICLESS_ASCII, y);
            }

            public CharacterOrderedPairX concat(CharacterExpression x, String y) {
                return x == null || y == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.CONCAT, y);
            }

            public CharacterOrderedPairX concat(CharacterExpression x, SpecialCharacterValue y) {
                return x == null || y == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.CONCAT, y);
            }

            public CharacterOrderedPairX concat(CharacterExpression x, CharacterExpression y) {
                return x == null || y == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.CONCAT, y);
            }

            public CharacterOrderedPairX format(CharacterExpression x, String y) {
                return x == null || y == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.FORMAT, y);
            }

            public CharacterOrderedPairX left(CharacterExpression x, NumericExpression y) {
                return x == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.LEFT, y);
            }

            public CharacterOrderedPairX left(CharacterExpression x, int y) {
                return x == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.LEFT, y);
            }

            public CharacterOrderedPairX right(CharacterExpression x, NumericExpression y) {
                return x == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.RIGHT, y);
            }

            public CharacterOrderedPairX right(CharacterExpression x, int y) {
                return x == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.RIGHT, y);
            }

            public CharacterOrderedPairX substr(CharacterExpression x, NumericExpression y) {
                return x == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.SUBSTR, y);
            }

            public CharacterOrderedPairX substr(CharacterExpression x, int y) {
                return x == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.SUBSTR, y);
            }

            public CharacterOrderedPairX toZeroPaddedString(CharacterExpression x, NumericExpression y) {
                return x == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.TO_ZERO_PADDED_STRING, y);
            }

            public CharacterOrderedPairX toZeroPaddedString(CharacterExpression x, int y) {
                return x == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.TO_ZERO_PADDED_STRING, y);
            }

            public CharacterOrderedPairX toZeroPaddedString(NumericExpression x, NumericExpression y) {
                return x == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.TO_ZERO_PADDED_STRING, y);
            }

            public CharacterOrderedPairX toZeroPaddedString(NumericExpression x, int y) {
                return x == null ? null : new CharacterOrderedPairX(x, OrderedPairOp.TO_ZERO_PADDED_STRING, y);
            }

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="RowsAggregate">
        public class RowsAggregate {

            // <editor-fold defaultstate="collapsed" desc="rowcount">
            public NumericRowsAggregateX rowcount(CharacterExpression measure) {
                return measure == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.COUNT, measure);
            }

            public NumericRowsAggregateX rowcount(CharacterExpression measure, Segment filter) {
                return measure == null || filter == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.COUNT, measure, filter);
            }

            public NumericRowsAggregateX rowcount(CharacterExpression measure, Entity dimension) {
                return measure == null || dimension == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.COUNT, measure, dimension);
            }

            public NumericRowsAggregateX rowcount(CharacterExpression measure, Segment filter, Entity dimension) {
                return measure == null || filter == null || dimension == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.COUNT, measure, filter, dimension);
            }
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="maximum">
            public CharacterRowsAggregateX maximum(CharacterExpression measure) {
                return measure == null
                    ? null : new CharacterRowsAggregateX(RowsAggregateOp.MAXIMUM, measure);
            }

            public CharacterRowsAggregateX maximum(CharacterExpression measure, Segment filter) {
                return measure == null || filter == null
                    ? null : new CharacterRowsAggregateX(RowsAggregateOp.MAXIMUM, measure, filter);
            }

            public CharacterRowsAggregateX maximum(CharacterExpression measure, Entity dimension) {
                return measure == null || dimension == null
                    ? null : new CharacterRowsAggregateX(RowsAggregateOp.MAXIMUM, measure, dimension);
            }

            public CharacterRowsAggregateX maximum(CharacterExpression measure, Segment filter, Entity dimension) {
                return measure == null || filter == null || dimension == null
                    ? null : new CharacterRowsAggregateX(RowsAggregateOp.MAXIMUM, measure, filter, dimension);
            }
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="minimum">
            public CharacterRowsAggregateX minimum(CharacterExpression measure) {
                return measure == null
                    ? null : new CharacterRowsAggregateX(RowsAggregateOp.MINIMUM, measure);
            }

            public CharacterRowsAggregateX minimum(CharacterExpression measure, Segment filter) {
                return measure == null || filter == null
                    ? null : new CharacterRowsAggregateX(RowsAggregateOp.MINIMUM, measure, filter);
            }

            public CharacterRowsAggregateX minimum(CharacterExpression measure, Entity dimension) {
                return measure == null || dimension == null
                    ? null : new CharacterRowsAggregateX(RowsAggregateOp.MINIMUM, measure, dimension);
            }

            public CharacterRowsAggregateX minimum(CharacterExpression measure, Segment filter, Entity dimension) {
                return measure == null || filter == null || dimension == null
                    ? null : new CharacterRowsAggregateX(RowsAggregateOp.MINIMUM, measure, filter, dimension);
            }
            // </editor-fold>

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Scalar">
        public class Scalar {

            public CharacterScalarX defaultWhenNull(CharacterExpression x) {
                return x == null ? null : new CharacterScalarX(ScalarOp.DEFAULT_WHEN_NULL, x);
            }

            public CharacterScalarX nullWhenDefault(CharacterExpression x) {
                return x == null ? null : new CharacterScalarX(ScalarOp.NULL_WHEN_DEFAULT, x);
            }

            public CharacterScalarX ascii(CharacterExpression x) {
                return x == null ? null : new CharacterScalarX(ScalarOp.ASCII, x);
            }

            public CharacterScalarX diacriticless(CharacterExpression x) {
                return x == null ? null : new CharacterScalarX(ScalarOp.DIACRITICLESS, x);
            }

            public CharacterScalarX diacriticlessAscii(CharacterExpression x) {
                return x == null ? null : new CharacterScalarX(ScalarOp.DIACRITICLESS_ASCII, x);
            }

            public CharacterScalarX lower(CharacterExpression x) {
                return x == null ? null : new CharacterScalarX(ScalarOp.LOWER, x);
            }

            public CharacterScalarX upper(CharacterExpression x) {
                return x == null ? null : new CharacterScalarX(ScalarOp.UPPER, x);
            }

            public CharacterScalarX capitalize(CharacterExpression x) {
                return x == null ? null : new CharacterScalarX(ScalarOp.CAPITALIZE, x);
            }

            public CharacterScalarX uncapitalize(CharacterExpression x) {
                return x == null ? null : new CharacterScalarX(ScalarOp.UNCAPITALIZE, x);
            }

            public CharacterScalarX trim(CharacterExpression x) {
                return x == null ? null : new CharacterScalarX(ScalarOp.TRIM, x);
            }

            public CharacterScalarX ltrim(CharacterExpression x) {
                return x == null ? null : new CharacterScalarX(ScalarOp.LTRIM, x);
            }

            public CharacterScalarX rtrim(CharacterExpression x) {
                return x == null ? null : new CharacterScalarX(ScalarOp.RTRIM, x);
            }

            public CharacterScalarX toChar(Object x) {
                return x == null ? null : new CharacterScalarX(ScalarOp.TO_CHARACTER, x);
            }

            public CharacterScalarX toCharString(Object x) {
                return x == null ? null : x instanceof String ? new CharacterScalarX(null, x) : new CharacterScalarX(ScalarOp.TO_STRING, x);
            }

            public CharacterScalarX toLocaleString(Object x) {
                return x == null ? null : x instanceof String ? new CharacterScalarX(null, x) : new CharacterScalarX(ScalarOp.TO_LOCALE_STRING, x);
            }

        }
        // </editor-fold>

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Numeric">
    public class NumericExpressionBuilder {

        private final Class<? extends VariantX> NumericX = NumericX.class;

        public NumericExpression getForeignExpression(String name, Class<? extends Entity> clazz) {
            return (NumericExpression) XS2.getForeignExpression(NumericX, name, clazz);
        }

        public Comparison Comparison = new Comparison();

        public Conditional Conditional = new Conditional();

        public DataAggregate DataAggregate = new DataAggregate();

        public OrderedPair OrderedPair = new OrderedPair();

        public RowsAggregate RowsAggregate = new RowsAggregate();

        public Scalar Scalar = new Scalar();

        // <editor-fold defaultstate="collapsed" desc="Comparison">
        public class Comparison {

            public BooleanComparisonX isNull(NumericExpression x) {
                return x == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL);
            }

            public BooleanComparisonX isNotNull(NumericExpression x) {
                return x == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NOT_NULL);
            }

            public BooleanComparisonX isEqualTo(NumericExpression x, Number y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.EQ, y);
            }

            public BooleanComparisonX isEqualTo(NumericExpression x, SpecialNumericValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.EQ, y);
            }

            public BooleanComparisonX isEqualTo(NumericExpression x, NumericExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.EQ, y);
            }

            public BooleanComparisonX isNotEqualTo(NumericExpression x, Number y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NEQ, y);
            }

            public BooleanComparisonX isNotEqualTo(NumericExpression x, SpecialNumericValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NEQ, y);
            }

            public BooleanComparisonX isNotEqualTo(NumericExpression x, NumericExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NEQ, y);
            }

            public BooleanComparisonX isGreaterThan(NumericExpression x, Number y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.GT, y);
            }

            public BooleanComparisonX isGreaterThan(NumericExpression x, SpecialNumericValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.GT, y);
            }

            public BooleanComparisonX isGreaterThan(NumericExpression x, NumericExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.GT, y);
            }

            public BooleanComparisonX isGreaterOrEqualTo(NumericExpression x, Number y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.GTEQ, y);
            }

            public BooleanComparisonX isGreaterOrEqualTo(NumericExpression x, SpecialNumericValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.GTEQ, y);
            }

            public BooleanComparisonX isGreaterOrEqualTo(NumericExpression x, NumericExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.GTEQ, y);
            }

            public BooleanComparisonX isLessThan(NumericExpression x, Number y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.LT, y);
            }

            public BooleanComparisonX isLessThan(NumericExpression x, SpecialNumericValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.LT, y);
            }

            public BooleanComparisonX isLessThan(NumericExpression x, NumericExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.LT, y);
            }

            public BooleanComparisonX isLessOrEqualTo(NumericExpression x, Number y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.LTEQ, y);
            }

            public BooleanComparisonX isLessOrEqualTo(NumericExpression x, SpecialNumericValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.LTEQ, y);
            }

            public BooleanComparisonX isLessOrEqualTo(NumericExpression x, NumericExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.LTEQ, y);
            }

            public BooleanComparisonX isIn(NumericExpression x, NumericExpression... y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_IN, y);
            }

            public BooleanComparisonX isIn(NumericExpression x, NativeQuery y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_IN, y);
            }

            public BooleanComparisonX isNotIn(NumericExpression x, NumericExpression... y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NOT_IN, y);
            }

            public BooleanComparisonX isNotIn(NumericExpression x, NativeQuery y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NOT_IN, y);
            }

            public BooleanComparisonX isBetween(NumericExpression x, NumericExpression y, NumericExpression z) {
                return x == null || y == null || z == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_BETWEEN, y, z);
            }

            public BooleanComparisonX isNotBetween(NumericExpression x, NumericExpression y, NumericExpression z) {
                return x == null || y == null || z == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NOT_BETWEEN, y, z);
            }

            public BooleanComparisonX isNullOrEqualTo(NumericExpression x, Number y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_EQ, y);
            }

            public BooleanComparisonX isNullOrEqualTo(NumericExpression x, SpecialNumericValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_EQ, y);
            }

            public BooleanComparisonX isNullOrEqualTo(NumericExpression x, NumericExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_EQ, y);
            }

            public BooleanComparisonX isNullOrNotEqualTo(NumericExpression x, Number y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NEQ, y);
            }

            public BooleanComparisonX isNullOrNotEqualTo(NumericExpression x, SpecialNumericValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NEQ, y);
            }

            public BooleanComparisonX isNullOrNotEqualTo(NumericExpression x, NumericExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NEQ, y);
            }

            public BooleanComparisonX isNullOrGreaterThan(NumericExpression x, Number y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_GT, y);
            }

            public BooleanComparisonX isNullOrGreaterThan(NumericExpression x, SpecialNumericValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_GT, y);
            }

            public BooleanComparisonX isNullOrGreaterThan(NumericExpression x, NumericExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_GT, y);
            }

            public BooleanComparisonX isNullOrGreaterOrEqualTo(NumericExpression x, Number y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_GTEQ, y);
            }

            public BooleanComparisonX isNullOrGreaterOrEqualTo(NumericExpression x, SpecialNumericValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_GTEQ, y);
            }

            public BooleanComparisonX isNullOrGreaterOrEqualTo(NumericExpression x, NumericExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_GTEQ, y);
            }

            public BooleanComparisonX isNullOrLessThan(NumericExpression x, Number y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_LT, y);
            }

            public BooleanComparisonX isNullOrLessThan(NumericExpression x, SpecialNumericValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_LT, y);
            }

            public BooleanComparisonX isNullOrLessThan(NumericExpression x, NumericExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_LT, y);
            }

            public BooleanComparisonX isNullOrLessOrEqualTo(NumericExpression x, Number y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_LTEQ, y);
            }

            public BooleanComparisonX isNullOrLessOrEqualTo(NumericExpression x, SpecialNumericValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_LTEQ, y);
            }

            public BooleanComparisonX isNullOrLessOrEqualTo(NumericExpression x, NumericExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_LTEQ, y);
            }

            public BooleanComparisonX isNullOrIn(NumericExpression x, NumericExpression... y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_IN, y);
            }

            public BooleanComparisonX isNullOrIn(NumericExpression x, NativeQuery y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_IN, y);
            }

            public BooleanComparisonX isNullOrNotIn(NumericExpression x, NumericExpression... y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NOT_IN, y);
            }

            public BooleanComparisonX isNullOrNotIn(NumericExpression x, NativeQuery y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NOT_IN, y);
            }

            public BooleanComparisonX isNullOrBetween(NumericExpression x, NumericExpression y, NumericExpression z) {
                return x == null || y == null || z == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_BETWEEN, y, z);
            }

            public BooleanComparisonX isNullOrNotBetween(NumericExpression x, NumericExpression y, NumericExpression z) {
                return x == null || y == null || z == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NOT_BETWEEN, y, z);
            }

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Conditional">
        public class Conditional {

            public NumericConditionalX then(BooleanExpression booleanExpression, Number value) {
                return booleanExpression == null || value == null ? null
                    : new NumericConditionalX(booleanExpression, value);
            }

            public NumericConditionalX then(BooleanExpression booleanExpression, SpecialNumericValue value) {
                return booleanExpression == null || value == null ? null
                    : new NumericConditionalX(booleanExpression, value);
            }

            public NumericConditionalX then(BooleanExpression booleanExpression, NumericExpression value) {
                return booleanExpression == null || value == null ? null
                    : new NumericConditionalX(booleanExpression, value);
            }

            public NumericOtherwiseX otherwise(NumericConditionalX conditional, Number value) {
                return conditional == null || value == null ? null
                    : new NumericOtherwiseX((ConditionalX) conditional, value);
            }

            public NumericOtherwiseX otherwise(NumericConditionalX conditional, SpecialNumericValue value) {
                return conditional == null || value == null ? null
                    : new NumericOtherwiseX((ConditionalX) conditional, value);
            }

            public NumericOtherwiseX otherwise(NumericConditionalX conditional, NumericExpression value) {
                return conditional == null || value == null ? null
                    : new NumericOtherwiseX((ConditionalX) conditional, value);
            }

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="DataAggregate">
        public class DataAggregate {

            public NumericDataAggregateX coalesce(NumericExpression operand1, NumericExpression operand2, NumericExpression... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new NumericDataAggregateX(DataAggregateOp.COALESCE, operands);
            }

            public NumericDataAggregateX count(NumericExpression operand1, NumericExpression operand2, NumericExpression... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new NumericDataAggregateX(DataAggregateOp.COUNT, operands);
            }

            public NumericDataAggregateX max(NumericExpression operand1, NumericExpression operand2, NumericExpression... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new NumericDataAggregateX(DataAggregateOp.MAXIMUM, operands);
            }

            public NumericDataAggregateX min(NumericExpression operand1, NumericExpression operand2, NumericExpression... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new NumericDataAggregateX(DataAggregateOp.MINIMUM, operands);
            }

            public NumericDataAggregateX sum(NumericExpression operand1, NumericExpression operand2, NumericExpression... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new NumericDataAggregateX(DataAggregateOp.SUM, operands);
            }

            public NumericDataAggregateX product(NumericExpression operand1, NumericExpression operand2, NumericExpression... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new NumericDataAggregateX(DataAggregateOp.PRODUCT, operands);
            }

            public NumericDataAggregateX avg(NumericExpression operand1, NumericExpression operand2, NumericExpression... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new NumericDataAggregateX(DataAggregateOp.AVERAGE, operands);
            }

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="OrderedPair">
        public class OrderedPair {

            public NumericOrderedPairX coalesce(NumericExpression x, Number y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.COALESCE, y);
            }

            public NumericOrderedPairX coalesce(NumericExpression x, SpecialNumericValue y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.COALESCE, y);
            }

            public NumericOrderedPairX coalesce(NumericExpression x, NumericExpression y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.COALESCE, y);
            }

            public NumericOrderedPairX nullIf(NumericExpression x, Number y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.NULLIF, y);
            }

            public NumericOrderedPairX nullIf(NumericExpression x, SpecialNumericValue y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.NULLIF, y);
            }

            public NumericOrderedPairX nullIf(NumericExpression x, NumericExpression y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.NULLIF, y);
            }

            public NumericOrderedPairX max(NumericExpression x, Number y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.MAXIMUM, y);
            }

            public NumericOrderedPairX max(NumericExpression x, SpecialNumericValue y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.MAXIMUM, y);
            }

            public NumericOrderedPairX max(NumericExpression x, NumericExpression y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.MAXIMUM, y);
            }

            public NumericOrderedPairX min(NumericExpression x, Number y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.MINIMUM, y);
            }

            public NumericOrderedPairX min(NumericExpression x, SpecialNumericValue y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.MINIMUM, y);
            }

            public NumericOrderedPairX min(NumericExpression x, NumericExpression y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.MINIMUM, y);
            }

            public NumericOrderedPairX xPlusY(NumericExpression x, Number y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.X_PLUS_Y, y);
            }

            public NumericOrderedPairX xPlusY(NumericExpression x, SpecialNumericValue y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.X_PLUS_Y, y);
            }

            public NumericOrderedPairX xPlusY(NumericExpression x, NumericExpression y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.X_PLUS_Y, y);
            }

            public NumericOrderedPairX xMinusY(NumericExpression x, Number y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.X_MINUS_Y, y);
            }

            public NumericOrderedPairX xMinusY(NumericExpression x, SpecialNumericValue y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.X_MINUS_Y, y);
            }

            public NumericOrderedPairX xMinusY(NumericExpression x, NumericExpression y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.X_MINUS_Y, y);
            }

            public NumericOrderedPairX xTimesY(NumericExpression x, Number y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.X_MULTIPLIED_BY_Y, y);
            }

            public NumericOrderedPairX xTimesY(NumericExpression x, SpecialNumericValue y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.X_MULTIPLIED_BY_Y, y);
            }

            public NumericOrderedPairX xTimesY(NumericExpression x, NumericExpression y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.X_MULTIPLIED_BY_Y, y);
            }

            public NumericOrderedPairX xOverY(NumericExpression x, Number y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.X_DIVIDED_INTO_Y, y);
            }

            public NumericOrderedPairX xOverY(NumericExpression x, SpecialNumericValue y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.X_DIVIDED_INTO_Y, y);
            }

            public NumericOrderedPairX xOverY(NumericExpression x, NumericExpression y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.X_DIVIDED_INTO_Y, y);
            }

            public NumericOrderedPairX xToTheY(NumericExpression x, Number y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.X_RAISED_TO_THE_Y, y);
            }

            public NumericOrderedPairX xToTheY(NumericExpression x, SpecialNumericValue y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.X_RAISED_TO_THE_Y, y);
            }

            public NumericOrderedPairX xToTheY(NumericExpression x, NumericExpression y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.X_RAISED_TO_THE_Y, y);
            }

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="RowsAggregate">
        public class RowsAggregate {

            // <editor-fold defaultstate="collapsed" desc="rowcount">
            public NumericRowsAggregateX rowcount(NumericExpression measure) {
                return measure == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.COUNT, measure);
            }

            public NumericRowsAggregateX rowcount(NumericExpression measure, Segment filter) {
                return measure == null || filter == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.COUNT, measure, filter);
            }

            public NumericRowsAggregateX rowcount(NumericExpression measure, Entity dimension) {
                return measure == null || dimension == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.COUNT, measure, dimension);
            }

            public NumericRowsAggregateX rowcount(NumericExpression measure, Segment filter, Entity dimension) {
                return measure == null || filter == null || dimension == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.COUNT, measure, filter, dimension);
            }
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="maximum">
            public NumericRowsAggregateX maximum(NumericExpression measure) {
                return measure == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.MAXIMUM, measure);
            }

            public NumericRowsAggregateX maximum(NumericExpression measure, Segment filter) {
                return measure == null || filter == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.MAXIMUM, measure, filter);
            }

            public NumericRowsAggregateX maximum(NumericExpression measure, Entity dimension) {
                return measure == null || dimension == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.MAXIMUM, measure, dimension);
            }

            public NumericRowsAggregateX maximum(NumericExpression measure, Segment filter, Entity dimension) {
                return measure == null || filter == null || dimension == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.MAXIMUM, measure, filter, dimension);
            }
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="minimum">
            public NumericRowsAggregateX minimum(NumericExpression measure) {
                return measure == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.MINIMUM, measure);
            }

            public NumericRowsAggregateX minimum(NumericExpression measure, Segment filter) {
                return measure == null || filter == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.MINIMUM, measure, filter);
            }

            public NumericRowsAggregateX minimum(NumericExpression measure, Entity dimension) {
                return measure == null || dimension == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.MINIMUM, measure, dimension);
            }

            public NumericRowsAggregateX minimum(NumericExpression measure, Segment filter, Entity dimension) {
                return measure == null || filter == null || dimension == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.MINIMUM, measure, filter, dimension);
            }
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="sum">
            public NumericRowsAggregateX sum(NumericExpression measure) {
                return measure == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.SUM, measure);
            }

            public NumericRowsAggregateX sum(NumericExpression measure, Segment filter) {
                return measure == null || filter == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.SUM, measure, filter);
            }

            public NumericRowsAggregateX sum(NumericExpression measure, Entity dimension) {
                return measure == null || dimension == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.SUM, measure, dimension);
            }

            public NumericRowsAggregateX sum(NumericExpression measure, Segment filter, Entity dimension) {
                return measure == null || filter == null || dimension == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.SUM, measure, filter, dimension);
            }
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="average">
            public NumericRowsAggregateX average(NumericExpression measure) {
                return measure == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.AVERAGE, measure);
            }

            public NumericRowsAggregateX average(NumericExpression measure, Segment filter) {
                return measure == null || filter == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.AVERAGE, measure, filter);
            }

            public NumericRowsAggregateX average(NumericExpression measure, Entity dimension) {
                return measure == null || dimension == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.AVERAGE, measure, dimension);
            }

            public NumericRowsAggregateX average(NumericExpression measure, Segment filter, Entity dimension) {
                return measure == null || filter == null || dimension == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.AVERAGE, measure, filter, dimension);
            }
            // </editor-fold>

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Scalar">
        public class Scalar {

            public NumericScalarX defaultWhenNull(NumericExpression x) {
                return x == null ? null : new NumericScalarX(ScalarOp.DEFAULT_WHEN_NULL, x);
            }

            public NumericScalarX nullWhenDefault(NumericExpression x) {
                return x == null ? null : new NumericScalarX(ScalarOp.NULL_WHEN_DEFAULT, x);
            }

            public NumericScalarX abs(NumericExpression x) {
                return x == null ? null : new NumericScalarX(ScalarOp.MODULUS, x);
            }

            public NumericScalarX chs(NumericExpression x) {
                return x == null ? null : new NumericScalarX(ScalarOp.OPPOSITE, x);
            }

            public NumericScalarX inv(NumericExpression x) {
                return x == null ? null : new NumericScalarX(ScalarOp.RECIPROCAL, x);
            }

            public NumericScalarX toByte(Object x) {
                return x == null ? null : new NumericScalarX(ScalarOp.TO_BYTE, x);
            }

            public NumericScalarX toShort(Object x) {
                return x == null ? null : new NumericScalarX(ScalarOp.TO_SHORT, x);
            }

            public NumericScalarX toInteger(Object x) {
                return x == null ? null : new NumericScalarX(ScalarOp.TO_INTEGER, x);
            }

            public NumericScalarX toLong(Object x) {
                return x == null ? null : new NumericScalarX(ScalarOp.TO_LONG, x);
            }

            public NumericScalarX toFloat(Object x) {
                return x == null ? null : new NumericScalarX(ScalarOp.TO_FLOAT, x);
            }

            public NumericScalarX toDouble(Object x) {
                return x == null ? null : new NumericScalarX(ScalarOp.TO_DOUBLE, x);
            }

            public NumericScalarX toBigInteger(Object x) {
                return x == null ? null : new NumericScalarX(ScalarOp.TO_BIG_INTEGER, x);
            }

            public NumericScalarX toBigDecimal(Object x) {
                return x == null ? null : new NumericScalarX(ScalarOp.TO_BIG_DECIMAL, x);
            }

        }
        // </editor-fold>

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Temporal">
    public class TemporalExpressionBuilder {

        private final Class<? extends VariantX> TemporalX = TemporalX.class;

        public TemporalExpression getForeignExpression(String name, Class<? extends Entity> clazz) {
            return (TemporalExpression) XS2.getForeignExpression(TemporalX, name, clazz);
        }

        public Comparison Comparison = new Comparison();

        public Conditional Conditional = new Conditional();

        public DataAggregate DataAggregate = new DataAggregate();

        public OrderedPair OrderedPair = new OrderedPair();

        public RowsAggregate RowsAggregate = new RowsAggregate();

        public Scalar Scalar = new Scalar();

        // <editor-fold defaultstate="collapsed" desc="Comparison">
        public class Comparison {

            public BooleanComparisonX isNull(TemporalExpression x) {
                return x == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL);
            }

            public BooleanComparisonX isNotNull(TemporalExpression x) {
                return x == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NOT_NULL);
            }

            public BooleanComparisonX isEqualTo(TemporalExpression x, java.util.Date y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.EQ, y);
            }

            public BooleanComparisonX isEqualTo(TemporalExpression x, SpecialTemporalValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.EQ, y);
            }

            public BooleanComparisonX isEqualTo(TemporalExpression x, TemporalExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.EQ, y);
            }

            public BooleanComparisonX isNotEqualTo(TemporalExpression x, java.util.Date y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NEQ, y);
            }

            public BooleanComparisonX isNotEqualTo(TemporalExpression x, SpecialTemporalValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NEQ, y);
            }

            public BooleanComparisonX isNotEqualTo(TemporalExpression x, TemporalExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.NEQ, y);
            }

            public BooleanComparisonX isGreaterThan(TemporalExpression x, java.util.Date y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.GT, y);
            }

            public BooleanComparisonX isGreaterThan(TemporalExpression x, SpecialTemporalValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.GT, y);
            }

            public BooleanComparisonX isGreaterThan(TemporalExpression x, TemporalExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.GT, y);
            }

            public BooleanComparisonX isGreaterOrEqualTo(TemporalExpression x, java.util.Date y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.GTEQ, y);
            }

            public BooleanComparisonX isGreaterOrEqualTo(TemporalExpression x, SpecialTemporalValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.GTEQ, y);
            }

            public BooleanComparisonX isGreaterOrEqualTo(TemporalExpression x, TemporalExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.GTEQ, y);
            }

            public BooleanComparisonX isLessThan(TemporalExpression x, java.util.Date y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.LT, y);
            }

            public BooleanComparisonX isLessThan(TemporalExpression x, SpecialTemporalValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.LT, y);
            }

            public BooleanComparisonX isLessThan(TemporalExpression x, TemporalExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.LT, y);
            }

            public BooleanComparisonX isLessOrEqualTo(TemporalExpression x, java.util.Date y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.LTEQ, y);
            }

            public BooleanComparisonX isLessOrEqualTo(TemporalExpression x, SpecialTemporalValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.LTEQ, y);
            }

            public BooleanComparisonX isLessOrEqualTo(TemporalExpression x, TemporalExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.LTEQ, y);
            }

            public BooleanComparisonX isIn(TemporalExpression x, TemporalExpression... y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_IN, y);
            }

            public BooleanComparisonX isIn(TemporalExpression x, NativeQuery y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_IN, y);
            }

            public BooleanComparisonX isNotIn(TemporalExpression x, TemporalExpression... y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NOT_IN, y);
            }

            public BooleanComparisonX isNotIn(TemporalExpression x, NativeQuery y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NOT_IN, y);
            }

            public BooleanComparisonX isBetween(TemporalExpression x, TemporalExpression y, TemporalExpression z) {
                return x == null || y == null || z == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_BETWEEN, y, z);
            }

            public BooleanComparisonX isNotBetween(TemporalExpression x, TemporalExpression y, TemporalExpression z) {
                return x == null || y == null || z == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NOT_BETWEEN, y, z);
            }

            public BooleanComparisonX isNullOrEqualTo(TemporalExpression x, java.util.Date y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_EQ, y);
            }

            public BooleanComparisonX isNullOrEqualTo(TemporalExpression x, SpecialTemporalValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_EQ, y);
            }

            public BooleanComparisonX isNullOrEqualTo(TemporalExpression x, TemporalExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_EQ, y);
            }

            public BooleanComparisonX isNullOrNotEqualTo(TemporalExpression x, java.util.Date y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NEQ, y);
            }

            public BooleanComparisonX isNullOrNotEqualTo(TemporalExpression x, SpecialTemporalValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NEQ, y);
            }

            public BooleanComparisonX isNullOrNotEqualTo(TemporalExpression x, TemporalExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NEQ, y);
            }

            public BooleanComparisonX isNullOrGreaterThan(TemporalExpression x, java.util.Date y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_GT, y);
            }

            public BooleanComparisonX isNullOrGreaterThan(TemporalExpression x, SpecialTemporalValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_GT, y);
            }

            public BooleanComparisonX isNullOrGreaterThan(TemporalExpression x, TemporalExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_GT, y);
            }

            public BooleanComparisonX isNullOrGreaterOrEqualTo(TemporalExpression x, java.util.Date y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_GTEQ, y);
            }

            public BooleanComparisonX isNullOrGreaterOrEqualTo(TemporalExpression x, SpecialTemporalValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_GTEQ, y);
            }

            public BooleanComparisonX isNullOrGreaterOrEqualTo(TemporalExpression x, TemporalExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_GTEQ, y);
            }

            public BooleanComparisonX isNullOrLessThan(TemporalExpression x, java.util.Date y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_LT, y);
            }

            public BooleanComparisonX isNullOrLessThan(TemporalExpression x, SpecialTemporalValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_LT, y);
            }

            public BooleanComparisonX isNullOrLessThan(TemporalExpression x, TemporalExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_LT, y);
            }

            public BooleanComparisonX isNullOrLessOrEqualTo(TemporalExpression x, java.util.Date y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_LTEQ, y);
            }

            public BooleanComparisonX isNullOrLessOrEqualTo(TemporalExpression x, SpecialTemporalValue y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_LTEQ, y);
            }

            public BooleanComparisonX isNullOrLessOrEqualTo(TemporalExpression x, TemporalExpression y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_LTEQ, y);
            }

            public BooleanComparisonX isNullOrIn(TemporalExpression x, TemporalExpression... y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_IN, y);
            }

            public BooleanComparisonX isNullOrIn(TemporalExpression x, NativeQuery y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_IN, y);
            }

            public BooleanComparisonX isNullOrNotIn(TemporalExpression x, TemporalExpression... y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NOT_IN, y);
            }

            public BooleanComparisonX isNullOrNotIn(TemporalExpression x, NativeQuery y) {
                return x == null || y == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NOT_IN, y);
            }

            public BooleanComparisonX isNullOrBetween(TemporalExpression x, TemporalExpression y, TemporalExpression z) {
                return x == null || y == null || z == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_BETWEEN, y, z);
            }

            public BooleanComparisonX isNullOrNotBetween(TemporalExpression x, TemporalExpression y, TemporalExpression z) {
                return x == null || y == null || z == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL_OR_NOT_BETWEEN, y, z);
            }

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Conditional">
        public class Conditional {

            public TemporalConditionalX then(BooleanExpression booleanExpression, java.util.Date value) {
                return booleanExpression == null || value == null ? null
                    : new TemporalConditionalX(booleanExpression, value);
            }

            public TemporalConditionalX then(BooleanExpression booleanExpression, SpecialTemporalValue value) {
                return booleanExpression == null || value == null ? null
                    : new TemporalConditionalX(booleanExpression, value);
            }

            public TemporalConditionalX then(BooleanExpression booleanExpression, TemporalExpression value) {
                return booleanExpression == null || value == null ? null
                    : new TemporalConditionalX(booleanExpression, value);
            }

            public TemporalOtherwiseX otherwise(TemporalConditionalX conditional, java.util.Date value) {
                return conditional == null || value == null ? null
                    : new TemporalOtherwiseX((ConditionalX) conditional, value);
            }

            public TemporalOtherwiseX otherwise(TemporalConditionalX conditional, SpecialTemporalValue value) {
                return conditional == null || value == null ? null
                    : new TemporalOtherwiseX((ConditionalX) conditional, value);
            }

            public TemporalOtherwiseX otherwise(TemporalConditionalX conditional, TemporalExpression value) {
                return conditional == null || value == null ? null
                    : new TemporalOtherwiseX((ConditionalX) conditional, value);
            }

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="DataAggregate">
        public class DataAggregate {

            public TemporalDataAggregateX coalesce(TemporalExpression operand1, TemporalExpression operand2, TemporalExpression... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new TemporalDataAggregateX(DataAggregateOp.COALESCE, operands);
            }

            public NumericDataAggregateX count(TemporalExpression operand1, TemporalExpression operand2, TemporalExpression... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new NumericDataAggregateX(DataAggregateOp.COUNT, operands);
            }

            public TemporalDataAggregateX max(TemporalExpression operand1, TemporalExpression operand2, TemporalExpression... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new TemporalDataAggregateX(DataAggregateOp.MAXIMUM, operands);
            }

            public TemporalDataAggregateX min(TemporalExpression operand1, TemporalExpression operand2, TemporalExpression... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                return operands == null ? null : new TemporalDataAggregateX(DataAggregateOp.MINIMUM, operands);
            }

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="OrderedPair">
        public class OrderedPair {

            public TemporalOrderedPairX coalesce(TemporalExpression x, java.util.Date y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.COALESCE, y);
            }

            public TemporalOrderedPairX coalesce(TemporalExpression x, SpecialTemporalValue y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.COALESCE, y);
            }

            public TemporalOrderedPairX coalesce(TemporalExpression x, TemporalExpression y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.COALESCE, y);
            }

            public TemporalOrderedPairX nullIf(TemporalExpression x, java.util.Date y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.NULLIF, y);
            }

            public TemporalOrderedPairX nullIf(TemporalExpression x, SpecialTemporalValue y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.NULLIF, y);
            }

            public TemporalOrderedPairX nullIf(TemporalExpression x, TemporalExpression y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.NULLIF, y);
            }

            public TemporalOrderedPairX max(TemporalExpression x, java.util.Date y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.MAXIMUM, y);
            }

            public TemporalOrderedPairX max(TemporalExpression x, SpecialTemporalValue y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.MAXIMUM, y);
            }

            public TemporalOrderedPairX max(TemporalExpression x, TemporalExpression y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.MAXIMUM, y);
            }

            public TemporalOrderedPairX min(TemporalExpression x, java.util.Date y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.MINIMUM, y);
            }

            public TemporalOrderedPairX min(TemporalExpression x, SpecialTemporalValue y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.MINIMUM, y);
            }

            public TemporalOrderedPairX min(TemporalExpression x, TemporalExpression y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.MINIMUM, y);
            }

            public TemporalOrderedPairX addYears(TemporalExpression x, Number y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.ADD_YEARS, y);
            }

            public TemporalOrderedPairX addYears(TemporalExpression x, NumericExpression y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.ADD_YEARS, y);
            }

            public TemporalOrderedPairX addMonths(TemporalExpression x, Number y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.ADD_MONTHS, y);
            }

            public TemporalOrderedPairX addMonths(TemporalExpression x, NumericExpression y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.ADD_MONTHS, y);
            }

            public TemporalOrderedPairX addWeeks(TemporalExpression x, Number y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.ADD_WEEKS, y);
            }

            public TemporalOrderedPairX addWeeks(TemporalExpression x, NumericExpression y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.ADD_WEEKS, y);
            }

            public TemporalOrderedPairX addDays(TemporalExpression x, Number y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.ADD_DAYS, y);
            }

            public TemporalOrderedPairX addDays(TemporalExpression x, NumericExpression y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.ADD_DAYS, y);
            }

            public TemporalOrderedPairX addHours(TemporalExpression x, Number y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.ADD_HOURS, y);
            }

            public TemporalOrderedPairX addHours(TemporalExpression x, NumericExpression y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.ADD_HOURS, y);
            }

            public TemporalOrderedPairX addMinutes(TemporalExpression x, Number y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.ADD_MINUTES, y);
            }

            public TemporalOrderedPairX addMinutes(TemporalExpression x, NumericExpression y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.ADD_MINUTES, y);
            }

            public TemporalOrderedPairX addSeconds(TemporalExpression x, Number y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.ADD_SECONDS, y);
            }

            public TemporalOrderedPairX addSeconds(TemporalExpression x, NumericExpression y) {
                return x == null || y == null ? null : new TemporalOrderedPairX(x, OrderedPairOp.ADD_SECONDS, y);
            }

            public NumericOrderedPairX diffInYears(TemporalExpression x, java.util.Date y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.DIFF_IN_YEARS, y);
            }

            public NumericOrderedPairX diffInYears(TemporalExpression x, SpecialTemporalValue y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.DIFF_IN_YEARS, y);
            }

            public NumericOrderedPairX diffInYears(TemporalExpression x, TemporalExpression y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.DIFF_IN_YEARS, y);
            }

            public NumericOrderedPairX diffInMonths(TemporalExpression x, java.util.Date y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.DIFF_IN_MONTHS, y);
            }

            public NumericOrderedPairX diffInMonths(TemporalExpression x, SpecialTemporalValue y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.DIFF_IN_MONTHS, y);
            }

            public NumericOrderedPairX diffInMonths(TemporalExpression x, TemporalExpression y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.DIFF_IN_MONTHS, y);
            }

            public NumericOrderedPairX diffInWeeks(TemporalExpression x, java.util.Date y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.DIFF_IN_WEEKS, y);
            }

            public NumericOrderedPairX diffInWeeks(TemporalExpression x, SpecialTemporalValue y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.DIFF_IN_WEEKS, y);
            }

            public NumericOrderedPairX diffInWeeks(TemporalExpression x, TemporalExpression y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.DIFF_IN_WEEKS, y);
            }

            public NumericOrderedPairX diffInDays(TemporalExpression x, java.util.Date y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.DIFF_IN_DAYS, y);
            }

            public NumericOrderedPairX diffInDays(TemporalExpression x, SpecialTemporalValue y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.DIFF_IN_DAYS, y);
            }

            public NumericOrderedPairX diffInDays(TemporalExpression x, TemporalExpression y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.DIFF_IN_DAYS, y);
            }

            public NumericOrderedPairX diffInHours(TemporalExpression x, java.util.Date y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.DIFF_IN_HOURS, y);
            }

            public NumericOrderedPairX diffInHours(TemporalExpression x, SpecialTemporalValue y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.DIFF_IN_HOURS, y);
            }

            public NumericOrderedPairX diffInHours(TemporalExpression x, TemporalExpression y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.DIFF_IN_HOURS, y);
            }

            public NumericOrderedPairX diffInMinutes(TemporalExpression x, java.util.Date y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.DIFF_IN_MINUTES, y);
            }

            public NumericOrderedPairX diffInMinutes(TemporalExpression x, SpecialTemporalValue y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.DIFF_IN_MINUTES, y);
            }

            public NumericOrderedPairX diffInMinutes(TemporalExpression x, TemporalExpression y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.DIFF_IN_MINUTES, y);
            }

            public NumericOrderedPairX diffInSeconds(TemporalExpression x, java.util.Date y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.DIFF_IN_SECONDS, y);
            }

            public NumericOrderedPairX diffInSeconds(TemporalExpression x, SpecialTemporalValue y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.DIFF_IN_SECONDS, y);
            }

            public NumericOrderedPairX diffInSeconds(TemporalExpression x, TemporalExpression y) {
                return x == null || y == null ? null : new NumericOrderedPairX(x, OrderedPairOp.DIFF_IN_SECONDS, y);
            }

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="RowsAggregate">
        public class RowsAggregate {

            // <editor-fold defaultstate="collapsed" desc="rowcount">
            public NumericRowsAggregateX rowcount(TemporalExpression measure) {
                return measure == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.COUNT, measure);
            }

            public NumericRowsAggregateX rowcount(TemporalExpression measure, Segment filter) {
                return measure == null || filter == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.COUNT, measure, filter);
            }

            public NumericRowsAggregateX rowcount(TemporalExpression measure, Entity dimension) {
                return measure == null || dimension == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.COUNT, measure, dimension);
            }

            public NumericRowsAggregateX rowcount(TemporalExpression measure, Segment filter, Entity dimension) {
                return measure == null || filter == null || dimension == null
                    ? null : new NumericRowsAggregateX(RowsAggregateOp.COUNT, measure, filter, dimension);
            }
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="maximum">
            public TemporalRowsAggregateX maximum(TemporalExpression measure) {
                return measure == null
                    ? null : new TemporalRowsAggregateX(RowsAggregateOp.MAXIMUM, measure);
            }

            public TemporalRowsAggregateX maximum(TemporalExpression measure, Segment filter) {
                return measure == null || filter == null
                    ? null : new TemporalRowsAggregateX(RowsAggregateOp.MAXIMUM, measure, filter);
            }

            public TemporalRowsAggregateX maximum(TemporalExpression measure, Entity dimension) {
                return measure == null || dimension == null
                    ? null : new TemporalRowsAggregateX(RowsAggregateOp.MAXIMUM, measure, dimension);
            }

            public TemporalRowsAggregateX maximum(TemporalExpression measure, Segment filter, Entity dimension) {
                return measure == null || filter == null || dimension == null
                    ? null : new TemporalRowsAggregateX(RowsAggregateOp.MAXIMUM, measure, filter, dimension);
            }
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="minimum">
            public TemporalRowsAggregateX minimum(TemporalExpression measure) {
                return measure == null
                    ? null : new TemporalRowsAggregateX(RowsAggregateOp.MINIMUM, measure);
            }

            public TemporalRowsAggregateX minimum(TemporalExpression measure, Segment filter) {
                return measure == null || filter == null
                    ? null : new TemporalRowsAggregateX(RowsAggregateOp.MINIMUM, measure, filter);
            }

            public TemporalRowsAggregateX minimum(TemporalExpression measure, Entity dimension) {
                return measure == null || dimension == null
                    ? null : new TemporalRowsAggregateX(RowsAggregateOp.MINIMUM, measure, dimension);
            }

            public TemporalRowsAggregateX minimum(TemporalExpression measure, Segment filter, Entity dimension) {
                return measure == null || filter == null || dimension == null
                    ? null : new TemporalRowsAggregateX(RowsAggregateOp.MINIMUM, measure, filter, dimension);
            }
            // </editor-fold>

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Scalar">
        public class Scalar {

            public TemporalScalarX defaultWhenNull(TemporalExpression x) {
                return x == null ? null : new TemporalScalarX(ScalarOp.DEFAULT_WHEN_NULL, x);
            }

            public TemporalScalarX nullWhenDefault(TemporalExpression x) {
                return x == null ? null : new TemporalScalarX(ScalarOp.NULL_WHEN_DEFAULT, x);
            }

            public TemporalScalarX toDate(Object x) {
                return x == null ? null : new TemporalScalarX(ScalarOp.TO_DATE, x);
            }

            public TemporalScalarX toTime(Object x) {
                return x == null ? null : new TemporalScalarX(ScalarOp.TO_TIME, x);
            }

            public TemporalScalarX toTimestamp(Object x) {
                return x == null ? null : new TemporalScalarX(ScalarOp.TO_TIMESTAMP, x);
            }

            public TemporalScalarX firstDateOfMonth(TemporalExpression x) {
                return x == null ? null : new TemporalScalarX(ScalarOp.FIRST_DATE_OF_MONTH, x);
            }

            public TemporalScalarX firstDateOfQuarter(TemporalExpression x) {
                return x == null ? null : new TemporalScalarX(ScalarOp.FIRST_DATE_OF_QUARTER, x);
            }

            public TemporalScalarX firstDateOfSemester(TemporalExpression x) {
                return x == null ? null : new TemporalScalarX(ScalarOp.FIRST_DATE_OF_SEMESTER, x);
            }

            public TemporalScalarX firstDateOfYear(TemporalExpression x) {
                return x == null ? null : new TemporalScalarX(ScalarOp.FIRST_DATE_OF_YEAR, x);
            }

            public TemporalScalarX lastDateOfMonth(TemporalExpression x) {
                return x == null ? null : new TemporalScalarX(ScalarOp.LAST_DATE_OF_MONTH, x);
            }

            public TemporalScalarX lastDateOfQuarter(TemporalExpression x) {
                return x == null ? null : new TemporalScalarX(ScalarOp.LAST_DATE_OF_QUARTER, x);
            }

            public TemporalScalarX lastDateOfSemester(TemporalExpression x) {
                return x == null ? null : new TemporalScalarX(ScalarOp.LAST_DATE_OF_SEMESTER, x);
            }

            public TemporalScalarX lastDateOfYear(TemporalExpression x) {
                return x == null ? null : new TemporalScalarX(ScalarOp.LAST_DATE_OF_YEAR, x);
            }

            public NumericScalarX extractYear(TemporalExpression x) {
                return x == null ? null : new NumericScalarX(ScalarOp.YEAR, x);
            }

            public NumericScalarX extractMonth(TemporalExpression x) {
                return x == null ? null : new NumericScalarX(ScalarOp.MONTH, x);
            }

            public NumericScalarX extractDay(TemporalExpression x) {
                return x == null ? null : new NumericScalarX(ScalarOp.DAY, x);
            }

            public NumericScalarX extractHour(TemporalExpression x) {
                return x == null ? null : new NumericScalarX(ScalarOp.HOUR, x);
            }

            public NumericScalarX extractMinute(TemporalExpression x) {
                return x == null ? null : new NumericScalarX(ScalarOp.MINUTE, x);
            }

            public NumericScalarX extractSecond(TemporalExpression x) {
                return x == null ? null : new NumericScalarX(ScalarOp.SECOND, x);
            }

        }
        // </editor-fold>

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="AnyType">
    public class AnyTypeExpressionBuilder {

        public Comparison Comparison = new Comparison();

        public DataAggregate DataAggregate = new DataAggregate();

        // <editor-fold defaultstate="collapsed" desc="Comparison">
        public class Comparison {

            public BooleanComparisonX exists(NativeQuery x) {
                return x == null ? null : new BooleanComparisonX(x, ComparisonOp.EXISTS);
            }

            public BooleanComparisonX notExists(NativeQuery x) {
                return x == null ? null : new BooleanComparisonX(x, ComparisonOp.NOT_EXISTS);
            }

            public BooleanComparisonX isNull(DataArtifact x) {
                return x == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NULL);
            }

            public BooleanComparisonX isNotNull(DataArtifact x) {
                return x == null ? null : new BooleanComparisonX(x, ComparisonOp.IS_NOT_NULL);
            }

        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="DataAggregate">
        public class DataAggregate {

            public BooleanDataAggregateX noneIsNull(DataArtifact operand1, DataArtifact operand2, DataArtifact... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                if (operands == null) {
                    return null;
                }
                for (int i = 0; i < operands.length; i++) {
                    operands[i] = new BooleanComparisonX(operands[i], ComparisonOp.IS_NOT_NULL);
                }
                return new BooleanDataAggregateX(DataAggregateOp.AND, operands);
            }

            public BooleanDataAggregateX atLeastOneIsNull(DataArtifact operand1, DataArtifact operand2, DataArtifact... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                if (operands == null) {
                    return null;
                }
                for (int i = 0; i < operands.length; i++) {
                    operands[i] = new BooleanComparisonX(operands[i], ComparisonOp.IS_NOT_NULL);
                }
                return new BooleanDataAggregateX(DataAggregateOp.NAND, operands);
            }

            public BooleanDataAggregateX atLeastOneIsNotNull(DataArtifact operand1, DataArtifact operand2, DataArtifact... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                if (operands == null) {
                    return null;
                }
                for (int i = 0; i < operands.length; i++) {
                    operands[i] = new BooleanComparisonX(operands[i], ComparisonOp.IS_NOT_NULL);
                }
                return new BooleanDataAggregateX(DataAggregateOp.OR, operands);
            }

            public BooleanDataAggregateX allAreNull(DataArtifact operand1, DataArtifact operand2, DataArtifact... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                if (operands == null) {
                    return null;
                }
                for (int i = 0; i < operands.length; i++) {
                    operands[i] = new BooleanComparisonX(operands[i], ComparisonOp.IS_NOT_NULL);
                }
                return new BooleanDataAggregateX(DataAggregateOp.NOR, operands);
            }

            public BooleanDataAggregateX oneAndOnlyOneIsNotNull(DataArtifact operand1, DataArtifact operand2, DataArtifact... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                if (operands == null) {
                    return null;
                }
                for (int i = 0; i < operands.length; i++) {
                    operands[i] = new BooleanComparisonX(operands[i], ComparisonOp.IS_NOT_NULL);
                }
                return new BooleanDataAggregateX(DataAggregateOp.NAXOR, operands);
            }

            public BooleanDataAggregateX noneOrMoreThanOneIsNotNull(DataArtifact operand1, DataArtifact operand2, DataArtifact... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                if (operands == null) {
                    return null;
                }
                for (int i = 0; i < operands.length; i++) {
                    operands[i] = new BooleanComparisonX(operands[i], ComparisonOp.IS_NOT_NULL);
                }
                return new BooleanDataAggregateX(DataAggregateOp.NAXNOR, operands);
            }

            public BooleanDataAggregateX noneOrOnlyOneIsNotNull(DataArtifact operand1, DataArtifact operand2, DataArtifact... extraOperands) {
                Object[] operands = aggregate(operand1, operand2, extraOperands);
                if (operands == null) {
                    return null;
                }
                for (int i = 0; i < operands.length; i++) {
                    operands[i] = new BooleanComparisonX(operands[i], ComparisonOp.IS_NOT_NULL);
                }
                return new BooleanDataAggregateX(DataAggregateOp.NOR_OR_NAXOR, operands);
            }

        }
        // </editor-fold>

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Scalar Expressions">
    public static EntityScalarX toEntity(Instance x) {
        return Entity.Scalar.cast(x);
    }

    public static BooleanScalarX not(BooleanExpression x) {
        return Boolean.Scalar.not(x);
    }

    public static BooleanScalarX toBoolean(Object x) {
        return Boolean.Scalar.toBoolean(x);
    }

    public static CharacterScalarX defaultWhenNull(CharacterExpression x) {
        return Character.Scalar.defaultWhenNull(x);
    }

    public static CharacterScalarX nullWhenDefault(CharacterExpression x) {
        return Character.Scalar.nullWhenDefault(x);
    }

    public static CharacterScalarX lower(CharacterExpression x) {
        return Character.Scalar.lower(x);
    }

    public static CharacterScalarX upper(CharacterExpression x) {
        return Character.Scalar.upper(x);
    }

    public static CharacterScalarX capitalize(CharacterExpression x) {
        return Character.Scalar.capitalize(x);
    }

    public static CharacterScalarX uncapitalize(CharacterExpression x) {
        return Character.Scalar.uncapitalize(x);
    }

    public static CharacterScalarX trim(CharacterExpression x) {
        return Character.Scalar.trim(x);
    }

    public static CharacterScalarX ltrim(CharacterExpression x) {
        return Character.Scalar.ltrim(x);
    }

    public static CharacterScalarX rtrim(CharacterExpression x) {
        return Character.Scalar.rtrim(x);
    }

    public static CharacterScalarX toChar(Object x) {
        return Character.Scalar.toChar(x);
    }

    public static CharacterScalarX toCharString(Object x) {
        return Character.Scalar.toCharString(x);
    }

    public static CharacterScalarX toLocaleString(Object x) {
        return Character.Scalar.toLocaleString(x);
    }

    public static NumericScalarX defaultWhenNull(NumericExpression x) {
        return Numeric.Scalar.defaultWhenNull(x);
    }

    public static NumericScalarX nullWhenDefault(NumericExpression x) {
        return Numeric.Scalar.nullWhenDefault(x);
    }

    public static NumericScalarX abs(NumericExpression x) {
        return Numeric.Scalar.abs(x);
    }

    public static NumericScalarX chs(NumericExpression x) {
        return Numeric.Scalar.chs(x);
    }

    public static NumericScalarX inv(NumericExpression x) {
        return Numeric.Scalar.inv(x);
    }

    public static NumericScalarX toBigDecimal(Object x) {
        return Numeric.Scalar.toBigDecimal(x);
    }

    public static NumericScalarX toBigInteger(Object x) {
        return Numeric.Scalar.toBigInteger(x);
    }

    public static NumericScalarX toByte(Object x) {
        return Numeric.Scalar.toByte(x);
    }

    public static NumericScalarX toShort(Object x) {
        return Numeric.Scalar.toShort(x);
    }

    public static NumericScalarX toInteger(Object x) {
        return Numeric.Scalar.toInteger(x);
    }

    public static NumericScalarX toLong(Object x) {
        return Numeric.Scalar.toLong(x);
    }

    public static NumericScalarX toDouble(Object x) {
        return Numeric.Scalar.toDouble(x);
    }

    public static NumericScalarX toFloat(Object x) {
        return Numeric.Scalar.toFloat(x);
    }

    public static TemporalScalarX defaultWhenNull(TemporalExpression x) {
        return Temporal.Scalar.defaultWhenNull(x);
    }

    public static TemporalScalarX nullWhenDefault(TemporalExpression x) {
        return Temporal.Scalar.nullWhenDefault(x);
    }

    public static TemporalScalarX toDate(Object x) {
        return Temporal.Scalar.toDate(x);
    }

    public static TemporalScalarX toTime(Object x) {
        return Temporal.Scalar.toTime(x);
    }

    public static TemporalScalarX toTimestamp(Object x) {
        return Temporal.Scalar.toTimestamp(x);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Special Fields">
    public static final BooleanScalarX NULL_BOOLEAN = new BooleanScalarX(null, SpecialBooleanValue.NULL);

    public static final BooleanScalarX TRUTH = new BooleanScalarX(null, SpecialBooleanValue.TRUE);

    public static final BooleanScalarX UNTRUTH = new BooleanScalarX(null, SpecialBooleanValue.FALSE);

    public static final CharacterScalarX NULL_STRING = new CharacterScalarX(null, SpecialCharacterValue.NULL);

    public static final CharacterScalarX EMPTY_STRING = new CharacterScalarX(null, SpecialCharacterValue.EMPTY);

    public static final CharacterScalarX EMPTY = new CharacterScalarX(null, SpecialCharacterValue.EMPTY);

    public static final CharacterScalarX CURRENT_USER_CODE = new CharacterScalarX(null, SpecialCharacterValue.CURRENT_USER_CODE);

    public static final NumericScalarX NULL_NUMBER = new NumericScalarX(null, SpecialNumericValue.NULL);

    public static final NumericScalarX CURRENT_USER_ID = new NumericScalarX(null, SpecialNumericValue.CURRENT_USER_ID);

    public static final TemporalScalarX NULL_TEMPORAL = new TemporalScalarX(null, SpecialTemporalValue.NULL);

    public static final TemporalScalarX CURRENT_DATE = new TemporalScalarX(null, SpecialTemporalValue.CURRENT_DATE);

    public static final TemporalScalarX CURRENT_TIME = new TemporalScalarX(null, SpecialTemporalValue.CURRENT_TIME);

    public static final TemporalScalarX CURRENT_TIMESTAMP = new TemporalScalarX(null, SpecialTemporalValue.CURRENT_TIMESTAMP);

    public static final EntityScalarX NULL_ENTITY = new EntityScalarX(null, SpecialEntityValue.NULL);
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Special Expressions">
    public static BooleanScalarX nullBoolean() {
        return NULL_BOOLEAN;
    }

    public static BooleanScalarX truth() {
        return TRUTH;
    }

    public static BooleanScalarX untruth() {
        return UNTRUTH;
    }

    public static CharacterScalarX nullString() {
        return NULL_STRING;
    }

    public static CharacterScalarX emptyString() {
        return EMPTY_STRING;
    }

    public static CharacterScalarX empty() {
        return EMPTY;
    }

    public static CharacterScalarX currentUserCode() {
        return CURRENT_USER_CODE;
    }

    public static NumericScalarX nullNumber() {
        return NULL_NUMBER;
    }

    public static NumericScalarX currentUserId() {
        return CURRENT_USER_ID;
    }

    public static TemporalScalarX nullTemporal() {
        return NULL_TEMPORAL;
    }

    public static TemporalScalarX currentDate() {
        return CURRENT_DATE;
    }

    public static TemporalScalarX currentTime() {
        return CURRENT_TIME;
    }

    public static TemporalScalarX currentTimestamp() {
        return CURRENT_TIMESTAMP;
    }

    public static EntityScalarX nullEntity() {
        return NULL_ENTITY;
    }
    // </editor-fold>

}
