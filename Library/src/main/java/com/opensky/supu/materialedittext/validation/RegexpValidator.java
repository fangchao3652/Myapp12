package com.opensky.supu.materialedittext.validation;

import android.support.annotation.NonNull;

import java.util.regex.Pattern;


/**
 * 自定义验证类（正则表达式）
 *
 * @author zhangxh
 */
public class RegexpValidator extends METValidator {

    private Pattern pattern;

    public RegexpValidator(@NonNull String errorMessage, @NonNull String regex) {
        super(errorMessage);
        pattern = Pattern.compile(regex);
    }

    @Override
    public boolean isValid(@NonNull CharSequence text, boolean isEmpty) {
        return pattern.matcher(text).matches();
    }
}
