/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AlmighteeTestModule } from '../../../test.module';
import { PatternDetailComponent } from 'app/entities/pattern/pattern-detail.component';
import { Pattern } from 'app/shared/model/pattern.model';

describe('Component Tests', () => {
    describe('Pattern Management Detail Component', () => {
        let comp: PatternDetailComponent;
        let fixture: ComponentFixture<PatternDetailComponent>;
        const route = ({ data: of({ pattern: new Pattern(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AlmighteeTestModule],
                declarations: [PatternDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PatternDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PatternDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pattern).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
