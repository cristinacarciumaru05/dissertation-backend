package uvt.example.dto;

import lombok.Data;

@Data
public class Results {
    private String type;
    private String cnatdcu;

    private boolean isWOS;
    private String WOS;

    private boolean isINFO;
    private String INFO;

    private boolean showJson;
    private String json;
}
