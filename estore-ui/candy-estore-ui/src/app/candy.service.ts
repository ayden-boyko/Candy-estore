import { Injectable } from '@angular/core';
import { Candy } from './candy';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})

export class CandyService {

  private candiesUrl = 'http://localhost:8080/candies';  // URL to web api
  candies!: Candy[]

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient
  ) {}
  
  

  
  getCandies(): Observable<Candy[]> {
    return this.http.get<Candy[]>(this.candiesUrl)
      .pipe(catchError(this.handleError<Candy[]>('getCandies', [])))
  }

  getCandy(id: number): Observable<Candy>{
    const url = `${this.candiesUrl}/${id}`;
    return this.http.get<Candy>(url).pipe(catchError(this.handleError<Candy>(`getCandy id=${id}`)));
  }

  /** PUT: update the candy on the server */
  updateCandy(candy: Candy): Observable<any> {
    return this.http.put(this.candiesUrl, candy, this.httpOptions).pipe(catchError(this.handleError<any>('updateCandy')));
  }

  /** POST: add a new candy to the server */
  addCandy(candy: Candy): Observable<Candy> {
    return this.http.post<Candy>(this.candiesUrl, candy, this.httpOptions).pipe(catchError(this.handleError<Candy>('addCandy')));
  }

  /** DELETE: delete the hero from the server */
  deleteCandy(id: number): Observable<Candy> {
    const url = `${this.candiesUrl}/${id}`;
    return this.http.delete<Candy>(url, this.httpOptions).pipe(catchError(this.handleError<Candy>('deleteCandy')));
  }
  
  searchCandies(term: string): Observable<Candy[]> {
    if (!term.trim()) {
      // if not search term, return empty hero array.
      return of([]);
    }
    return this.http.get<Candy[]>(`${this.candiesUrl}/?name=${term}`).pipe(
      catchError(this.handleError<Candy[]>('searchHeroes', []))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
  
      //send the error to remote logging infrastructure
      console.error(error); // log to console instead
  
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }  
}
