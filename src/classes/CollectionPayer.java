package classes;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * @author amuract
 */
public class CollectionPayer {

    Long id;
    String nickname;
    String first_name;
    String last_name;
    String email;
    CollectionPayerPhone phone;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public CollectionPayerPhone getPhone() {
        return phone;
    }

    public void setPhone(CollectionPayerPhone phone) {
        this.phone = phone;
    }


    @Override
    public String toString() {
        try {
            return this.toJson().toString(4);
        } catch (JSONException ex) {
            Logger.getLogger(CollectionPayer.class.getName()).log(Level.SEVERE, null, ex);
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
        json.put("id", this.id);
        json.put("nickname", this.nickname);
        json.put("first_name", this.first_name);
        json.put("last_name", this.last_name);
        json.put("email", this.email);
        json.put("phone", this.phone);
        return json;
    }
}
