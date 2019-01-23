package io.github.christophermanahan.carnitas;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class RequestRouterTest {
    @Test
    void itProcessesAGETRequestIntoAResponseIfTheRouteHasBeenAdded() {
        Function<HTTPRequest, HTTPResponse> handler = (HTTPRequest request) -> new HTTPResponse(StatusCodes.OK);
        HTTPRequest request = new HTTPRequest(RequestRouter.GET, "simple_get");
        RequestRouter router = new RequestRouter()
          .get("simple_get", handler);

        HTTPResponse response = router.process(request);

        assertArrayEquals(handler.apply(request).serialize(), response.serialize());
    }

    @Test
    void itProcessesAHEADRequestIntoAResponseIfTheRouteHasBeenAdded() {
        Function<HTTPRequest, HTTPResponse> handler = (HTTPRequest request) -> new HTTPResponse(StatusCodes.OK);
        HTTPRequest request = new HTTPRequest(RequestRouter.HEAD, "simple_get");
        RequestRouter router = new RequestRouter()
          .head("simple_get", handler);

        HTTPResponse response = router.process(request);

        assertArrayEquals(handler.apply(request).serialize(), response.serialize());
    }

    @Test
    void itProcessesAPOSTRequestIntoAResponseIfTheRouteHasBeenAdded() {
        Function<HTTPRequest, HTTPResponse> handler = (HTTPRequest request) -> new HTTPResponse(StatusCodes.CREATED);
        HTTPRequest request = new HTTPRequest(RequestRouter.POST, "simple_post");
        RequestRouter router = new RequestRouter()
          .post("simple_post", handler);

        HTTPResponse response = router.process(request);

        assertArrayEquals(handler.apply(request).serialize(), response.serialize());
    }

    @Test
    void itProcessesARequestIntoANotFoundResponseIfTheRouteHasNotBeenAdded() {
        Function<HTTPRequest, HTTPResponse> handler = (HTTPRequest request) -> new HTTPResponse(StatusCodes.OK);
        HTTPRequest request = new HTTPRequest(RequestRouter.POST, "simple_post");
        RequestRouter router = new RequestRouter()
          .get( "simple_get", handler);

        HTTPResponse response = router.process(request);

        assertArrayEquals(new HTTPResponse("404 Not Found").serialize(), response.serialize());
    }

    @Test
    void itProcessesAGETRequestIntoAResponseIfMultipleRoutesHaveBeenAdded() {
        Function<HTTPRequest, HTTPResponse> handler = (HTTPRequest request) -> new HTTPResponse(StatusCodes.OK);
        HTTPRequest request = new HTTPRequest(RequestRouter.GET, "simple_get_again");
        RequestRouter router = new RequestRouter()
          .get( "simple_get", handler)
          .get("simple_get_again", handler);

        HTTPResponse response = router.process(request);

        assertArrayEquals(new HTTPResponse(StatusCodes.OK).serialize(), response.serialize());

    }
}