package com.example.cicdtutorialyoutube.controller

import com.example.cicdtutorialyoutube.data.model.TaskCreateRequest
import com.example.cicdtutorialyoutube.data.model.TaskDto
import com.example.cicdtutorialyoutube.data.model.TaskUpdateRequest
import com.example.cicdtutorialyoutube.service.TaskService
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
@RequestMapping("api/v1/tasks")
class TaskController(private val service: TaskService) {

    @GetMapping
    fun getAllTasks(): ResponseEntity<List<TaskDto>> = ResponseEntity.ok(service.getAllTasks())

    @GetMapping("open")
    fun getAllOpenTasks(): ResponseEntity<List<TaskDto>> = ResponseEntity.ok(service.getAllOpenTasks())

    @GetMapping("closed")
    fun getAllClosedTasks(): ResponseEntity<List<TaskDto>> = ResponseEntity.ok(service.getAllClosedTasks())

    @GetMapping("{id}")
    fun getTaskById(@PathVariable id: Long): ResponseEntity<TaskDto> =
        ResponseEntity.ok(service.getTaskById(id))

    @PostMapping
    fun createTask(
        @Valid @RequestBody
        createRequest: TaskCreateRequest
    ): ResponseEntity<TaskDto> = ResponseEntity(service.createTask(createRequest), HttpStatus.CREATED)

    @PatchMapping("{id}")
    fun updateTask(
        @PathVariable id: Long,
        @Valid @RequestBody
        updateRequest: TaskUpdateRequest
    ): ResponseEntity<TaskDto> = ResponseEntity.ok(service.updateTask(id, updateRequest))

    @DeleteMapping("{id}")
    fun deleteTask(@PathVariable id: Long): ResponseEntity<Unit> {
        val headerValue: String = service.deleteTask(id)
        val httpHeaders = HttpHeaders()
        httpHeaders.add("delete-task-header", headerValue)
        return ResponseEntity(null, httpHeaders, HttpStatus.NO_CONTENT)
    }
}
