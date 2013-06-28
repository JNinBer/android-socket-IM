package yuner.example.hello.myNetwork;

import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientSendThread extends Thread{

	private Socket mSocket;
	private String mStrToSend;
	
	public ClientSendThread(Socket socket, String str0) {
		this.mSocket = socket;
		this.mStrToSend = str0;
	}
	
	@Override
	public void run() {
		try {
			OutputStreamWriter ost0=new OutputStreamWriter(mSocket.getOutputStream());
			ost0.write(mStrToSend+"\n");
			ost0.flush();
		} catch(Exception e){}
	}
}
