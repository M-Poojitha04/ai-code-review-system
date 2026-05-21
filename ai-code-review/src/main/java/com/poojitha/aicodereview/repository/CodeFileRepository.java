package com.poojitha.aicodereview.repository;

import com.poojitha.aicodereview.model.CodeFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeFileRepository extends JpaRepository<CodeFile, Long> {

}
