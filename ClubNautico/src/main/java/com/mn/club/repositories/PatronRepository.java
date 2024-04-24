package com.mn.club.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mn.club.models.PatronModel;



@Repository
public interface PatronRepository extends JpaRepository<PatronModel, Long> {
	

} 
