/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { IlDetailComponent } from 'app/entities/il/il-detail.component';
import { Il } from 'app/shared/model/il.model';

describe('Component Tests', () => {
    describe('Il Management Detail Component', () => {
        let comp: IlDetailComponent;
        let fixture: ComponentFixture<IlDetailComponent>;
        const route = ({ data: of({ il: new Il(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [IlDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(IlDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IlDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.il).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
