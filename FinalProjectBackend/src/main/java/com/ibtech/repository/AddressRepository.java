package com.ibtech.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibtech.entities.Address;
import com.ibtech.entities.Province;

public class AddressRepository extends BaseRepository<Address> {

	public List<Address> getAll() throws SQLException {
		String sql = "select a.*,p.\"name\" as province_name from addresses a join provinces p on a.province_id  = p.id";
		return super.listAll(sql);
	}

	public List<Address> getAllByCustomerName(String customerName) throws SQLException {
		List<Address> addressList = new ArrayList<>();
		connect();
		String sql = "select a.*,p.\"name\" as province_name from addresses a join provinces p on a.province_id  = p.id where a.user_name = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, customerName);
		ResultSet resultSet = statement.executeQuery();
		addressList = parseList(resultSet);
		disconnect();
		return addressList;
	}

	public Address getById(Long addressId) throws SQLException {
		String sql = "select a.*,p.\"name\" as province_name from addresses a join provinces p on a.province_id  = p.id where a.id = ?";
		return super.find(sql, addressId);
	}

	public boolean add(Address address) throws SQLException {
		connect();
		String sql = "Insert into addresses(province_id,address_line_1,address_line_2,user_name) values(?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, address.getProvince().getId());
		statement.setString(2, address.getAddressLine1());
		statement.setString(3, address.getAddressLine2());
		statement.setString(4, address.getUserName());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}

	public boolean update(Address address) throws SQLException {
		connect();
		String sql = "Update addresses set province_id = ? ,address_line_1 = ?, address_line_2 = ?,user_name = ? where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, address.getProvince().getId());
		statement.setString(2, address.getAddressLine1());
		statement.setString(3, address.getAddressLine2());
		statement.setString(4, address.getUserName());
		statement.setLong(5, address.getId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}

	public boolean remove(Address address) throws SQLException {
		connect();
		String sql = "Delete from addresses where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, address.getId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}

	@Override
	protected Address parse(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");
		String addressLine1 = resultSet.getString("address_line_1");
		String addressLine2 = resultSet.getString("address_line_2");
		String userName = resultSet.getString("user_name");
		int provinceId = resultSet.getInt("province_id");
		String provinceName = resultSet.getString("province_name");
		Province province = new Province(provinceId, provinceName);
		Address address = new Address(id, addressLine1, addressLine2, userName);
		address.setProvince(province);
		return address;
	}
}
