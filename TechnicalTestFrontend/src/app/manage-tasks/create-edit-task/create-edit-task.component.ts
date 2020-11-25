import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Task } from 'src/app/__model/task';
import { TaskServiceService } from 'src/app/__shared/task-service.service';

@Component({
	selector: 'app-create-edit-task',
	templateUrl: './create-edit-task.component.html',
	styleUrls: ['./create-edit-task.component.css']
})
export class CreateEditTaskComponent implements OnInit {

	/** Variables del componente */
	public modality: string;
	private idTask: number;
	public editableTask: Task;

	public description: FormControl;
	public valid: FormControl;

	/** 
	 * Constructor de la clase 
	 * @param inputParameters
	 * @param taskService
	 * @param _formBuilder
	*/
	constructor(
		@Inject(MAT_DIALOG_DATA) public inputParameters: any,
		private taskService: TaskServiceService) {
		this.idTask = this.inputParameters.idTask;
		this.modality = this.idTask === -1 ? 'Crear' : 'Editar';

		if (this.idTask !== -1) {
			this.loadTaskInformation();
		} else {
			this.description = new FormControl('', [Validators.required]);
			this.valid = new FormControl(false, [Validators.required]);
		}
	}

	ngOnInit(): void {
	}

	/** Permite guardar una tarea en la base de datos */
	public saveTask(): void {
		if (this.description.valid) {
			let task: Task = {
				id: this.idTask === -1 ? null : this.idTask,
				description: this.description.value,
				creationDate: null,
				valid: this.valid.value ? this.valid.value : false
			}

			this.taskService.saveEditTask(task)
				.subscribe(response => {
					if (response) {
						window.location.reload();
					}
				})
		}
	}

	/** Carga la información de la tarea que se desea editar */
	private loadTaskInformation(): void {
		this.taskService.findTaskById(this.idTask)
			.subscribe(response => {
				this.editableTask = response;
				this.description = new FormControl(this.editableTask.description, [Validators.required]);
				this.valid = new FormControl(this.editableTask.valid, [Validators.required]);
			});
	}
}
