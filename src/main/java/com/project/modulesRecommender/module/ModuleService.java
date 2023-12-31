package com.project.modulesRecommender.module;

import com.project.modulesRecommender.exceptions.CustomErrorException;
import com.project.modulesRecommender.module.models.Module;
import com.project.modulesRecommender.module.models.moduleReadOnlyInterface;
import com.project.modulesRecommender.module.models.moduleReadOnlyInterface.ModuleRead;
import com.project.modulesRecommender.repositories.ModuleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ModuleService {
    private final ModuleRepository moduleRepository;
    private final moduleReadOnlyInterface moduleReadOnlyInterface;

    public ModuleService(ModuleRepository moduleRepository, moduleReadOnlyInterface moduleReadOnlyInterface) {
        this.moduleRepository = moduleRepository;
        this.moduleReadOnlyInterface = moduleReadOnlyInterface;
    }

    public com.project.modulesRecommender.module.models.ModuleRead retrieveModule(String courseCode) {
        boolean moduleExists = moduleRepository.existsById(courseCode);

        if (!moduleExists) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Course code " + courseCode + " is invalid!");
        }

        return moduleReadOnlyInterface.retrieveModule(courseCode);
    }

    public List<Module> retrieveModules(List<String> courseCodes) {
        List<Module> modulesRetrieved = moduleRepository.findAllById(courseCodes);

        if (modulesRetrieved.size() != courseCodes.size()) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Some of the course codes entered are invalid!");
        }

        return modulesRetrieved;
    }

    public Collection<String> retrieveAllModuleCourseCodes() {
        return moduleReadOnlyInterface.retrieveAllModuleCourseCodes();
    }

    public Collection<String> retrieveAllFaculties() {
        return moduleReadOnlyInterface.retrieveAllFaculties();
    }

    public Collection<com.project.modulesRecommender.module.models.ModuleRead> retrieveAllModules(int skip, int limit) {
        return moduleReadOnlyInterface.retrieveAllModules(skip, limit);
    }

    public Collection<com.project.modulesRecommender.module.models.ModuleRead> searchModules(String searchTerm, Integer skip, Integer limit) {
        return moduleReadOnlyInterface.searchForModules(searchTerm, skip == null ? 0 : skip, limit);
    }

    public List<com.project.modulesRecommender.module.models.moduleReadOnlyInterface.ModuleCourseCodeAndName> retrieveAllModulesForAFaculty(String faculty) {
        return moduleReadOnlyInterface.retrieveAllModulesForAFaculty(faculty);
    }

    public Long getTotalNumOfModules() {
        return moduleRepository.count();
    }
}
