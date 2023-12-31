package com.project.modulesRecommender.module;

import com.project.modulesRecommender.errors.HttpResponse;
import com.project.modulesRecommender.module.models.Module;
import com.project.modulesRecommender.module.models.moduleReadOnlyInterface.ModuleRead;
import com.project.modulesRecommender.repositories.ModuleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/modules")
public class ModuleController {
    private final ModuleRepository moduleRepository;
    private final ModuleService moduleService;

    public ModuleController(ModuleRepository moduleRepository, ModuleService moduleService) {
        this.moduleRepository = moduleRepository;
        this.moduleService = moduleService;
    }

    /**
     * GET request to retrieve a single module based on its course code
     * @param courseCode the course code to retrieve
     * @return the response object containing the status code and retrieved module object
     * @since 1.0
     */
    @GetMapping("/{courseCode}")
    ResponseEntity<HttpResponse> byCourseCode(@PathVariable String courseCode) {
        com.project.modulesRecommender.module.models.ModuleRead module = moduleService.retrieveModule(courseCode);

        return new ResponseEntity<>(
                new HttpResponse(
                        HttpStatus.OK,
                        "Module with course code " + courseCode + " is retrieved.",
                        module
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/courseCodes")
    ResponseEntity<HttpResponse> retrieveAllModuleCourseCodes() {

        var moduleCourseCodes = moduleService.retrieveAllModuleCourseCodes();

        return new ResponseEntity<>(
                new HttpResponse(
                        HttpStatus.OK,
                        "All modules' course codes retrieved.",
                        moduleCourseCodes
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/{skip}/{limit}")
    ResponseEntity<HttpResponse> retrieveAllModules(@PathVariable Integer skip, @PathVariable Integer limit) {
        Collection<com.project.modulesRecommender.module.models.ModuleRead> allModulesPaginated = moduleService.retrieveAllModules(skip, limit);

        return new ResponseEntity<>(
                new HttpResponse(
                        HttpStatus.OK,
                        "Skipped " + skip + " modules, retrieved " + limit + " modules.",
                        allModulesPaginated,
                        allModulesPaginated.size()
                ),
                HttpStatus.OK
        );
    }

    /**
     * POST request to retrieve a list of modules based on their course codes
     * @param courseCodes the list of course codes to retrieve
     * @return the response object containing the status code and retrieved module objects
     * @since 1.0
     */
    @PostMapping()
    ResponseEntity<HttpResponse> retrieveModules(@RequestBody List<String> courseCodes) {
        List<Module> modulesRetrieved = moduleService.retrieveModules(courseCodes);

        return new ResponseEntity<>(
                new HttpResponse(
                        HttpStatus.OK,
                        "All selected modules retrieved.",
                        modulesRetrieved
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/search/{searchTerm}/{skip}/{limit}")
    ResponseEntity<HttpResponse> searchForModules(@PathVariable String searchTerm, @PathVariable Integer skip, @PathVariable Integer limit) {
        Collection<com.project.modulesRecommender.module.models.ModuleRead> modulesRetrieved = moduleService.searchModules(searchTerm, skip, limit);

        return new ResponseEntity<>(
                new HttpResponse(
                        HttpStatus.OK,
                        "Returning search results",
                        modulesRetrieved
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/faculties")
    ResponseEntity<HttpResponse> retrieveAllFaculties() {
       var faculties = moduleService.retrieveAllFaculties();

        return new ResponseEntity<>(
                new HttpResponse(
                        HttpStatus.OK,
                        "Retrieved all faculties",
                        faculties
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/faculty/{faculty}")
    ResponseEntity<HttpResponse> retrieveAllModulesForAFaculty(@PathVariable String faculty) {
        var modules = moduleService.retrieveAllModulesForAFaculty(faculty);

        return new ResponseEntity<>(
                new HttpResponse(
                        HttpStatus.OK,
                        "Returning all modules for " + faculty + ".",
                        modules
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/numberOfModules")
    ResponseEntity<HttpResponse> getTotalNumOfModules() {
        var totalNumOfModules = moduleService.getTotalNumOfModules();

        return new ResponseEntity<>(
                new HttpResponse(
                        HttpStatus.OK,
                        "Found " + totalNumOfModules + " modules",
                        totalNumOfModules
                ),
                HttpStatus.OK
        );
    }
}