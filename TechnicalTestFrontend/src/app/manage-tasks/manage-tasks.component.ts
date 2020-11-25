import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Task } from '../__model/task';
import { TaskServiceService } from '../__shared/task-service.service';
import Swal from 'sweetalert2';
import { MatDialog } from '@angular/material/dialog';
import { CreateEditTaskComponent } from './create-edit-task/create-edit-task.component';

export interface UserData {
	id: string;
	name: string;
	progress: string;
	color: string;
}


@Component({
	selector: 'app-manage-tasks',
	templateUrl: './manage-tasks.component.html',
	styleUrls: ['./manage-tasks.component.css']
})
export class ManageTasksComponent implements OnInit {

	/** Parámetros del componente */
	@ViewChild(MatPaginator) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;

	/** Variables del componetne */
	public displayedColumns: string[] = ['id', 'description', 'creationDate', 'valid', 'options'];
	public dataSource: MatTableDataSource<Task>;
	public tasks: Array<Task>;

	/**
	 * Constructor del componente
	 * @param taskService 
	 * @param dialog
	 */
	constructor(private taskService: TaskServiceService,
		private dialog: MatDialog) {
	}

	ngOnInit(): void {
		this.taskService.findAllTasks()
			.subscribe(data => {
				let dataString = JSON.stringify(data);
				let dataJson = JSON.parse(dataString);
				this.tasks = dataJson;
				this.dataSource = new MatTableDataSource(this.tasks);
				this.dataSource.paginator = this.paginator;
				this.dataSource.sort = this.sort;
			}, err => console.error(err)
			);
	}

	/** Permite agregar una tarea */
	public displayAddTask(idTask: number) {
		this.dialog.open(
			CreateEditTaskComponent,
			{
				disableClose: true,
				height: "auto",
				width: "40%",
				data: {
					idTask: idTask
				}
			}
		);
	}

	/* Permite realizar la eliminación de una tarea */
	public removeTask(idTask: number): void {
		Swal.fire({
			title: 'Confirmación',
			text: '¿Estás seguro de querer eliminar la tarea?',
			showCloseButton: true,
			allowOutsideClick: false,
			showConfirmButton: true,
			confirmButtonText: 'Confirmar',
			confirmButtonColor: '#69F0AE',
			cancelButtonText: 'Cancelar',
			showCancelButton: true,
			icon: 'question'
		}).then(r => {
			if (r.isConfirmed) {
				this.taskService.removeTask(idTask)
					.subscribe(data => {
						console.log(data);
						if (data) {
							window.location.reload();
						}
					});
			}
		});
	}

	public applyFilter(event: Event) {
		const filterValue = (event.target as HTMLInputElement).value;
		this.dataSource.filter = filterValue.trim().toLowerCase();

		if (this.dataSource.paginator) {
			this.dataSource.paginator.firstPage();
		}
	}
}
