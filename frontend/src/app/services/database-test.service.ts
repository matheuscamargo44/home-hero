import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DatabaseTestService {
  private apiUrl = `${environment.apiUrl}/database-test`;

  constructor(private http: HttpClient) {}

  getAvailableViews(): Observable<string[]> {
    return this.http.get<any>(`${this.apiUrl}/views`).pipe(
      map(response => response.success ? response.data : [])
    );
  }

  getAvailableProcedures(): Observable<string[]> {
    return this.http.get<any>(`${this.apiUrl}/procedures`).pipe(
      map(response => response.success ? response.data : [])
    );
  }

  getAvailableTriggers(): Observable<string[]> {
    return this.http.get<any>(`${this.apiUrl}/triggers`).pipe(
      map(response => response.success ? response.data : [])
    );
  }

  executeView(viewName: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/view/${viewName}`, {});
  }

  executeProcedure(procedureName: string, parameters?: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/procedure/${procedureName}`, parameters || {});
  }

  getTriggerInfo(triggerName: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/trigger/${triggerName}`);
  }

  executeTriggerAction(triggerName: string, params: any = {}): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/trigger/${triggerName}/execute`, params);
  }
}

