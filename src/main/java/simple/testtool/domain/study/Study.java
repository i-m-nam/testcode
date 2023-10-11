package simple.testtool.domain.study;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import simple.testtool.enums.study.StudyStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study {

    @Id
    @GeneratedValue
    private Long id;
    private StudyStatus status = StudyStatus.DRAFT;
    private int limitCount;
    private String name;
    private LocalDateTime openedDateTime;
    private Long ownerId;

    @Builder
    public Study(Long id, StudyStatus status, int limitCount, String name, LocalDateTime openedDateTime, Long ownerId) {
        this.id = id;
        this.status = status;
        this.limitCount = limitCount;
        this.name = name;
        this.openedDateTime = openedDateTime;
        this.ownerId = ownerId;
    }

    public Study(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("limit은 0보다 커야 한다.");
        }
        this.limitCount = limit;
    }

    public void open() {
        this.openedDateTime = LocalDateTime.now();
        this.status = StudyStatus.OPENED;

    }


    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

}