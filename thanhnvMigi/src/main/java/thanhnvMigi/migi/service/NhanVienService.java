package thanhnvMigi.migi.service;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Response;
import thanhnvMigi.migi.model.NhanVien;
import thanhnvMigi.migi.result.ServiceResult;

public interface NhanVienService {
	ServiceResult<NhanVien> result(String mess);
	
	ServiceResult<NhanVien> validateNhanVien(NhanVien nhanVien);

	ServiceResult<List<NhanVien>> getAll();

	ServiceResult<NhanVien> getOne(Long maNhanVien, HttpServletRequest servletRequest);

	ServiceResult<NhanVien> addNhanVien(NhanVien nhanVien);

	ServiceResult<NhanVien> updateNhanVien(Long maNhanVien, NhanVien nhanVien);

	ServiceResult<NhanVien> deleteNhanVien(Long maNhanVien, HttpServletRequest servletRequest);
}
