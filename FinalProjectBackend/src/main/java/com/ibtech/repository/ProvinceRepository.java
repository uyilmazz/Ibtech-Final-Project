package com.ibtech.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ibtech.entities.Province;

public class ProvinceRepository extends BaseRepository<Province> {

	public List<Province> getAll() throws SQLException {
		String sql = "Select * from provinces";
		return super.listAll(sql);
	}

	public Province findById(int provinceId) throws SQLException {
		Province province = null;
		connect();
		String sql = "Select * from provinces where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, provinceId);
		ResultSet resultSet = statement.executeQuery();
		if(resultSet.next()) {
			province = parse(resultSet);
		}
		disconnect();
		return province;
	}

	@Override
	protected Province parse(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id");
		String name = resultSet.getString("name");
		Province province = new Province(id, name);
		return province;
	}

}
