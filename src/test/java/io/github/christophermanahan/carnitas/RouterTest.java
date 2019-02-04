package io.github.christophermanahan.carnitas;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RouterTest {
    @Test
    void itProcessesAGETRequestIntoAResponseIfTheRouteHasBeenAdded() {
        Function<HTTPRequest, HTTPResponse> handler = (HTTPRequest request) -> new ResponseBuilder()
          .set(HTTPResponse.Status.OK)
          .get();
        HTTPRequest request = new HTTPRequest(HTTPRequest.Method.GET, "/simple_get");
        Router router = new Router()
          .get("/simple_get", handler);

        HTTPResponse response = router.handle(request);

        assertEquals(handler.apply(request), response);
    }

    @Test
    void itProcessesAHEADRequestIntoAResponseIfTheRouteHasBeenAdded() {
        Function<HTTPRequest, HTTPResponse> handler = (HTTPRequest request) -> new ResponseBuilder()
          .set(HTTPResponse.Status.OK)
          .get();
        HTTPRequest request = new HTTPRequest(HTTPRequest.Method.HEAD, "/simple_get");
        Router router = new Router()
          .head("/simple_get", handler);

        HTTPResponse response = router.handle(request);

        assertEquals(handler.apply(request), response);
    }

    @Test
    void itProcessesAPOSTRequestIntoAResponseIfTheRouteHasBeenAdded() {
        Function<HTTPRequest, HTTPResponse> handler = (HTTPRequest request) -> new ResponseBuilder()
          .set(HTTPResponse.Status.CREATED)
          .get();
        HTTPRequest request = new HTTPRequest(HTTPRequest.Method.POST, "/simple_post");
        Router router = new Router()
          .post("/simple_post", handler);

        HTTPResponse response = router.handle(request);

        assertEquals(handler.apply(request), response);
    }

    @Test
    void itProcessesARequestIntoAResponseIfMultipleRoutesHaveBeenAdded() {
        Function<HTTPRequest, HTTPResponse> handler = (HTTPRequest request) -> new ResponseBuilder()
          .set(HTTPResponse.Status.OK)
          .get();
        HTTPRequest request = new HTTPRequest(HTTPRequest.Method.GET, "simple_get_again");
        Router router = new Router()
          .get( "/simple_get", handler)
          .get("simple_get_again", handler);

        HTTPResponse response = router.handle(request);

        assertEquals(handler.apply(request), response);
    }

    @Test
    void itProcessesARequestIntoANotFoundResponseIfTheRouteHasNotBeenAdded() {
        Function<HTTPRequest, HTTPResponse> handler = (HTTPRequest request) -> new ResponseBuilder()
          .set(HTTPResponse.Status.OK)
          .get();
        HTTPRequest request = new HTTPRequest(HTTPRequest.Method.POST, "/simple_post");
        Router router = new Router()
          .get( "/simple_get", handler);

        HTTPResponse response = router.handle(request);

        HTTPResponse expectedResponse = new ResponseBuilder()
          .set(HTTPResponse.Status.NOT_FOUND)
          .set(List.of(Headers.contentLength(0)))
          .get();
        assertEquals(expectedResponse, response);
    }

    @Test
    void itDoesNotAllowRequestsToRoutesThatCannotRespondToTheRequestMethod() {
        Function<HTTPRequest, HTTPResponse> handler = (HTTPRequest request) -> new ResponseBuilder()
          .set(HTTPResponse.Status.CREATED)
          .get();
        HTTPRequest request = new HTTPRequest(HTTPRequest.Method.GET, "/simple_post");
        Router router = new Router()
          .post("/simple_post", handler);

        HTTPResponse response = router.handle(request);

        HTTPResponse expectedResponse = new ResponseBuilder()
          .set(HTTPResponse.Status.METHOD_NOT_ALLOWED)
          .set(List.of(
            Headers.contentLength(0),
            Headers.allow(List.of(
              HTTPRequest.Method.POST,
              HTTPRequest.Method.OPTIONS
            ))
          )).get();
        assertEquals(expectedResponse, response);
    }

    @Test
    void itProcessesAnOPTIONSRequestIntoAResponse() {
        Function<HTTPRequest, HTTPResponse> handler = (HTTPRequest request) -> new ResponseBuilder()
          .set(HTTPResponse.Status.OK)
          .get();
        HTTPRequest request = new HTTPRequest(HTTPRequest.Method.OPTIONS, "/simple_get");
        Router router = new Router()
          .get( "/simple_get", handler);

        HTTPResponse response = router.handle(request);

        HTTPResponse expectedResponse = new ResponseBuilder()
          .set(HTTPResponse.Status.OK)
          .set(List.of(
            Headers.allow(List.of(
              HTTPRequest.Method.GET,
              HTTPRequest.Method.OPTIONS
            )),
            Headers.contentLength(0)
          )).get();
        assertEquals(expectedResponse, response);
    }
}
