package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

/**
 * @author amuract
 */
public class ListCollection {

    List<Collection> listCollection;
    Long total;
    Long limit;
    Long offset;

    public ListCollection() {
        this.listCollection = new ArrayList<Collection>();
    }

    public List<Collection> getListCollection() {
        return listCollection;
    }

    public void setListCollection(List<Collection> listCollection) {
        this.listCollection = listCollection;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
    
    @Override
    public String toString() {
        try {
            return this.toJsonArray().toString(4);
        } catch (JSONException ex) {
            Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
    }

    /**
     * Generates a JSONArray from the list of objects
     * @return
     * @throws JSONException
     */
    public JSONArray toJsonArray() throws JSONException {
        JSONArray jarray = new JSONArray();

        for (Collection collectionDto : listCollection) {
            jarray.put(collectionDto.toJson());
        }

        return jarray;
    }
}
