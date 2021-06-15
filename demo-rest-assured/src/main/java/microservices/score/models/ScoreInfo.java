package microservices.score.models;

import lombok.Data;

@Data
public class ScoreInfo {
    String name;
    String score;

    public ScoreInfo(String name){
        this.name = "scores/sources/" + name;
        this.score = "1";
    }
}
