/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AlmighteeTestModule } from '../../../test.module';
import { LineItemDeleteDialogComponent } from 'app/entities/line-item/line-item-delete-dialog.component';
import { LineItemService } from 'app/entities/line-item/line-item.service';

describe('Component Tests', () => {
    describe('LineItem Management Delete Component', () => {
        let comp: LineItemDeleteDialogComponent;
        let fixture: ComponentFixture<LineItemDeleteDialogComponent>;
        let service: LineItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AlmighteeTestModule],
                declarations: [LineItemDeleteDialogComponent]
            })
                .overrideTemplate(LineItemDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LineItemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LineItemService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
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
            ));
        });
    });
});
