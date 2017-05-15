package com.my.diskbackedcollection;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public class DiskBackedCollection<K extends Serializable, V extends Serializable> extends TreeMap<K, V> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Local Database for storing the collections
	private DB database;
	// internal map
	private Map<K, V> treeMap;
	// name of the database
	private static final String DB_NAME = "DB_Collection";
	// name of the internal map
	private static final String MAP_NAME = "internal_map";

	private void initiliazeDB() {
		this.database = DBMaker.newFileDB(new File(DB_NAME)).closeOnJvmShutdown().make();
	}

	private void initiliazeTreeMap() {
		this.treeMap = this.database.getTreeMap(MAP_NAME);
	}

	/**
	 * Constructor which initialize DB and Map
	 */
	public DiskBackedCollection() {
		this.initiliazeDB();
		this.initiliazeTreeMap();
	}

	@Override
	public V put(K key, V value) {
		synchronized (treeMap) {
			return this.treeMap.put(key, value);
		}

	}

	@Override
	public V get(Object key) {
		synchronized (treeMap) {
			return this.treeMap.get(key);
		}

	}

}
