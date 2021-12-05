package io.github.pivopil.movieapp.repository;

import io.github.pivopil.movieapp.models.Rating;
import io.github.pivopil.movieapp.models.RatingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, RatingId> {}
