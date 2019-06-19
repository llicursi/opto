package com.llicursi.opto.dataaccessobject;

import com.llicursi.opto.domainobject.UserDO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserDO, Long> {

    Optional<UserDO> findUserByEmail(String email);
}
