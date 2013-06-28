/**
 * ChatListviewAdapter is responsible for updating the content and presentation of 
 * the chatting history Listview in ChatActivity
 * 
 * the major function here is getView to control the display of each child view in listview
 */

package yuner.example.hello.chatter;

import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import yuner.example.hello.myNetwork.ChatEntity;

import yuner.example.hello.R;

public class ChatListviewAdapter extends BaseAdapter{
	private List<ChatEntity> mVector;
	private List<Boolean> mVectorIsSelf;
	private LayoutInflater mInflater;
//	private String mUsrName;
	private Context mContext0;
	
	public ChatListviewAdapter(ChatActivity par,Context context,List<ChatEntity> vector, 
			List<Boolean> vectorIsSelf){
		this.mVector = vector;
		mInflater = LayoutInflater.from(context);
//		this.mUsrName=par.usrName;
		mContext0=context;
		mVectorIsSelf = vectorIsSelf;
	}

	public View getView(int position, View convertView, ViewGroup root) {
		ImageView avatar;
		TextView content;
		TextView NameOfSpeaker;
		
		ChatEntity ent0=mVector.get(position);
		String name=ent0.getNick();
		String time=ent0.getTime();
		int sex=ent0.getSex();
		String real_msg=ent0.getContent();
		
		if(mVectorIsSelf.get(position).booleanValue())
		{
			convertView = mInflater.inflate(R.layout.cb0_chat_listview_item_right, null);
			content=(TextView) convertView.findViewById(R.id.cb0ChatListviewMsgRight);
			NameOfSpeaker=(TextView) convertView.findViewById(R.id.cb0ChatListviewNameRight);
			avatar=(ImageView) convertView.findViewById(R.id.cb0ChatListviewAvatarRight);
			content.setText(real_msg);
			NameOfSpeaker.setText(time);
			
			int avatarId = ent0.getSenderAvatarid();
			if(avatarId==0)
				avatar.setImageResource(R.drawable.cb0_h001);
			else
				avatar.setImageResource(R.drawable.cb0_h002);
		}
		else
		{
			convertView = mInflater.inflate(R.layout.cb0_chat_listview_item_left, null);
			
			content=(TextView) convertView.findViewById(R.id.cb0ChatListviewMsgLeft);
			content.setText(real_msg);
			NameOfSpeaker=(TextView) convertView.findViewById(R.id.cb0ChatListviewNameLeft);
			NameOfSpeaker.setText(name+" "+time);
			
			avatar=(ImageView) convertView.findViewById(R.id.cb0ChatListviewAvatarLeft);
			int avatarId = ent0.getSenderAvatarid();
			if(avatarId==0)
				avatar.setImageResource(R.drawable.cb0_h001);
			else
				avatar.setImageResource(R.drawable.cb0_h002);
		}

		return convertView;
	}
	
	public int getCount() {
		return mVector.size();
	}

	public Object getItem(int position) {
		return mVector.get(position);
	}

	public long getItemId(int position) {
		return position;
	}
}