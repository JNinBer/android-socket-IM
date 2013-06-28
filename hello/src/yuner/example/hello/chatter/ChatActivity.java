/**
 * ChatActivity is the chatting page
 * in this activity, only basic matters associated with the presentation of ui is processed, including
 * 		a ServiceConnection with the chatting service
 * 		a TextWatcher to auto-tweak the height of the input area
 * 		two OnClickListener to respond to the two buttons (one is ImageView)
 * 		one updateListviewHistory to be called to update the chatting history
 * 		also, on Pause and onResume to control the binding to the service
 */

package yuner.example.hello.chatter;

import android.os.Bundle;
import android.app.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;

import android.text.Editable;
import android.text.TextWatcher;
import android.os.*;
import java.util.*;

import yuner.example.hello.chatServices.ChatService;
import yuner.example.hello.myNetwork.ChatEntity;
import yuner.example.hello.myNetwork.UserInfo;

import yuner.example.hello.ConnectedApp;
import yuner.example.hello.R;

public class ChatActivity extends Activity{
	
	private boolean mIsActive = false;
	
	private Handler mHandler;
	
	/* views contained in this activity */
	private ListView mListviewHistory=null;  
	private EditText mEtInput=null;
	private Button mBtnSend=null;
	private ImageView mImvReadymade=null;
	private ReadyMadeDialog mRmDialog;
	
	/* records of current line amount and current height of the layout contains etDo */
	private LinearLayout mLlEtDo;
	private int mHeightOfTextLayout=0; // height of layout contains etDo in pixel
	private int mCurLineAcc; // current line account
	
	/* PublicService and service connection defined here */
	private ChatService mPublicService;
	private ServiceConnection mServiceConnect = new ServiceConnection()
	{
		@Override
		public void onServiceConnected(ComponentName name, IBinder binder)
		{			
			mPublicService = ((ChatService.ChatBinder)binder).getService();
			mPublicService.setBoundChatActivity(ChatActivity.this);
			new ToDisplayHistory(mHandler).start();
		}
		
		@Override
		public void onServiceDisconnected(ComponentName name){}
	};
	
	/* instantiate a TextWatcher to auto-tweak the height of input editText */
    private TextWatcher mTextWatcher = new TextWatcher()
    {
		public void afterTextChanged(Editable s)
		{
			int LineAcc=ChatActivity.this.mEtInput.getLineCount();
			if(LineAcc>ChatActivity.this.mCurLineAcc)
			{
				int lineIncre=LineAcc-ChatActivity.this.mCurLineAcc;
				ChatActivity.this.mHeightOfTextLayout+=lineIncre*ChatActivity.this.mEtInput.getLineHeight();
				LinearLayout.LayoutParams imagebtn_params = new LinearLayout.LayoutParams(
		                  LinearLayout.LayoutParams.MATCH_PARENT, 
		                  LinearLayout.LayoutParams.WRAP_CONTENT);
				imagebtn_params.height =ChatActivity.this.mHeightOfTextLayout;
				ChatActivity.this.mLlEtDo.setLayoutParams(imagebtn_params);
				
				ChatActivity.this.mCurLineAcc=LineAcc;
			}
		}
		public void onTextChanged(CharSequence s, int start, int before, int count){}
		public void beforeTextChanged(CharSequence s, int start, int count, int after)
		{
			ChatActivity.this.mHeightOfTextLayout=ChatActivity.this.mLlEtDo.getHeight();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState){ 	
		
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);	
		setContentView(R.layout.cb0_chat);
			
		mListviewHistory=(ListView)findViewById(R.id.cb0ChatListview);
		mEtInput=(EditText)findViewById(R.id.cb0ChatMsgInput);
		mBtnSend=(Button)findViewById(R.id.cb0ChatBtnSendMsg);
		mImvReadymade=(ImageView)findViewById(R.id.cb0ChatImvReadymade);
		
		// pop this activity into listActivity define in ConnectedApp
		ConnectedApp connected_app0  =  (ConnectedApp)getApplication();
	    connected_app0.addItemIntoListActivity(this);
		
		/* add TextWatcher to watch for text line account changing */
		mLlEtDo=(LinearLayout)findViewById(R.id.cb0ChatLayoutMsg);
		mCurLineAcc=1;		
		mEtInput.addTextChangedListener(mTextWatcher);
		
		mImvReadymade.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				mRmDialog=new ReadyMadeDialog(ChatActivity.this,mEtInput);
				mRmDialog.createReadyMadeDialog();
			}
		});
		
		mBtnSend.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				String st0=mEtInput.getText().toString();

				mEtInput.setText("");
				
				if(!st0.equals(""))
				{
					LinearLayout.LayoutParams imagebtn_params = new LinearLayout.LayoutParams(
			                  LinearLayout.LayoutParams.MATCH_PARENT, 
			                  LinearLayout.LayoutParams.WRAP_CONTENT);
					imagebtn_params.height =mLlEtDo.getHeight()-(mCurLineAcc-1)*(mEtInput.getLineHeight());
					mLlEtDo.setLayoutParams(imagebtn_params);
					mCurLineAcc=1;
				}
				
				if(!st0.equals(""))
				{
					mPublicService.sendMyMessage(st0);		
				}
			}
		});
		
		mHandler = new Handler(){  
			 
	        public void handleMessage(Message msg) {  
	            switch (msg.what) {      
	            case 1:      
	            	mPublicService.chatActivityDisplayHistory();  
	                break;      
	            }      
	            super.handleMessage(msg);  
	        }  
	          
	    };
	}
	
	@Override
    protected void onResume() {
        super.onResume();
        mIsActive = true;
        Intent intentTemp = new Intent(ChatActivity.this,ChatService.class);
        bindService(intentTemp,mServiceConnect,Service.BIND_AUTO_CREATE);
    }
	
    @Override
    protected void onPause() {
        super.onPause();
        mIsActive = false;
        unbindService(mServiceConnect);
    }
    
    public boolean getIsActive() {
    	return mIsActive;
    }
    
    @Override
    protected void onDestroy()
    {
    	super.onDestroy();
    }
    
    /**
     * update the history listview with the coming of new message
     */
    public void updateListviewHistory(List<ChatEntity> msgs, List<Boolean> isSelf)
    {
    	mListviewHistory.setAdapter(new ChatListviewAdapter(this,this,msgs,isSelf));			
		mListviewHistory.setSelection(msgs.size()-1);
    }
}

class ToDisplayHistory extends Thread{
	private Handler mHandler;
	
	public ToDisplayHistory(Handler hl0) {
		mHandler = hl0;
	}
	
	@Override
	public void run() {
		try {
			sleep(15);
		} catch(Exception e) {}
		Message message = new Message();      
        message.what = 1;
		mHandler.sendMessage(message);
	}
}