package microservices.lead.sources.models;

import lombok.Data;
import utils.CommonUtils;

@Data
public class SourceInfo {
    Boolean hidden;
    Boolean online;
    String product;
    String source;

    public SourceInfo(){
        this.hidden = false;
        this.online = false;
        this.product = "products/car-insurance";
        this.source = "auto test " + CommonUtils.getRandomFiveCharsString();
    }
}
