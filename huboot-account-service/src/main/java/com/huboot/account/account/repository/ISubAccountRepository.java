package com.huboot.account.account.repository;

import com.huboot.commons.jpa.IBaseRepository;
import com.huboot.share.account_service.enums.SubAccountTypeEnum;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.huboot.account.account.entity.SubAccountEntity;

import java.math.BigDecimal;

/**
*子账户Repository
*/
@Repository("subAccountRepository")
public interface ISubAccountRepository extends IBaseRepository<SubAccountEntity> {

    SubAccountEntity findByAccountIdAndType(Long accountId, SubAccountTypeEnum type);

    /**
     * 可用金额
     * **/
    //并发情况下依然可以正常更新
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update SubAccountEntity sub set sub.totalBalance = sub.totalBalance + :amount, sub.usableBalance = sub.usableBalance + :amount where sub.id = :id")
    int addUsableBalance(@Param("id")Long id, @Param("amount")BigDecimal amount);

    /**
     * 不可用金额
     * **/
    //并发情况下依然可以正常更新
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update SubAccountEntity sub set sub.totalBalance = sub.totalBalance + :amount, sub.unusableBalance = sub.unusableBalance + :amount where sub.id = :id")
    int addUnusableBalance(@Param("id")Long id, @Param("amount")BigDecimal amount);

    /**
     * 可用金额
     * **/
    //保证扣款扣不成负的
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update SubAccountEntity sub set sub.totalBalance = sub.totalBalance - :amount, sub.usableBalance = sub.usableBalance - :amount where sub.id = :id and sub.usableBalance >= :amount")
    int reduceUsableBalance(@Param("id")Long id, @Param("amount")BigDecimal amount);

    /**
     * 不可用金额
     * **/
    //保证扣款扣不成负的
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update SubAccountEntity sub set sub.totalBalance = sub.totalBalance - :amount, sub.unusableBalance = sub.unusableBalance - :amount where sub.id = :id and sub.unusableBalance >= :amount")
    int reduceUnusableBalance(@Param("id")Long id, @Param("amount")BigDecimal amount);

}
