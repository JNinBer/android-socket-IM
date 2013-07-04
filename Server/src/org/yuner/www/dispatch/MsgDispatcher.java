/**
 *  this class is designed to store all the msgs to be dispatched, providing interface functions to store 
 *  and withdraw msgs
 */

package org.yuner.www.dispatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yuner.www.ServerListen;
import org.yuner.www.client.ChatEntity;
import org.yuner.www.client.ClientActivity;

public class MsgDispatcher {

	/**  
	 * all the msgs in the public room  
	 * publicMsgs is responsible for store all the newly coming msgs
	 * publicMsgsToSend will be swapped with publicMsgs when sending to make sure nothing bad happens
	 * */
	private List<ChatEntity> mPublicMsgs; 
	private List<ChatEntity> mPublicMsgsToSend;
	
	/**  the map between id and ClientActivity  **/
	private Map<Integer,ClientActivity> idToClientActivity;
	
	private ServerListen mServerListen;
	
	public class TimerToSendBroadcast extends Thread{
		public void run()
		{
			super.run();
			while(true){
				try{
					sleep(200);
					beginBroadcast();
				} catch (Exception e) {}
				
			}
		}
	}
	
	public MsgDispatcher(ServerListen serverListen)
	{
		mServerListen = serverListen;
		
		mPublicMsgs = new ArrayList<ChatEntity>();
		mPublicMsgsToSend = new ArrayList<ChatEntity>();
		
		/*  a timer to notify : it's time to send broadcast now  */
		new TimerToSendBroadcast().start();
	}
	
	/**  newMsgForPublicMsgs() and beginBroadcast() will access the same resource, mPublicMsgs in 
	 * this case, so these two need to be synchronized, i.e at one time, only one thread running 
	 * either of these two functions can get unlocked access to MsgDispatcher.this, ClientListenThread
	 * and TimerToSendBroadcast to be exactly, so only one of them is modifying the competed target 
	 * resource, i.e mPublicMsgs at one time*/
	public synchronized void newMsgForPublicMsgs(ChatEntity ent0)
	{
//		mPublicMsgs.add(ent0);
		modifyPublicMsgs(0,ent0);
	}
	
	public synchronized void beginBroadcast()
	{
//		swapPublicMsgs();
//		mPublicMsgs.clear();
		modifyPublicMsgs(1,null);
		new PublicBroadcast(mServerListen.getVectorOfClient(),mPublicMsgsToSend).start();
	}

	public synchronized void modifyPublicMsgs(int act,ChatEntity ent0) {
		// to add a new message
		if(act==0) {
			mPublicMsgs.add(ent0);
		} else if(act==1) {
			swapPublicMsgs();
			mPublicMsgs.clear();
		}
	}
	
	public void swapPublicMsgs()
	{
		List<ChatEntity> list0 = mPublicMsgs;
		mPublicMsgs = mPublicMsgsToSend;
		mPublicMsgsToSend = list0;
	}
}
