package com.getyourdoc.getyourdoctors.repositories;

import com.getyourdoc.getyourdoctors.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
