/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { StokTakipDetailComponent } from 'app/entities/stok-takip/stok-takip-detail.component';
import { StokTakip } from 'app/shared/model/stok-takip.model';

describe('Component Tests', () => {
    describe('StokTakip Management Detail Component', () => {
        let comp: StokTakipDetailComponent;
        let fixture: ComponentFixture<StokTakipDetailComponent>;
        const route = ({ data: of({ stokTakip: new StokTakip(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [StokTakipDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StokTakipDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StokTakipDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.stokTakip).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
