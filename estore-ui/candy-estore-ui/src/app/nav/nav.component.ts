import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from '../user';
import { UsersService } from '../users.service';
import { Location } from '@angular/common';
import { LoginComponent } from '../login/login.component';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {

  constructor(private userService:UsersService) { }

  ngOnInit(): void {
  }

  IsAdmin(): boolean{
    return this.userService.IsUserAdmin();
  }

  IsUserLoggedIn(): boolean{
    return this.userService.getisUserLoggedIn();
  }

  getUser(): string{
      return this.userService.getUserName();
  }

}
