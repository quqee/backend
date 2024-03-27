package org.hits.backend.hackaton.public_interface.common;

import lombok.Value;

import java.util.List;

@Value
public class PageResponse<T> {
    List<T> content;
    Metadata metadata;

    @Value
    public static class Metadata {
        int page;
        int size;
        long totalElements;
    }
}
