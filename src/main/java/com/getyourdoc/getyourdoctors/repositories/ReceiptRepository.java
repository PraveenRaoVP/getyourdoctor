package com.getyourdoc.getyourdoctors.repositories;

import com.getyourdoc.getyourdoctors.models.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
}
