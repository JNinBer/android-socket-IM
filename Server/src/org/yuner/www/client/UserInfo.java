package org.yuner.www.client;

public class UserInfo {
	
	public static String strSplitter = ChatEntity.strSplitter;
	
	public final int msgType = 3;
	
	private String mName = "";
	private int mId = 0;
	private int mSex = 0;
	private int mAge = 0;
	private int mAvatarId = 0;
	
	public UserInfo(String st0)
	{
		String[] sbArr0 = st0.split(strSplitter);
		
		this.mName = sbArr0[0];
		this.mId = Integer.parseInt(sbArr0[1]);
		this.mSex = Integer.parseInt(sbArr0[2]);
		this.mAge = Integer.parseInt(sbArr0[3]);
		this.mAvatarId = Integer.parseInt(sbArr0[4]);
	}
	
	public String toString()
	{
		String st0 = mName + strSplitter;
		st0 += mId + strSplitter;
		st0 += mSex + strSplitter;
		st0 += mAge + strSplitter;
		st0 += mAvatarId + strSplitter;
		
		return st0;
	}
	
	public String getName()
	{
		return mName;
	}
	
	public int getId()
	{
		return mId;
	}
	
	public void setId(int id)	{
		mId = id;
	}

	public int getSex()
	{
		return mSex;
	}
	
	public int getAge()
	{
		return mAge;
	}
	
	public int getAvatarId()
	{
		return mAvatarId;
	}
}
