import { Component, OnInit } from '@angular/core';
import { Candy } from '../candy';
import { CandyService } from '../candy.service';

@Component({
  selector: 'app-candies',
  templateUrl: './candies.component.html',
  styleUrls: ['./candies.component.css']
})

export class CandiesComponent implements OnInit {
  candies: Candy[] = [];

  constructor(private candyService: CandyService) 
  { }

  ngOnInit(): void {
    this.getCandies();
  }

  getCandies(): void {
    this.candyService.getCandies().subscribe(candies => this.candies = candies);
  }

  delete(candy: Candy): void {
    this.candies = this.candies.filter(c => c !== candy);
    this.candyService.deleteCandy(candy.id).subscribe();
  }

  add(name: string): void {
    name = name.trim();
    if (!name) { return; }
    this.candyService.addCandy({ name } as Candy)
      .subscribe(candy => {
        this.candies.push(candy);
      });
  }


  
}
