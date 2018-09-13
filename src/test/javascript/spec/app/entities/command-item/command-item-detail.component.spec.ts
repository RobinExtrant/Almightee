/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AlmighteeTestModule } from '../../../test.module';
import { CommandItemDetailComponent } from 'app/entities/command-item/command-item-detail.component';
import { CommandItem } from 'app/shared/model/command-item.model';

describe('Component Tests', () => {
    describe('CommandItem Management Detail Component', () => {
        let comp: CommandItemDetailComponent;
        let fixture: ComponentFixture<CommandItemDetailComponent>;
        const route = ({ data: of({ commandItem: new CommandItem(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AlmighteeTestModule],
                declarations: [CommandItemDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CommandItemDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommandItemDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.commandItem).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
