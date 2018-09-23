/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AlmighteeTestModule } from '../../../test.module';
import { CommandItemUpdateComponent } from 'app/entities/command-item/command-item-update.component';
import { CommandItemService } from 'app/entities/command-item/command-item.service';
import { CommandItem } from 'app/shared/model/command-item.model';

describe('Component Tests', () => {
    describe('CommandItem Management Update Component', () => {
        let comp: CommandItemUpdateComponent;
        let fixture: ComponentFixture<CommandItemUpdateComponent>;
        let service: CommandItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AlmighteeTestModule],
                declarations: [CommandItemUpdateComponent]
            })
                .overrideTemplate(CommandItemUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CommandItemUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommandItemService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CommandItem(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.commandItem = entity;
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
                    const entity = new CommandItem();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.commandItem = entity;
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
