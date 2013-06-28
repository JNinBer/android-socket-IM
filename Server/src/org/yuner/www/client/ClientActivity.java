/**
 * this package is only responsible for listening for and sending data packages, as for how to process
 * these data packages, we don't care at all
 * 
 * every user will have one socket, each of which corresponds to one ClientActivity
 */

package org.yuner.www.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.*;

import org.yuner.www.ServerListen;
import org.yuner.www.database.IdManager;

public class ClientActivity {

	private Socket mSocket;
	private BufferedReader mBuffRder;
	private BufferedWriter mBuffWter;
	private ServerListen mServerListen;
	
	private HandShakeThread mHandShake;
	private ClientListenThread mClientListen;
	
	private UserInfo mUsrInfo;

	private boolean mConnectSuccessfully;
	
	/**
	 * thread used to conduct handshake with client
	 */
	public class HandShakeThread extends Thread {
		boolean legitimateHandShaker = true;
		
		public void run()
		{
			super.run();

			// for test only
				
			// for test only
			
			BufferedReader buffRder = ClientActivity.this.mBuffRder;
			
			try {
				String msgType = buffRder.readLine();
				String[] typeArray = msgType.split(UserInfo.strSplitter);
				if (Integer.parseInt(typeArray[0]) != 3) {
					legitimateHandShaker = false;
					return;
				}
				
				// since no "\n" presents exists, so no need for "\n" detecting mechanism
				String handShake = buffRder.readLine();
				UserInfo userInfo = new UserInfo(handShake);
				mUsrInfo = userInfo;

				// set id
				mUsrInfo.setId(IdManager.getIdManager().getId());

			} catch (Exception e) {
				legitimateHandShaker = false;
			}
		}
		
		/**
		 * send a handShake message back to the client
		 */
		public boolean sendHandShakeBack(UserInfo usrInfo)
		{
			try {
				BufferedWriter buffWter = ClientActivity.this.mBuffWter;
				buffWter.write(usrInfo.msgType+UserInfo.strSplitter+"\n");
				buffWter.flush();
				buffWter.write(usrInfo.toString()+"\n");
				buffWter.flush();
			} catch (Exception e) {
				return false;
			}
			
			return true;
		}

		public void sendFriendList()
		{
			String type56 = "!!!!!&&&!";
			int n = mServerListen.getVectorOfClient().size();
			String str = n+type56;
			for(ClientActivity ca0 : mServerListen.getVectorOfClient()) {
				str += ca0.getUserInfo() + type56;
			} 
			str += UserInfo.strSplitter;
			try {
				BufferedWriter buffWter = ClientActivity.this.mBuffWter;
				buffWter.write(5+UserInfo.strSplitter+"\n");
				buffWter.flush();
				buffWter.write(str+"\n");
				buffWter.flush();
			} catch (Exception e) {}
		}
	}
	
	/**
	 * public constructor, major tasks here include : 
	 * 1 : initialize the BufferedReader and OutputStreamWriter
	 * 2 : read in the user information and close this connection if input format is not correct
	 */
	public ClientActivity(Socket socket, ServerListen serverListen)
	{
		this.mSocket=socket;
		mConnectSuccessfully = true;
		try{
			mBuffRder = new BufferedReader(new InputStreamReader(mSocket.getInputStream() ) );
			mBuffWter = new BufferedWriter(new OutputStreamWriter(	mSocket.getOutputStream() ));
			mServerListen = serverListen;
			
			mHandShake = new HandShakeThread();
			mHandShake.start();
			mHandShake.join();
			if(mHandShake.legitimateHandShaker) {
				mHandShake.sendHandShakeBack(mUsrInfo);
				mHandShake.sendFriendList();
			} else {
				unableToConnect();
			}
			
			mClientListen = new ClientListenThread(this,mBuffRder);
			mClientListen.start();
		}catch(Exception e){
			System.out.println("error occurs for creating");
			e.printStackTrace();
		}
	}
	
	public UserInfo getUserInfo()
	{
		return mUsrInfo;
	}

	public boolean isConnectedSuccessfully() {
		return mConnectSuccessfully;
	}
	
	/**  notify a new message has come  */
	public void receivedNewMsg(int type,String msg)
	{
		ChatEntity ent0 = ChatEntity.Str2Ent(msg);
		if(type == 0) {
			mServerListen.getMsgDispatcher().newMsgForPublicMsgs(ent0);
		} else if(type == 2) {
			int recvId = ent0.getReceiverId();
			Vector<ClientActivity> vct0 = mServerListen.getVectorOfClient();
			for(ClientActivity ca0 : vct0) {
				if(ca0.getUserInfo().getId() == recvId) {
					ca0.sendOneData(ent0, 2);
					break;
				}
			}
		}
	}
	
	/* send one String */
	public void sendOneString(String str, int type) {
		try {
			mBuffWter.write(type+ChatEntity.strSplitter+"\n");
			mBuffWter.flush();
			mBuffWter.write(str+"\n");
			mBuffWter.flush();
		} catch(Exception e) {}
	}

	/**  send one ChatEntity to this client  */
	public void sendOneData(ChatEntity entPackage, int sendType)
	{
		new ClientSendThread(mBuffWter,entPackage,sendType).run();
	}
	
	public void unableToConnect()
	{
		mConnectSuccessfully = false;
//		mServerListen.removeOneClient(this);
		closeConnect();
	}

	public void goOffLine() {
		closeConnect();
		mServerListen.removeOneClient(this);
		IdManager.getIdManager().setId(mUsrInfo.getId());
	}
	
	private void closeConnect()
	{
		try {
			mSocket.close();
		} catch (Exception e) {}
		try {
			mClientListen.closeBufferedReader();
		} catch (Exception e) {}
	}
}
