/**
 * in order to transfer non-primitive information, like socket, class, etc.
 * we need a global class to store these information, that is a child of
 * Application as we are implementing here, you can always access global information
 * stored in ConnectedApp as below:
 *    ConnectedApp connected_app0  =  (ConnectedApp)getApplication();
 * one thing to remember, register ConnectedApp in the manifest file
 */

package yuner.example.hello;

import java.util.ArrayList;
import java.util.List;

import yuner.example.hello.chatServices.ChatService;
import yuner.example.hello.myNetwork.NetConnect;
import yuner.example.hello.myNetwork.UserInfo;
import android.app.Activity;
import android.app.Application;

public  class  ConnectedApp  extends  Application
{
     private NetConnect mNectConnect;
     private UserInfo mUserInfo;
     private ChatService mChatService;
     private List<Activity> allActivities; 

     public  NetConnect getConnect() {
         return mNectConnect;
     }

     public  void  setConnect(NetConnect nc) {
         this.mNectConnect=nc;
     }
     
     public UserInfo getUserInfo() {
    	 return mUserInfo;
     }
     
     public void setUserInfo(UserInfo userInfo) {
    	 mUserInfo = userInfo;
     }
     
     public ChatService getChatService() {
    	 return mChatService;
     }
     
     public void setChatService(ChatService chatService) {
    	 mChatService = chatService;
     }
    
     public void instantiateListActivity()
     {
    	 allActivities =new ArrayList<Activity>();
     }
     
     public void clearListActivity()
     {
    	 if (allActivities != null) {
	    	 for (Activity act : allActivities) {
	    		 act.finish();
	    	 }
	    	 
	    	 allActivities.clear();
    	 }
     }
     
     public void addItemIntoListActivity(Activity act0)
     {
    	 allActivities.add(act0);
     }
}
