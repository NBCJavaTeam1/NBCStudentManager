import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataManager<T> {
    Gson gson;
    final String DIRECTORY = "Database";
    final String FILE_PATH = "\\";
    final String FILE_EXTENSION = ".json";

    public DataManager() {
        gson = new Gson();
    }

    public void directoryInitialize() {
        File folder = new File(DIRECTORY);

        if(!folder.exists()) {
            folder.mkdir();
        }
    }

    // File을 생성 함수( File이 존재하지 않을 경우 생성 )
    public File openFile(String fileName) throws Exception {
        File file = new File(DIRECTORY + FILE_PATH + fileName + FILE_EXTENSION);

        if(!file.exists()) {
            file.createNewFile();
        }

        return file;
    }
    // .json 파일을 읽어와서 FileReader로 생성하는 함수 ( 없을시 파일 생성 )
    public Reader loadFileReader(String fileName) throws Exception {
        File file = openFile(fileName);
        return new FileReader(file);
    }

    // .json 파일을 읽어와서 FileReader로 생성하는 함수 ( 없을시 파일 생성 )
    public Writer writeFileReader(String fileName) throws Exception {
        File file = openFile(fileName);
        return new FileWriter(file);
    }

    public void saveDatas(String fileName, List<T> lists) throws Exception {
        String jsonData = gson.toJson(lists);
        Writer reader = writeFileReader(fileName);

        reader.write(jsonData);
        reader.close();

        System.out.println(jsonData);
    }

    public List<T> loadDatas(String fileName, Class<T> myType) throws Exception  {
        Reader reader = loadFileReader(fileName);
        JsonElement jsonObject = JsonParser.parseReader(reader);

        System.out.println(jsonObject.toString());

        Type listType = TypeToken.getParameterized(List.class, myType).getType();

        reader.close();
        return gson.fromJson(jsonObject, listType);
    }
}
