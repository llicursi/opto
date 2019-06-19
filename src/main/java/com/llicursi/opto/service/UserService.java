package com.llicursi.opto.service;

import com.llicursi.opto.domainobject.UserDO;
import com.llicursi.opto.exception.ResultNotFoundException;

import java.util.List;

public interface UserService {

    List<UserDO> findAll();

    UserDO findById(Long id) throws ResultNotFoundException;

    UserDO create(UserDO user);

    void update(UserDO user, Long userId) throws ResultNotFoundException;

    void delete(Long id) throws ResultNotFoundException;


}
