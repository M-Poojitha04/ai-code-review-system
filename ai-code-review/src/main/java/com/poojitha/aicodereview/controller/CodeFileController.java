package com.poojitha.aicodereview.controller;

import com.poojitha.aicodereview.dto.AnalysisResult;
import com.poojitha.aicodereview.model.CodeFile;
import com.poojitha.aicodereview.repository.CodeFileRepository;
import com.poojitha.aicodereview.service.OpenAIService;

import org.apache.commons.io.IOUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/code")
public class CodeFileController {

    @Autowired
    private CodeFileRepository codeFileRepository;

    @Autowired
    private OpenAIService openAIService;

    @PostMapping("/upload")
    public Map<String, Object> uploadCode(
            @RequestParam("file") MultipartFile file)
            throws IOException {

        String content = IOUtils.toString(
                file.getInputStream(),
                StandardCharsets.UTF_8
        );

        String fileName = file.getOriginalFilename();

        String language = getLanguageFromFileName(fileName);

        CodeFile codeFile = new CodeFile(
                fileName,
                language,
                content
        );

        AnalysisResult result = openAIService.reviewCode(content);

        codeFile.setReviewComment(result.getReview());

        codeFileRepository.save(codeFile);

        Map<String, Object> response = new HashMap<>();

        response.put("fileName", fileName);

        response.put("language", language);

        response.put("score", result.getScore());

        response.put("complexity", result.getComplexity());

        response.put("warnings", result.getWarnings());

        response.put("review", result.getReview());

        return response;
    }

    private String getLanguageFromFileName(String fileName) {

        if (fileName.endsWith(".java")) {
            return "Java";
        } else if (fileName.endsWith(".py")) {
            return "Python";
        } else if (fileName.endsWith(".js")) {
            return "JavaScript";
        }

        return "Unknown";
    }
}