# PlacetoPay Redirection Java library

With this code you will be able to quickly connect with the PlacetoPay redirection service.

In order to see how it works, please refer to the examples.

## Installation

Gradle
------
```
dependencies {
    ...
    compile 'com.placetopay:java-placetopay:1.0.5'
}
```

Maven
-----
```
<dependency>
  <groupId>com.placetopay</groupId>
  <artifactId>java-placetopay</artifactId>
  <version>1.0.5</version>
  <type>pom</type>
</dependency>
```

Or If you just want to run the examples contained in this project you must fill the credentials in test.properties file

## Usage

Create an object with the configuration required for that instance

```
PlaceToPay placetopay = new com.placetopay.java_placetopay.PlaceToPay(
                    "YOUR_LOGIN",
                    "YOUR_TRANKEY",
                    new URL("https://THE_BASE_URL_TO_POINT_AT")
                );
```

### Creating a new Payment Request to obtain a Session Payment URL

Just provide the information of the payment needed and you will get a process url if its successful, for this example we are using the MINIMUM INFORMATION that needs to be provided

```
String reference = 'COULD_BE_THE_PAYMENT_ORDER_ID";
String data = 
            "{  " +
            "   \"payment\":{  " +
            "      \"reference\":\"TESTING123456\"," +
            "      \"amount\":{  " +
            "         \"currency\":\"COP\"," +
            "         \"total\":\"10000\"" +
            "      }" +
            "   }," +
            "   \"ipAddress\": \"127.0.0.1\"," +
            "   \"userAgent\": \"PHPUnit\"," +
            "   \"returnUrl\":\"http:\\/\\/your.url.com\\/return?reference=TESTING123456\"" +
            "}";

RedirectRequest request = new RedirectRequest(data);
RedirectResponse response = gateway.request(request);
if (response.isSuccessful()) {
    // STORE THE response.getRequestId() and response.getProcessUrl() on your DB associated with the payment order
    // Redirect the client to the processUrl or display it on the JS extension
    String redirectTo = response.getProcessUrl();
} else {
    // There was some error so check the message and log it
    response.getStatus().getMessage()
}
```

### Obtain information about a previously created session

```
RedirectInformation response = placetopay.query("THE_REQUEST_ID_TO_QUERY");
if (response.isSuccessful()) {
    // In order to use the functions please refer to the com.placetopay.java_placetopay.Entities.Models.RedirectInformation class

    if (response.getStatus().isApproved()) {
        // The payment has been approved
    }
} else {
    // There was some error with the connection so check the message
    System.out.print(($response.getStatus().getMessage() + "\n");
}
```