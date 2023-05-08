package Ex3;

public class UserEx3 {
    private int userId;
    private int id;
    private String title;
    private boolean completed;

    public UserEx3(int userId, int id, String title, boolean completed) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean getCompleted() {
        return completed;
    }

    @Override
    public String toString() {
        return "UserEx3{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", completed='" + completed + '\'' +
                '}';
    }
}
