package com.merckor.activitytest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity implements TipViewController.ViewDismissHandler{

    private static final String TAG = "FirstActivity";
    private TipViewController mTipViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        //注册button事件
        buttonClickTest();
        buttonClickExit();
        buttonClickStartActivity();
        buttonClickOpenUrl();
        buttonAddNearFriend();
        buttonOpenFloatWindow();
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

    private void buttonOpenFloatWindow(){
        Button btn = (Button)findViewById(R.id.open_float_window);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (mTipViewController != null) {
                    mTipViewController.updateContent("fuck");
                } else {
                    mTipViewController = new TipViewController(getApplication(), "fuck123");
                    mTipViewController.setViewDismissHandler(FirstActivity.this);
                    mTipViewController.show();
                }
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
                Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
                String data = "Hello Second Activity";
                intent.putExtra("extra_data",data);
                startActivityForResult(intent,1000);
            }
        });
    }

    protected void buttonClickOpenUrl(){
        Button buttonExit = (Button)findViewById(R.id.open_url);
        buttonExit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
            }
        });
    }

    protected void buttonAddNearFriend(){
        Button buttonExit = (Button)findViewById(R.id.add_near_friend);
        buttonExit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(FirstActivity.this,"功能暂时没有实现",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: "+data);
        if (resultCode != RESULT_CANCELED && data != null){
            switch (requestCode) {
                case 1000:
                    if (resultCode == RESULT_OK) {
                        Toast.makeText(FirstActivity.this, data.getStringExtra("data_return"), Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
            }
        }
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

    public static void startForContent(Context context, String content) {
        Intent intent = new Intent(context, FirstActivity.class);
        intent.putExtra("content", content);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onViewDismiss() {
        mTipViewController = null;
    }
}
