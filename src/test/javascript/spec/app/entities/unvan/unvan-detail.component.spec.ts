/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { UnvanDetailComponent } from 'app/entities/unvan/unvan-detail.component';
import { Unvan } from 'app/shared/model/unvan.model';

describe('Component Tests', () => {
    describe('Unvan Management Detail Component', () => {
        let comp: UnvanDetailComponent;
        let fixture: ComponentFixture<UnvanDetailComponent>;
        const route = ({ data: of({ unvan: new Unvan(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [UnvanDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(UnvanDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UnvanDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.unvan).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
