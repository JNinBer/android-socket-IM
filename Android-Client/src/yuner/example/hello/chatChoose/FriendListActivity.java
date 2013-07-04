package yuner.example.hello.chatChoose;

import java.util.ArrayList;
import java.util.List;

import yuner.example.hello.ConnectedApp;
import yuner.example.hello.R;
import yuner.example.hello.chatServices.ChatServiceData;
import yuner.example.hello.chatServices.FriendListInfo;
import yuner.example.hello.chatter.ChatActivity;
import yuner.example.hello.myNetwork.UserInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class FriendListActivity extends Activity implements OnChildClickListener {

	private static boolean mIsActive = false;
	private static FriendListActivity mFriendListActivity = null;
	
    private ExpandableListView mListView = null;
    private FriendListAdapter mAdapter = null;
    
    private List<FriendListGroupItem> mGrpInfo = null;
    private List<List<UserInfo>> mUsrInfo = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);	
		setContentView(R.layout.cc0_friend_list);
		
		mFriendListActivity = this;
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	mIsActive = false;
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	mIsActive = true;
    	
//    	initData();
        mListView = (ExpandableListView)findViewById(R.id.cc0_friend_list_listview);        
        mListView.setGroupIndicator(getResources().getDrawable(
                R.drawable.expander_floder));
//        mAdapter = new FriendListAdapter(this, mGrpInfo, mUsrInfo);
//        mListView.setAdapter(mAdapter);
//        mListView.setDescendantFocusability(ExpandableListView.FOCUS_AFTER_DESCENDANTS);
//        mListView.setOnChildClickListener(this);
        onFriendListUpdate();
    }
    
    public static boolean getIsActive() {
    	return mIsActive;
    }
    
    public static FriendListActivity getInstance() {
    	return mFriendListActivity;
    }

    /*
     * ChildView 设置 布局很可能onChildClick进不来，要在 ChildView layout 里加上
     * android:descendantFocusability="blocksDescendants",
     * 还有isChildSelectable里返回true
     */
    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
            int groupPosition, int childPosition, long id) {
        // TODO Auto-generated method stub
    	
    	int pos = childPosition;
    	int id_x = ChatServiceData.getInstance().getIdForPos(pos);
    	((ConnectedApp)getApplication()).getChatService().setType(2);
		((ConnectedApp)getApplication()).getChatService().setId(id_x);
		
		Intent intent0=new Intent(FriendListActivity.this,ChatActivity.class);
		startActivity(intent0.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
		overridePendingTransition(R.anim.my_slide_right_in,R.anim.my_slide_left_out);
    	
        return true;
    }

    private void initData() {
    	mGrpInfo = new ArrayList<FriendListGroupItem>();
        mUsrInfo = new ArrayList<List<UserInfo>>();
    	
    	List<UserInfo> curListFriends = FriendListInfo.getFriendListInfo().getFriendList();
    	
    	mGrpInfo.add(new FriendListGroupItem("Online Users",curListFriends.size()));
    	
    	List<UserInfo> lu0 = new ArrayList<UserInfo>();
    	for(UserInfo userInfo : curListFriends) {
    		lu0.add(userInfo);
    	}
    	mUsrInfo.add(lu0);
    }
    
    public void onFriendListUpdate() {
    	initData();
    	mAdapter = new FriendListAdapter(this, mGrpInfo, mUsrInfo);
        mListView.setAdapter(mAdapter);
        mListView.setDescendantFocusability(ExpandableListView.FOCUS_AFTER_DESCENDANTS);
        mListView.setOnChildClickListener(this);
    }

}
