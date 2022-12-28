package com.webp.bank.repository;

import com.webp.bank.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {

    @Query("select b from Bank b where id=?1 order by tdate desc")
    List<Bank> findByIdOrderByTdateDesc(String id);

    @Query("select b from Bank b order by id asc, tdate desc")
    List<Bank> findAllOrderByIdTdateDesc();

    @Query("select count(id) from Bank where id=?1")
    int findByIdConnect(String id);
}
