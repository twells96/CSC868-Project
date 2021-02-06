package net.codejava.ws;

import java.util.ArrayList;
import java.util.List;

public class StoreDAO {
	private static StoreDAO instance;
	private static List<Store> data = new ArrayList<Store>();
	private static int nextStoreId = 2;

	static {
		data.add(new Store(1, "store1", "LA"));
		data.add(new Store(2, "store2", "SF"));
	}

	private StoreDAO() {
	}

	public static StoreDAO getInstance() {
		if (instance == null) {
			instance = new StoreDAO();
		}
		return instance;
	}

	public List<Store> listAll() {
		return new ArrayList<Store>(data);
	}

	public int add(Store store) {
		nextStoreId++;
		store.setId(nextStoreId);
		data.add(store);
		return nextStoreId;
	}

	public Store get(int id) {
		Store storeToFind = new Store(id);
		int index = data.indexOf(storeToFind);
		if (index >= 0) {
			return data.get(index);
		}
		return null;
	}

	public boolean update(Store store) {
		int index = data.indexOf(store);
		if (index >= 0) {
			data.set(index, store);
			return true;
		}
		return false;
	}

	public boolean delete(int id) {
		System.out.println("\n\nDELETING: " + id);
		Store storeToFind = new Store(id);
		int index = data.indexOf(storeToFind);
		if (index >= 0) {
			data.remove(index);
			return true;
		}
		return false;
	}

}