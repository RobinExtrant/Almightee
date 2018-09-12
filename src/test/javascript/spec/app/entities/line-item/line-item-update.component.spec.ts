/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AlmighteeTestModule } from '../../../test.module';
import { LineItemUpdateComponent } from 'app/entities/line-item/line-item-update.component';
import { LineItemService } from 'app/entities/line-item/line-item.service';
import { LineItem } from 'app/shared/model/line-item.model';

describe('Component Tests', () => {
    describe('LineItem Management Update Component', () => {
        let comp: LineItemUpdateComponent;
        let fixture: ComponentFixture<LineItemUpdateComponent>;
        let service: LineItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AlmighteeTestModule],
                declarations: [LineItemUpdateComponent]
            })
                .overrideTemplate(LineItemUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LineItemUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LineItemService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new LineItem(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.lineItem = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new LineItem();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.lineItem = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
