import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private apiUrl = 'http://localhost:8080/api/clientes';

  constructor(private http: HttpClient) {}

  login(cpf: string, senha: string): Observable<any> {
    const cpfLimpo = cpf.replace(/[^0-9]/g, '');
    
    return this.http.get<any>(`${this.apiUrl}/cpf/${encodeURIComponent(cpfLimpo)}`).pipe(
      map(response => {
        if (response.success && response.cliente) {
          if (response.cliente.senha === senha) {
            return {
              success: true,
              cliente: response.cliente
            };
          } else {
            return {
              success: false,
              message: 'Senha incorreta'
            };
          }
        } else {
          return {
            success: false,
            message: response.message || 'CPF não encontrado'
          };
        }
      })
    );
  }

  getByCpf(cpf: string): Observable<any> {
    const cpfLimpo = cpf.replace(/[^0-9]/g, '');
    return this.http.get<any>(`${this.apiUrl}/cpf/${encodeURIComponent(cpfLimpo)}`);
  }
}




