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

    public Results(String type,String cnatdcu,String info,String json){
        this.type=type;
        this.cnatdcu=cnatdcu;
        this.INFO=info;
        this.json=json;
    }
    public Results(){};
}
