/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { ZimmetTuruDetailComponent } from 'app/entities/zimmet-turu/zimmet-turu-detail.component';
import { ZimmetTuru } from 'app/shared/model/zimmet-turu.model';

describe('Component Tests', () => {
    describe('ZimmetTuru Management Detail Component', () => {
        let comp: ZimmetTuruDetailComponent;
        let fixture: ComponentFixture<ZimmetTuruDetailComponent>;
        const route = ({ data: of({ zimmetTuru: new ZimmetTuru(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [ZimmetTuruDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ZimmetTuruDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ZimmetTuruDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.zimmetTuru).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
