package bind;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yanghj on 16/8/11.
 * supply object initialization and click event binding, needs the activity implements View.OnClickListener first
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WidgetBind {
    int value();
    boolean onclick() default false;
}
