import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PrestadorService {
  private apiUrl = `${environment.apiUrl}/prestadores`;

  constructor(private http: HttpClient) {}

  cadastrar(dados: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/cadastro`, dados);
  }

  getByCpf(cpf: string): Observable<any> {
    const cpfLimpo = cpf.replace(/[^0-9]/g, '');
    return this.http.get<any>(`${this.apiUrl}/cpf/${encodeURIComponent(cpfLimpo)}`);
  }
}


