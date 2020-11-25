package com.apiux.technicaltest.TechnicalTest;

import com.apiux.technicaltest.TechnicalTest.model.dao.ITaskRepositoryDao;
import com.apiux.technicaltest.TechnicalTest.model.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase de arranque de la aplicación
 *
 * @author Harold
 */
@SpringBootApplication
@EntityScan(basePackages = "com.apiux.technicaltest.TechnicalTest.model.entities")
public class TechnicalTestApplication {

    @Autowired
    private ITaskRepositoryDao iTaskRepositoryDao;

    public static void main(String[] args) {
        SpringApplication.run(TechnicalTestApplication.class, args);
    }

    @Bean
    public void loadDataBase() {
        Task taskA = new Task();
        taskA.setDescription("Reunión preliminar");
        taskA.setValid(Boolean.TRUE);

        Task taskB = new Task();
        taskB.setDescription("Acta de Kick-Off");
        taskB.setValid(Boolean.FALSE);

        Task taskC = new Task();
        taskC.setDescription("Levantamiento");
        taskC.setValid(Boolean.TRUE);

        Task taskD = new Task();
        taskD.setDescription("Desarrollo");
        taskD.setValid(Boolean.FALSE);

        Task taskE = new Task();
        taskE.setDescription("Pruebas Unitarias");
        taskE.setValid(Boolean.TRUE);

        Task taskF = new Task();
        taskF.setDescription("Pruebas Integración");
        taskF.setValid(Boolean.TRUE);

        Task taskG = new Task();
        taskG.setDescription("Desarrollo");
        taskG.setValid(Boolean.FALSE);

        List<Task> taskList = new ArrayList<>();
        taskList.add(taskA);
        taskList.add(taskB);
        taskList.add(taskC);
        taskList.add(taskD);
        taskList.add(taskE);
        taskList.add(taskF);
        taskList.add(taskG);

        this.iTaskRepositoryDao.saveAll(taskList);
    }
}
