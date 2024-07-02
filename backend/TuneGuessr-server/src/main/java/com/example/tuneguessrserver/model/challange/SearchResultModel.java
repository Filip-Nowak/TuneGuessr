package com.example.tuneguessrserver.model.challange;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchResultModel {
    private String id;
    private String name;
    private String description;
    private String author;
}
