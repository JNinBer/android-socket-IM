package org.yuner.www.dispatch;

import java.util.List;
import java.util.Vector;

import org.yuner.www.client.ChatEntity;
import org.yuner.www.client.ClientActivity;

public class PublicBroadcast extends Thread{

	private Vector<ClientActivity> mClientV;
	private List<ChatEntity> mPublicMsgs;
	
	@Override
	public void run()
	{
		super.run();
		
		for(ChatEntity ent0 : mPublicMsgs) {
			for(ClientActivity act0 : mClientV) {
				if(act0.getUserInfo().getId()!=ent0.getSenderId())
					act0.sendOneData(ent0, ent0.getType());
			}
		}
		mPublicMsgs.clear();
	}
	
	public PublicBroadcast(Vector<ClientActivity> clientV,List<ChatEntity> publicMsgs)
	{
		mClientV = clientV;
		mPublicMsgs = publicMsgs;
	}
}
