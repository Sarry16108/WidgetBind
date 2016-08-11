package bind;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by yanghj on 16/8/9.
 */
public class InjectUtils {

    /*
    在Method类中有一个public成员函数：Object invoke(Object receiver, Object... args)，
    参数receiver指明了调用对象，参数args指明了该方法所需要接收的参数。由于我们是在运行时动态的调用类的方法，
    无法提前知道该类的参数类型和返回值类型，所以传入的参数的类型是Object，返回的类型也是Object。
     */
    public static void bind(@NonNull final Activity activity) {
        Class<?> activityClass = activity.getClass();

        if (activityClass.isAnnotationPresent(ContentViewBind.class)) {
            ContentViewBind bind = activityClass.getAnnotation(ContentViewBind.class);
            setContentView(activity, bind.value());
        }

        Field[] fields = activityClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(WidgetViewBind.class)) {
                WidgetViewBind widgetViewBind = field.getAnnotation(WidgetViewBind.class);
                findViewById(activity, field, widgetViewBind.value());
            } else if (field.isAnnotationPresent(WidgetBind.class)) {
                initWidget(activity, field);
            }
        }

        Method[] methods = activityClass.getDeclaredMethods();
        for (final Method method : methods) {
            if (method.isAnnotationPresent(ClickBind.class)) {
                ClickBind clickBind = method.getAnnotation(ClickBind.class);
                int [] ids = clickBind.value();
                for (int id : ids) {
                    View view = activity.findViewById(id);
                    if (null != view) {
                        method.setAccessible(true);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    method.invoke(activity, v);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }

            }
        }

    }

    private static void setContentView(Activity activity, int id) {
        activity.setContentView(id);
    }

    private static void findViewById(Activity activity, Field field, int id) {
        field.setAccessible(true);
        try {
            /*
            对类的成员变量进行读写，在Field类中有两个public方法：
        　　Object get(Object object)，该方法可用于获取某成员变量的值
        　　Void set(Object object, Object value)，该方法设置某成员变量的值
        　　其中，Object参数是需要传入的对象；如果成员变量是静态属性，在object可传入null。
             */
            field.set(activity, activity.findViewById(id));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new NullPointerException();
        }
    }

    private static void initWidget(Activity activity, Field field) {
        field.setAccessible(true);
        WidgetBind widgetBind = field.getAnnotation(WidgetBind.class);
        try {
            View view = activity.findViewById(widgetBind.value());
            //创建对象实例
            field.set(activity, view);
            view.setOnClickListener((View.OnClickListener) activity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
