/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AlmighteeTestModule } from '../../../test.module';
import { CommandItemComponent } from 'app/entities/command-item/command-item.component';
import { CommandItemService } from 'app/entities/command-item/command-item.service';
import { CommandItem } from 'app/shared/model/command-item.model';

describe('Component Tests', () => {
    describe('CommandItem Management Component', () => {
        let comp: CommandItemComponent;
        let fixture: ComponentFixture<CommandItemComponent>;
        let service: CommandItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AlmighteeTestModule],
                declarations: [CommandItemComponent],
                providers: []
            })
                .overrideTemplate(CommandItemComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CommandItemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommandItemService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CommandItem(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.commandItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
