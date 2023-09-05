package thanhnvMigi.migi.reponsitory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import thanhnvMigi.migi.config.JDBCConnection;
import thanhnvMigi.migi.model.NhanVien;

public class NhanVienResponsitory {
   
	private static final Logger logger = LogManager.getLogger(NhanVienResponsitory.class);
	
	public List<NhanVien> getAll() {
		Connection con = null;
		String query = "SELECT * FROM nhanvien";
		logger.info("Query nhanVien");
		con = JDBCConnection.getJDBCConnection();
		try {
			PreparedStatement ps = con.prepareStatement(query);
			List<NhanVien> listAll = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				listAll.add(new NhanVien(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getDate(4),
						rs.getString(5), rs.getString(6)));
			}
			return listAll;
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		} finally {
			JDBCConnection.closeConnection(con);
		}
		return null;
	}

	// getOne

	public NhanVien getOne(Long ma) {
		
		String query = "Select nhanvien.manhanvien, nhanvien.ho, nhanvien.ten, nhanvien.ngaysinh, nhanvien.diachi, nhanvien.dienthoai from nhanvien where manhanvien = ?";
		logger.info("Bạn đang truy vấn đến 1 đối tượng nhân viên:" + ma);
		try (Connection con = JDBCConnection.getJDBCConnection(); PreparedStatement ps = con.prepareStatement(query)) {
			ps.setObject(1,ma);;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				NhanVien nv = new NhanVien(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getDate(4),
						rs.getString(5), rs.getString(6));
				return nv;
			}
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace(System.out);
		}
		return null;
	}
	
	// add
	public boolean add(NhanVien nv) {
		logger.info("Thêm nhân viên mới: ");
        String query = "INSERT INTO nhanvien (manhanvien, ho, ten, ngaysinh, diachi, dienthoai) VALUES (?,?,?,?,?,?)";
        int check = 0;
        try ( Connection con = JDBCConnection.getJDBCConnection();  PreparedStatement ps = con.prepareStatement(query)) {
        	ps.setObject(1, nv.getMaNhanVien());
        	ps.setObject(2, nv.getHo());
            ps.setObject(3, nv.getTen());
            ps.setObject(4, nv.getNgaySinh());
            ps.setObject(5, nv.getDiaChi());
            ps.setObject(6, nv.getDienThoai());
            check = ps.executeUpdate();
        } catch (Exception e) {
        	logger.error(e.toString());
            e.printStackTrace(System.out);
        }
        return check > 0;
    }
	
	
	public boolean update(NhanVien nv, Long ma) {
		logger.info("Cập nhật nhân viên!");
        String query = "Update nhanvien set ho= ?, ten =?, ngaysinh=?, diachi=?, dienthoai=? where manhanvien = ?";
        int check = 0;
        try ( Connection con = JDBCConnection.getJDBCConnection();  PreparedStatement ps = con.prepareStatement(query);){
            ps.setObject(1, nv.getHo());
            ps.setObject(2, nv.getTen());
            ps.setObject(3, nv.getNgaySinh());
            ps.setObject(4, nv.getDiaChi());
            ps.setObject(5, nv.getDienThoai());
            ps.setObject(6, ma);
            check = ps.executeUpdate();
        } catch (Exception e) {
        	logger.error(e.toString());
            e.printStackTrace(System.out);
        }
        return check > 0;
    }
	
	public boolean delete(Long ma) {
		logger.info("Xóa 1 nhân viên:" + ma);
        String query = "delete from nhanvien where manhanvien = ?";
        int check = 0;
        try ( Connection con = JDBCConnection.getJDBCConnection();  PreparedStatement ps = con.prepareStatement(query);){
            ps.setObject(1, ma);
            check = ps.executeUpdate();
        } catch (Exception e) {
        	logger.error(e.toString());
            e.printStackTrace(System.out);
        }
        return check > 0;
    }
	
	
    public static void main(String[] args) {
		NhanVien nv = new NhanVien(Long.valueOf(3),"1","1",Date.valueOf("2003-08-06"),"Bắc Ninh","0193023033");
		new NhanVienResponsitory().update(nv, Long.valueOf(3));
	}
}
