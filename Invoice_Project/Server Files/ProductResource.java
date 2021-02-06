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

@Path("/products")
public class ProductResource {
	private ProductDAO dao = ProductDAO.getInstance();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> list() {
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
	public Response add(Product product) throws URISyntaxException {
		int newProductId = dao.add(product);
		URI uri;
		uri = new URI("/Csc668-868-REST-website/rest/products" + newProductId);

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
		Product product = dao.get(id);
		if (product != null) {
			return Response.ok(product, MediaType.APPLICATION_JSON).build();
		}
		return Response.status(Response.Status.NOT_FOUND).build(); // http 400 error response
	}

	/*
	 * http://localhost:8080/Csc668-868-REST-website/rest/products/1
	 */

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response update(@PathParam("id") int id, Product product) throws URISyntaxException {
		product.setId(id);
		if (dao.update(product)) {
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
