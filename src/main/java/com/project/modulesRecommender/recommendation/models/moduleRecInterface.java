package com.project.modulesRecommender.recommendation.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

public interface moduleRecInterface {
    @Data
    @Builder
    @AllArgsConstructor
    class Recommendation {
        public final String courseCode;
        public final String courseName;
        public final String courseInformation;
        public final Integer academicUnits;
        public final Double score;
        public final Integer total;
    }

    @Transactional(readOnly = true)
    RecommendationsDTO recommendModules(String studentId);
}
