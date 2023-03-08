import { Component, OnInit } from '@angular/core';

import { FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from '../user';
import { UsersService } from '../users.service';

@Component({
   selector: 'app-login',
   templateUrl: './login.component.html',
   styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

   usernameLogin: string = '';
   usernameCreate: string = '';
   isValid: boolean = true;

   constructor(private userService : UsersService, private router : Router) { }

   ngOnInit() {
   }
   onClickSubmitLogin() {
      console.log("Login page: " + this.usernameLogin);
      this.userService.login(this.usernameLogin);
      if(this.userService.user === undefined){
         this.isValid = false
      }
   }

   onClickSubmitCreate(){
      console.log("Login page: " + this.usernameCreate);
      this.userService.createAccount(this.usernameCreate);
      this.isValid = this.userService.getisUserLoggedIn();
      if(this.userService.user === undefined){
         this.isValid = false
      }
   }

   onClickLogout(){
      this.userService.logout();
      this.isValid = true;
   }

   IsUserLoggedIn(): boolean{
      return this.userService.getisUserLoggedIn();
    }
}