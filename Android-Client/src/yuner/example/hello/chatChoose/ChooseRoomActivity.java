package yuner.example.hello.chatChoose;

import yuner.example.hello.MainActivity;
import yuner.example.hello.R;
import yuner.example.hello.chatter.ChatActivity;
import yuner.example.hello.ConnectedApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.View;

/**
 * ChooseRoom is used to present a listView for app-user to choose which chatting room he/she wants to
 * go in, be it public room, group chatting room or friend chatting *
 */
public class ChooseRoomActivity extends Activity{
	
	private ListView mListview = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);	
		setContentView(R.layout.cc0_choose_room);
		
		mListview = (ListView)findViewById(R.id.cb0ChooseRoomList);
		mListview.setAdapter(new ChooseRoomAdapter(this));
		mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:
					((ConnectedApp)getApplication()).getChatService().setType(0);
					((ConnectedApp)getApplication()).getChatService().setId(0);
					
					Intent intent0=new Intent(ChooseRoomActivity.this,ChatActivity.class);
					startActivity(intent0.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
					overridePendingTransition(R.anim.my_slide_right_in,R.anim.my_slide_left_out);
					break;
				case 2:
					Intent intent1 = new Intent(ChooseRoomActivity.this,FriendListActivity.class);
					startActivity(intent1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
					overridePendingTransition(R.anim.my_slide_right_in,R.anim.my_slide_left_out);
				default :
					break;
				}
			}
		});
	}
}
