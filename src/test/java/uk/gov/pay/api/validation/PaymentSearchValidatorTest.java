package uk.gov.pay.api.validation;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static uk.gov.pay.api.matcher.ValidationExceptionMatcher.aValidationExceptionContaining;

public class PaymentSearchValidatorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void validateParams_shouldSuccessValidation() {
        PaymentSearchValidator.validateSearchParameters("success", "ref", "2016-01-25T13:23:55Z", "2016-01-25T13:23:55Z", "1", "1");
    }

    @Test
    public void validateParams_reference_shouldGiveAnErrorValidation() throws Exception {
        expectedException.expect(aValidationExceptionContaining("P0401", "Invalid parameters: reference. See Public API documentation for the correct data formats"));
        PaymentSearchValidator.validateSearchParameters("success", randomAlphanumeric(500), "2016-01-25T13:23:55Z", "2016-01-25T13:23:55Z", "1", "1");
    }

    @Test
    public void validateParams_state_shouldGiveAnErrorValidation() throws Exception {
        expectedException.expect(aValidationExceptionContaining("P0401", "Invalid parameters: state. See Public API documentation for the correct data formats"));
        PaymentSearchValidator.validateSearchParameters("invalid", "ref", "2016-01-25T13:23:55Z", "2016-01-25T13:23:55Z", "1", "1");
    }

    @Test
    public void validateParams_toDate_shouldGiveAnErrorValidation() throws Exception {
        expectedException.expect(aValidationExceptionContaining("P0401", "Invalid parameters: to_date. See Public API documentation for the correct data formats"));
        PaymentSearchValidator.validateSearchParameters("success", "ref", "2016-01-25T13:23:55Z", "2016-01-25T13-23:55Z", "1", "1");
    }

    @Test
    public void validateParams_fromDate_shouldGiveAnErrorValidation() throws Exception {
        expectedException.expect(aValidationExceptionContaining("P0401", "Invalid parameters: from_date. See Public API documentation for the correct data formats"));
        PaymentSearchValidator.validateSearchParameters("success", "ref", "2016-01-25T13-23:55Z", "2016-01-25T13:23:55Z", "1", "1");
    }

    @Test
    public void validateParams_shouldGiveAnErrorValidation_forAllParams() throws Exception {
        expectedException.expect(aValidationExceptionContaining("P0401", "Invalid parameters: state, reference, from_date, to_date, page, display_size. See Public API documentation for the correct data formats"));
        PaymentSearchValidator.validateSearchParameters("invalid", randomAlphanumeric(500), "2016-01-25T13-23:55Z", "2016-01-25T13-23:55Z", "-1", "-1");
    }

    @Test
    public void validateParams_shouldGiveAnErrorValidation_forZeroPageDisplay() throws Exception {
        expectedException.expect(aValidationExceptionContaining("P0401", "Invalid parameters: state, reference, from_date, to_date, page, display_size. See Public API documentation for the correct data formats"));
        PaymentSearchValidator.validateSearchParameters("invalid", randomAlphanumeric(500), "2016-01-25T13-23:55Z", "2016-01-25T13-23:55Z", "0", "0");
    }

    @Test
    public void validateParams_shouldGiveAnErrorValidation_forMaxedOutValuesPageDisplaySize() throws Exception {
        expectedException.expect(aValidationExceptionContaining("P0401", "Invalid parameters: state, reference, from_date, to_date, page, display_size. See Public API documentation for the correct data formats"));
        PaymentSearchValidator.validateSearchParameters("invalid", randomAlphanumeric(500), "2016-01-25T13-23:55Z", "2016-01-25T13-23:55Z", String.valueOf(Integer.MAX_VALUE+1), String.valueOf(Integer.MAX_VALUE+1));
    }

    @Test
    public void validateParams_shouldNotGiveAnErrorValidation_ForMissingPageDisplaySize() throws Exception {
        expectedException.expect(aValidationExceptionContaining("P0401", "Invalid parameters: state, reference, from_date, to_date. See Public API documentation for the correct data formats"));
        PaymentSearchValidator.validateSearchParameters("invalid", randomAlphanumeric(500), "2016-01-25T13-23:55Z", "2016-01-25T13-23:55Z", null, null);
    }

    @Test
    public void validateParams_shouldNotGiveAnErrorValidation_ForNonNumberPageAndSize() throws Exception {
        expectedException.expect(aValidationExceptionContaining("P0401", "Invalid parameters: state, reference, from_date, to_date, page, display_size. See Public API documentation for the correct data formats"));
        PaymentSearchValidator.validateSearchParameters("invalid", randomAlphanumeric(500), "2016-01-25T13-23:55Z", "2016-01-25T13-23:55Z", "non-numeric-page", "non-numeric-size");
    }


}
