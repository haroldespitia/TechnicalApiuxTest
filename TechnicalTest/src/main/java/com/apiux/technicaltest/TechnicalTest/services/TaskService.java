package com.apiux.technicaltest.TechnicalTest.services;

import com.apiux.technicaltest.TechnicalTest.model.dao.ITaskRepositoryDao;
import com.apiux.technicaltest.TechnicalTest.model.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para procesar las peticiones.
 *
 * @author Harold
 */
@Service
public class TaskService {

    @Autowired
    private ITaskRepositoryDao iTaskRepositoryDao;

    /**
     * Permite crear o editar una tarea. La creación o edición del registro se realizará dependiendo de si el
     * campo llave (para este caso id), es recibido dentro de la petición.
     *
     * @param task
     * @return Task
     */
    public Task createEditTask(Task task) {
        task.setCreationDate(new Date());
        return this.iTaskRepositoryDao.save(task);
    }

    /**
     * Permite encontrar una tarea por identificación
     *
     * @param id
     * @return Task
     */
    public Task findById(Integer idTask) {
        Optional<Task> optionalTask = this.iTaskRepositoryDao.findById(idTask);
        return optionalTask.orElse(null);
    }

    /**
     * Método para retornar todas las tareas
     *
     * @return listado de tareas
     */
    public List<Task> findAllTasks() {
        return this.iTaskRepositoryDao.findAll();
    }

    /**
     * Método de prueba, que retorna la versión del servicio
     *
     * @return versión del servicio
     */
    public Boolean deleteTask(Integer idTask) {
        try {
            this.iTaskRepositoryDao.deleteById(idTask);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }
}
