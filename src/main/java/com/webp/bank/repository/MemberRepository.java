package com.webp.bank.repository;

import com.webp.bank.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    @Transactional
    @Modifying
    @Query("update Member set pw=?2, name=?3, phone=?4 where id=?1")
    int updateMyinfo(String id, String pw, String name, String phone);

    @Transactional
    @Query("select count(id) from Member")
    int countMember();

    @Transactional
//    @Query(value="select Member from Member order by Member.rdate desc", nativeQuery = true)
    @Query("select i from Member i order by rdate desc")
//    @Query("select Member from Member order by Member.rdate desc")
    List<Member> findAll();
//    List<Member> findAllOrderByIdDesc();

    @Query("select balance from Member where id=?1")
    Integer findBalance(String id);

    @Modifying
    @Query("update Member set balance=balance+?2 where id=?1")
    int updateBalance(String id, int money);

    @Query("select count(id) from Member")
    int findCount(); /* 로그인화면에서 사용했던 총회원수 메소드 */

    @Query("select sum(balance) from Member")
    int findSum(); /* 총잔액 */

    @Query("select i from Member i where i.balance>50000000")
    List<Member> findVipList();
}
