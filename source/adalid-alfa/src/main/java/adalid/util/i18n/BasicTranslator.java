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
package adalid.util.i18n;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import java.util.Locale;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class BasicTranslator {

    private static final Logger logger = Logger.getLogger(BasicTranslator.class);

    private Translate.TranslateOption sourceLanguage, targetLanguage;

    private Translate service;

    private int incomingCharCount, outgoingCharCount;

    public void build(Locale sourceLocale, Locale targetLocale) {
        build(sourceLocale == null ? null : sourceLocale.getLanguage(), targetLocale == null ? null : targetLocale.getLanguage());
    }

    public void build(String sourceLanguageCode, String targetLanguageCode) {
        if (service == null) {
            String slc = StringUtils.defaultIfBlank(sourceLanguageCode, "es");
            String tlc = StringUtils.defaultIfBlank(targetLanguageCode, "en");
            sourceLanguage = Translate.TranslateOption.sourceLanguage(slc);
            targetLanguage = Translate.TranslateOption.sourceLanguage(tlc);
            service = TranslateOptions.newBuilder().setTargetLanguage(tlc).build().getService();
            logger.info("source-language = " + sourceLanguage);
            logger.info("target-language = " + targetLanguage);
            logger.info("service = " + service);
        }
    }

    public void close() {
        logger.info("incoming-char-count = " + incomingCharCount);
        logger.info("outgoing-char-count = " + outgoingCharCount);
    }

    public String translate(String string) {
        if (StringUtils.isBlank(string)) {
            return string;
        }
        String strong = service.translate(replaceSlashedChars(string), sourceLanguage).getTranslatedText();
        strong = StringEscapeUtils.unescapeHtml(strong);
        incomingCharCount += string.length();
        outgoingCharCount += strong.length();
        return restoreSlashedChars(strong);
    }

    public int getIncomingCharCount() {
        return incomingCharCount;
    }

    public int getOutgoingCharCount() {
        return outgoingCharCount;
    }

    private static final String BS = "\b", HT = "\t", LF = "\n", FF = "\f", CR = "\r", DQ = "\"";

    private static final String bs = "[b]", ht = "[t]", lf = "[n]", ff = "[f]", cr = "[r]", dq = "[q]";

    private static final String[] slashedChars = {BS, HT, LF, FF, CR, DQ};

    private static final String[] replacements = {bs, ht, lf, ff, cr, dq};

    private String replaceSlashedChars(String string) {
        return StringUtils.replaceEach(string, slashedChars, replacements).trim();
    }

    private String restoreSlashedChars(String string) {
        return StringUtils.replaceEach(string, replacements, slashedChars);
    }

}
