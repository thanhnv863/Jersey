package thanhnvMigi.migi.model;

import java.sql.Date;


public class NhanVien {

	private Long maNhanVien;

	private String ho;

	private String ten;

	private Date ngaySinh;

	private String diaChi;

	private String dienThoai;

	public NhanVien(Long maNhanVien, String ho, String ten, Date ngaySinh, String diaChi, String dienThoai) {
		super();
		this.maNhanVien = maNhanVien;
		this.ho = ho;
		this.ten = ten;
		this.ngaySinh = ngaySinh;
		this.diaChi = diaChi;
		this.dienThoai = dienThoai;
	}

	public Long getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(Long maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getHo() {
		return ho;
	}

	public void setHo(String ho) {
		this.ho = ho;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getDienThoai() {
		return dienThoai;
	}

	public void setDienThoai(String dienThoai) {
		this.dienThoai = dienThoai;
	}

	public NhanVien() {
		super();
	}

	

}
