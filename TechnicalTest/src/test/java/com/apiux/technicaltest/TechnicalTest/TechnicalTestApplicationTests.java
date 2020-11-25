package com.apiux.technicaltest.TechnicalTest;

import com.apiux.technicaltest.TechnicalTest.model.entities.Task;
import com.apiux.technicaltest.TechnicalTest.services.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")
@Sql("/dump_test.sql")
class TechnicalTestApplicationTests {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private TaskService taskService;

    @Test
    public void findAllTest() {
        List<Task> tasks = taskService.findAllTasks();
        assertEquals(7, tasks.size());
        assertTrue(tasks.get(0).getValid());
        assertEquals(DATE_FORMAT.format(tasks.get(0).getCreationDate()), "2005-10-15");
        assertEquals(tasks.get(0).getDescription(), "Reunión preliminar");
    }

    @Test
    public void createEditTest() {
        Task taskA = new Task();
        taskA.setDescription("Nueva Tarea");
        taskA.setValid(Boolean.TRUE);

        taskA = taskService.createEditTask(taskA);
        assertTrue(taskA.getValid());
        assertNotNull(taskA.getId());
        assertEquals(taskA.getDescription(), "Nueva Tarea");
    }

}
