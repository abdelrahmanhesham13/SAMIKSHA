package sahityadiary.samiksha;

/**
 * Created by Abdelrahman Hesham on 11/27/2017.
 */

public class Subject {
    private String subjectId;
    private String subjectName;

    public Subject(String subjectId, String subjectName) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }
}
