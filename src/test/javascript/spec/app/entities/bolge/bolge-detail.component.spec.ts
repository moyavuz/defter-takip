/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { BolgeDetailComponent } from 'app/entities/bolge/bolge-detail.component';
import { Bolge } from 'app/shared/model/bolge.model';

describe('Component Tests', () => {
    describe('Bolge Management Detail Component', () => {
        let comp: BolgeDetailComponent;
        let fixture: ComponentFixture<BolgeDetailComponent>;
        const route = ({ data: of({ bolge: new Bolge(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [BolgeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BolgeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BolgeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bolge).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
