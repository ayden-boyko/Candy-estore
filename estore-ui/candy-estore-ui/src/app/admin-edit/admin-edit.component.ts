import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Candy } from '../candy';
import { CandyService } from '../candy.service';
import { UsersService } from '../users.service';

@Component({
  selector: 'app-admin-edit',
  templateUrl: './admin-edit.component.html',
  styleUrls: ['./admin-edit.component.css']
})
export class AdminEditComponent implements OnInit {

  candy: Candy | undefined;

  constructor(private route: ActivatedRoute, private candyService: CandyService, private location: Location, private userService: UsersService) { }

  ngOnInit(): void {
    this.getCandy();
  }

  getCandy(): void{
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.candyService.getCandy(id).subscribe((candy) => (this.candy = candy))
  }

  goBack(): void {
    this.location.back();
  }

  edit(): void {
    if(this.candy){
      this.candyService.updateCandy(this.candy).subscribe(() => this.goBack());
    }
  }

}
