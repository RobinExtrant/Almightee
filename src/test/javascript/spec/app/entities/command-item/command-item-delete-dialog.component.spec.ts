/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AlmighteeTestModule } from '../../../test.module';
import { CommandItemDeleteDialogComponent } from 'app/entities/command-item/command-item-delete-dialog.component';
import { CommandItemService } from 'app/entities/command-item/command-item.service';

describe('Component Tests', () => {
    describe('CommandItem Management Delete Component', () => {
        let comp: CommandItemDeleteDialogComponent;
        let fixture: ComponentFixture<CommandItemDeleteDialogComponent>;
        let service: CommandItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AlmighteeTestModule],
                declarations: [CommandItemDeleteDialogComponent]
            })
                .overrideTemplate(CommandItemDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommandItemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommandItemService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
