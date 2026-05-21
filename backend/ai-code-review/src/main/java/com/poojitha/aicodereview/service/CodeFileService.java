package com.poojitha.aicodereview.service;

import com.poojitha.aicodereview.model.CodeFile;
import com.poojitha.aicodereview.repository.CodeFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeFileService {

    @Autowired
    private CodeFileRepository codeFileRepository;

    public CodeFile saveCodeFile(CodeFile codeFile) {

        String review = analyzeCode(codeFile.getContent());

        codeFile.setReviewComment(review);

        return codeFileRepository.save(codeFile);
    }

    private String analyzeCode(String content) {

        if(content.contains("System.out.println")) {
            return "Avoid excessive console printing in production code.";
        }

        if(content.contains("==")) {
            return "Check usage of == operator carefully.";
        }

        return "Code looks good.";
    }
}
