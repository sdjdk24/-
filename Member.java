package dto;

public class Member {
    private String id;
    private String password;
    private String name;
    private int points;

    // 기본 생성자
    public Member() {}

    // 모든 필드를 초기화하는 생성자
    public Member(String id, String password, String name, int points) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.points = points;
    }

    // Getter 및 Setter 메소드
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
