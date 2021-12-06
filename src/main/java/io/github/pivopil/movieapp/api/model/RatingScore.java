package io.github.pivopil.movieapp.api.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class RatingScore {

  @NotNull
  @Min(0)
  @Max(99)
  Integer score;
}
