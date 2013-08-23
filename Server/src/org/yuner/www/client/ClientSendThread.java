package org.yuner.www.client;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import org.yuner.www.commons.*;

public class ClientSendThread {

	private BufferedWriter mBuffWter;
	private String mStrToSend;
	
	public synchronized void start(BufferedWriter bw0, String str0)
	{
		mBuffWter = bw0;
		mStrToSend = str0;
		
		mStrToSend = mStrToSend.replace("\n",GlobalStrings.replaceOfReturn);
		try {
			mBuffWter.write(mStrToSend + "\n");
			mBuffWter.flush();
			System.out.println(mStrToSend);
		} catch (Exception e) {}
	}
}
