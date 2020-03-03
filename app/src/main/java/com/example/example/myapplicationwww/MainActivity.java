package com.example.example.myapplicationwww;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.RemoteAction;
import android.app.Service;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View x) {
        Intent intent = new Intent("com.ser");
        intent.setPackage("com.example.example.myapplication");
        bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);


    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {


            Messenger messenger = new Messenger(service);
            Message message = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putString("activity", "今天出去玩吗？");
            message.setData(bundle);
            //在message中添加一个回复mRelyMessenger对象
            message.replyTo = mRelyMessenger;
            try {
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    Handler handler=new GetRelyHandler();


    private Messenger mRelyMessenger = new Messenger(handler);

    public  class GetRelyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String serviceMsg = bundle.getString("service");
            Log.i("123123", "来自服务端的回复：" + serviceMsg);
            Toast.makeText(getApplicationContext(),"收到消息"+serviceMsg,Toast.LENGTH_LONG).show();


        }
    }
}
