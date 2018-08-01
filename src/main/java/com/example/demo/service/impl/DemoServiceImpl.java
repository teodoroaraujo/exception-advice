package com.example.demo.service.impl;

import com.example.demo.exception.BusinessException;
import com.example.demo.service.DemoService;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {
    @Override public void findTest()  {

        throw new BusinessException();
    }
}
