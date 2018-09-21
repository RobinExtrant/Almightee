import { Component, OnInit, Input } from '@angular/core';
import { Pattern } from './../../shared/model/pattern.model';
import { CatalogComponent } from 'app/catalog/catalog.component';

@Component({
    selector: 'jhi-pattern-item',
    templateUrl: './pattern-item.component.html',
    styleUrls: ['./../catalog.css']
})
export class PatternItemComponent implements OnInit {
    @Input() private pattern: Pattern;
    @Input() private catalogComponent: CatalogComponent;

    constructor() {}

    ngOnInit() {}
}
