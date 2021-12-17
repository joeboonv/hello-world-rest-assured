package com.valtech;

import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.closeTo;

@RunWith(DataProviderRunner.class)
public class SoapTest {

    // TODO Move to TestNG instead of JUnit

    @Test
    @UseDataProvider("dataProviderCelsiusToFahrenheit")
    public void soapTemperatureApiConvertsSeveralValues(double inputDegreesC, double expectedDegreesF, double toleranceDegreesF) {
        System.out.println("Test CelsiusToFahrenheit: " + inputDegreesC + "C -> " + expectedDegreesF + "F +/- " + toleranceDegreesF + "F");

        String soapRequestTemplate = """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
                   <soapenv:Header/>
                   <soapenv:Body>
                      <CelsiusToFahrenheitRequest xmlns="http://learnwebservices.com/services/tempconverter">
                            <TemperatureInCelsius>%s</TemperatureInCelsius>
                      </CelsiusToFahrenheitRequest>
                   </soapenv:Body>
                </soapenv:Envelope>
                 """;

        String soapRequest = String.format(soapRequestTemplate, inputDegreesC);

        given().
                contentType(ContentType.XML).
                body(soapRequest).
        when().
                post("https://www.learnwebservices.com/services/tempconverter").
        then().
                statusCode(200).
                body("Envelope.Body.CelsiusToFahrenheitResponse.TemperatureInFahrenheit.toDouble()",
                        closeTo(expectedDegreesF, toleranceDegreesF));
    }

    @DataProvider
    public static Object[] dataProviderCelsiusToFahrenheit() {
        return new Object[][] {
                // C   F   Tolerance
                {-40d, -40.0d, 0.2d},
                {-29d, -20.2d, 0.2d},
                {-10d, 014.0d, 0.2d},
                {0.0d, 032.0d, 0.2d},
                {037d, 098.6d, 0.2d},
                {100d, 212.0d, 0.2d},
                {105d, 221.0d, 0.2d}
        };
    }

}