package Ex2;

import Const.Constants;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class JsonEx2 implements Constants {

    public static void getLastCommentOfUserById(int userId) throws IOException, InterruptedException {

        HttpResponse<String> postResponse = getResponse(USER_URL, "/" + userId + "/posts");
        Type typeForUser = TypeToken.getParameterized(List.class, UserEx2.class).getType();

        List<UserEx2> posts = getList(postResponse, typeForUser);
        int lastPostId = posts.stream()
                .mapToInt(i -> i.getId())
                .max()
                .getAsInt();

        HttpResponse<String> commentResponse = getResponse("https://jsonplaceholder.typicode.com/posts/", lastPostId + "/comments");

        Type typeForComment = TypeToken.getParameterized(List.class, Comment.class).getType();
        List<Comment> comments = getList(commentResponse, typeForComment);

        try (FileWriter mfr = new FileWriter("user-" + userId + "-post-" + lastPostId + "-comments.json")) {
            String res = GSON.toJson(comments);
            mfr.write(res);

        } catch (IOException e) {
            throw new IOException();
        }
        System.out.println("File created with name: user-" + userId + "-post-" + lastPostId + "-comments.json");
    }

    private static<T> List<T> getList(HttpResponse<String> response, Type type) {
        List<T> currentUser = GSON.fromJson(response.body(), type);
        return currentUser;
    }

    private static HttpResponse<String> getResponse(String url, String parameter) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + parameter))
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }
}
