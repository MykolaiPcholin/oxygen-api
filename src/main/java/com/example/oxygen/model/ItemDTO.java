package com.example.oxygen.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class ItemDTO {
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String size;
    @NotNull
    @NotEmpty
    private float weight;
    @NotNull
    @NotEmpty
    private int year;
    @NotNull
    @NotEmpty
    private String description;
    @NotNull
    @NotEmpty
    private float price;
    @NotNull
    @NotEmpty
    private String photourl;
}
