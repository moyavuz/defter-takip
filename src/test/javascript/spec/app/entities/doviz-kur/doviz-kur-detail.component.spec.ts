/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { DovizKurDetailComponent } from 'app/entities/doviz-kur/doviz-kur-detail.component';
import { DovizKur } from 'app/shared/model/doviz-kur.model';

describe('Component Tests', () => {
    describe('DovizKur Management Detail Component', () => {
        let comp: DovizKurDetailComponent;
        let fixture: ComponentFixture<DovizKurDetailComponent>;
        const route = ({ data: of({ dovizKur: new DovizKur(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [DovizKurDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DovizKurDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DovizKurDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dovizKur).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
