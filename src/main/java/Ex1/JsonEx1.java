package Ex1;

import Const.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class JsonEx1 implements Constants {
    public static String sendPost(UserEx1 user) throws IOException, InterruptedException {

        final String body = GSON.toJson(user);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USER_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return String.valueOf(response.body());
    }

    public static String sendPut(UserEx1 user) throws IOException, InterruptedException {

        final String body = GSON.toJson(user);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USER_URL + "/3"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return String.valueOf(response.body());
    }

    public static String sendDelete(int userId) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USER_URL + "/" + userId))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return String.valueOf(response.statusCode());
    }

    public static List<UserEx1> getUsers() throws IOException, InterruptedException {
        HttpResponse<String> response = getResponseByParameter("");
        Type type = TypeToken.getParameterized(List.class, UserEx1.class).getType();
        List<UserEx1> allUsers = new Gson().fromJson(response.body(), type);
        return allUsers;
    }

    public static String getUserById(int id) throws IOException, InterruptedException {
        HttpResponse<String> response = getResponseByParameter("/" + id);
        return response.body();
    }

    public static String getUserByUsername(String username) throws IOException, InterruptedException {
        HttpResponse<String> response = getResponseByParameter("?username=" + username);
        return response.body();
    }

    private static HttpResponse<String> getResponseByParameter(String parameter) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USER_URL + parameter))
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }
}