package com.merckor.activitytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        //注册button事件
        buttonClickTest();
        buttonClickExit();
        buttonClickStartActivity();
    }

    private void buttonClickTest(){
        Button button1 = (Button)findViewById(R.id.button_1);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(FirstActivity.this,"You Clicked Button 1",Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void buttonClickExit(){
        Button buttonExit = (Button)findViewById(R.id.exit);
        buttonExit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    protected void buttonClickStartActivity(){
        final Button startActivity = (Button)findViewById(R.id.start_activity);
        startActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //显示的Intent
//                Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
//                startActivity(intent);
                //隐式的Intent
                Intent intent = new Intent("com.merckor.activitytest.ACTION_START");
                intent.addCategory("com.merckor.activitytest.MY_CATEGORY");
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                Toast.makeText(FirstActivity.this,"You Clicked Add Item",Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(FirstActivity.this,"You Clicked Remove Item",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }
}
