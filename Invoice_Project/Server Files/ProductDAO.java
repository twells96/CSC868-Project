package net.codejava.ws;

import java.util.ArrayList;
import java.util.List;

/*
 * VERY GOOD YOUTUBE: https://www.youtube.com/watch?v=-_VPzhKJPfE
 * 
 * THIS CLASS PROVIDES ACCESS TO THE DATA; IN THIS CASE, THE DATA IS MAINTAINED HERE
 * THE CLASS MAY ALSO PROVIDE ACCESS TO DATA IN A DATABASE
 * 
 * THE FOLLOWING CODE WAS COPIED FROM AN OPENMRS MODULE I CREATED FOR MY TELEMEDICINE PROJECT
 * THIS INTERFACE IS USED TO PROVIDE DATABASE SERVICES WHERE WE RECORD THE COUNTRIES USED WITH DOCTORS AND PATIENTS
 * 
 * package org.openmrs.module.patientlist.api.db;

	import java.util.List;
	import org.openmrs.api.APIException;
	import org.openmrs.module.patientlist.Country;

	public interface CountryDao {
	
		Country saveCountry(Country item) throws APIException;
	
		public Country getCountry(Integer id);
	
		public List<Country> getAllCountry();
	}

 */

public class ProductDAO {
	private static ProductDAO instance;
	private static List<Product> data = new ArrayList<Product>();
	private static int nextProductId = 2;

	static {
		data.add(new Product(1, "iphone", 999.99f));
		data.add(new Product(2, "xbox", 329.50f));
	}

	private ProductDAO() {
	}

	public static ProductDAO getInstance() {
		if (instance == null) {
			instance = new ProductDAO();
		}
		return instance;
	}

	public List<Product> listAll() {
		return new ArrayList<Product>(data);
	}

	public int add(Product product) {
		nextProductId++;
		product.setId(nextProductId);
		data.add(product);
		return nextProductId;
	}

	public Product get(int id) {
		Product productToFind = new Product(id);
		int index = data.indexOf(productToFind);
		if (index >= 0) {
			return data.get(index);
		}
		return null;
	}

	public boolean update(Product product) {
		int index = data.indexOf(product);
		if (index >= 0) {
			data.set(index, product);
			return true;
		}
		return false;
	}

	public boolean delete(int id) {
		System.out.println("\n\nDELETING: " + id);
		Product productToFind = new Product(id);
		int index = data.indexOf(productToFind);
		if (index >= 0) {
			data.remove(index);
			return true;
		}
		return false;
	}
}
