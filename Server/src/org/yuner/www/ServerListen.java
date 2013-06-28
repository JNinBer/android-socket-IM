package org.yuner.www;

import java.io.*;
import java.net.*;
import java.util.*;

import org.yuner.www.client.ClientActivity;
import org.yuner.www.client.ChatEntity;
import org.yuner.www.dispatch.MsgDispatcher;

public class ServerListen {
	
	/*  the PORT number for this application  */
	public static final int PORT=8888;
	
	/*  Vector used to store all the ClientActivity  */
	private Vector<ClientActivity> mClientV;
	
	/*  the MsgDispatcher  */
	private MsgDispatcher mMsgDispatcher;

	public static void main(String args[]){
		new ServerListen().begin();
	}
	
	public void begin(){
		try{
			ServerSocket ss = new ServerSocket( PORT );
			mClientV=new Vector<ClientActivity>();
			mMsgDispatcher = new MsgDispatcher(this);
			
			System.out.println("server waiting\n");
			while(true)
			{
				Socket sock = ss.accept();
				ClientActivity ca0=new ClientActivity(sock,this);
				if(ca0.isConnectedSuccessfully()) {
					mClientV.add(ca0);
					new UpdateFriendList(mClientV, ca0, 1).start();
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	public void removeOneClient(ClientActivity client0)
	{
		this.mClientV.removeElement(client0);
		new UpdateFriendList(mClientV, client0, -1).start();
	}
	
	public MsgDispatcher getMsgDispatcher()
	{
		return mMsgDispatcher;
	}
	
	public Vector<ClientActivity> getVectorOfClient()
	{
		return mClientV;
	}
}
