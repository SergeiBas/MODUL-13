package Const;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.http.HttpClient;

public interface Constants {

    String USER_URL = "https://jsonplaceholder.typicode.com/users";
    Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    HttpClient CLIENT = HttpClient.newHttpClient();
}