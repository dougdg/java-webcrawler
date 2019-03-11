package com.dougdg.webcrawler.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeSet;

import com.dougdg.webcrawler.connection.DBConnection;

public class LinkDao {
	DBConnection dbConnection = new DBConnection();
	
	public LinkDao() {
		
	}
	
	public void dropLinksTable() {
		Statement stmt = null;
		try {
			Connection conn = dbConnection.getConnection();
			stmt = conn.createStatement();
			String sql = "DROP TABLE IF EXISTS links;";
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table dropped successfully");
	}

	public void createLinksTable() {
		dropLinksTable();
		Statement stmt = null;
		try {
			Connection conn = dbConnection.getConnection();
			stmt = conn.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS links "
					+ "(ID INTEGER PRIMARY KEY autoincrement, LINK BLOB NOT NULL);";
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
			System.out.println(sql);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table created successfully");
	}

	public void insertLinks(TreeSet<String> linksSet) {
		PreparedStatement pstmt = null;
		try {
			Connection conn = dbConnection.getConnection();
			conn.setAutoCommit(false);
			
			for(String link : linksSet) {				
				String sql = "INSERT INTO links VALUES (?,?);";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(2, link);
				pstmt.executeUpdate();
				pstmt.close();
				conn.commit();
			}			 
			conn.close();
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Links inserted successfully");
	}

	public ArrayList<String> selectLinks() {
		Statement stmt = null;
		ArrayList<String> linksList = new ArrayList<String>();		
		try {			
			Connection conn = dbConnection.getConnection();
			conn.setAutoCommit(false);

			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM links;");
			
			while (rs.next()) {
				String link = rs.getString("link");
				linksList.add(link);				
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Links selected successfully");
		
		return linksList;
	}

}
