import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Candy } from '../candy';
import { CandyService } from '../candy.service';
import { UsersService } from '../users.service';

@Component({
  selector: 'app-candy-detail',
  templateUrl: './candy-detail.component.html',
  styleUrls: [ './candy-detail.component.css' ]
})
export class CandyDetailComponent implements OnInit {
  candy: Candy | undefined;

  constructor(
    private route: ActivatedRoute,
    private candyService: CandyService,
    private location: Location,
    private userService: UsersService
  ) {}

  ngOnInit(): void {
    this.getCandy();
  }

  addToCart(id: number): void {
    this.userService.addToCart(id)
  }

  getCandy(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.candyService.getCandy(id)
      .subscribe(candy => this.candy = candy);
  }

  goBack(): void {
    this.location.back();
  }

  displayRating(): string {
    if (this.candy != undefined) {
      var rating = parseFloat((this.candy.ratingTotal / this.candy.ratingNum).toString()).toFixed(2);
      if(rating === "NaN"){
        return "0";
      }
      else{
        return rating;
      }
    } 
    return "";
  }

  addRating(rating: number): void {
    if (this.candy != undefined) {
      this.candy.ratingNum++; this.candy.ratingTotal += rating;
      this.candyService.updateCandy(this.candy).subscribe();
    }
  }

  save(): void {
    if (this.candy) {
      this.candyService.updateCandy(this.candy)
        .subscribe(() => this.goBack());
    }
  }

  IsUserLoggedIn(): boolean{
    return this.userService.getisUserLoggedIn();
  }
}
