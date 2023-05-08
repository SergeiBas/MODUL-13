package Ex3;

import Const.Constants;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

public class JsonEx3 implements Constants {

    public static List<UserEx3> getOpenTasksByUserId(int userId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users/" + userId + "/todos"))
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        Type type = TypeToken.getParameterized(List.class, UserEx3.class).getType();
        List<UserEx3> allUsers = GSON.fromJson(response.body(), type);

        List<UserEx3> onlyOpenTasks = allUsers.stream()
                .filter(user -> user.getCompleted() == false)
                .collect(Collectors.toList());

        try (FileWriter fr = new FileWriter("open-tasks-for-user-" + userId + ".json")) {
            String openTasks = GSON.toJson(onlyOpenTasks);
            fr.write(openTasks);
        }

        return onlyOpenTasks;
    }
}
