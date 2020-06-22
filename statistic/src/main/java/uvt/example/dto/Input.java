package uvt.example.dto;

import lombok.Data;

@Data
public class Input {
    private String doi1;
    private String doi2;
    private boolean computedForCS;
    private boolean showJson;
}
