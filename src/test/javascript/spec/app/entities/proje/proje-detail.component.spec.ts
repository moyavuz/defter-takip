/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { ProjeDetailComponent } from 'app/entities/proje/proje-detail.component';
import { Proje } from 'app/shared/model/proje.model';

describe('Component Tests', () => {
    describe('Proje Management Detail Component', () => {
        let comp: ProjeDetailComponent;
        let fixture: ComponentFixture<ProjeDetailComponent>;
        const route = ({ data: of({ proje: new Proje(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [ProjeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProjeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProjeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.proje).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
