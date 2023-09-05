package thanhnvMigi.migi.resource;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import thanhnvMigi.migi.model.NhanVien;
import thanhnvMigi.migi.result.ServiceResult;
import thanhnvMigi.migi.service.NhanVienService;
import thanhnvMigi.migi.service.impl.NhanVienServiceImpl;

@Path("/migi")
public class MigiResource {

	private NhanVienService nhanVienService = new NhanVienServiceImpl();

	@Context
	private HttpServletRequest servletRequest;

	@GET
	@Path("/get-all")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAll() {
		ServiceResult<List<NhanVien>> result = nhanVienService.getAll();
		return Response.status(Response.Status.OK).entity(result).build();
	}

	@GET
	@Path("/get-one/{ma}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ServiceResult<NhanVien> getOne(@PathParam("ma") Long ma ) {
		return nhanVienService.getOne(ma, servletRequest);
	}

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ServiceResult<NhanVien> add(NhanVien nv) {
		return nhanVienService.addNhanVien(nv);
	}

	@PUT
	@Path("/update/{ma}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ServiceResult<NhanVien> update(@PathParam("ma") String ma, NhanVien nv) {
		return nhanVienService.updateNhanVien(Long.valueOf(ma), nv);
	}

	@DELETE
	@Path("/delete/{ma}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ServiceResult<NhanVien> delete(@PathParam("ma") String ma) {
		return nhanVienService.deleteNhanVien(Long.valueOf(ma), servletRequest);
	}

	@GET
	@Path("/hello")
	public String xinChaoMigi() {
		return "Xin chào Thành";
	}

	@GET
	@Path("/working-directory")
	public String getWorkingDirectory() {
		return System.getProperty("user.dir");
	}
}