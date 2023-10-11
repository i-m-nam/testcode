package simple.testcode.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class User {

    private int id;
    private String name;

    @Builder
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
