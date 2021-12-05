package io.github.pivopil.movieapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "rating")
@IdClass(RatingId.class)
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "movie_id")
  private Movie movie;

  @Id private String userId;

  private Integer score;
}
