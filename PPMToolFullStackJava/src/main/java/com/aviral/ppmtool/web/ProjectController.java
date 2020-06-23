package com.aviral.ppmtool.web;

import com.aviral.ppmtool.domain.Project;
import com.aviral.ppmtool.services.MapValidationErrorService;
import com.aviral.ppmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result){

        ResponseEntity<?> errorMap= mapValidationErrorService.MapValidationService((result));

        if(errorMap!=null)  return errorMap;

        Project project1 = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId){

        Project project = projectService.findProjectByIdentifier(projectId);

        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(){
        return projectService.findAllProjects();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId){
        projectService.deleteProjectById(projectId.toUpperCase());
        return new ResponseEntity<String>("Project with ID: '"+projectId.toUpperCase()+"' was deleted", HttpStatus.OK);
    }
}
