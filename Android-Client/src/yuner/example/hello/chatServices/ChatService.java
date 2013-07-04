/**
 * the background Service that process the message sending by sendMyMessage()
 * and coming message by newMsgArrive()
 * 
 * it also manage the message queue for ChatActivity
 */

package yuner.example.hello.chatServices;

import java.util.ArrayList;
import java.util.List;

import yuner.example.hello.ConnectedApp;
import yuner.example.hello.chatter.ChatActivity;
import yuner.example.hello.myNetwork.ChatEntity;
import yuner.example.hello.myNetwork.NetConnect;
import yuner.example.hello.myNetwork.UserInfo;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;

public class ChatService extends Service {
	
	private ChatActivity mChatActv = null;
	private ChatMsgReceiver mMsgReceiver = null;
	private ChatBinder mBinder = new ChatBinder();
	
	private int mCurType = 0; // 0 for public room (default), 1 for group room, 2 for friend chatting
	private UserInfo mMyUserInfo;
	
	private List<ChatEntity> mMsgs;
	private List<Boolean> mIsSelf;
	
	private int mFriendId;
	
	private NetConnect mNetCon;
	
	public class ChatBinder extends Binder
	{
		public ChatService getService()
		{
			return ChatService.this;
		}
	}
	
	@Override
	public IBinder onBind(Intent intent)
	{
		return mBinder;
	}
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		mMsgs=new ArrayList<ChatEntity>();
		mIsSelf = new ArrayList<Boolean>();
		
		ConnectedApp connected_app0  =  (ConnectedApp)getApplication();
		connected_app0.setChatService(this);
		
		IntentFilter ifter=new IntentFilter("yuner.example.hello.MESSAGE_RECEIVED");
		mMsgReceiver=new ChatMsgReceiver(this);
		ChatService.this.registerReceiver(mMsgReceiver,ifter);
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		ChatService.this.unregisterReceiver(mMsgReceiver);
		mNetCon.closeNetConnect();
		((ConnectedApp)getApplication()).setChatService(null);
	}
	
	/* this function will be called after NetCOn and mUserInfo in MainActivity has been successfully set up */
	public void readGlobalInfo() {
		ConnectedApp connected_app0  =  (ConnectedApp)getApplication();
		mNetCon=connected_app0.getConnect();
		mMyUserInfo = connected_app0.getUserInfo();
	}
	
	public void setBoundChatActivity(ChatActivity act0)
	{
		mChatActv=act0;
	}
	
	public void setType(int type) {
		mCurType = type;
	}
	
	public void setId(int id) {
		mFriendId = id;
	}
	
	public void sendMyMessage(String st0)
	{
    	String div=ChatEntity.strSplitter;
    	String toSend = mCurType+div+mMyUserInfo.getId()+div+
				mMyUserInfo.getAvatarId()+div+mMyUserInfo.getName()+div+
				mMyUserInfo.getSex()+div+ChatEntity.genDate()+div+
				st0+div;
    	switch(mCurType) {
    	case 0:
    		toSend += 0;
    		break;
    	case 2:
    		toSend += mFriendId;
    		break;
		default:
			break;
    	}
    	ChatEntity ent0=ChatEntity.Str2Ent(toSend);
    	mNetCon.sendUpload(mCurType, ent0);
		
    	newMsgArrive(ent0.toString(),true);
	}
	
	public void newMsgArrive(String str0, boolean isSelf)
	{
		ChatEntity msgEntity=ChatEntity.Str2Ent(str0);
		
		int type = msgEntity.getType();
		int id = msgEntity.getSenderId();
		if(isSelf) {
			id = mFriendId;
		}
		
		ChatServiceData.getInstance().getCurMsg(type, id).add(msgEntity);		
		ChatServiceData.getInstance().getCurIsSelf(type, id).add(isSelf);
		chatActivityDisplayHistory();		
	}
	
	public void chatActivityDisplayHistory() {
		if(mChatActv == null) {
			return;
		}
		if(mChatActv.getIsActive()) {
			List<ChatEntity> msgs = ChatServiceData.getInstance().getCurMsg(mCurType, mFriendId);
			List<Boolean> isSelf = ChatServiceData.getInstance().getCurIsSelf(mCurType, mFriendId);
			mChatActv.updateListviewHistory(msgs,isSelf);
		}		
	}

}
