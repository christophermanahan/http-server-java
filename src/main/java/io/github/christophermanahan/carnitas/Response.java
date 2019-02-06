package io.github.christophermanahan.carnitas;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Response {
    private final Status status;
    private final Set<String> headers;
    private final Optional<String> body;

    static final String VERSION = "HTTP/1.1";

    enum Status {
        OK("200 OK"),
        CREATED("201 Created"),
        NOT_FOUND("404 Not Found"),
        METHOD_NOT_ALLOWED("405 Method Not Allowed");

        final String code;

        Status(String code) {
            this.code = code;
        }
    }

    Response(Status status, Set<String> headers, Optional<String> body) {
        this.status = status;
        this.headers = headers;
        this.body = body;
    }

    Status status() {
        return status;
    }

    public Set<String> headers() {
        return headers;
    }

    public Optional<String> body() {
        return body;
    }

    @Override
    public boolean equals(Object object) {
        Response response = (Response) object;
        return status.equals(response.status)
          && headers.equals(response.headers)
          && body.equals(response.body);
    }

    @Override
    public String toString() {
        return Stream.of(List.of(status.toString()), headers, List.of(body.orElse("")))
          .flatMap(Collection::stream)
          .collect(Collectors.joining(Serializer.CRLF));
    }
}
