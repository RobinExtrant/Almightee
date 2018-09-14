import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { PatternService } from './../entities/pattern/pattern.service';
import { Pattern, IPattern } from './../shared/model/pattern.model';

@Component({
    selector: 'jhi-catalog',
    templateUrl: './catalog.component.html',
    styles: []
})
export class CatalogComponent implements OnInit {
    currentPage: number;
    totalPages: number;
    totalItems: number;
    patterns: Pattern[] = [];
    itemsPerPage: number;
    err: any;

    constructor(private patternService: PatternService) {}

    ngOnInit() {
        /*this.patterns = [
      {name: 'Stylo', author: 'Alfred', imageURL: 'fakeUrl1', price: 30},
      {name: 'Vie', author: 'Vincent', imageURL: 'badUrl1', price: 100000},
      {name: 'Ordi', author: 'Alfred', imageURL: 'fakeUrl2', price: 500},
      {name: 'Prise', author: 'Mathieu', imageURL: 'badUrl1', price: 0.23}
      ];

        this.patternService.query().subscribe(patternsRes => {
            this.patterns = patternsRes.body;
        });*/
        this.currentPage = 1;
        this.itemsPerPage = 6;
        this.load(this.currentPage - 1, this.itemsPerPage);
    }

    loadAll() {
        this.patternService.query().subscribe((res: HttpResponse<IPattern[]>) => this.paginatePatterns(res.body, res.headers));
        return;
    }

    next() {
        this.currentPage = this.currentPage + 1;
        this.load(this.currentPage - 1, this.itemsPerPage);
    }

    previous() {
        this.currentPage = this.currentPage - 1;
        this.load(this.currentPage - 1, this.itemsPerPage);
    }

    load(pageToSearch: number, numberItems: number) {
        this.patternService
            .query({
                page: pageToSearch,
                size: numberItems
            })
            .subscribe((res: HttpResponse<IPattern[]>) => this.paginatePatterns(res.body, res.headers));
        return;
    }

    private paginatePatterns(data: IPattern[], headers: HttpHeaders) {
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.totalPages = this.totalItems / this.itemsPerPage;
        this.patterns = data;
    }

    addToCart(pattern: Pattern) {
        console.log('On ajoute ici le motif ' + pattern.name + ' au panier');
    }
}
