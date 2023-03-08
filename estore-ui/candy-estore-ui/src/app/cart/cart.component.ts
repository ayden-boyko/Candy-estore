import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Candy } from '../candy';
import { CandyService } from '../candy.service';
import { User } from '../user';
import { UsersService } from '../users.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})

export class CartComponent implements OnInit {
  quantity = 1
  candies!: Candy[]
  purchased: boolean = false;

  constructor(private userService : UsersService, private router : Router, private candyService : CandyService) {}

  ngOnInit(): void {
    this.candyService.getCandies().subscribe(candies => this.candies = candies)
  }

  changeAmount(id: number, amount: number): void {
    if (amount == 0)
    {
      this.removeFromCart(id)
    }
    else{
      this.userService.changeAmount(id,amount)
    }
  }

  validAmount(id: number, amount: number): boolean{
    return this.getByID(id).amount >= amount
  }

  getByID(id: number): Candy {
    for(var candy of this.candies){
      if (candy.id == id){return candy}
    }
    return this.candies[0]
  }

  validateCart(): boolean{
    for(let entry of this.userService.getCart()){
      if (!this.validAmount(entry[0], entry[1])){return false}
    }
    return true
  }

  removeFromCart(id: number) {
    this.userService.removeFromCart(id)
  }

  getCart(): Map<number, number> {
    return this.userService.getCart()
  }

  getpreviousOrder(): Map<number, number>{
    return this.userService.user!.previousOrder
  }

  purchaseCart(): void {
    this.userService.purchaseCart();
    this.purchased = true;
  }

  cartTotal(): string{
    var total = 0
    for (let key of this.getCart()){
      total += this.getByID(key[0]).price*key[1]
    }
    return parseFloat(total.toString()).toFixed(2);
  }

  

  
}
