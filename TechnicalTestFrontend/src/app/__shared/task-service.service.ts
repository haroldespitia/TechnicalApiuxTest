import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { retry, catchError, map } from 'rxjs/operators';
import { Task } from '../__model/task';
import { Observable, throwError } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class TaskServiceService {

	/** Header utilizado para todas las peticiones de esta página */
	public header = {
		headers: new HttpHeaders({
			'Content-Type': 'application/json',
			'Access-Control-Allow-Origin': '*'
		})
	};

	/**
	 * Concstructor del servicio
	 * @param http 
	 */
	constructor(private http: HttpClient) {
	}

	/** Retorna un listado de las tareas almacenadas en la base de datos */
	public findAllTasks() {
		let apiUrl = environment.api_backend.url + environment.api_backend.methods.findAll;
		
		return this.http.get<any>(apiUrl, this.header)
			.pipe(retry(1), catchError(this.handleError));
	}

	/** Permite buscar una tarea por el identificador de la misma */
	public findTaskById(idTask: number): Observable<Task> {
		let apiUrl = environment.api_backend.url + environment.api_backend.methods.findById + "/" + idTask;

		return this.http.get<Task>(apiUrl)
			.pipe(retry(1), catchError(this.handleError));
	}

	/** Permite guardar o editar una tarea según la información enviada al backend */
	public saveEditTask(task: Task): Observable<Task> {
		let apiUrl = environment.api_backend.url + environment.api_backend.methods.saveEdit;

		return this.http.post<Task>(apiUrl, JSON.stringify(task), this.header)
			.pipe(retry(1), catchError(this.handleError));
	}

	/** Permite eliminar una tarea de la base de datos */
	public removeTask(idTask: number): Observable<boolean> {
		let apiURL = environment.api_backend.url + environment.api_backend.methods.deleteTask + "/" + idTask;

		return this.http.delete<boolean>(apiURL)
			.pipe(retry(1), catchError(this.handleError));
	}

	/** Manejo de errores */
	private handleError(error: any) {
		let errorMessage = '';
		if (error.error instanceof ErrorEvent) {
			errorMessage = error.error.message;
		} else {
			errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
		}
		console.log(errorMessage);
		return throwError(errorMessage);
	}
}
