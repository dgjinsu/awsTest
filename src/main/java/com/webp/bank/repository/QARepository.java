package com.webp.bank.repository;

import com.webp.bank.entity.QA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface QARepository extends JpaRepository<QA, Integer> {
    @Transactional
    @Modifying
    @Query("update QA set adate=now(), answer=?2 where no=?1")
    void updateAnswer(int no, String answer);

    @Query("select count(id) from QA where id=?1")
    int countQA(String id);
}
