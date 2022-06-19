
package sixteen.dengxili.tjfsu.mylocation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class BaseActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceStace){
        super.onCreate(savedInstanceStace);
        ActivityCollector.addActivity(this);
    }

    protected void onDestroy(Bundle savedInstanceStace){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}