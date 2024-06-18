package com.example.cloud.service;

import com.example.cloud.entities.Pay;

import java.util.List;

public interface PayService {
    int add(Pay pay);
    int update(Pay PayDTO);
    int delete(Integer id);
    Pay getById(Integer id);
    List<Pay> getAll();
}
