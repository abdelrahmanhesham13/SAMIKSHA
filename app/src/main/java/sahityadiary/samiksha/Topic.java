package sahityadiary.samiksha;

/**
 * Created by Abdelrahman Hesham on 11/27/2017.
 */

public class Topic extends Subject {
    private String topicId;
    private String topicName;
    private String topicStatue;

    public Topic(String  subjectId, String subjectName, String topicId, String topicName, String topicStatue) {
        super(subjectId, subjectName);
        this.topicId = topicId;
        this.topicName = topicName;
        this.topicStatue = topicStatue;
    }

    public String getTopicId() {
        return topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getTopicStatue() {
        return topicStatue;
    }
}
