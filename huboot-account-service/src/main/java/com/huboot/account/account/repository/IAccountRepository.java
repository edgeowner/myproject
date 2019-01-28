package com.huboot.account.account.repository;

import com.huboot.account.account.entity.AccountEntity;
import com.huboot.commons.jpa.IBaseRepository;
import com.huboot.share.account_service.enums.AccountTypeEnum;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*主账户Repository
*/
@Repository("accountRepository")
public interface IAccountRepository extends IBaseRepository<AccountEntity> {

    List<AccountEntity> findByType(AccountTypeEnum type);

    AccountEntity findByRelaId(String relaId);

}
