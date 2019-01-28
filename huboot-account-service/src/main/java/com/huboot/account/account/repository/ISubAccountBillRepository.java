package com.huboot.account.account.repository;

import com.huboot.commons.jpa.IBaseRepository;
import com.huboot.share.account_service.enums.BillTypeEnum;
import com.huboot.share.account_service.enums.SubAccountStatusEnum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.huboot.account.account.entity.SubAccountBillEntity;

import java.math.BigDecimal;

/**
*Repository
*/
@Repository("subAccountBillRepository")
public interface ISubAccountBillRepository extends IBaseRepository<SubAccountBillEntity> {

    @Query("select sum(bill.amount) as amount from SubAccountBillEntity bill where bill.subAccountId = :subAccountId and bill.status = :status and bill.sign = :sign")
    BigDecimal getTotalAmount(@Param("subAccountId")Long subAccountId, @Param("status")SubAccountStatusEnum status, @Param("sign")String sign);

    SubAccountBillEntity findOneByOrderIdAndType(Long orderId, BillTypeEnum type);


}
