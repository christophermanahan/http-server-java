package io.github.christophermanahan.carnitas;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HTTPResponseTest {

    @Test
    void creates200OKResponseBytes() {
        String body = "";
        Response httpResponse = new HTTPResponse(body);

        byte[] httpResponseBytes = httpResponse.serialize();
        byte[] expectedResponse = (
          Constants.VERSION + " " + StatusCodes.GET
          + Constants.CRLF
          + Headers.contentLength(body.length())
          + Constants.CRLF
          + Constants.CRLF
        ).getBytes();

        Assertions.assertArrayEquals(expectedResponse, httpResponseBytes);
    }

    @Test
    void creates201OKResponseBytes() {
        String body = "body";
        Response httpResponse = new HTTPResponse(body);

        byte[] httpResponseBytes = httpResponse.serialize();
        byte[] expectedResponse = (
          Constants.VERSION + " " + StatusCodes.POST
          + Constants.CRLF
          + Headers.contentLength(body.length())
          + Constants.CRLF
          + Constants.CRLF
          + body
        ).getBytes();

        Assertions.assertArrayEquals(expectedResponse, httpResponseBytes);
    }
}
