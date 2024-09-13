package com.todoapi.task.mapper;

import com.todoapi.task.entity.Task;
import com.todoapi.task.model.TaskRecord;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);
    TaskRecord toTaskRecord(Task task);
    Task toTask(TaskRecord taskRecord);
}
