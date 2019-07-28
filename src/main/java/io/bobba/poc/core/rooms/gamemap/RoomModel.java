package io.bobba.poc.core.rooms.gamemap;

public class RoomModel {
	private int mapSizeX;
	private int mapSizeY;

	private int doorX;

	public int getMapSizeX() {
		return mapSizeX;
	}

	public int getMapSizeY() {
		return mapSizeY;
	}

	public int getDoorX() {
		return doorX;
	}

	public int getDoorY() {
		return doorY;
	}

	public int getDoorZ() {
		return doorZ;
	}

	public int getDoorRot() {
		return doorRot;
	}

	public String getHeightmap() {
		return heightmap;
	}

	public int[][] getSqFloorHeight() {
		return sqFloorHeight;
	}

	public SqState[][] getSqState() {
		return sqState;
	}

	private int doorY;
	private int doorZ;
	private int doorRot;

	private String heightmap;
	private int[][] sqFloorHeight;
	private SqState[][] sqState;

	private static short parse(char input) {
		switch (input) {
		case '0':
			return 0;
		case '1':
			return 1;
		case '2':
			return 2;
		case '3':
			return 3;
		case '4':
			return 4;
		case '5':
			return 5;
		case '6':
			return 6;
		case '7':
			return 7;
		case '8':
			return 8;
		case '9':
			return 9;
		case 'a':
			return 10;
		case 'b':
			return 11;
		case 'c':
			return 12;
		case 'd':
			return 13;
		case 'e':
			return 14;
		case 'f':
			return 15;
		case 'g':
			return 16;
		case 'h':
			return 17;
		case 'i':
			return 18;
		case 'j':
			return 19;
		case 'k':
			return 20;
		case 'o':
			return 24;
		default:
			throw new NumberFormatException("The input was not in a correct format: " + input);
		}
	}

	public RoomModel(int doorX, int doorY, int doorZ, int doorRot, String heightmap) {
		this.doorX = doorX;
		this.doorY = doorY;
		this.doorZ = doorZ;
		this.doorRot = doorRot;
		this.heightmap = heightmap.toLowerCase();

		String[] tempHeightmap = heightmap.toLowerCase().split("[\\r\\n]+");

		this.mapSizeX = tempHeightmap[0].length();
		this.mapSizeY = tempHeightmap.length;

		this.sqState = new SqState[mapSizeX][mapSizeY];
		this.sqFloorHeight = new int[mapSizeX][mapSizeY];

		for (int y = 0; y < mapSizeY; y++) {
			String line = tempHeightmap[y];

			for (int x = 0; x < mapSizeX; x++) {
				char square = line.charAt(x);
				if (square == 'x') {
					sqState[x][y] = SqState.Closed;
				} else {
					sqState[x][y] = SqState.Walkable;
					sqFloorHeight[x][y] = parse(square);
				}
			}
		}
		sqState[doorX][doorY] = SqState.Walkable;
		sqFloorHeight[doorX][doorY] = doorZ;
	}
	
	public void printHeightMap() {
		for (int y = 0; y < mapSizeY; y++) {
			for (int x = 0; x < mapSizeX; x++) {
				if (sqState[x][y] != SqState.Closed) {
					System.out.print(sqFloorHeight[x][y]);
				} else {
					System.out.print("x");
				}
			}
			System.out.println();
		}
	}
}
