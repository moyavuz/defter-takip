/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { BirimDetailComponent } from 'app/entities/birim/birim-detail.component';
import { Birim } from 'app/shared/model/birim.model';

describe('Component Tests', () => {
    describe('Birim Management Detail Component', () => {
        let comp: BirimDetailComponent;
        let fixture: ComponentFixture<BirimDetailComponent>;
        const route = ({ data: of({ birim: new Birim(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [BirimDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BirimDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BirimDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.birim).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
