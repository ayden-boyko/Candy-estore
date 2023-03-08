import { Component, OnInit, Input } from '@angular/core';
import { Candy } from '../candy';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { CandyService } from '../candy.service';
import { UsersService } from '../users.service';

@Component({
  selector: 'cart-app-candy-detail',
  templateUrl: './cart-candy-detail.component.html',
  styleUrls: ['./cart-candy-detail.component.css']
})

export class CartCandyDetailComponent implements OnInit {
  @Input() id!: number;
  candy!: Candy

  constructor(
    private route: ActivatedRoute,
    private candyService: CandyService,
    private location: Location,
    private userService: UsersService
  ) { }

  ngOnInit(): void {
    this.getCandy()
  }

  invalidAmount(): boolean{
    // I hate this code so much
    if (this.userService.user != undefined){
      const amount = this.userService.user.cart.get(this.candy.id)
      if (amount != undefined)
      return this.candy!.amount < amount
    }
    return false
  }

  getCandy(): void {
    this.candyService.getCandy(this.id).subscribe(candy => this.candy = candy);
  }

}
