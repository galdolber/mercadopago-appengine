package classes;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * @author amuract
 */
public class CollectionPayerPhone {

    String area_code;
    String number;
    String extension;

    public String getAreaCode() {
        return area_code;
    }

    public void setAreaCode(String area_code) {
        this.area_code = area_code;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        try {
            return this.toJson().toString(4);
        } catch (JSONException ex) {
            Logger.getLogger(CollectionPayerPhone.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
    }

    /**
     * Generates a JSONObject from the object
     * @return
     * @throws JSONException
     */
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("area_code", this.area_code);
        json.put("number", this.number);
        json.put("extension", this.extension);
     
        return json;
    }
}

