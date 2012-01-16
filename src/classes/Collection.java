package classes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import utils.TimeUtils;

/**
 * @author amuract
 */
public class Collection {

    Long id;
    String site_id;
    Date date_created;
    Date date_approved;
    Date last_modified;
    Date money_release_date;
    Long collector_id;
    String operation_type;
    CollectionPayer payer;
    String external_reference;
    String reason;
    BigDecimal transaction_amount;
    String currency_id;
    BigDecimal shipping_cost;
    BigDecimal total_paid_amount;
    BigDecimal mercadopago_fee;
    BigDecimal net_received_amount;
    String status;
    String status_detail;
    String released;
    String payment_method_id;
    String marketplace;

    public Long getCollectorId() {
        return collector_id;
    }

    public void setCollectorId(Long collector_id) {
        this.collector_id = collector_id;
    }

    public String getCurrencyId() {
        return currency_id;
    }

    public void setCurrencyId(String currency_id) {
        this.currency_id = currency_id;
    }

    public Date getDateApproved() {
        return date_approved;
    }

    public void setDateApproved(Date date_approved) {
        this.date_approved = date_approved;
    }

    public Date getDateCreated() {
        return date_created;
    }

    public void setDateCreated(Date date_created) {
        this.date_created = date_created;
    }

    public String getExternalReference() {
        return external_reference;
    }

    public void setExternalReference(String external_reference) {
        this.external_reference = external_reference;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLastModified() {
        return last_modified;
    }

    public void setLastModified(Date last_modified) {
        this.last_modified = last_modified;
    }

    public String getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(String marketplace) {
        this.marketplace = marketplace;
    }

    public BigDecimal getTotalPaidAmount() {
        return total_paid_amount;
    }

    public void setTotalPaidAmount(BigDecimal total_paid_amount) {
        this.total_paid_amount = total_paid_amount;
    }

    public BigDecimal getMercadopagoFee() {
        return mercadopago_fee;
    }

    public void setMercadopagoFee(BigDecimal mercadopago_fee) {
        this.mercadopago_fee = mercadopago_fee;
    }

    public Date getMoneyReleaseDate() {
        return money_release_date;
    }

    public void setMoneyReleaseDate(Date money_release_date) {
        this.money_release_date = money_release_date;
    }

    public BigDecimal getNetReceivedAmount() {
        return net_received_amount;
    }

    public void setNetReceivedAmount(BigDecimal net_received_amount) {
        this.net_received_amount = net_received_amount;
    }

    public String getOperationType() {
        return operation_type;
    }

    public void setOperationType(String operation_type) {
        this.operation_type = operation_type;
    }

    public CollectionPayer getPayer() {
        return payer;
    }

    public void setPayer(CollectionPayer payer) {
        this.payer = payer;
    }

    public String getPaymentMethodId() {
        return payment_method_id;
    }

    public void setPaymentMethodId(String payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public BigDecimal getShippingCost() {
        return shipping_cost;
    }

    public void setShippingCost(BigDecimal shipping_cost) {
        this.shipping_cost = shipping_cost;
    }

    public String getSiteId() {
        return site_id;
    }

    public void setSiteId(String site_id) {
        this.site_id = site_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDetail() {
        return status_detail;
    }

    public void setStatusDetail(String status_detail) {
        this.status_detail = status_detail;
    }

    public BigDecimal getTransactionAmount() {
        return transaction_amount;
    }

    public void setTransactionAmount(BigDecimal transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    @Override
    public String toString() {
        try {
            return this.toJson().toString(4);
        } catch (JSONException ex) {
            Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
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
        json.put("site_id", this.site_id);
        json.put("date_created", TimeUtils.dateToString(this.date_created));
        json.put("date_approved", TimeUtils.dateToString(this.date_approved));
        json.put("last_modified", TimeUtils.dateToString(this.last_modified));
        json.put("money_release_date", TimeUtils.dateToString(this.money_release_date));
        json.put("collector_id", this.collector_id);
        json.put("operation_type", this.operation_type);
        json.put("payer", this.payer != null ? this.payer.toJson() : null);
        json.put("external_reference", this.external_reference);
        json.put("reason", this.reason);
        json.put("transaction_amount", this.transaction_amount);
        json.put("currency_id", this.currency_id);
        json.put("shipping_cost", this.shipping_cost);
        json.put("total_paid_amount", this.total_paid_amount);
        json.put("mercadopago_fee", this.mercadopago_fee);
        json.put("net_received_amount", this.net_received_amount);
        json.put("status", this.status);
        json.put("status_detail", this.status_detail);
        json.put("released", this.released);
        json.put("payment_method_id", this.payment_method_id);
        json.put("marketplace", this.marketplace);

        return json;
    }


}

