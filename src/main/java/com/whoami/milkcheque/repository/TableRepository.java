package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.model.CustomerModel;
import com.whoami.milkcheque.model.TableModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<TableModel, Long> {
}
