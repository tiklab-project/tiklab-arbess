package io.thoughtware.arbess.task.task.service;


public interface TasksCloneService {


    void clonePostTasks(String id ,String cloneId);

    void clonePipelineTasks(String id ,String cloneId);

    void cloneStageTasks(String id ,String cloneId);



}
