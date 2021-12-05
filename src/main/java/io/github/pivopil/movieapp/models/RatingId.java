package io.github.pivopil.movieapp.models;

import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
public class RatingId implements Serializable {
  private String userId;
  private Long movie;
}
