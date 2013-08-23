package org.yuner.www.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

import org.yuner.www.commons.GlobalErrors;
import org.yuner.www.beans.UserInfo;
import org.yuner.www.beans.SearchEntity;
import org.yuner.www.beans.ChatEntity;

public class DBTempSaveUtil {

	public static void saveUnsentChatMsg(int senderId, int receiverId, ChatEntity ent0) {
		Connection con = DBCon.getConnect();

		String sql0 = "use my_IM_GGMM";
		String sql1 = "insert into unSendMsgs (senderId,receiverId,_datetime,sentence)" + 
						" values(?,?,now(),?)";
		
		try {
			PreparedStatement ps;

			ps = con.prepareStatement(sql0);
			ps.execute();

			ps = con.prepareStatement(sql1);
			ps.setInt(1, senderId);
			ps.setInt(2, receiverId);
			ps.setString(3, ent0.toString());
			int res = ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<ChatEntity> getUnsentChatMsg(int receiverId) {
		Connection con = DBCon.getConnect();

		String sql0 = "use my_IM_GGMM";
		String sql1 = "select * from unSendMsgs where receiverId=? order by _datetime asc";
		String sql2 = "delete from unSendMsgs where receiverId=?";

		ArrayList<ChatEntity> outList = new ArrayList<ChatEntity>();

		try {
			PreparedStatement ps;
			ResultSet rs;

			ps = con.prepareStatement(sql0);
			ps.execute();

			ps = con.prepareStatement(sql1);
			ps.setInt(1, receiverId);
			rs = ps.executeQuery();
			if(rs.first()) {
				do {
					String str0 = rs.getString("sentence");
					ChatEntity ent01 = ChatEntity.Str2Ent(str0);
					outList.add(ent01);
				} while (rs.next());
			}

			ps = con.prepareStatement(sql2);
			ps.setInt(1, receiverId);
			int res = ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return outList;
	}



	public static void saveUnsentFrdReqs(int requesterId, int requesteeId, String requestStr) {
		Connection con = DBCon.getConnect();

		String sql0 = "use my_IM_GGMM";
		String sql1 = "insert into unSendFrdReqs (requesterId,requesteeId,_datetime,requestStr)" + 
						" values(?,?,now(),?)";
		
		try {
			PreparedStatement ps;

			ps = con.prepareStatement(sql0);
			ps.execute();

			ps = con.prepareStatement(sql1);
			ps.setInt(1, requesterId);
			ps.setInt(2, requesteeId);
			ps.setString(3, requestStr);
			int res = ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> getUnsentFrdReqs(int requesteeId) {
		Connection con = DBCon.getConnect();

		String sql0 = "use my_IM_GGMM";
		String sql1 = "select * from unSendFrdReqs where requesteeId=? order by _datetime asc";
		String sql2 = "delete from unSendFrdReqs where requesteeId=?";

		ArrayList<String> outList = new ArrayList<String>();

		try {
			PreparedStatement ps;
			ResultSet rs;

			ps = con.prepareStatement(sql0);
			ps.execute();

			ps = con.prepareStatement(sql1);
			ps.setInt(1, requesteeId);
			rs = ps.executeQuery();
			if(rs.first()) {
				do {
					String str0 = rs.getString("requestStr");
					outList.add(str0);
				} while (rs.next());
			}

			ps = con.prepareStatement(sql2);
			ps.setInt(1, requesteeId);
			int res = ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return outList;
	}


	public static void saveUnsentFrdReqResponse(int requesterId, int requesteeId, String requestStr) {
		Connection con = DBCon.getConnect();

		String sql0 = "use my_IM_GGMM";
		String sql1 = "insert into unSendFrdReqResponse (requesterId,requesteeId,_datetime,requestStr)" + 
						" values(?,?,now(),?)";
		
		try {
			PreparedStatement ps;

			ps = con.prepareStatement(sql0);
			ps.execute();

			ps = con.prepareStatement(sql1);
			ps.setInt(1, requesterId);
			ps.setInt(2, requesteeId);
			ps.setString(3, requestStr);
			int res = ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> getUnsentFrdReqResponse(int requesterId) {
		Connection con = DBCon.getConnect();

		String sql0 = "use my_IM_GGMM";
		String sql1 = "select * from unSendFrdReqResponse where requesterId=? order by _datetime asc";
		String sql2 = "delete from unSendFrdReqResponse where requesterId=?";

		ArrayList<String> outList = new ArrayList<String>();

		try {
			PreparedStatement ps;
			ResultSet rs;

			ps = con.prepareStatement(sql0);
			ps.execute();

			ps = con.prepareStatement(sql1);
			ps.setInt(1, requesterId);
			rs = ps.executeQuery();
			if(rs.first()) {
				do {
					String str0 = rs.getString("requestStr");
					outList.add(str0);
				} while (rs.next());
			}

			ps = con.prepareStatement(sql2);
			ps.setInt(1, requesterId);
			int res = ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return outList;
	}


}
