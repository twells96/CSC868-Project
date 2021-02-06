package net.codejava.ws;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/stores")
public class StoreResource {
	private StoreDAO dao = StoreDAO.getInstance();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Store> list() {
		return dao.listAll();
	}

	/*
	 * curl -H "Accept: application/json"
	 * http://localhost:8080/Csc668-868-REST-website/rest/products
	 * [{"id":1,"name":"iphone","price":999.99},{"id":2,"name":"xbox","price":329.5}
	 * ]
	 * 
	 */

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(Store store) throws URISyntaxException {
		int newStoreId = dao.add(store);
		URI uri;
		uri = new URI("/Csc668-868-REST-website/rest/stores" + newStoreId);

		return Response.created(uri).build();
	}
	/*
	 * curl -X POST -H "Content-Type: application/json" -d
	 * "{\"name\":\"ipod\",\"price\":199.29}"
	 * http://localhost:8080/Csc668-868-REST-website/rest/products
	 */

	@GET
	@Path("{id}")
	public Response get(@PathParam("id") int id) {
		Store store = dao.get(id);
		if (store != null) {
			return Response.ok(store, MediaType.APPLICATION_JSON).build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}

	/*
	 * http://localhost:8080/Csc668-868-REST-website/rest/products/1
	 */

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response update(@PathParam("id") int id, Store store) throws URISyntaxException {
		store.setId(id);
		if (dao.update(store)) {
			return Response.ok().build();
		}
		return Response.notModified().build();
	}

	/*
	 * curl -v -X PUT -H "Content-Type: application/json" -d
	 * "{\"name\":\"iphone XI\",\"price\":25.03}" -H "Accept: application/json"
	 * http://localhost:8080/Csc668-868-REST-website/rest/products/1
	 */

	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") int id) {
		if (dao.delete(id)) {
			return Response.ok().build();
		}
		return Response.notModified().build();
	}

	/*
	 * curl -v -X DELETE
	 * http://localhost:8080/Csc668-868-REST-website/rest/products/2
	 */

}
