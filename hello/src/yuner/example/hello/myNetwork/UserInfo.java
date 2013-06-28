package yuner.example.hello.myNetwork;

import yuner.example.hello.myNetwork.ChatEntity;


public class UserInfo {
	
	public static String strSplitter = ChatEntity.strSplitter;
	
	public int msgType = 3;
	
	private String mName = "";
	private int mId = 0;
	private int mSex = 0;
	private int mAge = 0;
	private int mAvatarId = 0;
	
	public UserInfo(String name,int id,int sex,int age,int avatarId) {
		mName = new String(name);
		mId = id;
		mSex = sex;
		mAge = age;
		mAvatarId = avatarId;
	}
	
	public UserInfo(String st0)
	{
		String[] sbArr0 = st0.split(strSplitter);
		
		this.mName = new String(sbArr0[0]);
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
	
	public int getSex()
	{
		return mSex;
	}
	
	public int getAvatarId() {
		if(mSex==0) {
			return 0;
		} else {
			return 1;
		}
	}

}
