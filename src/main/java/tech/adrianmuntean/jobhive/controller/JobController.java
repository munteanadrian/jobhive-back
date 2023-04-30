package tech.adrianmuntean.jobhive.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.adrianmuntean.jobhive.DTO.JobDTO;
import tech.adrianmuntean.jobhive.model.Job;
import tech.adrianmuntean.jobhive.service.JobService;

import java.util.List;

/**
 * <b><h1>Controller for the Job entity</h1></b>
 * <table>
 *  <tr>
 *      <th>CREATE</th>
 *      <th>READ</th>
 *      <th>UPDATE</th>
 *      <th>DELETE</th>
 *  </tr>
 *  <tr>
 *      <td>POST /jobs - create a new job</td>
 *      <td>GET /jobs - get all jobs</td>
 *      <td>PUT /jobs/{id} - update a job</td>
 *      <td>DELETE /jobs/{id} - delete a job</td>
 *  </tr>
 *  <tr>
 *      <td>POST /jobs/{id}/task - create a new task for a job</td>
 *      <td>GET /jobs/{id} - get a job by id</td>
 *      <td>PUT /jobs/{id}/task/{id} - update a task for a job</td>
 *      <td>DELETE /jobs/{id}/task/{id} - delete a task from a job</td>
 *   </tr>
 *   <tr>
 *      <td>POST /jobs/{id}/action - create a new action for a job</td>
 *      <td>GET /jobs/{id}/tasks - get all tasks for a job</td>
 *      <td>PUT /jobs/{id}/action/{id} - update an action for a job</td>
 *      <td>DELETE /jobs/{id}/action/{id} - delete an action from a job</td>
 *   </tr>
 *   <tr>
 *      <td></td>
 *      <td>GET /jobs/{id}/actions - get all actions for a job</td>
 *      <td></td>
 *      <td></td>
 *   </tr>
 *  </table>
 */

@CrossOrigin
@RequestMapping("/jobs")
@RestController
public class JobController {
    private final JobService jobService;

    public JobController(@Autowired JobService jobService) {
        this.jobService = jobService;
    }

    //region CREATE OPERATIONS

    /**
     * Create a new job
     *
     * @param jobDTO json object with the job details - company, applied(bool) city, country, remote (bool)
     */
    @PostMapping("/")
    public ResponseEntity<Job> create(@RequestBody JobDTO jobDTO) {
        return ResponseEntity.ok(jobService.createJob(jobDTO));
    }

    //endregion

    //region READ OPERATIONS

    /**
     * Get all jobs
     */
    @GetMapping("/")
    public ResponseEntity<List<Job>> getJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    /**
     * Get a job by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJob(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    //endregion

    //region UPDATE OPERATIONS

    /**
     * Update a job
     *
     * @param id       id of the job to update
     * @param toUpdate json object with the key and value to update
     * @return the updated job
     */
    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody String toUpdate) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(toUpdate);
            return ResponseEntity.ok(jobService.updateJob(id, jsonNode.get("key").asText(), jsonNode.get("value").asText()));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid request body");
        }
    }

    //endregion

    //region DELETE OPERATIONS

    /**
     * Delete a job
     *
     * @param id id of the job to delete
     */
    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
    }

    //endregion
}
