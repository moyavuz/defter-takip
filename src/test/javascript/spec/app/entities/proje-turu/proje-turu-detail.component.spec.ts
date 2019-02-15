/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { ProjeTuruDetailComponent } from 'app/entities/proje-turu/proje-turu-detail.component';
import { ProjeTuru } from 'app/shared/model/proje-turu.model';

describe('Component Tests', () => {
    describe('ProjeTuru Management Detail Component', () => {
        let comp: ProjeTuruDetailComponent;
        let fixture: ComponentFixture<ProjeTuruDetailComponent>;
        const route = ({ data: of({ projeTuru: new ProjeTuru(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [ProjeTuruDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProjeTuruDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProjeTuruDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.projeTuru).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
