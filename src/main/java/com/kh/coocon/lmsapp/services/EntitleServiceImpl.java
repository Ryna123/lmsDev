package com.kh.coocon.lmsapp.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.coocon.lmsapp.entities.UserTest;

@Service
public class EntitleServiceImpl implements EntitleService{
	@Autowired
	private DataSource dataSource;
	
	@Override
	public List<UserTest> list() {
		/*String sql = "SELECT leaveType,(entitled - taken) as Available ,taken, entitiled "
				+ "from ("
					+ "SELECT "
						+ "SUM(A.duration) as taken"
						+ "b.NAME AS leaveType,"
						+ "MIN (C.days) AS entitled"
						+ "FROM"
							+ "lms_leaves A"
					+ "LEFT JOIN lms_types b ON b.type_id = A.type_id"
					+ "LEFT JOIN lms_entitleddays C ON b.type_ID = C.TYPE_id"
					+ "WHERE"
						+ "A.employee_id =2 and a.status_id = 2 "
					+ "GROUP BY NAME ORDER BY taken"
					+ ") as mytable";*/
		
String sql= " SELECT								"   
		+ " 	leaveType,							"   
		+ " 	(entitled - taken) AS available,				"   
		+ " 	taken,								"   
		+ " 	entitled							"   
		+ " FROM								"   
		+ " 	(								"   
		+ " 		SELECT							"   
		+ " 			SUM (A .duration) AS taken,			"   
		+ " 			b. NAME AS leaveType,				"   
		+ " 			MIN (C .days) AS entitled			"   
		+ " 		FROM							"   
		+ " 			lms_leaves A					"   
		+ " 		LEFT JOIN lms_types b ON b.type_id = A .type_id		"   
		+ " 		LEFT JOIN lms_entitleddays C ON b.type_ID = C .TYPE_id	"   
		+ " 		WHERE							"   
		+ " 			A .employee_id = 2				"   
		+ " 		AND A .status_id = 2					"   
		+ " 		GROUP BY						"   
		+ " 		NAME							"   
		+ " 	ORDER BY							"   
		+ " 		taken							"   
		+ " ) AS mytable							";  

		/*String sql = "select id, employee_id,"
				+ "status_id,"
				+ "type_id,"
				+ "startdate from lms_leaves";
		*/
		try (Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
		)
		{
			ArrayList<UserTest> a = new ArrayList<UserTest>();
			UserTest b = null;
			while (rs.next()) {
				b = new UserTest();				
				b.setId(rs.getInt("available"));
				b.setTitle(rs.getString("leaveType"));
				b.setAuthor(rs.getString("taken"));
				b.setPostedDate(rs.getString("entitled"));
				a.add(b);
			}
			return a;
		} catch (SQLException e) {
			System.out.println(e);
		} 
		return null;
	}

	@Override
	public boolean addUser(UserTest b) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUser(UserTest b) {
		// TODO Auto-generated method stub
		return false;
	}

}