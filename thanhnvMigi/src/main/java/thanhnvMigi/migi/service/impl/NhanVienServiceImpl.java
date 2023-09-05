package thanhnvMigi.migi.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import thanhnvMigi.migi.config.AppConstant;
import thanhnvMigi.migi.model.NhanVien;
import thanhnvMigi.migi.reponsitory.NhanVienResponsitory;
import thanhnvMigi.migi.resource.MigiResource;
import thanhnvMigi.migi.result.ServiceResult;
import thanhnvMigi.migi.service.NhanVienService;

public class NhanVienServiceImpl implements NhanVienService {

	private NhanVienResponsitory nhanVienResponsitory = new NhanVienResponsitory();
	private static final Logger logger = LogManager.getLogger(NhanVienServiceImpl.class);

	@Override
	public ServiceResult<List<NhanVien>> getAll() {
		List<NhanVien> listNhanVien = nhanVienResponsitory.getAll();
		return new ServiceResult<>(AppConstant.SUCCESS, "Danh sách nhân viên", listNhanVien);
	}

	@Override
	public ServiceResult<NhanVien> getOne(Long maNhanVien, HttpServletRequest servletRequest) {
		// TODO Auto-generated method stub
		NhanVien nv = nhanVienResponsitory.getOne(maNhanVien);
		if (nv == null) {
			logger.info("API - Method: {}, StatusCode: {},Status: {}, URI: {}", servletRequest.getMethod(),
					Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND,
					servletRequest.getRequestURI());
			return new ServiceResult<>(AppConstant.FAIL, "Mã nhân viên không tồn tại", null);
		} else {
			logger.info("API - Method: {}, StatusCode: {},Status: {}, URI: {}", servletRequest.getMethod(),
					Response.Status.OK.getStatusCode(), Response.Status.OK, servletRequest.getRequestURI());
			return new ServiceResult<>(AppConstant.SUCCESS, "Đã tìm thấy nhân viên với mã nhân viên:" + maNhanVien, nv);
		}
	}

	@Override
	public ServiceResult<NhanVien> addNhanVien(NhanVien nhanVien) {
		ServiceResult<NhanVien> validationResult = validateNhanVien(nhanVien);
		if (validationResult != null) {
			return validationResult;
		} else {
			boolean isSuccess = nhanVienResponsitory.add(nhanVien);
			if (isSuccess) {
				return new ServiceResult<>(AppConstant.SUCCESS, "Thêm nhân viên thành công.", nhanVien);
			} else {
				return new ServiceResult<>(AppConstant.FAIL, "Thêm nhân viên thất bại", null);
			}
		}
	}

	@Override
	public ServiceResult<NhanVien> updateNhanVien(Long maNhanVien, NhanVien nhanVien) {
		NhanVien nv = nhanVienResponsitory.getOne(maNhanVien);
		if (nv == null) {
			ServiceResult<NhanVien> result = new ServiceResult<>(AppConstant.FAIL, "Mã nhân viên không tồn tại", null);
			return result;
		} else {
			ServiceResult<NhanVien> validationResult = validateNhanVien(nhanVien);
			if (validationResult != null) {
				return validationResult;
			} else {
				try {
					nhanVienResponsitory.update(nhanVien, maNhanVien);
					nhanVien.setMaNhanVien(maNhanVien);
					ServiceResult<NhanVien> result = new ServiceResult<>(AppConstant.SUCCESS,
							"Cập nhật nhân viên thành công", nhanVien);
					return result;
				} catch (Exception e) {
					ServiceResult<NhanVien> result = new ServiceResult<>(AppConstant.FAIL,
							"Cập nhật nhân viên thất bại", null);
					return result;
				}
			}
		}
	}

	@Override
	public ServiceResult<NhanVien> deleteNhanVien(Long maNhanVien, HttpServletRequest servletRequest) {
		NhanVien nv = nhanVienResponsitory.getOne(maNhanVien);
		if (nv == null) {
			logger.info("API - Method: {}, StatusCode: {},Status: {}, URI: {}", servletRequest.getMethod(),
					Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND,
					servletRequest.getRequestURI());
			return new ServiceResult<>(AppConstant.FAIL, "Mã nhân viên không tồn tại", null);
		}
		try {
			logger.info("API - Method: {}, StatusCode: {},Status: {}, URI: {}", servletRequest.getMethod(),
					Response.Status.OK.getStatusCode(), Response.Status.OK, servletRequest.getRequestURI());
			nhanVienResponsitory.delete(maNhanVien);
			return new ServiceResult<>(AppConstant.SUCCESS, "Xóa nhân viên thành công", null);
		} catch (Exception e) {
			logger.info("API - Method: {}, StatusCode: {},Status: {}, URI: {}", servletRequest.getMethod(),
					Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST,
					servletRequest.getRequestURI());
			return new ServiceResult<>(AppConstant.FAIL, "Xóa nhân viên thất bại", null);
		}
	}

	@Override
	public ServiceResult<NhanVien> result(String mess) {
		// TODO Auto-generated method stub
		return new ServiceResult<>(AppConstant.FAIL, mess, null);
	}

	@Override
	public ServiceResult<NhanVien> validateNhanVien(NhanVien nhanVien) {
		// TODO Auto-generated method stub
		List<String> errorMessages = new ArrayList<>();
		if (nhanVien.getHo() == null || nhanVien.getTen() == null || nhanVien.getNgaySinh() == null
				|| nhanVien.getDiaChi() == null || nhanVien.getDienThoai() == null) {
			return result("Dữ liệu không được để trống.");
		}
		if (nhanVien.getMaNhanVien() != null) {
			NhanVien nv = nhanVienResponsitory.getOne(nhanVien.getMaNhanVien());
			if (nv != null) {
				return result("Mã nhân viên đã tồn tại.");
			}
		}
		// Kiểm tra ngày sinh không lớn hơn ngày hiện tại
		LocalDate currentDate = LocalDate.now();
		LocalDate ngaySinh = nhanVien.getNgaySinh().toLocalDate();
		if (ngaySinh.isAfter(currentDate)) {
			errorMessages.add("Ngày sinh không được lớn hơn ngày hiện tại.");
		}

		// Kiểm tra số điện thoại
		String dienThoai = nhanVien.getDienThoai();
		if (!dienThoai.startsWith("0") && !dienThoai.startsWith("+84")) {
			errorMessages.add("Số điện thoại phải bắt đầu bằng 0 hoặc +84.");
		}
		if (!dienThoai.matches("^\\+?\\d{10,11}$")) {
			errorMessages.add("Số điện thoại không hợp lệ.");
		}

		if (!errorMessages.isEmpty()) {
			String errorMessage = String.join(" ", errorMessages);
			return result(errorMessage);
		}

		return null;
	}

}
