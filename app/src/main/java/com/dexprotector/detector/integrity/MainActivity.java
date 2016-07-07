package com.dexprotector.detector.integrity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    static boolean checkTamper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView)findViewById(R.id.text);
        doProbe(this);
        tv.setText("Tamper detection result: " + checkTamper+"\n");
    }

    public void doProbe(Context ctx){
        System.out.println("doProbe"+ctx);
    }

    public static void positiveTamperCheck(Object data){
        System.out.println("positiveTamperCheck");
        System.out.println("data:"+data);
        checkTamper = true;
    }
    public static void negativeTamperCheck(Object data){
        System.out.println("negativeTamperCheck");
        System.out.println("data:"+data);
        checkTamper = false;
    }
}

