package yuner.example.hello.chatServices;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import yuner.example.hello.myNetwork.ChatEntity;
import yuner.example.hello.myNetwork.UserInfo;

public class ChatServiceData {

	private static ChatServiceData mChatServiceData;
	
	/* presumed maximum friends a user can have */
	private static final int maxFriends = 2000;
	
	// List for public room
	private List<ChatEntity> mMsgs;
	private List<Boolean> mIsSelf;

	/* List for friend chatting */
	private List<Integer> mFriendIds;
	private List<List<ChatEntity>> mFriendMsgs;
	private List<List<Boolean>> mFriendIsSelf;
	
	/* the index of an id corresponds to */
	private int mIndexForId[];
	
	public static ChatServiceData getInstance() {
		if(mChatServiceData == null) {
			mChatServiceData = new ChatServiceData();
		}
		return mChatServiceData;
	}
	
	private ChatServiceData() {
		
		mMsgs=new ArrayList<ChatEntity>();
		mIsSelf = new ArrayList<Boolean>();
		
		mFriendIds = new ArrayList<Integer>();
		mFriendMsgs = new ArrayList<List<ChatEntity>>();
		mFriendIsSelf = new ArrayList<List<Boolean>>();
		
		mIndexForId = new int[maxFriends];
	}
	
	public void newUser(UserInfo userInfo) {
		int id = userInfo.getId();
		int pos = mFriendIds.size();
		
		mIndexForId[id] = pos;
		mFriendIds.add(id);
		
		mFriendMsgs.add(new ArrayList<ChatEntity>());
		mFriendIsSelf.add(new ArrayList<Boolean>());
	}
	
	public void offLineUser(UserInfo userInfo) {
		int id = userInfo.getId();
		int pos = mIndexForId[id];
		
		mIndexForId[id] = 0;
		mFriendIds.remove(pos);
		mFriendMsgs.remove(pos);
		mFriendIsSelf.remove(pos);
	}
	
	public int getIdForPos(int pos) {
		int id = Integer.valueOf(mFriendIds.get(pos));
		return id;
	}
	
	public int getPosForId(int id) {
		int pos = mIndexForId[id];
		return pos;
	}
	
	public List<ChatEntity> getCurMsg(int type, int id) {
		if(type == 0) {
			return mMsgs;
		} else if(type ==2) {
			int pos = mIndexForId[id];
			return mFriendMsgs.get(pos);
		} else {
			return null;
		}
	}
	
	public List<Boolean> getCurIsSelf(int type, int id) {
		if(type == 0) {
			return mIsSelf;
		} else if(type == 2) {
			int pos = mIndexForId[id];
			return mFriendIsSelf.get(pos);
		} else {
			return null;
		}
	}
	
	public static void closeChatServiceData() {
		mChatServiceData = null;
	}
}