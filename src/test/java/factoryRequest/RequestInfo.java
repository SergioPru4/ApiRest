package factoryRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestInfo {

       private String url;
       private String body;
       private Map<String,String> header;

    public  RequestInfo(){
        header= new HashMap<>();
    }

    public String getUrl() {
        return url;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public RequestInfo setUrl(String url) {
        this.url = url;
        return this;
    }

    public RequestInfo setBody(String body) {
        this.body = body;
        return this;
    }

    public RequestInfo setHeader(String key,String value) {
        this.header.put(key,value);
        return this;
    }
}
