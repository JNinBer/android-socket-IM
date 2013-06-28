package yuner.example.hello.chatChoose;

import java.util.ArrayList;
import java.util.List;

import yuner.example.hello.R;
import yuner.example.hello.chatter.ChatActivity;
import yuner.example.hello.myNetwork.ChatEntity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChooseRoomAdapter extends BaseAdapter{
	private List<String> mVector;
	private LayoutInflater mInflater;
	private Context mContext0;
	
	public ChooseRoomAdapter(Context context) {
		mContext0=context;
		
		this.mVector = new ArrayList();
		mVector.add("Public Chatting Room");
		mVector.add("Group Chatting Room");
		mVector.add("Friends");
		mInflater = LayoutInflater.from(context);
	}

	public View getView(int position, View convertView, ViewGroup root) {
		String name=mVector.get(position);
		
		convertView = mInflater.inflate(R.layout.cc0_choose_room_item, null);
		TextView contact = (TextView) convertView.findViewById(R.id.cb0ChooseRoomItemLabel);
		contact.setText(name);

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
