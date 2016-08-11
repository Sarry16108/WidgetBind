package com.example.yanghj.widgetbind;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;

import bind.ClickBind;
import bind.ContentViewBind;
import bind.InjectUtils;
import bind.WidgetBind;
import bind.WidgetViewBind;

@ContentViewBind(R.layout.activity_main)
public class MainActivity extends Activity implements View.OnClickListener{

    @WidgetViewBind(R.id.button1)
    private Button  mButton1;
    @WidgetBind(value = R.id.button2, onclick = true)
    private Button  mButton2;
    @WidgetBind(value = R.id.button3, onclick = false)
    private Button  mButton3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InjectUtils.bind(this);
    }

    @ClickBind(R.id.button1)
    private void onButtonClick(View view) {
        Snackbar.make(view, "click:" + ((Button)view).getText(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onClick(View v) {
        Snackbar.make(v, "click:" + ((Button)v).getText(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
