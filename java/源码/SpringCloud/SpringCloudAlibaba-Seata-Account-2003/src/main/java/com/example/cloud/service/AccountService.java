package com.example.cloud.service;

public interface AccountService {
    /**
     * 扣减账户余额
     * @param userId 用户id
     * @param money 本次消费金额
     */
    void decrease( Long userId, Long money);
}
