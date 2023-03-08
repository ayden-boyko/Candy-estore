import { Component, OnInit } from '@angular/core';

import { Observable, Subject } from 'rxjs';

import {
   debounceTime, distinctUntilChanged, switchMap
 } from 'rxjs/operators';

import { Candy } from '../candy';
import { CandyService } from '../candy.service';

@Component({
  selector: 'app-candy-search',
  templateUrl: './candy-search.component.html',
  styleUrls: [ './candy-search.component.css' ]
})
export class CandySearchComponent implements OnInit {
  candies$!: Observable<Candy[]>;
  private searchTerms = new Subject<string>();

  constructor(private candyService: CandyService) {}

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
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
