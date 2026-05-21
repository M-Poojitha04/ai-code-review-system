package com.poojitha.aicodereview.service;

import com.poojitha.aicodereview.dto.AnalysisResult;
import org.springframework.stereotype.Service;

@Service
public class OpenAIService {

    public AnalysisResult reviewCode(String codeContent) {

        StringBuilder review = new StringBuilder();

        int warnings = 0;

        int score = 100;

        String complexity = "Low";

        // Console printing detection
        if (codeContent.contains("System.out.println")) {

            review.append("⚠ Avoid excessive console printing.\n");

            warnings++;

            score -= 10;
        }

        // Nested loop detection
        int forCount = countOccurrences(codeContent, "for");

        if (forCount >= 2) {

            review.append("⚠ Nested loops detected. Time complexity may be high.\n");

            warnings++;

            score -= 20;

            complexity = "High";
        }

        // Long file detection
        int lines = codeContent.split("\n").length;

        if (lines > 50) {

            review.append("⚠ Large file detected. Consider splitting logic.\n");

            warnings++;

            score -= 10;
        }

        // Missing comments
        if (!codeContent.contains("//")) {

            review.append("⚠ No comments found. Add documentation.\n");

            warnings++;

            score -= 5;
        }

        // Complexity adjustment
        if (forCount == 1) {
            complexity = "Medium";
        }

        // Default response
        if (warnings == 0) {

            review.append("✅ Code looks clean and well structured.");
        }

        return new AnalysisResult(
                score,
                review.toString(),
                complexity,
                warnings
        );
    }

    private int countOccurrences(String text, String keyword) {

        int count = 0;

        int index = 0;

        while ((index = text.indexOf(keyword, index)) != -1) {

            count++;

            index += keyword.length();
        }

        return count;
    }
}
