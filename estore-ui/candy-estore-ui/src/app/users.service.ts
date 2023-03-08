import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { User } from './user';
import { NONE_TYPE } from '@angular/compiler';
import { waitForAsync } from '@angular/core/testing';


@Injectable({
  providedIn: 'root'
})
export class UsersService {
  
  private usersUrl = 'http://localhost:8080/customers'
  isUserLoggedIn: boolean = false;
  user: User | undefined ; 
  isUserAdmin: boolean = false;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient
  ) { }

  login(username: string): void {
    console.log(username);
      // If pass for admin
    if (username == 'admin'){
      this.user = {id: 0, username: 'admin', cart: new Map<number, number>(), previousOrder: new Map<number, number>()}
      this.isUserAdmin = true
      this.isUserLoggedIn = true
    }
    else{
      this.getUserByName(username).subscribe(user => 
      {
        this.user = user
        this.fixCart(user)
        if(this.user != undefined){
          this.isUserLoggedIn = true
        }
      })
    }
  }


  logout(): void {
    this.isUserLoggedIn = false
    this.isUserAdmin = false
    this.user = undefined
  }

  IsUserAdmin() : boolean{
    return this.isUserAdmin;
  }

  getisUserLoggedIn(): boolean {
    return this.isUserLoggedIn
  }

  getUserID(): number {
    if (this.getisUserLoggedIn() && this.user != null){
      return this.user.id
    }
    return 0
  }

  getCart(): Map<number, number>{
    if (this.user != null){return this.user.cart}
    return new Map
  }

  getUserName(): string {
    if (this.getisUserLoggedIn() && this.user != undefined){
      return this.user.username
    }
    return ""
  }

  updateCart() {
    const hold = {id : this.user?.id, username: this.user?.username, cart: Object.fromEntries(this.user!.cart)}
    this.http.put<User>(this.usersUrl, hold , this.httpOptions).pipe(catchError(this.handleError<any>('updateUser'))).subscribe(user => 
      {
        this.user = user
        this.fixCart(user)
      });
    console.log(this.user)
  }

  purchaseCart(){
    this.http.post<User>(`${this.usersUrl}/${this.user?.id}`, this.httpOptions).subscribe(user => 
      {
        this.user = user
        this.fixCart(user)
      })
  }

  fixCart(user: User){
    const newmap = new Map<number,number>()
        for (let entry of Object.entries(user.cart)) {
          newmap.set(parseInt(entry[0]), entry[1])
        }
        this.user!.cart = newmap
  }




  createAccount(username: string): void {
    if (username == 'admin'){
      return
    }
    this.getUserByName(username).subscribe(user => {
      if (user != undefined){
        return
      }
      else{
        this.addUser({id: 1, username: username, cart: new Map, previousOrder: new Map}).subscribe(user => 
          {this.user = user
          this.fixCart(user)
          })
        this.isUserLoggedIn = true
      }
    })
  }

  addToCart(id: number): void {
    if (!this.user?.cart.has(id)){
      this.user?.cart.set(id, 1)
      this.updateCart()
    }
  }

  removeFromCart(id:number): void{
    this.user?.cart.delete(id)
    this.updateCart()
  }

  changeAmount(id:number, amount:number):void{
    this.user!.cart.set(id, amount)
    this.updateCart()
  }


  getUserByName(username: string): Observable<User>{
    const url = `${this.usersUrl}/?name=${username}`;
    return this.http.get<User>(url).pipe(catchError(this.handleError<User>(`getUser username=${username}`)))
  }

  addUser(user: User): Observable<User> {
    return this.http.post<User>(this.usersUrl, user, this.httpOptions).pipe(catchError(this.handleError<User>('addCandy')));
  }

  getUser(id: number): Observable<User>{
    const url = `${this.usersUrl}/${id}`;
    return this.http.get<User>(url).pipe(catchError(this.handleError<User>(`getUser id=${id}`)));
  }



  /*
  

  

  
  */

  /*
  getCandies(): Observable<Candy[]> {
    return this.http.get<Candy[]>(this.candiesUrl)
      .pipe(catchError(this.handleError<Candy[]>('getCandies', [])))
  }

  updateCandy(candy: Candy): Observable<any> {
    return this.http.put(this.candiesUrl, candy, this.httpOptions).pipe(catchError(this.handleError<any>('updateCandy')));
  }

  

  deleteCandy(id: number): Observable<Candy> {
    const url = `${this.candiesUrl}/${id}`;
    return this.http.delete<Candy>(url, this.httpOptions).pipe(catchError(this.handleError<Candy>('deleteCandy')));
  }
  */

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
  
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead
  
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }  
}
