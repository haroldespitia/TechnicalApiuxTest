package com.apiux.technicaltest.TechnicalTest.controllers;

import com.apiux.technicaltest.TechnicalTest.model.entities.Task;
import com.apiux.technicaltest.TechnicalTest.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Controlador principal de la aplicación
 *
 * @author Harold
 */
@RestController
@RequestMapping("tasks")
@CrossOrigin
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * Método para retornar todas las tareas
     *
     * @return listado de tareas
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = "application/json")
    public List<Task> findAllTasks() {
        return this.taskService.findAllTasks();
    }

    /**
     * Permite encontrar una tarea por identificación
     *
     * @param id
     * @return Task
     */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET, produces = "application/json")
    public Task findById(@Valid @PathVariable(required = true) Integer id) {
        return this.taskService.findById(id);
    }

    /**
     * Permite crear o editar una tarea. La creación o edición del registro se realizará dependiendo de si el
     * campo llave (para este caso id), es recibido dentro de la petición.
     *
     * @param task
     * @return Task
     */
    @RequestMapping(value = "/saveEdit", method = RequestMethod.POST)
    public Task createEditTask(@Valid @RequestBody Task task) {
        return this.taskService.createEditTask(task);
    }

    /**
     * Permite eliminar una tarea a partir de su identificador
     *
     * @param id
     * @return <code>true</code>, en caso de que se la eliminación fuese exitosa. <code>false,</code> en caso contrario.
     */
    @RequestMapping(value = "/deleteTask/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public Boolean deleteTask(@Valid @PathVariable(required = true) Integer id) {
        return this.taskService.deleteTask(id);
    }

    /**
     * Método de prueba, que retorna la versión del servicio
     *
     * @return versión del servicio
     */
    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public String getVersion() {
        return "v1.00.00";
    }
}
