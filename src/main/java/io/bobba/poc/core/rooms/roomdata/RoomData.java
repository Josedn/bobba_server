package io.bobba.poc.core.rooms.roomdata;

public class RoomData {
	private int id;
	private String name;
	private String owner;
	private String description;
	private int capacity;
	private int userCount;
	private String password;
	private String modelId;
	private LockType lockType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LockType getLockType() {
		return lockType;
	}

	public void setLockType(LockType lockType) {
		this.lockType = lockType;
	}

	public int getId() {
		return id;
	}

	public String getOwner() {
		return owner;
	}

	public String getModelId() {
		return modelId;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RoomData(int id, String name, String owner, String description, int capacity, String password, String modelId,
			LockType lockType) {
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.description = description;
		this.capacity = capacity;
		this.password = password;
		this.modelId = modelId;
		this.lockType = lockType;
		this.userCount = 0;
	}

}
