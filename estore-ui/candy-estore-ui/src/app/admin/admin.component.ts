import { Location } from '@angular/common';
import { Component, OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { debounceTime, distinctUntilChanged, Observable, Subject, switchMap } from 'rxjs';
import { Candy } from '../candy';
import { CandyService } from '../candy.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  candies: Candy[] = [];
  candy: Candy | undefined;
  candies$!: Observable<Candy[]>;
  private searchTerms = new Subject<string>();

  model: any = {};

  constructor(private candyService: CandyService, private route: ActivatedRoute, private location: Location) {
  }

  getCandy(): void{
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.candyService.getCandy(id).subscribe((candy) => (this.candy = candy))
  }

  getCandies(): void{
    this.candyService.getCandies().subscribe((candies) => (this.candies = candies))
  }

  add(name: string, amount: string, price: string, description: string): void{
    name = name.trim();
    if (!name) {
      return;
    }
    this.candyService.addCandy({name, amount, price, description} as unknown as Candy)
      .subscribe((candy) => {this.candies.push(candy)})
  }

  delete(candy: Candy): void {
    this.candies = this.candies.filter((c) => c !== candy);
    this.candyService.deleteCandy(candy.id).subscribe();
  }

  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.getCandies();
    this.getCandy();
    this.candies$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.candyService.searchCandies(term)),
    );
  }

}
