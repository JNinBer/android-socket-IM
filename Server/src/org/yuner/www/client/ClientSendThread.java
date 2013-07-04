package org.yuner.www.client;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class ClientSendThread extends Thread{

	private BufferedWriter mBuffWter;;
	private ChatEntity mEntityPackage;
	private int mSendType;
	
	public ClientSendThread(BufferedWriter bw0, ChatEntity ent0, int type0)
	{
		mBuffWter = bw0;
		mEntityPackage = ent0;
		mSendType=type0;
	}
	
	@Override
	public void run()
	{
		super.run();
		
		try {
			mBuffWter.write(mSendType+ChatEntity.strSplitter+"\n");
			mBuffWter.flush();
			mBuffWter.write(mEntityPackage.toString()+"\n");
			mBuffWter.flush();

			System.out.println(mSendType+ChatEntity.strSplitter);
			System.out.println(mEntityPackage.toString());
		} catch (Exception e) {}
	}
}
