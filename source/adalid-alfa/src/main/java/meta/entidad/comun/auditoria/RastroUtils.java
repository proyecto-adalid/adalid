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
package meta.entidad.comun.auditoria;

import adalid.core.interfaces.*;
import adalid.jee2.constants.*;
import meta.entidad.comun.configuracion.basica.CondicionEjeFun;

/**
 * @author Jorge Campins
 */
public class RastroUtils {

    protected static void setGraphicImageExpressions(CondicionEjeFun condicionEjeFun) { // protected avoids method never unused warning
        final String NULL = fa(FA.NULL_VALUE + FA.WITH_FIXED_WIDTH + CSS.STATUS_NULL_VALUE_IMAGE);
        final String fa11 = fa(W3.TEXT_INDIGO + FA.CHECK_CIRCLE + FA.WITH_SIZE_LG);
        final String fa12 = fa(W3.TEXT_BLUE + FA.CHECK_CIRCLE + FA.WITH_SIZE_LG);
        final String fa21 = fa(W3.TEXT_GREEN + FA.CHECK_CIRCLE + FA.WITH_SIZE_LG);
        final String fa22 = fa(W3.TEXT_DEEP_ORANGE + FA.EXCLAMATION_CIRCLE + FA.WITH_SIZE_LG);
        final String fa23 = fa(W3.TEXT_PINK + FA.EXCLAMATION_CIRCLE + FA.WITH_SIZE_LG);
        final String fa99 = fa(W3.TEXT_RED + FA.EXCLAMATION_CIRCLE + FA.WITH_SIZE_LG);
        final BooleanExpression nil = condicionEjeFun.isNull();
        final BooleanExpression c11 = condicionEjeFun.isEqualTo(condicionEjeFun.EJECUCION_PENDIENTE);
        final BooleanExpression c12 = condicionEjeFun.isEqualTo(condicionEjeFun.EJECUCION_EN_PROGRESO);
        final BooleanExpression c21 = condicionEjeFun.isEqualTo(condicionEjeFun.EJECUTADO_SIN_ERRORES);
        final BooleanExpression c22 = condicionEjeFun.isEqualTo(condicionEjeFun.EJECUTADO_CON_ERRORES);
        final BooleanExpression c23 = condicionEjeFun.isEqualTo(condicionEjeFun.EJECUCION_CANCELADA);
        final CharacterExpression expression = nil.then(NULL).
            otherwise(c11.then(fa11).
                otherwise(c12.then(fa12).
                    otherwise(c21.then(fa21).
                        otherwise(c22.then(fa22).
                            otherwise(c23.then(fa23).
                                otherwise(fa99))))));
        /**/
        condicionEjeFun.setGraphicImageFontAwesomeClassNameExpression(expression);
        /*
        condicionEjeFun.setMissingValueGraphicImageName(fa(FA.MISSING_VALUE + FA.WITH_SIZE_LG));
        condicionEjeFun.setNullValueGraphicImageName(fa(FA.NULL_VALUE + FA.WITH_SIZE_LG));
        condicionEjeFun.setUnnecessaryValueGraphicImageName(fa(FA.UNNECESSARY_VALUE + FA.WITH_SIZE_LG));
        /**/
    }

    private static String fa(String name) {
        return name.trim().replaceAll(" +", " ");
    }

}
