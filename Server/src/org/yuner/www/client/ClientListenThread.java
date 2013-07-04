package org.yuner.www.client;

import java.io.BufferedReader;

import org.yuner.www.ServerListen;

public class ClientListenThread extends Thread{
	
	private ClientActivity mClientActivity;
	private BufferedReader mBuffRder;
	
	public ClientListenThread(ClientActivity ca0, BufferedReader br0)
	{
		mClientActivity = ca0;
		mBuffRder = br0;
	}
	
	@Override
	public void run()
	{
		super.run();

		while(true)
		{
			try{				
				String received = mBuffRder.readLine();
				if (received == null) {	      // if received == null, meaning the socket has been closed
					// remove the class/threads etc. associated with this specific client
					mClientActivity.goOffLine();
					return;
				}
				else
				{
					String[] sbArr0 = received.split(ChatEntity.strSplitter);
					int msgType = Integer.parseInt(sbArr0[0]);			// type of message received
					
					boolean readRealMsg = true;
					String actualMsg = "";
					while (readRealMsg) {
						if((received = mBuffRder.readLine()) != null) {
							actualMsg+=received;
							if (!received.endsWith(ChatEntity.strSplitter)) {
								actualMsg += "\n";
								continue;
							}
							
							try {
								switch (msgType) {
								case 0:
									ChatEntity msg=ChatEntity.Str2Ent(actualMsg);
									mClientActivity.receivedNewMsg(msgType,msg.toString());
									// add this ChatEntity data package into the system stack
									break;
								case 1:
									/* to be added later */
									break;
								case 2:	
									ChatEntity msg2=ChatEntity.Str2Ent(actualMsg);
									mClientActivity.receivedNewMsg(msgType,msg2.toString());
									/* to be added later */
									break;
								default:
									break;
								}
							
								readRealMsg = false;
							} catch (Exception e) {     // to secure for if the first character is "\n"
								actualMsg += "\n";
								continue;							
							}
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("error occurs for trying to access thread and socket");
				// remove the class/threads etc. associated with this specific client
				mClientActivity.goOffLine();
				return;
			}
		}
	}
	
	public void closeBufferedReader()
	{
		try {
			mBuffRder.close();
		} catch(Exception e) {}
	}
}
