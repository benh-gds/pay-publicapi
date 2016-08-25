package uk.gov.pay.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.google.common.collect.ObjectArrays.concat;
import static java.lang.String.format;

@ApiModel(value = "Payment Error", description = "A Payment Error response")
@JsonInclude(NON_NULL)
public class PaymentError {

    public enum Code {

        CREATE_PAYMENT_ACCOUNT_ERROR("P0199", "There is a problem with your service account: contact us, quoting the error code"),
        CREATE_PAYMENT_CONNECTOR_ERROR("P0198", "Internal error with GOV.UK Pay: contact us, quoting the error code"),
        CREATE_PAYMENT_PARSING_ERROR("P0197", "Unable to parse JSON; the JSON you sent in the request body is invalid"),
        CREATE_PAYMENT_MISSING_FIELD_ERROR("P0101", "The request you sent is missing a required attribute: %s"),
        CREATE_PAYMENT_VALIDATION_ERROR("P0102", "The request you sent has an invalid attribute value: %s. %s"),

        GET_PAYMENT_NOT_FOUND_ERROR("P0200", "paymentId not found; no payment matched the paymentId you provided"),
        GET_PAYMENT_CONNECTOR_ERROR("P0298", "Internal error with GOV.UK Pay: contact us, quoting the error code"),

        GET_PAYMENT_EVENTS_NOT_FOUND_ERROR("P0300", "paymentId not found; no payment matched the paymentId you provided"),
        GET_PAYMENT_EVENTS_CONNECTOR_ERROR("P0398", "Internal error with GOV.UK Pay: contact us, quoting the error code"),

        SEARCH_PAYMENTS_VALIDATION_ERROR("P0401", "The request you sent has invalid parameters: %s. "),
        SEARCH_PAYMENTS_NOT_FOUND("P0402", "The requested page of search results does not exist"),
        SEARCH_PAYMENTS_CONNECTOR_ERROR("P0498", "Internal error with GOV.UK Pay: contact us, quoting the error code"),

        CANCEL_PAYMENT_NOT_FOUND_ERROR("P0500", "paymentId not found; no payment matched the paymentId you provided"),
        CANCEL_PAYMENT_CONNECTOR_BAD_REQUEST_ERROR("P0501", "Cancelling the payment failed; contact us, quoting the error code"),
        CANCEL_PAYMENT_CONNECTOR_ERROR("P0598", "Internal error with GOV.UK Pay; contact us, quoting the error code"),

        CREATE_PAYMENT_REFUND_CONNECTOR_ERROR("P0698", "Internal error with GOV.UK Pay; contact us, quoting the error code"),
        CREATE_PAYMENT_REFUND_PARSING_ERROR("P0697", "Unable to parse JSON; the JSON you sent in the request body is invalid"),
        CREATE_PAYMENT_REFUND_NOT_FOUND_ERROR("P0600", "paymentId not found; no payment matched the paymentId you provided"),
        CREATE_PAYMENT_REFUND_MISSING_FIELD_ERROR("P0601", "The request you sent is missing a required attribute: %s"),
        CREATE_PAYMENT_REFUND_VALIDATION_ERROR("P0602", "The value of an attribute you sent is invalid: %s. %s"),
        CREATE_PAYMENT_REFUND_NOT_AVAILABLE("P0603", "The payment is not available for refund. Payment refund status: %s"),

        GET_PAYMENT_REFUND_NOT_FOUND_ERROR("P0700", "refundId not found; no refund matched the refundId you provided"),
        GET_PAYMENT_REFUND_CONNECTOR_ERROR("P0798", "Internal error with GOV.UK Pay; contact us, quoting the error code"),

        GET_PAYMENT_REFUNDS_NOT_FOUND_ERROR("P0800", "refundId not found; no refund matched the refundId you provided"),
        GET_PAYMENT_REFUNDS_CONNECTOR_ERROR("P0898", "Internal error with GOV.UK Pay; contact us, quoting the error code"),

        TOO_MANY_REQUESTS_ERROR("P0900", "Too many requests; your service account is sending requests above the allowed rate; try the request again in a few seconds"),
        REQUEST_DENIED_ERROR("P0920", "Request blocked by security rules. Our firewall blocked your request. See Troubleshooting section of documentation for details");

        private String value;
        private String format;

        Code(String value, String format) {
            this.value = value;
            this.format = format;
        }

        public String value() {
            return value;
        }

        public String getFormat() {
            return format;
        }
    }

    private String field;
    private final Code code;
    private final String description;

    public static PaymentError aPaymentError(Code code, Object... parameters) {
        return new PaymentError(code, parameters);
    }

    public static PaymentError aPaymentError(String fieldName, Code code, Object... parameters) {
        return new PaymentError(fieldName, code, parameters);
    }

    private PaymentError(Code code, Object... parameters) {
        this.code = code;
        this.description = format(code.getFormat(), parameters);
    }

    private PaymentError(String fieldName, Code code, Object... parameters) {
        this.field = fieldName;
        this.code = code;
        this.description = format(code.getFormat(), concat(fieldName, parameters));
    }

    @ApiModelProperty(example = "amount")
    public String getField() {
        return field;
    }

    @ApiModelProperty(example = "P0102")
    public String getCode() {
        return code.value();
    }

    @ApiModelProperty(example = "Invalid attribute value: amount. Must be less than or equal to 10000000")
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "PaymentError{" +
                "field=" + field +
                "code=" + code +
                ", description='" + description + '\'' +
                '}';
    }
}
