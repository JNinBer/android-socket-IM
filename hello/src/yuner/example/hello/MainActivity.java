/**
 * main portal/entry for this app, 
 * it collects user information, 
 * 		instantiate the ConnectedApp, 
 * 		establish socket connection with our server
 * 		and start activity for the next stage
 */

package yuner.example.hello;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.*;
import android.app.Notification.Builder;
import android.view.*;
import android.view.View.*;
import android.widget.*;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;

import java.io.*;
import java.net.*;

import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.*;
import android.os.*;
import java.lang.Exception;
import java.util.*;

import yuner.example.hello.myNetwork.NetConnect;
import yuner.example.hello.myNetwork.ChatEntity;
import yuner.example.hello.myNetwork.UserInfo;

import yuner.example.hello.chatChoose.ChooseRoomActivity;
import yuner.example.hello.chatServices.ChatService;
import yuner.example.hello.chatServices.ChatServiceData;
import yuner.example.hello.chatServices.FriendListInfo;
import yuner.example.hello.chatServices.InitData;
import yuner.example.hello.chatter.ChatActivity;

/**
 * MainActivity is the entry activity, which provides a login page set to collect user information
 * and verification of username/password
 * 
 * @version 6 June 2013
 * @author wangqingyun
 */
public class MainActivity extends Activity {
	
	private String mName;
	private int mSex;   // 0 for lady, 1 for guy
	private UserInfo mUserInfo;
	private NetConnect mNetCon;
	
	private RadioGroup mRgpSex;
	private RadioButton mRbtnFemale;
	private RadioButton mRbtnMale;
	private EditText mEtAccount;
	private Button mBtnLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){ 
		
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main_login);
		
		mRgpSex=(RadioGroup)findViewById(R.id.mainLoginRgpChooseSex);
		mRbtnFemale=(RadioButton)findViewById(R.id.mainLoginRdBtnFemale);
		mRbtnMale=(RadioButton)findViewById(R.id.mainLoginRdBtnMale);
		mEtAccount=(EditText)findViewById(R.id.mainLoginEditAccount);
		mBtnLogin=(Button)findViewById(R.id.mainLoginBtn);
		
		mBtnLogin.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				MainActivity.this.tryLogin();
			}	
		});
		
		Intent intentTemp = new Intent(MainActivity.this,ChatService.class);
		startService(intentTemp);
	}
	
	@Override
    protected void onDestroy()
    {
    	super.onDestroy();
    	
    	// destroy service once it quits
    	Intent intentTemp = new Intent(MainActivity.this,ChatService.class);
    	stopService(intentTemp);
    }
	
	public void tryLogin()
	{
		int sexChoseId=mRgpSex.getCheckedRadioButtonId();
		switch(sexChoseId){
			case R.id.mainLoginRdBtnFemale:
				mSex=0;
				break;
			case R.id.mainLoginRdBtnMale:
				mSex=1;
				break;
			default:
				mSex=-1;
				break;
		}
		mName=mEtAccount.getText().toString();
		
		if(mSex==-1 || mName.equals(""))
		{//Please Specify Your Name and Sex"
			Toast.makeText(MainActivity.this,"Please Specify Your Name and Sex", Toast.LENGTH_LONG).show();
		}
		else
		{			
			mUserInfo = new UserInfo(mName,0,mSex,0,0);
			
			/*  if mNetcon is connected already, close it first  */
			/*  here we use try because mNetCon might not have been instantiated yet  */
			try {
				if(mNetCon.connectedOrNot()) {
					mNetCon.closeNetConnect();
				}
				InitData.closeInitData();
				FriendListInfo.closeFriendListInfo();
				ChatServiceData.closeChatServiceData();
			} catch (Exception e) {}
			
			/*  to establish a new connect  */
			mNetCon = NetConnect.getnetConnect(MainActivity.this,mUserInfo);			
			mNetCon.start();
			try {
				mNetCon.join();
			} catch(Exception e) {}
			
			if(!mNetCon.connectedOrNot())
			{
				mNetCon.closeNetConnect();
				Toast.makeText(this,"failed to connect to Server", Toast.LENGTH_LONG).show();
				return;
			}
			
			InitData initData = InitData.getInitData();
			initData.start();
			try {
				initData.join();
			} catch(Exception e) {}
			mUserInfo = initData.getUserInfo();
			
			ConnectedApp connected_app0  =  (ConnectedApp)getApplication();
		    connected_app0.setConnect(mNetCon);
		    connected_app0.setUserInfo(mUserInfo);
		    connected_app0.clearListActivity();
		    connected_app0.instantiateListActivity();
		    connected_app0.getChatService().readGlobalInfo();
			
	//		Intent intent0=new Intent(MainActivity.this,ChatActivity.class);
		    Intent intent0=new Intent(MainActivity.this,ChooseRoomActivity.class);
			intent0.putExtra("username", mUserInfo.getName());
			intent0.putExtra("usersex", mUserInfo.getSex());
			startActivity(intent0);
		}
	}
	
}