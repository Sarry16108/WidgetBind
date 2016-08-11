package bind;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yanghj on 16/8/9.
 * widget view binder
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WidgetViewBind {      //todo：在使用的时候，相当于一个类似于private/public/protected的修饰符
    int value();
}
