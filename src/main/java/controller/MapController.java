package controller;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapController {

    private static MapController mapController;
    public static ArrayList<char[][]> standingMaps = new ArrayList<>();

    private MapController() {
    }

    public static MapController getInstance() {
        if (mapController == null)
            mapController = new MapController();
        return mapController;
    }

    public char[][] generateMap() {
        char[][] field = new char[21][21];
        putWalls(field);
        field[1][1] = '*';
        generateField(field, 1, 1, 21, 21);
        removeWalls(field);
        field[10][10] = '0';
        field[19][19] = '0';
        field[19][1] = '0';
        field[1][19] = '0';
        return field;
    }

    private static void putWalls(char[][] field) {
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 21; j++) {
                field[i][j] = '1';
            }
        }
    }

    private void removeWalls(char[][] field) {
        Random random = new Random();
        for (int i = 1; i < 20; i++) {
            for (int j = 1; j < 20; j++) {
                int randomInteger = random.nextInt();
                if (randomInteger < 0) randomInteger *= -1;
                if (randomInteger % 3 == 0) field[i][j] = '0';
            }
        }
    }

    static void generateField(char[][] field, int x, int y, int rows, int columns) {
        int[] random = {0, 1, 2, 3};
        shuffle(random);

        for (int i = 0; i < 4; i++) {
            switch (random[i]) {
                case 0:
                    if (isSafe(field, x + 2, y, rows, columns)) {
                        field[y][x + 2] = '*';
                        field[y][x + 1] = '0';
                        generateField(field, x + 2, y, rows, columns);

                    }
                    break;

                case 1:
                    if (isSafe(field, x, y + 2, rows, columns)) {
                        field[y + 2][x] = '*';
                        field[y + 1][x] = '0';
                        generateField(field, x, y + 2, rows, columns);

                    }
                    break;

                case 2:
                    if (isSafe(field, x - 2, y, rows, columns)) {
                        field[y][x - 2] = '*';
                        field[y][x - 1] = '0';
                        generateField(field, x - 2, y, rows, columns);

                    }
                    break;

                case 3:
                    if (isSafe(field, x, y - 2, rows, columns)) {
                        field[y - 2][x] = '*';
                        field[y - 1][x] = '0';
                        generateField(field, x, y - 2, rows, columns);

                    }
                    break;

            }
        }
        return;
    }

    static boolean isSafe(char[][] field, int x, int y, int rows, int columns) {
        if (x > 0 &&
                y > 0 &&
                x < columns &&
                y < rows &&
                field[y][x] != '*') return true;
        else return false;
    }

    static void shuffle(int[] random) {
        Random rand = new Random();
        for (int i = 0; i < 4; i++) {
            int randomIndex = rand.nextInt(4);
            int temp = random[i];
            random[i] = random[randomIndex];
            random[randomIndex] = temp;
        }
    }

    public static void getStandingMapsFromJson() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("standingMaps.json")));
            ArrayList<char[][]> maps = new YaGson().fromJson(json,
                    new TypeToken<List<char[][]>>() {
                    }.getType()
            );
            standingMaps = maps;
        } catch (IOException e) {
            System.out.println("can't read from json");
        }
    }
}
