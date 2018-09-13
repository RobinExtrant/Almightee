import { Component, OnInit } from '@angular/core';
import { PatternService } from './../entities/pattern/pattern.service';
import { Pattern } from './../shared/model/pattern.model';

@Component({
    selector: 'jhi-catalog',
    templateUrl: './catalog.component.html',
    styles: []
})
export class CatalogComponent implements OnInit {
    patterns: Pattern[] = [];
    err: any;

    constructor(private patternService: PatternService) {}

    ngOnInit() {
        /*this.patterns = [
      {name: 'Stylo', author: 'Alfred', imageURL: 'fakeUrl1', price: 30},
      {name: 'Vie', author: 'Vincent', imageURL: 'badUrl1', price: 100000},
      {name: 'Ordi', author: 'Alfred', imageURL: 'fakeUrl2', price: 500},
      {name: 'Prise', author: 'Mathieu', imageURL: 'badUrl1', price: 0.23}
      ];*/

        this.patternService.query().subscribe(patternsRes => {
            this.patterns = patternsRes.body;
        });
    }
}
