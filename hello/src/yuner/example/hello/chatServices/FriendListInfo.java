package yuner.example.hello.chatServices;

import java.util.ArrayList;
import java.util.List;

import yuner.example.hello.chatChoose.FriendListActivity;
import yuner.example.hello.myNetwork.UserInfo;

public class FriendListInfo {

	public static final String strSplitter = "!!!!!&&&!";
	
	/* single instance */
	private static FriendListInfo mFriendListInfo;
	
	/* list stores all users */
	private List<UserInfo> mListOfFriends;
	
	/* retrieve single instance */
	public static FriendListInfo getFriendListInfo() {
		if(mFriendListInfo == null) {
			mFriendListInfo = new FriendListInfo();
		}
		return mFriendListInfo;
	}
	
	/* private constructor */
	private FriendListInfo() {
//		mListOfFriends = new ArrayList<UserInfo>();
		mListOfFriends = InitData.getInitData().getListOfFriends();
	}
	
	public void updateFriendList(String str0) {
		String[] strArr0 = str0.split(strSplitter);
		int type = Integer.parseInt(strArr0[0]);
		
		UserInfo userInfo = new UserInfo(strArr0[1]);
		if(type==1) {
			mListOfFriends.add(userInfo);
			ChatServiceData.getInstance().newUser(userInfo);
		} else if(type==-1) {
			for(UserInfo ui0 : mListOfFriends) {
				if(ui0.getId() == userInfo.getId()) {
					mListOfFriends.remove(mListOfFriends.indexOf(ui0));
					ChatServiceData.getInstance().offLineUser(ui0);
				}
			}
		}
		
		if(FriendListActivity.getIsActive()) {
			FriendListActivity.getInstance().onFriendListUpdate();
		}
	}
	
	public List<UserInfo> getFriendList() {
		return mListOfFriends;
	}
	
	public static void closeFriendListInfo() {
		mFriendListInfo = null;
	}
	
}
