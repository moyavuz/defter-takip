/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { CalisanDetailComponent } from 'app/entities/calisan/calisan-detail.component';
import { Calisan } from 'app/shared/model/calisan.model';

describe('Component Tests', () => {
    describe('Calisan Management Detail Component', () => {
        let comp: CalisanDetailComponent;
        let fixture: ComponentFixture<CalisanDetailComponent>;
        const route = ({ data: of({ calisan: new Calisan(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [CalisanDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CalisanDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CalisanDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.calisan).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
