package net.codejava.client;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductClientTestRest {
	private static String baseURI = "http://localhost:8080/testRest/rest/products";

	public static void main(String[] args) {
		testList();
		testGet();
		// testAdd();
		// testUpdate();
		// testDelete();
		testList();
	}

	static void testList() {
		WebTarget target = getWebTarget();
		String response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class);
		// System.out.println("\n\n\n" + response);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode rootNode = objectMapper.readTree(response);
			System.out.println(response);
			for (int i = 0; i < 10; i++) {
				JsonNode node = rootNode.get(i);
				if (node == null) {
					break;
				}

				System.out.println("ID: " + node.get("id").asInt() + " NAME: " + node.get("name").asText() + "  "
						+ node.get("price").asDouble());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void testGet() {
		WebTarget target = getWebTarget();
		String productId = "1";
		Product product = target.path(productId).request().accept(MediaType.APPLICATION_JSON).get(Product.class);
		System.out.println("\n\n\n" + product);
	}

	static void testUpdate() {
		WebTarget target = getWebTarget();
		String productId = "1";
		Product product = new Product("car", 12700.45f);
		Response response = target.path(productId).request().put(Entity.entity(product, MediaType.APPLICATION_JSON),
				Response.class);
		System.out.println("\n\n\n" + response);
		// System.out.println(response.getLocation().toString());
	}

	static void testAdd() {
		WebTarget target = getWebTarget();
		Product product = new Product("myProd1", 1127.45f);
		Response response = target.request().post(Entity.entity(product, MediaType.APPLICATION_JSON), Response.class);
		System.out.println("\n\n\n" + response);
		System.out.println(response.getLocation().toString());
	}

	static void testDelete() {
		WebTarget target = getWebTarget();
		String productId = "2";

		Response response = target.path(productId).request().delete(Response.class);
		System.out.println("\n\n\nDELETE: " + response);

	}

	static WebTarget getWebTarget() {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		return client.target(baseURI);
	}

}