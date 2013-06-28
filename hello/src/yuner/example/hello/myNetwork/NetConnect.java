/**
 * myNetwork block provides interface for basic network operations,
 * --> NetConnect provides interface to establish a socket connect to a server
 * --> ClientListen establish a watcher to listen to this socket
 * --> one thing to notice, only one socket is established to our server
 * 
 *  InetAddress ia0=InetAddress.getByName("javalobby.org");
 *  InetSocketAddress ist0=new InetSocketAddress(HostIp,HostPort);
 *  just an example to tell you how to use domain name
 */

package yuner.example.hello.myNetwork;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

public class NetConnect extends Thread{
	
	/*  the singleton NetConnect  */
	private static NetConnect netConnect;
	
	/*  information about Server, ip address and portal number  */
	private String mHostIp="192.168.32.130";
	private int mHostPort=8888;
	
	/*  socket  */
	private Socket mSocket0=null;
	
	/*  information from outside  */
	private Context mContext0;
	private UserInfo mUserInfo;
	
	private ClientListenThread mClientListen0;
	
	private boolean connectedAlready=false;
	
	/*  singleton retrieval  */
	public static NetConnect getnetConnect(Context par,UserInfo userInfo) {
		if(netConnect == null) {
			netConnect = new NetConnect(par,userInfo);
		}
		return netConnect;
	}
	
	private NetConnect(Context par,UserInfo userInfo)
	{
		this.mContext0=par;
		this.mUserInfo = userInfo;
	}
	
	public void run()
	{
		try{		
			// initialization and establish connection
			InetAddress ia0=InetAddress.getByName(mHostIp);
			InetSocketAddress ist0=new InetSocketAddress(ia0,mHostPort);
			mSocket0=new Socket();
			mSocket0.connect(ist0,2000);
			
			// start listening
			mClientListen0 = new ClientListenThread(mContext0,mSocket0,this);
			mClientListen0.start();
			
			/*  shake hand with server  */
			sendUpload("3"+UserInfo.strSplitter);
			String usrInfo = mUserInfo.toString();
			sendUpload(usrInfo);
			/*  shake hand with server  */
			
			connectedAlready=true;
		}catch(IOException e){
			System.out.println("error occured");
		}
	}
	
	public void sendUpload(int type, ChatEntity ent0)
	{
		String header = Integer.toString(type) + ChatEntity.strSplitter;
		sendUpload(header);
		
		String toSend = ent0.toString();
		sendUpload(toSend);
	}
	
	/*  synchronized so only one send action is happening at a time  */
	public synchronized void sendUpload(String buff)
	{
		new ClientSendThread(mSocket0,buff).start();
	}
	
	public boolean connectedOrNot()
	{
		return connectedAlready;
	}
	
	public void closeNetConnect()
	{
		try{
			mSocket0.close();
		} catch (Exception e) {}
		try{
			mClientListen0.closeBufferedReader();
		} catch (Exception e) {}
		netConnect=null;
	}
}