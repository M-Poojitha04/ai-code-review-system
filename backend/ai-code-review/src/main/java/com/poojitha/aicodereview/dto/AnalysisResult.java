package com.poojitha.aicodereview.dto;

public class AnalysisResult {

    private int score;

    private String review;

    private String complexity;

    private int warnings;

    public AnalysisResult() {
    }

    public AnalysisResult(int score, String review,
                          String complexity, int warnings) {

        this.score = score;
        this.review = review;
        this.complexity = complexity;
        this.warnings = warnings;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getComplexity() {
        return complexity;
    }

    public void setComplexity(String complexity) {
        this.complexity = complexity;
    }

    public int getWarnings() {
        return warnings;
    }

    public void setWarnings(int warnings) {
        this.warnings = warnings;
    }
}
