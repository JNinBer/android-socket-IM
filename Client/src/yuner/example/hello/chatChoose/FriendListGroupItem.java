package yuner.example.hello.chatChoose;

public class FriendListGroupItem {

	private String mGrpName;
	private int mNumOfUsr;
	
	public FriendListGroupItem(String grpName,int numOfUsr) {
		mGrpName = grpName;
		mNumOfUsr = numOfUsr;
	}
	
	public String getName() {
		return mGrpName;
	}
	
	public int getNum() {
		return mNumOfUsr;
	}
}
