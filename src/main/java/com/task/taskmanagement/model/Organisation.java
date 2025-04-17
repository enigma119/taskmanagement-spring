package com.task.taskmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.task.taskmanagement.service.ToolService;
import com.task.taskmanagement.service.UserService;
import com.task.taskmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import com.task.taskmanagement.dto.response.ToolResponse;
import com.task.taskmanagement.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "organisations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organisation {
    @Id
    private String id;
    
    private String name;
    
    @Field("user_ids")
    @Builder.Default
    private List<String> userIds = new ArrayList<>();
    
    @Field("tool_ids")
    @Builder.Default
    private List<String> toolIds = new ArrayList<>();
    
    @Field("task_ids")
    @Builder.Default
    private List<String> taskIds = new ArrayList<>();

    @Autowired
    private transient MemberRepository memberRepository;
    
    @Autowired
    private transient ToolService toolService;
    
    @Autowired
    private transient TaskService taskService;

    public void addUserId(String userId) {
        userIds.add(userId);
    }
    
    public void addToolId(String toolId) {
        toolIds.add(toolId);
    }

    public void addTaskId(String taskId) {
        taskIds.add(taskId);
    }

    public int getMemberCount() {
        return memberRepository.findByOrganisationId(this.id).size();
    }
    
    public int getTotalScore() {
        return memberRepository.findByOrganisationId(this.id).stream()
                .mapToInt(Member::getScore)
                .sum();
    }
    
    public int getCompletedTaskCount() {
        return (int) taskService.getTasksByOrganisationId(this.id).stream()
                .filter(task -> task.getStatus() == com.task.taskmanagement.model.enums.TaskStatus.DONE)
                .count();
    }

    public List<ToolResponse> getAvailableTools() {
        return toolService.getAvailableTools(this.id);
    }
}