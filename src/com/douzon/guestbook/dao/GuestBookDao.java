package com.douzon.guestbook.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.douzon.guestbook.vo.GuestBookVo;

public class GuestBookDao {

	public List<GuestBookVo> getList() {
		List<GuestBookVo> list = new ArrayList<GuestBookVo>();
		Connection conn = null;
		PreparedStatement pstmt=null;
		ResultSet rs;
		
		try {
			conn = getConnection();
			String sql="select * from guestbook order by no desc";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				long no=rs.getLong(1);
				String name=rs.getString(2);
				String password=rs.getString(3);
				String message=rs.getString(4);
				message=message.replace("\r\n", "<br>");
				String date=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(rs.getDate(5));
				
				GuestBookVo vo=new GuestBookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setPassword(password);
				vo.setDate(date);
				vo.setMessage(message);
				
				list.add(vo);
			}
			
			
		} catch (SQLException e) {
			System.out.println(e);

		} finally {

			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}

		return list;
	}
	public boolean insert(GuestBookVo guestBookVo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			conn = getConnection();
			String sql ="insert into guestbook values(null,?,?,?,now())";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, guestBookVo.getName());
			pstmt.setString(2, guestBookVo.getPassword());
			pstmt.setString(3, guestBookVo.getMessage());
			
			int count = pstmt.executeUpdate();
			result = count == 1;
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public boolean delete(GuestBookVo guestBookVo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			conn = getConnection();
			String sql ="delete from guestbook where no=? and password=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, guestBookVo.getNo());
			pstmt.setString(2, guestBookVo.getPassword());
			
			int count = pstmt.executeUpdate();
			result = count == 1;
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	
	
	
	
	
	public Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://localhost:3306/webdb?autoReconnect=true&useSSL=false";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}
}
